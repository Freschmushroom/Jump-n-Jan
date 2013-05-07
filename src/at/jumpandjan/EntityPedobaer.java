package at.jumpandjan;

import at.freschmushroom.Out;
import at.jumpandjan.level.Level;

public class EntityPedobaer extends Entity {
	private int cdAttack = 10 * 60;
	private int cdR = 5;
	private boolean attack = false;
	private double oldX = 0;
	private double oldY = 0;
	private double vecC = 0;
	private double vecX = 0;
	private double vecY = 0;
	private static final int countOfTicks = 40;

	public EntityPedobaer(double x, double y, Level level) {
		super(x, y, 32, 64, level);
		isGravityApplied = true;
	}

	@Override
	public void update() {
		super.update();
		if (collisions.contains(level.getPlayer()) && attack) {
			level.getPlayer().hurt(10);
		}
		motion.x = 0;
		if (Math.abs(level.getPlayer().getPivotX() - this.getPivotX()) < 640) {
			if (Math.abs(level.getPlayer().getPivotX() - this.getPivotX()) > 290) {
				motion.x = (float) ((JumpAndJan.getPlayer().getPivotX() - this
						.getPivotX())
						/ Math.abs(JumpAndJan.getPlayer().getPivotX()
								- this.getPivotX()) * 1.5f);
			}
			if (cdAttack <= 0) {
				attack = true;
				isGravityApplied = false;
				cdAttack = 60 * 5;
				oldX = this.bounds.x;
				oldY = this.bounds.y;
				vecX = (oldX - level.getPlayer().bounds.x) / countOfTicks;
				vecY = (oldX - level.getPlayer().bounds.y) / countOfTicks;
				vecC = 0;
			}
			if (attack && vecC <= countOfTicks) {
				this.bounds.x -= vecX;
				this.bounds.y -= vecY;
				vecC++;
			} else if (vecC > countOfTicks) {
				attack = false;
				isGravityApplied = true;
			}
			cdAttack--;
		}
	}

	@Override
	public void render() {
		super.renderHealthbar();
		super.render("/Opp_Pedobaer.png", bounds.width, bounds.height, bounds.x, bounds.y, 32, 64, state);
	}
	
	static {
		Out.inf(EntityPedobaer.class, "23.10.12", "Felix", null);
	}
}
