package modi;

import java.awt.Point;

import javax.swing.JPanel;

import operations.O_CompleteLayout;
import data.Collector;
import layouter.L_Tree;
import layouter.Layouter;
import modi.indicators.I_TreeLayout;
import modi.indicators.Indicator;
import modi.ui.JRectLayout;

public class M_TreeLayout extends LayoutModus {

	private static final M_TreeLayout INSTANCE = new M_TreeLayout();
	private JRectLayout option = new JRectLayout(this);
	private I_TreeLayout indicator = new I_TreeLayout();
	
	private Point start = new Point(0,0);
	private Point end = new Point(0,0);
	
	private M_TreeLayout()  {
		
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
		setEnd(mouse);	
	}
	
	
	@Override
	public void runReleasedOperation(Point mouse) {
		setEnd(mouse);
	}
	
	private void setEnd(Point mouse) {
		Optimizer optimizer = Collector.getModi().optimizer;			
		end = optimizer.getOptimizedPoint(mouse);
		
		indicator.setEnd(end);
		Collector.getWindow().repaint();
	}

	@Override
	public void runLayout() {
		int width = Math.abs(start.x - end.x);
		int height = Math.abs(start.y - end.y);
		
		Layouter layout = new L_Tree(start, height, width);
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
		return "Tree Layout";
	}

	public static M_TreeLayout getInstance() {
		return INSTANCE;
	}

	
}
