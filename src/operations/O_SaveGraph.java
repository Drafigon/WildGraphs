package operations;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import data.Collector;
import external.GraphSaver;
import graph.implementation.Graph;



public class O_SaveGraph implements Operation {

	private static final String EXTENSION = "graph";
	private static String path = ".";
	
	@Override
	public void run() {		
		if(Collector.getSlides() != null && !Collector.getSlides().isEmpty()) {
			JFileChooser chooser = new JFileChooser(path);
			
			// set filter
			FileFilter filter = new FileNameExtensionFilter("graph data", "graph");
			chooser.addChoosableFileFilter(filter);
			chooser.setFileFilter(filter);
					
			int result = chooser.showSaveDialog(null);
			
			if(result == JFileChooser.APPROVE_OPTION){
				// get values
				File file = chooser.getSelectedFile();		
				path = file.getPath();
				String completeName = file.getName();
	
				
				// split	
				int index = completeName.indexOf('.');
				
				String filename = "";
				if(index != -1) {
					filename = completeName.substring(0,index);
				}
				else {
					filename = completeName;
				}
				
				System.out.println(filename);
				
				// get graph
				List<Graph> graphs = Collector.getSlides().getAllGraphs();
				
				// create graph name
				String extension = "." + EXTENSION;
				String graphname = filename + extension;
								
				// correct path
				int lastseperator = path.lastIndexOf(File.separatorChar);
				path = path.substring(0, lastseperator + 1);
							
				// show option window
				GraphSaver.saveAnimation((ArrayList<Graph>) graphs, path + graphname);
				
				Collector.getWindow().setTextOfSelectedGraphTab(graphname);
			}	
		}
	}
}
