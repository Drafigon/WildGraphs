package operations.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import operations.ui.JFormatButton.Format;

public class JFormatOptions extends JPanel {

	private FormatButtonGroup formatGroup;
	private JFormatButton buttonPNG;
	private JFormatButton buttonGIF;
	private JFormatButton buttonJPEG;
	private JFormatButton buttonBMP;
	private JSaveGraphImagePanel parent;
	
	JFormatOptions(JSaveGraphImagePanel parent) {
		this.parent = parent;
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel labelTransBackground = new JLabel("Format");
		labelTransBackground.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(labelTransBackground);
		
		JPanel transparentFormats = new JPanel();
		this.add(transparentFormats);
		
		
		buttonPNG = new JFormatButton("PNG", JFormatButton.Format.PNG, "png");
		buttonPNG.setSelected(true);
		transparentFormats.add(buttonPNG);
		
		buttonGIF = new JFormatButton("GIF", JFormatButton.Format.GIF, "gif");
		transparentFormats.add(buttonGIF);
		
		JSeparator separator_3 = new JSeparator();
		this.add(separator_3);
		
		formatGroup = new FormatButtonGroup();
		formatGroup.addButton(buttonPNG);
		formatGroup.addButton(buttonGIF);
		
		buttonJPEG = new JFormatButton("JPEG", JFormatButton.Format.JPEG, "jpg");
		transparentFormats.add(buttonJPEG);
		formatGroup.addButton(buttonJPEG);
		
		buttonBMP = new JFormatButton("BMP", JFormatButton.Format.BMP, "bmp");
		transparentFormats.add(buttonBMP);
		formatGroup.addButton(buttonBMP);
		
		
		FormatButtonListener listener = new FormatButtonListener();
		buttonPNG.addActionListener(listener);
		buttonGIF.addActionListener(listener);
		buttonBMP.addActionListener(listener);
		buttonJPEG.addActionListener(listener);
	}
	
	JFormatButton.Format getSelectedFormat() {
		JFormatButton button = formatGroup.getSelected();
		if(button == null) return Format.PNG;
		return button.getFormat();
	}
	
	private final class FormatButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFormatButton button = (JFormatButton) e.getSource();
			String ending = "." + button.getEnding();
			parent.setEnding(ending);
		}	
	}
}
