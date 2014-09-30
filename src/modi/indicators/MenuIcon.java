package modi.indicators;

import external.ImageLoader;
import graph.implementation.Drawable;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class MenuIcon implements Drawable {

	private Point location = new Point(0,0);
	private boolean mouseOver = false;
	private BufferedImage image;
		
	public MenuIcon(String path) {
		loadImage(path);
	}
	
	private void loadImage(String path) {		
		image = ImageLoader.loadBufferedImage(path);
	}
	
	boolean isInside(Point mouse) {
		int left = location.x - image.getWidth()/2;
		int top = location.y - image.getHeight()/2;
		int right = location.x + image.getWidth()/2;
		int bottom = location.y + image.getHeight()/2;
		
		if(mouse.x > left && mouse.x < right && mouse.y > top && mouse.y < bottom) {
			return true;
		}
		
		return false;
	}
	
	boolean isMouseOver() {
		return mouseOver;
	}

	void setLocation(Point location) {
		this.location = location;
	}
	
	void moveBy(Point vector) {
		this.location.x += vector.x;
		this.location.y += vector.y;
	}

	void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	@Override
	public void draw(Graphics2D g2) {
		int size = image.getWidth();
		int x = location.x;
		int y = location.y;
		
		// if mouse over					
		if(mouseOver) {
			// Highlight on Background			
			int mouseOverSize = (int) Math.round(size * 1.5);
			g2.setColor(new Color(255,255,255,70));
	        g2.fillOval(x - mouseOverSize/2, y - mouseOverSize/2, mouseOverSize, mouseOverSize);
		}
		
		// Foreground
		g2.drawImage(image, x - size/2, y - size/2, null);
		
	}

	@Override
	public String toString() {
		return location.toString();
	}	
	
	
}
