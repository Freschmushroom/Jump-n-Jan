package at.jumpandjan.gui;

import at.freschmushroom.Out;
import at.jumpandjan.JumpAndJan;
import at.jumpandjan.User;

/**
 * The gui where you create a new user
 * @author Michael
 *
 */
public class GuiCreateUser extends Gui implements ActionListener {
	/**
	 * The username of the to creating user
	 */
	private CompTextField username;
	
	public GuiCreateUser() {
		username = new CompTextField(this, 10, 0, 400, 30, "zaboing");
		
		CompButton create = new CompButton(this, 450, 400, 80, 30, "Create");
		create.addButtonListener(this);
		
		CompButton cancel = new CompButton(this, 550, 400, 80, 30, "Cancel");
		cancel.addButtonListener(new CloseGuiListener());
		
		components.add(username);
		components.add(create);
		components.add(cancel);
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
			
		} else {
			JumpAndJan.closeCurrentGui();
			new User(username.getText());
		}
	}

	static {
		Out.inf(GuiCreateUser.class, "01.06.2013", "Michael", null);
	}
}
