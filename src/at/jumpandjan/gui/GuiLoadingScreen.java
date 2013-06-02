package at.jumpandjan.gui;

import at.freschmushroom.Out;
import at.freschmushroom.audio.SoundContainer;
import at.jumpandjan.Constants;
import at.jumpandjan.JumpAndJan;

/**
 * The loading screen shown at the beginning
 * @author Michael
 *
 */
public class GuiLoadingScreen extends Gui {
	/**
	 * The progress bar for the sound loading
	 */
	private CompProgressBar progressBar;
	/**
	 * The label indicating the currently loading sound
	 */
	private CompLabel soundLabel;
	/**
	 * The name of the currently loading sound
	 */
	private String soundName = "";

	public GuiLoadingScreen() {
		progressBar = new CompProgressBar(this,
				(Constants.getCameraWidth() - 400) / 2,
				(Constants.getCameraHeight() - 20) / 2, 400, 20);
		soundLabel = new CompLabel(this,
				(Constants.getCameraWidth() - 400) / 2,
				(Constants.getCameraHeight() - 20) / 2 - 75, 400, 20,
				"Loading sounds...");
		soundLabel.color = "000000";
		soundLabel.setText("Waiting for system sounds to load...");

		String user_dir = System.getProperty("user.dir");
		SoundContainer.mainLoadingThread.loadSound(user_dir + "/sound/main.ogg",
				"OGG", "game_music");
		SoundContainer.mainLoadingThread.loadSound(user_dir + "/sound/gameOver.ogg",
				"OGG", "game_over");
		SoundContainer.mainLoadingThread.loadSound(user_dir
				+ "/sound/opp_psy_attack.ogg", "OGG", "opp_psy_attack");
		SoundContainer.mainLoadingThread.loadSound(user_dir + "/sound/party.ogg",
				"OGG", "party_music");
		SoundContainer.mainLoadingThread.loadSound(user_dir + "/sound/explosion.ogg",
				"OGG", "explosion");

		progressBar.max = SoundContainer.mainLoadingThread.elementsInQueue();

		components.add(progressBar);
		components.add(soundLabel);
	}

	@Override
	public void paint() {
		if (!soundName.equals(SoundContainer.mainLoadingThread.currentSound)) {
			soundName = SoundContainer.mainLoadingThread.currentSound;
			soundLabel.setText("Loading " + soundName + " . . .");
			progressBar.value++;
		}
		if (progressBar.value == progressBar.max
				|| SoundContainer.mainLoadingThread.elementsInQueue() == 0) {
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
	
	static {
		Out.inf(GuiLoadingScreen.class, "01.06.2013", "Michael", null);
	}
}
