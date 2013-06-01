package at.jumpandjan.gui;

import org.lwjgl.opengl.GL11;

public class CompPanel extends Component {

	public CompPanel(int x, int y, int width, int height) {
		super(x, y, width, height);
		System.out.println(height);
	}

	public void add(Component component) {
		components.add(component);
		// component.inputClipX = this.inputClipX + component.getX();
		// component.inputClipY = this.inputClipY + component.getY();
		// component.inputClipWidth = component.getWidth();
		// component.inputClipHeight = component.getHeight();
	}

	public void drawComponent() {
		pushMatrix();
		clip(getX(), getY(), getWidth(), getHeight());
		bindTexture(null);
		color(200, 200, 200, 150);
		begin(QUADS);
		addVertex(getX(), getY());
		addVertex(getX(), getY() + getHeight());
		addVertex(getX() + getWidth(), getY() + getHeight());
		addVertex(getX() + getWidth(), getY());
		end();
		translate(getX(), getY());

		for (Component comp : components) {
			comp.render();
		}

		popMatrix();
	}

	public void onClick(int mouseX, int mouseY, int mouseButton) {
		if (mouseX < getX() || mouseX > getX() + getWidth() || mouseY < getY()
				|| mouseY > getY() + getHeight()) {
			return;
		}
		for (Component c : components) {
			if (mouseX >= c.getX() && mouseX <= c.getX() + c.getWidth()
					&& mouseY >= c.getY() && mouseY <= c.getY() + c.getHeight()) {
				c.onClick(mouseX, mouseY, mouseButton);
			}
		}
	}

	public void onReleased(int mouseX, int mouseY, int mouseButton) {
		if (mouseX < getX() || mouseX > getX() + getWidth() || mouseY < getY()
				|| mouseY > getY() + getHeight()) {
			return;
		}
		for (Component c : components) {
			if (mouseX >= c.getX() && mouseX <= c.getX() + c.getWidth()
					&& mouseY >= c.getY() && mouseY <= c.getY() + c.getHeight()) {
				c.onReleased(mouseX, mouseY, mouseButton);
			}
		}
	}

	public void onHovered(int mouseX, int mouseY) {
		if (mouseX < getX() || mouseX > getX() + getWidth() || mouseY < getY()
				|| mouseY > getY() + getHeight()) {
			return;
		}
		for (Component c : components) {
			if (mouseX >= c.getX() && mouseX <= c.getX() + c.getWidth()
					&& mouseY >= c.getY() && mouseY <= c.getY() + c.getHeight()) {
				c.onHovered(mouseX, mouseY);
			} else {
				c.onMouseExit(mouseX, mouseY);
			}
		}
	}
}
