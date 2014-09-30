package graph.drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
/**
 * Draw a vertex form like an end state of 
 * a finite state machine.
 * 
 * @author J. Wilde
 * @version 1.0
 * @since 02.04.2014
 */
public class VS_EndState implements VertexShape {

	@Override
	public void drawStandard(Graphics2D g2, int x, int y, int size, Color incolor,
			Color outcolor, BasicStroke stroke) {
		
		int width =  (int) (size-stroke.getLineWidth());
		int posX = (int) (x - width/2.0);
		int posY = (int) (y - width/2.0);
		g2.setColor(incolor);
		g2.fillOval(posX, posY, width, width);
		g2.setColor(outcolor);
		g2.setStroke(stroke);
		
		int difference = (int)(width*0.10);
		g2.drawOval(posX,posY, width, width);
		g2.drawOval(posX + difference, posY + difference, width - difference*2, width - difference*2);
	}

	
	@Override
	public void drawMouseOver(Graphics2D g2, int x, int y, int size) {
		int linewidth = 2;
		g2.setColor(Color.red);
		g2.setStroke(new BasicStroke(linewidth));
		int width =  size - linewidth;
		int radius = (int) (width * 0.5);
		g2.drawOval(x - radius, y - radius, width, width);		
	}

	@Override
	public void drawPin(Graphics2D g2, int x, int y, int size, Color outcolor) {
		int radius = (int) (size / 2.0 - 4);
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
	public Point calculatePointOnShape(double angle, int size) {
		Point p = new Point();
		p.x = (int) (Math.cos(angle) * (size/2.0));
		p.y = (int) (Math.sin(angle) * (size/2.0));
		return p;
	}


	@Override
	public String toString() {
		return "End State";
	}
}
