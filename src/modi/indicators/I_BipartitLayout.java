package modi.indicators;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class I_BipartitLayout implements Indicator {

	private Point start = new Point(0,0);
	private Point end = new Point(0,0);

	@Override
	public void draw(Graphics2D g2) {		
		g2.setColor(Color.red);
		g2.setStroke(new BasicStroke(2));
		g2.drawLine(start.x, start.y, end.x, start.y);
		g2.drawLine(start.x, end.y, end.x, end.y);
	}

	public void setStart(Point start) {
		this.start = start;
	}
	
	public void setEnd(Point end) {
		this.end = end;
	}
}
