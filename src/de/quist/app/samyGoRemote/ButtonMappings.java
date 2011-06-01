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

import java.util.ArrayList;

public class ButtonMappings {

	public static final int BTN_POWER_OFF = 2;
	public static final int BTN_TV = 27;
	public static final int BTN_1 = 4;
	public static final int BTN_2 = 5;
	public static final int BTN_3 = 6;
	public static final int BTN_4 = 8;
	public static final int BTN_5 = 9;
	public static final int BTN_6 = 10;
	public static final int BTN_7 = 12;
	public static final int BTN_8 = 13;
	public static final int BTN_9 = 14;
	public static final int BTN_0 = 17;
	public static final int BTN_FAVOURITE_CHANNEL = 68;
	public static final int BTN_PREVIOUS_CHANNEL = 19;
	public static final int BTN_VOLUME_UP = 7;
	public static final int BTN_VOLUME_DOWN = 11;
	public static final int BTN_CHANNEL_UP = 18;
	public static final int BTN_CHANNEL_DOWN = 16;
	public static final int BTN_MUTE = 15;
	public static final int BTN_SOURCE = 1;
	public static final int BTN_INFO = 31;
	public static final int BTN_TOOLS = 75;
	public static final int BTN_GUIDE = 79;
	public static final int BTN_RETURN = 88;
	public static final int BTN_MENU = 26;
	public static final int BTN_ENTER = 104;
	public static final int BTN_UP = 96;
	public static final int BTN_DOWN = 97;
	public static final int BTN_LEFT = 101;
	public static final int BTN_RIGHT = 98;
	public static final int BTN_INTERNET = 147;
	public static final int BTN_EXIT = 45;
	public static final int BTN_RED = 108;
	public static final int BTN_GREEN = 20;
	public static final int BTN_YELLOW = 21;
	public static final int BTN_BLUE = 22;
	public static final int BTN_TELETEXT = 44;
	public static final int BTN_MEDIA = 140;
	public static final int BTN_CONTENT = 121;
	public static final int BTN_CHANNEL_LIST = 107;
	public static final int BTN_AD = 0;
	public static final int BTN_SUBTITLE = 37;
	public static final int BTN_FORWARD = 69;
	public static final int BTN_PAUSE = 74;
	public static final int BTN_BACKWARD = 72;
	public static final int BTN_RECORD = 73;
	public static final int BTN_PLAY = 71;
	public static final int BTN_STOP = 70;
	public static final int BTN_SLEEP = 3;
	public static final int BTN_PICTURE_IN_PICTURE = 32;
	public static final int BTN_PSIZE = 62;
	public static final int BTN_ENERGY = 119;
	public static final int BTN_SRS = 110;
	public static final int BTN_PMODE = 40;
	public static final int BTN_P_DYNAMIC = 189;
	public static final int BTN_P_STANDARD = 223;
	public static final int BTN_P_MOVIE1 = 222;
	public static final int BTN_P_MOVIE2 = 221;
	public static final int BTN_P_USER1 = 220;
	public static final int BTN_P_USER2 = 219;
	public static final int BTN_P_USER3 = 218;
	public static final int BTN_ASPECT_43 = 227;
	public static final int BTN_ASPECT_169 = 228;
	public static final int BTN_S_SCART1 = 132;
	public static final int BTN_S_SCART2 = 235;
	public static final int BTN_S_MODULE = 134;
	public static final int BTN_S_AV = 236;
	public static final int BTN_S_VGA = 105;
	public static final int BTN_S_HDMI1 = 233;
	public static final int BTN_S_HDMI2 = 190;
	public static final int BTN_S_HDMI3_DVI = 194;
	public static final int BTN_S_HDMI4 = 197;

	public static final ArrayList<RemoteButton> BUTTONS = new ArrayList<RemoteButton>();

