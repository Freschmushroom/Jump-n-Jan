package at.jumpandjan.entity;

import at.freschmushroom.Out;
import at.jumpandjan.Body;
import at.jumpandjan.Constants;
import at.jumpandjan.level.Level;

/**
 * Hildegard is an old lady that just wants to cross the road, but then there
 * suddenly a wild JannyBunnyWarrior appaers and she needs to defaet him. And if
 * it weren't for her weak heart she'd probably succeed, but unfortunately she
 * gets an heart attack and dies.
 * 
 * @author Felix
 *
 */
public class EntityHildegard extends Entity
{
	/**
	 * The state in which Hildegard is at the moment
	 */
	private int state = 0;
	/**
	 * If she is looking left or right
	 */
	private boolean turned;
	/**
	 * The cooldown for the jump
	 */
	private int cdJump;
	/**
	 * The cooldown for the Attack
	 */
	private int cdAttack;
	/**
	 * The cooldown for the Animation
	 */
	private int cdAnimation;
	/**
	 * The number of attacks
	 */
	private int attackct;
	/**
	 * The number of attacks she manages before she dies
	 */
	private final int heartAttackIn;

	/**
	 * Constructs a new Hildegard with the default values for width and height
	 * 
	 * @param x
	 *            the x coordinate of the top left corner
	 * @param y
	 *            the y coordinate of the top left corner
	 * @param level
	 *            the level she spawns in
	 */
	public EntityHildegard(double x, double y, Level level)
	{
		this(x, y, 32, 64, level);
	}

	/**
	 * Constructs a new Hildegard
	 * 
	 * @param x
	 *            the x coordinate of the top left corner
	 * @param y
	 *            the y coordinate of the top left corner
	 * @param width
	 *            the width of this Hildegard instance
	 * @param height
	 *            the height of this Hildegard instance
	 * @param level
	 *            the level she spawns in
	 */
	public EntityHildegard(double x, double y, double width, double height, Level level)
	{
		super(x, y, width, height, level);
		heartAttackIn = Constants.random.nextInt(50) + 12;
	}

	@Override
	public void update()
	{
		super.update();
		motion.x = 0;
		if (cdAttack > 0)
		{
			cdAttack--;
		}
		else
		{
			cdAttack = 0;
		}
		turned = !(bounds.x > level.getPlayer().bounds.x);
		double distance = Math.abs(level.getPlayer().getPivotX() - this.getPivotX());
		if (distance < 320)
		{
			if (cdAnimation > 0)
			{
				cdAnimation--;
			}
			else
			{
				if (state == 2)
				{
					state = 1;
				}
				cdAnimation = 0;
			}
			if (distance > 0)
			{
				motion.x = (float) ((level.getPlayer().getPivotX() - this.getPivotX()) / Math.abs(level.getPlayer().getPivotX() - this.getPivotX()) * 1.5f);
			}
		}
		else
		{
			state = 0;
			cdAnimation = 0;
		}

		if (level.getPlayer().bounds.y + level.getPlayer().bounds.height < this.bounds.y && onGround && level.getPlayer().onGround)
		{
			if (cdJump <= 0)
			{
				motion.y = -10;
				cdJump = 10;
			}
			else
				cdJump--;
		}
	}

	@Override
	public void collide(Body withObject)
	{
		if (cdAttack <= 0)
		{
			attackct++;
			cdAttack = 25;
			state = 2;
			cdAnimation = 12;
			if (attackct > heartAttackIn)
			{
				level.getPlayer().hurt(25);
				level.addSpawnable(new EntityGhost(bounds.x, bounds.y, level));
				this.kill(this);
			}
		}
	}

	@Override
	public void render()
	{
		super.renderHealthbar();
		switch (state)
		{
			case 1:
			{
				super.render("/opp_hildegard_attack.png", bounds.width, bounds.height, bounds.x, bounds.y, 32, 64, turned);
				break;
			}
			case 2:
			{
				super.render("/opp_hildegard_after.png", bounds.width, bounds.height, bounds.x, bounds.y, 32, 64, turned);
				break;
			}
			default:
			{
				super.render("/opp_hildegard_passive.png", bounds.width, bounds.height, bounds.x, bounds.y, 32, 64, turned);
			}
		}
	}

	@Override
	public void renderIcon()
	{
		double h = bounds.height * 0.97;
		double w = h / 2;
		render("/Opp_hildegard_passive.png", w, h, bounds.x + (bounds.width - w) / 2, bounds.y + (bounds.height - h) / 2, 32, 64, 32, 64, false);
	}

	static
	{
		Out.inf(EntityHildegard.class, "01/06/13", "Felix", null);
	}
}
