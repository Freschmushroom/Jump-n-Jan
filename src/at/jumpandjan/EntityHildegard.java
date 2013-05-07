package at.jumpandjan;

import at.jumpandjan.level.Level;

public class EntityHildegard extends Entity {

	private int state = 0;

	private boolean turned;

	private int cdJump;
	private int cdAttack;
	private int cdAnimation;
	private int attackct;
	private final int heartAttackIn;

	public EntityHildegard(double x, double y, Level level) {
		this(x, y, 32, 64, level);
	}

	public EntityHildegard(double x, double y, double width, double height,
			Level level) {
		super(x, y, width, height, level);
		heartAttackIn = Constants.random.nextInt(50) + 12;
	}

	@Override
	public void update() {
		super.update();
		motion.x = 0;
		if (cdAttack == 0) {
			if (collisions.contains(JumpAndJan.getPlayer())) {
				attackct++;
				cdAttack = 25;
				state = 2;
				cdAnimation = 12;
				if (attackct > heartAttackIn) {
					JumpAndJan.getPlayer().hurt(25);
					level.addSpawnable(new EntityGhost(bounds.x, bounds.y,
							level));
					this.kill(this);
				}
				return;
			}
		} else {
			cdAttack--;
		}
		turned = !(bounds.x > Constants.getActualLevel().getPlayer().bounds.x);
		double distance = Math.abs(level.getPlayer().getPivotX()
				- this.getPivotX());
		if (distance < 320) {
			if (cdAnimation > 0) {
				cdAnimation--;
			} else {
				if (state == 2) {
					state = 1;
				}
				cdAnimation = 0;
			}
			if (distance > 0) {
				motion.x = (float) ((JumpAndJan.getPlayer().getPivotX() - this
						.getPivotX())
						/ Math.abs(JumpAndJan.getPlayer().getPivotX()
								- this.getPivotX()) * 1.5f);
			}
		} else {
			state = 0;
			cdAnimation = 0;
		}

		if (JumpAndJan.getPlayer().bounds.y
				+ JumpAndJan.getPlayer().bounds.height < this.bounds.y
				&& onGround && JumpAndJan.getPlayer().onGround) {
			if (cdJump <= 0) {
				motion.y = -10;
				cdJump = 10;
			} else
				cdJump--;
		}
	}

	@Override
	public void render() {
		super.renderHealthbar();
		switch (state) {
		case 1: {
			super.render("/opp_hildegard_attack.png", bounds.width,
					bounds.height, bounds.x, bounds.y, 32, 64, turned);
			break;
		}
		case 2: {
			super.render("/opp_hildegard_after.png", bounds.width,
					bounds.height, bounds.x, bounds.y, 32, 64, turned);
			break;
		}
		default: {
			super.render("/opp_hildegard_passive.png", bounds.width,
					bounds.height, bounds.x, bounds.y, 32, 64, turned);
		}
		}
	}

	public void renderIcon() {
		double h = bounds.height * 0.97;
		double w = h / 2;
		render("/Opp_hildegard_passive.png", w, h, bounds.x
				+ (bounds.width - w) / 2, bounds.y + (bounds.height - h) / 2,
				32, 64, 32, 64, false);
	}
}
