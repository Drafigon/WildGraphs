package operations;

import data.Collector;

public class O_MoveSlide implements Operation {

	public enum Direction {
		FORWARD,
		BACKWARD
	}
	
	private Direction direction;
	
	public O_MoveSlide(Direction d) {
		direction = d;
	}
	
	@Override
	public void run() {
		if(direction == Direction.FORWARD) {
			Collector.getSlides().moveSelectedForwards();
		}
		if(direction == Direction.BACKWARD) {
			Collector.getSlides().moveSelectedBackwards();
		}
		
		Collector.getWindow().updateAnimationImages();
		Collector.getWindow().repaintBoard();
	}

}
