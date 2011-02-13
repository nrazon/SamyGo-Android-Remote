/*
 *  Copyright (C) 2011  Tom Quist
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You can get the GNU General Public License at
 *  http://www.gnu.org/licenses/gpl.html
 */
package de.quist.app.samyGoRemote;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.NoSuchElementException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;

public class LayoutManager {

	public static class LayoutInfo {
		public String uid;
		public int resId;
		public CharSequence name;
		public boolean isEnabled;
	}

	private static final String NAMESPACE = "http://samyGoRemote.app.quist.de/apk/res/android";
	private static final String TAG = LayoutManager.class.getSimpleName();
	private Context context;
	private LayoutInfo[] entries;
	private HashMap<String, LayoutInfo> uriMap;

	public LayoutManager(Context context) {
		this.context = context;
		this.fetchEntries();
	}

	private Context getContext() {
		return this.context;
	}

	private void fetchEntries() {
		Context c = getContext();
		Resources res = c.getResources();
		Field[] fields = R.layout.class.getDeclaredFields();
		ArrayList<LayoutInfo> entries = new ArrayList<LayoutInfo>(fields.length);
		for (Field field : fields) {
			boolean isStatic = Modifier.isStatic(field.getModifiers());
			boolean isInt = field.getType().equals(int.class);
			if (isStatic && isInt) {
				try {
					int resId = field.getInt(null);
					LayoutInfo info = getLayoutInformation(res, resId);
					if (info != null) {
						entries.add(info);
					}
				} catch (IllegalArgumentException e) {
				} catch (IllegalAccessException e) {
				}
			}
		}
		Collections.sort(entries, new Comparator<LayoutInfo>() {

			public int compare(LayoutInfo object1, LayoutInfo object2) {
				String n1 = object1.name.toString();
				String n2 = object2.name.toString();
				return n1.compareTo(n2);
			}

		});
		this.entries = (LayoutInfo[]) entries.toArray(new LayoutInfo[entries.size()]);

		this.uriMap = new HashMap<String, LayoutManager.LayoutInfo>();
		for (LayoutInfo info : entries) {
			uriMap.put(info.uid, info);
		}
	}

	private static LayoutInfo getLayoutInformation(Resources res, int resId) {
		XmlResourceParser parser = res.getLayout(resId);
		try {
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if(eventType == XmlPullParser.START_TAG) {
					CharSequence name = null;
					int nameResource = parser.getAttributeResourceValue(NAMESPACE, "name", -1);
					if (nameResource != -1) {
						name = res.getText(nameResource);
					} else {
						name = parser.getAttributeValue(NAMESPACE, "name");
					}
					String uid;
					int uidResource = parser.getAttributeResourceValue(NAMESPACE, "uid", -1);
					if (uidResource != -1) {
						uid = res.getString(uidResource);
					} else {
						uid = parser.getAttributeValue(NAMESPACE, "uid");
					}
					boolean enabled = parser.getAttributeBooleanValue(NAMESPACE, "enabled", false);
					if (name != null && uid != null) {
						Log.v(TAG, "Resource " + res.getResourceName(resId) + " is not enabled.");
						LayoutInfo info = new LayoutInfo();
						info.uid = uid;
						info.resId = resId;
						info.name = name;
						info.isEnabled = enabled;
						return info;
					} else if (name != null) {
						Log.w(TAG, "Resource " + res.getResourceName(resId) + " does not have a uid attribute.");
					} else if (uid != null) {
						Log.w(TAG, "Resource " + res.getResourceName(resId) + " does not have a name attribute.");
					}
					break;
				}
				eventType = parser.next();
			}
		} catch (IOException e) {
		} catch (XmlPullParserException e) {
		}
		return null;
	}

	/**
	 * 
	 * @param layoutUri
	 * @return
	 * @throws NoSuchElementException If element does not exist
	 */
	public int getLayoutResource(String layoutUri) throws NoSuchElementException {
		if (!uriMap.containsKey(layoutUri)) {
			throw new NoSuchElementException();
		}
		return uriMap.get(layoutUri).resId;
	}

	public CharSequence[] getEntries() {
		ArrayList<CharSequence> result = new ArrayList<CharSequence>(this.entries.length);
		for (LayoutInfo entry : this.entries) {
			if (entry.isEnabled) result.add(entry.name);
		}
		return (CharSequence[]) result.toArray(new CharSequence[result.size()]);
	}

	public CharSequence[] getEntryValues() {
		ArrayList<CharSequence> result = new ArrayList<CharSequence>(this.entries.length);
		for (LayoutInfo entry : this.entries) {
			if (entry.isEnabled) result.add(entry.uid);
		}
		return (CharSequence[]) result.toArray(new CharSequence[result.size()]);
	}

}
