package at.jumpandjan;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Canvas;
import java.awt.Frame;
import java.awt.event.*;
import java.io.PrintStream;

import javax.swing.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.*;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.*;

import at.freschmushroom.*;
import at.freschmushroom.audio.SoundContainer;
import at.jumpandjan.gui.*;

/**
 * The main class
 * 
 * @author Michael
 * @author Felix
 * 
 */
public class JumpAndJan implements Constants.RAMListener {
	/**
	 * The frame of the display
	 */
	public static Frame parent;
	/**
	 * The canvas in the frame
	 */
	public static Canvas canvas;

	/**
	 * All active guis, where the one with the highest index is the current one
	 */
	public static final java.util.ArrayList<Gui> openGuis = new java.util.ArrayList<Gui>();

	public static void main(String[] args) {
		// System.exit(1);
		try {
			Constants.load();
			Constants.addRAMListener(new JumpAndJan());
			parent = initFrame();
			// Intro.showIntroFullScreen();
			SoundContainer.init();
			if (Constants.isSeq1()) {
				SequenceParty sp = new SequenceParty();
				sp.start();
				try {
					sp.join();
				} catch (InterruptedException e) {
					Errorhandling.handle(e);
				}
			}
			try {
				Display.setTitle("Jump\'n\'Jan");
				if (!Intro.init) {
					Display.setDisplayMode(new DisplayMode(640, 480));
					Out.line("Done creating new screen");
					Display.create();
					Display.setParent((Canvas) JumpAndJan.canvas);
					// Display.setVSyncEnabled(true);
					//JumpAndJan.parent.requestFocus();
					glMatrixMode(GL_PROJECTION);
					glLoadIdentity();
					glOrtho(0, 640, 480, 0, 1, -1);
					glMatrixMode(GL_MODELVIEW);
					glLoadIdentity();

					glViewport(0, 0, 640, 480);

					glEnable(GL_TEXTURE_2D);
					glEnable(GL_BLEND);
					glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
					glEnable(GL_SCISSOR_TEST);
				}
			} catch (LWJGLException e) {
				Errorhandling.handle(e);
			}
			Display.setTitle("Jump'n'Jan");
			Constants.setPaused(false);
			Constants.setRunning(true);
			if (Constants.isMusic())
				SoundContainer.play("game_music");

			Constants.update();

			openGuis.add(new GuiLoadingScreen());
			long startTime = System.nanoTime();
			long frameCount = 0;
			while (!Display.isCloseRequested() && Constants.isRunning()) {
				if (Display.wasResized()) {
					glViewport(0, 0, Display.getWidth(), Display.getHeight());
					for (Gui gui : openGuis) {
						gui.resized();
					}
				}
				Constants.update();

				if (Constants.getActualLevel() == null) {
					Constants.setCameraX(0);
				} else {
					Constants.setCameraX((int) (getPlayer().bounds.x - Display
							.getWidth() / 2));
				}
				glScissor(0, 0, Display.getWidth(), Display.getHeight());
				glClearColor(1, 1, 1, 1);
				glClear(GL_COLOR_BUFFER_BIT);
				glLoadIdentity();
				drawBackground();
				// glTranslated(-getPlayer().x + 304 + Intro.vertMvmt,
				// Intro.horiMvmt, 0);
				glTranslatef(-Constants.getCameraX(), 0, 0);
				// Constants.startRender();
				if (Constants.getActualLevel() != null) {
					Constants.getActualLevel().render();
				}
				if (openGuis.size() > 0) {
					openGuis.get(openGuis.size() - 1).render();
				}

				// Constants.stopRender();
				if (getPlayer() != null) {
					if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
						getPlayer().motion.x = -3;
					} else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
						getPlayer().motion.x = 3;
					} else {
						getPlayer().motion.x = 0;
					}
				}
				while (Keyboard.next()) {
					if (openGuis.isEmpty()) {
						if (Keyboard.getEventKeyState()
								&& Keyboard.getEventKey() == Keyboard.KEY_SPACE
								&& getPlayer().onGround) {
							getPlayer().motion.y = -16;
						} else if (Keyboard.getEventKeyState()
								&& Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
							openGui(new GuiMenu());
						}
					} else {
						openGuis.get(openGuis.size() - 1).fireKeyboardEvent(
								Keyboard.getEventKeyState(),
								Keyboard.getEventKey(),
								Keyboard.getEventCharacter(), Mouse.getX(),
								Display.getHeight() - Mouse.getY());
					}
				}
				while (Mouse.next()) {
					if (openGuis.isEmpty()) {
						if (Mouse.getEventButton() == 0) {
							if (Mouse.getEventButtonState()) {
								getPlayer().setAttackState(
										EntityPlayer.BEGIN_ATTACK_ANIMATION);
							} else {
								getPlayer().setAttackState(
										EntityPlayer.END_ATTACK_ANIMATION);
							}
						}
					} else {
						openGuis.get(openGuis.size() - 1).fireMouseEvent(
								Mouse.getEventButtonState(),
								Mouse.getEventButton(), Mouse.getEventX(),
								Mouse.getEventY(), Mouse.getEventDX(),
								-Mouse.getEventDY());
					}
				}
				if (openGuis.isEmpty() && Mouse.isButtonDown(1))
					getPlayer().attackAnimCount++;
				if (!Constants.isPaused()) {
					// Constants.startUpdate();
					if (Constants.getActualLevel() != null) {
						Constants.getActualLevel().update();
					}
					// Constants.stopUpdate();
				}
				for (int i = 0; i < openGuis.size() - 1; i++) {
					openGuis.get(i).updateWhileInactive();
				}
				Display.update();
				Display.sync(60);

				frameCount++;
			}
			long endTime = System.nanoTime();
			System.out.println("Time: " + (endTime - startTime) / 1000000000
					+ " seconds");
			System.out
					.println("Average FPS: "
							+ ((double) frameCount / ((endTime - startTime) / 1000000000)));
			AL.destroy();
			Display.destroy();
			parent.dispose();
			Constants.showRenderUpdateTimes();
			Out.destroy();
		} catch (Throwable e) {
			Errorhandling.handle(e);
			JFrame f = new JFrame("Error");
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JScrollPane scrollpane = new JScrollPane();
			JTextArea error = new JTextArea();
			error.append("An error occured. Please send the last file in errors folder plus a short description of what you did\r\n to our staff.\r\n\r\n");
			error.setEditable(false);
			e.printStackTrace();
			TextAreaOutputStream outputstream = new TextAreaOutputStream(error);
			PrintStream printstream = new PrintStream(outputstream);
			e.printStackTrace(printstream);
			printstream.close();
			scrollpane.getViewport().add(error);
			f.add(scrollpane);
			f.setSize(640, 480);
			f.setLocationRelativeTo(null);
			f.setVisible(true);
		}
	}

	/**
	 * YOU SHALL NOT INITIALIZE
	 */
	private JumpAndJan() {
		Out.line("Listeners started");
	}

	/**
	 * Gets the player
	 * 
	 * @return The player
	 */
	public static EntityPlayer getPlayer() {
		return Constants.getActualLevel() == null ? null : Constants
				.getActualLevel().getPlayer();
	}

	/**
	 * Initializes the frame
	 * 
	 * @return The frame
	 */
	private static Frame initFrame() {
		try {
			Constants.load();
			Frame f = new Frame("Jump\'n\'Jan");
			canvas = new Canvas();
			f.add(canvas);
			f.addFocusListener(new FocusListener() {
				@Override
				public void focusLost(FocusEvent e) {
					Constants.setPaused(true);
				}

				@Override
				public void focusGained(FocusEvent e) {
					Constants.setPaused(false);
				}
			});
			f.addWindowListener(new WindowListener() {
				@Override
				public void windowOpened(WindowEvent e) {
				}

				@Override
				public void windowClosing(WindowEvent e) {
					((Frame) e.getSource()).dispose();
					Constants.setRunning(false);
					Constants.showRenderUpdateTimes();
					// Out.printAllVersions();
					// Out.printAllMissingFiles();
				}

				@Override
				public void windowClosed(WindowEvent e) {
				}

				@Override
				public void windowIconified(WindowEvent e) {
				}

				@Override
				public void windowDeiconified(WindowEvent e) {
				}

				@Override
				public void windowActivated(WindowEvent e) {
				}

				@Override
				public void windowDeactivated(WindowEvent e) {
				}
			});
			f.setVisible(true);
			f.setSize(640 + f.getInsets().left + f.getInsets().right,
					480 + f.getInsets().top + f.getInsets().bottom);
			f.setLocationRelativeTo(null);
			return f;
		} catch (Exception ex) {
			return null;
		}
	}

	@Override
	public void tooFewRAM(double used) {
		Out.err("Not enough RAM already " + used + " used.");
	}

	@Override
	public void noFreeRAM(double used, boolean critical) {
		if (critical) {
			Out.err("Not enough RAM. Shutting down virtual machine");
		} else {
			tooFewRAM(used);
		}
	}

	static {
		Out.inf(JumpAndJan.class, "22.10.12", "Felix, Michael", null);
	}

	/**
	 * Draws the fancy background
	 */
	public static void drawBackground() {
		glPushMatrix();
		glColor3f(1, 1, 1);
		glTranslated(0, 0, 0);
		glEnable(GL_TEXTURE_2D);
		TextureManager.instance.bindTexture(TextureManager.instance
				.getTexture("/Background.png"));
		float f = 1f / TextureManager.instance.getImage("/Background.png")
				.getWidth();
		float f1 = 1f / TextureManager.instance.getImage("/Background.png")
				.getHeight();
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(0, 0);
		glTexCoord2f(0, (Intro.horiMvmt + 480) * f1);
		glVertex2f(0, Intro.horiMvmt + 480);
		glTexCoord2f((Intro.vertMvmt * 2 + 640) * f, (Intro.horiMvmt + 480)
				* f1);
		glVertex2f(Intro.vertMvmt * 2 + 640, Intro.horiMvmt + 480);
		glTexCoord2f((Intro.vertMvmt * 2 + 640) * f, 0);
		glVertex2f(Intro.vertMvmt * 2 + 640, 0);
		glEnd();
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
	}

	/**
	 * Closes the current gui
	 */
	public static void closeCurrentGui() {
		if (!openGuis.isEmpty())
			openGuis.remove(openGuis.size() - 1);
		if (openGuis.isEmpty())
			Constants.setPaused(false);
	}

	/**
	 * Closes all guis
	 */
	public static void closeAllGuis() {
		openGuis.clear();
		Constants.setPaused(false);
	}

	/**
	 * Opens the Gui
	 * 
	 * @param gui
	 *            The gui to open
	 */
	public static void openGui(Gui gui) {
		openGuis.add(gui);
		Constants.setPaused(true);
	}
}
