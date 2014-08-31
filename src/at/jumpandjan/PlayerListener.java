package at.jumpandjan;

import at.freschmushroom.Out;
import at.jumpandjan.entity.Entity;
import at.jumpandjan.entity.EntityListener;
import at.jumpandjan.gui.GuiGameOver;

/**
 * Provides methods for listening to the player
 * 
 * @author Michael
 *
 */
public class PlayerListener implements EntityListener
{

	@Override
	public void entityHit(Entity entityHit, DamageSource damageSource)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void entityHitByEntity(Entity entityHit, DamageSourceEntity damageSource)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void entityHurt(Entity entityHurt, int amount, boolean isLethal)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void entityKilled(Entity entityKilled)
	{
		entityKilled.level.game.level = null;
		JumpAndJan.openGui(new GuiGameOver(1, 0, 0, "You died.", entityKilled.level.game));
		throw new InterruptUpdateException();
	}

	@Override
	public void entityHealed(Entity entityHealed, int amount)
	{
		// TODO Auto-generated method stub

	}

	static
	{
		Out.inf(PlayerListener.class, "01.06.2013", "Michael", null);
	}
}
