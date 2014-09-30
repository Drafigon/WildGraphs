package operations.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;

final class FormatButtonGroup {
	private List<JFormatButton> list = new ArrayList<JFormatButton>();
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	void addButton(JFormatButton button) {
		buttonGroup.add(button);
		list.add(button);
	}
	
	JFormatButton getSelected() {
		for(JFormatButton b : list) {
			if(b.isSelected()) {
				return b;
			}
		}
		
		return null;
	}
}