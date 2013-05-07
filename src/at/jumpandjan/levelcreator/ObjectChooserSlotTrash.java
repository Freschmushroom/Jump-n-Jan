package at.jumpandjan.levelcreator;

import static org.lwjgl.opengl.GL11.*;
import at.jumpandjan.TextureManager;

public class ObjectChooserSlotTrash extends ObjectChooserSlot {

	private int trashList;
	
	public ObjectChooserSlotTrash(int x, int y, int width,
			int height) {
		super(null, x, y, width, height);
	}

	public void drawComponent() {
		glPushMatrix();
		
		if (trashList == 0) {
			trashList = glGenLists(1);
			glNewList(trashList, GL_COMPILE);
			TextureManager.instance.bindTexture("/slot_trash.png");
			glEnable(GL_TEXTURE_2D);
			
			glBegin(GL_QUADS);
			
			glTexCoord2f(0, 0);
			glVertex2d(getX(), getY());
			glTexCoord2f(0, 1);
			glVertex2d(getX(), getY() + getHeight());
			glTexCoord2f(1, 1);
			glVertex2d(getX() + getWidth(), getY() + getHeight());
			glTexCoord2f(1, 0);
			glVertex2d(getX() + getWidth(), getY());
			
			glEnd();
			glDisable(GL_TEXTURE_2D);
			
			glEndList();
		}
		
		glCallList(trashList);
		
		glPopMatrix();
	}
}
