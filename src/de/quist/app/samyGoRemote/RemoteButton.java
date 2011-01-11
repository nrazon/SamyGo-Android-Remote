package de.quist.app.samyGoRemote;

public class RemoteButton {

	int resId;
	int[] keyCodes;
	
	public RemoteButton(int resId, int...keyCodes) {
		this.resId = resId;
		this.keyCodes = keyCodes;
	}
}
