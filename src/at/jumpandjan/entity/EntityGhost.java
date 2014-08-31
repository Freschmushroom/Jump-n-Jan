package at.jumpandjan.entity;

import at.freschmushroom.Out;
import at.jumpandjan.level.Level;

/**
 * Ghost Entity for Hildegard
 * 
 * @author Felix
 *
 */
public class EntityGhost extends Entity
{
	/**
	 * Constructs a new Ghost Entity with the default width and height
	 * 
	 * @param x
	 *            the x coordinate of the top left corner
	 * @param y
	 *            the y coordinate of the top left corner
	 * @param level
	 *            the level the ghost should spawn in
	 */
	public EntityGhost(double x, double y, Level level)
	{
		super(x, y, 32, 64, level);
		isGravityApplied = false;
		isInfluencedByCollision = false;
	}

	@Override
	public void update()
	{
		super.update();
		motion.y = -5;
		if (bounds.y < -32)
		{
			this.kill(this);
		}
	}

	@Override
	public void render()
	{
		super.render("/opp_hildegard_ghost.png", bounds.width, bounds.height, bounds.x, bounds.y, 32, 64, true);
	}

	@Override
	public boolean hasCollision()
	{
		return false;
	}

	static
	{
		Out.inf(EntityGhost.class, "01/06/13", "Felix", null);
	}
}
