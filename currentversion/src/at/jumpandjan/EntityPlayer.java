package at.jumpandjan;

import static org.lwjgl.opengl.GL11.*;

public class EntityPlayer extends Entity
{
	private int hp = 200;
	
	public EntityPlayer(double x, double y, double width, double height)
	{
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
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
				.getTexture("/Janny_Krieger.png"));
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

	public void hurt(int hitpoints) {
		if(hp <= hitpoints) {
			hp = 0;
		} else {
			hp -= hitpoints;
		}
	}
	
	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}
	
}
