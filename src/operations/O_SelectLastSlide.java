package operations;

import data.Collector;

public class O_SelectLastSlide implements Operation {

	@Override
	public void run() {
		Collector.getSlides().selectLast();
		Collector.getWindow().repaintBoard();
	}
}
