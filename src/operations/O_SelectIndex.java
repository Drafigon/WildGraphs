package operations;

import data.Collector;

public class O_SelectIndex implements Operation {

	private final int index;
	
	public O_SelectIndex(int index) {
		super();
		this.index = index;
	}

	@Override
	public void run() {
		Collector.getSlides().selectIndex(index);
		Collector.getWindow().repaintBoard();
	}

}
