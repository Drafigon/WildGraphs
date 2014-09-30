package operations;

import data.Collector;

public class O_SelectNextSlide implements Operation {

	@Override
	public void run() {
		Collector.getSlides().selectNext();
		Collector.getWindow().repaintBoard();

	}

}
