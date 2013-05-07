package at.jumpandjan;

import static org.lwjgl.opengl.GL11.*;
import at.freschmushroom.TextureManager;
import at.jumpandjan.level.Level;
import at.freschmushroom.Out;

public class Floor extends Object {
	public Floor(double x, double y, double width, double height, Level level) {
		super(x, y, width, height, level);
		// TODO Auto-generated constructor stub
	}

	public void render() {
		glEnable(GL_TEXTURE_2D);
		TextureManager.instance.bindTexture(TextureManager.instance
				.getTexture("/Wall_M.png"));
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2d(this.bounds.x + 32, this.bounds.y);
		glTexCoord2f(0, 1);
		glVertex2d(this.bounds.x + 32, this.bounds.y + this.bounds.height);
		glTexCoord2f(1, 1);
		glVertex2d(this.bounds.x + this.bounds.width - 32, this.bounds.y
				+ this.bounds.height);
		glTexCoord2f(1, 0);
		glVertex2d(this.bounds.x + this.bounds.width - 32, this.bounds.y);
		glEnd();
		TextureManager.instance.bindTexture(TextureManager.instance
				.getTexture("/Wall_e.png"));
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2d(this.bounds.x, this.bounds.y);
		glTexCoord2f(0, 1);
		glVertex2d(this.bounds.x, this.bounds.y + this.bounds.height);
		glTexCoord2f(1, 1);
		glVertex2d(this.bounds.x + 32, this.bounds.y + this.bounds.height);
		glTexCoord2f(1, 0);
		glVertex2d(this.bounds.x + 32, this.bounds.y);
		glTexCoord2f(1, 0);
		glVertex2d(this.bounds.x + this.bounds.width - 32, this.bounds.y);
		glTexCoord2f(1, 1);
		glVertex2d(this.bounds.x + this.bounds.width - 32, this.bounds.y
				+ this.bounds.height);
		glTexCoord2f(0, 1);
		glVertex2d(this.bounds.x + this.bounds.width, this.bounds.y
				+ this.bounds.height);
		glTexCoord2f(0, 0);
		glVertex2d(this.bounds.x + this.bounds.width, this.bounds.y);
		glEnd();
		glDisable(GL_TEXTURE_2D);
	}

	public void renderIcon() {
		render("/Wall_M.png", bounds.width, 10, 0, bounds.height / 2 - 5, 32,
				32, 32, 32, false);
	}

	public double getDefaultHeight() {
		return 10;
	}

	public boolean hasDefaultWidth() {
		return false;
	}

	public boolean hasDefaultHeight() {
		return true;
	}

	static {
		Out.inf(Floor.class, "23.10.12", "Felix", null);
	}
}
