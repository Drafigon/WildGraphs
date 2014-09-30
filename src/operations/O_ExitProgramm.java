package operations;

import javax.swing.JOptionPane;

public class O_ExitProgramm implements Operation {

	@Override
	public void run() {
		int result = JOptionPane.showConfirmDialog(null,
				"You really want to close the editor?\n All unsaved files will be lost.", 
				"Are you sure?", 
				JOptionPane.YES_NO_OPTION);
		
		if(result == JOptionPane.OK_OPTION) {
			System.exit(0);		
		}
	}
}
