package at.jumpandjan.gui;

import java.awt.Font;

import static org.lwjgl.opengl.GL11.*;

import utils.TrueTypeFont;

public class CompLabel extends Component {

	private String text;
	private TrueTypeFont font = new TrueTypeFont(new Font(Font.MONOSPACED,
			Font.BOLD, 20), false);
	private int align = TrueTypeFont.ALIGN_LEFT;
	private boolean drawBackground;
	public String color = "ffffff";

	public CompLabel(int x, int y, int width, int height, String text) {
		super(x, y, width, height);
		setText(text);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public boolean drawBackground() {
		return drawBackground;
	}
	
	public void setDrawBackground(boolean drawBackground) {
		this.drawBackground = drawBackground;
	}

	public void drawComponent() {
		if (drawBackground) {
			color(150, 150, 150);
			bindTexture("/button.png");
			begin(QUADS);

			addVertexUV(getX(), getY(), 0, 0);
			addVertexUV(getX(), getY() + getHeight(), 0, 32);
			addVertexUV(getX() + getWidth(), getY() + getHeight(), 256, 32);
			addVertexUV(getX() + getWidth(), getY(), 256, 0);

			end();
		}
		color("0x" + color);

		glMatrixMode(GL_PROJECTION);
		glPushMatrix();
		glLoadIdentity();
		glOrtho(0, 640, 0, 480, 1, -1);
		glMatrixMode(GL_MODELVIEW);

		int textX;
		int textY = getY() - getHeight() / 2 + font.getHeight(text) / 2;
		switch (align) {
		case TrueTypeFont.ALIGN_CENTER:
			textX = getX() + getWidth() / 2;
			break;
		case TrueTypeFont.ALIGN_RIGHT:
			textX = getX() + getWidth();
			break;
		case TrueTypeFont.ALIGN_LEFT:
		default:
			textX = getX();
		}

		font.drawString(textX, 480 - font.getHeight(text) - textY, text, 1, 1,
				align);

		glMatrixMode(GL_PROJECTION);
		glPopMatrix();
		glMatrixMode(GL_MODELVIEW);
	}

	public CompLabel setTextAlignment(int align) {
		this.align = align;
		return this;
	}

	public void setFont(TrueTypeFont font) {
		this.font = font;
	}

	public TrueTypeFont getFont() {
		return this.font;
	}

	public CompLabel autoDesign(int centerX, int posY) {
		setTextAlignment(TrueTypeFont.ALIGN_LEFT);
		setWidth(font.getWidth(text));
		setHeight(font.getHeight(text));
		setX(centerX - getWidth() / 2);
		setY(posY);
		
		return this;
	}
}
