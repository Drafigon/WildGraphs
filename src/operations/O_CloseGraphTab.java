package operations;

import javax.swing.JOptionPane;

import data.Collector;

public class O_CloseGraphTab implements Operation {
	
	
	@Override
	public void run() {
		
		int result = JOptionPane.showConfirmDialog(null,
				"You really want to close this graph tab?", "Are you sure?", JOptionPane.YES_NO_OPTION);
		
		if(result == JOptionPane.OK_OPTION) {
			Collector.getWindow().removeSelectedGraphTab();
			Collector.getWindow().repaint();
			
			if(Collector.getWindow().hasNoTabs()) {
				Collector.getModi().active = false;
				Collector.setSlides(null);
			}
			
		}
	}

}
