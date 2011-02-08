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
import java.util.NoSuchElementException;

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
    
	private static final String PREFS_LAYOUT_KEY = "layout";
	private static final String PREFS_LAYOUT_DEFAULT = "layout:de.quist.app.samyGoRemote.bn59_00861a";
	private static final String PREFS_SERVER_HOST_KEY = "serverHost";
	private static final String PREFS_SERVER_HOST_DEFAULT = "changeme";
	private static final String PREFS_SERVER_PORT_KEY = "serverPort";
	private static final String PREFS_SERVER_PORT_DEFAULT = "2345";
	private static final String PREFS_VIBRATE_KEY = "vibrationDuration";
	private static final String PREFS_VIBRATE_DEFAULT = "50";
	
	private Connection mConnection;
    private Handler mHandler = new Handler();
	private int contentView;
	private int currentContentView;
	private LayoutManager layoutManager;
	private int vibrationDuration;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.layoutManager = new LayoutManager(this);
        initPrefs();
        this.currentContentView = getContentView();
        setContentView(this.currentContentView);
        for (RemoteButton button : ButtonMappings.BUTTONS) {
        	View v = findViewById(button.resId);
        	if (v != null) {
        		final int[] codes = button.keyCodes;
        		v.setOnClickListener(new View.OnClickListener() {
					
					public void onClick(View v) {
						vibrate(Remote.this, vibrationDuration);
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
    
	private void initPrefs() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor edit = prefs.edit();
		boolean changes = false;
		if (!prefs.contains(PREFS_VIBRATE_KEY)) {
			changes = true;
			edit.putString(PREFS_VIBRATE_KEY, PREFS_VIBRATE_DEFAULT);
		}
		if (!prefs.contains(PREFS_SERVER_HOST_KEY)) {
			changes = true;
			edit.putString(PREFS_SERVER_HOST_KEY, PREFS_SERVER_HOST_DEFAULT);
		}
		if (!prefs.contains(PREFS_SERVER_PORT_KEY)) {
			changes = true;
			edit.putString(PREFS_SERVER_PORT_KEY, PREFS_SERVER_PORT_DEFAULT);
		}
		if (!prefs.contains(PREFS_LAYOUT_KEY)) {
			changes = true;
			edit.putString(PREFS_LAYOUT_KEY, PREFS_LAYOUT_DEFAULT);
		}
		try {
			layoutManager.getLayoutResource(prefs.getString(PREFS_LAYOUT_KEY, ""));
		} catch (NoSuchElementException e) {
			changes = true;
			edit.putString(PREFS_LAYOUT_KEY, PREFS_LAYOUT_DEFAULT);
		}
		if (changes) {
			edit.commit();
		}
	}
	
	private int getContentView() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String layoutUri = prefs.getString(PREFS_LAYOUT_KEY, PREFS_LAYOUT_DEFAULT);
		return layoutManager.getLayoutResource(layoutUri);
	}
	
	private void restartActivity() {
		Intent intent = getIntent();
		finish();
		startActivity(intent);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String host = prefs.getString(PREFS_SERVER_HOST_KEY, "");
		this.contentView = getContentView();
		if (this.contentView != this.currentContentView) {
			restartActivity();
			return;
		}
		int port = Integer.parseInt(PREFS_SERVER_PORT_DEFAULT);
		try {
			port = Integer.parseInt(prefs.getString(PREFS_SERVER_PORT_KEY, PREFS_SERVER_PORT_DEFAULT));
		} catch (NumberFormatException e) { }
		this.vibrationDuration = Integer.parseInt(PREFS_VIBRATE_DEFAULT);
		try {
			this.vibrationDuration = Integer.parseInt(prefs.getString(PREFS_VIBRATE_KEY, PREFS_VIBRATE_DEFAULT));
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
    	if (millis == 0) return;
    	Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    	v.vibrate(millis);
    }
}