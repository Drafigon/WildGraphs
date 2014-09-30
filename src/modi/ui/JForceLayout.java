package modi.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import modi.LayoutModus;
import ui.JIconButton;

public class JForceLayout extends JPanel {
	private final int ROUNDS_MINIMUM = 10;
	private final int ROUNDS_STEP = 10;
	private final int ROUNDS_INIT = 500;
	private int rounds = ROUNDS_INIT;
	private JLabel label_rounds;
	private JIconButton button_less;
	private JIconButton button_more;
	private Timer timer;
	private JButton button_go;
	private JSlider distance;
	private LayoutModus modus;
	private JCheckBox showIndicator;
	private JButton refresh;
	
	public JForceLayout(final LayoutModus modus) {
		this.modus = modus;
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		showIndicator = new JCheckBox("show indicator");
		showIndicator.setSelected(true);
		showIndicator.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(showIndicator);
		
		JLabel lblMinimumDistance = new JLabel("Minimum distance");
		lblMinimumDistance.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lblMinimumDistance);
		
		distance = new JSlider();
		distance.setValue(200);
		distance.setMinorTickSpacing(10);
		distance.setSnapToTicks(true);
		distance.setMaximum(300);
		distance.setPaintTicks(true);
		distance.setMajorTickSpacing(50);
		distance.setMinimum(50);
		add(distance);
		
		JLabel labelMaxRounds = new JLabel("Max rounds");
		labelMaxRounds.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(labelMaxRounds);
		
		JPanel panel = new JPanel();
		add(panel);
		
		button_less = new JIconButton("");
		button_less.setIcons("button_minus.png", "button_minus_hover.png");
		button_less.setSizeToIconSize(10);
		button_less.setToolTipText("Faster");
		panel.add(button_less);
		
		label_rounds = new JLabel();
		label_rounds.setText("500");
		label_rounds.setPreferredSize(new Dimension(100, 20));
		label_rounds.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(label_rounds);
		
		button_more = new JIconButton("");
		button_more.setIcons("button_plus.png", "button_plus_hover.png");
		button_more.setSizeToIconSize(10);
		button_more.setToolTipText("Slower");
		panel.add(button_more);
		
		JPanel panel_1 = new JPanel();
		add(panel_1);
		
		refresh = new JButton("Refresh");	
		panel_1.add(refresh);
		
		button_go = new JButton("Go");
		panel_1.add(button_go);
		
		addListener();
	}
	
	public int getRadius() {
		return distance.getValue();
	}
	
	public boolean isShowIndicator() {
		return showIndicator.isSelected();
	}
	
	private void addListener() {
		RangeListener listener = new RangeListener();
		button_less.addActionListener(listener);
		button_more.addActionListener(listener);
		button_less.addMouseListener(listener);
		button_more.addMouseListener(listener);
		
		UpdateListener updateListener = new  UpdateListener();		
		distance.addChangeListener(updateListener);
		refresh.addActionListener(updateListener);
		showIndicator.addActionListener(updateListener);
		
		button_go.addActionListener(new StartListener());
	}

	public int getRounds() {
		return rounds;
	}
	
	private class UpdateListener implements ChangeListener, ActionListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			modus.updateValuesFromPanel();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			modus.updateValuesFromPanel();		
		}	
	}
	
	private class StartListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			modus.runLayout();
			modus.updateValuesFromPanel();
		}
	}
	
	private class RangeListener extends MouseAdapter implements ActionListener {
		private final int TICK = 40;
		private ActionListener subtractor;
		private ActionListener adder;
		
		public RangeListener() {
			subtractor = new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent arg0) {
					subtractRounds();
					updateRoundsText();					
				}
			};
			
			adder = new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent e) {
					addRounds();
					updateRoundsText();					
				}
			};
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			if(e.getSource() == button_less) {
				timer = new Timer(TICK, subtractor);
				timer.start();
			}
			if(e.getSource() == button_more) {
				timer = new Timer(TICK, adder);
				timer.start();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			timer.stop();
		}	
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == button_less) {
				subtractRounds();
			}
			if(e.getSource() == button_more) {			
				addRounds();
			}
			updateRoundsText();
		}
	}
	
	private void subtractRounds() {
		rounds = Math.max(rounds - ROUNDS_STEP, ROUNDS_MINIMUM);
	}
	
	private void addRounds() {
		rounds += ROUNDS_STEP;
	}
	
	private void updateRoundsText() {
		label_rounds.setText(rounds+"");
	}
}
