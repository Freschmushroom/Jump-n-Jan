package at.jumpandjan;

import at.freschmushroom.Out;

/**
 * A damage source
 * @author Michael
 *
 */
public class DamageSource {
	/**
	 * The damage inflicted
	 */
	private int damage;
	
	public DamageSource(int damage) {
		this.damage = damage;
	}
	
	/**
	 * The damage inflicted
	 * @return The damage inflicted
	 */
	public int getDamage() {
		return damage;
	}
	
	static {
		Out.inf(DamageSource.class, "01.06.2013", "Michael", null);
	}
}
