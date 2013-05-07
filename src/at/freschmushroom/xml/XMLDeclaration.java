package at.freschmushroom.xml;
/**
 * Einfache XML Deklaration
 * @author Felix Resch
 *
 */
public class XMLDeclaration implements XMLElement {
	/**
	 * Die XML Version
	 */
	private String version = "1.0";
	/**
	 * Die Zeichencodierung
	 */
	private String encoding = "UTF-8";
	/**
	 * k.a.
	 */
	private String standAlone = "yes";
	/**
	 * Generiert die Standard deklaration
	 */
	public XMLDeclaration() {
	}
	/**
	 * Generiert eine Deklaration mit den gegebenen Daten 
	 * @param version die XMLVersion
	 * @param encoding die Zeichenkodierung
	 * @param standAlone k.a.
	 */
	public XMLDeclaration(String version, String encoding, String standAlone) {
		super();
		if(!(version == null))
			this.version = version;
		if(!(encoding == null))
			this.encoding = encoding;
		if(!(standAlone == null))
			this.standAlone = standAlone;
	}
	@Override
	public XMLElement getParent() {
		return null;
	}

	@Override
	public String getName() {
		return "Declaration";
	}

	@Override
	public String[] getLines() {
		return new String[] {"<?xml " + "version=\"" + version + "\" encoding=\"" + encoding + "\" standalone=\"" + standAlone + "\"?>"};
	}
	@Override
	public void setParent(XMLElement parent) {
	}
}
