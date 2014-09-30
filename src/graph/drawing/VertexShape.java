package graph.drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
/**
 * Shape to draw a vertex
 * on given position.
 * It also can calculate a point an the shape.
 * Subclasses begin with "VS_".
 * 
 * @author J. Wilde
 * @version 1.0
 * @since 02.04.2014
 */
public interface VertexShape {
	
	/**
	 * Draw a vertex form.
	 * 
	 * @param g2
	 * @param x
	 * @param y
	 * @param size
	 * @param incolor
	 * @param outcolor
	 * @param stroke
	 */
	public void drawStandard(Graphics2D g2, int x, int y, int size, Color incolor, Color outcolor, BasicStroke stroke);
	
	/**
	 * Draw the mouse over highlight.
	 * 
	 * @param g2
	 * @param x
	 * @param y
	 * @param size
	 */
	public void drawMouseOver(Graphics2D g2, int x, int y, int size);
	
	/**
	 * Draw the pin, to show the vertex is fix.
	 * 
	 * @param g2
	 * @param x
	 * @param y
	 * @param size
	 * @param outcolor
	 */
	public void drawPin(Graphics2D g2, int x, int y, int size, Color outcolor);
	
	/**
	 * Return a point lying on the border of the shape.
	 * 
	 * @param angle : from center, x-axis, clockwise
	 * @param size
	 * @return Point
	 */
	public Point calculatePointOnShape(double angle, int size);
}
