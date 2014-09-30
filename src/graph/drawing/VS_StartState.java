package graph.drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
/**
 * Draw a vertex form like a start state of 
 * a finite state machine.
 * 
 * @author J. Wilde
 * @version 1.0
 * @since 02.04.2014
 */
public class VS_StartState implements VertexShape {

	private ES_Normal line_shape;
	
	public VS_StartState() {
		super();
		this.line_shape = new ES_Normal();
	}


	@Override
	public void drawStandard(Graphics2D g2, int x, int y, int size, Color incolor,
			Color outcolor, BasicStroke stroke) {	
		// values
		int width =  (int) (size - stroke.getLineWidth());
		int posX = (int) (x - width * 0.5);
		int posY = (int) (y - width * 0.5);
		
		// line + arrow		
		int length = 20;
		g2.setStroke(stroke);
		g2.setColor(outcolor);
		g2.drawLine(posX - length, y, posX, y);
		line_shape.drawArrow(g2, 20, new Point(posX, y), Math.PI, outcolor);
		
		// circle		
		g2.setColor(incolor);
		g2.fillOval(posX, posY, width, width);
				
		g2.setColor(outcolor);	
		g2.drawOval(posX, posY, width, width);
	}


	@Override
	public void drawMouseOver(Graphics2D g2, int x, int y, int size) {
		int linewidth = 2;		
		g2.setColor(Color.red);
		g2.setStroke(new BasicStroke(linewidth));
		size -= linewidth;
		int radius = (int) (size * 0.5);
		g2.drawOval(x - radius, y - radius, size, size);		
	}

	@Override
	public void drawPin(Graphics2D g2, int x, int y, int size, Color outcolor) {
		int radius = (int) (size / 4.0 - 4);
		Point lineStart = new Point(x, y - radius);
		Point lineEnd = new Point((int) (x + 3.0/4.0 * radius), (int) (y - 3.0/4.0 * radius - radius));
		Point headPoint = new Point((int) (x + radius/2.0), (int) (y - 2 * radius));
		int headsize = (int) (radius / 2.0);
		
		g2.setColor(outcolor);
		g2.drawLine(lineStart.x, lineStart.y, lineEnd.x, lineEnd.y);
		g2.setColor(Color.white);
		g2.fillOval(headPoint.x, headPoint.y, headsize, headsize);
		g2.setColor(outcolor);
		g2.drawOval(headPoint.x, headPoint.y, headsize, headsize);		
	}
	
	
	@Override
	public Point calculatePointOnShape(double degree, int size) {
		Point p = new Point();
		p.x = (int) (Math.cos(degree) * (size / 2.0));
		p.y = (int) (Math.sin(degree) * (size / 2.0));
		return p;
	}


	@Override
	public String toString() {
		return "Start State";
	}
}
