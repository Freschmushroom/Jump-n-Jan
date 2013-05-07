package at.xml;

public class XMLParser {
	XMLFile file;

	public XMLParser(XMLFile file) {
		this.file = file;
	}

	public void parse() {
		String[] buffer = file.getLines();
		XMLNode actual = null;
		XMLComment actualC = null;
		boolean comment = false;
		boolean first = true;
		for (String s : buffer) {
			s = s.replace("\t", "    ");
			if (first) {
				file.setDeclaration(new XMLDeclaration());
				first = false;
			} else if (comment) {
				if (s.endsWith("-->")) {
					comment = false;
					if (!s.substring(0, s.length() - 3).equals(""))
						actualC.addLine(s.substring(0, s.length() - 3));
				} else {
					actualC.addLine(s);
				}
			} else if (s.startsWith("</")) {
				actual = (XMLNode) actual.getParent();
			} else if (s.startsWith("<!")) {
				comment = !s.endsWith("-->"); 
				actualC = new XMLComment(actual);
				actualC.addLine(s.substring(4, s.length() - 3));
			} else if (s.startsWith("<?")) {
				String[] parts = s.split(" ");
				String vers = null, encode = null, stand = null;
				for (int i = 0; i < parts.length; i++) {
					if (parts[i].startsWith("version")) {
						vers = parts[i].split("\"")[1];
					} else if (parts[i].startsWith("encoding")) {
						encode = parts[i].split("\"")[1];
					} else if (parts[i].startsWith("standalone")) {
						stand = parts[i].split("\"")[1];
					}
				}
				file.setDeclaration(new XMLDeclaration(vers, encode, stand));
			} else if (s.startsWith("<")) {
				String name = s.split(">")[0].substring(1);
				if (s.endsWith("</" + name + ">")) {
					String value = s.split(">")[1].split("<")[0];
					new XMLTag(actual, name, value);
				} else {
					if (actual == null) {

						file.setRoot(actual = new XMLNode(file.getRoot(), s
								.substring(1, s.length() - 2)));
					} else {
						actual = new XMLNode(actual, s.substring(1,
								s.length() - 1));
					}
				}
			} else if (s.contains("=") && !s.contains("\"")) {
				String name = s.split("=")[0];
				String value = s.split("=")[1];
				if (actual == null) {
					new XMLAttribut(name, value, file.getRoot());
				} else {
					new XMLAttribut(name, value, actual);
				}
			} else {
				String name = s.split("=")[0];
				String value = s.split("\"")[1];
				if (actual == null) {
					new XMLAttribut(name, value, file.getRoot());
				} else {
					new XMLAttribut(name, value, actual);
				}
			}
		}
	}
	/*
	 * public static void main(String args[]) { XMLFile file = new XMLFile("",
	 * "xml2"); XMLWriter w = new XMLWriter(file); w.writeToTerminal();
	 * w.writeToFile(); }
	 */
}
