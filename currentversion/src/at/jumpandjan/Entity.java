package at.jumpandjan;

public class Entity extends Object
{
	protected boolean state;

	public Entity(double x, double y, double width, double height)
	{
		super(x, y, width, height);
	}

	public void update()
	{
		if (motionX < 0)
			state = true;
		else if (motionX > 0)
			state = false;
		motionY += 1.4;
		label1: for (int i = 0; i < 20000; i++)
		{
			double x = this.x + motionX * 0.00005;
			for (Object o : collision)
			{
				if (x >= o.x && x <= o.x + o.width && y >= o.y
						&& y <= o.y + o.height || x + width >= o.x
						&& x + width <= o.x + o.width && y >= o.y
						&& y <= o.y + o.height || x + width >= o.x
						&& x + width <= o.x + o.width && y + height >= o.y
						&& y + height <= o.y + o.height || x >= o.x
						&& x <= o.x + o.width && y + height >= o.y
						&& y + height <= o.y + o.height)
				{
					motionX = 0;
					break label1;
				}
			}
			this.x = x;
		}
		onGround = false;
		label2: for (int i = 0; i < 20000; i++)
		{
			double y = this.y + motionY * 0.00005;
			for (Object o : collision)
			{
				if (x >= o.x && x <= o.x + o.width && y >= o.y
						&& y <= o.y + o.height || x + width >= o.x
						&& x + width <= o.x + o.width && y >= o.y
						&& y <= o.y + o.height || x + width >= o.x
						&& x + width <= o.x + o.width && y + height >= o.y
						&& y + height <= o.y + o.height || x >= o.x
						&& x <= o.x + o.width && y + height >= o.y
						&& y + height <= o.y + o.height)
				{
					motionY = 0;
					onGround = true;
					break label2;
				}
			}
			this.y = y;
		}
	}
}
