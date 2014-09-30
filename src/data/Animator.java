package data;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import ui.JWindow;

public class Animator implements ActionListener {

	private Timer timer;
	private StorageSlides slides;
	private JWindow window;
	
	Animator(StorageSlides slides, JWindow window) {
		this.slides = slides;
		this.window = window;
	}
	
	void setSlides(StorageSlides s) {
		slides = s;
	}
	
	public void start(int milliseconds) {
		if(timer == null || !timer.isRunning()) {
			timer = new Timer(milliseconds, this);
			timer.start();
		}
	}
	
	public void stop() {
		if(timer != null)
			timer.stop();
	}
	
	private void next() {
		slides.selectNext();
			window.repaintBoard();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {		
		if(slides.getSelectedIndex() < slides.getObjectCount() - 1) {
			next();
		}
		else {
			stop();
		}
	}		
}
