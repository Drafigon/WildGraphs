package modi.ui;

import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.BoxLayout;

public class JMove extends JPanel {
	
	
	private JCheckBox c_relax;
	private JCheckBox c_allSlides;

	public JMove() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	
		
		c_relax = new JCheckBox("Relax edges by dragging vertex");
		c_relax.setSelected(true);
		add(c_relax);
		
		c_allSlides = new JCheckBox("Move all slides");
		c_allSlides.setSelected(false);
		add(c_allSlides);
	}

	public boolean isRelax(){
		return c_relax.isSelected();
	}
	
	public boolean isMoveAllSlides() {
		return c_allSlides.isSelected();
	}
}
