package at.jumpandjan.gui;

import at.freschmushroom.Out;
import at.jumpandjan.Constants;

/**
 * Quits the game
 * 
 * @author Michael
 *
 */
public class GameQuitListener implements ActionListener
{
	@Override
	public void onClicked(CompButton source)
	{

	}

	@Override
	public void onReleased(CompButton source)
	{

	}

	@Override
	public void onPressed(CompButton source)
	{
		Constants.setRunning(false);
	}

	static
	{
		Out.inf(GameQuitListener.class, "01.06.2013", "Michael", null);
	}
}
