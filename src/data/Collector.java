package data;

import graph.implementation.Graph;
import ui.JWindow;

public abstract class Collector {
	private static InteractionModi modi = new InteractionModi();
	private static StorageSlides slides = new StorageSlides(20);
	private static JWindow window = new JWindow(null, modi);
	private static Animator animator = new Animator(slides, window);
	
	private Collector(){}

	public static JWindow getWindow() {
		return window;
	}
	
	public static StorageSlides getSlides() {
		return slides;
	}
	
	public static InteractionModi getModi() {
		return modi;
	}

	public static Animator getAnimator() {
		return animator;
	}

	public static Graph getGraph() {
		return slides.getSelectedGraph();
	}

	public static void setSlides(StorageSlides s) {
		slides = s;
		window.setSlides(slides);		
		animator.setSlides(slides);
	}
}
