package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalToggleButtonUI;

import data.StorageSlides;

public class JGraphButton extends JToggleButton {
	
	public final StorageSlides SLIDES = new StorageSlides(20);

	private static final Color COLOR_OTHER = Color.lightGray;
	private static final Color COLOR_SELECTED = Color.white;
	private static final SelectionUI ui  = new SelectionUI();
	
	public JGraphButton() {
		this.setBackground(new Color(0,0,0,0));
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.setFocusPainted(false);
		this.setUI(ui);
		this.setBorder(new EmptyBorder(3, 5, 3, 5));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		
		Color background = COLOR_OTHER;		
		g2.setBackground(background);
		g2.clearRect(0, 0, getWidth(), getHeight());
		g2.setColor(Color.black);
		
		int height = getHeight() - 1;
		if(isSelected()) height += 5;
		
		
		super.paintComponent(g);
		g2.drawRect(0, 0, getWidth()-1, height);
	}
	
	private static class SelectionUI extends MetalToggleButtonUI {
		@Override
	    protected Color getSelectColor() {
	        return COLOR_SELECTED;
	    }
	}
}

