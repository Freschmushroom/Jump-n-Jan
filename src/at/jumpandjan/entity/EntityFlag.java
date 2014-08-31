package at.jumpandjan.entity;

import at.freschmushroom.Out;
import at.jumpandjan.level.Level;

/**
 * The super class for all flags
 * 
 * @author Michael
 *
 */
public class EntityFlag extends Entity
{
	/**
	 * The texture name
	 */
	private String imgName = "";

	public EntityFlag(double x, double y, String imgName, Level level)
	{
		super(x, y, 32, 64, level);
		isGravityApplied = false;
		this.imgName = imgName;
	}

	public EntityFlag(double x, double y, double width, double height, String imgName, Level level)
	{
		super(x, y, width, height, level);
		isGravityApplied = false;
		this.imgName = imgName;
	}

	@Override
	public void render()
	{
		super.render(imgName, bounds.width, bounds.height, bounds.x, bounds.y, 32f, 64f, 32, 64, state);
	}

	/**
	 * Teleports the player to this flag
	 */
	public void spawn()
	{
		level.getPlayer().bounds.x = this.bounds.x;
		level.getPlayer().bounds.y = this.bounds.y + this.bounds.height - level.getPlayer().bounds.height - 100;
	}

	@Override
	public void update()
	{
		super.update();
	}

	@Override
	public void renderIcon()
	{
		double offsetX = (bounds.width - 16) / 2;
		double offsetY = (bounds.height - 32) / 2;
		super.render(imgName, 16, 32, offsetX, offsetY, 32, 64, 32, 64, state);
	}

	@Override
	public double getDefaultWidth()
	{
		return 32;
	}

	@Override
	public double getDefaultHeight()
	{
		return 64;
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
		Out.inf(EntityFlag.class, "22.10.12", "Michael", null);
	}
}
