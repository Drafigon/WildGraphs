package layouter;

import graph.implementation.Graph;

/**
 * To implement different types of graph layouter algorithm.
 * 
 * @author J. Wilde
 * @version 1.0
 * @since 03.04.2014
 *
 */
public interface Layouter {
	
	/**
	 * Returns the next graph step.
	 * 
	 * @param graph
	 * @return
	 */
	public Graph getNextLayoutedGraph(Graph graph);
	
	public Graph getCompleteLayoutedGraph(Graph graph);
}
