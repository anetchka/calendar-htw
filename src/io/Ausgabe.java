package io;
	/**
	  * @author Anna Kovtun s0552342
	  * @version Eclipse Neon
	  * @since 21.04.2017
	  * Letzte Aenderung: 24.05.2017 
	  * Dateiname: Ausgabe.java
	  * Beschreibung: eine Klasse, die verschiedene Meldungen(abhaengig von Users Aktions) auf Console ausgibt 
	 */

public class Ausgabe {
	
	/** menueOut
	 * 
	 * Gibt auf dem Console 7 Optionen zum Auswaehlen aus
	*/
	public static void menueOut(){
		System.out.println();
		System.out.println("[1] Kalendar fuer das ganze Jahr");
		System.out.println("[2] Ausgabe Monatsblatt");
		System.out.println("[3] Kalendar mit Feiertagen");
		System.out.println("[4] Ausgabe Monatsblatt mit Feiertagen");
		System.out.println("[5] Kalendarblatt mit Mo beginnend(j/n)");
		System.out.println("[6] Jahresplaner mit Events(j/n)");
		System.out.println("[0] Beenden");
		System.out.println();
	}
	
	/** exitMessage
	  * 
	  * Eine Methode, die eine Message "Tschuess" ausgibt
	 */
	public static void exitMessage() {
		System.out.println("Tschuess!");
	}
	
	/** zeigeMeldung
	  * 
	  * @param text(String) Die uebergebende Meldung
	  * 
	  * Gibt eine uebrgebende Meldung auf das Console aus
	 */
	
	public static void zeigeMeldung(String text) {
		System.out.println(text);
	}
}
