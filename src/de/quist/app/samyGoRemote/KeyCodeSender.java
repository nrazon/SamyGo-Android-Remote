package de.quist.app.samyGoRemote;

import java.io.IOException;

public interface KeyCodeSender {

	/**
	 * Is called before any key is being sent.
	 * 
	 * @throws IOException If the connection failed
	 */
	void initialize() throws IOException;

	/**
	 * Sends the specified codes to the device.
	 * 
	 * @param codes The list of codes
	 * @throws IOException If the connection failed
	 * @throws InterruptedException If a waiting operation is being interrupted 
	 */
	void sendCode(int... codes) throws IOException, InterruptedException;

	/**
	 * Is after all keys has been sent. Before {@link #sendCode(int...)} is called
	 * again, {@link #initialize()} will be called.
	 */
	void uninitialize();

}
