package at.jumpandjan.entity;

import at.freschmushroom.Out;
import at.jumpandjan.Body;
import at.jumpandjan.level.Level;

/**
 * Unicoooorn
 * 
 * @author Felix
 *
 */
public class EntityUnicorn extends Entity
{
	/**
	 * The cool down on the jump
	 */
	private int cdJump;

	/**
	 * The cool down on the attack
	 */
	private int cdAttack = 300;

	/**
	 * The attack count
	 */
	private int attackct;

	public EntityUnicorn(double x, double y, double width, double height, Level level)
	{
		super(x, y, width, height, level);
	}

	@Override
	public void update()
	{
		super.update();
		motion.x = 0;
		if (cdAttack > 0)
		{
			if (Math.abs(level.getPlayer().getPivotX() - this.getPivotX()) < 320 && Math.abs(level.getPlayer().getPivotX() - this.getPivotX()) > 1)
			{
				motion.x = (float) ((level.getPlayer().getPivotX() - this.getPivotX()) / Math.abs(level.getPlayer().getPivotX() - this.getPivotX()) * 1.5f);
			}

			if (level.getPlayer().bounds.y + level.getPlayer().bounds.height < this.bounds.y && onGround && level.getPlayer().onGround)
			{
				if (cdJump <= 0)
				{
					motion.y = -20;
					cdJump = 10;
				}
				else
					cdJump--;
			}
			cdAttack--;
		}
		else
		{
			if (attackct <= 5)
			{
				isGravityApplied = false;
				isInfluencedByCollision = false;
				motion.y = -20;
			}
			else
			{
				isGravityApplied = true;
				isInfluencedByCollision = true;
				int dur = 120 - attackct;
				double vecX = (this.bounds.x - level.getPlayer().bounds.x) / (double) dur;
				double vecY = (this.bounds.y - level.getPlayer().bounds.y) / (double) dur;
				motion.x -= vecX;
				motion.y -= vecY;
			}
			attackct++;
			if (attackct == 120)
			{
				cdAttack = 300;
				attackct = 0;
			}
		}
	}

	@Override
	public void collide(Body withObject)
	{
		if (withObject instanceof EntityPlayer)
		{
			level.getPlayer().hurt(1);
		}
	}

	@Override
	public void render()
	{
		super.renderHealthbar();
		super.render("/opp_unicorn.png", bounds.width, bounds.height, bounds.x, bounds.y, 403, 420, 463, 420, state);
	}

	@Override
	public void renderIcon()
	{
		render("/opp_unicorn.png", bounds.width, bounds.height, bounds.x, bounds.y, 128, 0, 246, 260, 463, 420, false);
	}

	@Override
	public boolean hasDefaultWidth()
	{
		return true;
	}

	@Override
	public boolean hasDefaultHeight()
	{
		return true;
	}

	static
	{
		Out.inf(EntityUnicorn.class, "04.12.11", "Felix", null);
	}
}
