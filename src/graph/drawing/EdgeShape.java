package graph.drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * Shape to draw an edge
 * between two (three) points.
 * Subclasses begin with "ES_".
 * 
 * @author J. Wilde
 * @version 1.0
 * @since 02.04.2014
 */
public interface EdgeShape {
	/**
	 * Draws a curve between start and
	 * end location using the given color and stroke.
	 * 
	 * @param g2 : Graphics2D
	 * @param start 
	 * @param end
	 * @param curve : point on curve
	 * @param color
	 * @param stroke
	 */
	public void drawCurve(Graphics2D g2, Point start, Point end, Point curve, Color color, BasicStroke stroke);
	
	/**
	 * Draw an arrow an the location with given angle.
	 * 
	 * @param g2 : Graphics2D
	 * @param arrowsize : width and height
	 * @param location
	 * @param angle : how the curve hits the vertex
	 * @param color
	 */
	public void drawArrow(Graphics2D g2, int arrowsize, Point location, double angle, Color color);
	
	
	/**
	 * Draw the mouse over shape to the given position.
	 * 
	 * @param g2
	 * @param location
	 */
	public void drawMouseOver(Graphics2D g2, Point location);
}
