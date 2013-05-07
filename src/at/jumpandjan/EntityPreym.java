package at.jumpandjan;

import at.freschmushroom.Out;
import at.jumpandjan.level.Level;

public class EntityPreym extends Entity {

	private boolean turned = true;
	private int cdJump;
	private int cdAttack = 300;

	public EntityPreym(double x, double y, Level level) {
		super(x, y, 64, 128, level);
	}

	@Override
	public void update() {
		super.update();
		motion.x = 0;
		if (collisions.contains(JumpAndJan.getPlayer())) {
			JumpAndJan.getPlayer().hurt(2);
		}
		turned = bounds.x > Constants.getActualLevel().getPlayer().bounds.x;
		motion.x = (float) ((JumpAndJan.getPlayer().getPivotX() - this.getPivotX())
				/ Math.abs(JumpAndJan.getPlayer().getPivotX()
						- this.getPivotX()) * 1.8f);

		if (JumpAndJan.getPlayer().bounds.y
				+ JumpAndJan.getPlayer().bounds.height < this.bounds.y
				&& onGround && JumpAndJan.getPlayer().onGround) {
			if (cdJump <= 0) {
				motion.y = -20;
				cdJump = 10;
			} else
				cdJump--;
		}
		if (cdAttack == 0
				&& Math.abs(bounds.x - level.getPlayer().bounds.x) <= 600) {
			Constants.getActualLevel().addSpawnable(
					new EntityQuestion(bounds.x, bounds.y, level));
			cdAttack = 120;
		} else if (cdAttack > 0) {
			cdAttack--;
		}
	}

	@Override
	public void render() {
		super.renderHealthbar();
		super.render("/opp_preym.png", bounds.width, bounds.height, bounds.x,
				bounds.y, 64, 128, 64, 128, turned);
	}

	static {
		Out.inf(EntityPreym.class, "08.12.12", "Felix", null);
	}
}
