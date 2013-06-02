package at.jumpandjan.gui;

import at.freschmushroom.Out;
import at.jumpandjan.Constants;
import at.jumpandjan.User;

/**
 * Saves the current level and then opens a gui
 * @author zaboing
 *
 */
public class SaveAndOpenGuiListener extends OpenGuiListener {

	public SaveAndOpenGuiListener(Gui gui) {
		super(gui);
	}
	
	@Override
	public void onPressed(CompButton source) {
		User u = new User();
		u.finishedLvl(Constants.getActualLevel(), Constants
				.getActualLevel().getPlayer().getPoints());
		u.save();
		super.onPressed(source);
	}
	
	static {
		Out.inf(SaveAndOpenGuiListener.class, "01.06.2013", "Michael", null);
	}
}
