package at.jumpandjan.level;

import at.freschmushroom.Out;
import at.jumpandjan.EntityHaarfarbe;
import at.jumpandjan.Object;

/**
 * The building element for color
 * 
 * @author Felix
 * 
 */
public class Point extends LevelElement {

	public Point(double x, double y) {
		super(x, y, 0, 0);
	}

	@Override
	public Object getElement(Level level) {
		return new EntityHaarfarbe(getPosX(), getPosY(), level);
	}

	static {
		Out.inf(Point.class, "23.10.12", "Felix", null);
	}
}
