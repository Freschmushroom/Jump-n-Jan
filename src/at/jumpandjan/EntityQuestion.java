package at.jumpandjan;

import at.freschmushroom.Intro;
import at.freschmushroom.Out;
import at.jumpandjan.level.Level;

public class EntityQuestion extends Entity {

	private double destX;
	private double destY;

	private double vecX;
	private double vecY;

	public EntityQuestion(double x, double y, Level level) {
		super(x, y, 16, 16, level);
		isGravityApplied = false;
		destX = Constants.getActualLevel().getPlayer().bounds.x;
		destY = Constants.getActualLevel().getPlayer().bounds.y;
		vecX = (x - destX) / 20;
		vecY = (y - destY) / 20;

	}

	@Override
	public void update() {
		super.update();
		bounds.x -= vecX;
		bounds.y -= vecY;
		// if(x >= Constants.getActualLevel().getFinish() || x <
		// Constants.getActualLevel().getStart() || y <= 0 || y >= 480 +
		// Intro.vertMvmt) {
		// this.kill(this);
		// }
	}

	public void collide(at.jumpandjan.Object withObject) {
		if (withObject instanceof EntityPlayer) {
			Constants.getActualLevel().getPlayer().hurt(10);
			this.kill(this);
		}
	}

	@Override
	public void render() {
		super.render("/entity_quest.png", bounds.width, bounds.height,
				bounds.x, bounds.y, 16, 16, false);
	}

	static {
		Out.inf(EntityQuestion.class, "08.12.12", "Felix", null);
	}

}
