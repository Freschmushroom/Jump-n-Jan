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
import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import at.jumpandjan.Constants;

public class Intro {
	public static int vertMvmt = 0;
	public static int horiMvmt = 0;
	public static boolean init = false;

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

	public static void showIntroFullScreen() throws LWJGLException {
		Display.setTitle("FreshMushroom");
		Display.setDisplayModeAndFullscreen(getLargest());
		Display.create();
//		Display.setVSyncEnabled(true);
		renderIntro();
	}

	public static void showIntroWindowed() throws LWJGLException {
		Display.setTitle("FreshMushroom");
		Display.setDisplayMode(new DisplayMode(640, 480));
		Display.create();
		Display.setVSyncEnabled(true);
		renderIntro();
	}

	public static void showIntroInControl(Canvas parent) throws LWJGLException {
		Display.setTitle("FreshMushroom");
		Display.setDisplayMode(Display.getAvailableDisplayModes()[0]);
		Display.create();
		Display.setVSyncEnabled(true);
		Display.setParent(parent);
		renderIntro();
	}

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

	private static void renderFresch() throws IOException {
		FontParser.init();
		float[][] vertices_a = FontParser.getCharVertices('A');
		float[][] vertices_b = FontParser.getCharVertices('B');
		float[][] vertices_f = FontParser.getCharVertices('F');
		int counter = 0;
		counter = 200;
		while (counter >= 0) {
			glClearColor(0f, 0f, 0f, 0f);
			glClear(GL_COLOR_BUFFER_BIT);
			glLoadIdentity();
			glBegin(GL_TRIANGLES);
			for (float[] fl : vertices_a) {
				glColor3f(1, 1, 1);
				glVertex2f(fl[0], fl[1]);
			}
			for (float[] fl : vertices_b) {
				glColor3f(1, 1, 1);
				glVertex2f(fl[0] + 100, fl[1]);
			}
			for (float[] fl : vertices_f) {
				glColor3f(1, 1, 1);
				glVertex2f(fl[0] + 600, fl[1]);
			}
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
			showIntroFullScreen();
			Display.destroy();
		} catch (LWJGLException e) {
		}
	}
}
