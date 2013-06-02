package at.freschmushroom;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilePermission;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.lwjgl.opengl.Display;

/**
 * Provides sevices like lib fetching or loading
 * 
 * @author Felix Resch
 * 
 */
public class ServiceProvider {
	/**
	 * The architecture of the system
	 */
	public static short ARCH;
	/**
	 * The platform of the system
	 */
	public static String PLATFORM;

	/**
	 * Loads the libs from the freschmushroom folder, or, when not set, the
	 * appdata: Windows: %APPDATA%\.freschmushroom\natives\ Linux:
	 * /home/user/appdata/.freschmushroom/natives Mac:????
	 */
	public static void loadLibs() {
		String appfolder = System.getenv("FRESCHMUSHROOM_HOME");
		if (appfolder == null) {
			appfolder = System.getenv("APPDATA");
		}
		if (appfolder == null) {
			appfolder = System.getProperty("user.home") + "/appdata";
		}
		File f = new File(appfolder + "/.freschmushroom");
		if (!f.exists()) {
			f.mkdir();
		}
		appfolder += "/.freschmushroom";
		Out.line("Appfolder is: " + appfolder);
		f = new File(appfolder + "/natives");
		if (!f.exists()) {
			Out.err("FreschMushroom wasn't set up properly. Please try again.");
			System.exit(1);
		}
		System.setProperty("java.library.path",
				System.getProperty("java.library.path") + ";" + appfolder
						+ "/natives/" + PLATFORM + ";"
						+ "/opt/java/jre/lib/i386" + ";");
		System.setProperty("org.lwjgl.librarypath", appfolder + "/natives/"
				+ PLATFORM);
		try {
			final Field usrPathsField = ClassLoader.class
					.getDeclaredField("usr_paths");
			usrPathsField.setAccessible(true);

			java.util.List<String> paths = new java.util.ArrayList<String>(
					java.util.Arrays.asList((String[]) usrPathsField.get(null)));

			if (paths.contains(appfolder + "/native/" + PLATFORM)) {
				return;
			}

			paths.add(0, appfolder + "" + "/native/" + PLATFORM);

			usrPathsField.set(null, paths.toArray(new String[paths.size()]));
		} catch (NoSuchFieldException e1) {
			Errorhandling.handle(e1);
		} catch (SecurityException e1) {
			Errorhandling.handle(e1);
		} catch (IllegalArgumentException e) {
			Errorhandling.handle(e);
		} catch (IllegalAccessException e) {
			Errorhandling.handle(e);
		}
		Out.line("Platform: " + PLATFORM.toUpperCase() + " x86"
				+ (ARCH == 64 ? "_64 bit" : " _32bit"));
		Out.line(System.getProperty("java.library.path"));
		try {
			if (PLATFORM.equals("windows")) {
				if (ARCH == 64) {
					System.loadLibrary("lwjgl64");
					System.loadLibrary("jinput-dx8_64");
					System.loadLibrary("jinput-raw_64");
					System.loadLibrary("OpenAL64");
				} else {
					System.loadLibrary("lwjgl");
					System.loadLibrary("jinput-dx8");
					System.loadLibrary("jinput-raw");
					System.loadLibrary("OpenAL32");
				}
			} else if (PLATFORM.equals("macosx")) {
				System.loadLibrary("libjinput-osx");
				System.loadLibrary("liblwjgl");
				System.loadLibrary("openal");
			} else if (PLATFORM.equals("linux")) {
				if (ARCH == 64) {
					System.load(appfolder
							+ "/natives/linux/libjinput-linux64.so");
					System.load(appfolder + "/natives/linux/liblwjgl64.so");
					System.load(appfolder + "/natives/linux/libopenal64.so");
				} else {
					System.load(appfolder + "/natives/linux/libjinput-linux.so");
					System.load(appfolder + "/natives/linux/liblwjgl.so");
					System.load(appfolder + "/natives/linux/libopenal.so");
				}
			}
		} catch (Error e) {
			Out.err("Loading not possible. Maybe theres a problem with your OS");
			if (e instanceof UnsatisfiedLinkError) {
				Out.err("Loading Libs in alternative way");
			} else {
				Errorhandling.handle(e);
			}
		}
	}

	/**
	 * Initializes the Arguments for the lib loading module
	 */
	public static void initArgs() {
		String os = System.getProperty("os.name");
		Out.line("PLATFORM short: " + os);
		if (os.startsWith("Win")) {
			PLATFORM = "windows";
		} else if (os.startsWith("Lin")) {
			PLATFORM = "linux";
		}
		String arch = System.getProperty("os.arch");
		short temp = 64;
		boolean flag = false;
		while (arch.length() > 0 && !flag)
			try {
				temp = Short.parseShort(arch);
				flag = true;
			} catch (NumberFormatException e) {
				arch = arch.substring(1);
			}
		ARCH = temp;
	}

