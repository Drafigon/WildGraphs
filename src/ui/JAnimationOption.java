package ui;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;

import operations.O_AddEmptyGraph;
import operations.O_DeleteSelectedGraph;
import operations.O_DuplicateSelectedGraph;
import operations.O_SelectFirstSlide;
import operations.O_SelectLastSlide;
import operations.O_StartAnimation;
import operations.O_StopAnimation;

public class JAnimationOption extends JPanel {
	private final int SPEED_MINIMUM = 10;
	private final int SPEED_STEP = 10;
	private final int SPEED_INIT = 500;
	private int speed = SPEED_INIT;
	
	private Timer timer;
	private JIconButton b_faster;
	private JIconButton b_slower;
	private JLabel label_speed;
	
	public JAnimationOption() {
		this.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel labelAnimation = new JLabel("Animation");
		labelAnimation.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(labelAnimation);
		
		JPanel panel_2 = createAnimationBar();
		add(panel_2);
		
		JPanel panel_3 = createTimePanel();
		add(panel_3);
		
		JSeparator separator = new JSeparator();
		add(separator);	
		
		JLabel labelSlides = new JLabel("Slides");
		labelSlides.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(labelSlides);
		
		JPanel panel_4 = createSlideButtonBar();
		add(panel_4);
		
	}
	
	private JPanel createAnimationBar() {
		JPanel panel = new JPanel();
		
		JOperationButton b_first = new JOperationButton("");
		b_first.setOperation(new O_SelectFirstSlide());
		b_first.setIcons("button_ani_first.png", "button_ani_first_hover.png");
		b_first.setSizeToIconSize(5);
		b_first.setToolTipText("Select first");
		panel.add(b_first);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		
		JOperationButton b_stop = new JOperationButton("");
		b_stop.setIcons("button_ani_stop.png", "button_ani_stop_hover.png");
		b_stop.setSizeToIconSize(5);
		b_stop.setOperation(new O_StopAnimation());
		b_stop.setToolTipText("Stop animation");
		buttonGroup.add(b_stop);
		panel.add(b_stop);
		
		JIconButton b_start = new JIconButton("");
		b_start.setIcons("button_ani_start.png", "button_ani_start_hover.png");
		b_start.setSizeToIconSize(5);
		b_start.setToolTipText("Start animation");	
		b_start.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				new O_StartAnimation(speed).run();				
			}
		});
		buttonGroup.add(b_start);
		panel.add(b_start);
		
		JOperationButton b_last = new JOperationButton("");
		b_last.setOperation(new O_SelectLastSlide());
		b_last.setToolTipText("Select last");
		b_last.setIcons("button_ani_last.png", "button_ani_last_hover.png");
		b_last.setSizeToIconSize(5);
		panel.add(b_last);
		
		return panel;
	}
	
	private JPanel createTimePanel() {
		JPanel panel = new JPanel();
		b_faster = new JIconButton("");
		b_faster.setIcons("button_minus.png", "button_minus_hover.png");
		b_faster.setSizeToIconSize(5);
		b_faster.setToolTipText("Faster");
		panel.add(b_faster);
		
		label_speed = new JLabel();
		label_speed.setHorizontalAlignment(SwingConstants.CENTER);
		updateSpeedText();
		label_speed.setPreferredSize(new Dimension(100, 20));
		panel.add(label_speed);
		
		b_slower = new JIconButton("");
		b_slower.setIcons("button_plus.png", "button_plus_hover.png");
		b_slower.setSizeToIconSize(5);
		b_slower.setToolTipText("Slower");
		panel.add(b_slower);
		
		SpeedListener listener = new SpeedListener();
		b_faster.addMouseListener(listener);
		b_slower.addMouseListener(listener);
		
		return panel;
	}
	
	private JPanel createSlideButtonBar() {
		JPanel panel = new JPanel();
		
		JOperationButton b_addSlide = new JOperationButton("");
		b_addSlide.setIcons("button_add.png", "button_add_hover.png");
		b_addSlide.setSizeToIconSize(5);
		b_addSlide.setToolTipText("Add new");
		b_addSlide.setOperation(new O_AddEmptyGraph());
		panel.add(b_addSlide);
		
		JOperationButton b_duplicate = new JOperationButton( "");
		b_duplicate.setIcons("button_duplicate.png", "button_duplicate_hover.png");
		b_duplicate.setSizeToIconSize(5);
		b_duplicate.setToolTipText("Duplicate selected");
		b_duplicate.setOperation(new O_DuplicateSelectedGraph());
		panel.add(b_duplicate);
		
		JOperationButton b_deleteSlide = new JOperationButton("");
		b_deleteSlide.setIcons("button_remove.png", "button_remove_hover.png");
		b_deleteSlide.setSizeToIconSize(5);
		b_deleteSlide.setToolTipText("Delete selected");
		b_deleteSlide.setOperation(new O_DeleteSelectedGraph());
		panel.add(b_deleteSlide);
		
		return panel;
	}	
	
	
	private class SpeedListener extends MouseAdapter implements ActionListener {
		private final int TICK = 40;
		private ActionListener subtractor;
		private ActionListener adder;
		
		public SpeedListener() {
			subtractor = new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent arg0) {
					subtractSpeed();
					updateSpeedText();					
				}
			};
			
			adder = new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent e) {
					addSpeed();
					updateSpeedText();					
				}
			};
		}
		
		
		@Override
		public void mousePressed(MouseEvent e) {
			if(e.getSource() == b_faster) {
				timer = new Timer(TICK, subtractor);
				timer.start();
			}
			if(e.getSource() == b_slower) {
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
			if(e.getSource() == b_faster) {
				subtractSpeed();
				updateSpeedText();
			}
			if(e.getSource() == b_slower) {
				addSpeed();
				updateSpeedText();
			}
		}	
		
		
	}	

	private void subtractSpeed() {
		speed = Math.max(speed - SPEED_STEP, SPEED_MINIMUM);
	}
	
	private void addSpeed() {
		speed += SPEED_STEP;
	}
	
	private void updateSpeedText() {
		label_speed.setText(speed + " ms");
	}
}
