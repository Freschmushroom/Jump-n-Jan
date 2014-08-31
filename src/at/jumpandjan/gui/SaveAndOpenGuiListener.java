package at.jumpandjan.gui;

import at.freschmushroom.Out;
import at.jumpandjan.User;
import at.jumpandjan.level.Level;

/**
 * Saves the current level and then opens a gui
 * 
 * @author zaboing
 *
 */
public class SaveAndOpenGuiListener extends OpenGuiListener
{
	private Level level;

	public SaveAndOpenGuiListener(Gui gui, Level level)
	{
		super(gui);
		this.level = level;
	}

	@Override
	public void onPressed(CompButton source)
	{
		User u = new User();
		u.finishedLvl(level, level.getPlayer().getPoints());
		u.save();
		super.onPressed(source);
	}

	static
	{
		Out.inf(SaveAndOpenGuiListener.class, "01.06.2013", "Michael", null);
	}
}
