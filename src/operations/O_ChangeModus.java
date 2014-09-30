package operations;

import ui.JDrawer;
import ui.JWindow;
import data.Collector;
import modi.Modus;

public class O_ChangeModus implements Operation {

	private Modus modus;
	
	public O_ChangeModus(Modus modus) {
		this.modus = modus;
	}
	
	@Override
	public void run() {
		Collector.getModi().leftClickModus = modus;
		
		JWindow window = Collector.getWindow();
		window.setOptions(modus.getOptionPanel());
			
		JDrawer drawer = window.getDrawer();
		drawer.setModusIndicator(modus.getIndicator());
		
		window.setStatus(modus.getDescription());
		
		modus.updateValuesFromPanel();
		window.repaintBoard();
	}

}
