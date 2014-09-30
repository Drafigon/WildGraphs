package modi;

import java.awt.Point;

import javax.swing.JPanel;

import data.Collector;
import modi.indicators.I_Grid;
import modi.indicators.Indicator;
import modi.ui.JGrid;

public class Optimizer_Grid implements Optimizer {

	private static final Optimizer_Grid INSTANCE = new Optimizer_Grid();
	
	private JGrid option;
	private I_Grid indicator;
	private boolean snapToGrid;
	private int gridsize;
	
	private Optimizer_Grid() {
		option = new JGrid(this);
		snapToGrid = option.isSnaped();
		gridsize = option.getGridSize();
		indicator = new I_Grid();
	}
	
	public void updateAll() {
		snapToGrid = option.isSnaped();
		gridsize = option.getGridSize();
		indicator.setGridsize(gridsize);
		indicator.setVisible(snapToGrid);
		Collector.getWindow().repaint();
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
	public Point getOptimizedPoint(Point point) {
		return getNextGridPoint(point);
	}

	private Point getNextGridPoint(Point mouse){
		if(snapToGrid) {
			// Draw next Gridpoint
			int lowX = mouse.x / gridsize;
			int lowY = mouse.y / gridsize;
			int hightX = lowX + 1;
			int hightY = lowY + 1;
			
			int x = lowX * gridsize;
			int y = lowY * gridsize;
			
			if(hightX * gridsize - mouse.x < mouse.x - lowX * gridsize) {
				x = hightX * gridsize;
			}
			
			if(hightY * gridsize - mouse.y < mouse.y - lowY * gridsize) {
				y = hightY * gridsize;
			}
				
			// Side Effect
			setGridPoint(new Point(x,y));
			
			return new Point(x, y);
		}
		else {
			return mouse;
		}
	}

	public void setGridPoint(Point point) {
		indicator.setNextGridPoint(point);
		Collector.getWindow().repaint();
	}
	
	public static Optimizer_Grid getInstance() {
		return INSTANCE;
	}
}
