package at.freschmushroom.audio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;

import at.freschmushroom.Errorhandling;
import at.freschmushroom.Out;

/*
 * Neues layout
 * eventuell Musikaufruf
 * SoundContainer.play(Sound:String)
 */
public class SoundContainer {
	/**
	 * NOT YET IMPLEMENTED
	 */
	public static Audio gameOver;
	/**
	 * The actual gameMusic which is always played in the background
	 */
	private static Audio gameMusic;
	/**
	 * The sound when psy attacks
	 */
	public static Audio opp_psy_attack;
	public static Audio explosion;
	public static Audio party_music;

	/**
	 * Initializes all sound sources Must call before starting any sounds
	 */
	public static void init() {
		try {
			gameMusic = AudioLoader.getAudio("OGG",
					new FileInputStream(System.getProperty("user.dir")
							+ "\\main.ogg"));
		} catch (FileNotFoundException e) {
			Out.err("Audio System not loadable: FileNotFound");
			Errorhandling.handle(e);
		} catch (IOException e) {
			Out.err("Audio System not loadable: Other mistake");
			Errorhandling.handle(e);
		}
		try {
			gameOver = AudioLoader.getAudio("OGG",
					new FileInputStream(System.getProperty("user.dir")
							+ "\\gameOver.ogg"));
		} catch (FileNotFoundException e) {
			Out.err("Audio System not loadable: FileNotFound");
			Errorhandling.handle(e);
		} catch (IOException e) {
			Out.err("Audio System not loadable: Other mistake");
			Errorhandling.handle(e);
		}
		try {
			opp_psy_attack = AudioLoader.getAudio("OGG", new FileInputStream(
					System.getProperty("user.dir") + "\\opp_psy_attack.ogg"));
		} catch (FileNotFoundException e) {
			Out.err("Audio System not loadable: FileNotFound");
			Errorhandling.handle(e);
		} catch (IOException e) {
			Out.err("Audio System not loadable: Other mistake");
			Errorhandling.handle(e);
		}
		try {
			party_music = AudioLoader.getAudio("OGG", new FileInputStream(
					System.getProperty("user.dir") + "\\party.ogg"));
		} catch (FileNotFoundException e) {
			Out.err("Audio System not loadable: FileNotFound");
			Errorhandling.handle(e);
		} catch (IOException e) {
			Out.err("Audio System not loadable: Other mistake");
			Errorhandling.handle(e);
		}
		try {
			explosion = AudioLoader.getAudio("OGG",
					new FileInputStream(System.getProperty("user.dir")
							+ "\\explosion.ogg"));
		} catch (FileNotFoundException e) {
			Out.err("Audio System not loadable: FileNotFound");
			Errorhandling.handle(e);
		} catch (IOException e) {
			Out.err("Audio System not loadable: Other mistake");
			Errorhandling.handle(e);
		}
	}

	static {
		Out.inf(SoundContainer.class, "23.08.12", "Felix", null);
	}

	/**
	 * Starts the game music
	 */
	public static void startGameMusic() {
		gameMusic.playAsMusic(1, 1, true);
	}

	/**
	 * Stops the game music
	 */
	public static void stopGameMusic() {
		try {
			gameMusic.stop();
		} catch (Error e) {
			Errorhandling.handle(e);
		}
	}
}
