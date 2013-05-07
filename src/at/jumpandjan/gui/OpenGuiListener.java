package at.jumpandjan.gui;

import at.jumpandjan.Constants;
import at.jumpandjan.JumpAndJan;

public class OpenGuiListener implements ActionListener {

	private Gui gui;
	
	public OpenGuiListener(Gui gui) {
		this.gui = gui;
	}
	
	public void onClicked(CompButton source) {

	}

	public void onReleased(CompButton source) {

	}

	public void onPressed(CompButton source) {
		JumpAndJan.closeCurrentGui();
		Constants.setActualLevel(null);
		JumpAndJan.openGui(gui);
	}
}
