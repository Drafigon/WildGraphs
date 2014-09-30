package modi.ui;
import graph.drawing.VDO_StandardText;
import graph.drawing.VS_EndState;
import graph.drawing.VS_Rect;
import graph.drawing.VS_Round;
import graph.drawing.VS_StartState;
import graph.drawing.VertexDrawOption;
import graph.drawing.VertexShape;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.border.LineBorder;
import javax.swing.border.EmptyBorder;

import operations.O_PaintVDOToAll;


class JVertexType extends JPanel {

	private VertexDrawOption vdo = new VDO_StandardText();
	private JCreate parent;
	
	private JComboBox<VertexShape> c_shape;
	private JSlider c_size;
	private JSlider c_linewidth;
	private JButton c_incolor;
	private JCheckBox c_showText;

	private JButton c_outcolor;
	
	private boolean listenersActive = true;
	private JButton button_font;
	private JLabel c_font;
	private VertexShape[] shapes;

	/**
	 * Create the panel.
	 */
	JVertexType(JCreate parent) {
		setPreferredSize(new Dimension(100, 300));
		this.parent = parent;
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		JLabel lblVertex = new JLabel("Vertex");
		panel_1.add(lblVertex);
		lblVertex.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel panel_0 = new JPanel();
		panel_1.add(panel_0);
		
		JLabel lblShape = new JLabel("Shape");
		panel_0.add(lblShape);
		
		// List of all shapes
		c_shape = new JComboBox<VertexShape>();
		
		shapes = new VertexShape[] {
				new VS_Round(), 
				new VS_Rect(), 
				new VS_StartState(), 
				new VS_EndState()};
		DefaultComboBoxModel<VertexShape> model = new DefaultComboBoxModel<VertexShape>(shapes);
		c_shape.setModel(model);
		panel_0.add(c_shape);
		
		JLabel lblGre = new JLabel("Size");
		panel_1.add(lblGre);
		lblGre.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		c_size = new JSlider();
		panel_1.add(c_size);
		c_size.setMaximum(100);
		c_size.setPaintTicks(true);
		c_size.setMinimum(10);
		c_size.setValue(40);
		c_size.setSnapToTicks(true);
		c_size.setMinorTickSpacing(10);
		c_size.setMajorTickSpacing(50);
		
		JLabel lblLinewidth = new JLabel("Linewidth");
		panel_1.add(lblLinewidth);
		lblLinewidth.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		c_linewidth = new JSlider();
		panel_1.add(c_linewidth);
		c_linewidth.setPaintTicks(true);
		c_linewidth.setValue(2);
		c_linewidth.setMinorTickSpacing(1);
		c_linewidth.setMajorTickSpacing(5);
		c_linewidth.setMaximum(10);
		c_linewidth.setSnapToTicks(true);
		
		JPanel panel = new JPanel();
		panel_1.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblIncolor = new JLabel("Incolor");
		panel.add(lblIncolor);
		
		c_incolor = new JButton();
		c_incolor.setHorizontalAlignment(SwingConstants.TRAILING);
		c_incolor.setPreferredSize(new Dimension(50, 25));
		panel.add(c_incolor);
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3);
		
		JLabel lblOutcolor = new JLabel("Outcolor");
		panel_3.add(lblOutcolor);
		
		c_outcolor = new JButton("");
		c_outcolor.setPreferredSize(new Dimension(50, 25));
		panel_3.add(c_outcolor);
		
		c_showText = new JCheckBox("show Label");
		c_showText.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_1.add(c_showText);
		c_showText.setSelected(true);
		
		c_font = new JLabel("Text");
		c_font.setVisible(false);
		c_font.setBorder(new EmptyBorder(5, 10, 5, 10));
		c_font.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_1.add(c_font);
		
		button_font = new JButton("Select Font");
		button_font.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_1.add(button_font);
		
		JButton button_paintAll = new JButton("Paint to all");
		button_paintAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new O_PaintVDOToAll(vdo).run();
			}
		});
		add(button_paintAll, BorderLayout.SOUTH);

		
		// Init
		setVDO( parent.getVDO());
		
		// Add Listener
		addListener();
	}

	private void addListener() {
		ColorListener listener = new ColorListener();
		c_incolor.addActionListener(listener);
		c_outcolor.addActionListener(listener);
		
		ModifyListener modify = new ModifyListener();
		c_shape.addActionListener(modify);
		c_size.addChangeListener(modify);
		c_linewidth.addChangeListener(modify);
		c_showText.addActionListener(modify);
		button_font.addActionListener(new FontListener());
	}
	
	void setVDO(VertexDrawOption nextVDO) {
		vdo = null;
		vdo = nextVDO;
		updateInterface(vdo);		
	}
	
	private class ColorListener implements ActionListener {	
		private void setNewColor(JButton panel_color) {
			Color newColor = JColorChooser.showDialog(
	                null,
	                "Choose Color",
	                panel_color.getBackground());
			
			if(newColor != null) {
				panel_color.setBackground(newColor);
				parent.updateVDO();
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
		    int result = fontChooser.showDialog(null);
		    if (result == JFontChooser.OK_OPTION)
		    {
		    	Font font = fontChooser.getSelectedFont(); 
		        System.out.println("Selected Font : " + font); 
		        c_font.setFont(font);
		        parent.updateVDO();
		    }
		}
	}
	
	private class ModifyListener implements ChangeListener, ActionListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			if(listenersActive) {
				parent.updateVDO();	
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(listenersActive) {
				parent.updateVDO();		
			}
		}		
	}

	
	void updateVDO() {
		VertexShape shape = (VertexShape) c_shape.getSelectedItem();
		int linewidth = c_linewidth.getValue();
		int size = c_size.getValue();
		Color incolor = c_incolor.getBackground();
		Color outcolor = c_outcolor.getBackground();
		boolean showText = c_showText.isSelected();
		Font font = c_font.getFont();
		
		vdo.setShape(shape);
		vdo.setStroke(linewidth);
		vdo.setSize(size);
		vdo.setIncolor(incolor);
		vdo.setOutcolor(outcolor);
		vdo.setShowText(showText);
		vdo.setFont(font);
	}
	
	private void updateInterface(VertexDrawOption vdo) {
		// Set param
		listenersActive = false;

		for(VertexShape s : shapes) {
			if(vdo.getShape().getClass() == s.getClass()) {
				c_shape.setSelectedItem(s);
			}
		}
		
		
		c_linewidth.setValue((int) vdo.getStroke().getLineWidth());
		c_size.setValue(vdo.getSize());
		c_incolor.setBackground(vdo.getIncolor());
		c_outcolor.setBackground(vdo.getOutcolor());
		c_showText.setSelected(vdo.isShowText());
		c_font.setFont(vdo.getFont());
		repaint();
		listenersActive = true;
	}
	
}
