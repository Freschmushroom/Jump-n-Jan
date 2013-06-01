package at.jumpandjan;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;

import at.freschmushroom.Errorhandling;
import at.freschmushroom.Out;
import at.freschmushroom.ServiceProvider;
import at.freschmushroom.xml.XMLAttribut;
import at.freschmushroom.xml.XMLDeclaration;
import at.freschmushroom.xml.XMLFile;
import at.freschmushroom.xml.XMLNode;
import at.freschmushroom.xml.XMLTag;
import at.freschmushroom.xml.XMLWriter;
import at.jumpandjan.level.Level;

public class Constants {
	public static final Rectangle CAMERA_BOUNDS;
	private static boolean paused;
	private static boolean running;
	private static boolean music;
	private static boolean seq1;
	private static Level actualLevel;
	private static String levelName;
	private static double ramPercentage;
	private static int cores;
	private static List<RAMListener> listeners;
	private static long start = 0;
	private static long end = 0;
	private static boolean counting = false;
	private static long rendering;
	private static int render;
	private static long updating;
	private static int update;
	private static boolean inUse;
	private static ArrayList<Long> updates = new ArrayList<Long>();
	private static ArrayList<Long> renders = new ArrayList<Long>();
	private static String DEFAULT_LVL_FILE;
	private static User CURRENT_USER;
	public static final java.util.Random random = new java.util.Random();

	public static void load() {
		ServiceProvider.libs();
		if (Display.isCreated()) {
			CAMERA_BOUNDS.width = Display.getWidth();
			CAMERA_BOUNDS.height = Display.getHeight();
		}
		paused = false;
		running = true;
		XMLFile f = new XMLFile(new File("settings.xml"));
		XMLNode r = (XMLNode) f.getRoot();
		XMLNode music = (XMLNode) r.getChild("music", false);
		XMLNode level = (XMLNode) r.getChild("level", false);
		DEFAULT_LVL_FILE = ((XMLAttribut) level.getChild("value", false)).getValue();
		XMLNode seq1 = (XMLNode) r.getChild("seq1", false);
		Constants.music = ((XMLAttribut) music.getChild("value", false)).getValue().equals("true");
		// actualLevel = new Level(
		// LevelBuilder.load(Constants.levelName = levelName.getValue()));
		Constants.seq1 = ((XMLAttribut) seq1.getChild("value", false)).getValue().equals("true");
		Runtime rt = Runtime.getRuntime();
		rt.runFinalization();
		rt.gc();
		long total = rt.totalMemory();
		long free = rt.freeMemory();
		setRamPercentage((total - free) / total);
		if (getRamPercentage() >= 0.9) {
			if (listeners != null) {
				for (RAMListener r1 : listeners) {
					r1.tooFewRAM(1.0 - getRamPercentage());
				}
			}
			if (getRamPercentage() < 0.95) {
				if (listeners != null) {
					for (RAMListener r1 : listeners) {
						r1.noFreeRAM(1.0 - getRamPercentage(),
								getRamPercentage() < 0.01);
					}
				}
			}
		}
		setCores(rt.availableProcessors());
	}


	public static String getDEFAULT_LVL_FILE() {
		return DEFAULT_LVL_FILE;
	}

	public static void setDEFAULT_LVL_FILE(String dEFAULT_LVL_FILE) {
		DEFAULT_LVL_FILE = dEFAULT_LVL_FILE;
	}

	public static void update() {
		if (Display.isCreated()) {
			CAMERA_BOUNDS.width = Display.getWidth();
			CAMERA_BOUNDS.height = Display.getHeight();
		}
		Runtime rt = Runtime.getRuntime();
		long total = rt.totalMemory();
		long free = rt.freeMemory();
		setRamPercentage((total - free) / total);
		if (getRamPercentage() >= 0.9) {
			rt.runFinalization();
			rt.gc();
			if (listeners != null) {
				for (RAMListener r1 : listeners) {
					r1.tooFewRAM(1.0 - getRamPercentage());
				}
			}
			if (getRamPercentage() < 0.95) {
				if (listeners != null) {
					for (RAMListener r1 : listeners) {
						r1.noFreeRAM(1.0 - getRamPercentage(),
								getRamPercentage() < 0.01);
					}
				}
			}
		}
		setCores(rt.availableProcessors());
	}

