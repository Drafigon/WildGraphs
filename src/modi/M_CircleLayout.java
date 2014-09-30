package modi;

import java.awt.Point;

import javax.swing.JPanel;

import data.Collector;
import layouter.L_Circle;
import modi.indicators.I_CircleLayout;
import modi.ui.JCircleLayout;
import operations.O_CompleteLayout;

public class M_CircleLayout extends Modus {

	private static final M_CircleLayout INSTANCE = new M_CircleLayout();
	
	private JCircleLayout optionPanel;
	private I_CircleLayout indicator;
	
	private Point center;
	private Point circlepoint;
	private double radius;
	
	private M_CircleLayout() {
		this.center = new Point(0,0);
		this.circlepoint = new Point(0,0);
		calculateRadius();
		indicator = new I_CircleLayout(center, radius);
		optionPanel = new JCircleLayout(this);
		
	}
	
	private void calculateRadius() {
		this.radius = Point.distance(center.x, center.y, circlepoint.x, circlepoint.y);
	}
	
	@Override
	public void runPressedOperation(Point mouse) {
		Optimizer grid = Collector.getModi().optimizer;	
		
		center = grid.getOptimizedPoint(mouse);
		
		indicator.setCenter(center);
		Collector.getWindow().repaint();
	}

	@Override
	public void runDraggedOperation(Point mouse) {		
		Optimizer grid = Collector.getModi().optimizer;			
		circlepoint = grid.getOptimizedPoint(mouse);

		calculateRadius();
		
		indicator.setRadius(radius);
		Collector.getWindow().repaint();	
	}

	@Override
	public JPanel getOptionPanel() {
		return optionPanel;
	}
	@Override
	public I_CircleLayout getIndicator() {
		return indicator;
	}

	public void runLayout() {
		new O_CompleteLayout(new L_Circle(center, radius)).run();
	}
	
	@Override
	public void updateValuesFromPanel() {
	}	
	
	@Override
	public String getDescription() {
		return "Circle Layout";
	}

	public static M_CircleLayout getInstance() {
		return INSTANCE;
	}
}
