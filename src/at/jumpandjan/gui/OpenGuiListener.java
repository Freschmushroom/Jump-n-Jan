package at.jumpandjan.gui;

import at.freschmushroom.Out;
import at.jumpandjan.JumpAndJan;

/**
 * When the button is pressed, it opens the specified gui
 * @author Michael
 *
 */
public class OpenGuiListener implements ActionListener {
	/**
	 * The Gui to open
	 */
	private Gui gui;
	/**
	 * Whether all other Guis should be closed
	 */
	private boolean closeCurrentGui;

	public OpenGuiListener(Gui gui) {
		this(gui, true);
	}

	public OpenGuiListener(Gui gui, boolean closeCurrentGui) {
		this.gui = gui;
		this.closeCurrentGui = closeCurrentGui;
	}

	@Override
	public void onClicked(CompButton source) {

	}

	@Override
	public void onReleased(CompButton source) {

	}

	@Override
	public void onPressed(CompButton source) {
		if (closeCurrentGui) {
			JumpAndJan.closeCurrentGui();
		}
		JumpAndJan.openGui(gui);
	}
	
	static {
		Out.inf(OpenGuiListener.class, "01.06.2013", "Michael", null);
	}
}
