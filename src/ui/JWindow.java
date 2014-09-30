package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import operations.O_ExitProgramm;
import data.InteractionModi;
import data.StorageSlides;
import external.ImageLoader;


public class JWindow extends JFrame {
	
	private Container mainPane;
	private Icon icon = ImageLoader.loadIcon("graphtab.png");
	// Left
	private JPanel options;	
	private JSplitPane splitPane_option;
	
	// Bottom
	private JStatusPanel status;
	
	// Interface
	// Top
	private JMenuBar menubar = new JMenuBar();
	private JTools toolbar;
	private JGraphBar graphBar;
	
	// Blackboard Variables (Center)
	private JPanel board;
	private JDrawer drawer;
	
	// Right Animation
	private JAnimation animation;	
	


	public JWindow(StorageSlides slides, InteractionModi modi) {
		super("WildGraphs");
		mainPane = getContentPane();
		
		// Menubar
		this.setJMenuBar(menubar);
		addJMenu(new JFileMenu());
		addJMenu(new JEditMenu());
		addJMenu(new JSlidesMenu());
		addJMenu(new JModiMenu());
		addJMenu(new JCreatorMenu());
		addJMenu(new JLayoutMenu());
		
		// Toolbar
		setNewTools();
		JBasicToolbar basic = new JBasicToolbar();
		
		toolbar.addBasicToolbar(basic);
		addToolPanel(new JCreateToolPanel());
		addToolPanel(new JLayoutToolPanel());
		
		createCenter(slides, modi);		 
		
		// Status line
		status = new JStatusPanel();
		getContentPane().add(status, BorderLayout.SOUTH);
		
		this.addWindowListener(new ClosingListener());
		
		this.getContentPane().setBackground(new Color(150,150,150));
		this.setSize(new Dimension(800,700));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);	
		this.setExtendedState(this.getExtendedState()|JFrame.MAXIMIZED_BOTH );
		this.setFocusable(true);
		this.requestFocus();
		
		
	}


	private void setNewTools() {
		toolbar = new JTools();		
		mainPane.add(toolbar, BorderLayout.PAGE_START);
	}
	
	public void addJMenu(JMenu menu) {
		menubar.add(menu);
	}
	
	public void addToolPanel(JToolPanel panel) {
		toolbar.addSpecialPanel(panel);
	}
	
	private void createCenter(StorageSlides slides, InteractionModi modi) {
		JPanel center = new JPanel(new BorderLayout());
		graphBar = new JGraphBar();
		center.add(graphBar, BorderLayout.NORTH);
		// ------------ Blackboard --------
		
		board = new JPanel();
		board.setLayout(new BorderLayout());
		
		drawer = new JDrawer(slides);
		drawer.setGridIndicator(modi.optimizer.getIndicator());
		drawer.addMouseListener(modi);
		drawer.addMouseMotionListener(modi);
		
		board.add(drawer, BorderLayout.CENTER);
		
		
		// Animation
		animation = new JAnimation(slides);
		board.add(animation, BorderLayout.EAST);
		
		center.add(board, BorderLayout.CENTER);	
		
		// ------------ ---------- --------
		
		splitPane_option = new JSplitPane();
		splitPane_option.setOneTouchExpandable(true);
		splitPane_option.setRightComponent(center);
		splitPane_option.setLeftComponent(options);
		getContentPane().add(splitPane_option, BorderLayout.CENTER);
		
		// left
		options = new JPanel();
		setOptions(options);
	}

	public void repaintBoard() {
		board.repaint();
	}

	// JSlides
	
	public void updateAnimationImages() {
		animation.updateImages();
	}
	
	public void updateAnimationColor() {
		animation.updateColor();
	}

	public void setSlides(StorageSlides s) {
		drawer.setSlides(s);
		animation.setSlides(s);	
		repaintBoard();
	}

	public JDrawer getDrawer() {
		return drawer;
	}

	public void addGraphTab(JGraphButton button) {
		button.setText("Graph");
		button.setIcon(icon);
		graphBar.addGraphButton(button);
		revalidate();
		repaint();
	}

	
	public void removeSelectedGraphTab() {
		graphBar.removeSelectedGraphButton();
	}

	public void setTextOfSelectedGraphTab(String name){
		graphBar.setTextOfSelectedButton(name);	
	}
	
	public void setStatus(String text) {
		status.setStatus(text);
	}

	public void setOptions(JPanel panel) {
		splitPane_option.remove(options);
		options = panel;
		splitPane_option.setLeftComponent(options);
		this.revalidate();
		this.repaint();
	}
	
	public boolean hasNoTabs() {
		return graphBar.isEmpty();
	}
	
	public void colorBackground(Container container, Color color) {
        Component[] components = container.getComponents();
        for (Component component : components) {
        	if(component instanceof JPanel) {
        		component.setBackground(color);
        	}
            if (component instanceof Container) {
            	colorBackground((Container)component, color);
            }
        }
    }
	
	private class ClosingListener extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent arg0) {
			new O_ExitProgramm().run();		
		}
	}
}
