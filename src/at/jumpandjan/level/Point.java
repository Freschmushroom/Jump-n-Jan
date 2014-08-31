package at.jumpandjan.level;

import at.freschmushroom.Out;
import at.jumpandjan.Body;
import at.jumpandjan.entity.EntityInk;

/**
 * The building element for color
 * 
 * @author Felix
 * 
 */
public class Point extends LevelElement
{

	public Point(double x, double y)
	{
		super(x, y, 0, 0);
	}

	@Override
	public Body getElement(Level level)
	{
		return new EntityInk(getPosX(), getPosY(), level);
	}

	static
	{
		Out.inf(Point.class, "23.10.12", "Felix", null);
	}
}
