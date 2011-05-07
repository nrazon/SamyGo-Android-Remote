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
