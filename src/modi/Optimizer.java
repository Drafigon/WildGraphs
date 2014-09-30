package modi;
import java.awt.Point;

import javax.swing.JPanel;

import modi.indicators.Indicator;


public interface Optimizer {
	Point getOptimizedPoint(Point point);
	JPanel getOptionPanel();
	Indicator getIndicator();
}
