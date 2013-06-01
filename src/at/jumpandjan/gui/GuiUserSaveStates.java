package at.jumpandjan.gui;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import at.jumpandjan.Constants;
import at.jumpandjan.JumpAndJan;
import at.jumpandjan.User;

public class GuiUserSaveStates extends Gui {
	private CompLabel title;
	private CompButton addUser;
	private CompButton back;

	private ArrayList<User> users = new ArrayList<User>();

	public GuiUserSaveStates() {
		title = new CompLabel(0, 0, 0, 0, "Choose User");
		title.autoDesign(320, 10);
		title.color = "000000";
		
		addUser = new CompButton(0, 0, 150, 40, "Create User");
		addUser.setCenter(100, Constants.getCameraHeight() - 150);
		addUser.addButtonListener(new UserAddListener());

		back = new CompButton(0, 0, 150, 40, "<-- Back");
		back.setCenter(100, Constants.getCameraHeight() - 75);
		back.addButtonListener(new CloseGuiListener());

		components.add(title);
		components.add(addUser);
		components.add(back);

		initUsers();

		int y = 50;

		for (User user : users) {
			CompButton button = new CompButton(100, y, 200, 40, user.getName());
			button.addButtonListener(new UserButtonListener(user));
			components.add(button);
			y += button.getHeight() + 10;
		}
	}

	public void initUsers() {
		File[] userFiles = new File("saves/").listFiles();
		if (userFiles == null)
			return;
		for (File f : userFiles) {
			if (f.isDirectory() || !f.getName().endsWith(".user")) {
				continue;
			}
			try {
				ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)));
				
				User user = (User) ois.readObject();
				users.add(user);
				
				ois.close();
			} catch (Exception e) {
				System.err.println(f.getName() + " could not be read.");
			}
		}
	}

	class UserButtonListener implements ActionListener {
		private User user;

		public UserButtonListener(User user) {
			this.user = user;
		}

		public void onClicked(CompButton source) {
		}

		public void onReleased(CompButton source) {
		}

		public void onPressed(CompButton source) {
			Constants.setCURRENT_USER(user);
			JumpAndJan.openGui(new GuiLevelChooser());
		}
	}

	class UserAddListener implements ActionListener {

		public void onClicked(CompButton source) {
		}

		public void onReleased(CompButton source) {
		}

		public void onPressed(CompButton source) {
			// Display.destroy();
			// Mouse.destroy();
			// Keyboard.destroy();
			// JumpAndJan.parent.setVisible(false);
			// System.out.println(JOptionPane.showInputDialog(JumpAndJan.parent,
			// "Please enter a username", null));
			// JumpAndJan.parent.setVisible(true);
		}
	}
}
