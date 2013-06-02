package at.jumpandjan.gui;

import at.freschmushroom.Out;

/**
 * The abstract class of ALL ZE COMPONENTS
 * @author Michael
 *
 */
public abstract class Component extends Gui implements Cloneable {
	/**
	 * The bounds
	 */
	private int x, y, width, height;

	/**
	 * The parenting Gui
	 */
	protected Gui parent;
	
	/**
	 * Whether this component is focused
	 */
	protected boolean isFocused;

	public Component(Gui parent, int x, int y, int width, int height) {
		this.parent = parent;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Returns the x-position
	 * @return x-coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns the y-position
	 * @return y-coordinate
	 */
	public int getY() {
		return y;
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
	 * Sets the X-coordinate
	 * @param x X-coordinate
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Sets the Y-coordinate
	 * @param y Y-coordinate
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Sets the Width
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
	
	@Override
	public final void paint() {
		drawComponent();
	}

	@Override
	public final boolean fireKeyboardEvent(boolean eventKeyState, int eventKey, char eventChar, 
			int mouseX, int mouseY) {
		if (isFocused) {
			if (eventKeyState) {
				onKeyPressed(eventKey, eventChar, mouseX, mouseY);
			}
			return true;
		}
		return false;
	}

	@Override
	public final boolean fireMouseEvent(boolean eventButtonState,
			int eventButton, int mouseX, int mouseY, int dX, int dY) {
		mouseY = 480 - mouseY;
		if (eventButton == -1) {
			if (mouseX >= this.x && mouseX <= this.x + this.width
					&& mouseY >= this.y && mouseY <= this.y + this.height) {
				onHovered(mouseX, mouseY);
			} else {
				onMouseExit(mouseX, mouseY);
			}
		} else {
			if (eventButtonState) {
				if (mouseX >= this.x && mouseX <= this.x + this.width
						&& mouseY >= this.y && mouseY <= this.y + this.height) {
					onClick(mouseX, mouseY, eventButton);
				}
			} else {
				onReleased(mouseX, mouseY, eventButton);
				if (mouseX >= this.x && mouseX <= this.x + this.width
						&& mouseY >= this.y && mouseY <= this.y + this.height) {
					parent.requestFocus(this);
				}
			}
		}

		return false;
	}

	/**
	 * Called when a key is pressed
	 * @param key The keycode
	 * @param character The character code, if available
	 * @param mouseX The position of the mouse at the event
	 * @param mouseY The position of the mouse at the event
	 */
	public void onKeyPressed(int key, char character, int mouseX, int mouseY) {

	}

	/**
	 * Called when the mouse is clicked
	 * @param mouseX X-position of the mouse
	 * @param mouseY Y-position of the mouse
	 * @param mouseButton The button which was clicked
	 */
	public void onClick(int mouseX, int mouseY, int mouseButton) {

	}

	/**
	 * Called, when the mouse is released
	 * @param mouseX The X-Coordinate
	 * @param mouseY The Y-Coordinate
	 * @param mouseButton The mouse button which was released
	 */
	public void onReleased(int mouseX, int mouseY, int mouseButton) {

	}

	/**
	 * Called when the mouse is moved above the component
	 * @param mouseX The X-Position of the mouse
	 * @param mouseY The Y-Position of the mouse
	 */
	public void onHovered(int mouseX, int mouseY) {

	}

	/**
	 * Called when the mouse moves outside the box
	 * @param mouseX The X-Position of the mouse
	 * @param mouseY The Y-Position of the mouse
	 */
	public void onMouseExit(int mouseX, int mouseY) {

	}

	/**
	 * Called by paint; draws the component.
	 * To be implemented by subclasses.
	 */
	public void drawComponent() {
	}

	/**
	 * Copies this component
	 * @return A copy of this component
	 */
	public Object copy() {
		try {
			return clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	static {
		Out.inf(Component.class, "01.06.2013", "Michael", null);
	}
}
