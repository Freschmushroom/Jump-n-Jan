package at.jumpandjan.gui;

import static org.lwjgl.opengl.GL11.GL_LINES;
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
import static org.lwjgl.opengl.GL11.glScissor;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2d;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.awt.image.BufferedImage;

import at.freschmushroom.Out;
import at.freschmushroom.TextureManager;
import at.jumpandjan.Constants;

/**
 * A Gui
 * 
 * @author Michael
 * 
 */
public abstract class Gui {

	/**
	 * The currently bound image, used to determine texture coordinates
	 */
	private BufferedImage boundImage;

	/**
	 * The point render mode
	 */
	public static final int POINTS = GL_POINTS;

	/**
	 * The quad render mode
	 */
	public static final int QUADS = GL_QUADS;

	/**
	 * The triangle render mode
	 */
	public static final int TRIANGLES = GL_TRIANGLES;

	/**
	 * The line render mode
	 */
	public static final int LINES = GL_LINES;

	/**
	 * The components this Gui contains
	 */
	protected java.util.ArrayList<Component> components = new java.util.ArrayList<Component>();

	/**
	 * The focused component
	 */
	protected Component focusedComponent;

	/**
	 * Calls OpenGL methods to add a vertex to the current shape.
	 * 
	 * @param x
	 *            X-Coordinate
	 * @param y
	 *            Y-Coordinate
	 */
	public void addVertex(int x, int y) {
		glVertex2f(x, y);
	}

	/**
	 * Binds the specified texture
	 * 
	 * @param texture
	 *            The texture path, or null for unbinding
	 */
	public void bindTexture(String texture) {
		if (texture == null) {
			boundImage = null;
			TextureManager.instance.unbind();
		} else {
			boundImage = TextureManager.instance.getImage(texture);
			TextureManager.instance.bindTexture(texture);
		}
	}

	/**
	 * Begins drawing in the specified mode
	 * 
	 * @param mode
	 *            The mode
	 */
	public void begin(int mode) {
		glBegin(mode);
	}

	/**
	 * Ends the current render mode
	 */
	public void end() {
		glEnd();
	}

	/**
	 * Pushes the matrix
	 */
	public void pushMatrix() {
		glPushMatrix();
	}

	/**
	 * Pops the matrix
	 */
	public void popMatrix() {
		glPopMatrix();
	}

	/**
	 * Loads the Identity matrix
	 */
	public void loadIdentity() {
		glLoadIdentity();
	}

	/**
	 * Clips the view to the specified bounds (0, 0) = Upper left corner Max
	 * width: 640, max height: 480
	 * 
	 * @param x
	 *            X-Coordinate
	 * @param y
	 *            Y-Coordinate
	 * @param width
	 *            Width
	 * @param height
	 *            height
	 */
	public void clip(int x, int y, int width, int height) {
		int realX = (int) (x / 640f * Constants.getCameraWidth());
		int realY = (int) ((480 - height) / 480f * Constants.getCameraHeight());
		int realWidth = (int) (width / 640f * Constants.getCameraWidth());
		int realHeight = (int) ((480 - y - realY) / 480f * Constants
				.getCameraHeight());
		glScissor(realX, realY, realWidth, realHeight);
	}

	/**
	 * Calls OpenGL methods to add a new vertex with the specified texture
	 * coordinates
	 * 
	 * @param x
	 *            The X-coordinate
	 * @param y
	 *            The Y-coordinate
	 * @param u
	 *            The texture X-Coordinate
	 * @param v
	 *            The texture Y-Coordinate
	 */
	public void addVertexUV(int x, int y, int u, int v) {
		addVertexUV((double) x, (double) y, u, v);
	}

	/**
	 * Calls OpenGL methods to add a new vertex with the specified texture
	 * coordinates
	 * 
	 * @param x
	 *            The X-coordinate
	 * @param y
	 *            The Y-coordinate
	 * @param u
	 *            The texture X-Coordinate
	 * @param v
	 *            The texture Y-Coordinate
	 */
	public void addVertexUV(double x, double y, int u, int v) {
		if (boundImage == null)
			throw new IllegalStateException(
					"No image was bound by the GUI. Please use Gui.bindTexture(String texture)");
		glTexCoord2f(u / (float) boundImage.getWidth(),
				v / (float) boundImage.getHeight());
		glVertex2d(x, y);
	}

