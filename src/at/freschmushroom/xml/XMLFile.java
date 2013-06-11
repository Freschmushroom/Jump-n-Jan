package at.freschmushroom.xml;

import java.io.*;
import java.util.*;

import at.freschmushroom.Errorhandling;
import at.freschmushroom.Out;
import at.freschmushroom.ServiceProvider;

/**
 * Repräsentiert ein XML-File
 * @author Michael Pilz
 * @version 15/6/2012, 11:00
 */
public class XMLFile
{
	/**
	 * The root element of an XMLFile
	 */
	public XMLElement root = new XMLNode(null, "root");
	/**
	 * The declaration of an XMLFile
	 */
	public XMLDeclaration declaration = new XMLDeclaration();
	/**
	 * The File the XMLFile is based on
	 */
	private File f;
	/**
	 * The name of the file
	 * 
	 * Got unnecessary since all this is done via the File
	 */
	private String name;
	/**
	 * The path to the file
	 * 
	 * Got unnecessary since all this is done via the File
	 */
	private String path;
	/**
	 * The buffer for Runtime Efficient IO Access
	 */
	private List<String> buffer = new ArrayList<String>();
	/**
	 * Constructs a new XMLFile based on the given File
	 * @param f the File this XMLFile is based on
	 */
	public XMLFile(File f) {
		this.f = f;
		if(!f.exists())
			f.getAbsoluteFile().getParentFile().mkdirs();
		this.name = f.getName();
		this.path = f.getPath();
		readBuffer();
	}
	/**
	 * Constructs a new XMLFile based on the File specified by the given path and name
	 * @param path the path to the file
	 * @param name the name of the file (without .xml)
	 */
	public XMLFile(String path, String name)
	{
		File f = new File(path);
		if(!f.exists())
			f.mkdir();
		this.name = name + ".xml";
		this.path = System.getProperty("user.dir") + path;
		readBuffer();
	}
	/**
	 * Reads the File into the Runtime Efficient IO Access Buffer
	 */
	private void readBuffer()
	{
		try {
			if(f == null)
				f = new File(path + "\\" + name + ".xml");
			if(!f.exists()) {
//				f.getAbsoluteFile().getParentFile().mkdirs();
//				f.createNewFile();
//				return;
				ServiceProvider.fetchResources();
			}
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line = null;
			
			while((line = br.readLine()) != null)
			{
				int ws = 0;
				for(int i = 0; i < line.length(); i++) {
					if(line.charAt(i) == ' ') {
						ws++;
					} else {
						break;
					}
				}
				buffer.add(line.substring(ws));
			}
			
			br.close();
			
			XMLParser p = new XMLParser(this);
			p.parse();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * Returns the lines of the file
	 * @return the lines of the file stored in the Runtime Efficient IO Access Buffer
	 */
	public String[] getLines()
	{
		String[] ret = new String[buffer.size()];
		for(int i = 0; i < buffer.size(); i++)
			ret[i] = buffer.get(i);
		return ret;
	}
	/**
	 * Adds a string of characters to the end of the file
	 * @param string the character which should be appended
	 */
	public void write(String string)
	{
		buffer.set(buffer.size() - 1, buffer.get(buffer.size() - 1) + string);
	}
	/**
	 * Adds a string of characters to the end of the file in a new line
	 * @param string the character which should be appended
	 */
	public void writeln(String string)
	{
		buffer.add(string);
	}
	/**
	 * Clears the Runtime Efficient IO Access Buffer and the File on the FS
	 */
	public void clearFile()
	{
		buffer.clear();
		File f = new File(path + "/" + name);
		f.delete();
		try {
			f.createNewFile();
		} catch(IOException e) {
			Errorhandling.handle(e);
			Out.err("Failed to create file: " + path + "/" + name);
		}
	}
	/**
	 * Writes the Runtime Efficient IO Access Buffer to the File
	 */
	public void close()
	{
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(path + "/" + name));
			for(String s : buffer)
			{
				bw.write(s);
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Failed to write to File: " + path + "/" + name);
		}
	}
	/**
	 * Returns the root element of the Document
	 * @return the root element of the Document
	 */
	public XMLElement getRoot() {
		return root;
	}
	/**
	 * Sets the root element of the Document
	 * @param root the new root element
	 */
	public void setRoot(XMLElement root) {
		this.root = root;
	}
	/**
	 * Returns the declaration of the XML Document
	 * @return the declaration of the XML Document
	 */
	public XMLDeclaration getDeclaration() {
		return declaration;
	}
	/**
	 * Sets the declaration of the Document
	 * @param declaration the new declaration of the Document
	 */
	public void setDeclaration(XMLDeclaration declaration) {
		this.declaration = declaration;
	}
	
	static {
		Out.inf(XMLFile.class, "01.06.13", "Felix", null);
	}
	
}
