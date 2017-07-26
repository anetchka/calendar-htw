package controller;
/**
 * @author Anna Kovtun s0552342
 * @version Eclipse Neon
 * @since 20.06.2017
 * Letzte Aenderung: 20.06.2017 
 * Dateiname: ActionListenerJahresPLanerShow.java
 * Beschreibung: eine Klasse, die die ActionListener implementiert 
 * 
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.MeinFenster;

public class ActionListenerJahresPlanerShow implements ActionListener {
	//Attribut
	private MeinFenster mfenster = null;
	//Konstruktor
	public ActionListenerJahresPlanerShow(MeinFenster mfenster) {
		
		this.mfenster = mfenster;
		
	}
	
	/**
	 * @param e ActionEvent
	 * Die Funktion erzeugt ein neues Fenster fuer das Jahresplaner.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		mfenster.zeigeJahreSplanerFenster();
	}
}
