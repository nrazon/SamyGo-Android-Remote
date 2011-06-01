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

public interface KeyCodeSender extends Sender {

	/**
	 * Sends the specified codes to the device.
	 * 
	 * @param codes The list of codes
	 * @throws IOException If the connection failed
	 * @throws InterruptedException If a waiting operation is being interrupted 
	 */
	void sendCode(int... codes) throws IOException, InterruptedException;

}
