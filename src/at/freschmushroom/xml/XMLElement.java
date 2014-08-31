/**
 * 
 */
package at.freschmushroom.xml;

/**
 * Interface für funktionierende XML-Elemente für das Verwenden mit XMLFile
 * 
 * @author Felix Resch
 *
 */
public interface XMLElement
{
	/**
	 * Soll das Übergeordnete Element zurückgeben.
	 * 
	 * @return das übergeordnete Element
	 */
	public abstract XMLElement getParent();

	/**
	 * Setzt den parent für das aktuelle Element
	 */
	public abstract void setParent(XMLElement parent);

	/**
	 * Soll den Namen des Elements zurückgeben.
	 * 
	 * @return den Namen des Elements
	 */
	public abstract String getName();

	/**
	 * soll die Zeilen die dieses Element representieren zurückgeben
	 * 
	 * @return die Zeilen für das Dokument
	 */
	public abstract String[] getLines();
}
