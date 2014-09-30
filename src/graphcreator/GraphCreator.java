package graphcreator;

import graph.implementation.Graph;

/**
 * Implement this interface to
 * create different graph creators
 * for different kinds of graph.
 * Subclasses begin with "GC_"
 * 
 * @author J. Wilde
 * @version 1.0
 * @since 02.04.2014
 */
public interface GraphCreator {
	/**
	 * Creates a new graph.
	 * 
	 * @return new graph
	 */
	public Graph createGraph();
}
