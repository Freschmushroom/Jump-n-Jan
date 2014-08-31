package at.jumpandjan.gui;

import org.lwjgl.opengl.Display;

import at.freschmushroom.Out;
import at.jumpandjan.JumpAndJan;
import at.zaboing.StringStorage;

/**
 * Big MacMenu
 * 
 * @author Michael
 *
 */
public class GuiMainMenu extends Gui
{

	/**
	 * The buttons
	 */
	private CompButton loadGame, options, quit;

	public GuiMainMenu(final JumpAndJan game)
	{
		loadGame = new CompButton(this, 0, 0, 400, 40, StringStorage.getString("gui.main.play.text")).setCenter(Display.getWidth() / 2, 100 + 80);
		options = new CompButton(this, 0, 0, 400, 40, StringStorage.getString("gui.main.settings.text")).setCenter(Display.getWidth() / 2, loadGame.getY() + (int) (loadGame.getHeight() * 2));
		quit = new CompButton(this, 0, 0, 400, 40, StringStorage.getString("gui.main.quit.text")).setCenter(Display.getWidth() / 2, options.getY() + (int) (options.getHeight() * 2));

		loadGame.addButtonListener(new ActionListener() {

			public void onClicked(CompButton source)
			{

			}

			public void onReleased(CompButton source)
			{

			}

			public void onPressed(CompButton source)
			{
				JumpAndJan.openGui(new GuiUserSaveStates(game));
			}
		});

		options.addButtonListener(new OpenGuiListener(new GuiOptions(), false));

		quit.addButtonListener(new GameQuitListener());

		components.add(loadGame);
		components.add(options);
		components.add(quit);
	}

	@Override
	public void paint()
	{
		pushMatrix();
		loadIdentity();
		color(255, 0, 0, 128);
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
		Out.inf(GuiMainMenu.class, "01.06.2013", "Michael", null);
	}
}
