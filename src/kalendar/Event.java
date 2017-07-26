package kalendar;

import kalendar.KalendarFunktionen;
/**
 * @author Anna Kovtun s0552342
 * @version Eclipse Neon
 * @since 10.05.2017 
 * Letze Aenderung: 25.05.2017
 * Dateiname: Event.java 
 * Beschreibung: eine Klasse, die Names des Feiertages und dessen Julianisches Datum hat
 */

public class Event {
	private String name; // Name des Feiertages
	private long datum; // Julianisches Datum
	private int[] periodischesInterval = {1, 2, 3};// 1- wochentlich, 2 - monatlich, 3 - jaehrlich

	//Konstruktor
	public Event(String name, long datum) {
		super();
		this.name = name;
		this.datum = datum;
	}
	
	// Konstruktor mit Julianisches Datum
	public Event(String name, int tag, int monat, int jahr) {
		super();
		this.name = name;
		this.datum = new KalendarFunktionen().julian_date(tag, monat, jahr);
	}
	
	/**getName
	 * 
	 * @return (String) Name des Feiertages
	 * 
	 * Gibt den Name des Feiertages zurueck
	 */
	public String getName() {
		return name;
	}
	
	/**setName
	 * 
	 * @param name (String) name des Feiertages
	 * 
	 * Name des Feiertages eingeben
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**getDatum
	 * 
	 * @return (long) Julianisches Datum
	 * 
	 * Gibt ein Julianisches Datum zurueck
	 */
	public long getDatum() {
		return datum;
	}
	
	/**setDatum
	 * 
	 * @param datum (long) Julianisches Datum
	 * 
	 * Julianisches Datum eingeben
	 */
	public void setDatum(long datum) {
		this.datum = datum;
	}
	
	
}
