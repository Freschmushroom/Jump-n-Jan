package at.jumpandjan;

import java.util.ArrayList;
import java.util.List;

public class Object
{
	public List<Object> collision = new ArrayList<Object>();
	public double x;
	public double y;
	public double width;
	public double height;
	public double motionX;
	public double motionY;
	public boolean onGround;

	public Object(double x, double y, double width, double height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void update()
	{

	}

	public void render()
	{

	}
}
