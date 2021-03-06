package at.freschmushroom.xml;

import at.freschmushroom.Out;

/**
 * Diese Klasse schreibt alle Elemente in XML
 * 
 * @author Michael Pilz
 * @version
 */
public class XMLWriter
{
	/**
	 * The XMLFile that should be written into a File
	 */
	private XMLFile file;

	/**
	 * Constructs a new XMLWriter using the given File
	 * 
	 * @param file
	 *            the file that should be written
	 */
	public XMLWriter(XMLFile file)
	{
		this.file = file;
	}

	/**
	 * Writes the XMLFile in the appropriate XML Syntax into a File
	 */
	public void writeToFile()
	{
		file.clearFile();
		int whitespace = 0;
		file.writeln(file.getDeclaration().getLines()[0]);
		file.writeln("<" + file.getRoot().getName() + ">");
		whitespace++;
		for (XMLElement element : ((XMLNode) file.getRoot()).getChildren())
		{
			for (int i = 0; i < whitespace; i++)
				file.write(" ");
			if (element instanceof XMLNode)
			{
				int ws = whitespace;
				for (String s : element.getLines())
				{
					if (s.startsWith("</") && ws != whitespace)
					{
						ws--;
						for (int i = 0; i < ws; i++)
							s = "  " + s;
						file.writeln(s);
					}
					else if (s.startsWith("<!"))
					{
						for (int i = 0; i < ws; i++)
							s = "  " + s;
						file.writeln(s);
					}
					else if (s.startsWith(("<")))
					{
						for (int i = 0; i < ws; i++)
							s = "  " + s;
						ws++;
						file.writeln(s);
					}
					else
					{
						for (int i = 0; i < ws; i++)
							s = "  " + s;
						file.writeln(s);
					}
				}
			}
			else
			{
				String ws = "";
				for (int i = 0; i < whitespace; i++)
					ws += "  ";
				for (String s : element.getLines())
					file.writeln(ws + s);
			}
		}
		file.writeln("</" + file.getRoot().getName() + ">");
		file.close();
	}

	/**
	 * Writes the XMLFile in the appropriate XML Syntax onto the Terminal
	 */
	public void writeToTerminal()
	{
		// file.clearFile();
		int whitespace = 0;
		System.out.println(file.getDeclaration().getLines()[0]);
		System.out.println("<" + file.getRoot().getName() + ">");
		whitespace++;
		for (XMLElement element : ((XMLNode) file.getRoot()).getChildren())
		{
			for (int i = 0; i < whitespace; i++)
				System.out.print(" ");
			if (element instanceof XMLNode)
			{
				int ws = whitespace;
				for (String s : element.getLines())
				{
					if (s.startsWith("</") && ws != whitespace)
					{
						ws--;
						for (int i = 0; i < ws; i++)
							s = "  " + s;
						System.out.println(s);
					}
					else if (s.startsWith("<!"))
					{
						for (int i = 0; i < ws; i++)
							s = "  " + s;
						System.out.println(s);
					}
					else if (s.startsWith(("<")))
					{
						for (int i = 0; i < ws; i++)
							s = "  " + s;
						if (!s.contains("</"))
							ws++;
						System.out.println(s);
					}
					else
					{
						for (int i = 0; i < ws; i++)
							s = "  " + s;
						System.out.println(s);
					}
				}
			}
			else
			{
				String ws = "";
				for (int i = 0; i < whitespace; i++)
					ws += "  ";
				for (String s : element.getLines())
					System.out.println(ws + s);
			}
		}
		System.out.println("</" + file.getRoot().getName() + ">");
	}

	static
	{
		Out.inf(XMLWriter.class, "01/06/13", "Michi", null);
	}

}
