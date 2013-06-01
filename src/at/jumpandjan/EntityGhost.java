package at.jumpandjan;

import at.jumpandjan.level.Level;

public class EntityGhost extends Entity {

	public EntityGhost(double x, double y, Level level) {
		super(x, y, 32, 64, level);
		isGravityApplied = false;
		isInfluencedByCollision = false;
	}

	@Override
	public void update() {
		super.update();
		motion.y = -5;
		if (bounds.y < -32) {
			this.kill(this);
		}
	}

	@Override
	public void render() {
		super.render("/opp_hildegard_ghost.png", bounds.width, bounds.height,
				bounds.x, bounds.y, 32, 64, true);
	}

	public boolean hasCollision() {
		return false;
	}
}
