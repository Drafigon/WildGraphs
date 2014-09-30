package ui;

import graph.implementation.Graph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import data.StorageSlides;
import modi.indicators.I_Nothing;
import modi.indicators.Indicator;

public class JDrawer extends JPanel {

	private StorageSlides slides;
	private Indicator grid;
	private Indicator modus;
	private Indicator contextmenu;

	public JDrawer(StorageSlides slides) {		
		grid = new I_Nothing();
		modus = new I_Nothing();
		contextmenu = null;
		setSlides(null);
		
		this.setLayout(null);
		this.setBorder(BorderFactory.createLineBorder(Color.gray));
	}
	
	public void setSlides(StorageSlides s) {
		slides = s;
		this.setVisible(s != null);
		this.setEnabled(s != null);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		super.paintComponent(g2);
		
		if(slides != null && slides.getSelectedGraph() != null) {	
			// Anti-Aliasing
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			// Clear all
			g2.setBackground(slides.getBackgroundColor());
			g2.clearRect(0, 0, this.getWidth(),this.getHeight());
			
			// Show Modi
			Font standard = g2.getFont();
			g2.setColor(new Color(150,150,150,80));
			g2.setFont(new Font("Helvetica", Font.BOLD, 30));
			g2.setFont(standard);
					
			// Draw Graph
			grid.draw(g2);
			Graph slideGraph = slides.getSelectedGraph();
			if(slideGraph != null) {
				slideGraph.draw(g2);
			}
			
			modus.draw(g2);
			
			if(contextmenu != null) {
				contextmenu.draw(g2);
			}
			
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(600, 500);
	}	

	public void setModusIndicator(Indicator indicator){
		this.modus = indicator;
		repaint();
	}
		
	public void setGridIndicator(Indicator grid) {
		this.grid = grid;
	}

	public void setContextmenu(Indicator contextmenu) {
		this.contextmenu = contextmenu;
		repaint();
	}
}
