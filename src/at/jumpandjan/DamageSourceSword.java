package at.jumpandjan;

import at.freschmushroom.Out;

/**
 * The damage source by a sword
 * @author Michael
 *
 */
public class DamageSourceSword extends DamageSourceEntity {
	/**
	 * The weilder
	 */
	private Entity wielder;
	
	public DamageSourceSword(int damage, EntitySword sword, Entity wielder) {
		super(damage, sword);
		this.wielder = wielder;
	}

	/**
	 * Returns the wielder
	 * @return The wielder
	 */
	public Entity getWielder() {
		return wielder;
	}

	/**
	 * Sets the wielder
	 * @param wielder The wielder
	 */
	public void setWielder(Entity wielder) {
		this.wielder = wielder;
	}
	
	static {
		Out.inf(DamageSourceSword.class, "01.06.2013", "Michael", null);
	}
}
