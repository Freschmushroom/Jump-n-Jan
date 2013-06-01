package at.jumpandjan.gui;

import at.jumpandjan.Constants;
import at.jumpandjan.JumpAndJan;

public class OpenGuiListener implements ActionListener {

	private Gui gui;
	private boolean closeCurrentGui;

	public OpenGuiListener(Gui gui) {
		this(gui, true);
	}

	public OpenGuiListener(Gui gui, boolean closeCurrentGui) {
		this.gui = gui;
		this.closeCurrentGui = closeCurrentGui;
	}

	public void onClicked(CompButton source) {

	}

	public void onReleased(CompButton source) {

	}

	public void onPressed(CompButton source) {
		if (closeCurrentGui) {
			JumpAndJan.closeCurrentGui();
		}
		Constants.setActualLevel(null);
		JumpAndJan.openGui(gui);
	}
}
