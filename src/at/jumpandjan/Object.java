package at.jumpandjan;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glVertex2d;

import java.awt.Rectangle;

import org.lwjgl.util.vector.Vector2f;

import at.freschmushroom.TextureManager;
import at.jumpandjan.level.Level;

/**
 * This class provides basic fields and methods for Objects in
 * <strong>Jump'n'Jan</strong>.
 * 
 * @version Last Fix: 20/02/13
 * @author Michael
 */
public class Object {

	public Rectangle bounds = new Rectangle();

	/**
	 * The motion as Vector specified in the org.lwjgl.util package
	 */
	public final Vector2f motion = new Vector2f();

	/**
	 * Whether this Object is on ground.<br />
	 * Note: In the default Object, this will always be zero. When making use of
	 * this Variable, a check will have to be manually added.
	 */
	public boolean onGround;

	/**
	 * The Level this Object is in.
	 */
	public Level level;

	/**
	 * The standard constructor.<br />
	 * Note: Subclasses which want to be able to be automatically generated from
	 * a Level-file have to implement a constructor with these Parameters.
	 * 
	 * @param x
	 *            The <strong>X-Coordinate</strong> of this Object
	 * @param y
	 *            The <strong>Y-Coordinate</strong> of this Object
	 * @param width
	 *            The <strong>width</strong> of this Object
	 * @param height
	 *            The <strong>height</strong> of this Object
	 * @param level
	 *            The <strong>Level</strong> of this Object
	 */
	public Object(double x, double y, double width, double height, Level level) {
		setBounds(x, y, width, height);
		this.level = level;
	}

	/**
	 * Called each tick by the main Game Loop.<br />
	 * Note: Things like collision and movement must be implemented by
	 * subclasses.<br />
	 * Note: This method is empty and can be freely overridden.
	 */
	public void update() throws InterruptUpdateException {

	}

	/**
	 * A method used to minimize the amount of data<br />
	 * going through the Rendering Pipeline of OpenGL. It is not recommended to
	 * override this method; it is still possible, though.
	 * 
	 * @return Whether this Object should be passed to OpenGL
	 */
	public boolean shouldRender() {
		return bounds.intersects(Constants.CAMERA_BOUNDS);
	}

	/**
	 * Renders the Object on the Screen
	 */
	public void render() {
		render("/none", bounds.width, bounds.height, bounds.x, bounds.y, (float) bounds.width, (float) bounds.height,
				false);
	}

	public void render(String texture, double width, double height, double x,
			double y, float rWidth, float rHeight, boolean turned) {
		render(texture, width, height, x, y, rWidth, rHeight, (float) width,
				(float) height, turned);
	}

	public void render(String texture, double width, double height, double x,
			double y, float rWidth, float rHeight, float sWidth, float sHeight,
			boolean turned) {
		render(texture, width, height, x, y, 0, 0, rWidth, rHeight, sWidth,
				sHeight, turned);
	}

	public void render(String texture, double width, double height, double x,
			double y, float rX, float rY, float rWidth, float rHeight,
			float sWidth, float sHeight, boolean turned) {
		glPushMatrix();
		glTranslated(x, y, 0);
		if (turned) {
			glTranslated(width, 0, 0);
			glScalef(-1, 1, 1);
		}
		glEnable(GL_TEXTURE_2D);
		TextureManager.instance.bindTexture(TextureManager.instance
				.getTexture(texture));
		float f = 1 / sWidth;
		float f1 = 1 / sHeight;
		glBegin(GL_QUADS);
		glTexCoord2f(rX * f, rY * f1);
		glVertex2d(0, 0);
		glTexCoord2f(rX * f, (rY + rHeight) * f1);
		glVertex2d(0, height);
		glTexCoord2f((rX + rWidth) * f, (rY + rHeight) * f1);
		glVertex2d(width, height);
		glTexCoord2f((rX + rWidth) * f, rY * f);
		glVertex2d(width, 0);
		glEnd();
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
	}

	public void renderIcon() {
		render();
	}

	public double getDefaultWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getDefaultHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean hasDefaultWidth() {
		return false;
	}

	public boolean hasDefaultHeight() {
		return false;
	}

	public void setWidthOrDefault(float width) {
		if (hasDefaultWidth())
			this.bounds.width = (int) getDefaultWidth();
		else
			this.bounds.width = (int) width;
	}

	public void setHeightOrDefault(float height) {
		if (hasDefaultHeight())
			this.bounds.height = (int) getDefaultHeight();
		else
			this.bounds.height = (int) height;
	}
	
	
	/**
	 * Convenience method. Casts all arguments to integers,
	 * then passes them on to {@link setBounds(int, int, int, int)}
	 */
	public void setBounds(double x, double y, double width, double height) {
		setBounds((int) x, (int) y, (int) width, (int) height);
	}
	
	public void setBounds(int x, int y, int width, int height) {
		bounds.setBounds(x, y, width, height);
	}
	
	/**
	 * Sets the current bounds to the given Rectangle
	 * @param bounds The bounds which should be overtaken
	 * @throws NullPointerException Throws, if {@code bounds == null}
	 */
	public void setBounds(Rectangle bounds) throws NullPointerException {
		if (bounds == null) {
			throw new NullPointerException("Bounds may not be null");
		}
		this.bounds = bounds;
	}
	
	public boolean hasCollision() {
		return true;
	}
	
	public boolean checkCollisionWith(at.jumpandjan.Object withObject) {
		return this != withObject && hasCollision();
	}
	
	public void collide(at.jumpandjan.Object withObject) {
		
	}
}
