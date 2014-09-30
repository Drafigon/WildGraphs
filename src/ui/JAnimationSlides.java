package ui;

import graph.implementation.Graph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import data.StorageSlides;
import operations.O_SelectIndex;
import operations.O_SelectLastSlide;
import operations.Operation;
import utility.GraphFunctions;

public class JAnimationSlides extends JPanel {

	private final int SLIDE_W = 170;
	private final int SLIDE_H = 120;
	private final int SLIDE_BORDER = 10;
	private final Color SELECTION_COLOR = Color.cyan;
	private final Color DRAGGING_COLOR = Color.yellow;
	
	private StorageSlides slides;
	private BufferedImage backgroundImage;
	private ArrayList<Image> graphImages;
	private SlideShape shape;
	private Color backgroundColor = Color.white;
	
	private int selectedIndex = 0;
	private int draggedIndex;
	private SelectionListener listener = new SelectionListener();
	
	public JAnimationSlides() {
		
		setSlides(null);
		
		shape = new SlideShape(SLIDE_W, SLIDE_H, SLIDE_BORDER, backgroundColor);

		graphImages = new ArrayList<Image>();
		Image image = getGraphImage(new Graph());
		graphImages.add(image);
		
		// Listener
		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);
	}
	
	public void setSlides(StorageSlides s) {
		slides = s;
		this.setVisible(s != null);
		if(slides != null) {
			updateBackgroundColor();
			updateBackgroundImage();
			updateAllGraphImages();
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
		int size = 0;
		if(slides != null) { 
			size = slides.getObjectCount();
		}
		return new Dimension(SLIDE_W, SLIDE_H * size);
	}

	// the background-color changes only at a definite time (setBackgroundColor)
	// thats why this function isn't in updateBackgroundImage
	void updateBackgroundColor() {
		if(slides != null) {
			backgroundColor = slides.getBackgroundColor();
			shape.updateBackgroundImage(backgroundColor);
		}
	}

	void updateBackgroundImage() {
		if(slides != null) {
			
			if(slides.isEmpty()) {
				backgroundImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
			}
			else {
				// look of a slide
				BufferedImage oneSlide = shape.getSlideImage();
				
				int size = slides.getObjectCount();
				if(size == 0) size = 1;
				
				BufferedImage allSlides = new BufferedImage(SLIDE_W * size, SLIDE_H * size, oneSlide.getType());
				
				Graphics2D g2 = (Graphics2D) allSlides.getGraphics();		
				for(int i = 0; i <= size; i++) {
					g2.drawImage(oneSlide, 0, SLIDE_H * i, null);
				}
				
				g2.dispose();
				
				backgroundImage = allSlides;
			}
		}
	}

	void updateAllGraphImages() {
		if(slides != null) {
			// Clear
			graphImages = new ArrayList<Image>();
			
			// get all graphs
			List<Graph> graphs = slides.getAllGraphs();
			
			for(Graph g: graphs) {
				// create little version
				Image little = getGraphImage(g);
				graphImages.add(little);
			}
			
			revalidate();
		}
	}
	
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(slides != null) {		
			Graphics2D g2 = (Graphics2D) g;
			
			// set background-color
			Color color = backgroundColor;
			g2.setBackground(color);
			
			// draw background
			g2.drawImage(backgroundImage, 0, 0, null);
			
			// update current graph
			updateCurrentGraphImage();
			
			// draw all graphs
			int count = 0;
			for(Image image: graphImages) {
				int x = (int) ((SLIDE_W - image.getWidth(null)) * 0.5);
				int y = (int) ((SLIDE_H - image.getHeight(null)) * 0.5);
				
				g2.drawImage(image, x, count + y, null);
				count += SLIDE_H;
			}
			
			// draw selection frame
			g2.setStroke(new BasicStroke(4));
			g2.setColor(SELECTION_COLOR);
			g2.drawRect(1, SLIDE_H * slides.getSelectedIndex(), SLIDE_W-1, SLIDE_H-1);
			
			if(listener.isDragging) {
				g2.setColor(DRAGGING_COLOR);
				g2.drawRect(1, SLIDE_H * selectedIndex, SLIDE_W-1, SLIDE_H-1);
				int lineheight = draggedIndex * SLIDE_H;
				g2.drawLine(0, lineheight, SLIDE_W, lineheight);
			}
		}
	}
		
	private void updateCurrentGraphImage() {
		int currentIndex = slides.getSelectedIndex();
		if(currentIndex > -1) {
			Graph g = slides.getSelectedGraph();
			Image little = getGraphImage(g);
			graphImages.set(currentIndex, little);
		}
	}
	
	private Image getGraphImage(Graph g) {
		int width = SLIDE_W - SLIDE_BORDER * 2;
		int height = SLIDE_H - SLIDE_BORDER * 2;
		return GraphFunctions.getSizedGraphImage(g, width, height, backgroundColor);
	}
	
	private class SelectionListener extends MouseAdapter {
		private boolean isDragging = false;
		
		private Operation last = new O_SelectLastSlide();
		
		
		@Override
		public void mousePressed(MouseEvent event) {
			int y = event.getY();
					
			// select other graph
			int index = y / SLIDE_H;
			int size = slides.getObjectCount();
			if(index > size - 1) {
				last.run();
			}
			else {
				new O_SelectIndex(index).run();
			}			
		}


		@Override
		public void mouseDragged(MouseEvent m) {
			isDragging = true;
			selectedIndex = slides.getSelectedIndex();
			
			int y = m.getY();
			y += SLIDE_H / 2;
			int index = y / SLIDE_H;
			draggedIndex = index;
			
			repaint();
		}


		@Override
		public void mouseReleased(MouseEvent m) {
			if(isDragging) {
				
				// correction
				// During the exchange a slide will be first deleted 
				// and then inserted (move up)
				// the insert index is to hight by 1				
				if(draggedIndex > selectedIndex) {
					draggedIndex--;
				}
				
				
				slides.moveSelectedTo(draggedIndex);
				updateAllGraphImages();
				repaint();
				isDragging = false;
			}
		}		
	}
}
