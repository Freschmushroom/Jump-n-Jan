package at.jumpandjan.gui;


public abstract class Component extends Gui implements Cloneable {
	private int x, y, width, height;

	public Component(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public final void paint() {
		drawComponent();
	}

	public final boolean fireKeyboardEvent(boolean eventKeyState, int eventKey,
			int mouseX, int mouseY) {
		return false;
	}

	public final boolean fireMouseEvent(boolean eventButtonState,
			int eventButton, int mouseX, int mouseY, int dX, int dY) {
		mouseY = 480 - mouseY;
		if (eventButton == -1) {
			if (mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y
					&& mouseY <= this.y + this.height) {
				onHovered(mouseX, mouseY);
			} else {
				onMouseExit(mouseX, mouseY);
			}
		} else {
			if (eventButtonState) {
				if (mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y
						&& mouseY <= this.y + this.height) {
					onClick(mouseX, mouseY, eventButton);
				}
			} else {
				onReleased(mouseX, mouseY, eventButton);
			}
		}

		return false;
	}

	public void onClick(int mouseX, int mouseY, int mouseButton) {

	}

	public void onReleased(int mouseX, int mouseY, int mouseButton) {

	}

	public void onHovered(int mouseX, int mouseY) {

	}
	
	public void onMouseExit(int mouseX, int mouseY) {
		
	}

	public void drawComponent() {
	}
	
	public Object copy() {
		try {
			return clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
