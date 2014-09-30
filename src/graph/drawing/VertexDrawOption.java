package graph.drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;


/**<pre>
 * This class represents the way a vertex should draw.
 * All needed information are hold here and can be changed.
 * Often this class is abbreviated as "VDO".
 * For standard options a subclass can be created.
 * Subclasses begin with "VDO_".
 * </pre>
 * @author J. Wilde
 * @version 1.0
 * @since 02.04.2014
 *
 */
public class VertexDrawOption {
	// To count the VDOs
	private static int count = 0;
	private final int NUMBER = count;
	
	// Attributes
	private int size;
	private Color incolor;
	private Color outcolor;
	private BasicStroke stroke;
	private boolean showText;
	private VertexShape shape;
	private Font font;
	
	
	//*******************
	//	CONSTRUCTORS
	//*******************
	
	/**<pre>
	 * Creates a new VertexDrawOption with default attributes:
	 * <b>Shape: </b> new VS_Round()
	 * <b>Size: </b> 40
	 * <b>Incolor: </b> Color.white
	 * <b>Outcolor: </b> Color.black
	 * <b>Stroke: </b> new BasicStroke(2)
	 * <b>ShowText: </b> true
	 * </pre>
	 */
	public VertexDrawOption() {
		Font standard = new Font(Font.DIALOG, Font.PLAIN, 18);
		initiate(new VS_Round(), 40, Color.white, Color.black, new BasicStroke(2), true, standard);
	}
	
	/**
	 * Create a new VertexDrawOption with the same attributes as the given one.
	 * 
	 * @param vdo : VertexDrawOption
	 */
	public VertexDrawOption(VertexDrawOption vdo) {
		initiate(vdo.shape, vdo.size, vdo.incolor, vdo.outcolor, vdo.stroke, vdo.showText, vdo.getFont());
		count ++;
	}	
	
	private void initiate(VertexShape shape, int size, Color incolor, Color outcolor, BasicStroke stroke, boolean showText, Font font) {
		this.size = size;
		this.incolor = incolor;
		this.outcolor = outcolor;
		this.stroke = stroke;
		this.showText = showText;
		this.shape = shape;
		this.font = font;
		count ++;
	}
	
	
	//*******************
	//	DRAW FUNCTIONS
	//*******************
	
	public void drawShape(Graphics2D g2, int x, int y) {
		shape.drawStandard(g2, x, y, size, incolor, outcolor, stroke);
	}
	
	public void drawMouseOver(Graphics2D g2, int x, int y) {
		shape.drawMouseOver(g2, x, y, (int) (size + 20));
	}

	public void drawPin(Graphics2D g2, int x, int y) {
		shape.drawPin(g2, x, y, size, outcolor);
	}
	
	//*******************
	//		HELPER
	//*******************
	public Point calculatePointOnShape(double angle, int size) {
		Point point = shape.calculatePointOnShape(angle, size);
		return point;
	}
	
	/**
	 * Compare two VertexDrawOptions.
	 * Returns true, if the attributes are equal.
	 * This is used by export/import.
	 * 
	 * @see external.GraphSaver
	 * @see external.GraphLoader
	 * @param vdo
	 * @return true/false
	 */
	public boolean hasSameAttributesAs(VertexDrawOption vdo) {
		boolean result = true;
		
		if(this.size != vdo.size) result = false;
		if(this.showText != vdo.showText) result = false;
		if(this.incolor.getRGB() != vdo.incolor.getRGB()) result = false;
		if(this.outcolor.getRGB() != vdo.outcolor.getRGB()) result = false;
		if(this.stroke.getLineWidth() != vdo.stroke.getLineWidth()) result = false;
		if(this.shape.getClass() != vdo.shape.getClass()) result = false;
		
		return result;
	}
	
	//*******************
	//		SETTER
	//*******************
	
	
	public VertexShape getStandardShape() {
		return shape;
	}
	
	public void setSize(int size) {
		this.size = size;
	}

	public void setStroke(int size) {
		this.stroke = new BasicStroke(size);
	}
	
	public void setFont(Font font) {
		this.font = font;
	}

	public void setIncolor(Color incolor) {
		this.incolor = incolor;
	}

	public void setOutcolor(Color outcolor) {
		this.outcolor = outcolor;
	}

	public void setShape(VertexShape standardShape) {
		this.shape = standardShape;
	}
	
	public void setShowText(boolean showText) {
		this.showText = showText;
	}
	
	
	//*******************
	//		GETTER
	//*******************
	
	public int getSize() {
		return size;
	}

	public Color getIncolor() {
		return incolor;
	}

	public Color getOutcolor() {
		return outcolor;
	}

	public BasicStroke getStroke() {
		return stroke;
	}

	public Font getFont() {
		return font;
	}

	public VertexShape getShape() {
		return shape;
	}
	
	

	public boolean isShowText() {
		return showText;
	}

	
	
	@Override
	public String toString() {		
		String text = "";
		text += "VDO Number: " + NUMBER;
		return text;
	}
}
