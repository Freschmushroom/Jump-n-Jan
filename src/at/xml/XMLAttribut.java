package at.xml;
/**
 * Stellt ein Attribut im XML Format dar
 * @author Felix Resch
 *
 */
public class XMLAttribut implements XMLElement {
	/**
	 * Der Name des Attributs
	 */
	private String name;
	/**
	 * Der Wert des Attribut
	 */
	private String value;
	/**
	 * Des übergeordnete Element des Attributes
	 */
	private XMLElement parent;
	/**
	 * Generiert ein neues Attribut
	 * @param name der name des Attributs
	 * @param value der Wert des Attributes
	 * @param parent das Übergeordnete Element des Attributes
	 */
	public XMLAttribut(String name, String value, XMLElement parent) {
		this.name = name;
		this.value = value;
		this.parent = parent;
		((XMLNode)parent).addChild(this);
	}
	@Override
	public XMLElement getParent() {
		return parent;
	}

	@Override
	public void setParent(XMLElement parent) {
		this.parent = parent;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String[] getLines() {
		return new String[] {name + "=\"" + value + "\""};
	}
	/**
	 * Gibt den Wert des Attributs zurück
	 * @return Wert des Attributs 
	 */
	public String getValue() {
		return value;
	}
}
