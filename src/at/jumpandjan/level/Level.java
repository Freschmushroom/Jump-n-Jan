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

public class Level {
	private LevelElement start;
	private String name;
	private int points;
	private EntityPlayer player;
	private ArrayList<at.jumpandjan.Object> first = new ArrayList<at.jumpandjan.Object>();
	private ArrayList<at.jumpandjan.Object> second = new ArrayList<at.jumpandjan.Object>();
	private ArrayList<at.jumpandjan.Object> third = new ArrayList<at.jumpandjan.Object>();
	private ArrayList<at.jumpandjan.Object> second_point = new ArrayList<at.jumpandjan.Object>();
	private ArrayList<at.jumpandjan.Object> deadObjects = new ArrayList<at.jumpandjan.Object>();
	private ArrayList<at.jumpandjan.Object> spawnObjects = new ArrayList<at.jumpandjan.Object>();
	private List<String> unlocks = new ArrayList<String>();
	
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

	public LevelElement getStart() {
		return start;
	}

	public void setStart(LevelElement start) {
		this.start = start;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public ArrayList<at.jumpandjan.Object> getSecond_Point() {
		return second_point;
	}

	public ArrayList<at.jumpandjan.Object> getFirst() {
		return first;
	}

	public void setFirst(ArrayList<at.jumpandjan.Object> first) {
		this.first = first;
	}

	public ArrayList<at.jumpandjan.Object> getSecond() {
		return second;
	}

	public void setSecond(ArrayList<at.jumpandjan.Object> second) {
		this.second = second;
	}

	public ArrayList<at.jumpandjan.Object> getThird() {
		return third;
	}

	public void setThird(ArrayList<at.jumpandjan.Object> third) {
		this.third = third;
	}

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

	public EntityPlayer getPlayer() {
		return player;
	}

	public EntityStartFlag getStartFlag() {
		for (at.jumpandjan.Object o : first) {
			if (o instanceof EntityStartFlag) {
				return (EntityStartFlag) o;
			}
		}
		return null;
	}

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

	public void addSpawnable(at.jumpandjan.Object spawn) {
		spawnObjects.add(spawn);
	}
	
	public List<String> getUnlocks() {
		return unlocks;
	}

	static {
		Out.inf(Level.class, "23.10.12", "Felix, Michi", null);
	}
}
