package at.jumpandjan;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

import at.jumpandjan.level.Level;
import at.jumpandjan.level.LevelBuilder;

public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String name = "null";

	private HashSet<String> unlockedLvls = new HashSet<String>();
	private HashMap<String, Integer> pointsPerLevel = new HashMap<String, Integer>();

	private static HashMap<String, User> userMap = new HashMap<String, User>();

	public User() {
		this(getUniqueName());
	}

	public static String getUniqueName() {
		String name = "Player";
		while (userMap.containsKey(name)) {
			name += Constants.random.nextInt(10);
		}
		return name;
	}

	public static User getUserByName(String name) {
		return userMap.get(name.toLowerCase());
	}

	public User(String name) {
		System.out.println("Loaded " + name);
		setName(name);
		unlockLvl(LevelBuilder.load(Constants.getDEFAULT_LVL_FILE()).getName());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (!this.name.equals(name)) {
			userMap.put(name.toLowerCase(), this);
			try {
				File oldFile = new File("saves/" + this.name + ".user");
				oldFile.delete();
				userMap.remove(this.name.toLowerCase());
				this.name = name;
				save();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void unlockLvl(String lvl) {
		unlockedLvls.add(lvl);
		pointsPerLevel.put(lvl, 0);
	}

	public void finishedLvl(Level level, int achievedPoints) {
		pointsPerLevel.put(level.getName(), achievedPoints);
		for (String s : level.getUnlocks()) {
			if (s.isEmpty()) {
				continue;
			}
			LevelBuilder lvl = LevelBuilder.load(s);
			unlockLvl(lvl.getName());
			System.out.println("Level unlocked: " + lvl.getName());
		}

	}

	public void save() {
		try {
			File saveFile = new File("saves/" + name + ".user");
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(saveFile));

			oos.writeObject(this);

			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isUnlocked(String name) {
		return unlockedLvls.contains(name);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("User: ");
		sb.append(name);
		sb.append("\n");
		sb.append("Unlocked levels:\r\n");
		for (String s : unlockedLvls) {
			sb.append('\t');
			sb.append(s);
			sb.append("\r\n");
		}

		return sb.toString();
	}

	static {
		File[] saveDir = new File("saves").listFiles();
		for (File f : saveDir) {
			if (!f.isDirectory() && f.getName().endsWith(".user")) {
				try {
					ObjectInputStream ois = new ObjectInputStream(
							new BufferedInputStream(new FileInputStream(f)));
					User user = (User) ois.readObject();
					userMap.put(user.name.toLowerCase(), user);
					ois.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
