package at.jumpandjan;

import at.freschmushroom.Out;

/**
 * Thrown when a entity wants to stop an update, e.g. when winning
 * 
 * @author Michael
 *
 */
public class InterruptUpdateException extends RuntimeException
{
	private static final long serialVersionUID = 7583339623303164534L;

	static
	{
		Out.inf(InterruptUpdateException.class, "01.06.2013", "Michael", null);
	}
}
