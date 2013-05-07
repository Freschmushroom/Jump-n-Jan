package at.xml;

/**
 * Diese Klasse schreibt alle Elemente in XML
 * 
 * @author Michael Pilz
 * @version
 */
public class XMLWriter {
	private XMLFile file;

	public XMLWriter(XMLFile file) {
		this.file = file;
	}

	public void writeToFile() {
		file.clearFile();
		int whitespace = 0;
		file.writeln(file.getDeclaration().getLines()[0]);
		file.writeln("<" + file.getRoot().getName() + ">");
		whitespace++;
		for (XMLElement element : ((XMLNode) file.getRoot()).getChildren()) {
			for (int i = 0; i < whitespace; i++)
				file.write(" ");
			if (element instanceof XMLNode) {
				int ws = whitespace;
				for (String s : element.getLines()) {
					if (s.startsWith("</") && ws != whitespace) {
						ws--;
						for (int i = 0; i < ws; i++)
							s = "  " + s;
						file.writeln(s);
					} else if (s.startsWith("<!")) {
						for (int i = 0; i < ws; i++)
							s = "  " + s;
						file.writeln(s);
					} else if (s.startsWith(("<"))) {
						for (int i = 0; i < ws; i++)
							s = "  " + s;
						ws++;
						file.writeln(s);
					} else {
						for (int i = 0; i < ws; i++)
							s = "  " + s;
						file.writeln(s);
					}
				}
			} else {
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

	public void writeToTerminal() {
		// file.clearFile();
		int whitespace = 0;
		System.out.println(file.getDeclaration().getLines()[0]);
		System.out.println("<" + file.getRoot().getName() + ">");
		whitespace++;
		for (XMLElement element : ((XMLNode) file.getRoot()).getChildren()) {
			for (int i = 0; i < whitespace; i++)
				System.out.print(" ");
			if (element instanceof XMLNode) {
				int ws = whitespace;
				for (String s : element.getLines()) {
					if (s.startsWith("</") && ws != whitespace) {
						ws--;
						for (int i = 0; i < ws; i++)
							s = "  " + s;
						System.out.println(s);
					} else if (s.startsWith("<!")) {
						for (int i = 0; i < ws; i++)
							s = "  " + s;
						System.out.println(s);
					} else if (s.startsWith(("<"))) {
						for (int i = 0; i < ws; i++)
							s = "  " + s;
						if (!s.contains("</"))
							ws++;
						System.out.println(s);
					} else {
						for (int i = 0; i < ws; i++)
							s = "  " + s;
						System.out.println(s);
					}
				}
			} else {
				String ws = "";
				for (int i = 0; i < whitespace; i++)
					ws += "  ";
				for (String s : element.getLines())
					System.out.println(ws + s);
			}
		}
		System.out.println("</" + file.getRoot().getName() + ">");
	}
	/*
	 * public static void main(String[] args) { XMLFile f = new XMLFile("",
	 * "xml2"); XMLWriter w = new XMLWriter(f); XMLDeclaration dec = new
	 * XMLDeclaration(); XMLNode root = new XMLNode(null, "school"); XMLNode
	 * child1 = new XMLNode(root, "SchoolClass"); XMLNode child2 = new
	 * XMLNode(child1, "Student"); XMLNode child3 = new XMLNode(root,
	 * "Directory"); XMLAttribut att = new XMLAttribut("Test", "test", child1);
	 * XMLComment child4 = new XMLComment(child1); child4.addLine("HEYHO");
	 * f.setDeclaration(dec); f.setRoot(root); w.writeToFile(); }
	 */
}
