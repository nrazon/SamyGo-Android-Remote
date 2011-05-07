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

import android.content.Context;
import android.content.res.Resources;
import android.preference.DialogPreference;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;


public class SeekBarPreference extends DialogPreference {

	private static final String ANDROID_NAMESPACE = "http://schemas.android.com/apk/res/android";
	private static final String SAMYGO_NAMESPACE = "http://samyGoRemote.app.quist.de/apk/res/android";

	private static final String DIALOG_TEXT_ATTRIBUTE = "dialogText";
	private static final String VALUE_TEXT_ATTRIBUTE = "valueText";
	private static final String DEFAULT_VALUE_ATTRIBUTE = "defaultValue";
	private static final String MAX_ATTRIBUTE = "max";

	private static final int DEFAULT_VALUE = 0;
	private static final int DEFAULT_MAX = 100;

	private Context mContext;

	private SeekBar mSeekBar;
	private TextView mValueTextView;

	private CharSequence mDialogText;
	private CharSequence mValueText;
	private int mDefault;
	private int mMax;
	private int mValue = DEFAULT_VALUE;

	public SeekBarPreference(Context context, AttributeSet attrs) { 
		super(context, attrs); 
		mContext = context;
		Resources res = context.getResources();
		mDialogText = attrs.getAttributeValue(ANDROID_NAMESPACE,DIALOG_TEXT_ATTRIBUTE);

		// Get the dialog text
		int dialogTextResource = attrs.getAttributeResourceValue(ANDROID_NAMESPACE, DIALOG_TEXT_ATTRIBUTE, -1);
		if (dialogTextResource != -1) {
			mDialogText = res.getText(dialogTextResource);
		} else {
			mDialogText = attrs.getAttributeValue(ANDROID_NAMESPACE, DIALOG_TEXT_ATTRIBUTE);
		}

		// Get the value text
		mValueText = attrs.getAttributeValue(SAMYGO_NAMESPACE, VALUE_TEXT_ATTRIBUTE);
		int valueTextResource = attrs.getAttributeResourceValue(SAMYGO_NAMESPACE, VALUE_TEXT_ATTRIBUTE, -1);
		if (valueTextResource != -1) {
			mValueText = res.getText(valueTextResource);
		} else {
			mValueText = attrs.getAttributeValue(SAMYGO_NAMESPACE, VALUE_TEXT_ATTRIBUTE);
		}

		mDefault = attrs.getAttributeIntValue(ANDROID_NAMESPACE,DEFAULT_VALUE_ATTRIBUTE, DEFAULT_VALUE);
		mMax = attrs.getAttributeIntValue(ANDROID_NAMESPACE,MAX_ATTRIBUTE, DEFAULT_MAX);

	}
	@Override 
	protected View onCreateDialogView() {
		LayoutInflater layoutInflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = layoutInflator.inflate(R.layout.seek_bar_preference_layout, null);

		// Message text
		TextView messageTextView = (TextView) layout.findViewById(android.R.id.text1);
		if (mDialogText != null) {
			messageTextView.setText(mDialogText);
		} else {
			messageTextView.setVisibility(View.GONE);
		}

		// Value text
		mValueTextView = (TextView) layout.findViewById(android.R.id.text2);

		// Seek bar
		mSeekBar = (SeekBar) layout.findViewById(android.R.id.progress);
		mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				String valueString = Integer.toString(progress);
				if (mValueText == null) {
					mValueTextView.setText(valueString);
				} else {
					mValueTextView.setText(TextUtils.expandTemplate(mValueText, valueString));
				}
				mValue = progress;
				if (shouldPersist()) persistInt(progress);
				callChangeListener(progress);
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			public void onStopTrackingTouch(SeekBar seekBar) {
			}
		});
		mSeekBar.setMax(mMax);
		mSeekBar.setProgress(mValue);
		if (shouldPersist()) {
			mValue = getPersistedInt(mDefault);
		}

		return layout;
	}

	@Override
	protected int getPersistedInt(int defaultReturnValue) {
		int result;
		try {
			result = super.getPersistedInt(defaultReturnValue);
		} catch (ClassCastException e) {
			String stringValue = super.getPersistedString(Integer.toString(defaultReturnValue));
			result = Integer.parseInt(stringValue);
		}
		return result;
	}

	@Override 
	protected void onBindDialogView(View view) {
		super.onBindDialogView(view);
		mSeekBar.setMax(mMax);
		mSeekBar.setProgress(mValue);
	}

	@Override
	protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
		super.onSetInitialValue(restorePersistedValue, defaultValue);
		if (restorePersistedValue) {
			mValue = getPersistedInt(mDefault);
		} else {
			mValue = (Integer) defaultValue;
		}
		if (shouldPersist()) {
			persistInt(mValue);
		}
	}

}