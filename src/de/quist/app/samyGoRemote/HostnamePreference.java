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

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Vibrator;
import android.preference.EditTextPreference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import de.quist.app.samyGoRemote.upnp.Discovery;

public class HostnamePreference extends EditTextPreference {

	public interface OnSenderFactoryChangeListener {

		boolean onSenderFactoryChange(String newValue);
		
	}

	private OnSenderFactoryChangeListener onSenderFactoryChangeListener;

	public HostnamePreference(Context context) {
		super(context);
		setDialogLayoutResource(R.layout.hostname_preference);
	}
	
	public HostnamePreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		setDialogLayoutResource(R.layout.hostname_preference);
	}
	
	public HostnamePreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setDialogLayoutResource(R.layout.hostname_preference);
	}
	
	@Override
	protected void onAddEditTextToDialogView(View dialogView, EditText editText) {
		 ViewGroup container = (ViewGroup) dialogView.findViewById(R.id.hostname_edit_text);
		 if (container != null) {
			 container.addView(editText,
                     ViewGroup.LayoutParams.FILL_PARENT,
                     ViewGroup.LayoutParams.WRAP_CONTENT);
		 }
	}
	
	@Override
	protected void onBindDialogView(View view) {
		super.onBindDialogView(view);
		Button button = (Button) view.findViewById(R.id.auto_search_button);
		button.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				WifiManager wifiManager = (WifiManager) getContext().getSystemService(Context.WIFI_SERVICE);
				if (!wifiManager.isWifiEnabled() || wifiManager.getConnectionInfo().getIpAddress() == 0) {
					Toast.makeText(getContext(), R.string.enable_wifi, Toast.LENGTH_LONG).show();
				}
				
				Discovery d = new Discovery() {
					private ProgressDialog progress;

					@Override
					protected void onPreExecute() {
						CharSequence title = getContext().getResources().getText(R.string.tv_discovery_progress_title);
						CharSequence message = getContext().getResources().getText(R.string.tv_discovery_progress_message);
						progress = ProgressDialog.show(getContext(), title, message);
					}
					
					protected void onPostExecute(java.net.InetAddress result) {
						if (progress != null) progress.dismiss();
						if (result != null) {
							SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
							if (isCSeries) {
								prefs.edit().putString(Remote.PREFS_SENDER_FACTORY_KEY, CSeriesKeyCodeSenderFactory.class.getCanonicalName()).commit();
							} else {
								prefs.edit().putString(Remote.PREFS_SENDER_FACTORY_KEY, BSeriesKeyCodeSenderFactory.class.getCanonicalName()).commit();
							}
							if (onSenderFactoryChangeListener != null) {
								onSenderFactoryChangeListener.onSenderFactoryChange(prefs.getString(Remote.PREFS_SENDER_FACTORY_KEY, Remote.PREFS_SENDER_FACTORY_DEFAULT));
							}
							notifyDependencyChange(false);
							getEditText().setText(result.getHostName());
							Toast.makeText(getContext(), R.string.tv_discovery_found, Toast.LENGTH_LONG).show();
							Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
							vibrator.vibrate(new long[] {0, 300,300,300}, -1);
						} else {
							Toast.makeText(getContext(), R.string.tv_discovery_not_found, Toast.LENGTH_LONG).show();
						}
					};
				};
				d.execute();
			}
		});
	}
	
	public void setOnSenderFactoryChangeListener(OnSenderFactoryChangeListener onSenderFactoryChangeListener) {
		this.onSenderFactoryChangeListener = onSenderFactoryChangeListener;
	}
	
}
