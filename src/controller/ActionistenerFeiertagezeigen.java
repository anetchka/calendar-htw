package controller;
/**
 * @author Anna Kovtun s0552342
 * @version Eclipse Neon
 * @since 20.06.2017
 * Letzte Aenderung: 21.06.2017 
 * Dateiname: ActionistenerFeiertagezeigen.java
 * Beschreibung: eine Klasse, die die ActionListener implementiert 
 * und zwar zeigt alle Feiertagen fuer das ausgewaehltes Jahr im eigenem Fenster
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import gui.FeiertageFenster;

public class ActionistenerFeiertagezeigen implements ActionListener {

	//Attribut
	private JComboBox<Integer> cbJahr;
	
	//Konstruktor
	public ActionistenerFeiertagezeigen(JComboBox<Integer> cbJahr) {
		this.cbJahr = cbJahr;
	}
	/**
	 * @param event - Action Event
	 * 
	 * Eine Funktion die das Fenster fuer die Feiertagen erzeugt.
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		FeiertageFenster ffenster = new FeiertageFenster(cbJahr);
	}

}
