package at.jumpandjan;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class Object {
	public List<Object> collision = new ArrayList<Object>();
	public double x;
	public double y;
	public double width;
	public double height;
	public double motionX;
	public double motionY;
	public boolean onGround;

	public Object(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void update() {
		motionY += 1.4;
		label1: for (int i = 0; i < 200; i++) {
			double x = this.x + motionX * 0.005;
			for (Object o : collision) {
				if (x >= o.x && x <= o.x + o.width && y >= o.y
						&& y <= o.y + o.height || x + width >= o.x
						&& x + width <= o.x + o.width && y >= o.y
						&& y <= o.y + o.height || x + width >= o.x
						&& x + width <= o.x + o.width && y + height >= o.y
						&& y + height <= o.y + o.height || x >= o.x
						&& x <= o.x + o.width && y + height >= o.y
						&& y + height <= o.y + o.height) {
					motionX = 0;
					break label1;
				}
			}
			this.x = x;
		}
		onGround = false;
		label2: for (int i = 0; i < 200; i++) {
			double y = this.y + motionY * 0.005;
			for (Object o : collision) {
				if (x >= o.x && x <= o.x + o.width && y >= o.y
						&& y <= o.y + o.height || x + width >= o.x
						&& x + width <= o.x + o.width && y >= o.y
						&& y <= o.y + o.height || x + width >= o.x
						&& x + width <= o.x + o.width && y + height >= o.y
						&& y + height <= o.y + o.height || x >= o.x
						&& x <= o.x + o.width && y + height >= o.y
						&& y + height <= o.y + o.height) {
					motionY = 0;
					onGround = true;
					break label2;
				}
			}
			this.y = y;
		}
	}
}
