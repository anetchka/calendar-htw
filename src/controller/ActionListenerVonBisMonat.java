package controller;
/**
 * @author Anna Kovtun s0552342
 * @version Eclipse Neon
 * @since 21.06.2017
 * Letzte Aenderung: 21.06.2017 
 * Dateiname: ActionListenerVonBisMonat.java
 * Beschreibung: eine Klasse, die die ActionListener implementiert 
 * und jahresplanerfenster je nach MOnaten erneut
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.JahresPlanerFenster;

public class ActionListenerVonBisMonat implements ActionListener {

	//Attribute
	private JahresPlanerFenster jfenster = null;
	//Konstruktor
	public ActionListenerVonBisMonat(JahresPlanerFenster jfenster) {
		super();
		this.jfenster = jfenster;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		jfenster.refresh();

	}
}
