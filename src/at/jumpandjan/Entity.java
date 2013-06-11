package at.jumpandjan;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glVertex2d;

import java.awt.Rectangle;
import java.util.ArrayList;

import at.freschmushroom.Intro;
import at.freschmushroom.Out;
import at.freschmushroom.TextureManager;
import at.jumpandjan.level.Level;

/**
 * An entity in the huge world of Jan
 * 
 * @author Michael, Felix
 * 
 */
public class Entity extends Object {
	/**
	 * The turned state
	 */
	protected boolean state;
	/**
	 * If the entity is alive
	 */
	protected boolean alive = true;
	/**
	 * If gravity is applied
	 */
	protected boolean isGravityApplied = true;
	/**
	 * If it is influenced by collision
	 */
	protected boolean isInfluencedByCollision = true;
	/**
	 * The killer entity; will be mostly null
	 */
	protected Entity killer;

	/**
	 * The Health Points
	 */
	protected int hp;
	/**
	 * The max health points
	 */
	private int maxHP;

	/**
	 * The entity listeners
	 */
	private ArrayList<EntityListener> entityListener;

	public Entity(double x, double y, double width, double height, Level level) {
		super(x, y, width, height, level);
		entityListener = new ArrayList<EntityListener>();
		setMaxHP(200);
		setHp(200);
	}

	@Override
	public void update() {
		if (hp > getMaxHP()) {
			hp = getMaxHP();
		}
		if (motion.x < 0)
			state = true;
		else if (motion.x > 0)
			state = false;
		if (isGravityApplied) {
			if (!onGround) {
				motion.y += 1;
			} else if (motion.y == 0) {
				motion.y = 1;
			}
		}
		if (motion.length() != 0) {
			if (motion.x != 0) {
				int startX = bounds.x;
				Rectangle tempBounds = new Rectangle(bounds);
				int direction = (motion.x < 0) ? -1 : 1;
				boolean arrived = false;
				while (!arrived) {
					for (at.jumpandjan.Object o : level.collisionPool) {
						if (!o.checkCollisionWith(this)
								|| !this.checkCollisionWith(o)) {
							continue;
						}
						if (o.bounds.intersects(tempBounds)) {
							collide(o);
							o.collide(this);
							arrived = true;
						}
					}
					if (!arrived) {
						arrived = Math.abs(tempBounds.x - startX) == Math
								.abs((int) motion.x);
						bounds = (Rectangle) tempBounds.clone();
						tempBounds.setLocation(tempBounds.x + direction,
								tempBounds.y);
					}
				}
			}
			if (motion.y != 0) {
				int startY = bounds.y;
				onGround = false;
				Rectangle tempBounds = new Rectangle(bounds);
				int direction = (motion.y < 0) ? -1 : 1;
				boolean arrived = false;
				while (!arrived) {
					for (at.jumpandjan.Object o : level.collisionPool) {
						if (!o.checkCollisionWith(this)
								|| !this.checkCollisionWith(o)) {
							continue;
						}
						if (o.bounds.intersects(tempBounds)) {
							collide(o);
							o.collide(this);
							arrived = true;
							if (direction == 1) {
								onGround = true;
							}
							motion.y = 0;
						}
					}
					if (!arrived) {
						arrived = Math.abs(tempBounds.y - startY) == Math
								.abs((int) motion.y);
						bounds = (Rectangle) tempBounds.clone();
						tempBounds.setLocation(tempBounds.x, tempBounds.y
								+ direction);
					}
				}
			}
		}

		if (this.bounds.y > 480 || this.bounds.y - Intro.vertMvmt < -500) {
			kill(this);
		}
	}

	/**
	 * The pivot x
	 * @return The pivot x
	 */
	public double getPivotX() {
		return bounds.getCenterX();
	}

	/**
	 * The pivot y
	 * @return The pivot y
	 */
	public double getPivotY() {
		return bounds.getCenterY();
	}

