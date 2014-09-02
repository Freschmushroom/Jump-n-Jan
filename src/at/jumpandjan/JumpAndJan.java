package at.jumpandjan;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SCISSOR_TEST;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glScissor;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glViewport;

import java.awt.Canvas;
import java.awt.Frame;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import at.freschmushroom.Out;
import at.freschmushroom.TextAreaOutputStream;
import at.freschmushroom.audio.SoundContainer;
import at.jumpandjan.entity.EntityPlayer;
import at.jumpandjan.gui.Gui;
import at.jumpandjan.gui.GuiLoadingScreen;
import at.jumpandjan.gui.GuiMenu;
import at.jumpandjan.level.Level;

/**
 * The main class
 * 
 * @author Michael
 * @author Felix
 * 
 */
public class JumpAndJan implements Constants.RAMListener
{
	/**
	 * The frame of the display
	 */
	public Frame parent;
	/**
	 * The canvas in the frame
	 */
	public Canvas canvas;

	/**
	 * All active guis, where the one with the highest index is the current one
	 */
	public static final java.util.ArrayList<Gui> openGuis = new java.util.ArrayList<Gui>();

	public Level level;

	public static void main(String[] args)
	{
		new JumpAndJan();
	}

	private JumpAndJan()
	{
		// System.exit(1);
		try
		{
			Constants.load();
			Constants.addRAMListener(this);
			parent = initFrame();
			// Intro.showIntroFullScreen();
			SoundContainer.init();
			Out.line("Running LWJGL version " + Sys.getVersion());
			Out.surpressAdditionalInformation();
			try
			{
				Display.setTitle("Jump\'n\'Jan");
				Display.setDisplayMode(new DisplayMode(640, 480));
				Out.line("Done creating new screen");
				Display.setParent(canvas);
				Display.create();
				// Display.setVSyncEnabled(true);
				// JumpAndJan.parent.requestFocus();
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

			} catch (LWJGLException e)
			{
				Errorhandling.handle(e);
			}
			Display.setTitle("Jump'n'Jan");
			Constants.setPaused(false);
			Constants.setRunning(true);
			if (Constants.isMusic())
			{
				SoundContainer.play("game_music");
			}
			Constants.update();

			openGuis.add(new GuiLoadingScreen(this));
			long startTime = System.nanoTime();
			long frameCount = 0;
			while (!Display.isCloseRequested() && Constants.isRunning())
			{
				if (Display.wasResized())
				{
					glViewport(0, 0, Display.getWidth(), Display.getHeight());
					for (Gui gui : openGuis)
					{
						gui.resized();
					}
				}
				Constants.update();

				glScissor(0, 0, Display.getWidth(), Display.getHeight());
				glClearColor(1, 1, 1, 1);
				glClear(GL_COLOR_BUFFER_BIT);
				glLoadIdentity();
				drawBackground();
				// glTranslated(-getPlayer().x + 304 + Intro.vertMvmt,
				// Intro.horiMvmt, 0);
				// Constants.startRender();
				if (level != null)
				{
					level.render();
				}
				if (openGuis.size() > 0)
				{
					openGuis.get(openGuis.size() - 1).render();
					if (openGuis.get(openGuis.size() - 1).isDirty())
					{
						openGuis.get(openGuis.size() - 1).init();
					}
				}

				// Constants.stopRender();
				if (getPlayer() != null)
				{
					if (Keyboard.isKeyDown(Keyboard.KEY_A))
					{
						getPlayer().motion.x = -3;
					}
					else if (Keyboard.isKeyDown(Keyboard.KEY_D))
					{
						getPlayer().motion.x = 3;
					}
					else
					{
						getPlayer().motion.x = 0;
					}
				}
				if (getPlayer() != null && getPlayer().onGround && Keyboard.isKeyDown(Keyboard.KEY_SPACE))
				{
					getPlayer().motion.y = -16;
				}
				while (Keyboard.next())
				{
					if (openGuis.isEmpty())
					{
						if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == Keyboard.KEY_ESCAPE)
						{
							openGui(new GuiMenu(this));
						}
					}
					else
					{
						openGuis.get(openGuis.size() - 1).fireKeyboardEvent(Keyboard.getEventKeyState(), Keyboard.getEventKey(), Keyboard.getEventCharacter(), Mouse.getX(),
								Display.getHeight() - Mouse.getY());
					}
				}
				while (Mouse.next())
				{
					if (openGuis.isEmpty())
					{
						if (Mouse.getEventButton() == 0)
						{
							if (Mouse.getEventButtonState())
							{
								getPlayer().setAttackState(EntityPlayer.BEGIN_ATTACK_ANIMATION);
							}
							else
							{
								getPlayer().setAttackState(EntityPlayer.END_ATTACK_ANIMATION);
							}
						}
					}
					else
					{
						openGuis.get(openGuis.size() - 1).fireMouseEvent(Mouse.getEventButtonState(), Mouse.getEventButton(), Mouse.getEventX(), Mouse.getEventY(), Mouse.getEventDX(),
								-Mouse.getEventDY());
					}
				}
				if (openGuis.isEmpty() && Mouse.isButtonDown(1))
					getPlayer().attackAnimCount++;
				if (!Constants.isPaused())
				{
					// Constants.startUpdate();
					if (level != null)
					{
						level.update();
					}
					// Constants.stopUpdate();
				}
				for (int i = 0; i < openGuis.size() - 1; i++)
				{
					if (openGuis.get(i).isDirty())
					{
						openGuis.get(i).init();
					}
					openGuis.get(i).updateWhileInactive();
				}
				Display.update();
				Display.sync(60);

				frameCount++;
			}
			if (Constants.getCURRENT_USER() != null)
			{
				Constants.getCURRENT_USER().save();
			}
			long endTime = System.nanoTime();
			System.out.println("Time: " + (endTime - startTime) / 1000000000 + " seconds");
			System.out.println("Average FPS: " + ((double) frameCount / ((endTime - startTime) / 1000000000)));
			AL.destroy();
			Display.destroy();
			parent.dispose();
			Constants.showRenderUpdateTimes();
			Out.destroy();
		} catch (Throwable e)
		{
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
	 * Gets the player
	 * 
	 * @return The player
	 */
	public EntityPlayer getPlayer()
	{
		return level == null ? null : level.getPlayer();
	}

	/**
	 * Initializes the frame
	 * 
	 * @return The frame
	 */
	private Frame initFrame()
	{
		try
		{
			Constants.load();
			Frame f = new Frame("Jump\'n\'Jan");
			canvas = new Canvas();
			f.add(canvas);
			f.addFocusListener(new FocusListener() {
				@Override
				public void focusLost(FocusEvent e)
				{
					Constants.setPaused(true);
				}

				@Override
				public void focusGained(FocusEvent e)
				{
					Constants.setPaused(false);
				}
			});
			f.addWindowListener(new WindowListener() {
				@Override
				public void windowOpened(WindowEvent e)
				{
				}

				@Override
				public void windowClosing(WindowEvent e)
				{
					((Frame) e.getSource()).dispose();
					Constants.setRunning(false);
					Constants.showRenderUpdateTimes();
					// Out.printAllVersions();
					// Out.printAllMissingFiles();
				}

				@Override
				public void windowClosed(WindowEvent e)
				{
				}

				@Override
				public void windowIconified(WindowEvent e)
				{
				}

				@Override
				public void windowDeiconified(WindowEvent e)
				{
				}

				@Override
				public void windowActivated(WindowEvent e)
				{
				}

				@Override
				public void windowDeactivated(WindowEvent e)
				{
				}
			});
			f.setVisible(true);
			f.setSize(640 + f.getInsets().left + f.getInsets().right, 480 + f.getInsets().top + f.getInsets().bottom);
			f.setLocationRelativeTo(null);
			return f;
		} catch (Exception ex)
		{
			return null;
		}
	}

	@Override
	public void tooFewRAM(double used)
	{
		Out.err("Not enough RAM already " + used + " used.");
	}

	@Override
	public void noFreeRAM(double used, boolean critical)
	{
		if (critical)
		{
			Out.err("Not enough RAM. Shutting down virtual machine");
		}
		else
		{
			tooFewRAM(used);
		}
	}

	static
	{
		Out.inf(JumpAndJan.class, "22.10.12", "Felix, Michael", null);
	}

	/**
	 * Draws the fancy background
	 */
	public static void drawBackground()
	{
		glPushMatrix();
		glColor3f(1, 1, 1);
		glTranslated(0, 0, 0);
		glEnable(GL_TEXTURE_2D);
		TextureManager.instance.bindTexture(TextureManager.instance.getTexture("/Background.png"));
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(0, 0);
		glTexCoord2f(0, 1);
		glVertex2f(0, 480);
		glTexCoord2f(1, 1);
		glVertex2f(640, 480);
		glTexCoord2f(1, 0);
		glVertex2f(640, 0);
		glEnd();
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
	}

	/**
	 * Closes the current gui
	 */
	public static void closeCurrentGui()
	{
		if (!openGuis.isEmpty())
			openGuis.remove(openGuis.size() - 1);
		if (openGuis.isEmpty())
			Constants.setPaused(false);
	}

	/**
	 * Closes all guis
	 */
	public static void closeAllGuis()
	{
		openGuis.clear();
		Constants.setPaused(false);
	}

	/**
	 * Opens the Gui
	 * 
	 * @param gui
	 *            The gui to open
	 */
	public static void openGui(Gui gui)
	{
		openGuis.add(gui);
		gui.init();
		Constants.setPaused(true);
	}

	/**
	 * Reloads all open Guis at the beginning of the next tick
	 */
	public static void reloadGuis()
	{
		for (Gui gui : openGuis)
		{
			gui.markDirty();
		}
	}
}
