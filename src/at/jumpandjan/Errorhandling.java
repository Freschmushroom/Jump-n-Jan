package at.jumpandjan;

import java.io.FileNotFoundException;

public class Errorhandling
{
	// private static PrintStream out = null;
	/**
	 * Handles the given Throwable and prints it to the output destinations
	 * 
	 * @param t
	 *            the Throwable to be handled
	 */
	public static void handle(Throwable t)
	{
		if (t instanceof FileNotFoundException)
		{
			FileNotFoundException e = (FileNotFoundException) t;
			e.printStackTrace(Out.err);
		}
		else
		{
			t.printStackTrace(Out.err);
		}
	}

	/**
	 * Not longer used, use for later initialization Class List etc.
	 */
	static
	{
		Out.inf(Errorhandling.class, "28.08.12", "Felix", null);
		/*
		 * try { out = new PrintStream(new FileOutputStream("errorlog.log")); }
		 * catch (FileNotFoundException e) { e.printStackTrace(Out.err); }
		 */
	}
}
