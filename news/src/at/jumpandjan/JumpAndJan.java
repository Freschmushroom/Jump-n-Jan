package at.jumpandjan;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import org.lwjgl.*;
import org.lwjgl.input.*;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.*;
import org.lwjgl.opengl.DisplayMode;

import at.jumpandjan.audio.SoundContainer;

import static org.lwjgl.opengl.GL11.*;

public class JumpAndJan
{
	private static EntityPlayer player = new EntityPlayer(10, 330, 32, 64);
	private static Wall floor = new Wall(10, 460, 620, 10);
	public static int DISPLAY_WIDTH;
	public static int DISPLAY_HEIGHT;
	public static boolean paused;
	public static boolean running = true;

	public static void main(String[] args)
	{
		try
		{
			Frame f = new Frame("Jump\'n\'Jan");
			f.setSize(640 + f.getInsets().left + f.getInsets().right,
					480 + f.getInsets().top + f.getInsets().bottom);
			f.setLocationRelativeTo(null);
			Canvas canvas = new Canvas();
			f.add(canvas);
			f.addFocusListener(new FocusListener() {

				@Override
				public void focusLost(FocusEvent e)
				{
					paused = true;
				}

				@Override
				public void focusGained(FocusEvent e)
				{
					paused = false;
				}
			});
			f.addWindowListener(new WindowListener() {

				@Override
				public void windowOpened(WindowEvent e)
				{
					// TODO Auto-generated method stub

				}

				@Override
				public void windowClosing(WindowEvent e)
				{
					((Frame) e.getSource()).dispose();
					running = false;
				}

				@Override
				public void windowClosed(WindowEvent e)
				{
					// TODO Auto-generated method stub

				}

				@Override
				public void windowIconified(WindowEvent e)
				{
					// TODO Auto-generated method stub

				}

				@Override
				public void windowDeiconified(WindowEvent e)
				{
					// TODO Auto-generated method stub

				}

				@Override
				public void windowActivated(WindowEvent e)
				{
					// TODO Auto-generated method stub

				}

				@Override
				public void windowDeactivated(WindowEvent e)
				{
					// TODO Auto-generated method stub

				}

			});
			f.setVisible(true);
			Display.setTitle("Jump\'n\'Jan");
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.create();
			Display.setParent(canvas);
			f.requestFocus();
		} catch (LWJGLException e)
		{
			e.printStackTrace();
		}
		SoundContainer.init();
		SoundContainer.startGameMusic();
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 640, 480, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		EntityMeatball mb = new EntityMeatball(150, 300);
		mb.collision.add(floor);
		getPlayer().collision.add(floor);
		mb.collision.add(getPlayer());
		getPlayer().collision.add(mb);

		while (!Display.isCloseRequested() && running)
		{
			DISPLAY_WIDTH = Display.getWidth();
			DISPLAY_HEIGHT = Display.getHeight();
			glClearColor(0, 0, 1, 0);
			glClear(GL_COLOR_BUFFER_BIT);
			glLoadIdentity();
			glEnable(GL_TEXTURE_2D);
			FontRenderer.instance.drawStringAt("T", 0, 0, 640, 15,
					new float[]
					{ 0, 0, 0 });
			glDisable(GL_TEXTURE_2D);
			getPlayer().render();
			mb.render();
			floor.render();

			if (Keyboard.isKeyDown(Keyboard.KEY_A))
			{
				getPlayer().motionX = -3;
			} else if (Keyboard.isKeyDown(Keyboard.KEY_D))
			{
				getPlayer().motionX = 3;
			} else
				getPlayer().motionX = 0;

			while (Keyboard.next())
			{
				if (Keyboard.getEventKeyState()
						&& Keyboard.getEventKey() == Keyboard.KEY_SPACE
						&& getPlayer().onGround)
				{
					getPlayer().motionY -= 20;
				}
			}

			if (!paused) {
				getPlayer().update();
				mb.update();
			}
			Display.update();
			Display.sync(60);
		}
		AL.destroy();
	}

	public static EntityPlayer getPlayer()
	{
		return player;
	}
}
