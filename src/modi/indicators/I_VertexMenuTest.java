package modi.indicators;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import operations.Operation;

public class I_VertexMenuTest extends JFrame {
	private JPanel panel = new Tafel();
	private I_Menu menu = new I_EdgeMenu();
	
	private I_VertexMenuTest() {
		init();
	}
	
	private void init() {
		this.add(panel);
		MouseAdapter listener = new MoveListener();
		panel.addMouseMotionListener(listener);
		panel.addMouseListener(listener);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
	}
	
	I_Menu getMenu() {
		return menu;
	}
	
	private class Tafel extends JPanel {
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(400, 400);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;	
			menu.draw(g2);	
		}	
	}
	
	private class MoveListener extends MouseAdapter {	
		@Override
		public void mousePressed(MouseEvent e) {
			menu.setLocation(e.getPoint());
			menu.setVisible(true);
			panel.repaint();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			Operation op = menu.getHitOperation(e.getPoint());
			System.out.println("Operation: " + op);
			menu.setVisible(false);
			panel.repaint();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			menu.updateMouseOver(e.getPoint());
			panel.repaint();
		}	
	}
	
	public static void main(String[] args) {
		I_VertexMenuTest test = new I_VertexMenuTest();
		test.getMenu().setLocation(new Point(150,150));
		test.getMenu().setVisible(true);
		test.setVisible(true);
	}
}