	/**
	 * Translates the interges into español
	 * 
	 * @param x
	 *            The amount on the X-axis
	 * @param y
	 *            The amount on the Y-axis
	 */
	public void translate(int x, int y) {
		glTranslatef(x, y, 0);
	}

	/**
	 * Rotates around the specified axis
	 * 
	 * @param angle
	 *            The angle
	 * @param x
	 *            1, if it should rotate around the x-axis
	 * @param y
	 *            1, if it should rotate around the y-axis
	 * @param z
	 *            1, if it should rotate around the z-axis
	 */
	public void rotate(double angle, int x, int y, int z) {
		glRotated(angle, x, y, z);
	}

	/**
	 * Rotates around a custom (!) axis
	 * 
	 * @param angle
	 *            The angle
	 * @param x
	 *            1, if it should rotate around the x-axis
	 * @param y
	 *            1, if it should rotate around the y-axis
	 * @param z
	 *            1, if it should rotate around the z-axis
	 * @param axisX
	 *            the x-offset of the axis
	 * @param axisY
	 *            the y-offset of the axis
	 */
	public void rotateAroundAxis(double angle, int x, int y, int z, int axisX,
			int axisY) {
		translate(axisX, axisY);
		rotate(angle, x, y, z);
		translate(-axisX, -axisY);
	}

	/**
	 * Renders the rendering renderer
	 */
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

	/**
	 * Called by render, overriden by subclasses
	 */
	public void paint() {

	}

	/**
	 * Sets the color
	 * 
	 * @param color
	 *            The color
	 */
	public void color(String color) {
		color(java.awt.Color.decode(color));
	}

	/**
	 * Sets the color
	 * 
	 * @param color
	 *            The color
	 */
	public void color(java.awt.Color color) {
		color(color.getRed(), color.getGreen(), color.getBlue());
	}

	/**
	 * Sets the color
	 * 
	 * @param color
	 *            The color
	 */
	public void color(int red, int green, int blue) {
		color(red, green, blue, 255);
	}

	/**
	 * Sets the color
	 * 
	 * @param color
	 *            The color
	 */
	public void color(int red, int green, int blue, int alpha) {
		color(red / 255f, green / 255f, blue / 255f, alpha / 255f);
	}

	/**
	 * Sets the color
	 * 
	 * @param color
	 *            The color
	 */
	public void color(float red, float green, float blue) {
		color(red, green, blue, 1);
	}

	/**
	 * Sets the color
	 * 
	 * @param color
	 *            The color
	 */
	public void color(float red, float green, float blue, float alpha) {
		glColor4f(red, green, blue, alpha);
	}

	/**
	 * Fires a keyboard event
	 */
	public boolean fireKeyboardEvent(boolean eventKeyState, int eventKey,
			char eventChar, int mouseX, int mouseY) {
		if (focusedComponent != null) {
			focusedComponent.fireKeyboardEvent(eventKeyState, eventKey,
					eventChar, mouseX, mouseY);
		}
		return focusedComponent != null;
	}

	/**
	 * Fires a mouse event
	 */
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

	/**
	 * Called when ze gui was resized
	 */
	public void resized() {
	}

	/**
	 * Updates while inactive
	 */
	public void updateWhileInactive() {
		for (Component c : components) {
			c.updateWhileInactive();
		}
	}

	/**
	 * Focuses the given component and takes the focus from all other components
	 * 
	 * @param component
	 *            The component
	 */
	public void requestFocus(Component component) {
		if (components.contains(component)) {
			for (Component c : components) {
				if (c != component) {
					c.isFocused = false;
				}
			}
			this.focusedComponent = component;
			component.isFocused = true;
		}
	}

	static {
		Out.inf(Gui.class, "01.06.2013", "Michael", null);
	}
}
