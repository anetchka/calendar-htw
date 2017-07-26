package controller;
/**
 * @author Anna Kovtun s0552342
 * @version Eclipse Neon
 * @since 20.06.2017
 * Letzte Aenderung: 20.06.2017 
 * Dateiname: ActionListenerAutor.java
 * Beschreibung: eine Klasse, die die ActionListener implementiert 
 * und zwar zeigt info ueber das Projekt im eigenem Fenster
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import gui.MeinFenster;

public class ActionListenerAutor implements ActionListener {

	private MeinFenster mfenster = null;
	
	public ActionListenerAutor(MeinFenster mFenster) {
		this.mfenster = mfenster;
	}
	/**
	 * @param e - Action Event
	 * 
	 * Eine Funktion, die die Informaion ueber das Projekt im Dialogpanel zeigt
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(mfenster, "This program was developed by Anna Kovtun."
				+ System.lineSeparator() + "It took her 3 months to finish this project." +
				System.lineSeparator() + "The Eclipse version used for this project is Neon", "About the Programm", JOptionPane.INFORMATION_MESSAGE);
	}

}