	static {
		BUTTONS.add(new RemoteButton(R.id.btn_pwr_off, BTN_POWER_OFF));
		BUTTONS.add(new RemoteButton(R.id.btn_tv, BTN_TV));
		BUTTONS.add(new RemoteButton(R.id.btn_1, BTN_1));
		BUTTONS.add(new RemoteButton(R.id.btn_2, BTN_2));
		BUTTONS.add(new RemoteButton(R.id.btn_3, BTN_3));
		BUTTONS.add(new RemoteButton(R.id.btn_4, BTN_4));
		BUTTONS.add(new RemoteButton(R.id.btn_5, BTN_5));
		BUTTONS.add(new RemoteButton(R.id.btn_6, BTN_6));
		BUTTONS.add(new RemoteButton(R.id.btn_7, BTN_7));
		BUTTONS.add(new RemoteButton(R.id.btn_8, BTN_8));
		BUTTONS.add(new RemoteButton(R.id.btn_9, BTN_9));
		BUTTONS.add(new RemoteButton(R.id.btn_0, BTN_0));
		BUTTONS.add(new RemoteButton(R.id.btn_favch, BTN_FAVOURITE_CHANNEL));
		BUTTONS.add(new RemoteButton(R.id.btn_prech, BTN_PREVIOUS_CHANNEL));
		BUTTONS.add(new RemoteButton(R.id.btn_volup, BTN_VOLUME_UP));
		BUTTONS.add(new RemoteButton(R.id.btn_voldn, BTN_VOLUME_DOWN));
		BUTTONS.add(new RemoteButton(R.id.btn_chup, BTN_CHANNEL_UP));
		BUTTONS.add(new RemoteButton(R.id.btn_chdn, BTN_CHANNEL_DOWN));
		BUTTONS.add(new RemoteButton(R.id.btn_mute, BTN_MUTE));
		BUTTONS.add(new RemoteButton(R.id.btn_source, BTN_SOURCE));
		BUTTONS.add(new RemoteButton(R.id.btn_info, BTN_INFO));
		BUTTONS.add(new RemoteButton(R.id.btn_tools, BTN_TOOLS));
		BUTTONS.add(new RemoteButton(R.id.btn_guide, BTN_GUIDE));
		BUTTONS.add(new RemoteButton(R.id.btn_return, BTN_RETURN));
		BUTTONS.add(new RemoteButton(R.id.btn_menu, BTN_MENU));
		BUTTONS.add(new RemoteButton(R.id.btn_enter, BTN_ENTER));
		BUTTONS.add(new RemoteButton(R.id.btn_enter1, BTN_ENTER));
		BUTTONS.add(new RemoteButton(R.id.btn_up, BTN_UP));
		BUTTONS.add(new RemoteButton(R.id.btn_down, BTN_DOWN));
		BUTTONS.add(new RemoteButton(R.id.btn_left, BTN_LEFT));
		BUTTONS.add(new RemoteButton(R.id.btn_right, BTN_RIGHT));
		BUTTONS.add(new RemoteButton(R.id.btn_intern, BTN_INTERNET));
		BUTTONS.add(new RemoteButton(R.id.btn_exit, BTN_EXIT));
		BUTTONS.add(new RemoteButton(R.id.btn_red, BTN_RED));
		BUTTONS.add(new RemoteButton(R.id.btn_green, BTN_GREEN));
		BUTTONS.add(new RemoteButton(R.id.btn_yellow, BTN_YELLOW));
		BUTTONS.add(new RemoteButton(R.id.btn_blue, BTN_BLUE));
		BUTTONS.add(new RemoteButton(R.id.btn_ttx_mix, BTN_TELETEXT));
		BUTTONS.add(new RemoteButton(R.id.btn_media, BTN_MEDIA));
		BUTTONS.add(new RemoteButton(R.id.btn_conten, BTN_CONTENT));
		BUTTONS.add(new RemoteButton(R.id.btn_chlist, BTN_CHANNEL_LIST));
		BUTTONS.add(new RemoteButton(R.id.btn_ad, BTN_AD));
		BUTTONS.add(new RemoteButton(R.id.btn_subt, BTN_SUBTITLE));
		BUTTONS.add(new RemoteButton(R.id.btn_fb, BTN_FORWARD));
		BUTTONS.add(new RemoteButton(R.id.btn_pause, BTN_PAUSE));
		BUTTONS.add(new RemoteButton(R.id.btn_ff, BTN_BACKWARD));
		BUTTONS.add(new RemoteButton(R.id.btn_rec, BTN_RECORD));
		BUTTONS.add(new RemoteButton(R.id.btn_play, BTN_PLAY));
		BUTTONS.add(new RemoteButton(R.id.btn_stop, BTN_STOP));
		BUTTONS.add(new RemoteButton(R.id.btn_sleep, BTN_SLEEP));
		BUTTONS.add(new RemoteButton(R.id.btn_picture_in_picture, BTN_PICTURE_IN_PICTURE));
		BUTTONS.add(new RemoteButton(R.id.btn_psize, BTN_PSIZE));
		BUTTONS.add(new RemoteButton(R.id.btn_energy, BTN_ENERGY));
		BUTTONS.add(new RemoteButton(R.id.btn_srs, BTN_SRS));
		BUTTONS.add(new RemoteButton(R.id.btn_pmode, BTN_PMODE));
		BUTTONS.add(new RemoteButton(R.id.btn_p_dynamic, BTN_P_DYNAMIC));
		BUTTONS.add(new RemoteButton(R.id.btn_p_standard, BTN_P_STANDARD));
		BUTTONS.add(new RemoteButton(R.id.btn_p_movie1, BTN_P_MOVIE1));
		BUTTONS.add(new RemoteButton(R.id.btn_p_movie2, BTN_P_MOVIE2));
		BUTTONS.add(new RemoteButton(R.id.btn_p_user1, BTN_P_USER1));
		BUTTONS.add(new RemoteButton(R.id.btn_p_user2, BTN_P_USER2));
		BUTTONS.add(new RemoteButton(R.id.btn_p_user3, BTN_P_USER3));
		BUTTONS.add(new RemoteButton(R.id.btn_aspect_43, BTN_ASPECT_43));
		BUTTONS.add(new RemoteButton(R.id.btn_aspect_169, BTN_ASPECT_169));
		BUTTONS.add(new RemoteButton(R.id.btn_s_scart1, BTN_S_SCART1));
		BUTTONS.add(new RemoteButton(R.id.btn_s_scart2, BTN_S_SCART2));
		BUTTONS.add(new RemoteButton(R.id.btn_s_module, BTN_S_MODULE));
		BUTTONS.add(new RemoteButton(R.id.btn_s_av, BTN_S_AV));
		BUTTONS.add(new RemoteButton(R.id.btn_s_vga, BTN_S_VGA));
		BUTTONS.add(new RemoteButton(R.id.btn_s_hdmi1, BTN_S_HDMI1));
		BUTTONS.add(new RemoteButton(R.id.btn_s_hdmi2, BTN_S_HDMI2));
		BUTTONS.add(new RemoteButton(R.id.btn_s_hdmi3_dvi, BTN_S_HDMI3_DVI));
		BUTTONS.add(new RemoteButton(R.id.btn_s_hdmi4, BTN_S_HDMI4));
	}

}
