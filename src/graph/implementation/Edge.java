package graph.implementation;

import graph.drawing.EDO_Normal;
import graph.drawing.EdgeDrawOption;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;



public class Edge implements Drawable {
	// Definition
	private final Vertex source;
	private final Vertex destination;
	private final boolean directed;
	private int weight;
	
	// Draw attributes
	private String name;	
	private EdgeDrawOption drawOption;
	private Point curvePoint;
	private boolean mouseOver;
	
	//*******************
	//	 CONSTRUCTORS
	//*******************
	
	/**<pre>
	 * Create a new edge with:
	 * <b>Weight:</b> 1
	 * <b>EdgeDrawOption:</b> new EDO_Normal()
	 * </pre>
	 * @param source
	 * @param destination
	 * @param directed
	 */
	public Edge(Vertex source, Vertex destination, boolean directed) {	
		this.directed = directed;
		this.source = source;
		this.destination = destination;
		init(1, new EDO_Normal());
	}
	
	/**<pre>
	 * Create a new edge with:
	 * <b>Weight:</b> 1
	 * </pre>
	 * @param source
	 * @param destination
	 * @param directed
	 * @param edo
	 */
	public Edge(Vertex source, Vertex destination, boolean directed, EdgeDrawOption edo) {
		this.directed = directed;
		this.source = source;
		this.destination = destination;
		init(1, edo);
	}
	
	private void init(int weight, EdgeDrawOption edo) {
		this.weight = weight;
		this.drawOption = edo;
		this.mouseOver = false;
		this.name = "";
		
		// Set curve point to the middle of start/end => straight line	
		Point start = source.getLocation();
		Point end = destination.getLocation();
		int deltaX = end.x - start.x;
		int deltaY = end.y - start.y;	
		curvePoint = new Point(start.x + deltaX/2, start.y + deltaY/2);
		
	}
	
	//*******************
	//	 DRAW FUNCTION
	//*******************
	
	@Override
	public void draw(Graphics2D g2) {
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// draw line/curve
		drawOption.drawLine(g2, source, destination, curvePoint, directed);
		
		// draw text
		if(drawOption.isShowText()) {
			// if name == "", use the weight to show
			String text = "";
			if(name.equals("")) {
				text = Integer.toString(weight);
			}
			else {
				text = name;
			}
			
			// get values for the text
			Font standard = g2.getFont();
			g2.setFont(drawOption.getFont());
			int textheight = g2.getFont().getSize();	
			textheight -= g2.getFontMetrics().getDescent();
			int textwidth = g2.getFontMetrics().stringWidth(text);
			int textX = curvePoint.x - (int)(textwidth / 2f);
			int textY = curvePoint.y + (int) (textheight / 2f);
			
			// F get values for the circle
			//int max = Math.max(textwidth, textheight);
			int max = textwidth;
			int border = 20;
			int diameter = max + border;
			int circleX = curvePoint.x - diameter/2;
			int circleY = curvePoint.y - diameter/2;
			
			// draw
			g2.setColor(g2.getBackground());
			g2.fillOval(circleX, circleY, diameter, diameter);
			g2.setColor(drawOption.getColor());
			g2.drawString(text, textX, textY);
			
			g2.setFont(standard);
		}
		
		// MouseOver
		if(mouseOver) {
			drawOption.drawMouseOver(g2, curvePoint);
		}
	}

	//*******************
	//		SETTER
	//*******************

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public void setDrawOption(EdgeDrawOption drawOption) {
		this.drawOption = null;
		this.drawOption = drawOption;
	}
	
	public void setCurvePoint(Point curve) {
		this.curvePoint = curve;
	}	

	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	//*******************
	//		GETTER
	//*******************
	
	
	
	public Vertex getSource() {
		return source;
	}
	
	public String getName() {
		return name;
	}

	public Vertex getDestination() {
		return destination;
	}
	
	public int getWeight() {
		return weight;
	}

	public EdgeDrawOption getDrawOption() {
		return drawOption;
	}

	public Point getCurvePoint() {
		return curvePoint;
	}

	public boolean isDirected() {
		return directed;
	}

	@Override
	public String toString() {
		String text = "Edge: " + name + "\n";
		text += "- " + source.getName() + ", " + destination.getName() + "\n";
		text += "- directed: " + directed + "\n";
		text += "- curve point: " + curvePoint.x + ", " + curvePoint.y + "\n";
		text += "- draw option: " + drawOption;
		return text;
	}		
}
