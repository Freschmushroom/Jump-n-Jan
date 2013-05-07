package at.jumpandjan.level;

import java.io.Serializable;

import at.freschmushroom.Out;

public class Wall extends LevelElement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1334533100714027080L;
	private int pos;
	private int length;
	private int height;

	public Wall(double x, double y, double width, double height) {
		super(x, y, width, height);
	}

	public int getPos() {
		return pos;
	}

	public int getLength() {
		return length;
	}

	@Override
	public at.jumpandjan.Object getElement(Level level) {
		return new at.jumpandjan.Wall(pos, height,
				10, length, level);
	}

	static {
		Out.inf(Wall.class, "23.10.12", "Felix", null);
	}
}
