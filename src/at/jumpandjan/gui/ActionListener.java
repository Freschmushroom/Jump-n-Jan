package at.jumpandjan.gui;

/**
 * 
 * @author Michael
 * Implements ActionListeners for buttons
 */
public interface ActionListener {
	/**
	 * Invoked when a button is clicked
	 * @param source The button which caused the event
	 */
	public void onClicked(CompButton source);
	
	/**
	 * Invoked when a button is released
	 * @param source The button which caused the event
	 */
	public void onReleased(CompButton source);
	
	/**
	 * Invoked when a button is pressed, meaning between clicked and released
	 * @param source The button which caused the event
	 */
	public void onPressed(CompButton source);
}
