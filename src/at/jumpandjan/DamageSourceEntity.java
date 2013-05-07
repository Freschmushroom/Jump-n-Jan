package at.jumpandjan;

public class DamageSourceEntity extends DamageSource {
	private Entity sourceEntity;
	
	public DamageSourceEntity(int damage, Entity sourceEntity) {
		super(damage);
		this.sourceEntity = sourceEntity;
	}

	public Entity getSourceEntity() {
		return sourceEntity;
	}

	public void setSourceEntity(Entity sourceEntity) {
		this.sourceEntity = sourceEntity;
	}
}
