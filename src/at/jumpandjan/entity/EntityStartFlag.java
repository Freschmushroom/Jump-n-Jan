package at.jumpandjan.entity;

import at.freschmushroom.Out;
import at.jumpandjan.level.Level;

/**
 * The start flag
 * 
 * @author Felix
 *
 */
public class EntityStartFlag extends EntityFlag
{

	public EntityStartFlag(double x, double y, Level level)
	{
		super(x, y, "/Start_Flag.png", level);
	}

	public EntityStartFlag(double x, double y, double width, double height, Level level)
	{
		super(x, y, width, height, "/Start_Flag.png", level);
	}

	@Override
	public void update()
	{
		super.update();
	}

	@Override
	public boolean hasCollision()
	{
		return false;
	}

	static
	{
		Out.inf(EntityStartFlag.class, "23.10.12", "Felix", null);
	}
}
