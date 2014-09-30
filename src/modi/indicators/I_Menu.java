package modi.indicators;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import operations.Operation;

public class I_Menu implements Indicator {

	private Point location = new Point(0,0);
	private final int RADIUS_ICON = 60;
	private final int RADIUS_BACKGROUND = 100;
	private BufferedImage background;
	private List<IconOperation> list = new ArrayList<IconOperation>();
	
	private boolean visible = false;
	
	I_Menu() {
		updateBackgroundImage();
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	void addIcon(MenuIcon icon, Operation op, String tooltip) {
		IconOperation iconOperation = new IconOperation(icon, op);
		iconOperation.tooltip = tooltip;
		
		list.add(iconOperation);
		
		updateBackgroundImage();
	}
	
	public void setLocation(Point location) {
		int deltaX = location.x - this.location.x;
		int deltaY = location.y - this.location.y;
		
		for(IconOperation i : list) {
			i.icon.moveBy(new Point(deltaX, deltaY));
		}
		
		this.location = location;
	}

	public void updateMouseOver(Point mouse) {
		for(IconOperation i : list) {
			boolean mouseOver = i.icon.isInside(mouse);
			i.icon.setMouseOver(mouseOver);
		}
	}
	
	public Operation getHitOperation(Point mouse) {
		for(IconOperation i : list) {
			if(i.icon.isInside(mouse)){
				return i.operation;
			}
		}
		
		return null;
	}
	
	void layoutIcons() {
		// the angle between two icons on the circle
		double angleStep = 2 * Math.PI / list.size();
		
		double alpha = - 0.5 * Math.PI;
		for(IconOperation iconOperation : list) {
			
			int addX = (int) (Math.cos(alpha) * RADIUS_ICON);
			int addY = (int) (Math.sin(alpha) * RADIUS_ICON);
			
			alpha += angleStep;
			
			// location on cirlce for next icon
			int posX = location.x + addX;
			int posY = location.y + addY;
			
			iconOperation.icon.setLocation(new Point(posX, posY));
		}
	}
	
	void updateBackgroundImage() {
		int biggerRadius = RADIUS_BACKGROUND;
		int diameter = (int) (biggerRadius*2);
		
		background = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) background.getGraphics();
		
		// circle on background
		g2.setColor(new Color(200,200,200,230));
		g2.fillOval(0, 0, diameter, diameter);
		
		// lines
		int count = list.size();
		
		double radians = 2 * Math.PI / count;
		g2.setColor(new Color(0,0,0,100));
		
		for(int i = 0; i < count; i++) {
			double alpha = i * radians + radians/2  - 0.5 * Math.PI;
			
			int addX = (int) (Math.cos(alpha) * biggerRadius);
			int addY = (int) (Math.sin(alpha) * biggerRadius);
			
			int posX = biggerRadius + addX;
			int posY = biggerRadius + addY;
			
			g2.drawLine(biggerRadius, biggerRadius, posX, posY);
		}
		
		g2.dispose();
	}

	@Override
	public void draw(Graphics2D g2) {	
		if(visible) {	
			g2.drawImage(background, location.x - RADIUS_BACKGROUND, location.y - RADIUS_BACKGROUND,null);
			
			for(IconOperation icon : list) {
				icon.icon.draw(g2);
								
				// Tooltip
				if(icon.icon.isMouseOver()) {
					String tooltip = icon.tooltip;
					drawTooltip(g2, tooltip);
				}
			}
		}
	}
	
	private void drawTooltip(Graphics2D g2, String text) {
		int textheight = g2.getFont().getSize();
		int textwidth = g2.getFontMetrics().stringWidth(text);		
		
		// draw rectangle
		int posX = (int) (location.x - textwidth/2f);
		int posY = (int) (location.y  + RADIUS_BACKGROUND - textheight / 2f);
		g2.setColor(Color.white);
		g2.fillRect(posX - 5, posY - 5, textwidth + 10, textheight + 10);
		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke(1));
		g2.drawRect(posX - 5, posY - 5, textwidth + 10, textheight + 10);
		
		// draw text
		posY = (int) (location.y + RADIUS_BACKGROUND + textheight / 2f);
		g2.setColor(Color.black);
		g2.drawString(text, posX, posY);
	}

	private class IconOperation {
		public MenuIcon icon;
		public Operation operation;
		public String tooltip;
		
		IconOperation(MenuIcon icon, Operation operation) {
			this.operation = operation;
			this.tooltip = "";
			this.icon = icon;
		}

		@Override
		public String toString() {
			String text = "Icon: " + icon;
			text += "\nOperation: " + operation;
			text += "\nTooltip: " + tooltip;
			return text;
		}
		
	}
}
