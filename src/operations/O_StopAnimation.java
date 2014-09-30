package operations;

import data.Collector;

public class O_StopAnimation implements Operation {

	@Override
	public void run() {
		Collector.getAnimator().stop();
	}

}
