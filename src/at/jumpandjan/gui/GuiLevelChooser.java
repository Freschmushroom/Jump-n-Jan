package at.jumpandjan.gui;

import java.io.File;

import at.freschmushroom.Out;
import at.jumpandjan.Constants;
import at.jumpandjan.JumpAndJan;
import at.jumpandjan.User;
import at.jumpandjan.level.Level;
import at.jumpandjan.level.LevelBuilder;

/**
 * The Level Chooser
 * 
 * @author Michael
 * 
 */
public class GuiLevelChooser extends Gui {
	/**
	 * The backbutton
	 */
	private CompButton back;
	
	/**
	 * The label indicating the current score.
	 */
	private CompLabel score;

	public GuiLevelChooser() {
	}
	
	@Override
	public void init() {
		components.clear();

		back = new CompButton(this, 0, 0, 150, 40, "<-- Back");
		back.setCenter(100, Constants.getCameraHeight() - 75);
		back.addButtonListener(new CloseGuiListener());

		score = new CompLabel(this, 0, 0, 150, 40, "");
		score.autoDesign(320, 400);
		int currentScore = 0;
		for (String s : Constants.getCURRENT_USER().getPlayedLevels()) {
			currentScore += Constants.getCURRENT_USER().getScore(s);
			System.out.println(s);
		}
		score.color = "000000";
		score.setText("Overall score: " + currentScore);
		
		CompButton deleteUser = new CompButton(this, 0, 0, 150, 40, "Delete");
		deleteUser.setCenter(100, 480 - 150);
		deleteUser.addButtonListener(new ActionListener() {

			@Override
			public void onClicked(CompButton source) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onReleased(CompButton source) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPressed(CompButton source) {
				Constants.getCURRENT_USER().delete();
				Constants.setCURRENT_USER(null);
				JumpAndJan.reloadGuis();
			}
			
		});
		deleteUser.addButtonListener(new CloseGuiListener());

		components.add(deleteUser);
		components.add(score);
		components.add(back);

		initLevels();
	}

	/**
	 * Loads all levels
	 */
	private void initLevels() {
		File[] levels = new File("level/").listFiles();
		if (levels == null || levels.length == 0)
			return;
		int y = 50;
		for (File f : levels) {
			if (f.isDirectory() || !f.getName().endsWith(".xml")) {
				continue;
			}
			try {
				User user = Constants.getCURRENT_USER();
				Level level = new Level(LevelBuilder.load(f.getAbsolutePath()));
				if (user.isUnlocked(level.getName())) {
					CompButton button = new CompButton(this, 100, y, 200, 40,
							level.getName());
					button.addButtonListener(new LevelLoadListener(level));
					components.add(button);
					y += button.getHeight() + 10;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	class LevelLoadListener implements ActionListener {
		private Level level;

		public LevelLoadListener(Level level) {
			this.level = level;
		}

		public void onClicked(CompButton source) {
		}

		public void onReleased(CompButton source) {
		}

		public void onPressed(CompButton source) {
			JumpAndJan.closeAllGuis();
			Constants.setActualLevel(level);
		}
	}

	static {
		Out.inf(GuiLevelChooser.class, "01.06.2013", "Michael", null);
	}
}
