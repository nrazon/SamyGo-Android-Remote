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

import android.util.Log;
import de.quist.samy.remocon.Loggable;

public class RemoconLogWrapper implements Loggable {

	public void d(String tag, String message) {
		Log.d(tag, message);
	}

	public void d(String tag, String message, Throwable t) {
		Log.d(tag, message, t);
	}

	public void e(String tag, String message) {
		Log.e(tag, message);
	}

	public void e(String tag, String message, Throwable t) {
		Log.e(tag, message, t);
	}

	public void i(String tag, String message) {
		Log.i(tag, message);
	}

	public void i(String tag, String message, Throwable t) {
		Log.i(tag, message, t);
	}

	public void v(String tag, String message) {
		Log.v(tag, message);
	}

	public void v(String tag, String message, Throwable t) {
		Log.v(tag, message, t);
	}

	public void w(String tag, String message) {
		Log.w(tag, message);
	}

	public void w(String tag, String message, Throwable t) {
		Log.w(tag, message, t);
	}

}
