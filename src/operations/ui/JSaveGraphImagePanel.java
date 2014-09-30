package operations.ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

import data.StorageSlides;

public class JSaveGraphImagePanel extends JPanel {
	
	private JSaveOptions saveOptions;
	private JFormatOptions formatOptions;
	private JSavePreview preview;

	
	public JSaveGraphImagePanel() {
		super();
		initWindow();
	}
	
	public void setSlides(StorageSlides slides) {
		preview.setSlides(slides);
	}
	
	public void setBackgroundColor(Color color) {
		preview.setBackgroundColor(color);
	}
	
	private void initWindow() {
		this.setLayout(new BorderLayout(0, 0));
			
		saveOptions = new JSaveOptions();
		formatOptions = new JFormatOptions(this);
		
		JPanel north = new JPanel();
		north.setLayout(new BorderLayout(0, 0));
		north.add(saveOptions, BorderLayout.CENTER);	
		north.add(formatOptions, BorderLayout.SOUTH);	
		
		this.add(north, BorderLayout.NORTH);
		
		preview = new JSavePreview();
		this.add(preview, BorderLayout.CENTER);
	}
	
	
	public JSaveOptions.Save getGraphOption() {
		return saveOptions.getGraphOption();
	}
	
	public String getPath() {
		return saveOptions.getPath();
	}
	
	public String getFileName() {
		return saveOptions.getFileName();
	}
	
	public JFormatButton.Format getFormat() {
		return formatOptions.getSelectedFormat();
	}
	
	public String getFileEnding() {
		return saveOptions.getFileEnding();
	}
	
	public void setEnding(String ending) {
		saveOptions.setEnding(ending);
	}
}
