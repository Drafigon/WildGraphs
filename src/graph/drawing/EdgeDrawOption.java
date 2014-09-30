package graph.drawing;

import graph.implementation.Vertex;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

/**<pre>
 * This class represents the way an edge should draw.
 * All needed information are hold here and can be changed.
 * Often this class is abbreviated as "EDO".
 * For standard options a subclass can be created.
 * Subclasses begin with "EDO_".
 * </pre>
 * @author J. Wilde
 * @version 1.0
 * @since 02.04.2014
 *
 */
public class EdgeDrawOption {
	// To count
	private static int count = 0;
	private final int NUMBER = count;
	
	// attributes
	private Color color;
	private BasicStroke stroke;
	private boolean showText;
	private EdgeShape standardShape;
	private int arrowsize;
	private Font font;
	
	//*******************
	//	 CONSTRUCTORS
	//*******************

	/**<pre>
	 * Creates a new EdgeDrawOption with default attributes:
	 * <b>Shape: </b> new ES_Normal()
	 * <b>Color: </b> Color.black
	 * <b>Stroke: </b> new BasicStroke(2)
	 * <b>Arrowsize: </b> 20
	 * <b>ShowText: </b> true
	 * </pre>
	 */
	public EdgeDrawOption() {
		Font standard = new Font(Font.DIALOG, Font.PLAIN, 18);
		initiate(new ES_Normal(), Color.black, new BasicStroke(2), 20, true, standard);		
	}
	
	
	/**
	 * Create a new EdgeDrawOption with the same attributes as the given one.
	 * 
	 * @param edo : EdgeDrawOption
	 */
	public EdgeDrawOption(EdgeDrawOption edo) {		
		initiate(edo.standardShape, edo.color, edo.stroke, edo.arrowsize, edo.showText, edo.getFont());
	}	

	private void initiate(EdgeShape standardShape, Color color, BasicStroke stroke, int arrowsize, boolean showText, Font font) {
		this.standardShape = standardShape;
		this.color = color;
		this.stroke = stroke;
		this.arrowsize = arrowsize;
		this.showText = showText;
		this.font = font;
		count++;
	}
	
	
	//*******************
	//	DRAW FUNCTIONS
	//*******************
	public void drawLine(Graphics2D g2, Vertex source, Vertex destination, Point curve, boolean directed) {	

		// get values
		Point start = source.getLocation();
		Point end = destination.getLocation();
		int size = destination.getDrawOption().getSize();
		
		// angle
		double angle = calculateAngle(end.x, end.y, curve.x, curve.y);
		
		// loop		
		if(source == destination) {			
			double distance = Point.distance(curve.x, curve.y, end.x, end.y);			
			int half = (int) (distance * 0.5);
			
			// calculate points for curves
			Point outsideA = destination.getDrawOption().calculatePointOnShape(angle + 0.3, size + half);
			Point outsideB = destination.getDrawOption().calculatePointOnShape(angle - 0.3, size + half);
			outsideA.x += end.x;
			outsideA.y += end.y;
			outsideB.x += end.x;
			outsideB.y += end.y;
			
			// calculate points on shape
			Point onShapeA = destination.getDrawOption().calculatePointOnShape(angle + 0.9, size);
			Point onShapeB = destination.getDrawOption().calculatePointOnShape(angle - 0.9, size);
			onShapeA.x += end.x;
			onShapeA.y += end.y;
			onShapeB.x += end.x;
			onShapeB.y += end.y;
			
			standardShape.drawCurve(g2, outsideA, outsideB, curve, color, stroke);
			standardShape.drawCurve(g2, end, outsideA, onShapeA, color, stroke);
			standardShape.drawCurve(g2, end, outsideB, onShapeB, color, stroke);

			// arrow
			if(directed) {
				
				// rotation
				double arrowangle = calculateAngle(onShapeB.x, onShapeB.y, outsideB.x, outsideB.y);
				
				// draw
				standardShape.drawArrow(g2, arrowsize, onShapeB, arrowangle, color);
			}			
		}
		// Curve
		else {
			standardShape.drawCurve(g2, start, end, curve, color, stroke);
			
			// arrow
			if(directed) {
				// calculate point
				Point arrowPoint = destination.getDrawOption().calculatePointOnShape(angle, (int) (size));
				arrowPoint.x += end.x;
				arrowPoint.y += end.y;

				standardShape.drawArrow(g2, arrowsize, arrowPoint, angle, color);
			}
		}	
	}
	
	public void drawMouseOver(Graphics2D g2, Point location) {
		standardShape.drawMouseOver(g2, location);
	}
	
	private double calculateAngle(int x1, int y1, int x2, int y2) {
		double result = 0;
		
		double deltaX = x2 - x1;
		double deltaY = y2 - y1;
		
		result = Math.atan(deltaY / deltaX);
		
		if(deltaX < 0) {
			result += Math.PI;
		}
		
		if(result < 0) {
			result += Math.PI*2;
		}
		
		return result;
	}
	
	
	//*******************
	//		HELPER
	//*******************
	
	/**
	 * Compare two EdgeDrawOption.
	 * Returns true, if the attributes are equal.
	 * This is used by export/import.
	 * 
	 * @see external.GraphSaver
	 * @see external.GraphLoader
	 * @param edo
	 * @return true/false
	 */
	public boolean hasSameOptionsAs(EdgeDrawOption edo) {
		boolean result = true;
		
		if(this.color.getRGB() != edo.color.getRGB()) result = false;
		if(this.stroke.getLineWidth() != edo.stroke.getLineWidth()) result = false;
		if(this.showText != edo.showText)  result = false;
		if(this.standardShape.getClass() != edo.standardShape.getClass()) result = false;
		
		return result;
	}

	//*******************
	//		SETTER
	//*******************
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setShowText(boolean showText) {
		this.showText = showText;
	}
	
	public void setShape(EdgeShape shape) {
		this.standardShape = shape;
	}
	
	public void setStroke(int size) {
		this.stroke = new BasicStroke(size);
	}
	
	public void setFont(Font font) {
		this.font = font;
	}
	
	public void setArrowsize(int arrowsize) {
		this.arrowsize = arrowsize;
	}

	//*******************
	//		GETTER
	//*******************
	
	public Color getColor() {
		return color;
	}

	public boolean isShowText() {
		return showText;
	}

	public EdgeShape getShape() {
		return standardShape;
	}

	public int getArrowsize() {
		return arrowsize;
	}

	public BasicStroke getStroke() {
		return stroke;
	}

	
	public Font getFont() {
		return font;
	}


	@Override
	public String toString() {
		String text = "";
		text += "EDO Number: " + NUMBER;
		return text;
	}
}
