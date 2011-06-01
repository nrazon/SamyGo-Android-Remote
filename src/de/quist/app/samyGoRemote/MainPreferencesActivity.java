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

import de.quist.app.samyGoRemote.HostnamePreference.OnSenderFactoryChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class MainPreferencesActivity extends PreferenceActivity {

	private Preference portPref;
	private ListPreference senderFactoryPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.main_preferences);
		portPref = findPreference(Remote.PREFS_SERVER_PORT_KEY);
		senderFactoryPref = (ListPreference) findPreference(Remote.PREFS_SENDER_FACTORY_KEY);
		senderFactoryPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				setPortEnabledState(newValue);
				return true;
			}
		});
		Preference hostPrefs = findPreference(Remote.PREFS_SERVER_HOST_KEY);
		((HostnamePreference) hostPrefs).setOnSenderFactoryChangeListener(new OnSenderFactoryChangeListener() {
			
			public boolean onSenderFactoryChange(String newValue) {
				senderFactoryPref.setValue(newValue);
				setPortEnabledState(newValue);
				return true;
			}
			
		});
		String value = PreferenceManager.getDefaultSharedPreferences(this).getString(Remote.PREFS_SENDER_FACTORY_KEY, Remote.PREFS_SENDER_FACTORY_DEFAULT);
		setPortEnabledState(value);
	}
	
	private void setPortEnabledState(Object keyCodeSenderFactory) {
		boolean portEnabled = keyCodeSenderFactory.equals(BSeriesKeyCodeSenderFactory.class.getCanonicalName());
		portPref.setEnabled(portEnabled);
		if (portEnabled) {
			Toast.makeText(this, R.string.b_series_info, Toast.LENGTH_LONG).show();
		}
	}

}
