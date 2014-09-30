package modi;

import javax.swing.JPanel;

import modi.indicators.I_Nothing;
import modi.indicators.Indicator;

public class M_DoNothing extends Modus {

	private static final M_DoNothing INSTANCE = new M_DoNothing();

	private M_DoNothing() {};
	
	@Override
	public void updateValuesFromPanel() {
	}

	@Override
	public JPanel getOptionPanel() {
		return new JPanel();
	}

	@Override
	public Indicator getIndicator() {
		return new I_Nothing();
	}
	
	@Override
	public String getDescription() {
		return "Do Nothing";
	}

	public static M_DoNothing getInstance() {
		return INSTANCE;
	}
}
