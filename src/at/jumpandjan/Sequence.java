package at.jumpandjan;

public interface Sequence {
	public abstract void start();
	public abstract void pause();
	public abstract void stop();
	public abstract void join() throws InterruptedException;
}
