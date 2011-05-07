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
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class BSeriesSender implements KeyCodeSender {

	private String mHost;
	private int mPort;

	public BSeriesSender(String host, int port) {
		this.mHost = host;
		this.mPort = port;
	}

	public void initialize() throws IOException {
		// Nothing to do, as we setup the connection when a key has to be sent
		Socket s =  new Socket(mHost, mPort);
		OutputStream out = s.getOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(out);
		writer.write(Integer.toString(237));
		writer.write('\n');
		writer.flush();
		s.close();
	}

	public void sendCode(int... codes) throws UnknownHostException, IOException, InterruptedException {
		Socket s = new Socket(mHost, mPort);
		boolean first = true;
		for (int code : codes) {
			if (!first) Thread.sleep(300);
			first = false;
			OutputStream out = s.getOutputStream();
			OutputStreamWriter writer = new OutputStreamWriter(out);
			writer.write(Integer.toString(code));
			writer.write('\n');
			writer.flush();
			s.close();
		}
	}

	public void uninitialize() {
		// Nothing to do, as we setup the connection when a key has to be sent
	}

}
