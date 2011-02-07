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
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class Remote extends Activity {
    
    private Connection mConnection;
    private Handler mHandler = new Handler();

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.standard);
        for (RemoteButton button : ButtonMappings.BUTTONS) {
        	View v = findViewById(button.resId);
        	if (v != null) {
        		final int[] codes = button.keyCodes;
        		v.setOnClickListener(new View.OnClickListener() {
					
					public void onClick(View v) {
						vibrate(Remote.this, 50);
						Integer[] codes1 = new Integer[codes.length];
						for (int i=0; i < codes.length; i++) {
							codes1[i] = codes[i];
						}
						SendKeysTask sendCodesTask = new SendKeysTask(); 
						sendCodesTask.execute(codes1);
					}
				});
        	}
        }
    }
    
	@Override
	protected void onStart() {
		super.onStart();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String host = prefs.getString("serverHost", "");
		int port = 2345;
		try {
			port = Integer.parseInt(prefs.getString("serverPort", "2345"));
		} catch (NumberFormatException e) { }
		mConnection = new Connection(host, port);
	}
	
    private class SendKeysTask extends AsyncTask<Integer, Void, Void> {
    	@Override
    	protected Void doInBackground(Integer... codes) {
    		boolean first = true;
    		try {
    			for (int code : codes) {
    				if (!first) Thread.sleep(300);
    				mConnection.sendCode(code);
    				if (code == ButtonMappings.BTN_POWER_OFF) {
    					finish();
    				}
    				first = false;
    			}
    		} catch (InterruptedException e) {

    		} catch (final UnknownHostException e) {
				mHandler.post(new Runnable() {
					
					public void run() {
						Toast.makeText(Remote.this, e.getMessage(), Toast.LENGTH_LONG).show();
					}
				});
			} catch (final IOException e) {
				mHandler.post(new Runnable() {
					
					public void run() {
						Toast.makeText(Remote.this, e.getMessage(), Toast.LENGTH_LONG).show();
					}
				});
			}
    		return null;
    	}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.main_menu, menu);
    	return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.menu_item_settings:
    		Intent prefsActivity = new Intent(this, MainPreferencesActivity.class);
    		startActivity(prefsActivity);
    		break;
    	case R.id.menu_item_about:
    		Intent aboutActivity = new Intent(this, AboutActivity.class);
    		startActivity(aboutActivity);
    		break;
    	}
    	return super.onMenuItemSelected(featureId, item);
    }
    
    public static void vibrate(Context context, long millis) {
    	Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    	v.vibrate(millis);
    }
}