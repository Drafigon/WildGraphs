package operations;


import modi.M_Create;
import graph.drawing.EdgeDrawOption;
import graph.implementation.Edge;


public class O_AddEdgeDrawOption implements EdgeOperation {

	private EdgeDrawOption edo;
	
	public O_AddEdgeDrawOption(EdgeDrawOption edo) {
		this.edo = new EdgeDrawOption(edo);
	}
	
	@Override
	public void run() {
		M_Create.getInstance().addEDO(edo);
	}

	@Override
	public void setEdge(Edge edge) {
		edo =  new EdgeDrawOption(edge.getDrawOption());
	}

}
