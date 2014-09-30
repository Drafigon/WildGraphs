package operations;

import graph.implementation.Graph;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import data.StorageSlides;
import data.Collector;
import operations.ui.JFormatButton;
import operations.ui.JSaveGraphImagePanel;
import operations.ui.JSaveOptions;
import utility.GraphFunctions;



public class O_SaveGraphImage implements Operation {

	private Object[] options;
	private JSaveGraphImagePanel panel;
	private StorageSlides slides;
	private Color background;

	
	public O_SaveGraphImage() {
		initWindow();
		initOptions();	
	}
	
	private void initWindow() {		
		panel = new JSaveGraphImagePanel();
	}
	
	private void initOptions() {
		options = new Object[2];
		options[0] = "Save";
		options[1] = "Cancel";
	}
	
	@Override
	public void run() {
		if(Collector.getSlides() != null &&!Collector.getSlides().isEmpty()){
			updateWindow();
			
			int result = showDialog();
			
			if(result == JOptionPane.OK_OPTION) {
				// get options; they always set
				JSaveOptions.Save saveOption = panel.getGraphOption();
				String path = panel.getPath();
				String fileName = panel.getFileName();
				JFormatButton.Format format = panel.getFormat();
				
				if(saveOption == JSaveOptions.Save.ONE_GRAPH) {					
					Graph graph = slides.getSelectedGraph();					
					String completePath = path + File.separatorChar + fileName + panel.getFileEnding();
								
					saveGraphImage(graph, format.toString(), completePath);				
				}
				
				if(saveOption == JSaveOptions.Save.COMPLETE_ANIMATION) {
					List<Graph> allGraphs = slides.getAllGraphs();
					
					int count = 0;
					for(Graph g : allGraphs) {
						count++;				
						String completePath = path + File.separatorChar + fileName + "_" + count + panel.getFileEnding();
		
						saveGraphImage(g, format.toString(), completePath);
					}
				}
			}
		}
		
	}
	
	private void saveGraphImage(Graph g, String format, String path) {
		BufferedImage image = GraphFunctions.getGraphImage(g, background);
		
		try {
			File file = new File(path);
			
			if(file.exists()) {
				if(wantToOverride(path)) {
					ImageIO.write(image, format, file);
				}
			}	
			else {
				ImageIO.write(image, format, file);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean wantToOverride(String path) {
		int result = JOptionPane.showConfirmDialog(null, "Do you want to override \n" + path + " ?");
		return result == JOptionPane.OK_OPTION;
	}
	
	private void updateWindow() {
		
		slides = Collector.getSlides();
		StorageSlides copy = new StorageSlides(slides);
		
		background = Collector.getSlides().getBackgroundColor();
		panel.setBackgroundColor(background);
		
		panel.setSlides(copy);
		
		new O_DeselectAllVerticesAndEdges().run();
	}

	private int showDialog() {
		int result = JOptionPane.showOptionDialog(
				null, panel, "Choose save options", 
				JOptionPane.OK_CANCEL_OPTION, 
				JOptionPane.PLAIN_MESSAGE, 
				null, 
				options, 
				null);
		
		return result;
	}

}
