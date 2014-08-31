package at.freschmushroom.audio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;

import at.freschmushroom.Errorhandling;
import at.freschmushroom.Out;
import at.jumpandjan.Constants;

/**
 * Class to unify Sound Loading and Sound playing
 * 
 * @author Beide
 *
 */
public class SoundContainer
{
	/**
	 * Thread to display loading progress on screen
	 */
	public static final SoundLoadingThread mainLoadingThread = new SoundLoadingThread();
	/**
	 * The map where the sounds are stored
	 */
	private static HashMap<String, Audio> sounds = new HashMap<>();

	/**
	 * Loads a sound from a local File
	 * 
	 * @param path
	 *            the path to the sound file
	 * @param format
	 *            the format of the sound file
	 * @param name
	 *            the name the sound file should be remebered
	 */
	private static void loadSound(String path, String format, String name)
	{
		Out.line("Loading sound " + name + " from " + path + " in " + format + " format");
		try
		{
			sounds.put(name, AudioLoader.getAudio(format, new FileInputStream(path)));
		} catch (FileNotFoundException e)
		{
			Out.err("Audio " + name + "@" + path + " not loadable: FileNotFound");
			Errorhandling.handle(e);
		} catch (IOException e)
		{
			Out.err("Audio " + name + "@" + path + " not loadable: Other mistake");
			Errorhandling.handle(e);
		}
	}

	/**
	 * Starts the Main Loading Thread
	 */
	public static void init()
	{
		new Thread(mainLoadingThread).start();
	}

	/**
	 * Plays a sound continuously until its stoped
	 * 
	 * @param sound
	 *            the name of the sound you want to play
	 */
	public static void play(String sound)
	{
		if (sounds.containsKey(sound))
			sounds.get(sound).playAsMusic(1, 1, true);
	}

	/**
	 * Plays a sound once
	 * 
	 * @param sound
	 *            the sound you want to play
	 */
	public static void play_once(String sound)
	{
		if (sounds.containsKey(sound))
			sounds.get(sound).playAsMusic(1, 1, false);
	}

	/**
	 * Stops a sound immediatley if its playing
	 * 
	 * @param sound
	 *            the sound you want to stop
	 */
	public static void stop(String sound)
	{
		if (sounds.containsKey(sound))
			sounds.get(sound).stop();
	}

	/**
	 * Fixed: Now stops all the sounds instead of playing them
	 */
	public static void stopAll()
	{
		for (String s : sounds.keySet())
		{
			stop(s);
		}
	}

	/*
	 * public static void stopAll() { for (String s : sounds.keySet()) {
	 * play(s); } }
	 */
	/**
	 * Checks if the sound is playing
	 * 
	 * @param sound
	 *            the name of the sound you want to check
	 * @return if the sound is playing
	 */
	public static boolean isPlaying(String sound)
	{
		if (sounds.containsKey(sound))
			return sounds.get(sound).isPlaying();
		return false;
	}

	/**
	 * Looks up the position of a sound
	 * 
	 * @param sound
	 *            the name of the sound you want to check
	 * @return the current position of the sound, or -1 if the sound is not in
	 *         the list
	 */
	public static float getPosition(String sound)
	{
		if (sounds.containsKey(sound))
			return sounds.get(sound).getPosition();
		return -1f;
	}

	/**
	 * 
	 * Class to load all the sounds
	 * 
	 * @author Michi
	 *
	 */
	public static class SoundLoadingThread implements Runnable
	{
		/**
		 * A Queue to store the paths, formats and names of the sounds
		 */
		private Queue<String[]> loadingQueue = new ArrayDeque<String[]>();
		/**
		 * The name of the current sound
		 */
		public String currentSound;

		public void run()
		{
			while (Constants.isRunning())
			{
				if (!loadingQueue.isEmpty())
				{
					String[] sound = loadingQueue.element();
					loadingQueue.remove();
					currentSound = sound[2];
					try
					{
						SoundContainer.loadSound(sound[0], sound[1], sound[2]);
					} catch (Exception e)
					{
						System.err.println("Error occured while loading " + currentSound);
					}
				}
			}
		}

		/**
		 * Returns the number of Elements in the Loading Queue
		 * 
		 * @return the number of Elements
		 */
		public int elementsInQueue()
		{
			return loadingQueue.size();
		}

		/**
		 * Adds a sound to the Loading Queue
		 * 
		 * @param path
		 *            the path to the source file
		 * @param format
		 *            the format of the sound file
		 * @param name
		 *            the name of the sound file
		 */
		public void loadSound(String path, String format, String name)
		{
			loadingQueue.add(new String[] { path, format, name });
		}
	}

	static
	{
		Out.inf(SoundContainer.class, "01/06/13", "Beide", null);
	}
}
