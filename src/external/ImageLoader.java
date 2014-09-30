package external;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public abstract class ImageLoader {
	
	private final static String FOLDER = "Bilder/";
	
	private ImageLoader(){};
	
	public static BufferedImage loadBufferedImage(String path) {
		try {
			URL url = getURL(path);
			return ImageIO.read(url);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Can not load picture: "+FOLDER+path);
			System.exit(0);
		}
		return null;
	}
	
	public static Icon loadIcon(String path) {
		URL url = getURL(path);
		if(url == null) return null;
		Icon icon = new ImageIcon(url);
		return icon;
	}
	private static URL getURL(String path) {
		return ClassLoader.getSystemResource(FOLDER+path);
	}
}
