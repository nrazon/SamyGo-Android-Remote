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
package de.quist.samy.remocon;

public interface Loggable {

	void d(String tag, String message);
	
	void d(String tag, String message, Throwable t);
	
	void e(String tag, String message);
	
	void e(String tag, String message, Throwable t);
	
	void i(String tag, String message);
	
	void i(String tag, String message, Throwable t);
	
	void v(String tag, String message);
	
	void v(String tag, String message, Throwable t);
	
	void w(String tag, String message);
	
	void w(String tag, String message, Throwable t);
	
}
