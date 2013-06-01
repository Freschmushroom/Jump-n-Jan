package at.jumpandjan.gui;

public class CompYesNoButton extends CompButton implements ActionListener {

	private boolean value;
	
	public CompYesNoButton(int x, int y) {
		this(x, y, 0);
	}

	public CompYesNoButton(int x, int y, int width) {
		this(x, y, width, 32);
	}
	
	public CompYesNoButton(int x, int y, int width, int height) {
		this(x, y, width, height, false);
	}
	
	public CompYesNoButton(int x, int y, boolean value) {
		this(x, y, 0, value);
	}

	public CompYesNoButton(int x, int y, int width, boolean value) {
		this(x, y, width, 32, value);
	}
	
	public CompYesNoButton(int x, int y, int width, int height, boolean value) {
		super(x, y, width, height, value ? "Yes" : "No");
		addButtonListener(this);
	}

	public void onClicked(CompButton source) {
		
	}

	public void onReleased(CompButton source) {
		
	}

	public void onPressed(CompButton source) {
		setValue(!getValue());
		System.out.println("Pressed");
	}
	
	public void setValue(boolean value) {
		this.value = value;
		setText(value ? "Yes" : "No");
	}
	
	public boolean getValue() {
		return value;
	}
}
