package modi.indicators;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class I_ForceLayout implements Indicator {

	private List<Point> points = new ArrayList<Point>();
	private int radius = 0;
	private boolean visible = true;
	
	@Override
	public void draw(Graphics2D g2) {
		if(visible) {	
			g2.setStroke(new BasicStroke(2));
			g2.setColor(Color.red);
			
			for(Point p : points) {		
				int x = p.x - radius;
				int y = p.y - radius;		
				g2.drawOval(x, y, radius*2, radius*2);
			}
		}
	}

	public void setRadius(int r) {
		this.radius = r;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
