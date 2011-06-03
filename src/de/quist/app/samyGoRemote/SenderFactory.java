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

import de.quist.app.errorreporter.ExceptionReporter;
import android.content.Context;
import android.content.SharedPreferences;

public abstract class SenderFactory {

	public static final Sender createKeyCodeSender(Context ctx, SharedPreferences prefs, ExceptionReporter reporter) {
		String factoryClassString = prefs.getString(Remote.PREFS_SENDER_FACTORY_KEY, Remote.PREFS_SENDER_FACTORY_DEFAULT);
		try {
			@SuppressWarnings("unchecked")
			Class<SenderFactory> factoryClass = (Class<SenderFactory>) Class.forName(factoryClassString);
			SenderFactory factory = factoryClass.newInstance();
			return factory.create(ctx, prefs, reporter);
		} catch (ClassNotFoundException e1) {
			return null;
		} catch (IllegalAccessException e) {
			return null;
		} catch (InstantiationException e) {
			return null;
		}
	}

	protected abstract Sender create(Context ctx, SharedPreferences prefs, ExceptionReporter reporter);

}
