package operations;

import data.Collector;

public class O_SelectFirstSlide implements Operation {

	@Override
	public void run() {
		Collector.getSlides().selectFirst();
		Collector.getWindow().repaintBoard();
	}

}
