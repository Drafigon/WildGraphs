package operations;

import data.Collector;


public class O_Redo implements Operation {

	@Override
	public void run() {
		Collector.getSlides().redo();
		Collector.getWindow().repaintBoard();
	}

}
