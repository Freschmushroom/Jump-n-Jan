package at.jumpandjan.level;

import java.io.Serializable;

import at.freschmushroom.Out;

/**
 * The building element representing the floors
 * @author Felix
 *
 */
public class Floor extends LevelElement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3675690689629199967L;

	public Floor(double x, double y, double width, double height) {
		super(x, y, width, height);
	}

	@Override
	public at.jumpandjan.Object getElement(Level level) {
		return new at.jumpandjan.Floor(getPosX(), getPosY(), getWidth(), getHeight(), level);
	}
	
	static {
		Out.inf(Floor.class, "23.10.12", "Felix", null);
	}
}
