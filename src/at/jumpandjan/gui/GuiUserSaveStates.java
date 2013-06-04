package at.jumpandjan.gui;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import at.freschmushroom.Out;
import at.jumpandjan.Constants;
import at.jumpandjan.JumpAndJan;
import at.jumpandjan.User;

/**
 * A choice of all user save states
 * 
 * @author Michael
 * 
 */
public class GuiUserSaveStates extends Gui {
	/**
	 * The title
	 */
	private CompLabel title;
	/**
	 * The "Create user" button
	 */
	private CompButton addUser;
	/**
	 * A 0815 back button
	 */
	private CompButton back;

	/**
	 * All buffered users
	 */
	private ArrayList<User> users = new ArrayList<User>();

	@Override
	public void init() {
		components.clear();
		users.clear();

		title = new CompLabel(this, 0, 0, 0, 0, "Choose User");
		title.autoDesign(320, 10);
		title.color = "000000";

		addUser = new CompButton(this, 0, 0, 150, 40, "Create User");
		addUser.setCenter(100, Constants.getCameraHeight() - 150);
		addUser.addButtonListener(new OpenGuiListener(new GuiCreateUser(),
				false));

		back = new CompButton(this, 0, 0, 150, 40, "<-- Back");
		back.setCenter(100, Constants.getCameraHeight() - 75);
		back.addButtonListener(new CloseGuiListener());

		components.add(title);
		components.add(addUser);
		components.add(back);

		initUsers();


		for (int i = 0; i < users.size(); i++) {
			int x;
			if (i % 2 == 0) {
				x = 100;
			} else {
				x = 340;
			}
			CompButton button = new CompButton(this, x,
					i / 2 * 50 + 50, 200, 40, users.get(i).getName());
			button.addButtonListener(new UserButtonListener(users.get(i)));
			components.add(button);
		}
	}

	/**
	 * Loads and buffers the users
	 */
	public void initUsers() {
		File[] userFiles = new File("saves/").listFiles();
		if (userFiles == null)
			return;
		for (File f : userFiles) {
			if (f.isDirectory() || !f.getName().endsWith(".user")) {
				continue;
			}
			try {
				ObjectInputStream ois = new ObjectInputStream(
						new BufferedInputStream(new FileInputStream(f)));

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

	static {
		Out.inf(GuiUserSaveStates.class, "01.06.2013", "Michael", null);
	}
}
