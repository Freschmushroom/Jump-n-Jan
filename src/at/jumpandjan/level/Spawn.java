package at.jumpandjan.level;

import java.io.Serializable;

import at.freschmushroom.Out;
import at.jumpandjan.EntityHildegard;
import at.jumpandjan.EntityMeatball;
import at.jumpandjan.EntityPedobaer;
import at.jumpandjan.EntityPreym;
import at.jumpandjan.EntityPsy;
import at.jumpandjan.EntitySpaghetti;
import at.jumpandjan.EntityUnicorn;
import at.jumpandjan.EntityWaitrose;

public class Spawn extends LevelElement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6077068898560114829L;
	private String type;
	private String kind;

	@Override
	public at.jumpandjan.Object getElement(Level level) {
		if (type.equals("opp") && kind.equals("meatball")) {
			return new EntityMeatball(getPosX(), getPosY(), level);
		} else if (type.equals("opp") && kind.equals("waitrose")) {
			return new EntityWaitrose(getPosX(), getPosY(), level);
		} else if (type.equals("opp") && kind.equals("psy")) {
			return new EntityPsy(getPosX(), getPosY(), level);
		} else if (type.equals("opp") && kind.equals("pedo")) {
			return new EntityPedobaer(getPosX(), getPosY(), level);
		} else if (type.equals("opp") && kind.equals("spaghetti")) {
			return new EntitySpaghetti(getPosX(), getPosY(), level);
		} else if (type.equals("opp") && kind.equals("unicorn")) {
			return new EntityUnicorn(getPosX(), getPosY(), 48, 48, level);
		} else if (type.equals("opp") && kind.equals("preym")) {
			return new EntityPreym(getPosX(), getPosY(), level);
		} else if (type.equals("opp") && kind.equals("hilde")) {
			return new EntityHildegard(getPosX(), getPosY(), level);
		}
		Out.err("Mob " + type + "_" + kind
				+ " does not exist. Spawning Meatball instead.");
		return new EntityMeatball(getPosX(), getPosY(), level);
	}

	public String getType() {
		return type;
	}

	public String getKind() {
		return kind;
	}

	public Spawn(double posX, double posY, String type, String kind) {
		super(posX, posY, 0, 0);
		this.type = type;
		this.kind = kind;
	}

	static {
		Out.inf(Spawn.class, "23.10.12", "Felix", null);
	}
}
