package at.jumpandjan.gui;

import utils.TrueTypeFont;
import at.jumpandjan.Constants;

public class GuiOptions extends Gui {
	private CompButton back;
	private CompPanel panel;

	public GuiOptions() {
		back = new CompButton(0, 0, 150, 40, "<-- Back");
		back.setCenter(100, Constants.getCameraHeight() - 75);
		back.addButtonListener(new CloseGuiListener());
		panel = new CompPanel(0, 0, 640, 480);
		panel.add(back);
		CompLabel optionsLabel = new CompLabel(10, 10, 630, 20, "Options")
				.setTextAlignment(TrueTypeFont.ALIGN_CENTER);
		optionsLabel.color = "000000";
		panel.add(optionsLabel);
		components.add(panel);
	}

	public void resized() {

	}
}
