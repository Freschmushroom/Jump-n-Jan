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
import java.util.HashSet;

import at.freschmushroom.Intro;
import at.freschmushroom.Out;
import at.freschmushroom.TextureManager;
import at.jumpandjan.level.Level;

public class Entity extends Object {
	protected boolean state;
	protected boolean alive = true;
	protected boolean isGravityApplied = true;
	protected boolean isInfluencedByCollision = true;
	protected Entity killer;

	protected int hp = 200;
	private int maxHP;

	private ArrayList<EntityListener> entityListener;

	public Entity(double x, double y, double width, double height, Level level) {
		super(x, y, width, height, level);
		entityListener = new ArrayList<EntityListener>();
	}

	public void update() {
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
						if (this == o) {
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
			} else {
				for (at.jumpandjan.Object o : level.collisionPool) {
					if (!o.shouldRender()) {
						continue;
					}
					if (o.bounds.intersects(bounds)) {
						
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
						if (this == o) {
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
			} else {
				for (at.jumpandjan.Object o : level.collisionPool) {
					if (o.bounds.intersects(bounds)) {
						collide(o);
						o.collide(this);
					}
				}
			}
		}

		if (this.bounds.y > 480 || this.bounds.y - Intro.vertMvmt < -64) {
			level.getDeadObjects().add(this);
		}
	}

	public double getPivotX() {
		return bounds.getCenterX();
	}

	public double getPivotY() {
		return bounds.getCenterY();
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getHp() {
		return hp;
	}

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

	public void kill(Entity e) {
		this.alive = false;
		killer = e;
		for (EntityListener listener : entityListener) {
			listener.entityKilled(this);
		}
	}

	public boolean isAlive() {
		return alive;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}

	static {
		Out.inf(Entity.class, "22.10.12", "Felix, Michael", null);
	}

	public void hit(DamageSource damageSource) {
		hurt(damageSource.getDamage());
		for (EntityListener listener : entityListener) {
			listener.entityHit(this, damageSource);
		}
	}

	public void hitByEntity(DamageSourceEntity damageSource) {
		hurt(damageSource.getDamage(), damageSource.getSourceEntity());
		for (EntityListener listener : entityListener) {
			listener.entityHitByEntity(this, damageSource);
		}
	}

	public void entityHit(Entity entityHit) {

	}

	public void hurt(int dmg) {
		hurt(dmg, this);
	}

	public void hurt(int dmg, Entity killer) {
		if (hp - dmg <= 0) {
			hp = 0;
			kill(killer);
		} else {
			hp -= dmg;
		}
	}

	public double getDefaultHeight() {
		return 32;
	}

	public double getDefaultWidth() {
		return 32;
	}

	public void addEntityListener(EntityListener listener) {
		entityListener.add(listener);
	}

	public boolean removeEntityListener(EntityListener listener) {
		return entityListener.remove(listener);
	}
}
