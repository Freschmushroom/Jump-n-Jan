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

public class Out {
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
	public static void line(String msg) {
		String p = String.format(Locale.US,
				"%1$ta %1$tb %1$td %1$tT %1$tZ %1$tY INFO:- ",
				new GregorianCalendar(TimeZone.getDefault(), Locale.US));
		p += msg;
		System.out.println(printAll ? p : msg);
		if (line != null)
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
	public static void inf(Class<?> c, String version, String author, String[] els) {
		if (printAll) {
			System.out.println(c.toString() + ":");
			System.out.println("	" + "Version: " + version);
			System.out.println("	" + "Auhor: " + author);
		}
		if (inf != null) {
			inf.println(c.toString() + ":");
			inf.println("	" + "Version: " + version);
			inf.println("	" + "Auhor: " + author);
		}
		if (els != null) {
			for (String s : els) {
				if (printAll)
					System.out.println("	" + s);
				if (inf != null)
					inf.println("	" + s);
			}
		}

		versions.put(c, version);
	}

	/**
	 * Prints the message to the error Console and to err.log
	 * 
	 * @param msg
	 *            the message
	 */
	public static void err(String msg) {
		String p = String.format(Locale.US,
				"%1$ta %1$tb %1$td %1$tT %1$tZ %1$tY ERROR:- ",
				new GregorianCalendar(TimeZone.getDefault(), Locale.US));
		p += msg;
		System.err.println(printAll ? p : msg);
		if (err != null)
			err.println(p);
	}

	/**
	 * Initializes all Streams and starts all record keeping processes, do not
	 * change or delete
	 */
	static {
		Out.inf(Out.class, "27.09.12", "Felix", null);
		try {
			File li = new File("line.log");
			li.createNewFile();
			line = new PrintStream(new FileOutputStream(li));
			File in = new File("inf.log");
			in.createNewFile();
			inf = new PrintStream(new FileOutputStream(in));
			File er = new File("err.log");
			er.createNewFile();
			err = new PrintStream(new FileOutputStream(er));
		} catch (FileNotFoundException e) {
			Errorhandling.handle(e);
			err("Fatal error: Unable to initialize Console. r u sure i can write here?! ");
		} catch (IOException e) {
			Errorhandling.handle(e);
			err("Fatal error: Unable to initialize Console. r u sure i can write here?! ");
		}
	}

	/**
	 * Destroys all record keeping processes. Last call in every program
	 */
	public static void destroy() {
		line("Destroying Console");
		try {
			line.flush();
			line.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		line = null;
		try {
			inf.flush();
			inf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		inf = null;
		err("Canceling all record keeping processes");
		try {
			err.flush();
			err.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		err = null;
	}

	public static boolean checkVersion(HashMap<Class<?>, String> versions) {
		return Out.versions.equals(versions);
	}

	public static boolean reportMissingFile(String filename) {
		if (!missingFiles.contains(filename)) {
			missingFiles.add(filename);
			return true;
		}
		return false;
	}

	public static void printAllVersions() {
		if (!printAll)
			return;
		Set<Class<?>> keys = versions.keySet();
		Collection<String> values = versions.values();
		Iterator<Class<?>> i = keys.iterator();
		Iterator<String> j = values.iterator();
		while (i.hasNext()) {
			String txt = String.format("%1$-50s %2$8s", i.next()
					.getCanonicalName(), j.next());
			Out.line(txt);
		}
	}

	public static void printAllMissingFiles() {
		Iterator<String> i = missingFiles.iterator();
		if (missingFiles.size() == 0 || !printAll)
			return;
		Out.err("Following Files are missing on your System. Please send us these Information");
		while (i.hasNext()) {
			Out.err(i.next());
		}
	}

	public static void saveErrorInfo(Throwable t) throws FileNotFoundException {
		File f = new File(System.getProperty("user.dir") + "/errors/");
		if (!f.exists())
			f.mkdir();
		PrintStream f_out = new PrintStream(
				new FileOutputStream(f.getAbsolutePath() + "/"
						+ System.currentTimeMillis() + ".log"));
		t.printStackTrace(f_out);
		Set<Class<?>> keys = versions.keySet();
		Collection<String> values = versions.values();
		Iterator<Class<?>> i = keys.iterator();
		Iterator<String> j = values.iterator();
		while (i.hasNext()) {
			String txt = String.format("%1$-50s %2$8s", i.next()
					.getCanonicalName(), j.next());
			f_out.println(txt);
		}
		Iterator<String> m = missingFiles.iterator();
		while (m.hasNext()) {
			f_out.println("Missing file:" + m.next());
		}
		f_out.close();
	}

	public static void surpressAdditionalInformation() {
		printAll = false;
	}

	public static void forceAdditionalInformation() {
		printAll = true;
	}
}
