package operations;

import data.Collector;

public class O_SelectPreviousSlide implements Operation {

	@Override
	public void run() {
		Collector.getSlides().selectPrevious();
		Collector.getWindow().repaintBoard();
	}
}
