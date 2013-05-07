package at.jumpandjan;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Canvas;
import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import at.freschmushroom.Errorhandling;
import at.freschmushroom.TextureManager;
import at.freschmushroom.Out;
import at.freschmushroom.audio.SoundContainer;
import at.jumpandjan.level.Level;

public class SequenceParty implements Sequence {
	private EntityPsy psy;
	private EntityPlayer p;
	private PartyThread pt;
	private Level l;

	public SequenceParty() {
		l = new Level(new ArrayList<Object>(), new ArrayList<Object>(),
				new ArrayList<Object>());
		psy = new EntityPsy(20, 20, l);
		p = new EntityPlayer(598.0, 356.0, 32.0, 64.0, l);
		l.getSecond().add(psy);
		l.getThird().add(p);
		pt = new PartyThread();
	}

	@Override
	public void start() {
		pt.start();
	}

	@Override
	public void pause() {

	}

	@Override
	public void stop() {
		pt.interrupt();
	}

	@Override
	public void join() throws InterruptedException {
		pt.join();
	}

	private class PartyThread extends Thread {
		public void run() {
			try {
				Display.setTitle("Jump\'n\'Jan");
				Display.setDisplayMode(new DisplayMode(640, 480));
				Display.create();
				Display.setParent((Canvas) JumpAndJan.canvas);
				Display.setVSyncEnabled(true);
				JumpAndJan.parent.requestFocus();
			} catch (LWJGLException e) {
				Errorhandling.handle(e);
			}
			glMatrixMode(GL_PROJECTION);
			glLoadIdentity();
			glOrtho(0, 640, 480, 0, 1, -1);
			glMatrixMode(GL_MODELVIEW);
			glLoadIdentity();

			glViewport(0, 0, 640, 480);

			glEnable(GL_TEXTURE_2D);
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			SoundContainer.party_music.playAsMusic(1.0f, 1.0f, false);
			int counter = 0;
			int color = 0;
			boolean black = false;
			while (SoundContainer.party_music.isPlaying()) {
				Display.isCloseRequested();
				Constants.update();
				GL11.glClearColor(0, 0, 0, 0);
				glClear(GL_COLOR_BUFFER_BIT);
				glLoadIdentity();
				Color c;
				if (counter % 5 == 0) {
					if (black) {
						color = color++ > 5 ? 0 : color;
						black = false;
						p.state = !p.state;
					} else {
						black = true;
					}
				}
				switch (color) {
				case 0:
					c = Color.red;
					break;
				case 1:
					c = Color.green;
					break;
				case 2:
					c = Color.yellow;
					break;
				case 3:
					c = Color.blue;
					break;
				case 4:
					c = Color.pink;
					break;
				default:
					c = Color.orange;
				}
				if (!black) {
					glBegin(GL_QUADS);
					glColor4f(c.getRed() / 255.0f, c.getGreen() / 255.0f,
							c.getBlue() / 255.0f, 0.45f);
					glVertex2f(0, 0);
					glVertex2f(0, 480);
					glVertex2f(640, 480);
					glVertex2f(640, 0);
					glEnd();
				}
				l.render();
				Display.update();
				Display.sync(60);
				counter++;
			}
			SoundContainer.explosion.playAsMusic(1.0f, 1.0f, false);
			SoundContainer.party_music.playAsSoundEffect(1.0f, 1.0f, false);
			while (SoundContainer.explosion.isPlaying()) {
				if (SoundContainer.explosion.getPosition() >= 6) {
					SoundContainer.party_music.stop();
					break;
				}
				Color c;
				if (counter % 5 == 0) {
					if (black) {
						color = color++ > 5 ? 0 : color;
						black = false;
						p.state = !p.state;
					} else {
						black = true;
					}
				}
				switch (color) {
				case 0:
					c = Color.red;
					break;
				case 1:
					c = Color.green;
					break;
				case 2:
					c = Color.yellow;
					break;
				case 3:
					c = Color.blue;
					break;
				case 4:
					c = Color.pink;
					break;
				default:
					c = Color.orange;
				}
				if (!black) {
					glBegin(GL_QUADS);
					glColor4f(c.getRed() / 255.0f, c.getGreen() / 255.0f,
							c.getBlue() / 255.0f, 0.45f);
					glVertex2f(0, 0);
					glVertex2f(0, 480);
					glVertex2f(640, 480);
					glVertex2f(640, 0);
					glEnd();
				}
				l.render();
				Display.update();
				Display.sync(60);
				counter++;
			}
			for(int d = 0; d < 250; d += 50) {
				Constants.update();
				GL11.glClearColor(0, 0, 0, 0);
				glClear(GL_COLOR_BUFFER_BIT);
				glLoadIdentity();
				glBegin(GL_QUADS);
				glColor4f(1.0f, 1.0f, 1.0f, d / 255);
				glVertex2f(0, 0);
				glVertex2f(0, 480);
				glVertex2f(640, 480);
				glVertex2f(640, 0);
				glEnd();
				Display.update();
				Display.sync(60);
			}
			while(SoundContainer.explosion.isPlaying());
			Display.destroy();
			TextureManager.instance.clearMaps();
		}
	}
	static {
		Out.inf(SequenceParty.class, "23.10.12", "Felix", new String[] {"Noch nicht fertig, unbedingt ausbauen"});
	}
}
