package at.jumpandjan;

public interface EntityListener {
	public void entityHit(Entity entityHit, DamageSource damageSource);
	public void entityHitByEntity(Entity entityHit, DamageSourceEntity damageSource);
	public void entityHurt(Entity entityHurt, int amount, boolean isLethal);
	
	public void entityKilled(Entity entityKilled);
	
	public void entityHealed(Entity entityHealed, int amount);
}
