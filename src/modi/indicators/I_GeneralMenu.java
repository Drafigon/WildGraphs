package modi.indicators;

import modi.M_Create;
import modi.M_Move;
import modi.M_Paint;
import operations.O_CenterGraph;
import operations.O_ChangeModus;
import operations.Operation;

public class I_GeneralMenu extends I_Menu {

	public I_GeneralMenu() {
		super();
		MenuIcon icon;
		Operation op;
		
		icon = new MenuIcon("g_create.png");
		op = new O_ChangeModus(M_Create.getInstance());
		this.addIcon(icon, op, "Create");
		
		icon = new MenuIcon("icon_options.png");
		op = new O_ChangeModus(M_Paint.getInstance());
		this.addIcon(icon, op, "Paint");
		
		icon = new MenuIcon("g_move.png");
		op = new O_ChangeModus(M_Move.getInstance());
		this.addIcon(icon, op, "Move");
		
		icon = new MenuIcon("g_center.png");
		op = new O_CenterGraph();
		this.addIcon(icon, op, "Center graph");
		
		this.layoutIcons();
		this.updateBackgroundImage();
	}
}
