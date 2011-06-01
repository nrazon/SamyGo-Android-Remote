package de.quist.app.samyGoRemote;

import java.io.IOException;

public interface TextSender extends Sender {

	/**
	 * Sends the specified codes to the device.
	 * 
	 * @param codes The list of codes
	 * @throws IOException If the connection failed
	 * @throws InterruptedException If a waiting operation is being interrupted 
	 */
	void sendText(String text) throws IOException, InterruptedException;
	
}
