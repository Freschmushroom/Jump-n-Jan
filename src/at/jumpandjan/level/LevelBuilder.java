package at.jumpandjan.level;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import at.freschmushroom.Out;
import at.freschmushroom.xml.XMLAttribute;
import at.freschmushroom.xml.XMLElement;
import at.freschmushroom.xml.XMLFile;
import at.freschmushroom.xml.XMLNode;
import at.jumpandjan.Body;
import at.jumpandjan.entity.EntityStartFlag;

/**
 * The level builder
 * 
 * @author Felix
 * @author Michael
 * 
 */
public class LevelBuilder implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -852392770449393352L;
	/**
	 * All building elements
	 */
	private ArrayList<LevelElement> elements = new ArrayList<LevelElement>();
	/**
	 * The index
	 */
	private int index = 0;
	/**
	 * The name
	 */
	private String name = "";
	/**
	 * The amount of points
	 */
	private long points = 0;
	/**
	 * The start flag
	 */
	private LevelElement start;
	/**
	 * The levels being unlocked by completing this one
	 */
	private String[] unlocks;

	/**
	 * Loads a level from the specified file
	 * 
	 * @param name
	 *            the name of the level
	 * @return The levelbuilder instance
	 */
	@SuppressWarnings("unchecked")
	public static LevelBuilder load(String name)
	{
		Out.line("Loading level " + name + " . . .");
		File file = new File(name);
		if (!file.exists())
		{
			Out.err("File " + file.getPath() + " not found. Looking for alternative locations");
			file = new File("level/" + name);
			if (!file.exists())
			{
				Out.err("Level " + name + " could not be loaded: Missing required file.");
				return null;
			}
		}
		XMLFile f = new XMLFile(file);
		LevelBuilder b = new LevelBuilder();
		XMLNode r = (XMLNode) f.root;
		XMLNode info = (XMLNode) r.getChild("Info", false);
		b.index = Integer.parseInt(((XMLAttribute) info.getChild("index", false)).getValue());
		b.name = ((XMLAttribute) info.getChild("name", false)).getValue();
		b.points = Integer.parseInt(((XMLAttribute) info.getChild("points", false)).getValue());
		String unlocks = ((XMLAttribute) info.getChild("unlock", false)).getValue();
		if (unlocks.contains(";"))
		{
			b.unlocks = unlocks.split(";");
		}
		else
		{
			b.unlocks = new String[] { unlocks };
		}
		for (XMLElement el : r.getChildren())
		{
			if (el instanceof XMLNode)
			{
				XMLNode n = (XMLNode) el;
				if (n.getName().equalsIgnoreCase("Info"))
				{
					continue;
				}
				if (n.getName().equalsIgnoreCase("start"))
				{
					double posX, posY, width, height;
					posX = Double.parseDouble(((XMLAttribute) n.getChild("posX", false)).getValue());
					posY = Double.parseDouble(((XMLAttribute) n.getChild("posY", false)).getValue());
					width = Double.parseDouble(((XMLAttribute) n.getChild("width", false)).getValue());
					height = Double.parseDouble(((XMLAttribute) n.getChild("height", false)).getValue());
					b.start = new GenericElement(posX, posY, width, height, EntityStartFlag.class);
					continue;
				}
				if (n.getName().equalsIgnoreCase("point"))
				{
					int x, y;
					x = Integer.parseInt(((XMLAttribute) n.getChild("x", false)).getValue());
					y = Integer.parseInt(((XMLAttribute) n.getChild("y", false)).getValue());
					b.elements.add(new Point(x, y));
					continue;
				}
				else if (n.getName().equalsIgnoreCase("spawn"))
				{
					double posX, posY;
					String type, kind;
					posX = Integer.parseInt(((XMLAttribute) n.getChild("posX", false)).getValue());
					posY = Integer.parseInt(((XMLAttribute) n.getChild("posY", false)).getValue());
					type = ((XMLAttribute) n.getChild("type", false)).getValue();
					kind = ((XMLAttribute) n.getChild("kind", false)).getValue();
					b.elements.add(new Spawn(posX, posY, type, kind));
					continue;
				}
				Class<?> elementClass;
				try
				{
					elementClass = Class.forName(n.getName());
				} catch (Exception e)
				{
					try
					{
						elementClass = Class.forName("at.jumpandjan." + n.getName());
					} catch (Exception e1)
					{
						try
						{
							elementClass = Class.forName("at.jumpandjan.entity." + n.getName());
						} catch (Exception e2)
						{
							System.err.println("COULD NOT LOAD ELEMENT TYPE " + n.getName());
							continue;
						}
					}
				}
				try
				{
					int x, y, width, height;
					x = Integer.parseInt(((XMLAttribute) n.getChild("posX", false)).getValue());
					y = Integer.parseInt(((XMLAttribute) n.getChild("posY", false)).getValue());
					width = Integer.parseInt(((XMLAttribute) n.getChild("width", false)).getValue());
					height = Integer.parseInt(((XMLAttribute) n.getChild("height", false)).getValue());
					b.elements.add(new GenericElement(x, y, width, height, (Class<? extends Body>) elementClass));
				} catch (Exception e)
				{
					System.err.println("EXCEPTION " + e + " THROWN AT");
					System.err.println("INVALID PARAMETERS: ");
					String[] lines = n.getLines();
					for (String s : lines)
					{
						System.err.println(s);
					}
				}
			}
		}
		return b;
	}

	/**
	 * Returns all building elements
	 * 
	 * @return All building elements
	 */
	public ArrayList<LevelElement> getElements()
	{
		return elements;
	}

	/**
	 * Returns the index
	 * 
	 * @return The index
	 */
	public int getIndex()
	{
		return index;
	}

	/**
	 * Returns the name
	 * 
	 * @return The name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Returns the points
	 * 
	 * @return The points
	 */
	public long getPoints()
	{
		return points;
	}

	/**
	 * Returns the start flag
	 * 
	 * @return The start flag
	 */
	public LevelElement getStart()
	{
		return start;
	}

	static
	{
		Out.inf(LevelBuilder.class, "23.10.12", "Felix", null);
	}

	/**
	 * Returns the unlocking levels
	 * 
	 * @return The unlocking levels
	 */
	public String[] getUnlocks()
	{
		return unlocks;
	}
}
