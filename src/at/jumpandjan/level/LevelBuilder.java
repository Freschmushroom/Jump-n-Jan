package at.jumpandjan.level;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import at.freschmushroom.Out;
import at.freschmushroom.xml.XMLAttribut;
import at.freschmushroom.xml.XMLElement;
import at.freschmushroom.xml.XMLFile;
import at.freschmushroom.xml.XMLNode;
import at.freschmushroom.xml.XMLTag;
import at.jumpandjan.EntityStartFlag;

public class LevelBuilder implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -852392770449393352L;
	private ArrayList<LevelElement> elements = new ArrayList<LevelElement>();
	private int index = 0;
	private String name = "";
	private long points = 0;
	private LevelElement start;
	private String[] unlocks;

	public static LevelBuilder load(String name) {
		File file = new File(name);
		if (!file.exists()) {
			file = new File("level/" + name);
		}
		XMLFile f = new XMLFile(file);
		LevelBuilder b = new LevelBuilder();
		XMLNode r = (XMLNode) f.root;
		XMLNode info = (XMLNode) r.getChild("Info", false);
		b.index = Integer
				.parseInt(((XMLAttribut) info.getChild("index", false))
						.getValue());
		b.name = ((XMLAttribut) info.getChild("name", false)).getValue();
		b.points = Integer.parseInt(((XMLAttribut) info.getChild("points",
				false)).getValue());
		b.unlocks = ((XMLAttribut) info.getChild("unlock", false)).getValue().split(";");
		// b.finish = Integer.parseInt(((XMLTag) r.getChild("Finish", false))
		// .getValue());
		for (XMLElement el : r.getChildren()) {
			if (el instanceof XMLNode) {
				XMLNode n = (XMLNode) el;
				if (n.getName().equalsIgnoreCase("Info"))
					continue;
				if (n.getName().equalsIgnoreCase("start"))
				{
					double posX, posY, width, height;
					posX = Double.parseDouble(((XMLAttribut) n.getChild("posX", false)).getValue());
					posY = Double.parseDouble(((XMLAttribut) n.getChild("posY", false)).getValue());
					width = Double.parseDouble(((XMLAttribut) n.getChild("width", false)).getValue());
					height = Double.parseDouble(((XMLAttribut) n.getChild("height", false)).getValue());
					b.start = new FlexibleElement(posX, posY, width, height, EntityStartFlag.class);
				}
				// if(n.getName().equalsIgnoreCase("wall")) {
				// int pos, length, height;
				// pos = Integer.parseInt(((XMLAttribut) n.getChild("pos",
				// false)).getValue());
				// length = Integer.parseInt(((XMLAttribut) n.getChild("length",
				// false)).getValue());
				// height = Integer.parseInt(((XMLAttribut) n.getChild("height",
				// false)).getValue());
				// b.elements.add(new Wall(pos, length, height));
				// } else if(n.getName().equalsIgnoreCase("spawn")) {
				// int pos;
				// String type, kind;
				// pos = Integer.parseInt(((XMLAttribut) n.getChild("pos",
				// false)).getValue());
				// type = ((XMLAttribut) n.getChild("type", false)).getValue();
				// kind = ((XMLAttribut) n.getChild("kind", false)).getValue();
				// b.elements.add(new Spawn(pos, type, kind));
				// } else if(n.getName().equalsIgnoreCase("floor")) {
				// int pos, length, height;
				// pos = Integer.parseInt(((XMLAttribut) n.getChild("pos",
				// false)).getValue());
				// length = Integer.parseInt(((XMLAttribut) n.getChild("length",
				// false)).getValue());
				// height = Integer.parseInt(((XMLAttribut) n.getChild("height",
				// false)).getValue());
				// b.elements.add(new Floor(pos, length, height));
				if (n.getName().equalsIgnoreCase("point")) {
					int x, y;
					x = Integer.parseInt(((XMLAttribut) n.getChild("x", false))
							.getValue());
					y = Integer.parseInt(((XMLAttribut) n.getChild("y", false))
							.getValue());
					b.elements.add(new Point(x, y));
					continue;
				} else if(n.getName().equalsIgnoreCase("spawn")) {
					double posX, posY;
					String type, kind;
					posX = Integer.parseInt(((XMLAttribut) n.getChild("posX",
							false)).getValue());
					posY = Integer.parseInt(((XMLAttribut) n.getChild("posY",
							false)).getValue());
					type = ((XMLAttribut) n.getChild("type", false)).getValue();
					kind = ((XMLAttribut) n.getChild("kind", false)).getValue();
					b.elements.add(new Spawn(posX, posY, type, kind));
					continue;
				}
				try {
					Class<? extends LevelElement> c = (Class<? extends LevelElement>) Class
							.forName("at.jumpandjan.level." + n.getName());
					double x, y, width, height;
					x = Integer.parseInt(((XMLAttribut) n.getChild("posX",
							false)).getValue());
					y = Integer.parseInt(((XMLAttribut) n.getChild("posY",
							false)).getValue());
					width = Integer.parseInt(((XMLAttribut) n.getChild("width",
							false)).getValue());
					height = Integer.parseInt(((XMLAttribut) n.getChild(
							"height", false)).getValue());
					b.elements.add(c.getConstructor(
							new Class[] { double.class, double.class,
									double.class, double.class }).newInstance(
							new Object[] { x, y, width, height }));
				} catch (Exception e) {
					try {
						Class<? extends at.jumpandjan.Object> c = (Class<? extends at.jumpandjan.Object>) Class
								.forName("at.jumpandjan." + n.getName());
						double x, y, width, height;
						x = Integer.parseInt(((XMLAttribut) n.getChild("posX",
								false)).getValue());
						y = Integer.parseInt(((XMLAttribut) n.getChild("posY",
								false)).getValue());
						width = Integer.parseInt(((XMLAttribut) n.getChild(
								"width", false)).getValue());
						height = Integer.parseInt(((XMLAttribut) n.getChild(
								"height", false)).getValue());
						b.elements.add(new FlexibleElement(x, y, width, height,
								c));
					} catch (Exception e1) {
						System.err.println("INVALID CODE:");
						String[] lines = n.getLines();
						for (String s : lines)
							System.err.println(s);
					}
				}
			}
		}
		return b;
	}

	public ArrayList<LevelElement> getElements() {
		return elements;
	}

	public int getIndex() {
		return index;
	}

	public String getName() {
		return name;
	}

	public long getPoints() {
		return points;
	}

	public LevelElement getStart() {
		return start;
	}

	static {
		Out.inf(LevelBuilder.class, "23.10.12", "Felix", null);
	}

	public String[] getUnlocks() {
		return unlocks;
	}
}
