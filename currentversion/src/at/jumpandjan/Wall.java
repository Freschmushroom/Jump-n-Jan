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
		glEnable(GL_TEXTURE_2D);
		TextureManager.instance.bindTexture(TextureManager.instance.getTexture("/Wall_M.png"));
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2d(this.x + 32, this.y);
		glTexCoord2f(0, 1);
		glVertex2d(this.x + 32, this.y + this.height);
		glTexCoord2f(1, 1);
		glVertex2d(this.x + this.width - 32, this.y + this.height);
		glTexCoord2f(1, 0);
		glVertex2d(this.x + this.width - 32, this.y);
		glEnd();
		TextureManager.instance.bindTexture(TextureManager.instance.getTexture("/Wall_e.png"));
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2d(this.x, this.y);
		glTexCoord2f(0, 1);
		glVertex2d(this.x, this.y + this.height);
		glTexCoord2f(1, 1);
		glVertex2d(this.x + 32, this.y + this.height);
		glTexCoord2f(1, 0);
		glVertex2d(this.x + 32, this.y);
		glTexCoord2f(1, 0);
		glVertex2d(this.x + this.width - 32, this.y);
		glTexCoord2f(1, 1);
		glVertex2d(this.x + this.width - 32, this.y + this.height);
		glTexCoord2f(0, 1);
		glVertex2d(this.x + this.width, this.y + this.height);
		glTexCoord2f(0, 0);
		glVertex2d(this.x + this.width, this.y);
		glEnd();
		glDisable(GL_TEXTURE_2D);
	}
}
