package de.quist.app.samyGoRemote;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle(R.string.about_title);
		this.setContentView(R.layout.about);
		TextView aboutText = (TextView) findViewById(R.id.about_text);
		aboutText.setMovementMethod(LinkMovementMethod.getInstance());
		Button okButton = (Button) findViewById(android.R.id.button1);
		okButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				finish();
			}
		});
	}

}
