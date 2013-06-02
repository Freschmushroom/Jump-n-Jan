package at.jumpandjan.level;

import java.io.Serializable;

import at.freschmushroom.Out;
import at.jumpandjan.EntityFinishFlag;
import at.jumpandjan.Object;

/**
 * The level element representing the Finish Flag
 * @author Felix
 *
 */
public class FinishFlag extends LevelElement implements Serializable {
	
	public FinishFlag(double posX, double posY, double width, double height) {
		super(posX, posY, width, height);
	}

	@Override
	public Object getElement(Level level) {
		return new EntityFinishFlag(getPosX(), getPosY(), getWidth(), getHeight(), level);
	}
	
	static {
		Out.inf(FinishFlag.class, "01.06.31", "Felix", null);
	}
}
