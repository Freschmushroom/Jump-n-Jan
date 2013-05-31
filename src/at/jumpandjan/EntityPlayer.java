package at.jumpandjan;

import static org.lwjgl.opengl.GL11.*;

import java.util.List;

import at.freschmushroom.TextureManager;
import at.jumpandjan.level.Level;
import at.freschmushroom.Out;

/**
 * The Player's Entity
 * 
 * @author Michael P.
 * 
 */
public class EntityPlayer extends Entity {
	/**
	 * Constant for the state when the player isn't attacking
	 */
	public static final byte NO_ATTACK_ANIMATION = 0;

	/**
	 * Constant for the state when the player is charging for attack state
	 */
	public static final byte BEGIN_ATTACK_ANIMATION = 1;

	/**
	 * Constant for the state when the player is in attack state
	 */
	public static final byte IN_ATTACK_ANIMATION = 2;

	/**
	 * Constant for the state when the player stopped attack state
	 */
	public static final byte END_ATTACK_ANIMATION = 3;

	/**
	 * The current attack state; either 0, 1, 2, or 3.
	 * 
	 * @see {@link #NO_ATTACK_ANIMATION}, {@link #BEGIN_ATTACK_ANIMATION},
	 *      {@link #IN_ATTACK_ANIMATION}, {@link #END_ATTACK_ANIMATION}
	 */
	public byte attackState;

	/**
	 * The amount of ticks the animation has lasted.
	 */
	public int attackAnimCount;

	/**
	 * The sword of the player :IFYOUKNOWWHATIMEAN:
	 */
	private EntitySword sword = null;

	/**
	 * The amount of colors the player has collected. Used for the color of the
	 * player's hair
	 * @see {@link #getPoints(int)}, {@link #setPoints(int)}
	 */
	private int points = 0;

	/**
	 * The standard constructor.<br />
	 * Note: Subclasses which want to be able to be automatically generated from
	 * a Level-file have to implement a constructor with these Parameters.
	 * 
	 * @param x
	 *            The <strong>X-Coordinate</strong> of this Object
	 * @param y
	 *            The <strong>Y-Coordinate</strong> of this Object
	 * @param width
	 *            The <strong>width</strong> of this Object
	 * @param height
	 *            The <strong>height</strong> of this Object
	 * @param level
	 *            The <strong>Level</strong> of this Object
	 */
	public EntityPlayer(double x, double y, double width, double height,
			Level level) {
		super(x, y, width, height, level);
	}

	@Override
	public void update() {
		if (sword != null) {
			motion.x *= 0.6f;
		}
		super.update();
		if (sword != null) {
			if (state)
				sword.bounds.x = (int) (this.bounds.x - 30);
			else
				sword.bounds.x = (int) (this.bounds.x + this.bounds.width);
			sword.bounds.y = this.bounds.y;
		}
	}

