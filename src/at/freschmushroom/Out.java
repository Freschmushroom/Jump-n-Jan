package at.freschmushroom;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

public class Out
{
	/**
	 * PrintStream for normal output
	 */
	private static PrintStream line;
	/**
	 * Debuginfo, just classnames, versions etc.
	 */
	private static PrintStream inf;
	/**
	 * Errorstream, really just for errors
	 */
	public static PrintStream err;
	/**
	 * saves the versions of the Element to check if all classes are in the
	 * correct version
	 */
	private static HashMap<Class<?>, String> versions = new HashMap<Class<?>, String>();
	/**
	 * Saves the current not existing files on the systems
	 */
	private static ArrayList<String> missingFiles = new ArrayList<String>();
	/**
	 * if true additional information is printed
	 */
	private static boolean printAll = true;

	/**
	 * Prints the msg to the console and writes it to line.log
	 * 
	 * @param msg
	 *            the message to be printed
	 */
	public static void line(String msg)
	{
		String p = String.format(Locale.US, "%1$ta %1$tb %1$td %1$tT %1$tZ %1$tY INFO:- ", new GregorianCalendar(TimeZone.getDefault(), Locale.US)); // Create
																																						// epic
																																						// time
																																						// format
		p += msg; // Create message string
		System.out.println(printAll ? p : msg); // If all should be printed
												// print the message with the
												// time, else just the message
		if (line != null) // Write it to the file if possible
			line.println(p);
	}

	/**
	 * Prints the full name of the class, version, author and comments to the
	 * console and to inf.log if els is null nothing else is printed
	 * 
	 * @param c
	 *            the class
	 * @param version
	 *            the version
	 * @param author
	 *            the author
	 * @param els
	 *            other things ("REMOVE METHOD SOUNDSO")
	 */
	public static void inf(Class<?> c, String version, String author, String[] els)
	{
		if (printAll)
		{ // If everything should be printed
			System.out.println(c.toString() + ":"); // Print class name
			System.out.println("	" + "Version: " + version); // Print version
			System.out.println("	" + "Auhor: " + author); // Print author
		}
		if (inf != null)
		{ // If the file is available
			inf.println(c.toString() + ":");
			inf.println("	" + "Version: " + version);
			inf.println("	" + "Auhor: " + author);
		}
		if (els != null)
		{ // Print other comments
			for (String s : els)
			{
				if (printAll)
					System.out.println("	" + s);
				if (inf != null)
					inf.println("	" + s);
			}
		}

		versions.put(c, version); // Store information into the version control
									// map
	}

	/**
	 * Prints the message to the error Console and to err.log
	 * 
	 * @param msg
	 *            the message
	 */
	public static void err(String msg)
	{ // See line()
		String p = String.format(Locale.US, "%1$ta %1$tb %1$td %1$tT %1$tZ %1$tY ERROR:- ", new GregorianCalendar(TimeZone.getDefault(), Locale.US));
		p += msg;
		System.err.println(printAll ? p : msg);
		if (err != null)
			err.println(p);
	}

	/**
	 * Initializes all Streams and starts all record keeping processes, do not
	 * change or delete
	 */
	static
	{
		Out.inf(Out.class, "27.09.12", "Felix", null);
		try
		{
			File li = new File("line.log");
			li.createNewFile();
			line = new PrintStream(new FileOutputStream(li));
			File in = new File("inf.log");
			in.createNewFile();
			inf = new PrintStream(new FileOutputStream(in));
			File er = new File("err.log");
			er.createNewFile();
			err = new PrintStream(new FileOutputStream(er));
		} catch (FileNotFoundException e)
		{
			Errorhandling.handle(e);
			err("Fatal error: Unable to initialize Console. r u sure i can write here?! ");
		} catch (IOException e)
		{
			Errorhandling.handle(e);
			err("Fatal error: Unable to initialize Console. r u sure i can write here?! ");
		}
	}

	/**
	 * Destroys all record keeping processes. Last call in every program
	 */
	public static void destroy()
	{
		line("Destroying Console");
		try
		{
			line.flush();
			line.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		line = null;
		try
		{
			inf.flush();
			inf.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		inf = null;
		err("Canceling all record keeping processes");
		try
		{
			err.flush();
			err.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		err = null;
	}

	/**
	 * Compares two version controlling maps
	 * 
	 * @param versions
	 *            the version map of another instance
	 * @return if the versions are equal
	 */
	public static boolean checkVersion(HashMap<Class<?>, String> versions)
	{
		return Out.versions.equals(versions);
	}

	/**
	 * Called from Errorhandling Used to state later which files are missing
	 * 
	 * @param filename
	 *            The name of the file
	 * @return if the file was previously reported missing
	 */
	public static boolean reportMissingFile(String filename)
	{
		if (!missingFiles.contains(filename))
		{
			missingFiles.add(filename);
			return true;
		}
		return false;
	}

	/**
	 * Prints basically all the version on the console, only of those classes
	 * who reported them
	 */
	public static void printAllVersions()
	{
		if (!printAll)
			return;
		Set<Class<?>> keys = versions.keySet();
		Collection<String> values = versions.values();
		Iterator<Class<?>> i = keys.iterator();
		Iterator<String> j = values.iterator();
		while (i.hasNext())
		{
			String txt = String.format("%1$-50s %2$8s", i.next().getCanonicalName(), j.next());
			Out.line(txt);
		}
	}

	/**
	 * Prints the List of missing files
	 */
	public static void printAllMissingFiles()
	{
		Iterator<String> i = missingFiles.iterator();
		if (missingFiles.size() == 0 || !printAll)
			return;
		Out.err("Following Files are missing on your System. Please send us these Information");
		while (i.hasNext())
		{
			Out.err(i.next());
		}
	}

	/**
	 * Creates an error report based on a Throwable
	 * 
	 * including missing files and all the Throwable information
	 * 
	 * @param t
	 *            the error causing throwable
	 * @throws FileNotFoundException
	 *             if the neede file cannot be found
	 */
	public static void saveErrorInfo(Throwable t) throws FileNotFoundException
	{
		File f = new File(System.getProperty("user.dir") + "/errors/");
		if (!f.exists())
			f.mkdir();
		PrintStream f_out = new PrintStream(new FileOutputStream(f.getAbsolutePath() + "/" + System.currentTimeMillis() + ".log"));
		t.printStackTrace(f_out);
		Set<Class<?>> keys = versions.keySet();
		Collection<String> values = versions.values();
		Iterator<Class<?>> i = keys.iterator();
		Iterator<String> j = values.iterator();
		while (i.hasNext())
		{
			String txt = String.format("%1$-50s %2$8s", i.next().getCanonicalName(), j.next());
			f_out.println(txt);
		}
		Iterator<String> m = missingFiles.iterator();
		while (m.hasNext())
		{
			f_out.println("Missing file:" + m.next());
		}
		f_out.close();
	}

	/**
	 * Suppresses the output of the date and unnesecary information on the
	 * console
	 */
	public static void surpressAdditionalInformation()
	{
		printAll = false;
	}

	/**
	 * Forces the output of the date and additional information on the console
	 */
	public static void forceAdditionalInformation()
	{
		printAll = true;
	}
}
