package at.jumpandjan.level;

public abstract class LevelElement {
	private int posX;
	private int posY;
	private int width;
	private int height;

	public LevelElement(double x, double y, double width, double height) {
		setPosX((int) x);
		setPosY((int) y);
		setWidth((int) width);
		setHeight((int) height);
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public abstract at.jumpandjan.Object getElement(Level level);
}
