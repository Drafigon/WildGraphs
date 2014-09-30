package ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class SlideShape {

	private int width;
	private int height;
	private int border;
	private BufferedImage slide;

	
	public SlideShape(int width, int height, int border, Color background) {
		super();
		this.width = width;
		this.height = height;
		this.border = border;
		updateBackgroundImage(background);
	}
	
	void updateBackgroundImage(Color background) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		g2.setColor(Color.GRAY);
		g2.fillRect(0, 0, width, height);
		g2.setColor(background);
		
		final double MAX_IMAGE_WIDTH = width - border*2;
		final double MAX_IMAGE_HEIGHT = height - border*2;
		
		g2.fillRect(border, border, (int) MAX_IMAGE_WIDTH, (int) MAX_IMAGE_HEIGHT);
		
		// border
		g2.setColor(Color.BLACK);
		g2.drawRect(border - 1, border - 1, (int) MAX_IMAGE_WIDTH + 1, (int) MAX_IMAGE_HEIGHT + 1);
		g2.dispose();
		
		slide = image;		
	}

	public BufferedImage getSlideImage() {
		return slide;
	}
	
	
}
