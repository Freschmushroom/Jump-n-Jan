package at.jumpandjan.levelcreator;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glViewport;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class LevelCreator {

	public static ObjectChooser chooserGui = new ObjectChooser();

	public static int VIEWPORT_HEIGHT;
	public static int VIEWPORT_WIDTH;
	
	public static void main(String[] args) {
		try {
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setTitle("Jump'n'Jan: Level Maker");
			Display.setResizable(true);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 640, 0, 480, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		while (!Display.isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT);
			if (Display.wasResized()) {
				resize(Display.getWidth(), Display.getHeight());
			}

			chooserGui.render();

			while (Keyboard.next()) {
				if (!chooserGui.isVisible()) {
					if (Keyboard.getEventKey() == Keyboard.KEY_O
							&& Keyboard.getEventKeyState()) {
						chooserGui.setVisible(true);
					}
				} else {
					chooserGui.processKeyboardInput(Keyboard.getEventKey(),
							Keyboard.getEventCharacter(),
							Keyboard.getEventKeyState());
				}
			}

			while (Mouse.next()) {
				if (chooserGui.isVisible()) {
					chooserGui.processMouseInput(Mouse.getEventButton(),
							Mouse.getEventButtonState(), Mouse.getEventX(),
							Mouse.getEventY(), Mouse.getEventDX(),
							Mouse.getEventDY());
				}
			}

			Display.update();
			Display.sync(60);
		}
	}
	
	private static void resize(int width, int height) {
		VIEWPORT_WIDTH = 4 * height / 3;
		VIEWPORT_HEIGHT = height;
		int viewportX = (width - VIEWPORT_WIDTH) / 2;
		glViewport(viewportX, 0, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
		glPushAttrib(GL_TRANSFORM_BIT);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, VIEWPORT_WIDTH, 0, VIEWPORT_HEIGHT, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glPopAttrib();
	}
}
