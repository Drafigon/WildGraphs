package modi.indicators;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class I_CreateEdge implements Indicator {

	private Point start;
	private Point end;
	private boolean visible;
	
	public I_CreateEdge() {
		start = new Point(0,0);
		end = new Point(0,0);
		visible = false;
	}
	
	@Override
	public void draw(Graphics2D g2) {
		if(visible) {
			g2.setStroke(new BasicStroke(2));
			g2.setColor(Color.red);
			g2.drawLine(start.x, start.y, end.x, end.y);
		}
	}

	public void setStart(Point start) {
		this.start = start;
	}

	public void setEnd(Point end) {
		this.end = end;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
}
