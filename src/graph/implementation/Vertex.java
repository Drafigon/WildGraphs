package graph.implementation;

import graph.drawing.VDO_StandardText;
import graph.drawing.VertexDrawOption;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.HashMap;
import java.util.Map;


public class Vertex implements Drawable {
	private static int count = 1;
	public final int id = count;
	
	// Definition
	private Map<Edge, Vertex> predecessors;
	private Map<Edge, Vertex> successors;
	private int weight;
	private String name;
	
	// Draw attributes
	private Point location;
	private VertexDrawOption drawOption;
	private boolean isFixed;
	private boolean mouseover;
	
	//*******************
	//	 CONSTRUCTORS
	//*******************
	
	/**
	 * Creates a new vertex with the
	 * same attributes as the given one.
	 * 
	 * @param vertex
	 */
	public Vertex(Vertex vertex) {
		init(vertex.name, vertex.location, vertex.drawOption, vertex.weight, vertex.isFixed);
	}
	
	/**<pre>
	 * Creates a new vertex with standard format.
	 * <b>Location:</b> new Point(0,0)
	 * <b>VertexDrawOption:</b> new VDO_StandardText()
	 * <b>Weight:</b> 1
	 * <b>IsFixed:</b> false
	 * </pre>
	 * 
	 * @param name
	 */
	public Vertex(String name) {
		init(name, new Point(0,0), new VDO_StandardText(), 1, false);
	}
	
	/**<pre>
	 * Creates a new vertex with the given attributes and:
	 * <b>Weight:</b> 1
	 * <b>IsFixed:</b> false
	 * </pre>
	 * @param name
	 * @param location
	 * @param vdo
	 */
	public Vertex(String name, Point location, VertexDrawOption vdo) {
		init(name, location, vdo, 1, false);
	}
	
	private void init(String name, Point location, VertexDrawOption vdo, int w, boolean isFixed) {
		count++;
		this.setName(name);
		this.weight = w;
		this.isFixed = isFixed;
		this.drawOption = vdo;
		predecessors = new HashMap<Edge, Vertex>();
		successors = new HashMap<Edge, Vertex>();
		this.setLocation(location);	
		this.mouseover = false;	
	}
	
	//*******************
	//	 DRAW FUNCTION
	//*******************
	@Override
	public void draw(Graphics2D g2) {
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// Draw shape		
		drawOption.drawShape(g2, location.x, location.y);
			
		// Draw name
		if(drawOption.isShowText()) {	
			Font standard = g2.getFont();
			g2.setFont(drawOption.getFont());
			
			// get values
			float textheight = g2.getFont().getSize();
			textheight -= g2.getFontMetrics().getDescent();	
			float textwidth = g2.getFontMetrics().stringWidth(name);
			
			
			// Location in center
			float x = location.x - textwidth / 2f;
			float y = location.y + textheight / 2f;
			
			// draw
			g2.setColor(drawOption.getOutcolor());
			g2.drawString(name, x, y);
			
			g2.setFont(standard);
		}
		
		// Pin
		if(isFixed) {
			drawOption.drawPin(g2, location.x, location.y);
		}
		
		// MouseOver
		if(mouseover) {
			drawOption.drawMouseOver(g2, location.x, location.y);
		}
	}
	
	//*******************
	//		ADDER
	//*******************

	public void addNeighbour(Edge edge, Vertex vertex) {
		addPredecessor(edge, vertex);
		addSuccessor(edge, vertex);
	}
	
	public void addPredecessor(Edge edge, Vertex vertex) {
		predecessors.put(edge, vertex);
	}

	public void addSuccessor(Edge edge, Vertex vertex) {
		successors.put(edge, vertex);
	}

	//*******************
	//		REMOVER
	//*******************
	
	public void removeNeighbour(Edge edge) {
		removePredecessor(edge);
		removeSuccessor(edge);
	}
	
	public void removePredecessor(Edge edge) {
		predecessors.remove(edge);
	}

	public void removeSuccessor(Edge edge) {
		successors.remove(edge);
	}	
	
	//*******************
	//		SETTER
	//*******************

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public void setName(String name) {
		this.name = name;		
	}
	
	public void setLocation(Point location) {
		this.location = location;
	}

	public void setFixed(boolean isFixed) {
		this.isFixed = isFixed;
	}
		
	public void setDrawOption(VertexDrawOption drawOption) {
		this.drawOption = drawOption;
	}
	
	public void setMouseover(boolean mouseover) {
		this.mouseover = mouseover;
	}
	
	//*******************
	//		GETTER
	//*******************
	
	public Map<Edge, Vertex> getPredecessors() {
		Map<Edge, Vertex> copy = new HashMap<Edge, Vertex>();
		copy.putAll(predecessors);
		return copy;
	}

	public Map<Edge, Vertex> getSuccessors() {
		Map<Edge, Vertex> copy = new HashMap<Edge, Vertex>();
		copy.putAll(successors);
		return copy;
	}

	public Map<Edge, Vertex> getNeighbours() {
		Map<Edge, Vertex> neighbours = new HashMap<Edge, Vertex>();
		neighbours.putAll(predecessors);
		neighbours.putAll(successors);
		return neighbours;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public String getName() {
		return name;
	}

	public Point getLocation() {
		return new Point(location);
	}	
	
	public VertexDrawOption getDrawOption() {
		return drawOption;
	}
	
	public boolean isMouseover() {
		return mouseover;
	}
	
	public boolean isFixed() {
		return isFixed;
	}

	@Override
	public String toString() {
		String text = "Vertex: " + name + " (" + id + ")\n";
		text += "- position: " + location.x + ", " + location.y +"\n";
		text += "- fix: " + isFixed + "\n";
		text += "- draw option: " + drawOption + "\n";
		text += "- successors: ";
		
		for(Vertex v: successors.values()) {
			text += v.name + ", ";
		}
		text += "\n";
		
		text += "- predecessors: ";
		for(Vertex v: predecessors.values()) {		
			text += v.name + ", ";
		}
		text += "\n";
		
		return text;
	}	
	
}
