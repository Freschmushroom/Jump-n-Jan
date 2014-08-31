package at.jumpandjan;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import at.freschmushroom.Errorhandling;
import at.freschmushroom.Out;
import at.freschmushroom.ServiceProvider;
import at.freschmushroom.xml.XMLAttribute;
import at.freschmushroom.xml.XMLDeclaration;
import at.freschmushroom.xml.XMLFile;
import at.freschmushroom.xml.XMLNode;
import at.freschmushroom.xml.XMLTag;
import at.freschmushroom.xml.XMLWriter;

/**
 * Class that holds everything that didn't fit into any other class
 * 
 * @author Felix
 *
 */
public class Constants
{
	/**
	 * If the game is paused (the menu is open)
	 */
	private static boolean paused;
	/**
	 * If the game is still running, for any render loops
	 */
	private static boolean running;
	/**
	 * If music is running in the background
	 */
	private static boolean music;
	/**
	 * If the first party sequence should be shown
	 */
	private static boolean seq1;
	/**
	 * The name of the 'actualLevel'
	 */
	private static String levelName;
	/**
	 * The percentage of RAM used at the moment
	 */
	private static double ramPercentage;
	/**
	 * The number of available CPU cores
	 */
	private static int cores;
	/**
	 * The List of RAMListeners
	 */
	private static List<RAMListener> listeners;
	/**
	 * The start of the timer function
	 */
	private static long start = 0;
	/**
	 * The end of the timer function
	 */
	private static long end = 0;
	/**
	 * If a counting process is in operation
	 */
	private static boolean counting = false;
	/**
	 * The total time for rendering
	 */
	private static long rendering;
	/**
	 * The number of renders already performed
	 */
	private static int render;
	/**
	 * The total time for updates
	 */
	private static long updating;
	/**
	 * The number of updates already performed
	 */
	private static int update;
	/**
	 * WTF???
	 */
	private static boolean inUse;
	/**
	 * A list of all the update times (stats.txt)
	 */
	private static ArrayList<Long> updates = new ArrayList<Long>();
	/**
	 * A list of all the render times (stats.txt)
	 */
	private static ArrayList<Long> renders = new ArrayList<Long>();
	/**
	 * The name of the default level file
	 */
	private static String DEFAULT_LVL_FILE;
	/**
	 * The current user
	 */
	private static User CURRENT_USER;
	/**
	 * WTF???
	 */
	public static final java.util.Random random = new java.util.Random();

	/**
	 * Loads the Settings and fetches all the Runtime Information
	 */
	public static void load()
	{
		ServiceProvider.libs();
		paused = false;
		running = true;
		XMLFile f = new XMLFile(new File("settings.xml"));
		XMLNode r = (XMLNode) f.getRoot();
		XMLNode music = (XMLNode) r.getChild("music", false);
		XMLNode level = (XMLNode) r.getChild("level", false);
		DEFAULT_LVL_FILE = ((XMLAttribute) level.getChild("value", false)).getValue();
		XMLNode seq1 = (XMLNode) r.getChild("seq1", false);
		Constants.music = ((XMLAttribute) music.getChild("value", false)).getValue().equals("true");
		Constants.seq1 = ((XMLAttribute) seq1.getChild("value", false)).getValue().equals("true");
		Runtime rt = Runtime.getRuntime();
		rt.runFinalization();
		rt.gc();
		long total = rt.totalMemory();
		long free = rt.freeMemory();
		ramPercentage = ((total - free) / total);
		if (getRamPercentage() >= 0.9)
		{
			if (listeners != null)
			{
				for (RAMListener r1 : listeners)
				{
					r1.tooFewRAM(1.0 - getRamPercentage());
				}
			}
			if (getRamPercentage() < 0.95)
			{
				if (listeners != null)
				{
					for (RAMListener r1 : listeners)
					{
						r1.noFreeRAM(1.0 - getRamPercentage(), getRamPercentage() < 0.01);
					}
				}
			}
		}
		cores = (rt.availableProcessors());
	}

