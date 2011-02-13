package de.quist.app.samyGoRemote;

import android.content.SharedPreferences;

public class BSeriesKeyCodeSenderFactory extends KeyCodeSenderFactory {

	@Override
	protected KeyCodeSender create(SharedPreferences prefs) {
		String host = prefs.getString(Remote.PREFS_SERVER_HOST_KEY, "");
		int port = Integer.parseInt(Remote.PREFS_SERVER_PORT_DEFAULT);
		try {
			port = Integer.parseInt(prefs.getString(Remote.PREFS_SERVER_PORT_KEY, Remote.PREFS_SERVER_PORT_DEFAULT));
		} catch (NumberFormatException e) { }

		KeyCodeSender keyCodeSender = new BSeriesSender(host, port);
		return keyCodeSender;
	}

}
