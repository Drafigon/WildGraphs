package modi.ui;
import graph.drawing.ES_Normal;
import graph.drawing.EdgeDrawOption;
import graph.drawing.EdgeShape;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import operations.O_PaintEDOToAll;


class JEdgeType extends JPanel {

	private JCreate parent;
	private EdgeDrawOption edo;
	
	private JComboBox<EdgeShape> c_shape;
	private JSlider c_linewidth;
	private JButton c_color;
	private JCheckBox c_showText;

	
	private boolean listenersActive = true;
	private JSlider c_arrowsize;
	private JLabel c_font;
	private JButton button_font;
	private EdgeShape[] shapes;
	/**
	 * Create the panel.
	 */
	JEdgeType(JCreate parent) {
		setPreferredSize(new Dimension(100, 300));
		this.parent = parent;
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		
		shapes = new EdgeShape[] {
				new ES_Normal()};
		DefaultComboBoxModel<EdgeShape> model = new DefaultComboBoxModel<EdgeShape>(shapes);	
		JLabel lblEdge = new JLabel("Edge");
		panel.add(lblEdge);
		lblEdge.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel panel_2 = new JPanel();		
		JLabel label = new JLabel("Shape");
		panel_2.add(label);
		
		c_shape = new JComboBox<EdgeShape>();
		c_shape.setModel(model);
		panel_2.add(c_shape);
		panel.add(panel_2);
		
		
		// Arrow Size
		JLabel lblArrowSize = new JLabel("Arrow Size");
		panel.add(lblArrowSize);
		lblArrowSize.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		c_arrowsize = new JSlider();
		panel.add(c_arrowsize);
		c_arrowsize.setMinimum(10);
		c_arrowsize.setValue(20);
		c_arrowsize.setSnapToTicks(true);
		c_arrowsize.setPaintTicks(true);
		c_arrowsize.setMinorTickSpacing(5);
		c_arrowsize.setMaximum(50);
		c_arrowsize.setMajorTickSpacing(10);
		
		JLabel label_1 = new JLabel("Linewidth");
		panel.add(label_1);
		label_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		c_linewidth = new JSlider();
		panel.add(c_linewidth);
		c_linewidth.setValue(2);
		c_linewidth.setSnapToTicks(true);
		c_linewidth.setPaintTicks(true);
		c_linewidth.setMinorTickSpacing(1);
		c_linewidth.setMaximum(10);
		c_linewidth.setMajorTickSpacing(5);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		
		JLabel lblColor = new JLabel("Color");
		panel_3.add(lblColor);
		
		c_color = new JButton();
		c_color.setPreferredSize(new Dimension(50, 25));
		panel_3.add(c_color);
		
		JPanel panel_5 = new JPanel();
		panel.add(panel_5);
		
		c_showText = new JCheckBox("show label");
		c_showText.setSelected(true);
		c_showText.setAlignmentX(0.5f);
		panel_5.add(c_showText);
		
		c_font = new JLabel("Text");
		c_font.setVisible(false);
		c_font.setFont(null);
		c_font.setBorder(new EmptyBorder(5, 10, 5, 10));
		c_font.setAlignmentX(0.5f);
		panel.add(c_font);
		
		button_font = new JButton("Select Font");
		button_font.setAlignmentX(0.5f);
		panel.add(button_font);
		
		JButton button_paintAll = new JButton("Paint to all");
		button_paintAll.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				new O_PaintEDOToAll(edo).run();				
			}
		});
		add(button_paintAll, BorderLayout.SOUTH);

		// Listener
		addListener();
		
		setEDO(parent.getEDO());
	}
	

	void setEDO(EdgeDrawOption edo) {
		this.edo = null;
		this.edo = edo;
		updateInterface();
	}

	private void addListener() {
		ColorListener listener = new ColorListener();
		c_color.addActionListener(listener);
		
		ModifyListener modify = new ModifyListener();
		c_shape.addActionListener(modify);
		c_linewidth.addChangeListener(modify);
		c_arrowsize.addChangeListener(modify);
		c_showText.addActionListener(modify);
		button_font.addActionListener(new FontListener());
	}
	
	void updateEDO() {
		EdgeShape shape = (EdgeShape) c_shape.getSelectedItem();
		int linewidth = c_linewidth.getValue();
		int arrowsize = c_arrowsize.getValue();
		Color color = c_color.getBackground();
		boolean showText = c_showText.isSelected();
		Font font = c_font.getFont();
		
		
		edo.setShape(shape);
		edo.setStroke(linewidth);
		edo.setArrowsize(arrowsize);
		edo.setColor(color);
		edo.setShowText(showText);
		edo.setFont(font);
	}
	
	private void updateInterface() {		
		listenersActive = false;
		c_shape.setSelectedItem(edo.getShape());
		
		for(EdgeShape s : shapes) {
			if(edo.getShape().getClass() == s.getClass()) {
				c_shape.setSelectedItem(s);
			}
		}
		
		c_linewidth.setValue((int) edo.getStroke().getLineWidth());
		c_arrowsize.setValue(edo.getArrowsize());
		c_color.setBackground(edo.getColor());
		c_showText.setSelected(edo.isShowText());
		c_font.setFont(edo.getFont());
		repaint();
		listenersActive = true;
	}
	
	private class ModifyListener implements ChangeListener, ActionListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			if(listenersActive) {
				parent.updateEDO();
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(listenersActive) {
				parent.updateEDO();
			}
		}		
	}
	
	private class ColorListener implements ActionListener {	
		private void setNewColor(JButton panel_color) {			
			Color newColor = JColorChooser.showDialog(
	                null,
	                "Choose Color",
	                panel_color.getBackground());
			
			if(newColor != null) {
				panel_color.setBackground(newColor);
				parent.updateEDO();
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(listenersActive) {
				setNewColor((JButton) e.getSource());	
			}
		}
	}
	
	private class FontListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(listenersActive) {
				setNewFont();
			}
		}
		
		private void setNewFont() {
			JFontChooser fontChooser = new JFontChooser();
			fontChooser.setSelectedFont(c_font.getFont());
		    int result = fontChooser.showDialog(null);
		    
		    if (result == JFontChooser.OK_OPTION)
		    {
		    	Font font = fontChooser.getSelectedFont(); 
		        System.out.println("Selected Font : " + font); 
		        c_font.setFont(font);
		        parent.updateEDO();
		    }
		}
	}
}
