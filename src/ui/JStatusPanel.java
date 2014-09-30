package ui;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.FlowLayout;

public class JStatusPanel extends JPanel implements StatusInfo {

	private JLabel info = new JLabel("");
	
	JStatusPanel() {
		super();
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		this.add(info);
	}
	
	@Override
	public void setStatus(String text) {
		info.setText(text);
	}
}
