package modi.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JCheckBox;

class JCreateAndPaint extends JPanel {
	private JCheckBox c_directed;
	private JCreate parent;
	
	JCreateAndPaint(JCreate parent) {
		this.parent = parent;

		
		c_directed = new JCheckBox("Directed");
		c_directed.setSelected(true);
		add(c_directed);
		
		
		c_directed.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateEDO();
			}
		});
		
	}
	
	private void updateEDO(){
		parent.updateEDO();
	}

	boolean isDirected() {
		return c_directed.isSelected();
	}
}
