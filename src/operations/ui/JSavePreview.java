package operations.ui;

import graph.implementation.Graph;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import data.StorageSlides;
import ui.SlideShape;
import utility.GraphFunctions;

public class JSavePreview extends JPanel {

	private JPanel preview;
	private JButton buttonPrevious;
	private JLabel labelCurrentGraph;
	private JButton buttonNext;
	private StorageSlides slides;
	private Color background;

	JSavePreview() {		
		this.setBorder(new EmptyBorder(20, 40, 20, 40));
		
		this.setLayout(new BorderLayout(0, 0));
		
		JLabel lblVorschau = new JLabel("Preview");
		lblVorschau.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(lblVorschau, BorderLayout.NORTH);
		
		JPanel panel_11 = new JPanel();
		this.add(panel_11, BorderLayout.SOUTH);
		
		buttonPrevious = new JButton("<");
		panel_11.add(buttonPrevious);
		
		labelCurrentGraph = new JLabel("x / y");
		labelCurrentGraph.setHorizontalAlignment(SwingConstants.CENTER);
		labelCurrentGraph.setPreferredSize(new Dimension(60, 14));
		panel_11.add(labelCurrentGraph);
		
		buttonNext = new JButton(">");
		panel_11.add(buttonNext);
		
		PositionListener listener = new PositionListener();
		buttonPrevious.addActionListener(listener);
		buttonNext.addActionListener(listener);
		
		
		preview = new JGraphImage();
		preview.setBorder(new LineBorder(new Color(0, 0, 0)));
		preview.setBackground(Color.WHITE);
		this.add(preview, BorderLayout.CENTER);	
	}

	
	void setToFirstGraph() {
		slides.selectFirst();
		this.repaint();
	}
	
	void setSlides(StorageSlides slides) {
		this.slides = slides;
		updateGraphCount();
		this.repaint();
	}
	
	void setBackgroundColor(Color background) {
		this.background = background;
	}
	
	private void updateGraphCount() {
		String text = (slides.getSelectedIndex()+1) + " / " + slides.getObjectCount();; 
		labelCurrentGraph.setText(text);
	}
	
	private final class PositionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			
			if(button == buttonPrevious) {
				slides.selectPrevious();
			}
			
			if(button == buttonNext) {
				slides.selectNext();
			}
			
			updateGraphCount();
			JSavePreview.this.repaint();
		}		
	}
	
	private final class JGraphImage extends JPanel {
		private SlideShape shape;
		private int width = 250;
		private int height = 250; 
		
		public JGraphImage() {
			shape = new SlideShape(width,height,0,Color.white);
		}

		@Override
		protected void paintComponent(Graphics g) {	
			super.paintComponent(g);
			
			Graphics2D g2 = (Graphics2D) g;
			Graph graph = slides.getSelectedGraph();
			Image image = GraphFunctions.getSizedGraphImage(graph, width, height, background);
			
			g2.setBackground(Color.black);
			g2.clearRect(0, 0, width, height);
			g2.drawImage(image, 0, 0, null);
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(width, height);
		}	
	}
}
