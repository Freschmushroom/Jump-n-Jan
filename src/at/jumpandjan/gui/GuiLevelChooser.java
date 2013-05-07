package at.jumpandjan.gui;

import java.io.File;

import at.jumpandjan.Constants;
import at.jumpandjan.JumpAndJan;
import at.jumpandjan.level.Level;
import at.jumpandjan.level.LevelBuilder;

public class GuiLevelChooser extends Gui {
	private CompButton back;

	public GuiLevelChooser() {
		back = new CompButton(0, 0, 150, 40, "<-- Back");
		back.setCenter(100, Constants.getCameraHeight() - 75);
		back.addButtonListener(new CloseGuiListener());
		
		components.add(back);
		
		initLevels();
	}

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
				Level level = new Level(LevelBuilder.load(f.getAbsolutePath()));
				if (Constants.getCURRENT_USER().isUnlocked(level.getName())) {
					CompButton button = new CompButton(100, y, 200,
							40, level.getName());
					button.addButtonListener(new LevelLoadListener(level));
					components.add(button);
					y += button.getHeight() + 10;
				} else {
					System.out.println(Constants.getCURRENT_USER().toString());
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
}
