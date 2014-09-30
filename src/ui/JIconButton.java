package ui;

import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JButton;

import external.ImageLoader;

public class JIconButton extends JButton {
	
	public JIconButton(String name) {
		super(name);
	}
	
	public void setSizeToIconSize(int border) {
		int width = this.getIcon().getIconWidth();
		int height = this.getIcon().getIconHeight();
				
		this.setPreferredSize(new Dimension(width + border, height + border));
	}
	
	public void setIcons(String iconpath, String iconhoverpath) {
		this.setFocusPainted(false);	
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
		
		Icon icon = ImageLoader.loadIcon(iconpath);
		this.setIcon(icon);
		
		if(!iconpath.equals(iconhoverpath)) 
			icon = ImageLoader.loadIcon(iconhoverpath);
		
		this.setRolloverIcon(icon);	
	}
}