	/**
	 * Fetches the libs from a online source
	 */
	public static void fetchLibs() {
		String appfolder = System.getenv("FRESCHMUSHROOM_HOME");
		if (appfolder == null) {
			appfolder = System.getenv("APPDATA");
		}
		if (appfolder == null) {
			appfolder = System.getProperty("user.home") + "/appdata";
		}
		File f = new File(appfolder + "/.freschmushroom");
		if (!f.exists()) {
			f.mkdir();
		}
		appfolder += "/.freschmushroom";
		try {
			URL u = new URL(
					"http://downloads.sourceforge.net/project/java-game-lib/Official%20Releases/LWJGL%202.9.0/lwjgl-2.9.0.zip?r=http%3A%2F%2Fsourceforge.net%2Fprojects%2Fjava-game-lib%2Ffiles%2FOfficial%2520Releases%2FLWJGL%25202.9.0%2F&ts=1370079043&use_mirror=heanet");
			URLConnection con = u.openConnection();
			con.connect();
			ZipInputStream zip = new ZipInputStream(con.getInputStream());
			ZipEntry ze = zip.getNextEntry();
			while (ze != null) {
				String name = ze.getName();
				name = name.substring(name.indexOf("/") + 1);
				Out.line(name);
				if (!ze.isDirectory()) {
					if (name.startsWith("jar")) {
						File ff = new File(System.getProperty("user.dir") + "/"
								+ name);
						if (!ff.getParentFile().exists()) {
							ff.getParentFile().mkdirs();
						}
						FileOutputStream fout = new FileOutputStream(ff);
						byte[] b = new byte[1024];
						int n = 0;
						while ((n = zip.read(b, 0, 1024)) != -1) {
							fout.write(b, 0, n);
						}
						fout.close();
					} else if (name.startsWith("native")) {
						name = name.replace("native", "natives");
						File ff = new File(appfolder + "/" + name);
						if (!ff.getParentFile().exists()) {
							ff.getParentFile().mkdirs();
						}
						FileOutputStream fout = new FileOutputStream(ff);
						byte[] b = new byte[1024];
						int n = 0;
						while ((n = zip.read(b, 0, 1024)) != -1) {
							fout.write(b, 0, n);
						}
						fout.close();
					}
				}
				ze = zip.getNextEntry();
			}
			zip.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Fetches the game resources (Images and Sounds)
	 */
	public static void fetchResources() {
		String appfolder = System.getProperty("user.dir") + "/test/";
		try {
			URL u = new URL(
					"https://github.com/Freschmushroom/Jump-n-Jan/blob/master/src/com.zip?raw=true");
			URLConnection con = u.openConnection();
			con.connect();
			ZipInputStream zip = new ZipInputStream(con.getInputStream());
			ZipEntry ze = zip.getNextEntry();
			while (ze != null) {
				if (ze.isDirectory()) {
					System.out.println(ze.getName());
					File f = new File(appfolder + ze.getName());
					if (!f.exists())
						f.mkdirs();
				} else {
					System.out.println(ze.getName());
					File f = new File(appfolder + ze.getName());
					if (!f.exists())
						f.createNewFile();
					FileOutputStream fout = new FileOutputStream(f);
					byte[] buffer = new byte[1024];
					int n = 0;
					while ((n = zip.read(buffer, 0, 1024)) != -1) {
						fout.write(buffer, 0, n);
					}
					fout.close();
				}
				ze = zip.getNextEntry();
			}
		} catch (IOException e) {

		}
	}

	/**
	 * Cares completely about the whole lib stuff
	 */
	public static void libs() {
		initArgs();
		if (!checkLibs()) {
			fetchLibs();
		}
		loadLibs();
	}

	/*
	 * Should be advanced to file per file check, not just if folder exists
	 */
	/**
	 * Checks if the libs exist
	 * 
	 * @return if the libs exist
	 */
	public static boolean checkLibs() {
		String appfolder = System.getenv("FRESCHMUSHROOM_HOME");
		if (appfolder == null) {
			appfolder = System.getenv("APPDATA");
		}
		if (appfolder == null) {
			appfolder = System.getProperty("user.home") + "/appdata";
		}
		File f = new File(appfolder + "/.freschmushroom");
		if (!f.exists()) {
			f.mkdir();
		}
		appfolder += "/.freschmushroom";
		File f_1 = new File(System.getProperty("user.dir") + "/jar/");
		File f_2 = new File(appfolder + "/natives/");
		return f_1.exists() && f_2.exists();
	}

	static {
		Out.inf(ServiceProvider.class, "19.02.13", "Felix", null);
	}

	public static void main(String[] args) {
		fetchResources();
	}
}
