package at.jumpandjan.gui;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.Display;

import utils.TrueTypeFont;
import at.freschmushroom.Out;
import at.freschmushroom.xml.XMLAttribute;
import at.freschmushroom.xml.XMLElement;
import at.freschmushroom.xml.XMLFile;
import at.freschmushroom.xml.XMLNode;

/**
 * The option menu
 * 
 * @author Michael
 *
 */
public class GuiOptions extends Gui
{
	/**
	 * The xml file containing the settings
	 */
	public static final XMLFile settingsFile = new XMLFile(new File("settings.xml"));
	/**
	 * A map of data type with their corresponding gui components
	 */
	public static final Map<String, Component> dataTypes = new HashMap<String, Component>();

	/**
	 * A back button
	 */
	private CompButton back;
	/**
	 * A panel just for fun
	 */
	private CompPanel panel;

	public GuiOptions()
	{
		back = new CompButton(this, 0, 0, 150, 40, "<-- Back");
		back.setCenter(100, Display.getHeight() - 75);
		back.addButtonListener(new CloseGuiListener());
		panel = new CompPanel(this, 0, 0, 640, 480);
		panel.add(back);
		CompLabel optionsLabel = new CompLabel(this, 10, 10, 620, 20, "Options").setTextAlignment(TrueTypeFont.ALIGN_CENTER);
		optionsLabel.color = "000000";
		panel.add(optionsLabel);

		XMLNode root = (XMLNode) settingsFile.root;
		CompLabel settingName;
		Component settingComp;
		int i = 0;
		for (XMLElement xmlelement : root.getChildren())
		{
			XMLNode setting = (XMLNode) xmlelement;
			String text = ((XMLAttribute) setting.getChild("text", false)).getValue();
			settingName = new CompLabel(this, 20 + (i % 2) * 320, 50 + (i / 2) * 75, 280, 20, text);
			settingName.color = "000000";
			settingName.setTextAlignment(TrueTypeFont.ALIGN_LEFT);

			String type = ((XMLAttribute) setting.getChild("type", false)).getValue();
//			String value = ((XMLAttribute) setting.getChild("value", false)).getValue();
			settingComp = (Component) dataTypes.get(type).copy();
			settingComp.setX(settingName.getX());
			settingComp.setY(settingName.getY() + 30);
			settingComp.setWidth(280);
			settingComp.setHeight(40);
			settingComp.parent = this;

			panel.add(settingName);
			panel.add(settingComp);
			i++;
		}

		components.add(panel);
	}

	static
	{
		Out.inf(GuiOptions.class, "01.06.2013", "Michael", null);
	}

	static
	{
		dataTypes.put("bool", new CompYesNoButton(null, 0, 0));
		dataTypes.put("label", new CompLabel(null, 0, 0, 0, 0, ""));
	}
}
