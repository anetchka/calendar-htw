package io;

/**
 * @author Anna Kovtun s0552342
 * @version Eclipse Neon
 * @since 03.07.2017
 * Letzte Aenderung: 03.07.2017 
 * Dateiname: DateiSpeicher.java
 * Beschreibung: eine Klasse zum Speichern vom Dateien
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DateiSpeicher {
	/**dateiSpeichern
	 * 
	 * @param menueAuswahlt (int) von 0 bis 6 ink - User Auswahl in der Menue
	 * @param name (String) - Datei Name zum Speichern(das ist eine Default Wert)
	 * @param text (String) - text zum Speichern
	 * @param dateiSpeichernJaNein (int) - (int) j fuer "ja", (int) n fuer "nwin"
	 * 
	 * @return speichert das Dokument mit default name oder mit den Eingegebenen von User Namen
	 */
	public static String dateiSpeichern(int menueAuswahlt, String name, String text, int dateiSpeichernJaNein) {
		// einlesen, ob die Datei gespeichert werden soll oder nicht
		dateiSpeichernJaNein = Eingabe.liesDateiSpeichern();
		// falls gespeichert werden soll, den User Fragen, den Namen einzugeben und diesen Namen speichern
		String dateiname = name;
		if (dateiSpeichernJaNein == 1) {
			name = Eingabe.dateinamenEingeben();
			if (!name.equals("")) {
				name += ".txt";
			} else {
				name = dateiname;
			}
		} 
		File datei = new File(name);
		BufferedWriter out = null;
		// Stream oeffnen
		try {
			out = new BufferedWriter(new FileWriter(datei));
		} catch (IOException e) {
			System.out.println("Fehler Datei = " + name + " konnte nicht geoeffnet werden");
			e.printStackTrace();
			// Aufhoeren, falls Datei konnte nicht gespiechert werden
			
		}
		try{
			//Datei schreiben
			out.write(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			//Stream schliessen
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// monatBlattZumSpeichern leer machen
		return "";
	}
}
