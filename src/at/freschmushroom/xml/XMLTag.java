package at.freschmushroom.xml;

public class XMLTag implements XMLElement {
	private XMLElement parent = null;
	private String name = null;
	private String value = null;
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
		return new String[] {"<" + name + ">" + value + "</" + name + ">"};
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setName(String name) {
		this.name = name;
	}

	public XMLTag(XMLElement parent, String name, String value) {
		super();
		this.parent = parent;
		this.name = name;
		this.value = value;
		((XMLNode)parent).addChild(this);
	}

}
