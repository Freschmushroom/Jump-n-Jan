package at.jumpandjan.gui;

import at.freschmushroom.Out;
import at.jumpandjan.JumpAndJan;
import at.jumpandjan.User;

/**
 * The gui where you create a new user
 * 
 * @author Michael
 * 
 */
public class GuiCreateUser extends Gui implements ActionListener {
	/**
	 * The username of the to creating user
	 */
	private CompTextField username;

	/**
	 * The message which shows when the username is taken
	 */
	private CompLabel errorMessage;
	
	@Override
	public void init() {
		components.clear();

		username = new CompTextField(this, 10, 100, 400, 30, "");
		errorMessage = new CompLabel(this, 10, 200, 400, 30, "This username is already taken.");
		errorMessage.color = "ff0000";
		errorMessage.visible = false;
		
		CompButton create = new CompButton(this, 450, 400, 80, 30, "Create");
		create.addButtonListener(this);

		CompButton cancel = new CompButton(this, 550, 400, 80, 30, "Cancel");
		cancel.addButtonListener(new CloseGuiListener());

		components.add(username);
		components.add(create);
		components.add(cancel);
		components.add(errorMessage);
	}

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
		if (User.getUserByName(username.getText()) != null) {
			errorMessage.visible = true;
		} else {
			JumpAndJan.closeCurrentGui();
			new User(username.getText());
			JumpAndJan.reloadGuis();
		}
	}

	static {
		Out.inf(GuiCreateUser.class, "01.06.2013", "Michael", null);
	}
}