	/**
	 * Returns the name of the default level
	 * 
	 * @return the name of the default level
	 */
	public static String getDEFAULT_LVL_FILE()
	{
		return DEFAULT_LVL_FILE;
	}

	/**
	 * Sets the name of the default level
	 * 
	 * @param dEFAULT_LVL_FILE
	 *            the new name of the default level
	 */
	public static void setDEFAULT_LVL_FILE(String dEFAULT_LVL_FILE)
	{
		DEFAULT_LVL_FILE = dEFAULT_LVL_FILE;
	}

	/**
	 * Updates all the Runtime Information
	 */
	public static void update()
	{
		Runtime rt = Runtime.getRuntime();
		long total = rt.totalMemory();
		long free = rt.freeMemory();
		ramPercentage = ((total - free) / total);
		if (getRamPercentage() >= 0.9)
		{
			rt.runFinalization();
			rt.gc();
			if (listeners != null)
			{
				for (RAMListener r1 : listeners)
				{
					r1.tooFewRAM(1.0 - getRamPercentage());
				}
			}
			if (getRamPercentage() < 0.95)
			{
				if (listeners != null)
				{
					for (RAMListener r1 : listeners)
					{
						r1.noFreeRAM(1.0 - getRamPercentage(), getRamPercentage() < 0.01);
					}
				}
			}
		}
		cores = (rt.availableProcessors());
	}

	/**
	 * Writes the settings to se file
	 */
	public static void writeToFile()
	{
		paused = true;
		XMLFile f = new XMLFile(new File("settings.xml"));
		f.clearFile();
		XMLDeclaration dec = new XMLDeclaration();
		f.setDeclaration(dec);
		XMLNode root = new XMLNode(null, "Settings");
		new XMLTag(root, "music", music ? "true" : "false");
		new XMLTag(root, "level", levelName);
		new XMLTag(root, "seq1", seq1 + "");
		XMLWriter w = new XMLWriter(f);
		w.writeToFile();
		w.writeToTerminal();
	}

	/**
	 * 
	 * Interface to identify a Class to be a RAMListener
	 * 
	 * @author Felix
	 *
	 */
	public interface RAMListener
	{
		/**
		 * Called whenever there is less than 10 % percent RAM available
		 * 
		 * @param used
		 *            the amount of RAM in use
		 */
		public abstract void tooFewRAM(double used);

		/**
		 * Called whenever there is less than 5 % percent RAM available
		 * 
		 * @param used
		 *            the amount of RAM in use
		 * @param critical
		 *            if less than 2% are available
		 */
		public abstract void noFreeRAM(double used, boolean critical);
	}

	/**
	 * Returns if the game is paused
	 * 
	 * @return if the game is paused
	 */
	public static boolean isPaused()
	{
		return paused;
	}

	/**
	 * Sets if the game is paused
	 * 
	 * @param paused
	 *            the new state of the game
	 */
	public static void setPaused(boolean paused)
	{
		Constants.paused = paused;
	}

	/**
	 * Returns if the game is running
	 * 
	 * @return if the game is running
	 */
	public static boolean isRunning()
	{
		return running;
	}

	/**
	 * Sets if the game is running
	 * 
	 * @param running
	 *            the new state of the game
	 */
	public static void setRunning(boolean running)
	{
		Constants.running = running;
	}

	/**
	 * Returns if the music should be played or not
	 * 
	 * @return if the music should be played or not
	 */
	public static boolean isMusic()
	{
		return music;
	}

	/**
	 * Sets if the music should be played or not
	 * 
	 * @param music
	 *            if the music should be played or not
	 */
	public static void setMusic(boolean music)
	{
		Constants.music = music;
	}

	/**
	 * Returns if the party sequence should be played
	 * 
	 * @return if the party sequence should be played
	 */
	public static boolean isSeq1()
	{
		return seq1;
	}

	/**
	 * Sets if the party sequence should be played
	 * 
	 * @param seq1
	 *            if the party sequence should be played
	 */
	public static void setSeq1(boolean seq1)
	{
		Constants.seq1 = seq1;
	}

	/**
	 * Returns the used RAM percentage
	 * 
	 * @return the used RAM percentage
	 */
	public static double getRamPercentage()
	{
		return ramPercentage;
	}

