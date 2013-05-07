package at.jumpandjan.levelcreator;

import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glNewList;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glVertex2d;
import at.jumpandjan.Object;
import at.jumpandjan.TextureManager;
import at.jumpandjan.gui.Component;

public class ObjectChooserSlot extends Component {
	public at.jumpandjan.Object forObject;
	public boolean enabled = true;

	private int displayList;
	private int listMarginEnabled;
	private int listMarginDisabled;

	public ObjectChooserSlot(Object o, int x, int y, int width,
			int height) {
		super(x, y, width, height);
		forObject = o;
	}

	public void initList() {
		displayList = glGenLists(1);
		glNewList(displayList, GL_COMPILE);

		forObject.renderIcon();

		glEndList();
	}

	public void drawComponent() {
		glPushMatrix();
		glTranslated(getX(), getY(), 0);
		if (displayList == 0)
			initList();
		glCallList(displayList);
		renderMargin(enabled);
		glPopMatrix();
	}

	private void renderMargin(boolean enabled) {
		if (listMarginEnabled == 0)
			initListMarginEnabled();
		if (listMarginDisabled == 0)
			initListMarginDisabled();
		glCallList(enabled ? listMarginEnabled : listMarginDisabled);
	}

	private void initListMarginDisabled() {
		listMarginDisabled = glGenLists(1);
		glNewList(listMarginDisabled, GL_COMPILE);
		
		glEnable(GL_TEXTURE_2D);
		TextureManager.instance.bindTexture("/slot_margin_disabled.png");
		glBegin(GL_QUADS);
		
		glTexCoord2f(0, 0);
		glVertex2d(0, 0);
		glTexCoord2f(0, 1);
		glVertex2d(0, 0 + getHeight());
		glTexCoord2f(1, 1);
		glVertex2d(0 + getWidth(), 0 + getHeight());
		glTexCoord2f(1, 0);
		glVertex2d(0 + getWidth(), 0);
		
		glEnd();
		glDisable(GL_TEXTURE_2D);
		
		glEndList();
	}

	private void initListMarginEnabled() {
		listMarginEnabled = glGenLists(1);
		glNewList(listMarginEnabled, GL_COMPILE);
		
		glEnable(GL_TEXTURE_2D);
		TextureManager.instance.bindTexture("/slot_margin_enabled.png");
		glBegin(GL_QUADS);
		
		glTexCoord2f(0, 0);
		glVertex2d(0, 0);
		glTexCoord2f(0, 1);
		glVertex2d(0, 0 + getHeight());
		glTexCoord2f(1, 1);
		glVertex2d(0 + getWidth(), 0 + getHeight());
		glTexCoord2f(1, 0);
		glVertex2d(0 + getWidth(), 0);
		
		glEnd();
		glDisable(GL_TEXTURE_2D);
		
		glEndList();
	}
}
