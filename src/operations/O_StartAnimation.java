package operations;

import data.Collector;

public class O_StartAnimation implements Operation {

	private int milliseconds;
	
	public O_StartAnimation(int milliseconds) {
		this.milliseconds = milliseconds;
	}

	@Override
	public void run() {
		Collector.getAnimator().start(milliseconds);
	}

}
