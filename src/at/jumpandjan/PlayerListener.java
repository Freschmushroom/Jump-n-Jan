package at.jumpandjan;

import at.jumpandjan.gui.GuiGameOver;

public class PlayerListener implements EntityListener {

	@Override
	public void entityHit(Entity entityHit, DamageSource damageSource) {
		// TODO Auto-generated method stub

	}

	@Override
	public void entityHitByEntity(Entity entityHit,
			DamageSourceEntity damageSource) {
		// TODO Auto-generated method stub

	}

	@Override
	public void entityHurt(Entity entityHurt, int amount, boolean isLethal) {
		// TODO Auto-generated method stub

	}

	@Override
	public void entityKilled(Entity entityKilled) {
		JumpAndJan.openGui(new GuiGameOver(1, 0, 0, "You died."));
	}

	@Override
	public void entityHealed(Entity entityHealed, int amount) {
		// TODO Auto-generated method stub

	}

}
