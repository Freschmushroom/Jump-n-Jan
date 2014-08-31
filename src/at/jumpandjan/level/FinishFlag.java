package at.jumpandjan.level;

import at.freschmushroom.Out;
import at.jumpandjan.Body;
import at.jumpandjan.entity.EntityFinishFlag;

/**
 * The level element representing the Finish Flag
 * 
 * @author Felix
 *
 */
public class FinishFlag extends LevelElement
{

	public FinishFlag(double posX, double posY, double width, double height)
	{
		super(posX, posY, width, height);
	}

	@Override
	public Body getElement(Level level)
	{
		return new EntityFinishFlag(getPosX(), getPosY(), getWidth(), getHeight(), level);
	}

	static
	{
		Out.inf(FinishFlag.class, "01.06.31", "Felix", null);
	}
}
