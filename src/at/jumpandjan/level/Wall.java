package at.jumpandjan.level;

import java.io.Serializable;

import at.freschmushroom.Out;

/**
 * The Element representing walls
 * @author Felix
 *
 */
public class Wall extends LevelElement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1334533100714027080L;

	public Wall(double x, double y, double width, double height) {
		super(x, y, width, height);
	}

	@Override
	public at.jumpandjan.Object getElement(Level level) {
		return new at.jumpandjan.Wall(getPosX(), getPosY(),
				getWidth(), 10, level);
	}

	static {
		Out.inf(Wall.class, "23.10.12", "Felix", null);
	}
}
