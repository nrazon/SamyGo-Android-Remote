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