	public static void writeToFile() {
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

	public interface RAMListener {
		public abstract void tooFewRAM(double used);

		public abstract void noFreeRAM(double used, boolean critical);
	}

	public static boolean isPaused() {
		return paused;
	}

	public static void setPaused(boolean paused) {
		Constants.paused = paused;
	}

	public static boolean isRunning() {
		return running;
	}

	public static void setRunning(boolean running) {
		Constants.running = running;
	}

	public static boolean isMusic() {
		return music;
	}

	public static void setMusic(boolean music) {
		Constants.music = music;
	}

	public static Level getActualLevel() {
		return actualLevel;
	}

	public static void setActualLevel(Level actualLevel) {
		Constants.actualLevel = actualLevel;
	}

	public static int getCameraWidth() {
		return CAMERA_BOUNDS.width;
	}

	public static int getCameraHeight() {
		return CAMERA_BOUNDS.height;
	}

	public static boolean isSeq1() {
		return seq1;
	}

	public static void setSeq1(boolean seq1) {
		Constants.seq1 = seq1;
	}

	public static double getRamPercentage() {
		return ramPercentage;
	}

	public static void setRamPercentage(double ramPercentage) {
		Constants.ramPercentage = ramPercentage;
	}

	public static int getCores() {
		return cores;
	}

	public static void setCores(int cores) {
		Constants.cores = cores;
	}

	public static void addRAMListener(RAMListener r) {
		if (listeners == null)
			listeners = new ArrayList<RAMListener>();
		listeners.add(r);
	}

	public static void removeRAMListener(RAMListener r) {
		if (listeners == null)
			return;
		listeners.remove(r);
	}

	public static void start() {
		start = System.nanoTime();
		end = 0;
		counting = true;
	}

	public static void stop() {
		end = System.nanoTime();
		counting = false;
	}

	public static long count() {
		if (counting) {
			return 0;
		} else {
			return end - start;
		}
	}

	public static void startRender() {
		if (!inUse) {
			start();
			inUse = true;
		} else {
			Out.err("Timing funtion already in use please quit first");
		}
	}

	public static void stopRender() {
		if (inUse) {
			stop();
			render++;
			rendering += count();
			inUse = false;
			renders.add(count());
		}
	}

	public static double totalRender() {
		return rendering;
	}

	public static double avgRender() {
		if (render != 0)
			return rendering / render;
		return 0;
	}

	public static void startUpdate() {
		if (!inUse) {
			start();
			inUse = true;

		} else {
			Out.err("Timing funtion already in use please quit first");
		}
	}

	public static void stopUpdate() {
		if (inUse) {
			stop();
			update++;
			updating += count();
			inUse = false;
			updates.add(count());
		}
	}

	public static double totalUpdate() {
		return updating;
	}

	public static double avgUpdate() {
		if (update != 0)
			return updating / update;
		return 0;
	}

	public static void showRenderUpdateTimes() {
		try {
			Out.line("Saving stats.txt");
			PrintStream f_out = new PrintStream(new FileOutputStream(
					"stats.txt"));
			f_out.println("Update Times in ms");
			for (Long s : updates) {
				f_out.println(s);
			}
			f_out.println("Render Times in nanosecond");
			for (Long s : renders) {
				f_out.println(s);
			}
			f_out.close();
			Out.line("Saved stats.txt");
			f_out.close();
		} catch (IOException e) {
			Errorhandling.handle(e);
			Out.err("Failed to save stats.png");
		}
	}

	public static int getCameraX() {
		return CAMERA_BOUNDS.x;
	}

	public static void setCameraX(int cameraX) {
		CAMERA_BOUNDS.x = cameraX;
	}

	public static int getCameraY() {
		return CAMERA_BOUNDS.y;
	}

	public static void setCameraY(int cameraY) {
		CAMERA_BOUNDS.y = cameraY;
		;
	}

	public static User getCURRENT_USER() {
		return CURRENT_USER;
	}

	public static void setCURRENT_USER(User cURRENT_USER) {
		CURRENT_USER = cURRENT_USER;
	}

	static {
		Out.inf(Constants.class, "22.10.12", "Felix", null);
		CAMERA_BOUNDS = new Rectangle();
	}
}
