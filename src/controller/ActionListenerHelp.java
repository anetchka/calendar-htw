package controller;
/**
 * @author Anna Kovtun s0552342
 * @version Eclipse Neon
 * @since 20.06.2017
 * Letzte Aenderung: 20.06.2017 
 * Dateiname: ActionListenerHelp.java
 * Beschreibung: eine Klasse, die die ActionListener implementiert 
 * und zwar zeigt info ueber die Bedienung vom Programm
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import gui.MeinFenster;

public class ActionListenerHelp implements ActionListener {

	private MeinFenster mfenster = null;
	
	public ActionListenerHelp(MeinFenster mFenster) {
		this.mfenster = mFenster;
	}
	
	/**
	 * @param e - Action Event
	 * 
	 * Eine Funktion, die die Hilfe zur Bedienung vom Programm im Dialogpanel zeigt
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(mfenster, "This program shows you the calendar and you acn choose"
				+ "either a calendar for the whole year of just for a specific month. "
				+ System.lineSeparator() +  "All you have to do is to choose the month or the year you are itnerested in from the list."
				+ System.lineSeparator() +"Also you have the possibility to show your Jahresplaner for the whole year."
				+ System.lineSeparator() +"This program is very easy to use. "
				+ System.lineSeparator() + "You can see on the menue bar different menus such as: Datei, Kalendar, Jahresplaner, Look&Feel and Info. "
				+ System.lineSeparator() +"In general you just have to click on the item you are interested in. "
				+ System.lineSeparator() + "It took her 3 months to finish this project." +
				System.lineSeparator() + "The Eclipse version used for this project is Neon", "Help", JOptionPane.INFORMATION_MESSAGE);
	}

}
