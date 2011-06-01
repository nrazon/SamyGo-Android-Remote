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

import static de.quist.app.samyGoRemote.ButtonMappings.*;
import static de.quist.samy.remocon.Key.*;

import java.util.HashMap;

import de.quist.samy.remocon.Key;

public abstract class CSeriesButtons {

	public static final HashMap<Integer, Key> MAPPINGS = new HashMap<Integer, Key>();
	
	static {
		MAPPINGS.put(BTN_POWER_OFF, KEY_POWEROFF);
		MAPPINGS.put(BTN_TV, KEY_TV);
		MAPPINGS.put(BTN_1, KEY_1);
		MAPPINGS.put(BTN_2, KEY_2);
		MAPPINGS.put(BTN_3, KEY_3);
		MAPPINGS.put(BTN_4, KEY_4);
		MAPPINGS.put(BTN_5, KEY_5);
		MAPPINGS.put(BTN_6, KEY_6);
		MAPPINGS.put(BTN_7, KEY_7);
		MAPPINGS.put(BTN_8, KEY_8);
		MAPPINGS.put(BTN_9, KEY_9);
		MAPPINGS.put(BTN_0, KEY_0);
		MAPPINGS.put(BTN_FAVOURITE_CHANNEL, KEY_FAVCH);
		MAPPINGS.put(BTN_PREVIOUS_CHANNEL, KEY_PRECH);
		MAPPINGS.put(BTN_VOLUME_UP, KEY_VOLUP);
		MAPPINGS.put(BTN_VOLUME_DOWN, KEY_VOLDOWN);
		MAPPINGS.put(BTN_CHANNEL_UP, KEY_CHUP);
		MAPPINGS.put(BTN_CHANNEL_DOWN, KEY_CHDOWN);
		MAPPINGS.put(BTN_MUTE, KEY_MUTE);
		MAPPINGS.put(BTN_SOURCE, KEY_SOURCE);
		MAPPINGS.put(BTN_INFO, KEY_INFO);
		MAPPINGS.put(BTN_TOOLS, KEY_TOOLS);
		MAPPINGS.put(BTN_GUIDE, KEY_GUIDE);
		MAPPINGS.put(BTN_RETURN, KEY_RETURN);
		MAPPINGS.put(BTN_MENU, KEY_MENU);
		MAPPINGS.put(BTN_ENTER, KEY_ENTER);
		MAPPINGS.put(BTN_UP, KEY_UP);
		MAPPINGS.put(BTN_DOWN, KEY_DOWN);
		MAPPINGS.put(BTN_LEFT, KEY_LEFT);
		MAPPINGS.put(BTN_RIGHT, KEY_RIGHT);
		MAPPINGS.put(BTN_INTERNET, KEY_RSS);
		MAPPINGS.put(BTN_EXIT, KEY_EXIT);
		MAPPINGS.put(BTN_RED, KEY_RED);
		MAPPINGS.put(BTN_GREEN, KEY_GREEN);
		MAPPINGS.put(BTN_YELLOW, KEY_YELLOW);
		MAPPINGS.put(BTN_BLUE, KEY_CYAN);
		MAPPINGS.put(BTN_TELETEXT, KEY_TTX_MIX);
		MAPPINGS.put(BTN_MEDIA, KEY_W_LINK);
		MAPPINGS.put(BTN_CONTENT, KEY_CONTENTS);
		MAPPINGS.put(BTN_CHANNEL_LIST, KEY_CH_LIST);
		MAPPINGS.put(BTN_AD, KEY_AD);
		MAPPINGS.put(BTN_SUBTITLE, KEY_SUB_TITLE);
		MAPPINGS.put(BTN_FORWARD, KEY_FF);
		MAPPINGS.put(BTN_PAUSE, KEY_PAUSE);
		MAPPINGS.put(BTN_BACKWARD, KEY_REWIND);
		MAPPINGS.put(BTN_RECORD, KEY_REC);
		MAPPINGS.put(BTN_PLAY, KEY_PLAY);
		MAPPINGS.put(BTN_STOP, KEY_STOP);
		MAPPINGS.put(BTN_SLEEP, KEY_SLEEP);
		MAPPINGS.put(BTN_PICTURE_IN_PICTURE, KEY_PIP_ONOFF);
		MAPPINGS.put(BTN_PSIZE, KEY_PICTURE_SIZE);
		MAPPINGS.put(BTN_ENERGY, KEY_ESAVING);
		MAPPINGS.put(BTN_SRS, KEY_SRS);
		MAPPINGS.put(BTN_PMODE, KEY_PMODE);
		MAPPINGS.put(BTN_P_DYNAMIC, KEY_DYNAMIC);
		MAPPINGS.put(BTN_P_STANDARD, KEY_STANDARD);
		MAPPINGS.put(BTN_P_MOVIE1, KEY_MOVIE1);
		//MAPPINGS.put(BTN_P_MOVIE2, ); // KEY_CUSTOM ???
		//MAPPINGS.put(BTN_P_USER1, );
		//MAPPINGS.put(BTN_P_USER2, );
		//MAPPINGS.put(BTN_P_USER3, );
		MAPPINGS.put(BTN_ASPECT_43, KEY_4_3);
		MAPPINGS.put(BTN_ASPECT_169, KEY_16_9);
		//MAPPINGS.put(BTN_S_SCART1, ); // KEY_EXT1 ???
		//MAPPINGS.put(BTN_S_SCART2, ); // KEY_EXT2 ???
		//MAPPINGS.put(BTN_S_MODULE, );
		MAPPINGS.put(BTN_S_AV, KEY_AV1);
		//MAPPINGS.put(BTN_S_VGA, );
		MAPPINGS.put(BTN_S_HDMI1, KEY_HDMI1);
		MAPPINGS.put(BTN_S_HDMI2, KEY_HDMI2);
		MAPPINGS.put(BTN_S_HDMI3_DVI, KEY_HDMI3);
		MAPPINGS.put(BTN_S_HDMI4, KEY_HDMI4);
	}
	
}
