package at.jumpandjan.gui;

import java.awt.Font;

import utils.TrueTypeFont;
import at.jumpandjan.Constants;

public class GuiGameOver extends Gui {
	private float r, g, b;
	private CompButton mainMenu;
	private CompButton levels;
	private CompButton quit;
	private CompLabel label;

	public GuiGameOver(float r, float g, float b, String message) {
		setBackground(r, g, b);
		label = new CompLabel(0, 0, 0, 0, "[color=0,0,0]" + message
				+ "[/color]").autoDesign(Constants.getCameraWidth() / 2, 100);
		mainMenu = new CompButton(0, 0, 400, 40, "Go to Main Menu").setCenter(
				Constants.getCameraWidth() / 2, 250);
		levels = new CompButton(0, 0, 200, 40, "Choose level").setCenter(
				Constants.getCameraWidth() / 3, 300);
		quit = new CompButton(0, 0, 200, 40, "Quit Game").setCenter(
				2 * Constants.getCameraWidth() / 3, 300);

		label.setDrawBackground(false);
		TrueTypeFont font = new TrueTypeFont(new Font("monospaced", Font.PLAIN,
				40), true);
		label.setFont(font);

		mainMenu.addButtonListener(new OpenGuiListener(new GuiMainMenu()));
		levels.addButtonListener(new OpenGuiListener(new GuiLevelChooser()));

		components.add(label);
		components.add(mainMenu);
		components.add(levels);
		components.add(quit);
	}

	public void setBackground(int r, int g, int b) {
		setBackground(r / 255f, g / 255f, b / 255f);
	}

	public void setBackground(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public void paint() {
		super.color(r, g, b);
		super.begin(Gui.QUADS);
		super.addVertex(0, 0);
		super.addVertex(0, 480);
		super.addVertex(640, 480);
		super.addVertex(640, 0);
		super.end();
	}
}
