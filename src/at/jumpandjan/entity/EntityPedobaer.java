package at.jumpandjan.entity;

import at.freschmushroom.Out;
import at.jumpandjan.Body;
import at.jumpandjan.level.Level;

/**
 * An Entity that represents a Pedo baer, which follows the Player at the edge
 * of the screen, and suddenly attacks him
 * 
 * @author Felix
 *
 */
public class EntityPedobaer extends Entity
{
	/**
	 * The cooldown for the Attack
	 */
	private int cdAttack = 10 * 60;
	/**
	 * If the pedobaer is in attack state
	 */
	private boolean attack = false;
	/**
	 * The old x coordinate of the pedobaer
	 */
	private double oldX = 0;
	/**
	 * The old y coordinate of the pedobaer
	 */
	private double oldY = 0;
	/**
	 * The amount of attack ticks already done
	 */
	private double vecC = 0;
	/**
	 * The Vector on the x axis
	 */
	private double vecX = 0;
	/**
	 * The Vector on the y axis
	 */
	private double vecY = 0;
	/**
	 * The amount of ticks an attack takes
	 */
	private static final int countOfTicks = 40;

	/**
	 * Constructs a new Pedobaer at the given Position using the default width
	 * and height
	 * 
	 * @param x
	 *            the x coordinate of the top left corner
	 * @param y
	 *            the y coordinate of the top left corner
	 * @param level
	 *            the level the pedobaer wants to spawn in
	 */
	public EntityPedobaer(double x, double y, Level level)
	{
		super(x, y, 32, 64, level);
		isGravityApplied = true;
	}

	@Override
	public void update()
	{
		super.update();
		motion.x = 0;
		if (Math.abs(level.getPlayer().getPivotX() - this.getPivotX()) < 640)
		{
			if (Math.abs(level.getPlayer().getPivotX() - this.getPivotX()) > 290)
			{
				motion.x = (float) ((level.getPlayer().getPivotX() - this.getPivotX()) / Math.abs(level.getPlayer().getPivotX() - this.getPivotX()) * 1.5f);
			}
			if (cdAttack <= 0)
			{
				attack = true;
				isGravityApplied = false;
				cdAttack = 60 * 5;
				oldX = this.bounds.x;
				oldY = this.bounds.y;
				vecX = (oldX - level.getPlayer().bounds.x) / countOfTicks;
				vecY = (oldY - level.getPlayer().bounds.y) / countOfTicks;
				vecC = 0;
			}
			if (attack && vecC <= countOfTicks)
			{
				this.bounds.x -= vecX;
				this.bounds.y -= vecY;
				vecC++;
			}
			else if (vecC > countOfTicks)
			{
				attack = false;
				isGravityApplied = true;
			}
			cdAttack--;
		}
	}

	@Override
	public void collide(Body withObject)
	{
		if (withObject instanceof EntityPlayer && attack)
		{
			level.getPlayer().hurt(10);
		}
	}

	@Override
	public void render()
	{
		super.renderHealthbar();
		super.render("/Opp_Pedobaer.png", bounds.width, bounds.height, bounds.x, bounds.y, 32, 64, state);
	}

	static
	{
		Out.inf(EntityPedobaer.class, "23.10.12", "Felix", null);
	}
}