	/**
	 * Returns the number of available cores
	 * 
	 * @return the number of available cores
	 */
	public static int getCores()
	{
		return cores;
	}

	/**
	 * Adds a RAM Listener to the EventQueing System
	 * 
	 * @param r
	 *            the new RAM Listener
	 */
	public static void addRAMListener(RAMListener r)
	{
		if (listeners == null)
			listeners = new ArrayList<RAMListener>();
		listeners.add(r);
	}

	/**
	 * Removes a RAM Listener from the EventQueing System
	 * 
	 * @param r
	 *            the RAM Listener
	 */
	public static void removeRAMListener(RAMListener r)
	{
		if (listeners == null)
			return;
		listeners.remove(r);
	}

	/**
	 * Starts the time counter
	 */
	public static void start()
	{
		start = System.nanoTime();
		end = 0;
		counting = true;
	}

	/**
	 * Stops the time counter
	 */
	public static void stop()
	{
		end = System.nanoTime();
		counting = false;
	}

	/**
	 * Returns the difference between start and end time
	 * 
	 * @return the difference between start and end time
	 */
	public static long count()
	{
		if (counting)
		{
			return 0;
		}
		else
		{
			return end - start;
		}
	}

	/**
	 * Starts a render time measurement
	 */
	public static void startRender()
	{
		if (!inUse)
		{
			start();
			inUse = true;
		}
		else
		{
			Out.err("Timing funtion already in use please quit first");
		}
	}

	/**
	 * Stops a render time measurement
	 */
	public static void stopRender()
	{
		if (inUse)
		{
			stop();
			render++;
			rendering += count();
			inUse = false;
			renders.add(count());
		}
	}

	/**
	 * Returns the total rendering time
	 * 
	 * @return the total rendering time
	 */
	public static double totalRender()
	{
		return rendering;
	}

	/**
	 * Returns the average render time
	 * 
	 * @return the average render time
	 */
	public static double avgRender()
	{
		if (render != 0)
			return rendering / render;
		return 0;
	}

	/**
	 * Starts an update time measurement
	 */
	public static void startUpdate()
	{
		if (!inUse)
		{
			start();
			inUse = true;

		}
		else
		{
			Out.err("Timing funtion already in use please quit first");
		}
	}

	/**
	 * Stops an update time measurement
	 */
	public static void stopUpdate()
	{
		if (inUse)
		{
			stop();
			update++;
			updating += count();
			inUse = false;
			updates.add(count());
		}
	}

	/**
	 * Returns the total updating time
	 * 
	 * @return the total updating time
	 */
	public static double totalUpdate()
	{
		return updating;
	}

	/**
	 * Returns the average update time
	 * 
	 * @return the average update time
	 */
	public static double avgUpdate()
	{
		if (update != 0)
			return updating / update;
		return 0;
	}

	/**
	 * Saves the render and update times to the stats.txt
	 */
	public static void showRenderUpdateTimes()
	{
		try
		{
			Out.line("Saving stats.txt");
			PrintStream f_out = new PrintStream(new FileOutputStream("stats.txt"));
			f_out.println("Update Times in ms");
			for (Long s : updates)
			{
				f_out.println(s);
			}
			f_out.println("Render Times in nanosecond");
			for (Long s : renders)
			{
				f_out.println(s);
			}
			f_out.close();
			Out.line("Saved stats.txt");
			f_out.close();
		} catch (IOException e)
		{
			Errorhandling.handle(e);
			Out.err("Failed to save stats.png");
		}
	}

	/**
	 * Returns the current User
	 * 
	 * @return the current User
	 */
	public static User getCURRENT_USER()
	{
		return CURRENT_USER;
	}

	/**
	 * Sets the current user
	 * 
	 * @param cURRENT_USER
	 *            the new current user
	 */
	public static void setCURRENT_USER(User cURRENT_USER)
	{
		CURRENT_USER = cURRENT_USER;
	}

	static
	{
		Out.inf(Constants.class, "22.10.12", "Felix", null);
	}
}
