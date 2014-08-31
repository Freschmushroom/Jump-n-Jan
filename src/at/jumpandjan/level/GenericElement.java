package at.jumpandjan.level;

import java.lang.reflect.InvocationTargetException;

import at.freschmushroom.Out;
import at.jumpandjan.Body;

/**
 * An element used for building levels; takes a class and instanciates it with
 * the default bounds and level as arguments
 * 
 * @author Michael
 *
 */
public class GenericElement extends LevelElement
{
	/**
	 * The unknown class
	 */
	private Class<? extends Body> c;

	public GenericElement(double posX, double posY, double width, double height, Class<? extends Body> c)
	{
		super(posX, posY, width, height);
		this.c = c;
	}

	@Override
	public Body getElement(Level level)
	{
		try
		{
			return c.getConstructor(new Class[] { double.class, double.class, double.class, double.class, Level.class }).newInstance(
					new Object[] { getPosX(), getPosY(), getWidth(), getHeight(), level });
		} catch (InstantiationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Failed to return object: " + c.getName());
		return null;
	}

	static
	{
		Out.inf(GenericElement.class, "01.06.13", "Michael", null);
	}
}
