package at.freschmushroom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Provides sevices like lib fetching or loading
 * 
 * @author Felix Resch
 * 
 */
public class ServiceProvider
{
	/**
	 * The architecture of the system
	 */
	public short ARCH;
	/**
	 * The platform of the system
	 */
	public String PLATFORM;

	public boolean requiresRestart;

	/**
	 * Loads the libs from the freschmushroom folder, or, when not set, the
	 * appdata: Windows: %APPDATA%\.freschmushroom\natives\ Linux:
	 * /home/user/appdata/.freschmushroom/natives Mac:????
	 */
	public void loadLibs()
	{
		String appfolder = System.getenv("FRESCHMUSHROOM_HOME");
		if (appfolder == null)
		{
			appfolder = System.getenv("APPDATA");
		}
		if (appfolder == null)
		{
			appfolder = System.getProperty("user.home") + "/appdata";
		}
		File f = new File(appfolder + "/.freschmushroom");
		if (!f.exists())
		{
			f.mkdir();
		}
		appfolder += "/.freschmushroom";
		Out.line("Appfolder is: " + appfolder);
		f = new File(appfolder + "/natives");
		if (!f.exists())
		{
			Out.err("FreschMushroom wasn't set up properly. Please try again.");
			System.exit(1);
		}
		System.setProperty("org.lwjgl.librarypath", appfolder + "/natives/" + PLATFORM);
	}

	/**
	 * Initializes the Arguments for the lib loading module
	 */
	public void initArgs()
	{
		String os = System.getProperty("os.name");
		Out.line("PLATFORM short: " + os);
		if (os.startsWith("Win"))
		{
			PLATFORM = "windows";
		}
		else if (os.startsWith("Lin"))
		{
			PLATFORM = "linux";
		}
		String arch = System.getProperty("os.arch");
		short temp = 64;
		boolean flag = false;
		while (arch.length() > 0 && !flag)
			try
			{
				temp = Short.parseShort(arch);
				flag = true;
			} catch (NumberFormatException e)
			{
				arch = arch.substring(1);
			}
		ARCH = temp;
	}

	/**
	 * Fetches the libs from a online source
	 */
	public void fetchLibs()
	{
		String appfolder = System.getenv("FRESCHMUSHROOM_HOME");
		if (appfolder == null)
		{
			appfolder = System.getenv("APPDATA");
		}
		if (appfolder == null)
		{
			appfolder = System.getProperty("user.home") + "/appdata";
		}
		File f = new File(appfolder + "/.freschmushroom");
		if (!f.exists())
		{
			f.mkdir();
		}
		appfolder += "/.freschmushroom";
		try
		{
			URL u = new URL(
					"http://downloads.sourceforge.net/project/java-game-lib/Official%20Releases/LWJGL%202.9.0/lwjgl-2.9.0.zip?r=http%3A%2F%2Fsourceforge.net%2Fprojects%2Fjava-game-lib%2Ffiles%2FOfficial%2520Releases%2FLWJGL%25202.9.0%2F&ts=1370079043&use_mirror=heanet");
			URLConnection con = u.openConnection();
			con.connect();
			ZipInputStream zip = new ZipInputStream(con.getInputStream());
			ZipEntry ze = zip.getNextEntry();
			while (ze != null)
			{
				String name = ze.getName();
				name = name.substring(name.indexOf("/") + 1);
				Out.line(name);
				if (!ze.isDirectory())
				{
					if (name.startsWith("jar"))
					{
						File ff = new File(System.getProperty("user.dir") + "/" + name);
						if (!ff.getParentFile().exists())
						{
							ff.getParentFile().mkdirs();
						}
						FileOutputStream fout = new FileOutputStream(ff);
						byte[] b = new byte[1024];
						int n = 0;
						while ((n = zip.read(b, 0, 1024)) != -1)
						{
							fout.write(b, 0, n);
						}
						fout.close();
					}
					else if (name.startsWith("native"))
					{
						name = name.replace("native", "natives");
						File ff = new File(appfolder + "/" + name);
						if (!ff.getParentFile().exists())
						{
							ff.getParentFile().mkdirs();
						}
						FileOutputStream fout = new FileOutputStream(ff);
						byte[] b = new byte[1024];
						int n = 0;
						while ((n = zip.read(b, 0, 1024)) != -1)
						{
							fout.write(b, 0, n);
						}
						fout.close();
					}
				}
				ze = zip.getNextEntry();
			}
			zip.close();
			u = new URL("https://bitbucket.org/kevglass/slick/src/96a4b840204c/trunk/Slick/lib/slick-util.jar?at=development");
			con = u.openConnection();
			con.connect();
			InputStream is = con.getInputStream();
			FileOutputStream fout = new FileOutputStream(new File(System.getProperty("user.dir") + "/jar/slick-util.jar"));
			byte[] b = new byte[1024];
			int n = 0;
			while ((n = is.read(b, 0, 1024)) != -1)
			{
				fout.write(b, 0, n);
			}
			fout.close();
		} catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		requiresRestart = true;
	}

