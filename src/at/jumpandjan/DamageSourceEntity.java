package at.jumpandjan;

import at.freschmushroom.Out;
import at.jumpandjan.entity.Entity;

/**
 * A damage source which was caused by a source
 * 
 * @author Michael
 *
 */
public class DamageSourceEntity extends DamageSource
{
	/**
	 * The source
	 */
	private Entity sourceEntity;

	public DamageSourceEntity(int damage, Entity sourceEntity)
	{
		super(damage);
		this.sourceEntity = sourceEntity;
	}

	/**
	 * The source
	 * 
	 * @return The source entity
	 */
	public Entity getSourceEntity()
	{
		return sourceEntity;
	}

	/**
	 * Sets the source entity
	 * 
	 * @param sourceEntity
	 *            The source entity
	 */
	public void setSourceEntity(Entity sourceEntity)
	{
		this.sourceEntity = sourceEntity;
	}

	static
	{
		Out.inf(DamageSourceEntity.class, "01.06.2013", "Michael", null);
	}
}
