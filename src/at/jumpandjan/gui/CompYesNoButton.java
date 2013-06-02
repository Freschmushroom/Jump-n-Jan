package at.jumpandjan.gui;

import at.freschmushroom.Out;

/**
 * A button which can only differentiate between Yes (true) or No (false)
 * @author Michael
 *
 */
public class CompYesNoButton extends CompButton implements ActionListener {

	/**
	 * The value
	 */
	private boolean value;
	
	public CompYesNoButton(Gui parent, int x, int y) {
		this(parent, x, y, 0);
	}

	public CompYesNoButton(Gui parent, int x, int y, int width) {
		this(parent, x, y, width, 32);
	}
	
	public CompYesNoButton(Gui parent, int x, int y, int width, int height) {
		this(parent, x, y, width, height, false);
	}
	
	public CompYesNoButton(Gui parent, int x, int y, boolean value) {
		this(parent, x, y, 0, value);
	}

	public CompYesNoButton(Gui parent, int x, int y, int width, boolean value) {
		this(parent, x, y, width, 32, value);
	}
	
	public CompYesNoButton(Gui parent, int x, int y, int width, int height, boolean value) {
		super(parent, x, y, width, height, value ? "Yes" : "No");
		addButtonListener(this);
	}

	@Override
	public void onClicked(CompButton source) {
		
	}

	@Override
	public void onReleased(CompButton source) {
		
	}

	@Override
	public void onPressed(CompButton source) {
		setValue(!getValue());
		System.out.println("Pressed");
	}
	
	/**
	 * Sets the value of this button
	 * @param value The value
	 */
	public void setValue(boolean value) {
		this.value = value;
		setText(value ? "Yes" : "No");
	}
	
	/**
	 * Gets the value of this button
	 * @return The value
	 */
	public boolean getValue() {
		return value;
	}
	
	static {
		Out.inf(CompYesNoButton.class, "01.06.2013", "Michael", null);
	}
}
