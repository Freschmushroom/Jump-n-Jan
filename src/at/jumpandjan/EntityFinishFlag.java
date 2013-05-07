package at.jumpandjan;

import at.freschmushroom.Out;
import at.jumpandjan.gui.GuiLevelChooser;
import at.jumpandjan.gui.GuiMenu;
import at.jumpandjan.gui.GuiUserSaveStates;
import at.jumpandjan.level.Level;

public class EntityFinishFlag extends EntityFlag {
	public EntityFinishFlag(double x, double y, Level level) {
		super(x, y, "/Finish_Flag.png", level);
	}
	
	public EntityFinishFlag(double x, double y, double width, double height, Level level) {
		super(x, y, width, height, "/Finish_Flag.png", level);
	}

	@Override
	public void update() {
		super.update();
		if(collisions.contains(JumpAndJan.getPlayer())) {
			User u = new User("zaboing");
			Constants.setCURRENT_USER(u);
			u.save();
			u.finishedLvl(level, JumpAndJan.getPlayer().getPoints());
			Constants.setActualLevel(null);
			JumpAndJan.closeAllGuis();
			JumpAndJan.openGui(new GuiMenu());
			JumpAndJan.openGui(new GuiUserSaveStates());
			JumpAndJan.openGui(new GuiLevelChooser());
			throw new InterruptUpdateException();
		}
	}
	
	static {
		Out.inf(EntityFinishFlag.class, "22.10.12", "Felix", null);
	}
}
