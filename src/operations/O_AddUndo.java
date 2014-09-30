package operations;

import data.Collector;

public class O_AddUndo implements Operation {
	
	@Override
	public void run() {
		Collector.getSlides().addUndo();
	}
}
