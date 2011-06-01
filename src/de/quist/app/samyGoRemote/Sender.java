package de.quist.app.samyGoRemote;

import java.io.IOException;

public interface Sender {

	/**
	 * Is called before any key is being sent.
	 * 
	 * @throws IOException If the connection failed
	 */
	void initialize() throws IOException;

	/**
	 * Is after all keys has been sent. Before {@link #sendCode(int...)} is called
	 * again, {@link #initialize()} will be called.
	 */
	void uninitialize();
	
}
