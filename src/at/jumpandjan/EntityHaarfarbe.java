package at.jumpandjan;

import at.freschmushroom.Out;
import at.jumpandjan.level.Level;

/**
 * Fabulous Haarfarbe
 * 
 * @author Felix
 * 
 */
public class EntityHaarfarbe extends Entity {

	public EntityHaarfarbe(double x, double y, Level level) {
		super(x, y, 16, 16, level);
		isGravityApplied = false;
	}

	@Override
	public void collide(at.jumpandjan.Object withObject) {
		if (withObject instanceof EntityPlayer) {
			level.getPlayer().addPoint();
			this.kill(level.getPlayer());
		}
	}

	@Override
	public void render() {
		super.render("/Janny_Farbe.png", bounds.width, bounds.height, bounds.x,
				bounds.y, 16, 16, state);
	}

	@Override
	public boolean checkCollisionWith(at.jumpandjan.Object withObject) {
		return super.checkCollisionWith(withObject)
				&& withObject instanceof EntityPlayer;
	}

	static {
		Out.inf(EntityHaarfarbe.class, "22.10.12", "Felix", null);
	}
}
