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
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import de.quist.app.errorreporter.ReportingActivity;
import de.quist.app.samyGoRemote.upnp.Discovery;

public class Remote extends ReportingActivity {

	private static final String PREFS_ABOUT_DIALOG_SHOWN_KEY = "aboutDialogShown";
	private static final boolean PREFS_ABOUT_DIALOG_SHOWN_DEFAULT = false;
	public static final String PREFS_LAYOUT_KEY = "layout";
	public static final String PREFS_LAYOUT_DEFAULT = "layout:de.quist.app.samyGoRemote.bn59_00861a";
	public static final String PREFS_SERVER_HOST_KEY = "serverHost";
	public static final String PREFS_SERVER_HOST_DEFAULT = "Please change the IP address in settings";
	public static final String PREFS_SERVER_PORT_KEY = "serverPort";
	public static final String PREFS_SERVER_PORT_DEFAULT = "2345";
	public static final String PREFS_VIBRATE_KEY = "vibrationDuration";
	public static final int PREFS_VIBRATE_DEFAULT = 50;
	public static final String PREFS_SENDER_FACTORY_KEY = "keyCodeSenderFactory";
	public static final String PREFS_SENDER_FACTORY_DEFAULT = CSeriesKeyCodeSenderFactory.class.getCanonicalName();

