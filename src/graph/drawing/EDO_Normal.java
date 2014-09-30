package graph.drawing;

import java.awt.Color;
/**
 * Basic black with weight.
 * 
 * @author J. Wilde
 * @version 1.0
 * @since 02.04.2014
 */
public class EDO_Normal extends EdgeDrawOption {

	public EDO_Normal() {
		super();
		setShape(new ES_Normal());
		setColor(Color.black);
		setStroke(2);
		setShowText(true);
		
	}
}
