package at.jumpandjan.entity;

import at.jumpandjan.DamageSource;
import at.jumpandjan.DamageSourceEntity;

/**
 * Provides methods for listening to an entity
 * 
 * @author Michael
 *
 */
public interface EntityListener
{
	public void entityHit(Entity entityHit, DamageSource damageSource);

	public void entityHitByEntity(Entity entityHit, DamageSourceEntity damageSource);

	public void entityHurt(Entity entityHurt, int amount, boolean isLethal);

	public void entityKilled(Entity entityKilled);

	public void entityHealed(Entity entityHealed, int amount);
}
