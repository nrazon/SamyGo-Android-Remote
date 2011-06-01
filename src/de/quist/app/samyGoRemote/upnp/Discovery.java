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
package de.quist.app.samyGoRemote.upnp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

public class Discovery extends AsyncTask<Integer, Integer, InetAddress> {

	public final static InetAddress MULTICAST_ADDRESS;
    public final static short DEFAULT_PORT = 1900;
    private static final int DEFAULT_TIMEOUT = 10000;
    private static final String MEDIA_RENDERER_UUID = "SamsungMRDesc.xml";
    private static final String PERSONAL_MESSAGE_RECEIVER_UUID = "PersonalMessageReceiver.xml";
    private static final String REMOTE_CONTROL_RECEIVER_UUID = "RemoteControlReceiver.xml";
    private static final String REMOCON_SN = "urn:samsung.com:device:RemoteControlReceiver:1";
    


	static {
        try {
            MULTICAST_ADDRESS = InetAddress.getByName("239.255.255.250");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
	
	private final static String SEARCH_TEAMPLTE = 
        "M-SEARCH * HTTP/1.1\r\n" +
        "HOST: 239.255.255.250:1900\r\n" +
        "MAN: \"ssdp:discover\"\r\n" + 
        "USER-AGENT: " + System.getProperty("os.name") + "/" + System.getProperty("os.version") + " UPnP/1.1 SamyGo Remote\r\n" +
        "ST: %s\r\n" + 
        "MX: %d\r\n" +
        "\r\n";
	private int timeout = DEFAULT_TIMEOUT;
	protected boolean isCSeries = false;
	
	@Override
	public InetAddress doInBackground(Integer... params) {
		if (params.length > 0) {
			this.timeout  = params[0]; 
		}
		this.isCSeries = false;
		return sendSearchMessage();
	}
	
	private InetAddress sendSearchMessage() {
		try {
			MulticastSocket socket = new MulticastSocket();
			socket.setReuseAddress(true);
			socket.joinGroup(MULTICAST_ADDRESS);
			
			// First send a search message
			//String searchMessage = String.format(SEARCH_TEAMPLTE, "upnp:rootdevice", 3);
			String searchMessage = String.format(SEARCH_TEAMPLTE, REMOCON_SN, 3);
			byte[] buffer = searchMessage.getBytes("UTF-8");
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, MULTICAST_ADDRESS, DEFAULT_PORT);
			socket.send(packet);
			
			searchMessage = String.format(SEARCH_TEAMPLTE, "urn:schemas-upnp-org:device:MediaRenderer:1", 3);
			buffer = searchMessage.getBytes("UTF-8");
			packet = new DatagramPacket(buffer, buffer.length, MULTICAST_ADDRESS, DEFAULT_PORT);
			socket.send(packet);
			
			// Now listen for replies
			buffer = new byte[8192];
			packet = new DatagramPacket(buffer, buffer.length);
			long start = SystemClock.uptimeMillis();
			InetAddress addr = null;
			while (SystemClock.uptimeMillis() - start < timeout) {
				try {
					int soTimeout = (int)(timeout - (SystemClock.uptimeMillis()-start));
					if (soTimeout < 0) break;
					socket.setSoTimeout(soTimeout);
					socket.receive(packet);
					String data = new String(packet.getData(), 0, packet.getLength());
					Log.d("TESTSDASDSADA", data);
					if (data.contains(REMOTE_CONTROL_RECEIVER_UUID)) {
						
						// We have a C- or D-Series TV
						addr = packet.getAddress();
						this.isCSeries  = true;
						socket.close();
						return addr;
					} else if (data.contains(PERSONAL_MESSAGE_RECEIVER_UUID) || data.contains(MEDIA_RENDERER_UUID)) {
						// B-Series
						addr = packet.getAddress();
					}
					
					buffer = new byte[8192];
					packet = new DatagramPacket(buffer, buffer.length);
				} catch (SocketTimeoutException e) {
					continue;
				}
			}
			socket.close();
			return addr;
		} catch (UnsupportedEncodingException e) {
			// Should never happen
		} catch (IOException e) {
			
		}
		return null;
	}
	
}
