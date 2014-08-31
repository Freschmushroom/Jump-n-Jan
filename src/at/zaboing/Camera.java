package at.zaboing;

import java.awt.Rectangle;

import org.lwjgl.opengl.Display;

public class Camera extends Rectangle
{
	private static final long serialVersionUID = -2000182997546920255L;

	public static Camera createCamera()
	{
		return new Camera(0, 0, 0, 0);
	}

	private Camera(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	}

	public void update()
	{
		if (Display.isCreated())
		{
			width = Display.getWidth();
			height = Display.getHeight();
		}
	}
}
