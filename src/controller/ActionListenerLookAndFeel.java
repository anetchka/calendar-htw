package controller;
/**
 * @author Anna Kovtun s0552342
 * @version Eclipse Neon
 * @since 17.06.2017
 * Letzte Aenderung: 17.06.2017 
 * Dateiname: ActionListenerLookAndFeel.java
 * Beschreibung: eine Klasse, die die ActionListener implementiert 
 * und Look&Feel aendert
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import gui.MeinFenster;


public class ActionListenerLookAndFeel implements ActionListener {
	private MeinFenster mfenster = null;
	
	public ActionListenerLookAndFeel(MeinFenster mfenster) {
		this.mfenster = mfenster;
	}
	
	/**
	 * @param event(ActionEvent)
	 * 
	 * Eine Funktion, die LookAndFeel aendert(Metal, Motif oder Windows)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		try {
			//Durch Look&Feel iterieren
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				//Falls den name vom Look and Feel vom actionCommand identisch
				//mit dem Name vom Look and Feel im UIManager ist
				if (info.getName().contains(event.getActionCommand())) {
					UIManager.setLookAndFeel(info.getClassName());
					SwingUtilities.updateComponentTreeUI(mfenster);
					mfenster.pack();
					break;
				}
			} 	
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
