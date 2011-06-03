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
import java.util.concurrent.TimeoutException;

import android.util.Log;
import de.quist.app.errorreporter.ExceptionReporter;
import de.quist.samy.remocon.ConnectionDeniedException;
import de.quist.samy.remocon.Key;
import de.quist.samy.remocon.RemoteSession;

public class CSeriesSender implements KeyCodeSender, TextSender {

	private static final String TAG = CSeriesSender.class.getSimpleName();
	
	private static final String NAME = "SamyGo Remote";
	private String mHost;
	private String macAddress;
	private RemoteSession session;

	private ExceptionReporter reporter;


	public CSeriesSender(String host, String mac, ExceptionReporter reporter) {
		this.mHost = host;
		this.macAddress = mac;
		this.reporter = reporter;
	}
	
	public void initialize() throws IOException {
		try {
			Log.v(TAG, "Creating session");
			session = RemoteSession.create(NAME, macAddress, mHost, 55000, new RemoconLogWrapper(reporter, RemoteSession.REPORT_TAG));
			Log.v(TAG, "Successfully created session");
		} catch (ConnectionDeniedException e) {
			throw new IOException("Connection denied");
		} catch (TimeoutException e) {
			throw new IOException("Timeout");
		}
	}
	
	public void sendCode(int... codes) throws UnknownHostException, IOException, InterruptedException {
		if (session == null) initialize();
		for (int code : codes) {
			Key key = CSeriesButtons.MAPPINGS.get(code);
			if (key != null) {
				session.sendKey(key);
			}
		}
	}
	
	public void sendText(String text) throws UnknownHostException, IOException, InterruptedException {
		Log.v(TAG, "Sending text " + text);
		if (session == null) initialize();
		if (session != null) session.sendText(text);
	}
	
	public void uninitialize() {
		Log.v(TAG, "Uninitializing C-Series Sender...");
		if (session != null) {
			session.destroy();
			Log.v(TAG, "...Uninitialized C-Series Sender");
		} else {
			Log.v(TAG, "...Nothing to uninitialize, session is null");
		}
	}

}