	/**
	 * Sets the health points
	 * @param hp The health points
	 */
	public void setHp(int hp) {
		this.hp = hp;
	}

	/**
	 * Returns the HP
	 * @return The HP
	 */
	public int getHp() {
		return hp;
	}

	/**
	 * Renders the health bar
	 */
	protected void renderHealthbar() {
		glPushMatrix();
		glTranslated(bounds.x, bounds.y - 10, 0);

		glEnable(GL_TEXTURE_2D);
		TextureManager.instance.bindTexture(TextureManager.instance
				.getTexture("/ProgressBar.png"));

		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2d(0, 0);
		glTexCoord2f(1, 0);
		glVertex2d(bounds.width, 0);
		glTexCoord2f(1, 1);
		glVertex2d(bounds.width, 4);
		glTexCoord2f(0, 1);
		glVertex2d(0, 4);
		glEnd();

		TextureManager.instance.bindTexture(TextureManager.instance
				.getTexture("/Progress.png"));
		float health = this.getHp() / 200F;
		double x2 = 1 / 128D * bounds.width;
		double x3 = health * bounds.width - 1 / 128D * bounds.width;

		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2d(x2, 1);
		glTexCoord2f(1, 0);
		glVertex2d(x3, 1);
		glTexCoord2f(1, 1);
		glVertex2d(x3, 3);
		glTexCoord2f(0, 1);
		glVertex2d(x2, 3);
		glEnd();
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
	}

	/**
	 * Kills the entity
	 * @param e The killer
	 */
	public void kill(Entity e) {
		this.alive = false;
		killer = e;
		for (EntityListener listener : entityListener) {
			listener.entityKilled(this);
		}
		level.getDeadObjects().add(this);
	}

	/**
	 * Whether the entity is alive
	 * @return Whether the entity is alive
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * Returns the max HP
	 * @return The max HP
	 */
	public int getMaxHP() {
		return maxHP;
	}

	/**
	 * Sets the max HP
	 * @param maxHP The max HP
	 */
	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}

	static {
		Out.inf(Entity.class, "22.10.12", "Felix, Michael", null);
	}

	/**
	 * Hits the entity 
	 * @param damageSource With this damagesource
	 */
	public void hit(DamageSource damageSource) {
		hurt(damageSource.getDamage());
		for (EntityListener listener : entityListener) {
			listener.entityHit(this, damageSource);
		}
	}

	/**
	 * Hits the entity 
	 * @param damageSource The damagesource
	 */
	public void hitByEntity(DamageSourceEntity damageSource) {
		hurt(damageSource.getDamage(), damageSource.getSourceEntity());
		for (EntityListener listener : entityListener) {
			listener.entityHitByEntity(this, damageSource);
		}
	}

	/**
	 * This entity hit someone
	 * @param entityHit The entity hit
	 */
	public void entityHit(Entity entityHit) {

	}

	/**
	 * Ouch!
	 * @param dmg The damage
	 */
	public void hurt(int dmg) {
		hurt(dmg, this);
	}

	/**
	 * Ouch! Stahp!
	 * @param dmg The damage
	 * @param killer The attacker
	 */
	public void hurt(int dmg, Entity killer) {
		if (hp - dmg <= 0) {
			hp = 0;
			kill(killer);
		} else {
			hp -= dmg;
		}
	}

	@Override
	public double getDefaultHeight() {
		return 32;
	}

	@Override
	public double getDefaultWidth() {
		return 32;
	}

	/**
	 * Adds the listener
	 * @param listener Adds this listener
	 */
	public void addEntityListener(EntityListener listener) {
		entityListener.add(listener);
	}

	/**
	 * Removes the listener
	 * @param listener Removes the listener
	 * @return Whether the removal was successful
	 */
	public boolean removeEntityListener(EntityListener listener) {
		return entityListener.remove(listener);
	}
	
	@Override
	public boolean checkCollisionWith(at.jumpandjan.Object o) {
		return this.isAlive() && super.checkCollisionWith(o);
	}
}
