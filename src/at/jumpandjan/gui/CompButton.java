package at.jumpandjan.gui;

import java.awt.Font;
import java.awt.Point;

import at.freschmushroom.Out;

import utils.TrueTypeFont;


/**
 * Implements methods for buttons
 * @author Michael
 *
 */
public class CompButton extends Component {

	/**
	 * The text being displayed
	 */
	private String text;
	
	/**
	 * If the button is disabled, no input will be accepted and it will be rendered differently
	 */
	private boolean enabled = true;
	
	/**
	 * Whether the mouse is above this button
	 */
	private boolean mouseHover;
	
	/**
	 * Whether the mouse has clicked a short time ago
	 */
	private boolean mouseClicked;
	
	/**
	 * The count down for the render state
	 */
	private byte cooldown;
	
	/**
	 * Holds the font being used for rendering
	 */
	private TrueTypeFont font = new TrueTypeFont(new Font(Font.MONOSPACED,
			Font.BOLD, 20), false);

	/**
	 * ALL ZE LISTENERS
	 */
	private java.util.ArrayList<ActionListener> buttonListener = new java.util.ArrayList<ActionListener>();

	public CompButton(Gui parent, int x, int y, String text) {
		this(parent, x, y, 0, text);
	}

	public CompButton(Gui parent, int x, int y, int width, String text) {
		this(parent, x, y, width, 32, text);
	}
	
	public CompButton(Gui parent, int x, int y, int width, int height, String text) {
		super(parent, x, y, width, height);
		setText(text);
	}

	/**
	 * Centers the button at the specified location.
	 * Convenience method for layouting.
	 * @param x The x-coordinate of the center
	 * @param y The y-coordinate of the center
	 * @return This button
	 */
	public CompButton setCenter(int x, int y) {
		setX(x - getWidth() / 2);
		setY(y - getHeight() / 2);
		return this;
	}
	
	/**
	 * Returns the center of this button. 
	 * @return The center
	 */
	public Point getCenter() {
		return new Point(getX() + getWidth() / 2, getY() + getHeight() / 2);
	}

	/**
	 * Sets the text of this button
	 * @param text The text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Returns the text of this button
	 * @return The text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @see enabled
	 * @param enabled The new enabled state
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Enables this button. Equivalent to setEnabled(true).
	 * Convenience method
	 */
	public void enable() {
		setEnabled(true);
	}

	/**
	 * Disables this button. Equivalent to setEnabled(false).
	 * Convenience method
	 */
	public void disable() {
		setEnabled(false);
	}

	/**
	 * @see enable
	 * @return The enable-state of this button
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Sets the width of this component. The minimum width is 20; if width is smaller than 20, it is clamped.
	 */
	@Override
	public void setWidth(int width) {
		if (width < 20)
			width = 20;
		super.setWidth(width);
	}

	// @Deprecated
	// public void setHeight(int height) {
	//
	// }

	@Override
	public void drawComponent() {
		color(255, 255, 255, 255);
		
		int yOffset = (enabled ? (mouseClicked ? 2 : (mouseHover ? 1 : 0)) : 3) * 32;

		bindTexture("/button.png");
		begin(QUADS);

		addVertexUV(getX(), getY(), 0, 0 + yOffset);
		addVertexUV(getX(), getY() + getHeight(), 0, 32 + yOffset);
		addVertexUV(getX() + 16, getY() + getHeight(), 16, 32 + yOffset);
		addVertexUV(getX() + 16, getY(), 16, 0 + yOffset);

		addVertexUV(getX() + 16, getY(), 16, 0 + yOffset);
		addVertexUV(getX() + 16, getY() + getHeight(), 16, 32 + yOffset);
		addVertexUV(getX() + getWidth() - 16, getY() + getHeight(), 256 - 16,
				32 + yOffset);
		addVertexUV(getX() + getWidth() - 16, getY(), 256 - 16, 0 + yOffset);

		addVertexUV(getX() + getWidth() - 16, getY(), 256 - 16, 0 + yOffset);
		addVertexUV(getX() + getWidth() - 16, getY() + getHeight(), 256 - 16,
				32 + yOffset);
		addVertexUV(getX() + getWidth(), getY() + getHeight(), 256,
				32 + yOffset);
		addVertexUV(getX() + getWidth(), getY(), 256, 0 + yOffset);

		end();

		font.drawString(this.getX() + this.getWidth() / 2,
				this.getY() + this.getHeight() * 0.85f, text, 1, -1,
				TrueTypeFont.ALIGN_CENTER);

		if (cooldown == 1)
			mouseClicked = false;
		if (cooldown > 0)
			cooldown--;

	}

	@Override
	public void onClick(int mouseX, int mouseY, int mouseButton) {
		mouseClicked = true;
		for (ActionListener al : buttonListener) {
			al.onClicked(this);
		}
	}

	@Override
	public void onHovered(int mouseX, int mouseY) {
		mouseHover = true;
	}

	@Override
	public void onMouseExit(int mouseX, int mouseY) {
		mouseHover = false;
	}

	@Override
	public void onReleased(int mouseX, int mouseY, int mouseButton) {
		if (mouseClicked) {
			cooldown = 16;
			for (ActionListener al : buttonListener) {
				al.onReleased(this);
			}
			for (ActionListener al : buttonListener) {
				al.onPressed(this);
			}
		}
	}

	/**
	 * Adds a listener
	 * @see buttonListener
	 * @param al The new listener
	 */
	public void addButtonListener(ActionListener al) {
		buttonListener.add(al);
	}

	/**
	 * Removes a listener
	 * @param al The old listener
	 */
	public void removeButtonListener(ActionListener al) {
		buttonListener.remove(al);
	}
	
	@Override
	public void updateWhileInactive() {
		if (cooldown == 1)
			mouseClicked = false;
		if (cooldown > 0)
			cooldown--;
	}
	
	static {
		Out.inf(CompButton.class, "01.06.2013", "Michael", null);
	}
}
