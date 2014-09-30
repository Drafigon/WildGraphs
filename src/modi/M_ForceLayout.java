package modi;

import graph.implementation.Graph;
import graph.implementation.Vertex;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import layouter.L_Force;
import layouter.Layouter;
import modi.indicators.I_ForceLayout;
import modi.indicators.Indicator;
import modi.ui.JForceLayout;
import operations.O_CompleteLayout;
import data.Collector;

public class M_ForceLayout extends LayoutModus {
	private static final M_ForceLayout INSTANCE = new M_ForceLayout();
	private I_ForceLayout indicator = new I_ForceLayout();
	private JForceLayout option = new JForceLayout(this);
	
	private M_ForceLayout() {}
	
	@Override
	public void updateValuesFromPanel() {
		updateRadius();
		updatePoints();
		updateVisibility();
		Collector.getWindow().repaintBoard();
	}

	
	private void updateVisibility() {
		indicator.setVisible(option.isShowIndicator());
	}
	
	private void updateRadius() {
		int radius = option.getRadius();
		indicator.setRadius(radius);
	}
	
	private void updatePoints() {
		Graph graph = Collector.getGraph();
		List<Point> points = new ArrayList<Point>();
		
		if(graph != null) {	
			for(Vertex v : graph.getVertices()) {
				Point p = new Point(v.getLocation());
				points.add(p);
			}	
		}
		
		indicator.setPoints(points);
	}

	@Override
	public void runLayout() {
		int minDistance = option.getRadius();
		int rounds = option.getRounds();
		
		Layouter layouter = new L_Force(minDistance, 0.5, rounds);
		new O_CompleteLayout(layouter).run();	
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
		return "Force Layout";
	}

	public static M_ForceLayout getInstance() {
		return INSTANCE;
	}
}
