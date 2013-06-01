package at.jumpandjan.gui;

import org.lwjgl.input.Keyboard;

import at.freschmushroom.audio.SoundContainer;
import at.jumpandjan.Constants;
import at.jumpandjan.JumpAndJan;

public class GuiLoadingScreen extends Gui {

	private CompProgressBar progressBar;
	private CompLabel soundLabel;
	private String soundName = "";

	public GuiLoadingScreen() {
		progressBar = new CompProgressBar(
				(Constants.getCameraWidth() - 400) / 2,
				(Constants.getCameraHeight() - 20) / 2, 400, 20);
		soundLabel = new CompLabel((Constants.getCameraWidth() - 400) / 2,
				(Constants.getCameraHeight() - 20) / 2 - 75, 400, 20,
				"Loading sounds...");
		soundLabel.color = "000000";
		soundLabel.setText("Waiting for system sounds to load...");

		String user_dir = System.getProperty("user.dir");
		SoundContainer.mainLoadingThread.loadSound(user_dir + "/main.ogg",
				"OGG", "game_music");
		SoundContainer.mainLoadingThread.loadSound(user_dir + "/gameOver.ogg",
				"OGG", "game_over");
		SoundContainer.mainLoadingThread.loadSound(user_dir
				+ "/opp_psy_attack.ogg", "OGG", "opp_psy_attack");
		SoundContainer.mainLoadingThread.loadSound(user_dir + "/party.ogg",
				"OGG", "party_music");
		SoundContainer.mainLoadingThread.loadSound(user_dir + "/explosion.ogg",
				"OGG", "explosion");
		
		progressBar.max = SoundContainer.mainLoadingThread.elementsInQueue();

		components.add(progressBar);
		components.add(soundLabel);
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
		if (!soundName
						.equals(SoundContainer.mainLoadingThread.currentSound)) {
			soundName = SoundContainer.mainLoadingThread.currentSound;
			soundLabel.setText("Loading " + soundName + " . . .");
			progressBar.value++;
		}
		if (progressBar.value == progressBar.max || SoundContainer.mainLoadingThread.elementsInQueue() == 0) {
			JumpAndJan.closeAllGuis();
			JumpAndJan.openGui(new GuiMainMenu());
			return;
		}

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
}
