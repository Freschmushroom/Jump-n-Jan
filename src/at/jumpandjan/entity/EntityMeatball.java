package at.jumpandjan.entity;

import at.freschmushroom.Out;
import at.jumpandjan.Body;
import at.jumpandjan.level.Level;

/**
 * A cute meatball :3
 * 
 * @author Felix
 * @author Michael
 * 
 */
public class EntityMeatball extends Entity
{
	/**
	 * The cooldown on the jump
	 */
	private int cdJump;

	public EntityMeatball(double x, double y, Level level)
	{
		this(0, 0, 32, 32, level);
	}

	public EntityMeatball(double x, double y, double width, double height, Level level)
	{
		super(x, y, width, height, level);
	}

	@Override
	public void update()
	{
		super.update();
		motion.x = 0;
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
		super.render("/Opp_Meatball.png", bounds.width, bounds.height, bounds.x, bounds.y, 28, 26, 32, 32, state);
	}

	@Override
	public void renderIcon()
	{
		render("/Opp_Meatball.png", bounds.width - 4, bounds.height - 4, bounds.x + 2, bounds.y + 2, 28, 26, 32, 32, false);
	}

	@Override
	public double getDefaultWidth()
	{
		return 32;
	}

	@Override
	public double getDefaultHeight()
	{
		return 32;
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
		Out.inf(EntityMeatball.class, "22.10.12", "Felix, Michael", null);
	}
}
