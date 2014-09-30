package operations;

import graph.implementation.Graph;
import layouter.L_InMiddle;
import layouter.Layouter;
import ui.JDrawer;
import data.Collector;
import data.StorageSlides;

public class O_CenterAllGraphs implements Operation {

	@Override
	public void run() {
		JDrawer drawer = Collector.getWindow().getDrawer();
		Layouter layouter = new L_InMiddle(drawer.getWidth(), drawer.getHeight());
		
		StorageSlides slides = Collector.getSlides();
		int selected = slides.getSelectedIndex();
		
		for(int i = 0; i < slides.getObjectCount(); i++) {
			slides.selectIndex(i);
			slides.addUndo();
			Graph graph = slides.getSelectedGraph();
			Graph next = layouter.getCompleteLayoutedGraph(graph);
			slides.setSelectedGraph(next);
		}
		
		slides.selectIndex(selected);
	
		drawer.repaint();
	}

}
