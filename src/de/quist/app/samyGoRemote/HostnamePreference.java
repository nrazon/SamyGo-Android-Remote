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
import android.os.Vibrator;
import android.preference.EditTextPreference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import de.quist.app.samyGoRemote.upnp.Discovery;

public class HostnamePreference extends EditTextPreference {

	public HostnamePreference(Context context) {
		super(context);
	}
	
	public HostnamePreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public HostnamePreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	protected void onBindDialogView(View view) {
		super.onBindDialogView(view);
		Button button = new Button(getContext());
		button.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
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
		button.setText(R.string.tv_discovery_button);
		((ViewGroup)((ViewGroup) view).getChildAt(0)).addView(button);
	}
	
}
