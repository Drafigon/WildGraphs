package operations;

import data.Collector;
import external.GraphLoader;
import graph.implementation.Graph;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class O_LoadGraph implements Operation {
	
	private final String EXTENSION = "graph";
	
	private static String path = ".";
	
	@Override
	public void run() {
		JFileChooser chooser = new JFileChooser(path);
				
		// Filter 
		FileFilter filter = new FileNameExtensionFilter("graph data", EXTENSION);
		chooser.addChoosableFileFilter(filter);
		chooser.setFileFilter(filter);
			
		int result = chooser.showOpenDialog(null);
		
		if(result == JFileChooser.APPROVE_OPTION) {			
			File file = chooser.getSelectedFile();
			path = file.getPath();
			
			ArrayList<Graph> graphs = GraphLoader.loadAnimation(path);
			
			Graph graph = new Graph();
			graph.setName(file.getName());
			
			new O_AddGraphTab(graph).run();
			Collector.getSlides().setAllGraphs(graphs);
			Collector.getWindow().updateAnimationImages();
			Collector.getWindow().setStatus(Collector.getModi().leftClickModus.getDescription());
			Collector.getWindow().setTextOfSelectedGraphTab(file.getName());
			Collector.getWindow().repaint();
		}
	}

}
