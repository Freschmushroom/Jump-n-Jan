package at.freschmushroom.xml;

import java.io.*;
import java.util.*;

import at.freschmushroom.Errorhandling;
import at.freschmushroom.Out;

/**
 * Repräsentiert ein XML-File
 * @author Michael Pilz
 * @version 15/6/2012, 11:00
 */
public class XMLFile
{
	public XMLElement root = new XMLNode(null, "root");
	public XMLDeclaration declaration = new XMLDeclaration();
	
	private File f;
	private String name;
	private String path;
	
	private List<String> buffer = new ArrayList<String>();
	public XMLFile(File f) {
		this.f = f;
		if(!f.exists())
			f.mkdir();
		this.name = f.getName();
		this.path = f.getPath();
		readBuffer();
	}
	public XMLFile(String path, String name)
	{
		File f = new File(path);
		if(!f.exists())
			f.mkdir();
		this.name = name + ".xml";
		this.path = System.getProperty("user.dir") + path;
		readBuffer();
	}
	
	private void readBuffer()
	{
		try {
			if(f == null)
				f = new File(path + "\\" + name + ".xml");
			if(!f.exists()) {
				f.createNewFile();
				return;
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
			Errorhandling.handle(e);
			Out.err("Failed to read from file " + path + "/" + name);
		}
	}
	
	public String[] getLines()
	{
		String[] ret = new String[buffer.size()];
		for(int i = 0; i < buffer.size(); i++)
			ret[i] = buffer.get(i);
		return ret;
	}
	
	public void write(String string)
	{
		buffer.set(buffer.size() - 1, buffer.get(buffer.size() - 1) + string);
	}
	
	public void writeln(String string)
	{
		buffer.add(string);
	}
	
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

	public XMLElement getRoot() {
		return root;
	}

	public void setRoot(XMLElement root) {
		this.root = root;
	}

	public XMLDeclaration getDeclaration() {
		return declaration;
	}

	public void setDeclaration(XMLDeclaration declaration) {
		this.declaration = declaration;
	}
	
}
