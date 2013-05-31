package at.jumpandjan;

import at.jumpandjan.level.Level;
import at.freschmushroom.Out;

public class EntityStartFlag extends EntityFlag {

	public EntityStartFlag(double x, double y, Level level) {
		super(x, y, "/Start_Flag.png", level);
	}
	
	public EntityStartFlag(double x, double y, double width, double height, Level level) {
		super(x, y, width, height, "/Start_Flag.png", level);
	}

	@Override
	public void update() {
		super.update();
	}
	
	public boolean hasCollision() {
		return false;
	}

	static {
		Out.inf(EntityStartFlag.class, "23.10.12", "Felix", null);
	}
}
