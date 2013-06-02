package at.jumpandjan.level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.freschmushroom.Out;
import at.jumpandjan.Entity;
import at.jumpandjan.EntityPlayer;
import at.jumpandjan.EntityStartFlag;
import at.jumpandjan.InterruptUpdateException;
import at.jumpandjan.Object;
import at.jumpandjan.PlayerListener;

/**
 * The level
 * @author Felix, Michael
 *
 */
public class Level {
	/**
	 * The start flag
	 */
	private LevelElement start;
	/**
	 * The level name
	 */
	private String name;
	/**
	 * The points
	 */
	private int points;
	/**
	 * The player
	 */
	private EntityPlayer player;
	/**
	 * First object layer
	 */
	private ArrayList<at.jumpandjan.Object> first = new ArrayList<at.jumpandjan.Object>();
	/**
	 * Second Object layer
	 */
	private ArrayList<at.jumpandjan.Object> second = new ArrayList<at.jumpandjan.Object>();
	/**
	 * Third Object layer
	 */
	private ArrayList<at.jumpandjan.Object> third = new ArrayList<at.jumpandjan.Object>();
	/**
	 * Point layer
	 */
	private ArrayList<at.jumpandjan.Object> second_point = new ArrayList<at.jumpandjan.Object>();
	/**
	 * The objects to despawn the next tick
	 */
	private ArrayList<at.jumpandjan.Object> deadObjects = new ArrayList<at.jumpandjan.Object>();
	/**
	 * The objects to spawn the next tick
	 */
	private ArrayList<at.jumpandjan.Object> spawnObjects = new ArrayList<at.jumpandjan.Object>();
	/**
	 * The levels which are unlocked by finishing this one
	 */
	private List<String> unlocks = new ArrayList<String>();
	
	/**
	 * All object which should be checked for collision
	 */
	public ArrayList<at.jumpandjan.Object> collisionPool = new ArrayList<at.jumpandjan.Object>();
	
	public Level(LevelBuilder lb) {
		start = lb.getStart();
		name = lb.getName();
		unlocks = Arrays.asList(lb.getUnlocks());
		for (LevelElement le : lb.getElements()) {
			if (le instanceof Wall || le instanceof Floor
					|| le instanceof FinishFlag) {
				first.add(le.getElement(this));
			} else if (le instanceof Point) {
				second_point.add(le.getElement(this));
			} else {
				second.add(le.getElement(this));
			}
		}
		first.add(start.getElement(this));
		// first.add(new EntityFinishFlag(finish, this));
		third.add(player = new EntityPlayer(start.getPosX(), start.getPosY()
				+ start.getHeight() - 64, 32, 64, this));
		player.addEntityListener(new PlayerListener());
		for (at.jumpandjan.Object o : first) {
			if (o.hasCollision()) {
				collisionPool.add(o);
			}
		}
		for (at.jumpandjan.Object o : second) {
			if (o.hasCollision()) {
				collisionPool.add(o);
			}
		}
		for (at.jumpandjan.Object o : second_point) {
			if (o.hasCollision()) {
				collisionPool.add(o);
			}
		}
		for (at.jumpandjan.Object o : third) {
			if (o.hasCollision()) {
				collisionPool.add(o);
			}
		}
	}

	/**
	 * Returns the start flag
	 * @return The start flag
	 */
	public LevelElement getStart() {
		return start;
	}

	/**
	 * Sets the start flag
	 * @param start The start flag
	 */
	public void setStart(LevelElement start) {
		this.start = start;
	}

	/**
	 * Returns the name of the level
	 * @return The name of the level
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the level
	 * @param name The name of the level
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the amount of points
	 * @return The amount of points
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * Specifies the amount of points
	 * @param points Amount of points
	 */
	public void setPoints(int points) {
		this.points = points;
	}

	/**
	 * Returns the point layer
	 * @return The point layer
	 */
	public ArrayList<at.jumpandjan.Object> getSecond_Point() {
		return second_point;
	}

	/**
	 * Returns the first layer
	 * @return The first layer
	 */
	public ArrayList<at.jumpandjan.Object> getFirst() {
		return first;
	}

	/**
	 * Sets the first layer
	 * @param first The first layer
	 */
	public void setFirst(ArrayList<at.jumpandjan.Object> first) {
		this.first = first;
	}

