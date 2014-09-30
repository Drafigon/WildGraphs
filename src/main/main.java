package main;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;


import modi.M_Create;
import operations.O_AddEmptyGraphTab;
import operations.O_ChangeModus;
import data.Collector;

public class main {

	public static void main(String[] args) {
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // standard look
		}
			
		runGrapheditor();
	}
	
	public static void runGrapheditor() {
		new O_AddEmptyGraphTab().run();
		new O_ChangeModus(M_Create.getInstance()).run();		
		Collector.getWindow().setVisible(true);
	}
}
