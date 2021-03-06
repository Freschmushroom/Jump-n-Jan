package at.jumpandjan.gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import at.freschmushroom.Out;
import at.jumpandjan.Constants;
import at.jumpandjan.JumpAndJan;

/**
 * The ingame menu
 * 
 * @author Michael
 *
 */
public class GuiMenu extends Gui
{
	/**
	 * A button to the main menu
	 */
	private CompButton mainMenu;
	/**
	 * A button to save the game
	 */
	private CompButton saveGame;
	/**
	 * One button to quit them all
	 */
	private CompButton quit;
	/**
	 * A button to the options
	 */
	private CompButton options;

	private JumpAndJan game;

	public GuiMenu(JumpAndJan game)
	{
		this.game = game;
	}

	public void init()
	{
		components.clear();
		mainMenu = new CompButton(this, 0, 0, 400, 40, "Go to Main Menu").setCenter(Display.getWidth() / 2, 100);
		saveGame = new CompButton(this, 0, 0, 400, 40, "Save User").setCenter(Display.getWidth() / 2, mainMenu.getY() + (int) (mainMenu.getHeight() * 2));
		options = new CompButton(this, 0, 0, 400, 40, "Options").setCenter(Display.getWidth() / 2, saveGame.getY() + (int) (saveGame.getHeight() * 2));
		quit = new CompButton(this, 0, 0, 400, 40, "Quit Game").setCenter(Display.getWidth() / 2, options.getY() + (int) (options.getHeight() * 2));

		mainMenu.addButtonListener(new OpenGuiListener(new GuiMainMenu(game)));
		mainMenu.addButtonListener(new ActionListener() {

			public void onClicked(CompButton source)
			{
				// TODO Auto-generated method stub

			}

			public void onReleased(CompButton source)
			{
				// TODO Auto-generated method stub

			}

			public void onPressed(CompButton source)
			{
				game.level = null;
			}

		});

		saveGame.addButtonListener(new CloseGuiListener());
		saveGame.addButtonListener(new ActionListener() {

			public void onClicked(CompButton source)
			{
			}

			public void onReleased(CompButton source)
			{
			}

			public void onPressed(CompButton source)
			{
				Constants.getCURRENT_USER().save();
			}

		});

		options.addButtonListener(new OpenGuiListener(new GuiOptions(), false));

		quit.addButtonListener(new GameQuitListener());

		components.add(mainMenu);
		components.add(saveGame);
		components.add(options);
		components.add(quit);
	}

	@Override
	public boolean fireKeyboardEvent(boolean eventKeyState, int eventKey, char eventChar, int mouseX, int mouseY)
	{
		if (!super.fireKeyboardEvent(eventKeyState, eventKey, eventChar, mouseX, mouseY))
		{
			if (eventKeyState && eventKey == Keyboard.KEY_ESCAPE)
			{
				JumpAndJan.closeCurrentGui();
				return true;
			}
			return false;
		}
		return true;
	}

	@Override
	public void paint()
	{
		pushMatrix();
		loadIdentity();
		color(64, 64, 64, 128);
		begin(QUADS);

		addVertex(0, 0);
		addVertex(0, Display.getHeight());
		addVertex(Display.getWidth(), Display.getHeight());
		addVertex(Display.getWidth(), 0);

		end();
		popMatrix();
	}

	static
	{
		Out.inf(GuiMenu.class, "01.06.2013", "Michael", null);
	}
}
