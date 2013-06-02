package at.jumpandjan.gui;

import at.freschmushroom.Out;
import at.jumpandjan.Constants;
import at.jumpandjan.JumpAndJan;
import at.jumpandjan.level.Level;
import at.jumpandjan.level.LevelBuilder;

/**
 * Big MacMenu
 * @author Michael
 *
 */
public class GuiMainMenu extends Gui {
	
	/**
	 * The buttons
	 */
	private CompButton loadGame, newGame, options, quit;
	
	public GuiMainMenu() {
		loadGame = new CompButton(this, 0, 0, 400, 40, "Load Game").setCenter(
				Constants.getCameraWidth() / 2, 100);
		newGame = new CompButton(this, 0, 0, 400, 40, "New Game").setCenter(
				Constants.getCameraWidth() / 2, loadGame.getY()
				+ (int) (loadGame.getHeight() * 2));
		options = new CompButton(this, 0, 0, 400, 40, "Options").setCenter(
				Constants.getCameraWidth() / 2, newGame.getY()
				+ (int) (newGame.getHeight() * 2));
		quit = new CompButton(this, 0, 0, 400, 40, "Quit Game").setCenter(
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

		options.addButtonListener(new OpenGuiListener(new GuiOptions(), false));
		
		quit.addButtonListener(new GameQuitListener());

		components.add(loadGame);
		components.add(newGame);
		components.add(options);
		components.add(quit);
	}
	
	@Override
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
	
	static {
		Out.inf(GuiMainMenu.class, "01.06.2013", "Michael", null);
	}
}