	/**
	 * Checks the game resources for updates
	 */
	public boolean checkResources()
	{
		Out.line("Looking online for res.zip check sums");
		try
		{
			URL u = new URL("https://github.com/Freschmushroom/Jump-n-Jan/blob/master/hashes?raw=true");
			URLConnection con = u.openConnection();
			con.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			Properties hashes = new Properties();
			hashes.load(reader);
			String imgHash = computeHash("img/");
			String soundHash = computeHash("sound/");
			String levelHash = computeHash("level/");
			String localesHash = computeHash("locales/");
			String settingsHash = computeHash("settings.xml");
			reader.close();
			boolean upToDate = hashes.getProperty("img.md5").equalsIgnoreCase(imgHash) && hashes.getProperty("sound.md5").equalsIgnoreCase(soundHash)
					&& hashes.getProperty("level.md5").equalsIgnoreCase(levelHash) && hashes.getProperty("settings.md5").equalsIgnoreCase(settingsHash)
					&& hashes.getProperty("locales.md5").equalsIgnoreCase(localesHash);
			if (upToDate)
			{
				Out.line("Program is up-to-date");
			}
			else
			{
				Out.line("Program needs updating");
			}
			return upToDate;
		} catch (Exception e)
		{
			Out.line("Assuming the program is up-to-date, since no hashes were found");
		}
		return true;
	}

