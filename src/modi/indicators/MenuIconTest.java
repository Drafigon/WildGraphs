package modi.indicators;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MenuIconTest {
	private JFrame frame;
	private Menu menu;
	
	private MenuIconTest() {
		init();
	}
	
	private void init() {
		frame = new JFrame();
		menu = new Menu();
		frame.add(menu);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
	}
	
	
	public void testDraw() {
		MenuIcon icon = new MenuIcon("Bilder/e_delete.png");
		icon.setLocation(new Point(100,100));
		menu.addIcon(icon);
		
		icon = new MenuIcon("Bilder/v_delete.png");
		icon.setLocation(new Point(200,200));
		icon.setMouseOver(true);
		menu.addIcon(icon);
		
		frame.setVisible(true);	
	}
	
	private class Menu extends JPanel {
		private List<MenuIcon> icons = new ArrayList<MenuIcon>();

		void addIcon(MenuIcon icon) {
			icons.add(icon);
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setBackground(Color.lightGray);
			g2.clearRect(0, 0, getWidth(), getHeight());
			
			for(MenuIcon i : icons) {
				i.draw(g2);
			}
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(400, 400);
		} 
	}
	
	public static void main(String[] args) {
		MenuIconTest test = new MenuIconTest();
		test.testDraw();
	}
}

