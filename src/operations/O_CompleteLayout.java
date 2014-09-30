package operations;

import javax.swing.JOptionPane;

import data.Collector;
import graph.implementation.Graph;
import layouter.Layouter;


public class O_CompleteLayout implements Operation {

	private Layouter layouter;
	
	public O_CompleteLayout(Layouter layouter) {
		this.layouter = layouter;
	}
	
	
	@Override
	public void run() {
		Collector.getSlides().addUndo();
		Graph graph = Collector.getGraph();
		Graph next = layouter.getCompleteLayoutedGraph(graph);
		new O_RelaxAllEdges(next).run();
		Collector.getSlides().setSelectedGraph(next);
		Collector.getWindow().repaintBoard();
		
		if(next == graph) {
			JOptionPane.showMessageDialog(null, "Cannot layout this graph.");
		}
	}
}
