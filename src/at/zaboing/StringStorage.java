package at.zaboing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class StringStorage
{
	public static Properties language;

	public static final List<Properties> languages = new ArrayList<Properties>();

	public static String getString(String name)
	{
		return language.getProperty(name);
	}

	static
	{
		File[] locales = new File("locales").listFiles();
		for (File locale : locales)
		{
			if (locale.isFile())
			{
				try
				{
					BufferedReader reader = new BufferedReader(new FileReader(locale));
					Properties lang = new Properties();
					lang.load(reader);
					reader.close();
					languages.add(lang);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
//		language = languages.get(languages.size() - 1);
		language = languages.get(0);
	}
}
