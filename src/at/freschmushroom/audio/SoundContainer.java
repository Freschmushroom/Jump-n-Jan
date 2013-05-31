package at.freschmushroom.audio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;

import at.freschmushroom.Errorhandling;
import at.jumpandjan.Constants;
import at.jumpandjan.Out;


public class SoundContainer {
	public static final SoundLoadingThread mainLoadingThread = new SoundLoadingThread();
	
	private static HashMap<String, Audio> sounds = new HashMap<>();

	private static void loadSound(String path, String format, String name) {
		Out.line("Loading sound " + name + " from " + path + " in " + format
				+ " format");
		try {
			sounds.put(name,
					AudioLoader.getAudio(format, new FileInputStream(path)));
		} catch (FileNotFoundException e) {
			Out.err("Audio " + name + "@" + path
					+ " not loadable: FileNotFound");
			Errorhandling.handle(e);
		} catch (IOException e) {
			Out.err("Audio " + name + "@" + path
					+ " not loadable: Other mistake");
			Errorhandling.handle(e);
		}
	}

	public static void init() {
		new Thread(mainLoadingThread).start();
	}

	public static void play(String sound) {
		if (sounds.containsKey(sound))
			sounds.get(sound).playAsMusic(1, 1, true);
	}

	public static void stop(String sound) {
		if (sounds.containsKey(sound))
			sounds.get(sound).stop();
	}
	
	public static void stopAll() {
		for (String s : sounds.keySet()) {
			play(s);
		}
	}
	
	public static boolean isPlaying(String sound) {
		if(sounds.containsKey(sound))
			return sounds.get(sound).isPlaying();
		return false;
	}
	
	public static float getPosition(String sound) {
		if(sounds.containsKey(sound))
			return sounds.get(sound).getPosition();
		return -1f;
	}
	
	public static class SoundLoadingThread implements Runnable {
		private Queue<String[]> loadingQueue = new ArrayDeque<String[]>();
		public String currentSound;
		
		public void run() {
			while (Constants.isRunning()) {
				if (!loadingQueue.isEmpty()) {
					String[] sound = loadingQueue.element();
					loadingQueue.remove();
					currentSound = sound[2];
					try {
						SoundContainer.loadSound(sound[0], sound[1], sound[2]);
					} catch (Exception e) {
						System.err.println("Error occured while loading " + currentSound);
					}
				}
			}
		}
		
		public int elementsInQueue() {
			return loadingQueue.size();
		}
		
		public void loadSound(String path, String format, String name) {
			loadingQueue.add(new String[] {path, format, name});
		}
	}
}
