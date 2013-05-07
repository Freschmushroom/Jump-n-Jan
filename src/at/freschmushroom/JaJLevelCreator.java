package at.freschmushroom;

import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import at.jumpandjan.*;
import at.jumpandjan.level.Level;
import at.freschmushroom.xml.*;

import static org.lwjgl.opengl.GL11.*;

public class JaJLevelCreator {
	public static ArrayList<at.jumpandjan.Object> firstLayer = new ArrayList<at.jumpandjan.Object>();
	public static ArrayList<at.jumpandjan.Object> secondLayer = new ArrayList<at.jumpandjan.Object>();
	public static ArrayList<at.jumpandjan.Object> thirdLayer = new ArrayList<at.jumpandjan.Object>();

	public static void main(String[] args) {
		try {
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setTitle("Jump'n'Jan: Level creator");
			Display.create();
		} catch (LWJGLException lwjgle) {
			lwjgle.printStackTrace();
			System.err.println("Aborting...");
			System.exit(1);
		}

		float camX = 0;

		boolean objectGuiOpen = false;
		ObjectSlot[] objectGui = new ObjectSlot[70];
		objectGui[0] = new ObjectSlot(0, 0, Wall.class);
		objectGui[1] = new ObjectSlot(1, 0, Floor.class);
		objectGui[2] = new ObjectSlot(2, 0, EntityMeatball.class);
		objectGui[3] = new ObjectSlot(3, 0, EntityStartFlag.class);
		objectGui[4] = new ObjectSlot(4, 0, EntityFinishFlag.class);
		objectGui[5] = new ObjectSlot(5, 0, EntityUnicorn.class);
		objectGui[6] = new ObjectSlot(6, 0, EntityWaitrose.class);

		at.jumpandjan.Object selectedObject = null;

		Vector2f start = null;
		Vector2f end = null;

		while (!Display.isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT);
			glLoadIdentity();
			glTranslatef(-camX, 0, 0);

			for (at.jumpandjan.Object o : firstLayer) {
				if (o != null)
					o.render();
			}

			glPushMatrix();
			glLoadIdentity();

			glColor4f(0.5f, 0.5f, 0.5f, 0.5f);
			glRectf(0, 220, 40, 260);
			glRectf(600, 220, 640, 260);

			glColor4f(1, 1, 1, 1);

			glEnable(GL_TEXTURE_2D);
			TextureManager.instance.bindTexture(TextureManager.instance
					.getTexture("/button_object.png"));
			glBegin(GL_QUADS);
			glTexCoord2f(0, 0);
			glVertex2f(40, 430);
			glTexCoord2f(0, 1);
			glVertex2f(40, 462);
			glTexCoord2f(1, 1);
			glVertex2f(72, 462);
			glTexCoord2f(1, 0);
			glVertex2f(72, 430);
			glEnd();
			TextureManager.instance.bindTexture(TextureManager.instance
					.getTexture("/button_save.png"));
			glBegin(GL_QUADS);
			glTexCoord2f(0, 0);
			glVertex2f(80, 430);
			glTexCoord2f(0, 1);
			glVertex2f(80, 462);
			glTexCoord2f(1, 1);
			glVertex2f(112, 462);
			glTexCoord2f(1, 0);
			glVertex2f(112, 430);
			glEnd();
			glDisable(GL_TEXTURE_2D);

			if (objectGuiOpen) {
				glColor4f(0f, 0f, 0.3f, 0.5f);
				glRectf(37, 37, 603, 443);
				for (int i = 0; i < objectGui.length; i++) {
					if (objectGui[i] == null)
						continue;
					int xInGui = objectGui[i].xInGui;
					int yInGui = objectGui[i].yInGui;
					float x = 40 + xInGui * 53.6f + xInGui * 3;
					float y = 40 + yInGui * 53.6f + yInGui * 3;
					glPushMatrix();
					glTranslatef(x, y, 0);
					glColor4f(1, 1, 1, 1);
					if (objectGui[i] != null)
						objectGui[i].render();
					glPopMatrix();
				}
			}
			if (selectedObject != null) {
				glPushMatrix();
				glTranslatef(Mouse.getX(), 480 - Mouse.getY(), 0);
				selectedObject.renderIcon();
				glPopMatrix();
			}
			glPopMatrix();

			while (Mouse.next()) {
				double x = Mouse.getEventX();
				double y = 480 - Mouse.getEventY();

				if (Mouse.getEventButtonState()) {
					if (objectGuiOpen && Mouse.getEventButton() == 0) {
						if (x >= 40 && x <= 72 && y >= 430 && y <= 462) {
							objectGuiOpen = false;
						} else {
							for (int i = 0; i < objectGui.length; i++) {
								if (objectGui[i] == null) {
									continue;
								}
								int xInGui = objectGui[i].xInGui;
								int yInGui = objectGui[i].yInGui;
								float slotX = 40 + xInGui * 53.6f + xInGui * 3;
								float slotY = 40 + yInGui * 53.6f + yInGui * 3;
								if (x >= slotX && x <= slotX + 53.6
										&& y >= slotY && y <= slotY + 53.6)
									selectedObject = objectGui[i].getObject();
							}
						}
					} else if (Mouse.getEventButton() == 0) {
						if (x >= 0 && x <= 40 && y >= 220 && y <= 260) {
							camX -= 50;
						} else if (x >= 600 && x <= 640 && y >= 220 && y <= 260) {
							camX += 50;
						} else if (x >= 40 && x <= 72 && y >= 430 && y <= 462) {
							objectGuiOpen = true;
						} else if (x >= 80 && x <= 112 && y >= 430 && y <= 462) {
							save();
						} else if (selectedObject != null) {
							if (start == null)
								start = new Vector2f((float) x + camX,
										(float) y);
							else {
								end = new Vector2f((float) x + camX, (float) y);
								selectedObject.bounds.x = (int) start.x;
								selectedObject.bounds.y = (int) start.y;
								selectedObject.bounds.width = (int) (end.x - start.x);
								selectedObject.bounds.height = (int) (end.y - start.y);
								firstLayer.add(selectedObject);
								if (selectedObject instanceof EntityStartFlag)
									for (ObjectSlot o : objectGui)
										if (o != null
												&& o.objectClass == EntityStartFlag.class)
											o.isEnabled = false;
								selectedObject = null;
								start = null;
								end = null;
							}
						}
					} else if (Mouse.getEventButton() == 1
							&& selectedObject != null) {
						if (start == null) {
							selectedObject.bounds.x = (int) (x + camX);
							selectedObject.bounds.y = (int) y;
							selectedObject.bounds.width = (int) selectedObject
									.getDefaultWidth();
							selectedObject.bounds.height = (int) selectedObject
									.getDefaultHeight();
							System.out.println(selectedObject.bounds.width + " "
									+ selectedObject.bounds.height);
							firstLayer.add(selectedObject);
							if (selectedObject instanceof EntityStartFlag)
								for (ObjectSlot o : objectGui)
									if (o != null
											&& o.objectClass == EntityStartFlag.class)
										o.isEnabled = false;
							selectedObject = null;
						} else {
							end = new Vector2f((float) x + camX, (float) y);
							selectedObject.bounds.x = (int) start.x;
							selectedObject.bounds.y = (int) start.y;
							selectedObject.setWidthOrDefault(end.x - start.x);
							selectedObject.setHeightOrDefault(end.y - start.y);
							firstLayer.add(selectedObject);
							if (selectedObject instanceof EntityStartFlag)
								for (ObjectSlot o : objectGui)
									if (o != null
											&& o.objectClass == EntityStartFlag.class)
										o.isEnabled = false;
							selectedObject = null;
							start = null;
							end = null;
						}
					}
				}
			}
			// camX++;

			Display.update();
			Display.sync(60);
		}
	}

	private static void save() {
		XMLFile f = new XMLFile("/level/", "Level2");
		f.root = new XMLNode(null, "Level");
		XMLNode root = (XMLNode) f.root;
		XMLNode info = new XMLNode(root, "Info");
		new XMLAttribut("index", "2", info);
		new XMLAttribut("name", "\"LevelMakerTest\"", info);
		new XMLAttribut("points", "150", info);

		for (at.jumpandjan.Object o : firstLayer) {
			String name;
			if (o instanceof EntityStartFlag)
				name = "Start";
			else if (o instanceof EntityFinishFlag)
				name = "Finish";
			else
				name = o.getClass().getSimpleName();
			XMLNode node = new XMLNode(root, name);
			new XMLAttribut("posX", String.valueOf((int) o.bounds.x), node);
			new XMLAttribut("posY", String.valueOf((int) o.bounds.y), node);
			new XMLAttribut("width", String.valueOf((int) o.bounds.width), node);
			new XMLAttribut("height", String.valueOf((int) o.bounds.height), node);
		}

		XMLWriter w = new XMLWriter(f);
		w.writeToTerminal();
		w.writeToFile();
	}
}