	public static void generateHashes()
	{
		try (OutputStream os = Files.newOutputStream(Paths.get("hashes")))
		{
			Properties hashes = new Properties();
			hashes.setProperty("img.md5", computeHash("img/"));
			hashes.setProperty("sound.md5", computeHash("sound/"));
			hashes.setProperty("level.md5", computeHash("level/"));
			hashes.setProperty("locales.md5", computeHash("locales/"));
			hashes.setProperty("settings.md5", computeHash("settings.xml"));
			hashes.setProperty("gamejar.md5", computeHash("JumpnJan.jar"));
			hashes.store(os, "GENERATED RESOURCE HASHES, USED FOR UPDATING");
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		generateHashes();
		// resources();
	}

	private static String computeHash(String path)
	{
		try
		{
			File file = new File(path);
			if (!file.exists())
			{
				Out.line("File " + file.getAbsolutePath() + " does not exist");
				return null;
			}
			final MessageDigest md = MessageDigest.getInstance("MD5");
			if (file.isFile())
			{
				try (InputStream is = Files.newInputStream(Paths.get(path)))
				{
					DigestInputStream dis = new DigestInputStream(is, md);
					while (dis.read() != -1)
						;
					dis.close();
				}
			}
			else if (file.isDirectory())
			{
				FileVisitor<Path> visitor = new FileVisitor<Path>() {
					public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException
					{
						return FileVisitResult.CONTINUE;
					}

					public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException
					{
						return FileVisitResult.CONTINUE;
					}

					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
					{
						try (InputStream is = Files.newInputStream(file))
						{
							DigestInputStream dis = new DigestInputStream(is, md);
							while (dis.read() != -1)
								;
							dis.close();
						}
						return FileVisitResult.CONTINUE;
					}

					public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException
					{
						return FileVisitResult.CONTINUE;
					}
				};
				Files.walkFileTree(Paths.get(path), visitor);
			}
			return bytesToHex(md.digest());
		} catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
			return null;
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Fetches the game resources (Images and Sounds)
	 */
	public void fetchResources()
	{
		String appfolder = System.getProperty("user.dir") + "/";
		try
		{
			URL u = new URL("https://github.com/Freschmushroom/Jump-n-Jan/blob/master/res.zip?raw=true");
			URLConnection con = u.openConnection();
			con.connect();
			ZipInputStream zip = new ZipInputStream(con.getInputStream());
			ZipEntry ze = zip.getNextEntry();
			while (ze != null)
			{
				if (ze.isDirectory())
				{
					Out.line(ze.getName());
					File f = new File(appfolder + ze.getName());
					if (!f.exists())
						f.mkdirs();
				}
				else
				{
					Out.line(ze.getName());
					File f = new File(appfolder + ze.getName());
					if (!f.exists())
					{
						f.getParentFile().mkdirs();
						f.createNewFile();
					}
					FileOutputStream fout = new FileOutputStream(f);
					byte[] buffer = new byte[1024];
					int n = 0;
					while ((n = zip.read(buffer, 0, 1024)) != -1)
					{
						fout.write(buffer, 0, n);
					}
					fout.close();
				}
				ze = zip.getNextEntry();
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		Out.line("Finished");
	}

	/**
	 * Cares completely about the whole lib stuff
	 */
	public void libs()
	{
		initArgs();
		if (!checkLibs())
		{
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
	public boolean checkLibs()
	{
		String appfolder = System.getenv("FRESCHMUSHROOM_HOME");
		if (appfolder == null)
		{
			appfolder = System.getenv("APPDATA");
		}
		if (appfolder == null)
		{
			appfolder = System.getProperty("user.home") + "/appdata";
		}
		File f = new File(appfolder + "/.freschmushroom");
		if (!f.exists())
		{
			f.mkdir();
		}
		appfolder += "/.freschmushroom";
		File f_1 = new File(System.getProperty("user.dir") + "/jar/");
		File f_2 = new File(appfolder + "/natives/");
		return f_1.exists() && f_2.exists();
	}

	{
		Out.inf(ServiceProvider.class, "19.02.13", "Felix", null);
	}

	public void resources()
	{
		if (!checkResources())
		{
			fetchResources();
		}
	}

	public boolean checkGameJar()
	{
		try
		{
			URL u = new URL("https://github.com/Freschmushroom/Jump-n-Jan/blob/master/hashes?raw=true");
			URLConnection con = u.openConnection();
			con.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			Properties hashes = new Properties();
			hashes.load(reader);
			reader.close();
			return computeHash("JumpnJan.jar").equalsIgnoreCase(hashes.getProperty("gamejar.md5"));
		} catch (Exception e)
		{
			Out.line("Assuming the jar is up to date due to connection issues");
			return true;
		}
	}

	public void fetchGameJar()
	{
		try
		{
			URL u = new URL("https://github.com/Freschmushroom/Jump-n-Jan/blob/master/JumpnJan.jar?raw=true");
			URLConnection con = u.openConnection();
			con.connect();
			Files.copy(con.getInputStream(), Paths.get("JumpnJan.jar"), StandardCopyOption.REPLACE_EXISTING);
			requiresRestart = true;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void gameJar()
	{
		if (!checkGameJar())
		{
			fetchGameJar();
		}
	}

	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

	public static String bytesToHex(byte[] bytes)
	{
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++)
		{
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}
}
