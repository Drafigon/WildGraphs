package operations;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import data.Collector;

public class O_Info implements Operation {

	@Override
	public void run() {
		System.out.println("Graph: " + Collector.getGraph());
		
		JTextArea area = new JTextArea(Collector.getGraph().toString());
		area.setEditable(false);
		Object[] options = { "OK"}; 
		
		JOptionPane.showOptionDialog(
				null, 
				area, 
				"Graph information", 
				JOptionPane.OK_OPTION, 
				JOptionPane.INFORMATION_MESSAGE, 
				null, 
				options, 
				null);
		
	}

}
