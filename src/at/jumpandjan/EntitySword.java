package at.jumpandjan;

import at.jumpandjan.level.Level;

/**
 * Basically only the hitbox of a player's sword.
 * 
 * @author Michael P.
 * 
 */
public class EntitySword extends at.jumpandjan.Entity {
	/**
	 * @see {@link #getDamage()}
	 * @see {@link #setDamage(int)}
	 */
	private int damage;

	/**
	 * The source of the sword. Passed on for further processing to hit units.
	 */
	private EntityPlayer wielder;

	public EntitySword(double x, double y, double width, double height,
			Level level, int damage, EntityPlayer parent) {
		super(x, y, width, height, level);
		setDamage(damage);
		isGravityApplied = false;
		wielder = parent;
	}

	/**
	 * The amount of hearts being subtracted from each entity hit on hit.
	 * 
	 * @return The damage in hearts.
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * Sets the damage of the sword
	 * 
	 * @param The
	 *            amount of hearts being subtracted each hit.
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}

	/**
	 * Overwritten and empty as this Entity shall under no circumstances be
	 * rendered.<br />
	 * It is solely logical.
	 */
	public void render() {
	}

	@Override
	public void update() {
		super.update();
		for (at.jumpandjan.Object o : collisions) {
			if (!(o instanceof Entity))
				continue;
			Entity e = (Entity) o;
			e.hurt(damage, wielder);
		}
	}
}
