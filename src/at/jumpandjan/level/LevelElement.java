package at.jumpandjan.level;

import at.freschmushroom.Out;

/**
 * The building element base class
 * 
 * @author Felix
 * @author Michael
 * 
 */
public abstract class LevelElement {
	/**
	 * The x-position of the element
	 */
	private int posX;

	/**
	 * The y-position of the element
	 */
	private int posY;
	/**
	 * The element width
	 */
	private int width;
	/**
	 * The element height
	 */
	private int height;

	public LevelElement(double x, double y, double width, double height) {
		setPosX((int) x);
		setPosY((int) y);
		setWidth((int) width);
		setHeight((int) height);
	}

	/**
	 * Sets the x-position
	 * @param posX The x-position
	 */
	public void setPosX(int posX) {
		this.posX = posX;
	}

	/**
	 * Sets the y-position
	 * @param posY The y-position
	 */
	public void setPosY(int posY) {
		this.posY = posY;
	}

	/**
	 * Sets the width
	 * @param width The width
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Sets the height
	 * @param height The height
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Returns the x-position
	 * @return The x-position
	 */
	public int getPosX() {
		return posX;
	}

	/**
	 * Returns the y-position
	 * @return The y-position
	 */
	public int getPosY() {
		return posY;
	}

	/**
	 * Returns the width
	 * @return The width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns the height
	 * @return The height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Returns the Object of this element
	 * @param level For this level
	 * @return The object
	 */
	public abstract at.jumpandjan.Object getElement(Level level);
	
	static {
		Out.inf(LevelElement.class, "01.06.2013", "Felix", null);
	}
}
