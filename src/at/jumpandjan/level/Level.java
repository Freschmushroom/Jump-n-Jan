package at.jumpandjan.level;

import static org.lwjgl.opengl.GL11.glTranslatef;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.Display;

import at.freschmushroom.Out;
import at.jumpandjan.Body;
import at.jumpandjan.Floor;
import at.jumpandjan.InterruptUpdateException;
import at.jumpandjan.JumpAndJan;
import at.jumpandjan.PlayerListener;
import at.jumpandjan.Wall;
import at.jumpandjan.entity.Entity;
import at.jumpandjan.entity.EntityFlag;
import at.jumpandjan.entity.EntityPlayer;
import at.jumpandjan.entity.EntityStartFlag;
import at.zaboing.Camera;

/**
 * The level
 * 
 * @author Felix, Michael
 *
 */
public class Level
{
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
	private ArrayList<Body> first = new ArrayList<Body>();
	/**
	 * Second Object layer
	 */
	private ArrayList<Body> second = new ArrayList<Body>();
	/**
	 * Third Object layer
	 */
	private ArrayList<Body> third = new ArrayList<Body>();
	/**
	 * Point layer
	 */
	private ArrayList<Body> second_point = new ArrayList<Body>();
	/**
	 * The objects to despawn the next tick
	 */
	private ArrayList<Body> deadObjects = new ArrayList<Body>();
	/**
	 * The objects to spawn the next tick
	 */
	private ArrayList<Body> spawnObjects = new ArrayList<Body>();
	/**
	 * The levels which are unlocked by finishing this one
	 */
	private List<String> unlocks = new ArrayList<String>();

	/**
	 * All object which should be checked for collision
	 */
	public ArrayList<Body> collisionPool = new ArrayList<Body>();

	public Camera camera;

	public JumpAndJan game;

	public Level(JumpAndJan game, LevelBuilder lb)
	{
		this.game = game;
		camera = Camera.createCamera();
		start = lb.getStart();
		name = lb.getName();
		unlocks = Arrays.asList(lb.getUnlocks());
		for (LevelElement le : lb.getElements())
		{
			Body body = le.getElement(this);
			if (body instanceof Wall || body instanceof Floor || body instanceof EntityFlag)
			{
				first.add(body);
			}
			else if (le instanceof Point)
			{
				second_point.add(body);
			}
			else
			{
				second.add(body);
			}
		}
		first.add(start.getElement(this));
		// first.add(new EntityFinishFlag(finish, this));
		third.add(player = new EntityPlayer(start.getPosX(), start.getPosY() + start.getHeight() - 64, 32, 64, this));
		player.addEntityListener(new PlayerListener());
		for (Body o : first)
		{
			if (o.hasCollision())
			{
				collisionPool.add(o);
			}
		}
		for (Body o : second)
		{
			if (o.hasCollision())
			{
				collisionPool.add(o);
			}
		}
		for (Body o : second_point)
		{
			if (o.hasCollision())
			{
				collisionPool.add(o);
			}
		}
		for (Body o : third)
		{
			if (o.hasCollision())
			{
				collisionPool.add(o);
			}
		}
	}

	/**
	 * Returns the start flag
	 * 
	 * @return The start flag
	 */
	public LevelElement getStart()
	{
		return start;
	}

	/**
	 * Sets the start flag
	 * 
	 * @param start
	 *            The start flag
	 */
	public void setStart(LevelElement start)
	{
		this.start = start;
	}

	/**
	 * Returns the name of the level
	 * 
	 * @return The name of the level
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the name of the level
	 * 
	 * @param name
	 *            The name of the level
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Returns the amount of points
	 * 
	 * @return The amount of points
	 */
	public int getPoints()
	{
		return points;
	}

	/**
	 * Specifies the amount of points
	 * 
	 * @param points
	 *            Amount of points
	 */
	public void setPoints(int points)
	{
		this.points = points;
	}

	/**
	 * Returns the point layer
	 * 
	 * @return The point layer
	 */
	public ArrayList<Body> getSecond_Point()
	{
		return second_point;
	}

	/**
	 * Returns the first layer
	 * 
	 * @return The first layer
	 */
	public ArrayList<Body> getFirst()
	{
		return first;
	}

	/**
	 * Sets the first layer
	 * 
	 * @param first
	 *            The first layer
	 */
	public void setFirst(ArrayList<Body> first)
	{
		this.first = first;
	}

