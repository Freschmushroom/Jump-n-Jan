package at.jumpandjan;

import at.jumpandjan.level.Level;
import at.freschmushroom.Out;

public class EntityFlag extends Entity {
	private String imgName = "";

	public EntityFlag(double x, double y, String imgName, Level level) {
		super(x, y, 32, 64, level);
		isGravityApplied = false;
		this.imgName = imgName;
	}

	public EntityFlag(double x, double y, double width, double height,
			String imgName, Level level) {
		super(x, y, width, height, level);
		isGravityApplied = false;
		this.imgName = imgName;
	}

	public void render() {
		super.render(imgName, bounds.width, bounds.height, bounds.x, bounds.y,
				32f, 64f, 32, 64, state);
	}

	public void spawn() {
		JumpAndJan.getPlayer().bounds.x = this.bounds.x;
		JumpAndJan.getPlayer().bounds.y = this.bounds.y + this.bounds.height
				- JumpAndJan.getPlayer().bounds.height - 100;
	}

	@Override
	public void update() {
		super.update();
	}

	public void renderIcon() {
		double offsetX = (bounds.width - 16) / 2;
		double offsetY = (bounds.height - 32) / 2;
		super.render(imgName, 16, 32, offsetX, offsetY, 32, 64, 32, 64, state);
	}

	public double getDefaultWidth() {
		return 32;
	}

	public double getDefaultHeight() {
		return 64;
	}

	public boolean hasDefaultWidth() {
		return true;
	}

	public boolean hasDefaultHeight() {
		return true;
	}

	static {
		Out.inf(EntityFlag.class, "22.10.12", "Michael", null);
	}
}
