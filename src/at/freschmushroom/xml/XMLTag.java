package at.freschmushroom.xml;

import at.freschmushroom.Out;

/**
 * Class to represent XML Tags like the following:
 * 
 * <name>value</name>
 * 
 * @author Felix
 *
 */
public class XMLTag implements XMLElement
{
	/**
	 * The parent element of this element
	 */
	private XMLElement parent = null;
	/**
	 * The name of this Element
	 */
	private String name = null;
	/**
	 * The value of this element
	 */
	private String value = null;

	@Override
	public XMLElement getParent()
	{
		return parent;
	}

	@Override
	public void setParent(XMLElement parent)
	{
		this.parent = parent;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public String[] getLines()
	{
		return new String[] { "<" + name + ">" + value + "</" + name + ">" };
	}

	/**
	 * Returns the Value of the element
	 * 
	 * @return the value of the element
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * Set the value of the element
	 * 
	 * @param value
	 *            the new value
	 */
	public void setValue(String value)
	{
		this.value = value;
	}

	/**
	 * Set the name of the element
	 * 
	 * @param name
	 *            the new name of the element
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Constructs a new XMLTag using the given Informations
	 * 
	 * @param parent
	 *            the parent Element of this element
	 * @param name
	 *            the name of this element
	 * @param value
	 *            the value of this element
	 */
	public XMLTag(XMLElement parent, String name, String value)
	{
		super();
		this.parent = parent;
		this.name = name;
		this.value = value;
		((XMLNode) parent).addChild(this);
	}

	static
	{
		Out.inf(XMLTag.class, "01/06/13", "Felix", null);
	}
}
