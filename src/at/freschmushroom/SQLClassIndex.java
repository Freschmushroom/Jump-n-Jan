package at.freschmushroom;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Class to create a class List usable with the standard mysql servers
 * 
 * The Generated Script creates a table 'class' where all the fields are
 * automatically filled in
 * 
 * @author Felix
 *
 */
public class SQLClassIndex
{
	/**
	 * Creates the Skript
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			File f = new File(System.getProperty("user.dir") + "/src/"); // Set
																			// entry
																			// point
																			// into
																			// FS
			PrintStream sql = new PrintStream("insert_classes.sql"); // Create
																		// the
																		// script

			sql.println("DROP TABLE IF EXISTS class;"); // Clean out dated
														// tables
			sql.println("CREATE TABLE class (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100), package VARCHAR(300), programmer_id INT(11), type VARCHAR(1), version VARCHAR(12), state INT);"); // Create
																																																		// new
																																																		// table
			sql.println();

			index(f, sql, ""); // index beginning from the entry point

			sql.println();
			sql.println("UPDATE class SET programmer_id=3, type='C', version='13/05/18', state = 0;"); // Set
																										// the
																										// standard
																										// values

			sql.close();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Recursive Method to create the SQL Insert Statements needed for the
	 * database
	 * 
	 * @param f
	 *            the Directory to start from
	 * @param sql
	 *            the output Stream
	 * @param pack
	 *            the package (path) of the classes
	 */
	private static void index(File f, PrintStream sql, String pack)
	{
		if (!f.isDirectory())
		{ // only possible if filesystem has been altered
			System.exit(1);
		}
		File[] children = f.listFiles(); // Fetch file list
		if (pack.startsWith("com.")) // Exclude external packages
			return;
		for (File child : children)
		{
			if (child.isDirectory())
			{ // If the File is a Directory
				index(child, sql, pack + (pack.length() == 0 ? "" : ".") + child.getName()); // Index
																								// it
			}
			else if (child.getAbsolutePath().endsWith(".java"))
			{ // If its a java File
				sql.print("INSERT INTO class SET "); // Standard insert
														// statement
				sql.print("name='" + child.getName().split(".java")[0] + "', "); // set
																					// the
																					// name
				sql.println("package='" + pack + "';"); // set the package
			}
		}
	}

}
