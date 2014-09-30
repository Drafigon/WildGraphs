package modi.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import external.ImageLoader;
import modi.Optimizer_Grid;
import ui.JIconButton;
import utility.LookAndFeel;
import javax.swing.border.EmptyBorder;

public class JGrid extends JPanel {
	private final int MINIMUM_GRIDSIZE = 10;
	private final int MAXIMUM_GRIDSIZE = 200;
	private final int STEP = 10;
	
	private int currentGridsize = 40;
	
	private JToggleButton button_raster;
	private Optimizer_Grid optimizer;
	private JIconButton buttonMinus;
	private JIconButton buttonPlus;
	
	public JGrid(Optimizer_Grid optimizer) {
		//this.setBackground(LookAndFeel.Background1);
		
		this.optimizer = optimizer;
		
		button_raster = new JToggleButton();
		button_raster.setIcon(ImageLoader.loadIcon("button_snaptogrid.png"));
		button_raster.setToolTipText("Snap to Grid");
		button_raster.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateOptimizer();
			}
		});
		add(button_raster);
		
		buttonMinus = new JIconButton("");
		String path = "button_gridMinus.png";
		buttonMinus.setIcons(path, path);
		buttonMinus.setToolTipText("Reduce grid size");
		buttonMinus.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				currentGridsize = Math.max(currentGridsize - STEP, MINIMUM_GRIDSIZE);
				updateOptimizer();
			}
		});
		add(buttonMinus);
		
		buttonPlus = new JIconButton("");
		path = "button_gridPlus.png";
		buttonPlus.setIcons(path, path);
		buttonPlus.setToolTipText("Increase grid size");
		buttonPlus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentGridsize = Math.min(currentGridsize + STEP, MAXIMUM_GRIDSIZE);
				updateOptimizer();
			}
		});
		add(buttonPlus);
	}
	
	private void updateOptimizer() {
		optimizer.updateAll();
	}

	public boolean isSnaped() {
		return button_raster.isSelected();
	}
	
	public int getGridSize() {
		return currentGridsize;
	}	

}
