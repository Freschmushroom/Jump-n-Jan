package at.jumpandjan;

import at.freschmushroom.Out;
import at.jumpandjan.level.Level;

public class EntitySpaghetti extends Entity {

	private int cdJump;
	private int cdSpawn;

	public EntitySpaghetti(double x, double y, Level level) {
		super(x, y, 128, 64, level);
	}

	@Override
	public void update() {
		super.update();
		if (Math.abs(JumpAndJan.getPlayer().getPivotX() - this.getPivotX()) < 200
				&& Math.abs(JumpAndJan.getPlayer().getPivotX()
						- this.getPivotX()) > 1) {
			motion.x = (float) (-(JumpAndJan.getPlayer().getPivotX() - this.getPivotX())
					/ Math.abs(JumpAndJan.getPlayer().getPivotX()
							- this.getPivotX()) * 2.75f);
			if (JumpAndJan.getPlayer().bounds.y
					+ JumpAndJan.getPlayer().bounds.height < this.bounds.y
					&& onGround && JumpAndJan.getPlayer().onGround) {
				if (cdJump <= 0) {
					motion.y = -25;
					cdJump = 10;
				} else
					cdJump--;

			}
			if (cdSpawn <= 0) {
				level.addSpawnable(new EntityMeatball(bounds.x, bounds.y - 32, level));
				cdSpawn = 60 * 3;
			} else
				cdSpawn--;
		}
	}
	
	public void collide(at.jumpandjan.Object withObject) {
		if (withObject instanceof EntityPlayer) {
			this.hurt(2);
		}
	}

	@Override
	public void render() {
		super.renderHealthbar();
		super.render("/Opp_Spaghetti.png", bounds.width, bounds.height, bounds.x, bounds.y, 128, 64, state);
	}

	static {
		Out.inf(EntitySpaghetti.class, "06.11.12", "Felix", null);
	}
}
