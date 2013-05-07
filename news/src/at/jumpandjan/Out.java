package at.jumpandjan;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JOptionPane;

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
	 * Prints the msg to the console and writes it to line.log
	 * @param msg the message to be printed
	 */
	public static void line(String msg) {
		System.out.println(msg);
		if(line != null)
			line.println(msg);
	}
	/**
	 * Prints the full name of the class, version, author and comments to the console and to inf.log
	 * if els is null nothing else is printed
	 * @param c the class
	 * @param version the version
	 * @param author the author
	 * @param els other things ("REMOVE METHOD SOUNDSO")
	 */
	public static void inf(Class c, String version, String author, String[] els) {
		System.out.println(c.toString() + ":");
		System.out.println("	" + "Version: " + version);
		System.out.println("	" + "Auhor: " + author);
		if(inf != null) {
			inf.println(c.toString() + ":");
			inf.println("	" + "Version: " + version);
			inf.println("	" + "Auhor: " + author);
		}
		if(els != null) {
			for(String s : els) {
				System.out.println("	" + s);
				if(inf != null)
					inf.println("	" + s);
			}
		}
	}
	/**
	 * Prints the message to the error Console and to err.log
	 * @param msg the message
	 */
	public static void err(String msg) {
		System.err.println(msg);
		if(err != null)
			err.println(msg);
	}
	/**
	 * Initializes all Streams and starts all record keeping processes, do not change or delete
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
		//Remove this as soon as all described in the box is done
		//JOptionPane.showMessageDialog(null, "Bitte alle System.out durch Out.line und printStackTrace nur noch mit \r\n Out.err ausführen!!!", "TODO", JOptionPane.INFORMATION_MESSAGE);
	}
	/**
	 * Destroys all record keeping processes. Last call in every program
	 */
	public static void destroy() {
		line("Destroying Console");
		line.flush();
		line.close();
		line = null;
		inf.flush();
		inf.close();
		inf = null;
		err("Canceling all record keeping processes");
		err.flush();
		err.close();
		err = null;
	}
}