	/**
	 * Returns the second layer
	 * @return The second layer
	 */
	public ArrayList<at.jumpandjan.Object> getSecond() {
		return second;
	}

	/**
	 * Sets the second layer
	 * @param second The second layer
	 */
	public void setSecond(ArrayList<at.jumpandjan.Object> second) {
		this.second = second;
	}

	/**
	 * Returns the third layer
	 * @return The third layer
	 */
	public ArrayList<at.jumpandjan.Object> getThird() {
		return third;
	}

	/**
	 * Sets the third layer
	 * @param third The third layer
	 */
	public void setThird(ArrayList<at.jumpandjan.Object> third) {
		this.third = third;
	}

	/**
	 * Renders all objects
	 */
	public void render() {
		for (at.jumpandjan.Object o : first) {
			if (o.shouldRender())
				o.render();
		}
		for (at.jumpandjan.Object o : second) {
			if (((Entity) o).isAlive())
				if (o.shouldRender())
					o.render();
		}
		for (at.jumpandjan.Object o : second_point) {
			if (((Entity) o).isAlive())
				if (o.shouldRender())
					o.render();
		}
		for (at.jumpandjan.Object o : third) {
			if (((Entity) o).isAlive())
				if (o.shouldRender())
					o.render();
		}
	}

	/**
	 * Updates all entities, removes dead objects and spawns new objects
	 */
	public void update() {
		for (at.jumpandjan.Object o : deadObjects) {
			first.remove(o);
			second.remove(o);
			third.remove(o);
			collisionPool.remove(o);
		}
		deadObjects.clear();
		for (at.jumpandjan.Object o : spawnObjects) {
			second.add(o);
			if (o.hasCollision()) {
				collisionPool.add(o);
			}
		}
		spawnObjects.clear();
		boolean continueUpdate = true;
		for (at.jumpandjan.Object o : first) {
			try {
				if (continueUpdate) {
					o.update();
				}
			} catch (InterruptUpdateException iue) {
				continueUpdate = false;
			}
		}
		for (at.jumpandjan.Object o : second) {
			if (((Entity) o).isAlive()) {
				try {
					if (continueUpdate) {
						o.update();
					}
				} catch (InterruptUpdateException iue) {
					continueUpdate = false;
				}
			} else {
				deadObjects.add(o);
			}
		}
		for (at.jumpandjan.Object o : second_point) {
			if (((Entity) o).isAlive()) {
				try {
					if (continueUpdate) {
						o.update();
					}
				} catch (InterruptUpdateException iue) {
					continueUpdate = false;
				}
			} else {
				deadObjects.add(o);
			}
		}
		for (at.jumpandjan.Object o : third) {
			if (((Entity) o).isAlive()) {
				try {
					if (continueUpdate) {
						o.update();
					}
				} catch (InterruptUpdateException iue) {
					continueUpdate = false;
				}
			} else {
				deadObjects.add(o);
			}
		}
	}

	/**
	 * Returns the player
	 * @return The player
	 */
	public EntityPlayer getPlayer() {
		return player;
	}

	/**
	 * Gets the startflag of this level
	 * @return The start flag
	 */
	public EntityStartFlag getStartFlag() {
		for (at.jumpandjan.Object o : first) {
			if (o instanceof EntityStartFlag) {
				return (EntityStartFlag) o;
			}
		}
		return null;
	}

	/**
	 * Returns all dead objects
	 * @return All dead objects
	 */
	public ArrayList<at.jumpandjan.Object> getDeadObjects() {
		return deadObjects;
	}

	public Level(ArrayList<Object> first, ArrayList<Object> second,
			ArrayList<Object> third) {
		super();
		this.first = first;
		this.second = second;
		this.third = third;
	}

	/**
	 * Adds the object to spawn next round
	 * @param spawn The object to spawn
	 */
	public void addSpawnable(at.jumpandjan.Object spawn) {
		spawnObjects.add(spawn);
	}
	
	/**
	 * Get all the Levels being unlocked
	 * @return The levels being unlocked
	 */
	public List<String> getUnlocks() {
		return unlocks;
	}

	static {
		Out.inf(Level.class, "23.10.12", "Felix, Michi", null);
	}
}
