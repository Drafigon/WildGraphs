package modi.ui;

import graph.drawing.EDO_Normal;
import graph.drawing.EdgeDrawOption;
import graph.drawing.VDO_StandardBlank;
import graph.drawing.VertexDrawOption;
import graph.implementation.Edge;
import graph.implementation.Graph;
import graph.implementation.Vertex;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import data.Slides;
import ui.SlideShape;
import utility.GraphFunctions;

public class JEDOSlides extends JPanel {

	private JCreate parent;
	private Graph graphDirected = new Graph();
	private Graph graphUndirected = new Graph();
	private Edge edgeDirected;
	private Edge edgeUndirected;
	private Slides<EdgeDrawOption> edos = new Slides<EdgeDrawOption>();
	
	private final int SLIDE_W = 106;
	private final int SLIDE_H = 106;
	private final int SLIDE_BORDER = 3;
	private final Color SELECTION_COLOR = Color.cyan;
	private final Color DRAGGING_COLOR = Color.yellow;
	private final int VERTEX_SIZE = 10;
	
	private BufferedImage backgroundImage;
	private ArrayList<Image> graphImages;
	private SlideShape shape;
	private Color backgroundColor = Color.white;
	
	private int selectedIndex = -1;
	private int draggedIndex;
	private SelectionListener listener = new SelectionListener();
	
	public JEDOSlides(JCreate p) {
		this.parent = p;
		
		VertexDrawOption vdo = new VDO_StandardBlank();
		vdo.setSize(VERTEX_SIZE);
		vdo.setIncolor(Color.black);
		
		Vertex v1 = new Vertex("", new Point(VERTEX_SIZE*2, SLIDE_H - VERTEX_SIZE*2), vdo);
		Vertex v2 = new Vertex("", new Point(SLIDE_W - VERTEX_SIZE*2, VERTEX_SIZE*2), vdo);
		edgeDirected = new Edge(v1, v2, true);
		graphDirected.addEdge(edgeDirected);
		
		edgeUndirected = new Edge(v1, v2, false);
		graphUndirected.addEdge(edgeUndirected);
		
	
		// create shape
		shape = new SlideShape(SLIDE_W, SLIDE_H, SLIDE_BORDER, backgroundColor);
		
		// init with new graph
		graphImages = new ArrayList<Image>();
		Image image = getGraphImage(new Graph());
		graphImages.add(image);
		
		
		updateBackgroundImage();
		
		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);
		
		addAfterSelected(new EDO_Normal());
		edos.selectFirst();
	}

	
	
	void addAfterSelected(EdgeDrawOption edo) {
		edos.addAfterSelectedAndUpdateIndex(edo);
		updateBackgroundImage();
		revalidate();
		repaint();
	}
	
	void duplicateSelected() {
		EdgeDrawOption copy = new EdgeDrawOption(edos.getSelectedObject());
		addAfterSelected(copy);
	}
	
	void removeSelected() {
		edos.removeSelectedAndUpdateIndex();
		updateBackgroundImage();
		revalidate();
		repaint();
	}
	
	EdgeDrawOption getSelectedEDO() {
		if(edos.isEmpty()) return null;
		return edos.getSelectedObject();
	}
	
	@Override
	public Dimension getPreferredSize() {
		int size = 0 + edos.getObjectCount();
		return new Dimension(SLIDE_W, SLIDE_H * size);
	}


	private void updateBackgroundImage() {
		if(edos.isEmpty()) {
			backgroundImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		}
		else {
			// look of one slide
			BufferedImage oneSlide = shape.getSlideImage();
			
			int size = edos.getObjectCount();
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
	
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graph graph = graphUndirected;
		if(parent.isDirected()) 
			graph = graphDirected;
		
		
		Graphics2D g2 = (Graphics2D) g;		
		
		// set backgroundcolor
		Color color = backgroundColor;
		g2.setBackground(color);
		
		// draw background (slides)
		g2.drawImage(backgroundImage, 0, 0, null);
				
		for(EdgeDrawOption edo : edos.getAllObjects()) {
			edgeDirected.setDrawOption(edo);
			edgeUndirected.setDrawOption(edo);
			graph.draw(g2);
			GraphFunctions.moveGraphBy(graph, new Point(0, SLIDE_H));
		}
	
		GraphFunctions.moveGraphBy(graph, new Point(0, - edos.getObjectCount() * SLIDE_H));
		
		g2.setStroke(new BasicStroke(4));
		g2.setColor(SELECTION_COLOR);
		g2.drawRect(1, SLIDE_H * edos.getSelectedIndex(), SLIDE_W-1, SLIDE_H-1);
		
		if(listener.isDragging) {
			g2.setColor(DRAGGING_COLOR);
			g2.drawRect(1, SLIDE_H * selectedIndex, SLIDE_W-1, SLIDE_H-1);
			int lineheight = draggedIndex * SLIDE_H;
			g2.drawLine(0, lineheight, SLIDE_W, lineheight);
		}
	}
	
	private Image getGraphImage(Graph g) {
		int width = SLIDE_W - SLIDE_BORDER * 2;
		int height = SLIDE_H - SLIDE_BORDER * 2;
		return GraphFunctions.getSizedGraphImage(g, width, height, backgroundColor);
	}

	private class SelectionListener extends MouseAdapter {
		private boolean isDragging = false;		
		
		@Override
		public void mousePressed(MouseEvent event) {
			int y = event.getY();
					
			// select other graph
			int index = y / SLIDE_H;
			int size = edos.getObjectCount();
			if(index > size - 1) {
				edos.selectLast();
			}
			else {
				edos.selectIndex(index);
			}
			
			parent.updateSelection();
			repaint();
		}

		@Override
		public void mouseDragged(MouseEvent m) {
			selectedIndex = edos.getSelectedIndex();
			isDragging = true;			
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
				
				edos.moveSelectedTo(draggedIndex);
				
				repaint();
				isDragging = false;
			}
		}				
	}
}
