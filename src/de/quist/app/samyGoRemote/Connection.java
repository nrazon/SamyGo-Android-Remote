package de.quist.app.samyGoRemote;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connection {

	private String mHost;
	private int mPort;

	public Connection(String host, int port) {
		this.mHost = host;
		this.mPort = port;
	}
	
	public void sendCode(int code) throws UnknownHostException, IOException {
		Socket s = new Socket(mHost, mPort);
		OutputStream out = s.getOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(out);
		writer.write(Integer.toString(code));
		writer.write('\n');
		writer.flush();
		s.close();
	}
	
}
