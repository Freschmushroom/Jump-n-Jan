package at.jumpandjan.entity;

import at.freschmushroom.Out;
import at.jumpandjan.Body;
import at.jumpandjan.level.Level;

/**
 * A waitrose
 * 
 * @author Felix
 * 
 */
public class EntityWaitrose extends Entity
{
	/**
	 * The cool down on the jump
	 */
	private int cdJump;

	public EntityWaitrose(double x, double y, Level level)
	{
		super(x, y, 32, 64, level);
	}

	public EntityWaitrose(double x, double y, double width, double height, Level level)
	{
		super(x, y, width, height, level);
	}

	@Override
	public void update()
	{
		super.update();
		this.motion.x = 0;
		if (Math.abs(level.getPlayer().getPivotX() - this.getPivotX()) < 100 && Math.abs(level.getPlayer().getPivotX() - this.getPivotX()) > 1)
		{
			motion.x = (float) ((level.getPlayer().getPivotX() - this.getPivotX()) / Math.abs(level.getPlayer().getPivotX() - this.getPivotX()) * 2.9f);
		}
		if (level.getPlayer().bounds.y + level.getPlayer().bounds.height < this.bounds.y && onGround && level.getPlayer().onGround)
		{
			if (cdJump <= 0)
			{
				motion.y = -40;
				cdJump = 10;
			}
			else
				cdJump--;
		}
	}

	@Override
	public void render()
	{
		super.renderHealthbar();
		super.render("/Opp_Cactus.png", bounds.width, bounds.height, bounds.x, bounds.y, 128, 256, 128, 256, state);
	}

	@Override
	public void renderIcon()
	{
		super.render("/Opp_Cactus.png", 2 * bounds.width / 3, bounds.height, bounds.x + bounds.width / 6, bounds.y, 128, 256, 128, 256, false);
	}

	@Override
	public void collide(Body withObject)
	{
		if (withObject instanceof EntityPlayer)
		{
			level.getPlayer().hurt(2);
		}
	}

	static
	{
		Out.inf(EntityWaitrose.class, "23.10.12", "Felix", null);
	}
}
