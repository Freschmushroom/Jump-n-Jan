package at.jumpandjan.levelcreator;

import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glRectf;

import org.lwjgl.input.Keyboard;

import at.jumpandjan.EntityFinishFlag;
import at.jumpandjan.EntityHildegard;
import at.jumpandjan.EntityMeatball;
import at.jumpandjan.EntityStartFlag;
import at.jumpandjan.EntityWaitrose;
import at.jumpandjan.gui.Gui;

public class ObjectChooser extends Gui {
	private boolean isVisible;
	private ObjectChooserSlot[][] objects = new ObjectChooserSlot[10][7];
	private int selectedIndex;

	public ObjectChooser() {
		objects[0][0] = new ObjectChooserSlotTrash(40, 40, 32, 32);
		addSlot(new EntityStartFlag(0, 0, 32, 32, null));
		addSlot(new EntityFinishFlag(0, 0, 32, 32, null));
		addSlot(new EntityMeatball(0, 0, 32, 32, null));
		addSlot(new EntityWaitrose(0, 0, 32, 32, null));
		addSlot(new EntityHildegard(0, 0, 32, 32, null));
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void processMouseInput(int button, boolean buttonState, int eventX,
			int eventY, int eventDX, int eventDY) {

	}

	public void processKeyboardInput(int key, char keyChar, boolean keyState) {
		if (keyState) {
			if (key == Keyboard.KEY_ESCAPE || key == Keyboard.KEY_O) {
				setVisible(false);
			} else if (key == Keyboard.KEY_LEFT) {
				selectedIndex--;
				selectedIndex += 69;
				selectedIndex %= 69;
			} else if (key == Keyboard.KEY_RIGHT) {
				selectedIndex++;
				selectedIndex += 69;
				selectedIndex %= 69;
			}
		}
	}

	public void addSlot(at.jumpandjan.Object o) {
		for (int i = 0; i < objects.length * objects[0].length; i++) {
			int j = i % objects.length;
			int k = i / objects.length;
			if (objects[j][k] == null) {
				objects[j][k] = new ObjectChooserSlot(o, j * 32 + 40,
						k * 32 + 40, 32, 32);
				return;
			}
		}
		System.err.println("No empty slot found. Please try again");
	}

	public void paint() {
		if (!isVisible)
			return;
		glColor4f(0f, 0f, 0.3f, 0.5f);
		glRectf(37, 37, 603, 443);
		glColor4f(1, 1, 1, 1);

		for (ObjectChooserSlot[] oa : objects) {
			for (ObjectChooserSlot o : oa) {
				if (o != null)
					o.render();
			}
		}
	}
}
