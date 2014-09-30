package modi.ui;
import graph.drawing.EdgeDrawOption;
import graph.drawing.VertexDrawOption;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

import modi.Modus;


public class JCreate extends JPanel {
	private Modus modus;
	private JVDOSelection vdoSelection;
	private JEDOSelection edoSelection;
	private JVertexType vertexType;
	private JEdgeType edgeType;
	private JCreateAndPaint create;
	/**
	 * Create the panel.
	 */
	public JCreate(Modus modus) {
		this.modus = modus;
		
		setLayout(new BorderLayout(0, 0));
		
		create = new JCreateAndPaint(this);
		this.add(create, BorderLayout.NORTH);
		
		JPanel panel_options = new JPanel();
		
		JPanel selection = new JPanel();
		selection.setLayout(new GridLayout(0, 2, 0, 0));
		
		vdoSelection = new JVDOSelection(this);
		selection.add(vdoSelection);
		
		edoSelection = new JEDOSelection(this);
		selection.add(edoSelection);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);	
		splitPane.setDividerLocation(300);
		splitPane.setOneTouchExpandable(true);
		splitPane.setTopComponent(selection);
		splitPane.setBottomComponent(panel_options);
		
		this.add(splitPane, BorderLayout.CENTER);
		panel_options.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_box = new JPanel();
		panel_options.add(panel_box);
		panel_box.setLayout(new BoxLayout(panel_box, BoxLayout.X_AXIS));
		vertexType = new JVertexType(this);
		panel_box.add(vertexType);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		panel_box.add(separator);
		edgeType = new JEdgeType(this);
		panel_box.add(edgeType);
	}

	
	public boolean isDirected() {
		return create.isDirected();
	}
	
	public VertexDrawOption getVDO() {
		return vdoSelection.getSelected();
	}
	
	public EdgeDrawOption getEDO() {
		return edoSelection.getSelected();
	}
	
	public void addVDO(VertexDrawOption vdo) {
		vdoSelection.add(vdo);
	}
	
	public void addEDO(EdgeDrawOption edo) {
		edoSelection.add(edo);
	}
	
	void updateModus() {
		modus.updateValuesFromPanel();
	}
	
	void updateVDO() {		
		vertexType.updateVDO();
		vdoSelection.repaint();
		modus.updateValuesFromPanel();
	}
	
	void updateEDO() {	
		edgeType.updateEDO();
		edoSelection.repaint();
		modus.updateValuesFromPanel();
	}
	
	void updateSelection() {			
		VertexDrawOption vdo = vdoSelection.getSelected();
		EdgeDrawOption edo = edoSelection.getSelected();
			
		
		vertexType.setVDO(vdo);
		edgeType.setEDO(edo);

		modus.updateValuesFromPanel();
	}

	
	@Override
	public String toString() {
		String text = "";
		text += "< ------- \n";
		text += "Selection VDO: " + vdoSelection.getSelected() + "\n";
		text += "Selection EDO: " + edoSelection.getSelected() + "\n";
		text += " ------- >";
		return text;
	}
}
