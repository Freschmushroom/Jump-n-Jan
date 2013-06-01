package at.jumpandjan.gui;

import java.awt.Font;
import java.awt.Point;

import utils.TrueTypeFont;

public class CompButton extends Component {

	private String text;
	private boolean enabled = true;
	private boolean mouseHover;
	private boolean mouseClicked;
	private byte cooldown;
	private TrueTypeFont font = new TrueTypeFont(new Font(Font.MONOSPACED,
			Font.BOLD, 20), false);

	private java.util.ArrayList<ActionListener> buttonListener = new java.util.ArrayList<ActionListener>();

	public CompButton(int x, int y, String text) {
		this(x, y, 0, text);
	}

	public CompButton(int x, int y, int width, String text) {
		this(x, y, width, 32, text);
	}
	
	public CompButton(int x, int y, int width, int height, String text) {
		super(x, y, width, height);
		setText(text);
	}

	public CompButton setCenter(int x, int y) {
		setX(x - getWidth() / 2);
		setY(y - getHeight() / 2);
		return this;
	}
	
	public Point getCenter() {
		return new Point(getX(), getY());
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void enable() {
		setEnabled(true);
	}

	public void disable() {
		setEnabled(false);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setWidth(int width) {
		if (width < 20)
			width = 20;
	}

	// @Deprecated
	// public void setHeight(int height) {
	//
	// }

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

	public void onClick(int mouseX, int mouseY, int mouseButton) {
		mouseClicked = true;
		for (ActionListener al : buttonListener) {
			al.onClicked(this);
		}
	}

	public void onHovered(int mouseX, int mouseY) {
		mouseHover = true;
	}

	public void onMouseExit(int mouseX, int mouseY) {
		mouseHover = false;
	}

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

	public void addButtonListener(ActionListener al) {
		buttonListener.add(al);
	}

	public void removeButtonListener(ActionListener al) {
		buttonListener.remove(al);
	}
	
	public void updateWhileInactive() {
		if (cooldown == 1)
			mouseClicked = false;
		if (cooldown > 0)
			cooldown--;
	}
}
