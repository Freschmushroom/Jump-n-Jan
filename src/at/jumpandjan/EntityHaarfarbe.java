package at.jumpandjan;

import at.freschmushroom.Out;
import at.jumpandjan.level.Level;

public class EntityHaarfarbe extends Entity {

	public EntityHaarfarbe(double x, double y, Level level) {
		super(x, y, 16, 16, level);
		isGravityApplied = false;
	}

	@Override
	public void update() {
		super.update();
		if (collisions.contains(level.getPlayer())) {
			level.getPlayer().addPoint();
			this.kill(level.getPlayer());
		}
	}

	@Override
	public void render() {
		super.render("/Janny_Farbe.png", bounds.width, bounds.height, bounds.x,
				bounds.y, 16, 16, state);
	}

	static {
		Out.inf(EntityHaarfarbe.class, "22.10.12", "Felix", null);
	}
}
