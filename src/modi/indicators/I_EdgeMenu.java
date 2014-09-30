package modi.indicators;

import java.util.ArrayList;
import java.util.List;

import graph.implementation.Edge;
import graph.implementation.Vertex;
import operations.EdgeOperation;
import operations.O_AddEdgeDrawOption;
import operations.O_AddWeightToEdge;
import operations.O_ChangeEdgeDirection;
import operations.O_DeleteEdge;
import operations.O_RelaxEdge;
import operations.O_SetEdgeName;

public class I_EdgeMenu extends I_Menu {

	private List<EdgeOperation> operations = new ArrayList<EdgeOperation>();
	
	public I_EdgeMenu() {	
		super();
		Edge edge = new Edge(new Vertex("A"), new Vertex("B"), true);
		MenuIcon icon;
		EdgeOperation op;
		
		icon = new MenuIcon("e_delete.png");
		op = new O_DeleteEdge(edge);
		operations.add(op);
		this.addIcon(icon, op, "Delete");
		
		icon = new MenuIcon("v_name.png");
		op = new O_SetEdgeName(edge);
		operations.add(op);
		this.addIcon(icon, op, "Description");
		
		icon = new MenuIcon("e_weight+1.png");
		op = new O_AddWeightToEdge(edge, +1);
		operations.add(op);
		this.addIcon(icon, op, "Weight +1");
		
		icon = new MenuIcon("e_weight-1.png");
		op = new O_AddWeightToEdge(edge, -1);
		operations.add(op);
		this.addIcon(icon, op, "Weight -1");
		
		icon = new MenuIcon("e_relax.png");
		op = new O_RelaxEdge(edge);
		operations.add(op);
		this.addIcon(icon, op, "Relax");
		
		icon = new MenuIcon("icon_options.png");
		op = new O_AddEdgeDrawOption(edge.getDrawOption());
		operations.add(op);
		this.addIcon(icon, op, "Save draw options");
	
		icon = new MenuIcon("e_direction.png");
		op = new O_ChangeEdgeDirection(edge);
		operations.add(op);
		this.addIcon(icon, op, "Change direction");
		
		this.layoutIcons();
		this.updateBackgroundImage();
	}
	
	public void setEdge(Edge edge) {
		setLocation(edge.getCurvePoint());	
		
		for(EdgeOperation op : operations) {
			op.setEdge(edge);
		}
	}
}
