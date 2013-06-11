package at.jumpandjan;

import at.freschmushroom.Out;
import at.jumpandjan.flags.FlagBoss;
import at.jumpandjan.level.Level;
/**
 * The first boss opponent in Jump'n'Jan. It runs away from the player and spawns Meatballs on its way.
 * 
 * @author Felix
 *
 */
public class EntitySpaghetti extends Entity implements FlagBoss {
	/**
	 * The cooldown for the jump
	 */
	private int cdJump;
	/**
	 * The cooldown for the spawn
	 */
	private int cdSpawn;
	/**
	 * Constructs a new Spaghetti Monster using the default width and height
	 * @param x the x coordinate of the top left corner
	 * @param y the y coordiante of the top left corner
	 * @param level the level the entity spawns in
	 */
	public EntitySpaghetti(double x, double y, Level level) {
		this(x, y, 128, 64, level);
	}
	
	public EntitySpaghetti(double x, double y, double width, double height, Level level) {
		super(x, y, width, height, level);
		setMaxHP(6000);
		setHp(6000);
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
	@Override
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
