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

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.preference.ListPreference;
import android.text.TextUtils;
import android.util.AttributeSet;

public class LayoutListPreference extends ListPreference {

	private LayoutManager layoutManager;

	public LayoutListPreference(Context context) {
		super(context);
		this.setEntries(getEntries());
		this.setEntryValues(getEntryValues());
	}

	public LayoutListPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.layoutManager = new LayoutManager(context);
		this.setEntries(getEntries());
		this.setEntryValues(getEntryValues());
	}

	@Override
	public CharSequence[] getEntries() {
		return this.layoutManager.getEntries();
	}

	@Override
	public CharSequence[] getEntryValues() {
		return this.layoutManager.getEntryValues();
	}

	@Override
	public CharSequence getSummary() {
		CharSequence result = super.getSummary();
		return TextUtils.expandTemplate(result, getEntry());
	}

	@Override
	public void setValue(String value) {
		super.setValue(value);
		notifyChanged();
	}

	@Override
	protected void onPrepareDialogBuilder(Builder builder) {
		super.onPrepareDialogBuilder(builder);
	}
}
