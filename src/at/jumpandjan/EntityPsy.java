package at.jumpandjan;

import at.freschmushroom.Out;
import at.freschmushroom.audio.SoundContainer;
import at.jumpandjan.level.Level;

public class EntityPsy extends Entity {

	private int cdJump;
	private int cdAnimation;
	private int cdAttack;
	private boolean animState;

	public EntityPsy(double x, double y, Level level) {
		super(x, y, 32, 64, level);
	}

	@Override
	public void update() {
		super.update();
		this.motion.x = 0;
		if (Math.abs(JumpAndJan.getPlayer().getPivotX() - this.getPivotX()) < 320
				&& Math.abs(JumpAndJan.getPlayer().getPivotX()
						- this.getPivotX()) > 1) {
			motion.x = (float) ((JumpAndJan.getPlayer().getPivotX() - this
					.getPivotX())
					/ Math.abs(JumpAndJan.getPlayer().getPivotX()
							- this.getPivotX()) * 1.5f);
		}
		if (JumpAndJan.getPlayer().bounds.y
				+ JumpAndJan.getPlayer().bounds.height < this.bounds.y
				&& onGround && JumpAndJan.getPlayer().onGround) {
			if (cdJump <= 0) {
				motion.y = -20;
				cdJump = 10;
			} else
				cdJump--;
		}
		cdAttack--;
	}

	public void collide(at.jumpandjan.Object withObject) {
		if (withObject instanceof EntityPlayer && cdAttack <= 0) {
			JumpAndJan.getPlayer().hurt(10);
			cdAttack = 120;
			SoundContainer.play("opp_psy_attack");
		}
	}

	@Override
	public void render() {
		super.renderHealthbar();
		super.render("/Opp_Psy.png", bounds.width, bounds.height, bounds.x,
				bounds.y, 32, 64, state ^ animState);
		if (cdAnimation == 0) {
			animState = !animState;
			cdAnimation = 10;
		}
		cdAnimation--;
	}

	static {
		Out.inf(EntityPsy.class, "23.10.12", "Felix", null);
	}
}
