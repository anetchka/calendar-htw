package controller;
/**
* @author Anna Kovtun s0552342
* @version Eclipse Neon
* @since 20.06.2017
* Letzte Aenderung: 21.06.2017 
* Dateiname: ActionListenerFeiertagenOhneSaSo.java
* Beschreibung: eine Klasse, die die ActionListener implementiert 
* und zwar zeigt alle Feiertagen ohne Sonntag und Samstag fuer das ausgewaehltes Jahr im eigenem Fenster
*/
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import gui.FeiertageFensterOhneSoSa;

public class ActionListenerFeiertagenOhneSaSo implements ActionListener {
	// Attribut
	private JComboBox<Integer> cbJahr;

	// Konstruktor
	public ActionListenerFeiertagenOhneSaSo(JComboBox<Integer> cbJahr) {
			this.cbJahr = cbJahr;
		}

	/**
	 * @param event - Action Event
	 * 
	 * Eine Funktion die das Fenster fuer die Feiertagen ohne Sonntag und Samstag erzeugt.
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		FeiertageFensterOhneSoSa ffenster = new FeiertageFensterOhneSoSa(cbJahr);
	}

}
