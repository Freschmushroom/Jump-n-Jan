package at.jumpandjan;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glVertex2d;

public class EntityMeatball extends Entity
{
	public EntityMeatball(double x, double y)
	{
		super(x, y, 32, 32);
	}

	public void update()
	{
		if (Math.abs(JumpAndJan.getPlayer().x - this.x) < 320 && Math.abs(JumpAndJan.getPlayer().x - this.x) > 1)
		{
			motionX = (JumpAndJan.getPlayer().x - this.x)
					/ Math.abs(JumpAndJan.getPlayer().x - this.x);
			if(JumpAndJan.getPlayer().y < this.y && onGround) {
				motionY = -10;
				Out.line("UP");
			}
		} else
		{
			motionX = 0;
			if (Math.abs(JumpAndJan.getPlayer().x - this.x) <= 1) {
				Out.line(JumpAndJan.getPlayer().getHp() + "");
				JumpAndJan.getPlayer().hurt(1);
			}
		}
		super.update();
	}

	public void render()
	{
		glPushMatrix();
		glTranslated(x, y, 0);
		if (state)
		{
			glTranslated(width, 0, 0);
			glScalef(-1, 1, 1);
		}
		glEnable(GL_TEXTURE_2D);
		TextureManager.instance.bindTexture(TextureManager.instance
				.getTexture("/Opp_Meatball.png"));
		glColor3f(1, 1, 1);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2d(0, 0);
		glTexCoord2f(0, 1);
		glVertex2d(0, height);
		glTexCoord2f(1, 1);
		glVertex2d(width, height);
		glTexCoord2f(1, 0);
		glVertex2d(width, 0);
		glEnd();
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
	}
}
