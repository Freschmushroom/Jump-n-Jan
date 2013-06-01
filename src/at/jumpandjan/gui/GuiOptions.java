package at.jumpandjan.gui;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import utils.TrueTypeFont;
import at.freschmushroom.xml.XMLAttribut;
import at.freschmushroom.xml.XMLElement;
import at.freschmushroom.xml.XMLFile;
import at.freschmushroom.xml.XMLNode;
import at.jumpandjan.Constants;

public class GuiOptions extends Gui {
	public static final XMLFile settingsFile = new XMLFile(new File(
			"settings.xml"));
	public static final Map<String, Component> dataTypes = new HashMap<String, Component>();

	private CompButton back;
	private CompPanel panel;

	public GuiOptions() {
		back = new CompButton(0, 0, 150, 40, "<-- Back");
		back.setCenter(100, Constants.getCameraHeight() - 75);
		back.addButtonListener(new CloseGuiListener());
		panel = new CompPanel(0, 0, 640, 480);
		panel.add(back);
		CompLabel optionsLabel = new CompLabel(10, 10, 620, 20, "Options")
				.setTextAlignment(TrueTypeFont.ALIGN_CENTER);
		optionsLabel.color = "000000";
		panel.add(optionsLabel);
		
		XMLNode root = (XMLNode) settingsFile.root;
		CompLabel settingName;
		Component settingComp;
		int i = 0;
		for (XMLElement xmlelement : root.getChildren()) {
			XMLNode setting = (XMLNode) xmlelement;
			String text = ((XMLAttribut) setting.getChild("text", false)).getValue();
			settingName = new CompLabel(20 + (i % 2) * 320, 50 + (i / 2) * 75, 280, 20, text);
			settingName.color = "000000";
			settingName.setTextAlignment(TrueTypeFont.ALIGN_LEFT);
			
			
			String type = 
					((XMLAttribut) setting.getChild("type", false)).getValue();
			String value = ((XMLAttribut) setting.getChild("value", false)).getValue();
			settingComp = (Component) dataTypes.get(type)
					.copy();
			settingComp.setX(settingName.getX());
			settingComp.setY(settingName.getY() + 30);
			settingComp.setWidth(280);
			settingComp.setHeight(40);
			apply(type, value);
			
			System.out.println(settingComp.getWidth());
			
			panel.add(settingName);
			panel.add(settingComp);
			i++;
		}
		
		components.add(panel);
	}

	static {
		dataTypes.put("bool", new CompYesNoButton(0, 0));
		dataTypes.put("label", new CompLabel(0, 0, 0, 0, ""));
	}

	public static void apply(String datatype, String value) {
		if (dataTypes.get(datatype) == null) {
			return;
		}
		if (dataTypes.get(datatype) instanceof CompYesNoButton) {
			((CompYesNoButton) dataTypes.get(datatype)).setValue("true"
					.equals(value));
		}
	}
}
