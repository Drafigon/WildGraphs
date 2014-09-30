package operations;

import data.StorageSlides;
import data.Collector;

public class O_SetSlides implements Operation {

	private StorageSlides slides;
	
	public O_SetSlides(StorageSlides slides) {
		this.slides = slides;
	}

	@Override
	public void run() {
		Collector.setSlides(slides);
	}

}
