package operations;

import graph.drawing.VertexDrawOption;
import graph.implementation.Vertex;
import modi.M_Create;


public class O_AddVertexDrawOption implements VertexOperation {

	private VertexDrawOption vdo;
	
	public O_AddVertexDrawOption(VertexDrawOption vdo) {
		this.vdo = new VertexDrawOption(vdo);
	}
	
	@Override
	public void run() {
		M_Create.getInstance().addVDO(vdo);
	}

	@Override
	public void setVertex(Vertex vertex) {
		this.vdo = new VertexDrawOption(vertex.getDrawOption());
	}
}
