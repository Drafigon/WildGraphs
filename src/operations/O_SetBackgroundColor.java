package operations;

import java.awt.Color;

import javax.swing.JColorChooser;

import data.Collector;

public class O_SetBackgroundColor implements Operation {

	@Override
	public void run() {
		Color old = Collector.getSlides().getBackgroundColor();
		
		Color newColor = JColorChooser.showDialog(
                null,
                "Choose Background Color",
                old);
		
		if(newColor != null) {
			Collector.getSlides().setBackgroundColor(newColor);
			Collector.getWindow().updateAnimationColor();
			Collector.getWindow().updateAnimationImages();
			Collector.getWindow().repaintBoard();
		}
	}

}
