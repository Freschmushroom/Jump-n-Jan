package at.jumpandjan;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2d;
import at.freschmushroom.Out;
import at.freschmushroom.TextureManager;
import at.jumpandjan.level.Level;

/**
 * The floor on the floor
 * 
 * @author Michael
 *
 */
public class Floor extends Body
{
	public Floor(double x, double y, double width, double height, Level level)
	{
		super(x, y, width, height, level);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render()
	{
		glEnable(GL_TEXTURE_2D);
		TextureManager.instance.bindTexture(TextureManager.instance.getTexture("/wall_middle.png"));
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2d(this.bounds.x + this.bounds.height, this.bounds.y);
		glTexCoord2f(0, 1);
		glVertex2d(this.bounds.x + this.bounds.height, this.bounds.y + this.bounds.height);
		glTexCoord2f(1, 1);
		glVertex2d(this.bounds.x + this.bounds.width - this.bounds.height, this.bounds.y + this.bounds.height);
		glTexCoord2f(1, 0);
		glVertex2d(this.bounds.x + this.bounds.width - this.bounds.height, this.bounds.y);
		glEnd();
		TextureManager.instance.bindTexture(TextureManager.instance.getTexture("/wall_end.png"));
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2d(this.bounds.x, this.bounds.y);
		glTexCoord2f(0, 1);
		glVertex2d(this.bounds.x, this.bounds.y + this.bounds.height);
		glTexCoord2f(1, 1);
		glVertex2d(this.bounds.x + this.bounds.height, this.bounds.y + this.bounds.height);
		glTexCoord2f(1, 0);
		glVertex2d(this.bounds.x + this.bounds.height, this.bounds.y);
		glTexCoord2f(1, 0);
		glVertex2d(this.bounds.x + this.bounds.width - this.bounds.height, this.bounds.y);
		glTexCoord2f(1, 1);
		glVertex2d(this.bounds.x + this.bounds.width - this.bounds.height, this.bounds.y + this.bounds.height);
		glTexCoord2f(0, 1);
		glVertex2d(this.bounds.x + this.bounds.width, this.bounds.y + this.bounds.height);
		glTexCoord2f(0, 0);
		glVertex2d(this.bounds.x + this.bounds.width, this.bounds.y);
		glEnd();
		glDisable(GL_TEXTURE_2D);
	}

	@Override
	public void renderIcon()
	{
		render("/Wall_M.png", bounds.width, 10, 0, bounds.height / 2 - 5, 32, 32, 32, 32, false);
	}

	@Override
	public double getDefaultHeight()
	{
		return 10;
	}

	@Override
	public boolean hasDefaultWidth()
	{
		return false;
	}

	@Override
	public boolean hasDefaultHeight()
	{
		return true;
	}

	static
	{
		Out.inf(Floor.class, "23.10.12", "Michael", null);
	}
}
