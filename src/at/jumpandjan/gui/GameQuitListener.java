package at.jumpandjan.gui;

import at.jumpandjan.Constants;

public class GameQuitListener implements ActionListener {
	public void onClicked(CompButton source) {

	}
	
	public void onReleased(CompButton source) {
		
	}

	public void onPressed(CompButton source) {
		Constants.setRunning(false);
	}

}
