package at.freschmushroom.xml;

import java.util.ArrayList;

import at.freschmushroom.Out;
/**
 * Klasse zur erstellung von Kommentaren in XML
 * @author Felix Resch
 *
 */
public class XMLComment implements XMLElement {
	/**
	 * Speichert die Zeilen des Kommentares
	 */
	private ArrayList<String> lines = new ArrayList<String>();
	/**
	 * Stellt das übergeordnete Element dar 
	 */
	private XMLElement parent;
	@Override
	public XMLElement getParent() {
		return parent;
	}

	@Override
	public void setParent(XMLElement parent) {
		this.parent = parent;
	}
	public XMLComment(XMLElement parent) {
		this.parent = parent;
		((XMLNode)parent).addChild(this);

	}
	@Override
	public String getName() {
		return null;
	}
	@Override
	public String[] getLines() {
		if(lines.size() < 1) return null;
		String[] res = new String[] {"<!--" + lines.get(0)};
		for(int i = 1; i < lines.size(); i++) {
			res = addString(res, lines.get(i));
		}
		res = addString(res, "-->");
		return res;
	}
	/**
	 * Fügt einem Array am Ende ein Element dazu
	 * @param a das Array das vergrößert werden soll
	 * @param b das Element das angehängt werden soll
	 * @return das neue Array
	 */
	private String[] addString(String a[], String b) {
		String[] c = new String[a.length + 1];
		for(int i = 0; i < a.length; i++) {
			c[i] = a[i];
		}
		c[c.length - 1] = b;
		return c;
	}
	/**
	 * Fügt eine neue Zeile dem Kommentar hinzu
	 * @param line die Zeile
	 */
	public void addLine(String line) {
		lines.add(line);
	}
	public ArrayList<String> getLine() {
		return lines;
	}
	
	static {
		Out.inf(XMLComment.class, "01/06/13", "Felix", null);
	}
}
