/**
 * 
 */
package at.freschmushroom.xml;

/**
 * Interface f�r funktionierende XML-Elemente f�r das Verwenden mit XMLFile
 * 
 * @author Felix Resch
 *
 */
public interface XMLElement
{
	/**
	 * Soll das �bergeordnete Element zur�ckgeben.
	 * 
	 * @return das �bergeordnete Element
	 */
	public abstract XMLElement getParent();

	/**
	 * Setzt den parent f�r das aktuelle Element
	 */
	public abstract void setParent(XMLElement parent);

	/**
	 * Soll den Namen des Elements zur�ckgeben.
	 * 
	 * @return den Namen des Elements
	 */
	public abstract String getName();

	/**
	 * soll die Zeilen die dieses Element representieren zur�ckgeben
	 * 
	 * @return die Zeilen f�r das Dokument
	 */
	public abstract String[] getLines();
}
