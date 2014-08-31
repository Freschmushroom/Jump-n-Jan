package at.jumpandjan.entity;

import at.freschmushroom.Out;
import at.jumpandjan.Body;
import at.jumpandjan.level.Level;

/**
 * 
 * Class that shoots at the Player
 * 
 * @author Felix
 *
 */
public class EntityQuestion extends Entity
{
	/**
	 * The destination x coordinate
	 */
	private double destX;
	/**
	 * The destination y coordinate
	 */
	private double destY;
	/**
	 * The vector for the x axis
	 */
	private double vecX;
	/**
	 * The vector for the y axis
	 */
	private double vecY;

	/**
	 * Constructs a new Question aiming directly at the Player
	 * 
	 * @param x
	 *            the x coordinate of the top left corner
	 * @param y
	 *            the y coordinate of the top left corner
	 * @param level
	 *            the level the entity should spawn in
	 */
	public EntityQuestion(double x, double y, Level level)
	{
		super(x, y, 16, 16, level);
		isGravityApplied = false;
		destX = level.getPlayer().bounds.x;
		destY = level.getPlayer().bounds.y;
		vecX = (x - destX) / 20;
		vecY = (y - destY) / 20;

	}

	@Override
	public void update()
	{
		super.update();
		bounds.x -= vecX;
		bounds.y -= vecY;
	}

	@Override
	public void collide(Body withObject)
	{
		if (withObject instanceof EntityPlayer)
		{
			level.getPlayer().hurt(10);
			this.kill(this);
		}
	}

	@Override
	public void render()
	{
		super.render("/entity_quest.png", bounds.width, bounds.height, bounds.x, bounds.y, 16, 16, false);
	}

	static
	{
		Out.inf(EntityQuestion.class, "08.12.12", "Felix", null);
	}

}
