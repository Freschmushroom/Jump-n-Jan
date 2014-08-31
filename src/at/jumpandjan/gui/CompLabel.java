package at.jumpandjan.gui;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

import java.awt.Font;

import utils.TrueTypeFont;
import at.freschmushroom.Out;

/**
 * Represents a label
 * 
 * @author Michael
 * 
 */
public class CompLabel extends Component
{

	/**
	 * The text which is displayed by this Label
	 */
	private String text;

	/**
	 * The font being used to render the text
	 */
	private TrueTypeFont font = new TrueTypeFont(new Font(Font.MONOSPACED, Font.BOLD, 20), false);

	/**
	 * The alignment
	 */
	private int align = TrueTypeFont.ALIGN_LEFT;

	/**
	 * Whether a fancy background should be drawn
	 */
	private boolean drawBackground;

	/**
	 * The color as a hexadecimal string (without the prefix 0x)
	 */
	public String color = "ffffff";

	public CompLabel(Gui parent, int x, int y, int width, int height, String text)
	{
		super(parent, x, y, width, height);
		setText(text);
	}

	/**
	 * Returns the text
	 * 
	 * @return The text
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * Sets the text
	 * 
	 * @param text
	 *            The text
	 */
	public void setText(String text)
	{
		this.text = text;
	}

	/**
	 * Returns whether the background should be drawn
	 * 
	 * @return Whether the background should be drawn
	 */
	public boolean drawBackground()
	{
		return drawBackground;
	}

	/**
	 * Sets whether the background should be drawn
	 * 
	 * @param drawBackground
	 *            whether the background should be drawn
	 */
	public void setDrawBackground(boolean drawBackground)
	{
		this.drawBackground = drawBackground;
	}

	@Override
	public void drawComponent()
	{
		if (drawBackground)
		{
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
		switch (align)
		{
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

		font.drawString(textX, 480 - font.getHeight(text) - textY, text, 1, 1, align);

		glMatrixMode(GL_PROJECTION);
		glPopMatrix();
		glMatrixMode(GL_MODELVIEW);
	}

	/**
	 * Sets the text alignment
	 * 
	 * @param align
	 *            The text alignment
	 * @return this
	 */
	public CompLabel setTextAlignment(int align)
	{
		this.align = align;
		return this;
	}

	/**
	 * Sets the font
	 * 
	 * @param font
	 *            the font
	 */
	public void setFont(TrueTypeFont font)
	{
		this.font = font;
	}

	/**
	 * Gets the font
	 * 
	 * @return The font
	 */
	public TrueTypeFont getFont()
	{
		return this.font;
	}

	/**
	 * Automatically locates the label at the specified horizontal center and
	 * centers the alignment Also packs the label
	 * 
	 * @param centerX
	 *            The horizontal center
	 * @param posY
	 *            The y-position
	 * @return This
	 */
	public CompLabel autoDesign(int centerX, int posY)
	{
		setTextAlignment(TrueTypeFont.ALIGN_CENTER);
		setWidth(font.getWidth(text));
		setHeight(font.getHeight(text));
		setX(centerX - getWidth() / 2);
		setY(posY);

		return this;
	}

	static
	{
		Out.inf(CompLabel.class, "01.06.2013", "Michael", null);
	}
}
