package ui;

import javax.swing.JPanel;

public class JToolPanel extends JPanel {
	private final String NAME;

	public JToolPanel(String name) {
		super();
		NAME = name;
	}

	@Override
	public String toString() {
		return NAME;
	}
}
