package modi.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ui.JOperationButton;
import modi.M_CircleLayout;


public class JCircleLayout extends JPanel {

	private JOperationButton go;
	
	public JCircleLayout(final M_CircleLayout modus) {
		super();
		
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		String text = "Drag a circle with the mouse.";
		JLabel info = new JLabel(text);
		
		
		go = new JOperationButton("Go!");
		go.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {	
				modus.runLayout();
			}
		});
		
		this.add(info);
		this.add(Box.createHorizontalStrut(15));
		this.add(go);
	}
}
