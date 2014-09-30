package operations;

import data.Collector;

public class O_DeleteSelectedGraph implements Operation {

	@Override
	public void run() {
		Collector.getSlides().removeSelectedAndUpdateIndex();
		Collector.getWindow().updateAnimationImages();
		
		if(Collector.getSlides().isEmpty()) {
			Collector.getModi().active = false;
		}
		Collector.getWindow().repaintBoard();
	}
}
