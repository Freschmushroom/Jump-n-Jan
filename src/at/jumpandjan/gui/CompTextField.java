package at.jumpandjan.gui;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

import java.awt.Font;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import utils.TrueTypeFont;
import at.freschmushroom.Out;

/**
 * A text field
 * 
 * @author Michael
 * 
 */
public class CompTextField extends Component
{

	/**
	 * The text
	 */
	private String text;

	/**
	 * The font
	 */
	private TrueTypeFont font = new TrueTypeFont(new Font(Font.MONOSPACED, Font.BOLD, 20), false);

	/**
	 * Whether the even fancier background should be drawn
	 */
	private boolean drawBackground = true;

	/**
	 * The color; by default: white
	 */
	public String color = "ffffff";

	/**
	 * A count for the caret animation
	 */
	private int count;

	/**
	 * The position of the caret
	 */
	public int caret;

	/**
	 * When the textfield is smaller than the text to be displayed, charOffset
	 * is used to scroll around
	 */
	private int charOffset;

	/**
	 * The time when the key which is currently down was pressed
	 */
	private long repetitiveKeyTime;

	/**
	 * The key code of the key which is currently down
	 */
	private int repetitiveKeyCode;

	/**
	 * The character code of the key which is currently down
	 */
	private char repetitiveCharCode;

	public CompTextField(Gui parent, int x, int y, int width, int height, String text)
	{
		super(parent, x, y, width, height);
		setText(text);
	}

	/**
	 * Returns the text
	 * 
	 * @return the text
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * Sets the text
	 * 
	 * @param text
	 *            the text
	 */
	public void setText(String text)
	{
		this.text = text;
	}

	/**
	 * Whether the background should be drawn
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
	 *            Whether the background should be drawn
	 */
	public void setDrawBackground(boolean drawBackground)
	{
		this.drawBackground = drawBackground;
	}

	@Override
	public void drawComponent()
	{
		if (repetitiveKeyTime != -1 && System.currentTimeMillis() - repetitiveKeyTime > 500)
		{
			onKeyPressed(repetitiveKeyCode, repetitiveCharCode, Mouse.getX() / Display.getWidth() * 640, 480 - (Mouse.getY() / Display.getHeight()) / 480);
		}

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

		int textX = getX() + 10;
		int textY = getY() - getHeight() / 2 + font.getHeight(text) / 2;
		if (!text.isEmpty())
		{
			int length = maxCharsInTextField();
			if (text.length() - charOffset < length)
			{
				font.drawString(textX, 480 - font.getHeight(text) - textY, text, charOffset, text.length() - 1, 1, 1, TrueTypeFont.ALIGN_LEFT);
			}
			else
			{
				font.drawString(textX, 480 - font.getHeight(text) - textY, text, charOffset, length + charOffset - 2, 1, 1, TrueTypeFont.ALIGN_LEFT);
			}
		}

		if (isFocused && count >= 30)
		{

			bindTexture(null);
			begin(QUADS);

			addVertex((int) (textX + font.getActualWidth(caret - charOffset)) + 4, (int) (480 - font.getHeight(text) / 1.5f - textY) - 2);
			addVertex((int) (textX + font.getActualWidth(caret - charOffset)) + 4, (int) (480 - font.getHeight(text) / 1.5f - textY) - 4);
			addVertex((int) (textX + font.getActualWidth(caret + 1 - charOffset)) + 4, (int) (480 - font.getHeight(text) / 1.5f - textY) - 4);
			addVertex((int) (textX + font.getActualWidth(caret + 1 - charOffset)) + 4, (int) (480 - font.getHeight(text) / 1.5f - textY) - 2);

			end();

		}
		++count;
		if (count == 60 || !isFocused)
		{
			count = 0;
		}

		glMatrixMode(GL_PROJECTION);
		glPopMatrix();
		glMatrixMode(GL_MODELVIEW);
	}

	/**
	 * Sets the font
	 * 
	 * @param font
	 *            The font
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
	 * @see at.jumpandjan.gui.CompLabel.autoDesign
	 * @param centerX
	 * @param posY
	 * @return
	 */
	public CompTextField autoDesign(int centerX, int posY)
	{
		setWidth(font.getWidth(text));
		setHeight(font.getHeight(text));
		setX(centerX - getWidth() / 2);
		setY(posY);

		return this;
	}

	@Override
	public void onKeyPressed(int key, char character, int mouseX, int mouseY)
	{
		if (character >= '!' && character <= '~')
		{
			text = text.substring(0, caret) + character + text.substring(caret);
			caret++;
		}
		else if (key == Keyboard.KEY_LEFT)
		{
			count = 30;
			if (caret > 0)
			{
				caret--;
			}
		}
		else if (key == Keyboard.KEY_RIGHT)
		{
			count = 30;
			if (caret < text.length())
			{
				caret++;
			}
		}
		else if (key == Keyboard.KEY_BACK)
		{
			if (caret > 0)
			{
				caret--;
				text = text.substring(0, caret) + text.substring(caret + 1);
			}
		}
		else if (key == Keyboard.KEY_DELETE)
		{
			if (caret < text.length())
			{
				text = text.substring(0, caret) + text.substring(caret + 1);
			}
		}
		else if (key == Keyboard.KEY_HOME)
		{
			caret = 0;
			charOffset = 0;
		}
		else if (key == Keyboard.KEY_END)
		{
			caret = text.length();
			if (caret > maxCharsInTextField())
			{
				charOffset = text.length() - maxCharsInTextField();
			}
		}
		if (caret - charOffset == maxCharsInTextField())
		{
			charOffset++;
		}
		if (caret != 0 && caret == charOffset)
		{
			charOffset--;
		}
		if (repetitiveKeyCode != key)
		{
			repetitiveKeyCode = key;
			repetitiveCharCode = character;
			repetitiveKeyTime = System.currentTimeMillis();
		}
	}

	private int maxCharsInTextField()
	{
		return (getWidth() - 20) / font.getActualWidth(1);
	}

	@Override
	public void onKeyReleased(int key, char character, int mouseX, int mouseY)
	{
		if (key == repetitiveKeyCode)
		{
			repetitiveKeyCode = -1;
			repetitiveCharCode = '\0';
			repetitiveKeyTime = -1;
		}
	}

	static
	{
		Out.inf(CompTextField.class, "01.06.2013", "Michael", null);
	}
}
