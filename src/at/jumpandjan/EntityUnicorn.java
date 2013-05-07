package at.jumpandjan;

import at.freschmushroom.Out;
import at.jumpandjan.level.Level;

public class EntityUnicorn extends Entity {
	private int cdJump;
	private int cdAttack = 300;
	private int attackct;

	public EntityUnicorn(double x, double y, double width, double height,
			Level level) {
		super(x, y, width, height, level);
	}

	public void update() {
		super.update();
		motion.x = 0;
		if (collisions.contains(JumpAndJan.getPlayer())) {
			JumpAndJan.getPlayer().hurt(1);
		}
		if (cdAttack > 0) {
			if (Math.abs(level.getPlayer().getPivotX() - this.getPivotX()) < 320
					&& Math.abs(JumpAndJan.getPlayer().getPivotX()
							- this.getPivotX()) > 1) {
				motion.x = (float) ((JumpAndJan.getPlayer().getPivotX() - this
						.getPivotX())
						/ Math.abs(JumpAndJan.getPlayer().getPivotX()
								- this.getPivotX()) * 1.5f);
			}

			if (JumpAndJan.getPlayer().bounds.y
					+ JumpAndJan.getPlayer().bounds.height < this.bounds.y
					&& onGround && JumpAndJan.getPlayer().onGround) {
				if (cdJump <= 0) {
					motion.y = -20;
					cdJump = 10;
				} else
					cdJump--;
			}
			cdAttack--;
		} else {
			if (attackct <= 5) {
				isGravityApplied = false;
				isInfluencedByCollision = false;
				motion.y = -20;
			} else {
				isGravityApplied = true;
				isInfluencedByCollision = true;
				int dur = 120 - attackct;
				double vecX = (this.bounds.x - Constants.getActualLevel()
						.getPlayer().bounds.x) / (double) dur;
				double vecY = (this.bounds.y - Constants.getActualLevel()
						.getPlayer().bounds.y) / (double) dur;
				bounds.x -= vecX;
				bounds.y -= vecY;
			}
			attackct++;
			if (attackct == 120) {
				cdAttack = 300;
				attackct = 0;
			}
		}
	}

	public void render() {
		super.renderHealthbar();
		super.render("/opp_unicorn.png", bounds.width, bounds.height, bounds.x,
				bounds.y, 403, 420, 463, 420, state);
	}

	public void renderIcon() {
		render("/opp_unicorn.png", bounds.width, bounds.height, bounds.x,
				bounds.y, 128, 0, 246, 260, 463, 420, false);
	}

	public boolean hasDefaultWidth() {
		return true;
	}

	public boolean hasDefaultHeight() {
		return true;
	}

	static {
		Out.inf(EntityUnicorn.class, "04.12.11", "Michael", null);
	}
}
