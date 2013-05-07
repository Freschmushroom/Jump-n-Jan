package at.jumpandjan.gui;

import at.jumpandjan.Constants;
import at.jumpandjan.User;

public class SaveAndOpenGuiListener extends OpenGuiListener {

	public SaveAndOpenGuiListener(Gui gui) {
		super(gui);
	}
	
	public void onPressed(CompButton source) {
		User u = new User();
		u.finishedLvl(Constants.getActualLevel(), Constants
				.getActualLevel().getPlayer().getPoints());
		u.save();
		super.onPressed(source);
	}
}
