package at.jumpandjan.entity;

import at.freschmushroom.Out;
import at.jumpandjan.Body;
import at.jumpandjan.Constants;
import at.jumpandjan.JumpAndJan;
import at.jumpandjan.flags.FlagBoss;
import at.jumpandjan.gui.GuiLevelChooser;
import at.jumpandjan.gui.GuiMainMenu;
import at.jumpandjan.gui.GuiUserSaveStates;
import at.jumpandjan.level.Level;

/**
 * The finish flag
 * 
 * @author Felix
 * 
 */
public class EntityFinishFlag extends EntityFlag
{
	public EntityFinishFlag(double x, double y, Level level)
	{
		super(x, y, "/Finish_Flag.png", level);
	}

	public EntityFinishFlag(double x, double y, double width, double height, Level level)
	{
		super(x, y, width, height, "/Finish_Flag.png", level);
	}

	@Override
	public void collide(Body withObject)
	{
		if (withObject instanceof EntityPlayer)
		{
			for (Body o : level.getSecond())
			{
				if (o instanceof FlagBoss)
					return;
			}
			Constants.getCURRENT_USER().finishedLvl(level, level.getPlayer().getPoints());
			Constants.getCURRENT_USER().save();
			level.game.level = null;
			JumpAndJan.closeAllGuis();
			JumpAndJan.openGui(new GuiMainMenu(level.game));
			JumpAndJan.openGui(new GuiUserSaveStates(level.game));
			JumpAndJan.openGui(new GuiLevelChooser(level.game));
		}
	}

	static
	{
		Out.inf(EntityFinishFlag.class, "22.10.12", "Felix", null);
	}
}
