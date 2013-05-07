package at.jumpandjan.level;

import java.io.Serializable;

import at.jumpandjan.EntityFinishFlag;
import at.jumpandjan.Object;

public class FinishFlag extends LevelElement implements Serializable {
	
	public FinishFlag(double posX, double posY, double width, double height) {
		super(posX, posY, width, height);
	}

	@Override
	public Object getElement(Level level) {
		return new EntityFinishFlag(getPosX(), getPosY(), getWidth(), getHeight(), level);
	}
}
