package at.jumpandjan.gui;

import org.lwjgl.input.Keyboard;

import utils.TrueTypeFont;

import at.jumpandjan.Constants;
import at.jumpandjan.JumpAndJan;
import at.jumpandjan.level.Level;
import at.jumpandjan.level.LevelBuilder;

public class GuiMainMenu extends Gui {
	
	private CompButton loadGame, newGame, options, quit;
	
	public GuiMainMenu() {
		loadGame = new CompButton(0, 0, 400, 40, "Load Game").setCenter(
				Constants.getCameraWidth() / 2, 100);
		newGame = new CompButton(0, 0, 400, 40, "New Game").setCenter(
				Constants.getCameraWidth() / 2, loadGame.getY()
				+ (int) (loadGame.getHeight() * 2));
		options = new CompButton(0, 0, 400, 40, "Options").setCenter(
				Constants.getCameraWidth() / 2, newGame.getY()
				+ (int) (newGame.getHeight() * 2));
		quit = new CompButton(0, 0, 400, 40, "Quit Game").setCenter(
				Constants.getCameraWidth() / 2, options.getY()
						+ (int) (options.getHeight() * 2));
		
		loadGame.addButtonListener(new ActionListener() {

			public void onClicked(CompButton source) {

			}

			public void onReleased(CompButton source) {

			}

			public void onPressed(CompButton source) {
				JumpAndJan.openGui(new GuiUserSaveStates());
			}
		});
		
		newGame.addButtonListener(new ActionListener() {
			
			public void onClicked(CompButton source) {

			}

			public void onReleased(CompButton source) {

			}

			public void onPressed(CompButton source) {
				JumpAndJan.closeAllGuis();
				Constants.setActualLevel(new Level(LevelBuilder.load(Constants.getDEFAULT_LVL_FILE())));
				System.err.println(Constants.getActualLevel().getPlayer().bounds.y);
			}
		});

		quit.addButtonListener(new GameQuitListener());

		components.add(loadGame);
		components.add(newGame);
		components.add(options);
		components.add(quit);
	}
	
	public boolean fireKeyboardEvent(boolean eventKeyState, int eventKey, int mouseX, int mouseY) {
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
		color(255, 0, 0, 128);
		begin(QUADS);
		
		addVertex(0, 0);
		addVertex(0, Constants.getCameraHeight());
		addVertex(Constants.getCameraWidth(), Constants.getCameraHeight());
		addVertex(Constants.getCameraWidth(), 0);
		
		end();
		popMatrix();
	}
	
	public void resized() {
		loadGame.setCenter(
				Constants.getCameraWidth() / 2, 100);
		newGame.setCenter(
				Constants.getCameraWidth() / 2, loadGame.getY()
				+ (int) (loadGame.getHeight() * 2));
		options.setCenter(
				Constants.getCameraWidth() / 2, newGame.getY()
				+ (int) (newGame.getHeight() * 2));
		quit.setCenter(
				Constants.getCameraWidth() / 2, options.getY()
				+ (int) (options.getHeight() * 2));
	}
}
