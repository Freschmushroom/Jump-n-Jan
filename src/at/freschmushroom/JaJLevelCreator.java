package at.freschmushroom;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRectf;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Vector2f;

import at.freschmushroom.xml.XMLAttribute;
import at.freschmushroom.xml.XMLFile;
import at.freschmushroom.xml.XMLNode;
import at.freschmushroom.xml.XMLWriter;
import at.jumpandjan.Body;
import at.jumpandjan.Floor;
import at.jumpandjan.Wall;
import at.jumpandjan.entity.EntityFinishFlag;
import at.jumpandjan.entity.EntityMeatball;
import at.jumpandjan.entity.EntityStartFlag;
import at.jumpandjan.entity.EntityUnicorn;
import at.jumpandjan.entity.EntityWaitrose;
import at.jumpandjan.level.Level;

public class JaJLevelCreator
{
	public static ArrayList<Body> firstLayer = new ArrayList<Body>();
	public static ArrayList<Body> secondLayer = new ArrayList<Body>();
	public static ArrayList<Body> thirdLayer = new ArrayList<Body>();

	public static void main(String[] args)
	{
		try
		{
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setTitle("Jump'n'Jan: Level creator");
			Display.create();
		} catch (LWJGLException lwjgle)
		{
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

		Body selectedObject = null;

		Vector2f start = null;
		Vector2f end = null;

		while (!Display.isCloseRequested())
		{
			glClear(GL_COLOR_BUFFER_BIT);
			glLoadIdentity();
			glTranslatef(-camX, 0, 0);

			for (Body o : firstLayer)
			{
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
			TextureManager.instance.bindTexture(TextureManager.instance.getTexture("/button_object.png"));
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
			TextureManager.instance.bindTexture(TextureManager.instance.getTexture("/button_save.png"));
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

			if (objectGuiOpen)
			{
				glColor4f(0f, 0f, 0.3f, 0.5f);
				glRectf(37, 37, 603, 443);
				for (int i = 0; i < objectGui.length; i++)
				{
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
			if (selectedObject != null)
			{
				glPushMatrix();
				glTranslatef(Mouse.getX(), 480 - Mouse.getY(), 0);
				selectedObject.renderIcon();
				glPopMatrix();
			}
			glPopMatrix();

			while (Mouse.next())
			{
				double x = Mouse.getEventX();
				double y = 480 - Mouse.getEventY();

				if (Mouse.getEventButtonState())
				{
					if (objectGuiOpen && Mouse.getEventButton() == 0)
					{
						if (x >= 40 && x <= 72 && y >= 430 && y <= 462)
						{
							objectGuiOpen = false;
						}
						else
						{
							for (int i = 0; i < objectGui.length; i++)
							{
								if (objectGui[i] == null)
								{
									continue;
								}
								int xInGui = objectGui[i].xInGui;
								int yInGui = objectGui[i].yInGui;
								float slotX = 40 + xInGui * 53.6f + xInGui * 3;
								float slotY = 40 + yInGui * 53.6f + yInGui * 3;
								if (x >= slotX && x <= slotX + 53.6 && y >= slotY && y <= slotY + 53.6)
									selectedObject = objectGui[i].getObject();
							}
						}
					}
					else if (Mouse.getEventButton() == 0)
					{
						if (x >= 0 && x <= 40 && y >= 220 && y <= 260)
						{
							camX -= 50;
						}
						else if (x >= 600 && x <= 640 && y >= 220 && y <= 260)
						{
							camX += 50;
						}
						else if (x >= 40 && x <= 72 && y >= 430 && y <= 462)
						{
							objectGuiOpen = true;
						}
						else if (x >= 80 && x <= 112 && y >= 430 && y <= 462)
						{
							save();
						}
						else if (selectedObject != null)
						{
							if (start == null)
								start = new Vector2f((float) x + camX, (float) y);
							else
							{
								end = new Vector2f((float) x + camX, (float) y);
								selectedObject.bounds.x = (int) start.x;
								selectedObject.bounds.y = (int) start.y;
								selectedObject.bounds.width = (int) (end.x - start.x);
								selectedObject.bounds.height = (int) (end.y - start.y);
								firstLayer.add(selectedObject);
								if (selectedObject instanceof EntityStartFlag)
									for (ObjectSlot o : objectGui)
										if (o != null && o.objectClass == EntityStartFlag.class)
											o.isEnabled = false;
								selectedObject = null;
								start = null;
								end = null;
							}
						}
					}
					else if (Mouse.getEventButton() == 1 && selectedObject != null)
					{
						if (start == null)
						{
							selectedObject.bounds.x = (int) (x + camX);
							selectedObject.bounds.y = (int) y;
							selectedObject.bounds.width = (int) selectedObject.getDefaultWidth();
							selectedObject.bounds.height = (int) selectedObject.getDefaultHeight();
							System.out.println(selectedObject.bounds.width + " " + selectedObject.bounds.height);
							firstLayer.add(selectedObject);
							if (selectedObject instanceof EntityStartFlag)
								for (ObjectSlot o : objectGui)
									if (o != null && o.objectClass == EntityStartFlag.class)
										o.isEnabled = false;
							selectedObject = null;
						}
						else
						{
							end = new Vector2f((float) x + camX, (float) y);
							selectedObject.bounds.x = (int) start.x;
							selectedObject.bounds.y = (int) start.y;
							selectedObject.setWidthOrDefault(end.x - start.x);
							selectedObject.setHeightOrDefault(end.y - start.y);
							firstLayer.add(selectedObject);
							if (selectedObject instanceof EntityStartFlag)
								for (ObjectSlot o : objectGui)
									if (o != null && o.objectClass == EntityStartFlag.class)
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

	private static void save()
	{
		XMLFile f = new XMLFile("/level/", "Level2");
		f.root = new XMLNode(null, "Level");
		XMLNode root = (XMLNode) f.root;
		XMLNode info = new XMLNode(root, "Info");
		new XMLAttribute("index", "2", info);
		new XMLAttribute("name", "\"LevelMakerTest\"", info);
		new XMLAttribute("points", "150", info);

		for (Body o : firstLayer)
		{
			String name;
			if (o instanceof EntityStartFlag)
				name = "Start";
			else if (o instanceof EntityFinishFlag)
				name = "Finish";
			else
				name = o.getClass().getSimpleName();
			XMLNode node = new XMLNode(root, name);
			new XMLAttribute("posX", String.valueOf((int) o.bounds.x), node);
			new XMLAttribute("posY", String.valueOf((int) o.bounds.y), node);
			new XMLAttribute("width", String.valueOf((int) o.bounds.width), node);
			new XMLAttribute("height", String.valueOf((int) o.bounds.height), node);
		}

		XMLWriter w = new XMLWriter(f);
		w.writeToTerminal();
		w.writeToFile();
	}
}

class ObjectSlot
{
	public Class<? extends Body> objectClass;
	private Body body;
	public int xInGui;
	public int yInGui;
	public boolean isEnabled;

	public ObjectSlot(int x, int y, Class<? extends Body> objectClass)
	{
		xInGui = x;
		yInGui = y;
		isEnabled = true;
		this.objectClass = objectClass;
		try
		{
			this.body = objectClass.getConstructor(new Class[] { double.class, double.class, double.class, double.class, Level.class })
					.newInstance(new java.lang.Object[] { 0, 0, 53.6, 53.6, null });
		} catch (InstantiationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void render()
	{
		body.renderIcon();
		glPushMatrix();
		glEnable(GL_TEXTURE_2D);
		TextureManager.instance.bindTexture(TextureManager.instance.getTexture("/slot_margin_" + (isEnabled ? "enabled" : "disabled") + ".png"));
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

	public Body getObject()
	{
		if (!isEnabled)
			return null;
		try
		{
			return objectClass.getConstructor(new Class[] { double.class, double.class, double.class, double.class, Level.class }).newInstance(new java.lang.Object[] { 0, 0, 53.6, 53.6, null });
		} catch (InstantiationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}