	@Override
	public void render() {
		glPushMatrix();
		glTranslated(bounds.x, bounds.y, 0);
		if (state) {
			glTranslated(bounds.width, 0, 0);
			glScalef(-1, 1, 1);
		}
		glEnable(GL_TEXTURE_2D);
		TextureManager.instance.bindTexture(TextureManager.instance
				.getTexture("/Janny_Knight.png"));
		glColor3f(1, 1, 1);
		float f = 1 / 32f;
		float f1 = 1 / 256f;
		glBegin(GL_QUADS);
		glTexCoord2f(0, 7 * f1);
		glVertex2d(0, 0);
		glTexCoord2f(0, 44 * f1);
		glVertex2d(0, 37);
		glTexCoord2f(28 * f, 44 * f1);
		glVertex2d(bounds.width, 37);
		glTexCoord2f(28 * f, 7 * f1);
		glVertex2d(bounds.width, 0);
		glEnd();

		float maxPoints = level.getSecond_Point().size();

		glColor3f(1, (maxPoints - points) / maxPoints, (maxPoints - points)
				/ maxPoints);

		glBegin(GL_QUADS);
		glTexCoord2f(0, 0 * f1);
		glVertex2d(0, 0);
		glTexCoord2f(0, 7 * f1);
		glVertex2d(0, 7);
		glTexCoord2f(28 * f, 7 * f1);
		glVertex2d(bounds.width, 7);
		glTexCoord2f(28 * f, 0 * f1);
		glVertex2d(bounds.width, 0);
		glEnd();

		glColor3f(1, 1, 1);
		glPushMatrix();
		switch (attackState) {
		case NO_ATTACK_ANIMATION:
			glTranslatef(17, 21, 0);
			glRotatef(-30, 0, 0, 1);
			glTranslatef(-17, -21, 0);
			break;
		case BEGIN_ATTACK_ANIMATION:
			glTranslatef(17, 21, 0);
			glRotatef(attackAnimCount / 15F * -30, 0, 0, 1);
			glTranslatef(-17, -21, 0);
			attackAnimCount--;
			if (attackAnimCount <= 0)
				setAttackState(IN_ATTACK_ANIMATION);
			break;
		case IN_ATTACK_ANIMATION:
			break;
		case END_ATTACK_ANIMATION:
			glTranslatef(17, 21, 0);
			glRotatef(attackAnimCount / 15F * -30, 0, 0, 1);
			glTranslatef(-17, -21, 0);
			attackAnimCount++;
			if (attackAnimCount >= 15)
				setAttackState(NO_ATTACK_ANIMATION);
			break;
		}
		glBegin(GL_QUADS);
		glTexCoord2f(0, 44 * f1);
		glVertex2d(14, 7);
		glTexCoord2f(0, 75 * f1);
		glVertex2d(14, 38);
		glTexCoord2f(32 * f, 75 * f1);
		glVertex2d(43, 38);
		glTexCoord2f(32 * f, 44 * f1);
		glVertex2d(43, 7);
		glEnd();
		glPopMatrix();

		glPushMatrix();
		glBegin(GL_QUADS);
		glTexCoord2f(0, 75 * f1);
		glVertex2d(0, 37);
		glTexCoord2f(0, 98 * f1);
		glVertex2d(0, bounds.height);
		glTexCoord2f(28 * f, 98 * f1);
		glVertex2d(bounds.width, bounds.height);
		glTexCoord2f(28 * f, 75 * f1);
		glVertex2d(bounds.width, 37);
		glEnd();
		glBegin(GL_QUADS);
		glTexCoord2f(5 * f, 98 * f1);
		glVertex2d(0, 37);
		glTexCoord2f(5 * f, 121 * f1);
		glVertex2d(0, bounds.height);
		glTexCoord2f(32 * f, 121 * f1);
		glVertex2d(bounds.width, bounds.height);
		glTexCoord2f(32 * f, 98 * f1);
		glVertex2d(bounds.width, 37);
		glEnd();
		glPopMatrix();

		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
		//
		glPushMatrix();
		glLoadIdentity();
		glEnable(GL_TEXTURE_2D);
		TextureManager.instance.bindTexture(TextureManager.instance
				.getTexture("/ProgressBar.png"));
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(64, 10);
		glTexCoord2f(1, 0);
		glVertex2f(576, 10);
		glTexCoord2f(1, 1);
		glVertex2f(576, 18);
		glTexCoord2f(0, 1);
		glVertex2f(64, 18);
		glEnd();

		TextureManager.instance.bindTexture(TextureManager.instance
				.getTexture("/Progress.png"));
		float health = this.getHp() / 200F;
		float x2 = health * 504 + 69;
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(69, 12);
		glTexCoord2f(1, 0);
		glVertex2f(x2, 12);
		glTexCoord2f(1, 1);
		glVertex2f(x2, 16);
		glTexCoord2f(0, 1);
		glVertex2f(69, 16);
		glEnd();
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
	}

	/**
	 * 
	 * @return The amount of color the player has collected.
	 * @see {@link #points}
	 */
	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void addPoint() {
		points++;
	}

	public void setAttackState(byte attackState) {
		if (this.attackState != attackState) {
			this.attackState = attackState;
			if (attackState == IN_ATTACK_ANIMATION) {
				int x;
				if (state)
					x = (int) (this.bounds.x - 30);
				else
					x = (int) (this.bounds.x + this.bounds.width);
				sword = new EntitySword(x, bounds.y, 30, bounds.height, level, 20, this);
				level.getSecond().add(sword);
			} else if (attackState == END_ATTACK_ANIMATION) {
				if (sword != null) {
					sword.kill(null);
					sword = null;
				}
			}
		}
	}

	public double getDefaultHeight() {
		return 64;
	}

	static {
		Out.inf(EntityPlayer.class, "23.10.12", "Michi", null);
	}
}
