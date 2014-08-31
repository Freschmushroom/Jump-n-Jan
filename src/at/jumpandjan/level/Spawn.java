package at.jumpandjan.level;

import java.io.Serializable;

import at.freschmushroom.Out;
import at.jumpandjan.Body;
import at.jumpandjan.entity.EntityHildegard;
import at.jumpandjan.entity.EntityMeatball;
import at.jumpandjan.entity.EntityPedobaer;
import at.jumpandjan.entity.EntityPreym;
import at.jumpandjan.entity.EntityPsy;
import at.jumpandjan.entity.EntitySpaghetti;
import at.jumpandjan.entity.EntityUnicorn;
import at.jumpandjan.entity.EntityWaitrose;

/**
 * The Element which spawns entities later
 * 
 * @author Felix
 *
 */
public class Spawn extends LevelElement implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6077068898560114829L;
	/**
	 * The type of entity
	 */
	private String type;
	/**
	 * The kind of entity
	 */
	private String kind;

	@Override
	public Body getElement(Level level)
	{
		if (type.equals("opp") && kind.equals("meatball"))
		{
			return new EntityMeatball(getPosX(), getPosY(), level);
		}
		else if (type.equals("opp") && kind.equals("waitrose"))
		{
			return new EntityWaitrose(getPosX(), getPosY(), level);
		}
		else if (type.equals("opp") && kind.equals("psy"))
		{
			return new EntityPsy(getPosX(), getPosY(), level);
		}
		else if (type.equals("opp") && kind.equals("pedo"))
		{
			return new EntityPedobaer(getPosX(), getPosY(), level);
		}
		else if (type.equals("opp") && kind.equals("spaghetti"))
		{
			return new EntitySpaghetti(getPosX(), getPosY(), level);
		}
		else if (type.equals("opp") && kind.equals("unicorn"))
		{
			return new EntityUnicorn(getPosX(), getPosY(), 48, 48, level);
		}
		else if (type.equals("opp") && kind.equals("preym"))
		{
			return new EntityPreym(getPosX(), getPosY(), level);
		}
		else if (type.equals("opp") && kind.equals("hilde"))
		{
			return new EntityHildegard(getPosX(), getPosY(), level);
		}
		Out.err("Mob " + type + "_" + kind + " does not exist. Spawning Meatball instead.");
		return new EntityMeatball(getPosX(), getPosY(), level);
	}

	/**
	 * Returns the type of entity
	 * 
	 * @return The type of entity
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * Returns the kind of entity
	 * 
	 * @return The kind of entity
	 */
	public String getKind()
	{
		return kind;
	}

	public Spawn(double posX, double posY, String type, String kind)
	{
		super(posX, posY, 0, 0);
		this.type = type;
		this.kind = kind;
	}

	static
	{
		Out.inf(Spawn.class, "23.10.12", "Felix", null);
	}
}
