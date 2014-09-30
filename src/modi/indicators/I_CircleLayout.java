package modi.indicators;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class I_CircleLayout implements Indicator {

	private Point center;
	private double radius;
	
	public I_CircleLayout(Point center, double radius) {
		this.center = center;
		this.radius = radius;
	}
		
	public void setCenter(Point center) {
		this.center = center;
	}	
	
	public void setRadius(double radius) {
		this.radius = radius;
	}


	@Override
	public void draw(Graphics2D g2) {		
		// circle calculation	
		int startX = (int) (center.x - radius);
		int startY = (int) (center.y - radius);
		int size = (int) (radius * 2);
		
		g2.setStroke(new BasicStroke(2));
		g2.setColor(Color.red);
		g2.drawOval(startX, startY, size, size);	
		g2.drawLine(center.x - 20, center.y, center.x + 20, center.y);
		g2.drawLine(center.x, center.y - 20, center.x, center.y + 20);
	}
}
