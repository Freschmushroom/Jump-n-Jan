package at.jumpandjan.gui;

import org.lwjgl.input.Keyboard;

import at.jumpandjan.Constants;
import at.jumpandjan.JumpAndJan;
import at.jumpandjan.User;

public class GuiMenu extends Gui {
	private CompButton mainMenu;
	private CompButton saveGame;
	private CompButton quit;
	private CompButton options;

	public GuiMenu() {
		mainMenu = new CompButton(0, 0, 400, 40, "Go to Main Menu").setCenter(
				Constants.getCameraWidth() / 2, 100);
		saveGame = new CompButton(0, 0, 400, 40, "Save Game").setCenter(
				Constants.getCameraWidth() / 2, mainMenu.getY()
						+ (int) (mainMenu.getHeight() * 2));
		options = new CompButton(0, 0, 400, 40, "Options").setCenter(
				Constants.getCameraWidth() / 2, saveGame.getY()
						+ (int) (saveGame.getHeight() * 2));
		quit = new CompButton(0, 0, 400, 40, "Quit Game").setCenter(
				Constants.getCameraWidth() / 2, options.getY()
						+ (int) (options.getHeight() * 2));

		mainMenu.addButtonListener(new OpenGuiListener(new GuiMainMenu()));

		saveGame.addButtonListener(new ActionListener() {

			public void onClicked(CompButton source) {
			}

			public void onReleased(CompButton source) {
			}

			public void onPressed(CompButton source) {
				User u = new User("zaboing");
				u.finishedLvl(Constants.getActualLevel(), Constants
						.getActualLevel().getPlayer().getPoints());
				u.save();
			}

		});

		quit.addButtonListener(new GameQuitListener());

		components.add(mainMenu);
		components.add(saveGame);
		components.add(options);
		components.add(quit);
	}

	public boolean fireKeyboardEvent(boolean eventKeyState, int eventKey,
			int mouseX, int mouseY) {
		if (!super.fireKeyboardEvent(eventKeyState, eventKey, mouseX, mouseY)) {
			if (eventKeyState && eventKey == Keyboard.KEY_ESCAPE) {
				JumpAndJan.closeCurrentGui();
				return true;
			}
			return false;
		}
		return true;
	}

	public void paint() {
		pushMatrix();
		loadIdentity();
		color(64, 64, 64, 128);
		begin(QUADS);

		addVertex(0, 0);
		addVertex(0, Constants.getCameraHeight());
		addVertex(Constants.getCameraWidth(), Constants.getCameraHeight());
		addVertex(Constants.getCameraWidth(), 0);

		end();
		popMatrix();
	}
}
