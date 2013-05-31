package at.jumpandjan.gui;

import org.lwjgl.opengl.GL11;

public class CompProgressBar extends Component {
	public int value = 1;
	public int max = 100;
	
	public CompProgressBar(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	public void drawComponent() {
		color("000000");
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		begin(QUADS);
		
		addVertex(getX(), getY());
		addVertex(getX(), getY() + getHeight());
		addVertex(getX() + 1, getY() + getHeight());
		addVertex(getX() + 1, getY());
		
		addVertex(getX(), getY());
		addVertex(getX(), getY() + 1);
		addVertex(getX() + getWidth(), getY() + 1);
		addVertex(getX() + getWidth(), getY());
		
		addVertex(getX(), getY() + getHeight() - 1);
		addVertex(getX(), getY() + getHeight());
		addVertex(getX() + getWidth(), getY() + getHeight());
		addVertex(getX() + getWidth(), getY() + getHeight() - 1);
		
		addVertex(getX() + getWidth() - 1, getY());
		addVertex(getX() + getWidth() - 1, getY() + getHeight());
		addVertex(getX() + getWidth(), getY() + getHeight());
		addVertex(getX() + getWidth(), getY());
		
		end();
		
		color(0, 255, 0);
		
		begin(QUADS);
		
		addVertex(getX() + 1, getY() + 1);
		addVertex(getX() + 1, getY() + getHeight() - 1);
		addVertex(getX() + (int) (value / (float) max * (getWidth() - 1)), getY() + getHeight() - 1);
		addVertex(getX() + (int) (value / (float) max * (getWidth() - 1)), getY() + 1);
		
		end();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
}
