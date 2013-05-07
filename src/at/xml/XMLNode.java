package at.xml;

import java.util.ArrayList;
/**
 * Stellt einen Container für XMLElemente dar
 * @author Felix Resch
 *
 */
public class XMLNode implements XMLElement {
	/**
	 * Speichert die Unterelemente des Containers
	 */
	private ArrayList<XMLElement> children = new ArrayList<XMLElement>();
	/**
	 * Stellt das Übergeordnete Element des Containers dar
	 */
	private XMLElement parent;
	/**
	 * Den Namen des Containers 
	 */
	private String name;
	/**
	 * Generiert einen neuen Container
	 * @param parent das übergeordnete Element 
	 * @param name der Name der Node
	 */
	public XMLNode(XMLElement parent, String name) {
		this.parent = parent;
		this.name = name;
		if(parent == null) return;
		((XMLNode)parent).addChild(this);
	}
	@Override
	public XMLElement getParent() {
		return parent;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String[] getLines() {
		String[] res = new String[] {"<" + name + ">"};
		for(XMLElement x : children) {
			res = add(res, x.getLines());
		}
		res = add(res, new String[] {"</" + name + ">"});
		return res;
	}
	/**
	 * Fügt dem Container ein Element hinzu
	 * @param x das Element
	 */
	public void addChild(XMLElement x) {
		x.setParent(this);
		children.add(x);
	}
	/**
	 * Fügt zwei Arrays zusammen
	 * @param a das erste array
	 * @param b das zweite array
	 * @return das zusammengefügte array
	 */
	private String[] add(String[] a, String[] b) {
		String[] c = new String[a.length + b.length];
		for(int i = 0; i < a.length; i++) {
			c[i] = a[i];
		}
		for(int i = 0; i < b.length; i++) {
			c[i + a.length] = b[i];
		}
		return c;
	}
	@Override
	public void setParent(XMLElement parent) {
		this.parent = parent;
	}
	public XMLElement getChild(String name, boolean safe) {
		for(XMLElement x : children) {
			if(x.getName().equals(name)) {
				return x;
			}
		}
		return safe ? new XMLNode(this, name) : null;
	}
	public ArrayList<XMLElement> getChildren() {
		return children;
	}
	
}
