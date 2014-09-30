package modi.indicators;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class I_Grid implements Indicator {

	private Point nextGridPoint;
	private int gridsize;
	private boolean isVisible;
	
	
	public I_Grid() {
		super();
		this.nextGridPoint = new Point(0,0);
		this.gridsize = 30;
		this.isVisible = false;
	}


	public void setNextGridPoint(Point nextGridPoint) {
		this.nextGridPoint = nextGridPoint;
	}

	public void setGridsize(int gridsize) {
		this.gridsize = gridsize;
	}

	@Override
	public void draw(Graphics2D g2) {
		if(isVisible) {
			Rectangle rect = g2.getClipBounds();
			
			// Draw Grid
			int countX = rect.width / gridsize + 1;
			int countY = rect.height / gridsize + 1;
			
			g2.setStroke(new BasicStroke(1));
			g2.setColor(new Color(150,150,150, 100));
			
			for(int i = 1; i < countX; i++){
				g2.drawLine(i * gridsize, 0, i * gridsize, rect.height);
			}
			
			for(int i = 1; i < countY; i++) {
				g2.drawLine(0, i * gridsize, rect.width, i * gridsize);
			}
			
			g2.setColor(new Color(100,100,100));
			drawCross(g2, nextGridPoint.x, nextGridPoint.y);
		}
		
	}
	
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	private void drawCross(Graphics2D g2,int x, int y) {
		g2.drawLine(x - 10, y, x + 10, y);
		g2.drawLine(x, y - 10, x, y + 10);
	}
	
	
}
