package at.jumpandjan;
import org.lwjgl.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;

import at.jumpandjan.audio.SoundContainer;

import static org.lwjgl.opengl.GL11.*;

public class JumpAndJan {
	private static Object player = new Object(10, 10, 10, 50);
	private static Object floor = new Object(10, 460, 620, 10);
	
	public static void main(String[] args) {
		try {
			Display.setTitle("Jump\'n\'Jan");
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		SoundContainer.init();
		SoundContainer.startGameMusic();
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 640, 480, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		
		player.collision.add(floor);
		
		while(!Display.isCloseRequested()) {
			glClearColor(0, 0, 1, 0);
			glClear(GL_COLOR_BUFFER_BIT);
			
			glBegin(GL_QUADS);
			glColor3f(0, 1, 0);
			glVertex2d(player.x, player.y);
			glVertex2d(player.x, player.y + player.height);
			glVertex2d(player.x + player.width, player.y + player.height);
			glVertex2d(player.x + player.width, player.y);
			glEnd();
			glBegin(GL_QUADS);
			glColor3f(0, 1, 0);
			glVertex2d(floor.x, floor.y);
			glVertex2d(floor.x, floor.y + floor.height);
			glVertex2d(floor.x + floor.width, floor.y + floor.height);
			glVertex2d(floor.x + floor.width, floor.y);
			glEnd();
			
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
				player.motionX = -3;
			} else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
				player.motionX = 3;
			} else
				player.motionX = 0;
			
			while(Keyboard.next()) {
				if(Keyboard.getEventKeyState() && Keyboard.getEventKey() == Keyboard.KEY_SPACE && player.onGround)
				{
					player.motionY -= 20;
				}
			}
			
			player.update();
			
			Display.update();
			Display.sync(60);
		}
	}
}
