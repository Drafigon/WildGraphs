package operations.ui;

import javax.swing.JToggleButton;

public final class JFormatButton extends JToggleButton {
	
	public static enum Format {
		PNG,
		GIF,
		JPEG,
		BMP
	}
	
	private Format format;
	private String ending;

	JFormatButton(String name, Format format, String ending) {
		super(name);
		this.format = format;
		this.ending = ending;
	}
	
	Format getFormat()  { 
		return format;
	}
	
	String getEnding() {
		return ending;
	}
}