package controller;
/**
 * 
 * @author Anna Kovtun s0552342
 * @version Eclipse Neon
 * @since 17.06.2017
 * Letzte Aenderung: 17.06.2017 
 * Dateiname: ActionListenerBeenden.java
 * Beschreibung: eine Klasse, die die ActionListener implementiert
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;


public class ActionListenerBeenden implements ActionListener {
	/**
	 * @param event - ActionListener
	 * 
	 * Eine Funktion, die das GUI Application schliesst
	 */
	
	@Override
	public void actionPerformed(ActionEvent event) {
		System.exit(JFrame.EXIT_ON_CLOSE);
	}
}
