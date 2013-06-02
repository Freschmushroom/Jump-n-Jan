package at.jumpandjan.gui;

import java.awt.Font;

import utils.TrueTypeFont;
import at.freschmushroom.Out;
import at.jumpandjan.Constants;

/**
 * Dead End
 * @author Michael
 *
 */
public class GuiGameOver extends Gui {
	/**
	 * COLORS OF THE RAINBOW
	 */
	private float r, g, b;
	/**
	 * The button to open the main menu
	 */
	private CompButton mainMenu;
	/**
	 * The button to open the level menu
	 */
	private CompButton levels;
	/**
	 * The button to quit
	 */
	private CompButton quit;
	/**
	 * The label with the message
	 */
	private CompLabel label;

	public GuiGameOver(float r, float g, float b, String message) {
		setBackground(r, g, b);
		label = new CompLabel(this, 0, 0, 0, 0, message).autoDesign(
				Constants.getCameraWidth() / 2, 100);
		mainMenu = new CompButton(this, 0, 0, 400, 40, "Go to Main Menu").setCenter(
				Constants.getCameraWidth() / 2, 250);
		levels = new CompButton(this, 0, 0, 200, 40, "Choose level").setCenter(
				Constants.getCameraWidth() / 3, 300);
		quit = new CompButton(this, 0, 0, 200, 40, "Quit Game").setCenter(
				2 * Constants.getCameraWidth() / 3, 300);

		label.setDrawBackground(false);
		TrueTypeFont font = new TrueTypeFont(new Font("monospaced", Font.PLAIN,
				40), true);
		label.setFont(font);
		String color = String.format("%02x%02x%02x", new Object[] {
				((int) (r * 255)), ((int) (g * 255)), ((int) (b * 255)) });
		label.color = color;
		System.out.println(color);

		mainMenu.addButtonListener(new OpenGuiListener(new GuiMainMenu()));
		levels.addButtonListener(new OpenGuiListener(new GuiMainMenu(), false));
		levels.addButtonListener(new OpenGuiListener(new GuiUserSaveStates(), false));
		levels.addButtonListener(new OpenGuiListener(new GuiLevelChooser(), false));

		quit.addButtonListener(new GameQuitListener());
		
		components.add(label);
		components.add(mainMenu);
		components.add(levels);
		components.add(quit);
	}

	/**
	 * Sets the background
	 */
	public void setBackground(int r, int g, int b) {
		setBackground(r / 255f, g / 255f, b / 255f);
	}

	/**
	 * Sets the background
	 */
	public void setBackground(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	@Override
	public void paint() {
		super.color(r, g, b);
		super.begin(Gui.QUADS);
		super.addVertex(0, 0);
		super.addVertex(0, 480);
		super.addVertex(640, 480);
		super.addVertex(640, 0);
		super.end();
	}
	
	static {
		Out.inf(GuiGameOver.class, "01.06.2013", "Michael", null);
	}
}
