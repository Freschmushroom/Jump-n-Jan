package at.jumpandjan;

import static org.lwjgl.opengl.GL11.*;

public class Wall extends Object
{
	public Wall(double x, double y, double width, double height)
	{
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	public void render()
	{
		glBegin(GL_QUADS);
		glColor3f(0, 1, 0);
		glVertex2d(this.x, this.y);
		glVertex2d(this.x, this.y + this.height);
		glVertex2d(this.x + this.width, this.y + this.height);
		glVertex2d(this.x + this.width, this.y);
		glEnd();
	}
}
