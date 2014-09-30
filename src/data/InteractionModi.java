package data;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import modi.M_DoNothing;
import modi.M_Menu;
import modi.Modus;
import modi.Optimizer;
import modi.Optimizer_Grid;

public class InteractionModi extends MouseAdapter {
	public Optimizer optimizer = Optimizer_Grid.getInstance();
	public Modus leftClickModus = M_DoNothing.getInstance();
	public Modus rightClickModus = M_Menu.getInstance();
	public boolean active = false;
	
	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
		if(active) {
			super.mouseClicked(mouseEvent);
			if(mouseEvent.getButton() == MouseEvent.BUTTON1) {
				leftClickModus.runClickedOperation(mouseEvent.getPoint());
			}
			if(mouseEvent.getButton() == MouseEvent.BUTTON3) {
				rightClickModus.runClickedOperation(mouseEvent.getPoint());
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent mouseEvent) {	
		if(active) {
			super.mouseMoved(mouseEvent);
			optimizer.getOptimizedPoint(mouseEvent.getPoint());
			leftClickModus.runMovedOperation(mouseEvent.getPoint());	
		}
	}

	@Override
	public void mousePressed(MouseEvent mouseEvent) {
		if(active) {
			super.mousePressed(mouseEvent);
			if(mouseEvent.getButton() == MouseEvent.BUTTON1) {
				leftClickModus.runPressedOperation(mouseEvent.getPoint());
			}
			if(mouseEvent.getButton() == MouseEvent.BUTTON3) {
				rightClickModus.runPressedOperation(mouseEvent.getPoint());
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {
		if(active) {
			super.mouseReleased(mouseEvent);
			if(mouseEvent.getButton() == MouseEvent.BUTTON1) {
				leftClickModus.runReleasedOperation(mouseEvent.getPoint());
			}
			if(mouseEvent.getButton() == MouseEvent.BUTTON3) {
				rightClickModus.runReleasedOperation(mouseEvent.getPoint());
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent mouseEvent) {
		if(active) {
			super.mouseDragged(mouseEvent);
			if(mouseEvent.getModifiers() == MouseEvent.BUTTON1_MASK){
				leftClickModus.runDraggedOperation(mouseEvent.getPoint());
			}
			if(mouseEvent.getModifiers() == MouseEvent.BUTTON3_MASK) {
				rightClickModus.runDraggedOperation(mouseEvent.getPoint());
			}
		}
	}		
}
