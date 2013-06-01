package at.jumpandjan.gui;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRANSFORM_BIT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopAttrib;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushAttrib;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2d;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.awt.image.BufferedImage;

import at.jumpandjan.Constants;
import at.jumpandjan.TextureManager;

public abstract class Gui {

	private BufferedImage boundImage;

	public static final int POINTS = GL_POINTS, QUADS = GL_QUADS,
			TRIANGLES = GL_TRIANGLES, LINES = GL_LINES;

	protected java.util.ArrayList<Component> components = new java.util.ArrayList<Component>();

	public void addVertex(int x, int y) {
		glVertex2f(x, y);
	}

	public void bindTexture(String texture) {
		if (texture == null) {
			boundImage = null;
			TextureManager.instance.unbind();
		} else {
			boundImage = TextureManager.instance.getImage(texture);
			TextureManager.instance.bindTexture(texture);
		}
	}

	public void begin(int mode) {
		glBegin(mode);
	}

	public void end() {
		glEnd();
	}

	public void pushMatrix() {
		glPushMatrix();
	}

	public void popMatrix() {
		glPopMatrix();
	}

	public void loadIdentity() {
		glLoadIdentity();
	}
	
	public void clip(int x, int y, int width, int height) {
		glScissor(x, y, width, height);
	}

	public void addVertexUV(int x, int y, int u, int v) {
		addVertexUV((double) x, (double) y, u, v);
	}

	public void addVertexUV(double x, double y, int u, int v) {
		if (boundImage == null)
			throw new IllegalStateException(
					"No image was bound by the GUI. Please use Gui.bindTexture(String texture)");
		glTexCoord2f(u / (float) boundImage.getWidth(),
				v / (float) boundImage.getHeight());
		glVertex2d(x, y);
	}

	public void translate(int x, int y) {
		glTranslatef(x, y, 0);
	}

	public void rotate(double angle, int x, int y, int z) {
		glRotated(angle, x, y, z);
	}

	public void rotateAroundAxis(double angle, int x, int y, int z, int axisX,
			int axisY) {
		translate(axisX, axisY);
		rotate(angle, x, y, z);
		translate(-axisX, -axisY);
	}

	public final void render() {
		glPushAttrib(GL_TRANSFORM_BIT);
		glPushMatrix();

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 640, 480, 0, -1, 1);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		glEnable(GL_TEXTURE_2D);
		for (Component c : components)
			c.paint();
		paint();
		glDisable(GL_TEXTURE_2D);

		glPopMatrix();
		glPopAttrib();
	}

	public void paint() {

	}

	public void color(String color) {
		color(java.awt.Color.decode(color));
	}

	public void color(java.awt.Color color) {
		color(color.getRed(), color.getGreen(), color.getBlue());
	}

	public void color(int red, int green, int blue) {
		color(red, green, blue, 255);
	}

	public void color(int red, int green, int blue, int alpha) {
		color(red / 255f, green / 255f, blue / 255f, alpha / 255f);
	}

	public void color(float red, float green, float blue) {
		color(red, green, blue, 1);
	}

	public void color(float red, float green, float blue, float alpha) {
		glColor4f(red, green, blue, alpha);
	}

	public boolean fireKeyboardEvent(boolean eventKeyState, int eventKey,
			int mouseX, int mouseY) {
		return false;
	}

	public boolean fireMouseEvent(boolean eventButtonState, int eventButton,
			int mouseX, int mouseY, int dX, int dY) {
		mouseX = (int) ((double) mouseX / (double) Constants.getCameraWidth() * 640);
		mouseY = (int) ((double) mouseY / (double) Constants.getCameraHeight() * 480);
		for (Component c : components) {
			if (c.fireMouseEvent(eventButtonState, eventButton, mouseX, mouseY,
					dX, dY)) {
				return true;
			}
		}

		return false;
	}

	public void resized() {
	}

	public void updateWhileInactive() {
		for (Component c : components) {
			c.updateWhileInactive();
		}
	}
}