class ObjectSlot {
	public Class<? extends at.jumpandjan.Object> objectClass;
	private at.jumpandjan.Object object;
	public int xInGui;
	public int yInGui;
	public boolean isEnabled;

	public ObjectSlot(int x, int y,
			Class<? extends at.jumpandjan.Object> objectClass) {
		xInGui = x;
		yInGui = y;
		isEnabled = true;
		this.objectClass = objectClass;
		try {
			this.object = objectClass.getConstructor(
					new Class[] { double.class, double.class, double.class,
							double.class, Level.class }).newInstance(
					new java.lang.Object[] { 0, 0, 53.6, 53.6, null });
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void render() {
		object.renderIcon();
		glPushMatrix();
		glEnable(GL_TEXTURE_2D);
		TextureManager.instance.bindTexture(TextureManager.instance
				.getTexture("/slot_margin_"
						+ (isEnabled ? "enabled" : "disabled") + ".png"));
		glScalef(53.6f, 53.6f, 1);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(0, 0);
		glTexCoord2f(0, 1);
		glVertex2f(0, 1);
		glTexCoord2f(1, 1);
		glVertex2f(1, 1);
		glTexCoord2f(1, 0);
		glVertex2f(1, 0);
		glEnd();
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
	}

	public at.jumpandjan.Object getObject() {
		if (!isEnabled)
			return null;
		try {
			return objectClass.getConstructor(
					new Class[] { double.class, double.class, double.class,
							double.class, Level.class }).newInstance(
					new java.lang.Object[] { 0, 0, 53.6, 53.6, null });
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}