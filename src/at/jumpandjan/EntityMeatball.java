package at.jumpandjan;

import at.freschmushroom.Out;
import at.jumpandjan.level.Level;

public class EntityMeatball extends Entity {
	private int cdJump;

	public EntityMeatball(double x, double y, Level level) {
		this(0, 0, 32, 32, level);
	}

	public EntityMeatball(double x, double y, double width, double height,
			Level level) {
		super(x, y, width, height, level);
	}

	public void update() {
		super.update();
		motion.x = 0;
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
	}

	public void collide(at.jumpandjan.Object withObject) {
		if (withObject instanceof EntityPlayer) {
			JumpAndJan.getPlayer().hurt(1);
		}
	}

	public void render() {
		super.renderHealthbar();
		super.render("/Opp_Meatball.png", bounds.width, bounds.height,
				bounds.x, bounds.y, 28, 26, 32, 32, state);
	}

	public void renderIcon() {
		render("/Opp_Meatball.png", bounds.width - 4, bounds.height - 4,
				bounds.x + 2, bounds.y + 2, 28, 26, 32, 32, false);
	}

	public double getDefaultWidth() {
		return 32;
	}

	public double getDefaultHeight() {
		return 32;
	}

	public boolean hasDefaultWidth() {
		return true;
	}

	public boolean hasDefaultHeight() {
		return true;
	}

	static {
		Out.inf(EntityMeatball.class, "22.10.11", "Felix, Michael", null);
	}
}
