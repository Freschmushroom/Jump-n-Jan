package at.freschmushroom;

import java.io.FileNotFoundException;

public class Errorhandling {
	// private static PrintStream out = null;
	/**
	 * Handles the given Throwable and prints it to the output destinations
	 * 
	 * @param t
	 *            the Throwable to be handled
	 */
	public static void handle(Throwable t) {
		if (t instanceof FileNotFoundException) {
			FileNotFoundException e = (FileNotFoundException) t;
			e.printStackTrace();
			int pathL = System.getProperty("user.dir").length();
			String name = e.getMessage().substring(pathL);
			name = name.split(" ")[0];
			Out.reportMissingFile(name);
		} else {
			try {
				Out.saveErrorInfo(t);
			} catch (FileNotFoundException e) {
				handle(e);
			}
			t.printStackTrace(Out.err);
			t.printStackTrace();
		}
	}

	/**
	 * Not longer used, use for later initialization Class List etc.
	 */
	static {
		Out.inf(Errorhandling.class, "06.11.12", "Felix", null);
	}
}
