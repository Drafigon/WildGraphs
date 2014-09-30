package modi;

import java.awt.Point;

import javax.swing.JPanel;

import modi.indicators.Indicator;

/**
 * This class is for user interaction
 * with the mouse on the JDrawer.
 * 
 * @author J. Wilde
 *
 */
public abstract class Modus {	
	public void runClickedOperation(Point mouse){};
	public void runPressedOperation(Point mouse){};
	public void runReleasedOperation(Point mouse){};
	public void runDraggedOperation(Point mouse){};
	public void runMovedOperation(Point mouse){};
	
	
	public abstract void updateValuesFromPanel();
	
	public abstract JPanel getOptionPanel();
	
	public abstract Indicator getIndicator();
	
	public abstract String getDescription();
}
