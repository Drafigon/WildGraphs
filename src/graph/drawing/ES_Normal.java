package graph.drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;

/**
 * Basic shape for edges.
 * 
 * @author J. Wilde
 * @version 1.0
 * @since 02.04.2014
 *
 */
public class ES_Normal implements EdgeShape {
	
	@Override
	public void drawCurve(Graphics2D g2, Point start, Point end, Point curve,
			Color color, BasicStroke stroke) {
		g2.setStroke(stroke);
		g2.setColor(color);

		CubicCurve2D.Double cubicCurve = new CubicCurve2D.Double(start.x, start.y, curve.x, curve.y, curve.x, curve.y, end.x, end.y);
		g2.draw(cubicCurve);
		
	}

	@Override
	public void drawArrow(Graphics2D g2, int arrowsize, Point location, double angle, Color color) {
		BufferedImage arrow = createArrowImage(arrowsize, angle, color, g2.getBackground());
		Point arrowLocation = calculateArrowheadPoint(arrowsize * 4.0/6.0, angle);
		int diffX = (int) (arrowsize * 0.5 - arrowLocation.x);
		int diffY = (int) (arrowsize * 0.5 - arrowLocation.y);
		
		g2.drawImage(arrow, location.x - diffX, location.y - diffY, null);		
	}
	
	@Override
	public void drawMouseOver(Graphics2D g2, Point location) {
		g2.setStroke(new BasicStroke(2));
		g2.setColor(Color.red);
		int size = 25;
		int x = (int) (location.x - size * 0.5);
		int y = (int) (location.y - size * 0.5);
		
		g2.drawOval(x, y, size, size);
		
	}
	
	private Point calculateArrowheadPoint(double size, double angle) {
		Point p = new Point();
		p.x = (int) (Math.cos(angle) * (size * 0.5));
		p.y = (int) (Math.sin(angle) * (size * 0.5));
		return p;
	}
	
	
	private BufferedImage createArrowImage(int size, double degree, Color color, Color background) {
		BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g3 = (Graphics2D) image.getGraphics();
		g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// path for arrow
		GeneralPath path2 = new GeneralPath();
		path2.moveTo(size * 1.0/6.0, size * 1.0/6.0);
		path2.lineTo(size * 1.0/3.0, size * 1.0/2.0);
		path2.lineTo(size * 1.0/6.0, size * 5.0/6.0);
		path2.lineTo(size * 5.0/6.0, size * 1.0/2.0);
		path2.closePath();
		
		// rotate
		g3.rotate(degree + Math.PI, size * 0.5, size * 0.5);
		
		// color background with backgroundcolor
		// so the line behind disappears
		g3.setBackground(background);
		g3.clearRect((int) (size / 2.5), 0, (int) (size * 0.5), size);
		
		// draw arrow
		g3.setColor(color);				
		g3.fill(path2);
		g3.dispose();
		
		return image;
	}


	@Override
	public String toString() {
		return "Normal";
	}
}
