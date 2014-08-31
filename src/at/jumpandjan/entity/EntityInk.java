package at.jumpandjan.entity;

import at.freschmushroom.Out;
import at.jumpandjan.Body;
import at.jumpandjan.level.Level;

/**
 * Fabulous Haarfarbe
 * 
 * @author Felix
 * 
 */
public class EntityInk extends Entity
{

	public EntityInk(double x, double y, Level level)
	{
		super(x, y, 16, 16, level);
		isGravityApplied = false;
	}

	@Override
	public void collide(Body withObject)
	{
		if (withObject instanceof EntityPlayer)
		{
			level.getPlayer().addPoint();
			this.kill(level.getPlayer());
		}
	}

	@Override
	public void render()
	{
		super.render("/Janny_Farbe.png", bounds.width, bounds.height, bounds.x, bounds.y, 16, 16, state);
	}

	@Override
	public boolean checkCollisionWith(Body withObject)
	{
		return super.checkCollisionWith(withObject) && (withObject instanceof EntityPlayer || withObject instanceof EntityJannyBunny);
	}

	static
	{
		Out.inf(EntityInk.class, "22.10.12", "Felix", null);
	}
}
