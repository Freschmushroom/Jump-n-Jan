package at.jumpandjan.gui;

import at.freschmushroom.Out;
import at.jumpandjan.JumpAndJan;

/**
 * When the button is pressed, it closes the gui of the button
 * @author Michael
 *
 */
public class CloseGuiListener implements ActionListener {

	@Override
	public void onClicked(CompButton source) {
	}

	@Override
	public void onReleased(CompButton source) {
	}

	@Override
	public void onPressed(CompButton source) {
		JumpAndJan.closeCurrentGui();
	}
	
	static {
		Out.inf(CloseGuiListener.class, "01.06.2013", "Michael", null);
	}
}
