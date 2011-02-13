package de.quist.app.samyGoRemote;

import android.content.SharedPreferences;

public abstract class KeyCodeSenderFactory {

	public static final KeyCodeSender createKeyCodeSender(SharedPreferences prefs) {
		String factoryClassString = prefs.getString(Remote.PREFS_KEY_CODE_SENDER_FACTORY_KEY, Remote.PREFS_KEY_CODE_SENDER_FACTORY_DEFAULT);
		try {
			@SuppressWarnings("unchecked")
			Class<KeyCodeSenderFactory> factoryClass = (Class<KeyCodeSenderFactory>) Class.forName(factoryClassString);
			KeyCodeSenderFactory factory = factoryClass.newInstance();
			return factory.create(prefs);
		} catch (ClassNotFoundException e1) {
			return null;
		} catch (IllegalAccessException e) {
			return null;
		} catch (InstantiationException e) {
			return null;
		}
	}

	protected abstract KeyCodeSender create(SharedPreferences prefs);

}
