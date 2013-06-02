package at.jumpandjan.level;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import at.freschmushroom.Out;

/**
 * An element used for building levels; takes a class and instanciates it with the default bounds and level as arguments
 * @author Michael
 *
 */
public class FlexibleElement extends LevelElement implements Serializable {
	/**
	 * The unknown class
	 */
	private Class<? extends at.jumpandjan.Object> c;
	
	public FlexibleElement(double posX, double posY, double width, double height, Class<? extends at.jumpandjan.Object> c) {
		super(posX, posY, width, height);
		this.c = c;
	}

	@Override
	public at.jumpandjan.Object getElement(Level level) {
		try {
			return c.getConstructor(new Class[] {double.class, double.class, double.class, double.class, Level.class}).newInstance(new Object[] {getPosX(), getPosY(), getWidth(), getHeight(), level});
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Failed to return object: " + c.getName());
		return null;
	}
	
	static {
		Out.inf(FlexibleElement.class, "01.06.13", "Michael", null);
	}
}
