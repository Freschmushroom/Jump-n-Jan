package at.jumpandjan.gui;

import at.jumpandjan.JumpAndJan;

public class CloseGuiListener implements ActionListener {

	public void onClicked(CompButton source) {
	}

	public void onReleased(CompButton source) {
	}

	public void onPressed(CompButton source) {
		JumpAndJan.closeCurrentGui();
	}
}
