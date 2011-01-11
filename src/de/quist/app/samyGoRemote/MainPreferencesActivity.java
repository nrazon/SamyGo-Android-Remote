package de.quist.app.samyGoRemote;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class MainPreferencesActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.main_preferences);
		
	}
	
	
	
}