	/**
	 * Returns the second layer
	 * 
	 * @return The second layer
	 */
	public ArrayList<Body> getSecond()
	{
		return second;
	}

	/**
	 * Sets the second layer
	 * 
	 * @param second
	 *            The second layer
	 */
	public void setSecond(ArrayList<Body> second)
	{
		this.second = second;
	}

	/**
	 * Returns the third layer
	 * 
	 * @return The third layer
	 */
	public ArrayList<Body> getThird()
	{
		return third;
	}

	/**
	 * Sets the third layer
	 * 
	 * @param third
	 *            The third layer
	 */
	public void setThird(ArrayList<Body> third)
	{
		this.third = third;
	}

	/**
	 * Renders all objects
	 */
	public void render()
	{
		glTranslatef(-camera.x, 0, 0);
		camera.x = (int) (player.bounds.x - Display.getWidth() / 2);
		for (Body o : first)
		{
			if (o.shouldRender())
				o.render();
		}
		for (Body o : second)
		{
			if (((Entity) o).isAlive())
				if (o.shouldRender())
					o.render();
		}
		for (Body o : second_point)
		{
			if (((Entity) o).isAlive())
				if (o.shouldRender())
					o.render();
		}
		for (Body o : third)
		{
			if (((Entity) o).isAlive())
				if (o.shouldRender())
					o.render();
		}
	}

	/**
	 * Updates all entities, removes dead objects and spawns new objects
	 */
	public void update()
	{
		camera.update();
		for (Body o : deadObjects)
		{
			first.remove(o);
			second.remove(o);
			third.remove(o);
			collisionPool.remove(o);
		}
		deadObjects.clear();
		for (Body o : spawnObjects)
		{
			second.add(o);
			if (o.hasCollision())
			{
				collisionPool.add(o);
			}
		}
		spawnObjects.clear();
		boolean continueUpdate = true;
		for (Body o : first)
		{
			try
			{
				if (continueUpdate)
				{
					o.update();
				}
			} catch (InterruptUpdateException iue)
			{
				continueUpdate = false;
			}
		}
		for (Body o : second)
		{
			if (((Entity) o).isAlive())
			{
				try
				{
					if (continueUpdate)
					{
						o.update();
					}
				} catch (InterruptUpdateException iue)
				{
					continueUpdate = false;
				}
			}
			else
			{
				deadObjects.add(o);
			}
		}
		for (Body o : second_point)
		{
			if (((Entity) o).isAlive())
			{
				try
				{
					if (continueUpdate)
					{
						o.update();
					}
				} catch (InterruptUpdateException iue)
				{
					continueUpdate = false;
				}
			}
			else
			{
				deadObjects.add(o);
			}
		}
		for (Body o : third)
		{
			if (((Entity) o).isAlive())
			{
				try
				{
					if (continueUpdate)
					{
						o.update();
					}
				} catch (InterruptUpdateException iue)
				{
					continueUpdate = false;
				}
			}
			else
			{
				deadObjects.add(o);
			}
		}
	}

	/**
	 * Returns the player
	 * 
	 * @return The player
	 */
	public EntityPlayer getPlayer()
	{
		return player;
	}

	/**
	 * Gets the startflag of this level
	 * 
	 * @return The start flag
	 */
	public EntityStartFlag getStartFlag()
	{
		for (Body o : first)
		{
			if (o instanceof EntityStartFlag)
			{
				return (EntityStartFlag) o;
			}
		}
		return null;
	}

	/**
	 * Returns all dead objects
	 * 
	 * @return All dead objects
	 */
	public ArrayList<Body> getDeadObjects()
	{
		return deadObjects;
	}

	public Level(ArrayList<Body> first, ArrayList<Body> second, ArrayList<Body> third)
	{
		super();
		this.first = first;
		this.second = second;
		this.third = third;
	}

	/**
	 * Adds the object to spawn next round
	 * 
	 * @param spawn
	 *            The object to spawn
	 */
	public void addSpawnable(Body spawn)
	{
		spawnObjects.add(spawn);
	}

	/**
	 * Get all the Levels being unlocked
	 * 
	 * @return The levels being unlocked
	 */
	public List<String> getUnlocks()
	{
		return unlocks;
	}

	static
	{
		Out.inf(Level.class, "23.10.12", "Felix, Michi", null);
	}
}
