package at.jumpandjan;
/**
 * Interface for a Player Sequence
 * 
 * @author Felix
 *
 */
public interface Sequence {
	/**
	 * Starts the Sequence
	 */
	public abstract void start();
	/**
	 * Pauses the Sequence
	 */
	public abstract void pause();
	/**
	 * Stops the Sequence
	 */
	public abstract void stop();
	/**
	 * Wait till the Sequence is over
	 * @throws InterruptedException if I interrupted you, my dear
	 */
	public abstract void join() throws InterruptedException;
}
