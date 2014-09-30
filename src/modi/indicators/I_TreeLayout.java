package modi.indicators;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class I_TreeLayout implements Indicator {

	private Point start = new Point(0,0);
	private Point end = new Point(0,0);
	private int height = 0;
	private int width = 0;
	private final int COUNT_W = 3;
	private final int COUNT_H = 3;
	private final int CIRCLE_SIZE = 10;

	@Override
	public void draw(Graphics2D g2) {	
		
		g2.setColor(Color.red);
	
		// Main
		g2.setStroke(new BasicStroke(2));
		drawCross(g2, end.x, end.y, 20);
		g2.drawLine(start.x, start.y, start.x, end.y);
		g2.drawLine(start.x, end.y, end.x, end.y);
		fillCircle(g2, start.x, start.y, CIRCLE_SIZE);
		
		// Grid
		g2.setStroke(new BasicStroke(1));
		int x = width * COUNT_W;
		for(int i = 0; i < COUNT_W+1; i++){
			int step = (i+1) * height;
			int y = start.y + step;
			
			g2.drawLine(start.x - x, y, start.x + x, y);
		}
		
		int posX = start.x - width * COUNT_W;
		int posY = start.y + height;
		int maxHeight = height * COUNT_H;
		for(int i = 0; i < COUNT_W*2+1; i++) {
			g2.drawLine(posX, posY, posX, posY + maxHeight);
			fillCircle(g2, posX, posY + maxHeight, CIRCLE_SIZE);
			posX += width;
		}
	}

	private void drawCross(Graphics2D g2, int x, int y, int size) {
		Point topLeft = new Point(x - size/2, y - size/2);
		Point rightBottom = new Point(x + size/2, y + size/2);
		g2.drawLine(topLeft.x, topLeft.y, rightBottom.x, rightBottom.y);
		g2.drawLine(rightBottom.x, topLeft.y, topLeft.x, rightBottom.y);
	}
	
	private void fillCircle(Graphics2D g2, int x, int y, int size) {
		Point topLeft = new Point(x - size/2, y - size/2);
		g2.fillOval(topLeft.x, topLeft.y, size, size);
	}
	
	public void setStart(Point start) {
		this.start = start;
		update();
	}
	
	public void setEnd(Point end) {
		this.end = end;
		update();
	}
	
	private void update() {
		height = Math.abs(end.y - start.y);
		width = Math.abs(end.x - start.x);
	}
}
