package at.freschmushroom;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class SQLClassIndex {

	public static void main(String[] args) {
		try {
			File f = new File(System.getProperty("user.dir") + "/src/");
			PrintStream sql = new PrintStream("insert_classes.sql");
			
			sql.println("DROP TABLE IF EXISTS class;");
			sql.println("CREATE TABLE class (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100), package VARCHAR(300), programmer_id INT(11), type VARCHAR(1), version VARCHAR(12));");
			sql.println();
			
			index(f, sql, "");
			
			
			sql.println();
			sql.println("UPDATE class SET programmer_id=3, type='C', version='13/05/18';");
			
			sql.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private static void index(File f, PrintStream sql, String pack) {
		if(!f.isDirectory()) {
			System.exit(1);
		}
		File[] children = f.listFiles();
		if(pack.startsWith("com."))
			return;
		for(File child : children) {
			if(child.isDirectory()) {
				index(child, sql, pack + (pack.length() == 0 ? "" : ".") + child.getName());
			} else if (child.getAbsolutePath().endsWith(".java")) {
				sql.print("INSERT INTO class SET ");
				sql.print("name='" + child.getName().split(".java")[0] + "', ");
				sql.println("package='" + pack + "';");
			}
		}
	}

}
