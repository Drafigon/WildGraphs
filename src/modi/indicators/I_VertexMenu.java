package modi.indicators;

import java.util.ArrayList;
import java.util.List;

import graph.implementation.Vertex;
import operations.O_AddVertexDrawOption;
import operations.O_DeleteVertex;
import operations.O_SetVertexName;
import operations.O_ToggleVertexFixed;
import operations.VertexOperation;

public class I_VertexMenu extends I_Menu {
	
	private List<VertexOperation> operations = new ArrayList<VertexOperation>();
	
	public I_VertexMenu() {
		super();
		Vertex vertex = new Vertex("A");
		MenuIcon icon;
		VertexOperation op;
		
		
		icon = new MenuIcon("v_delete.png");
		op = new O_DeleteVertex(vertex);
		operations.add(op);
		this.addIcon(icon, op, "Delete");
		
		icon = new MenuIcon("v_name.png");
		op = new O_SetVertexName(vertex);
		operations.add(op);
		this.addIcon(icon, op, "Set name");
		
		icon = new MenuIcon("v_fix.png");
		op = new O_ToggleVertexFixed(vertex);
		operations.add(op);
		this.addIcon(icon, op, "Fix/Unfix");
		
		icon = new MenuIcon("icon_options.png");
		op = new O_AddVertexDrawOption(vertex.getDrawOption());
		operations.add(op);
		this.addIcon(icon, op, "Save Draw Options");

		
		this.layoutIcons();
		this.updateBackgroundImage();
	}
	
	public void setVertex(Vertex vertex) {
		setLocation(vertex.getLocation());	
		
		for(VertexOperation op : operations) {
			op.setVertex(vertex);
		}
	}
}
