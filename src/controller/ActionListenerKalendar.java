package controller;
/**
 * @author Anna Kovtun s0552342
 * @version Eclipse Neon
 * @since 18.06.2017
 * Letzte Aenderung: 18.06.2017 
 * Dateiname: ActionListenerKalendar.java
 * Beschreibung: eine Klasse, die die ActionListener implementiert 
 * und zwar die Kalendar im Hauptfenster erneut
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import gui.MeinFenster;

public class ActionListenerKalendar implements ActionListener {
	//Attribute
	private MeinFenster mfenster = null;
	//Konstruktor
	public ActionListenerKalendar(MeinFenster mfenster) {
		super();
		this.mfenster = mfenster;
	}
	/**
	 * @param event ActionEvent
	 * Die Funktion erneut die Kalendar im Hauptfenster
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		mfenster.refreshCalendar();
	}
}