	private Sender mSender;
	private Handler mHandler = new Handler();
	private int contentView;
	private int currentContentView;
	private LayoutManager layoutManager;
	private int vibrationDuration;
	private boolean initialized;

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
						Integer[] codes1 = intArrayToIntegerArray(codes);
						SendKeysTask sendCodesTask = new SendKeysTask(); 
						sendCodesTask.execute(codes1);
					}
				});
			}
		}
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		if (!prefs.getBoolean(PREFS_ABOUT_DIALOG_SHOWN_KEY, PREFS_ABOUT_DIALOG_SHOWN_DEFAULT)) {
			SharedPreferences.Editor edit = prefs.edit();
			edit.putBoolean(PREFS_ABOUT_DIALOG_SHOWN_KEY, true);
			edit.commit();
			Intent aboutActivity = new Intent(this, AboutActivity.class);
			startActivity(aboutActivity);
		}
	}

	private void initPrefs() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		final SharedPreferences.Editor edit = prefs.edit();
		boolean changes = false;
		if (!prefs.contains(PREFS_VIBRATE_KEY)) {
			changes = true;
			edit.putInt(PREFS_VIBRATE_KEY, PREFS_VIBRATE_DEFAULT);
		}
		if (!prefs.contains(PREFS_SERVER_HOST_KEY)) {
			changes = true;
			edit.putString(PREFS_SERVER_HOST_KEY, PREFS_SERVER_HOST_DEFAULT);
		}
		if (!prefs.contains(PREFS_SERVER_PORT_KEY) || prefs.getString(PREFS_SERVER_PORT_KEY, "").trim().equals("")) {
			changes = true;
			edit.putString(PREFS_SERVER_PORT_KEY, PREFS_SERVER_PORT_DEFAULT);
		}
		if (!prefs.contains(PREFS_LAYOUT_KEY)) {
			changes = true;
			edit.putString(PREFS_LAYOUT_KEY, PREFS_LAYOUT_DEFAULT);
		}
		if (!prefs.contains(PREFS_ABOUT_DIALOG_SHOWN_KEY)) {
			changes = true;
			edit.putBoolean(PREFS_ABOUT_DIALOG_SHOWN_KEY, PREFS_ABOUT_DIALOG_SHOWN_DEFAULT);
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
		if (prefs.getString(PREFS_SERVER_HOST_KEY, PREFS_SERVER_HOST_DEFAULT).equals(PREFS_SERVER_HOST_DEFAULT)) {
			Discovery discovery = new Discovery() {
				protected void onPostExecute(InetAddress addr) {
					if (addr != null) {
						edit.putString(PREFS_SERVER_HOST_KEY, addr.getHostAddress());
						if (this.isCSeries) {
							edit.putString(PREFS_SENDER_FACTORY_KEY, CSeriesKeyCodeSenderFactory.class.getCanonicalName());
						}
						edit.commit();
						new AsyncTask<Void, Void, Boolean>() {

							@Override
							protected Boolean doInBackground(Void... params) {
								return initializeConnection();
							}

							protected void onPostExecute(Boolean result) {
								Remote.this.initialized = result;
							};

						}.execute();
					}
				};
			};
			discovery.execute();
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

		// Check if layout changed and restart if necessary
		this.contentView = getContentView();
		if (this.contentView != this.currentContentView) {
			restartActivity();
			return;
		}
		this.contentView = getContentView();

		// Set vibration duration
		this.vibrationDuration = PREFS_VIBRATE_DEFAULT;
		try {
			this.vibrationDuration = prefs.getInt(PREFS_VIBRATE_KEY, PREFS_VIBRATE_DEFAULT);
		} catch (ClassCastException e) {
			try {
				this.vibrationDuration = Integer.parseInt(prefs.getString(PREFS_VIBRATE_KEY, Integer.toString(PREFS_VIBRATE_DEFAULT)));
			} catch (NumberFormatException e1) { }
		}
		
		// Initialize the connection asynchronous
		new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(Void... params) {
				return initializeConnection();
			}

			protected void onPostExecute(Boolean result) {
				Remote.this.initialized = result;
			};

		}.execute();

	}

	private boolean initializeConnection() {
		if (mSender != null) mSender.uninitialize();
		mSender = null;
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		mSender = SenderFactory.createKeyCodeSender(this, prefs, getExceptionReporter());
		try {
			mSender.initialize();
		} catch (final IOException e) {
			mHandler.post(new Runnable() {
				
				public void run() {
					Toast.makeText(Remote.this, e.getMessage(), Toast.LENGTH_LONG).show();
				}
			});
			return false;
		}
		return true;
	}

	private class SendKeysTask extends AsyncTask<Integer, Void, Void> {
		@Override
		protected Void doInBackground(Integer... codes) {
			try {
				if (mSender == null) return null;
				if (!Remote.this.initialized) Remote.this.mSender.initialize();
				if (mSender instanceof KeyCodeSender) {
					KeyCodeSender keyCodeSender = (KeyCodeSender) mSender;
					keyCodeSender.sendCode(integerArrayToIntArray(codes));
					if (codes[codes.length-1] == ButtonMappings.BTN_POWER_OFF) {
						finish();
					}
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

	private static int[] integerArrayToIntArray(Integer[] codes) {
		int[] result = new int[codes.length];
		for (int i=0; i<codes.length; i++) {
			result[i] = codes[i];
		}
		return result;
	}

	private static Integer[] intArrayToIntegerArray(int[] codes) {
		Integer[] result = new Integer[codes.length];
		for (int i=0; i < codes.length; i++) {
			result[i] = codes[i];
		}
		return result;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem textInputItem = menu.findItem(R.id.menu_item_textinput);
		if (mSender instanceof TextSender) {
			textInputItem.setVisible(true);
		} else {
			textInputItem.setVisible(false);
		}
		return super.onPrepareOptionsMenu(menu);
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
		case R.id.menu_item_textinput:
			showInputDialog();
		}
		return super.onMenuItemSelected(featureId, item);
	}

	private void showInputDialog() {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.text_input_title);
		EditText edit = new EditText(this);
		edit.addTextChangedListener(new TextWatcher() {
			
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			public void afterTextChanged(Editable s) {
				TextSender sender = (TextSender) mSender;
				try {
					sender.sendText(s.toString());
				} catch (IOException e) {
					
				} catch (InterruptedException e) {
					
				}
			}
		});
		builder.setView(edit);
		AlertDialog d = builder.create();
		d.setOnDismissListener(new OnDismissListener() {
			
			public void onDismiss(DialogInterface dialog) {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			}
		});
		d.show();
	}

	public static void vibrate(Context context, long millis) {
		if (millis == 0) return;
		Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		if (v != null) { 
			v.vibrate(millis);
		}
	}
}