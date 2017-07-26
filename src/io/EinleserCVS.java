package io;
/**
 * @author Anna Kovtun s0552342
 * @version Eclipse Neon
 * @since 03.07.2017
 * Letzte Aenderung: 03.07.2017 
 * Dateiname: EinleserCVS.java
 * Beschreibung: eine Klasse zum Einlesen CSV Datei
 */
import kalendar.Event;
import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import kalendar.KalendarFunktionen;

public class EinleserCVS {
	/**
	 * 
	 * @param dateiname Name des Datei
	 * @return HashMap mit Namen von Feiertagen und dessen Julianisches Datum
	 * 
	 * Die Funktion liest eine Datei mit Geburtstagen und speichert diese Geburtstage in HashMap
	 */
	public static HashMap<Long, Event> lesenCVS(String dateiname) {
		File f = new File(dateiname);
		BufferedReader in = null;
		String zeile = "";
		HashMap<Long, Event> geburtstageMap = new HashMap<Long, Event>();
		try {
			in = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			while((zeile = in.readLine()) != null) {
				String[] zeilen = zeile.split("\\;");
				long date = baueDatum(zeilen[0]);
				String vorname = zeilen[1];
				String name = zeilen[2];
				String eventName = vorname + " " + name;
				Event geburtstagEvent = new Event(eventName, date);
				geburtstageMap.put(date, geburtstagEvent);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return geburtstageMap;
	}

	/**
	 * 
	 * @param zeile Gesplitete Zeile
	 * @return Julianisches Datum
	 * 
	 * Die Funktion liest ein String, der hat so ein Format "01.01.1999", 
	 * splitet String nach Punkt und gibt den Wert von diesem String als Julianische Datum
	 */
	private static long baueDatum(String zeile) {
		
		String[] datum = zeile.split("\\.");
		
		int day = Integer.valueOf(datum[0]);
		
		int month = Integer.valueOf(datum[1]);
		
		int year = Integer.valueOf(datum[2]);
		
		long datumLong = new KalendarFunktionen().julian_date(day, month, year);
		return datumLong;
	}
}
