package operations;

import data.Collector;

public class O_Undo implements Operation {

	@Override
	public void run() {
		Collector.getSlides().undo();
		Collector.getWindow().repaintBoard();
	}

}
