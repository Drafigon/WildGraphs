package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;

import data.StorageSlides;

public class JAnimation extends JPanel {

	private JAnimationOption jOption;
	private JAnimationSlides jSlides;
	
	public JAnimation(StorageSlides slides) {
		this.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		this.setLayout(new BorderLayout(0, 0));
		
		jOption = new JAnimationOption();
		this.add(jOption, BorderLayout.NORTH);
		
		jSlides = new JAnimationSlides();
		JScrollPane scroll = new JScrollPane(jSlides);
		scroll.setAutoscrolls(true);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		this.add(scroll, BorderLayout.CENTER);
		
		setSlides(null);
	}

	void setSlides(StorageSlides s) {
		jSlides.setSlides(s);
		enableComponents(jOption, s != null);
	}
	
	private void enableComponents(Container container, boolean enable) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            component.setEnabled(enable);
            if (component instanceof Container) {
                enableComponents((Container)component, enable);
            }
        }
    }
	
	void updateImages() {
		jSlides.updateBackgroundImage();
		jSlides.updateAllGraphImages();
	}
	
	void updateColor() {
		jSlides.updateBackgroundColor();
	}
}
