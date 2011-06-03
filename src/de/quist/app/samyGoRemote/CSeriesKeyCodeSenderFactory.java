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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import de.quist.app.errorreporter.ExceptionReporter;

public class CSeriesKeyCodeSenderFactory extends SenderFactory {

	private static final File[] MAC_ADDRESS_FILES = new File[1];
	
	static {
		MAC_ADDRESS_FILES[0] = new File("/sys/class/net/eth0/address");
	}
	
	private static final String TAG = CSeriesKeyCodeSenderFactory.class.getSimpleName();

	@Override
	protected Sender create(Context ctx, SharedPreferences prefs, ExceptionReporter reporter) {
		String host = prefs.getString(Remote.PREFS_SERVER_HOST_KEY, "");
		Log.v(TAG, "Factoring C-Series sender for host " + host);
		WifiManager wifiManager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		String mac = wifiInfo.getMacAddress();
		int i = 0;
		while (mac == null && i<MAC_ADDRESS_FILES.length) {
			mac = getNetworkAddress(MAC_ADDRESS_FILES[i]);
			i++;
		}
		if (mac == null) {
			mac = "00:50:C2:00:11:22"; // Only for devices without an wifi interface (e.g. the emulator)
			Log.v(TAG, "No wifi-interface. Set mac address to a virtual address " + mac);
		}
		Log.v(TAG, "Obtained MAC address " + mac);
		return new CSeriesSender(host, mac, reporter);
	}
	
	public String getNetworkAddress(File f) {
		try {
			if (!f.exists()) return null;
			InputStream s = new FileInputStream(f);
			Reader r = new InputStreamReader(s);
			char[] buffer = new char[1024];
			int i;
			StringBuilder sb = new StringBuilder();
			while ((i = r.read(buffer)) > -1) {
				sb.append(buffer, 0, i);
			}
			return sb.toString().trim();
		} catch (IOException e) {
			return null;
		}
	}
	
}
