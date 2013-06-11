package at.jumpandjan;

import static org.lwjgl.opengl.GL11.glColor3f;
import at.jumpandjan.level.Level;

public class EntityJannyBunny extends Entity {

	private int cd = 0;
	private int healCd = 0;
	private int cdJump;
	
	public EntityJannyBunny(double x, double y, double width, double height,
			Level level) {
		super(x, y, width, height, level);
	}

	public void render() {
		this.renderHealthbar();
		float maxPoints = level.getSecond_Point().size();

		glColor3f(1, (maxPoints - JumpAndJan.getPlayer().getPoints())
				/ maxPoints, (maxPoints - JumpAndJan.getPlayer().getPoints())
				/ maxPoints);
		this.render("/bunny.png", this.bounds.width, this.bounds.height,
				this.bounds.x, this.bounds.y, 0, 0, 1, 1, 1, 1, this.state);
		glColor3f(1, 1, 1);
	}

	public void update() {
		cd--;
		if (cd <= 1) {
			cd = 0;
		}
		healCd--;
		if (healCd <= 1) {
			cd = 0;
			if (JumpAndJan.getPlayer().hp < JumpAndJan.getPlayer().getMaxHP()) {
				JumpAndJan.getPlayer().hurt(-100, this);
				cd = 600;
			}
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
		super.update();
		float playerX = (float) JumpAndJan.getPlayer().getPivotX();
		float thisX = (float) this.getPivotX();
		float difference = playerX - thisX;
		if (Math.abs(difference) > JumpAndJan.getPlayer().bounds.width / 2
				+ this.bounds.width / 2) {
			motion.x = difference / Math.abs(difference);
		}
	}
	
	public void collide(at.jumpandjan.Object withObject) {
		double percentage;
		if (level.getSecond_Point().size() != 0) {
			percentage =  JumpAndJan.getPlayer().getPoints() / (double) level.getSecond_Point().size();
		} else {
			percentage = 0.5;
		}
		super.collide(withObject);
		if (cd <= 0 && withObject instanceof Entity && !(withObject instanceof EntityPlayer)) {
			cd = 600;
			((Entity) withObject).hurt((int) (percentage * 10));
		}
	}
	
	public boolean checkCollisionWith(at.jumpandjan.Object withObject) {
		return super.checkCollisionWith(withObject) && !(withObject instanceof EntityPlayer);
	}
}