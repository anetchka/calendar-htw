package kalendar;
/**
 * @author Anna Kovtun s0552342
 * @version Eclipse Neon
 * @since 10.05.2017 
 * Letze Aenderung: 25.05.2017
 * Dateiname: Jahresplaner.java 
 * Beschreibung: 
 */
import java.util.HashMap;
import java.util.LinkedList;

public class Jahresplaner {
	private KalendarFunktionen kf = new KalendarFunktionen();
	private LinkedList<LinkedList<String>> planliste = new LinkedList<LinkedList<String>>();

	private int jahr; // Default aktuelles Jahr
	private boolean modus; // true fuer FeiertAGEN

	//Konstruktor
	public Jahresplaner(int jahr) {
		this.jahr = jahr; 
	}
	
	//Konstruktor parameterlose
	public Jahresplaner() {
		this.jahr = 2017;
	}
	
	//Konstruktor
	public Jahresplaner(int  jahr, boolean anzeigeFeiertage) {
		this(jahr);
		this.modus = anzeigeFeiertage; 
	}

	/** baueMonat
	 * 
	 * @param monat (int) - 1= jan bis 12= dez
	 * @return LinkedList<String> der "Monatsplan" - Container mit allen Zeilen des Monats
	 * 
	 * 	 * der angegebene Monat fuer den Jahresplan wird zusammengebaut in der Form
	 * Mai 2017 Mo|01| |121 Di|02| |122 Mi|03| |123 usw. Di|30| |150 Mi|31| |151
	 * Jede Zeile wird als String in einer LinkedList abgespeichert
	 */
	public LinkedList<String> baueMonat(int monat, HashMap<Long, Event> map) {
		Kalendar kal = Kalendar.getInstance();
		//Monatslaenge bekommen
		int monatslaenge = kal.getMonatLaeng(monat, jahr);
		//Tagesnummer von 1 bis 365/366 bekommen
		int tagesnummer = kf.tagesnummer(1, monat, jahr);
		//hier werden alle Strigns gespeichert
		LinkedList<String> monatsBlatt = new LinkedList<String>();
		// entspricht den Nummer von tagen im Monat (von 1 bis 28 oder 29 oder 30 oder 31 je nach edm Monat)
		int counter = 1;
		// ob es Sonntag, Montag ... ist
		int wochenTagImJahr = kf.wochentag_im_jahr(jahr, tagesnummer);
		// Den Namen von Monat in LinkedList als erster Element speichern
		monatsBlatt.add(kal.MONATEN[monat-1] + " " + jahr);
		// fuer ein Monat: mit tagesnummer anfangen und die monatslaenge mal durchlaufen 
		for (int i = tagesnummer; i < tagesnummer + monatslaenge; i++) {
			// falls WochenTagImJahr mehr als 7 ist (mehr als Samstag == 6), dann ist Wochentag wieder Sonntag ist (entspricht 0)
			if (wochenTagImJahr == 7) {
				wochenTagImJahr = 0;
			}
			// Den Namen von wochenttagImJahr bekommen
			String wochenTagName = kal.TAGEN[wochenTagImJahr];
			// falls User mit feiernTagen moechte und falls dieses Tag feiertag ist
			if (modus && kal.istFeierTag(counter, monat, jahr)) {
				// datum bekommen
				long datum = kf.julian_date(counter, monat, jahr);
				// Value aus der HasMap holen
				Event value = map.get(datum);
				// Name von diesem Value bekommen
				String nameDesFeiertages = value.getName();
				// In LinkedList speichern
				monatsBlatt.add(String.format("%s|%02d| %-20s|%2d", wochenTagName, counter, nameDesFeiertages, i));

			} else {
				// falls ohne Feiertagen, einfach Wochentag (So,Mo, Di...),
				// Tag (1, 2,... bis 28/29/30/31), leerzeichen und tagesnummer in LinkedList speichern
				monatsBlatt.add(String.format("%s|%02d|%21s|%2d", wochenTagName, counter, "", i));
			}
			counter++;
			wochenTagImJahr++;
		}
		return monatsBlatt;
	}

	/**
	 * Der Jahresplan fuer die angegebenen Monate wird als String zurueck gegeben.
	 * Hinweis zur Implementierung: Die Monatsplaene der angegebenen Monate
	 * werden in einer Container-Klasse LinkedList con LinkedList der
	 * "Planliste" zusammengefasst. Damit erhaelt man eine 2-dimensionale
	 * Datenstruktur. Um den String zusammenzubauen, wird die Datenstruktur so
	 * durchlaufen, dass jeweils die ersten Zeilen aller Monate nebeneinander
	 * ausgegeben werden. Der zurueckgegebene String sollte folgendes Format
	 * haben: Januar 2017 Februar 2017 Maerz 2017 So|01| |1 Mi|01| |32 Mi|01|
	 * |60 Mo|02| |2 Do|02| |33 Do|02| |61 Di|03| |3 Fr|03| |34 Fr|03| |62
	 * Mi|04| |4 Sa|04| |35 Sa|04| |63 Do|05| |5 So|05| |36 So|05| |64 Fr|06| |6
	 * Mo|06| |37 Mo|06| |65 usw.
	 *
	 * @param von (int) - 1= jan bis 12= dez
	 * @param bis (int) - 1= jan bis 12= dez
	 * @return String - der Jahresplan
	 */
	public String gibJahresplan(int von, int bis) {
		Kalendar kal = Kalendar.getInstance();
		//HashMap mit Events generieren
		kal.feierTagenGenerieren(jahr);
		//HashMap mit Events bekommen
		HashMap<Long, Event> map = kal.getFeierTage();
		String ergebnis = "";
		// Die maximale Laenge von Tagen im Monat
		int maxMonatsLaenge = 0;
		LinkedList<String> monatsZeile = new LinkedList<String>();
		for (int i = von; i <= bis; i++) {
			//LinkedList von Monatszeile zu bekommne
			monatsZeile = baueMonat(i, map);
			// Bedinung, um Maximum zu finden
			if (maxMonatsLaenge < monatsZeile.size()) {
				maxMonatsLaenge = monatsZeile.size();
			}
			//2D LinkedList befuellen 
			planliste.add(monatsZeile);	
		}
		// 
		for (int i = 0; i < maxMonatsLaenge; i++) {
			
			for (int j = 0; j < planliste.size(); j++) {
				
				LinkedList<String> einMonat = planliste.get(j);
				// falls monat kleiner als maxMonatsLaenge ist
				if (i < einMonat.size()) {
					// die i-te Zeile aus den j-Monat zu bekommen und im ergebnis speichern
					ergebnis += String.format("%-35s", einMonat.get(i));
				} else {
					// falls monat groesser oder gleich als maxMonatsLaenge ist, leerzeichen einfuegen
					ergebnis += String.format("%35s", " ");
				}
			}
			//mit neuer Zeile anfangen
			ergebnis+=System.lineSeparator();
		}
		planliste.clear();
		return ergebnis;
	}
	
	public LinkedList<LinkedList<String>> getPlanliste() {
		return planliste;
	}

	public void setPlanliste(LinkedList<LinkedList<String>> planliste) {
		this.planliste = planliste;
	}
}
