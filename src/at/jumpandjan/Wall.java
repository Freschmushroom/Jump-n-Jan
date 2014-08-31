package at.jumpandjan;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glVertex2d;
import at.freschmushroom.Out;
import at.freschmushroom.TextureManager;
import at.jumpandjan.level.Level;

/**
 * A vertical unit providing collision.
 * 
 * @author Michael
 *
 */
public class Wall extends Body
{
	public Wall(double x, double y, double width, double height, Level level)
	{
		super(x, y, width, height, level);
	}

	@Override
	public void render()
	{
		glEnable(GL_TEXTURE_2D);
		TextureManager.instance.bindTexture(TextureManager.instance.getTexture("/wall_middle.png"));
		glBegin(GL_QUADS);
		glTexCoord2f(0, 1);
		glVertex2d(this.bounds.x, this.bounds.y + this.bounds.width);
		glTexCoord2f(1, 1);
		glVertex2d(this.bounds.x, this.bounds.y + this.bounds.height - this.bounds.width);
		glTexCoord2f(1, 0);
		glVertex2d(this.bounds.x + this.bounds.width, this.bounds.y + this.bounds.height - this.bounds.width);
		glTexCoord2f(0, 0);
		glVertex2d(this.bounds.x + this.bounds.width, this.bounds.y + this.bounds.width);
		glEnd();
		TextureManager.instance.bindTexture(TextureManager.instance.getTexture("/wall_end.png"));
		glBegin(GL_QUADS);
		glTexCoord2f(0, 1);
		glVertex2d(this.bounds.x, this.bounds.y + this.bounds.height);
		glTexCoord2f(1, 1);
		glVertex2d(this.bounds.x, this.bounds.y + this.bounds.height - this.bounds.width);
		glTexCoord2f(1, 0);
		glVertex2d(this.bounds.x + this.bounds.width, this.bounds.y + this.bounds.height - this.bounds.width);
		glTexCoord2f(0, 0);
		glVertex2d(this.bounds.x + this.bounds.width, this.bounds.y + this.bounds.height);
		glTexCoord2f(0, 0);
		glVertex2d(this.bounds.x + this.bounds.width, this.bounds.y);
		glTexCoord2f(1, 0);
		glVertex2d(this.bounds.x + this.bounds.width, this.bounds.y + this.bounds.width);
		glTexCoord2f(1, 1);
		glVertex2d(this.bounds.x, this.bounds.y + this.bounds.width);
		glTexCoord2f(0, 1);
		glVertex2d(this.bounds.x, this.bounds.y);
		glEnd();
		glDisable(GL_TEXTURE_2D);
	}

	@Override
	public void renderIcon()
	{
		glTranslated(bounds.width / 2, bounds.height / 2, 0);
		glRotatef(90, 0, 0, 1);
		glTranslated(-bounds.width / 2, -bounds.height / 2, 0);
		render("/Wall_M.png", bounds.width, 10, 0, bounds.height / 2 - 5, 32, 32, 32, 32, false);
	}

	static
	{
		Out.inf(Wall.class, "23.10.12", "Michael", null);
	}

	@Override
	public double getDefaultWidth()
	{
		return 10;
	}

	@Override
	public boolean hasDefaultWidth()
	{
		return true;
	}

	@Override
	public boolean hasDefaultHeight()
	{
		return false;
	}
}
