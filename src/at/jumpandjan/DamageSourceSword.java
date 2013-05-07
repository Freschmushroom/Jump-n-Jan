package at.jumpandjan;

public class DamageSourceSword extends DamageSourceEntity {
	private Entity wielder;
	
	public DamageSourceSword(int damage, EntitySword sword, Entity wielder) {
		super(damage, sword);
		this.wielder = wielder;
	}

	public Entity getWielder() {
		return wielder;
	}

	public void setWielder(Entity wielder) {
		this.wielder = wielder;
	}
}
