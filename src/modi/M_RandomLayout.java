package modi;

import java.awt.Point;

import javax.swing.JPanel;

import data.Collector;
import operations.O_CompleteLayout;
import layouter.L_Random;
import layouter.Layouter;
import modi.indicators.I_RandomLayout;
import modi.indicators.Indicator;
import modi.ui.JRectLayout;

public class M_RandomLayout extends LayoutModus {
	
	private static final M_RandomLayout INSTANCE = new M_RandomLayout();
	
	private I_RandomLayout indicator;
	private JRectLayout option;
	private Point startCorner = new Point(0,0);
	private Point endCorner = new Point(0,0);

	private M_RandomLayout() {
		super();
		this.indicator = new I_RandomLayout();
		this.option = new JRectLayout(this);
	}

	@Override
	public void runPressedOperation(Point mouse) {
		Optimizer optimizer = Collector.getModi().optimizer;		
		startCorner = optimizer.getOptimizedPoint(mouse);
		
		indicator.setStart(startCorner);
		Collector.getWindow().repaint();
	}

	@Override
	public void runDraggedOperation(Point mouse) {
		Optimizer optimizer = Collector.getModi().optimizer;			
		endCorner = optimizer.getOptimizedPoint(mouse);
		
		indicator.setEnd(endCorner);
		Collector.getWindow().repaint();	
	}

	@Override
	public void runLayout() {
		Point leftCorner = new Point(0,0);
		Point rightCorner = new Point(0,0);
		
		leftCorner.x = Math.min(startCorner.x, endCorner.x);
		leftCorner.y = Math.min(startCorner.y, endCorner.y);
		rightCorner.x = Math.max(startCorner.x, endCorner.x);
		rightCorner.y = Math.max(startCorner.y, endCorner.y);
		
		Layouter layout = new L_Random(leftCorner, rightCorner);
		new O_CompleteLayout(layout).run();
	}
	
	@Override
	public void updateValuesFromPanel() {
		
	}

	@Override
	public JPanel getOptionPanel() {
		return option;
	}

	@Override
	public Indicator getIndicator() {
		return indicator;
	}

	@Override
	public String getDescription() {
		return "Random Layout";
	}

	public static M_RandomLayout getInstance() {
		return INSTANCE;
	}

}
