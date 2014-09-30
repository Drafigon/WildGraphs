package modi;

import java.awt.Point;

import javax.swing.JPanel;

import operations.O_CompleteLayout;
import data.Collector;
import layouter.L_Bipartit;
import layouter.Layouter;
import modi.indicators.I_BipartitLayout;
import modi.indicators.Indicator;
import modi.ui.JRectLayout;

public class M_BipartitLayout extends LayoutModus {

	private static final M_BipartitLayout INSTANCE = new M_BipartitLayout();
	
	private JRectLayout option = new JRectLayout(this);
	private I_BipartitLayout indicator = new I_BipartitLayout();
	
	private Point start = new Point(0,0);
	private Point end = new Point(0,0);
	
	private M_BipartitLayout() {
	}
	
	
	@Override
	public void runPressedOperation(Point mouse) {
		Optimizer optimizer = Collector.getModi().optimizer;		
		start = optimizer.getOptimizedPoint(mouse);
		
		indicator.setStart(start);
		Collector.getWindow().repaint();
	}

	@Override
	public void runDraggedOperation(Point mouse) {
		Optimizer optimizer = Collector.getModi().optimizer;			
		end = optimizer.getOptimizedPoint(mouse);
		
		indicator.setEnd(end);
		Collector.getWindow().repaint();	
	}
	
	@Override
	public void runLayout() {
		Point leftCorner = new Point(0,0);
		Point rightCorner = new Point(0,0);
		
		leftCorner.x = Math.min(start.x, end.x);
		leftCorner.y = Math.min(start.y, end.y);
		rightCorner.x = Math.max(start.x, end.x);
		rightCorner.y = Math.max(start.y, end.y);
		
		Layouter layout = new L_Bipartit(leftCorner, rightCorner);
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
		return "Modus Bipartit Layout";
	}
	
	public static M_BipartitLayout getInstance() {
		return INSTANCE;
	}

}
