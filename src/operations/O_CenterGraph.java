package operations;

import ui.JDrawer;
import data.Collector;
import graph.implementation.Graph;
import layouter.L_InMiddle;
import layouter.Layouter;

public class O_CenterGraph implements Operation {
	@Override
	public void run() {

		Collector.getSlides().addUndo();
		
		JDrawer drawer = Collector.getWindow().getDrawer();
		Layouter layouter = new L_InMiddle(drawer.getWidth(), drawer.getHeight());
		
		Graph graph = Collector.getGraph();
		Graph next = layouter.getCompleteLayoutedGraph(graph);
		Collector.getSlides().setSelectedGraph(next);
		
		drawer.repaint();
	}

}
