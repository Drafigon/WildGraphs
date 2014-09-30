package modi.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modi.LayoutModus;
import ui.JOperationButton;


public class JRectLayout extends JPanel {

	private JOperationButton go;
	
	public JRectLayout(final LayoutModus modus) {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		String text = "Drag a rectangle with the mouse.";
		JLabel info = new JLabel(text);
				
		go = new JOperationButton("Go!");
		go.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				modus.runLayout();
			}
		});
		
		this.add(info);
		this.add(Box.createHorizontalStrut(15));
		this.add(go);
	}
}
