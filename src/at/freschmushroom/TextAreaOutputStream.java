package at.freschmushroom;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;

/**
 * Basically an output stream which writes to a JTextArea
 * @author Michael
 *
 */
public class TextAreaOutputStream extends OutputStream {
	/**
	 * The text area
	 */
	private JTextArea textArea;
	
	public TextAreaOutputStream(JTextArea textArea) {
		setTextArea(textArea);
	}
	
	/**
	 * Gets the text area
	 * @return the text area
	 */
	public JTextArea getTextArea() {
		return textArea;
	}
	
	/**
	 * Sets the text area
	 * @param textArea the text area
	 */
	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}
	
	@Override
	public void write(int b) throws IOException {
		textArea.append("" + (char) b);
	}

}
