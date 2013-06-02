package at.freschmushroom;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glViewport;

import java.awt.Canvas;
import java.awt.Font;
import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import utils.TrueTypeFont;

import at.jumpandjan.Constants;
/**
 * 
 * Class formerly used to startup the GL Thing and show an Intro
 * 
 * @author Felix
 *
 */
public class Intro {
	/**
	 * The vertical difference between the 640*480 0,0 and the used 0,0
	 */
	public static int vertMvmt = 0;
	/**
	 * The horizontal difference between the 640*480 0,0 and the used 0,0
	 */
	public static int horiMvmt = 0;
	/**
	 * If the Display is already created
	 */
	public static boolean init = false;
	/**
	 * Finds the largest possible DisplayMode available
	 * @return the largest DisplayMode
	 * @throws LWJGLException if no DisplayMode was found
	 */
	public static DisplayMode getLargest() throws LWJGLException {
		DisplayMode[] modes = Display.getAvailableDisplayModes();
		int index = 0;
		long value = 0;
		for (int i = 0; i < modes.length; i++) {
			DisplayMode m = modes[i];
			if (value < m.getHeight() * m.getWidth() * m.getFrequency()) {
				index = i;
				value = m.getHeight() * m.getWidth() * m.getFrequency();
			}
		}
		Out.line(Display.getDisplayMode().getWidth() + "x"
				+ Display.getDisplayMode().getHeight() + "x"
				+ Display.getDisplayMode().getFrequency());
		Out.line(modes[index].getWidth() + "x" + modes[index].getHeight() + "x"
				+ modes[index].getFrequency());
		vertMvmt = (modes[index].getWidth() - 640) / 2;
		horiMvmt = modes[index].getHeight() - 480;
		return modes[index];
	}
	/**
	 * Shows the Intro in FullScreenMode
	 * 
	 * 
	 * @throws LWJGLException if FullScreen makes problems
	 */
	public static void showIntroFullScreen() throws LWJGLException {
		Display.setTitle("FreshMushroom");
		Display.setDisplayModeAndFullscreen(getLargest());
		Display.create();
		renderIntro();
	}
	/**
	 * Shows the Intro in a Window 
	 * 
	 * @throws LWJGLException if showing the Intro in a window is Problematic
	 */
	public static void showIntroWindowed() throws LWJGLException {
		Display.setTitle("FreshMushroom");
		Display.setDisplayMode(new DisplayMode(640, 480));
		Display.create();
		Display.setVSyncEnabled(true);
		renderIntro();
	}
	/**
	 * Shows the Intro into a custom Canvas
	 * 
	 * @param parent the canvas the Intro should be shown on
	 * @throws LWJGLException if you shall not show your Intro in this Canvas
	 */
	public static void showIntroInControl(Canvas parent) throws LWJGLException {
		Display.setTitle("FreshMushroom");
		Display.setDisplayMode(Display.getAvailableDisplayModes()[0]);
		Display.create();
		Display.setVSyncEnabled(true);
		Display.setParent(parent);
		renderIntro();
	}
	/**
	 * Renders the Intro
	 * 
	 * @throws LWJGLException if the Intro fails
	 */
	public static void renderIntro() throws LWJGLException {
		init = true;
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getDisplayMode().getWidth(), Display
				.getDisplayMode().getHeight(), 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		glViewport(0, 0, Display.getDisplayMode().getWidth(), Display
				.getDisplayMode().getHeight());
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		Display.setTitle("Jump'n'Jan");
		Constants.update();
		Out.line("Done showing Intro");
		try {
			renderFresch();
		} catch (IOException e) {
			Errorhandling.handle(e);
		}
	}
	/**
	 * Renders the FM to the Screen
	 * 
	 * @throws IOException if some font files could not be found
	 */
	private static void renderFresch() throws IOException {
		int counter = 200;
		TrueTypeFont ttf = new TrueTypeFont(new Font(Font.MONOSPACED, Font.BOLD, 42), true);
		while (counter >= 0) {
			glClearColor(0f, 0f, 0f, 0f);
			glClear(GL_COLOR_BUFFER_BIT);
			glLoadIdentity();
			ttf.drawString(0, 0, "FM", 8, 8);
			glEnd();
			Display.update();
			Display.sync(60);
			counter--;
		}
	}

	static {
		Out.inf(Intro.class, "07.11.12", "Felix", null);
	}

	public static void main(String args[]) {
		try {
			ServiceProvider.libs();
			showIntroFullScreen();
			Display.destroy();
		} catch (LWJGLException e) {
		}
	}
}
