package de.quist.app.samyGoRemote;

import java.io.IOException;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
    	}
    	return super.onMenuItemSelected(featureId, item);
    }
}