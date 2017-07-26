package io;

import java.util.InputMismatchException;
import java.util.Scanner;

	/**
	  * @author Anna Kovtun s0552342
	  * @version Eclipse Neon
	  * @since 10.05.2017 
	  * Letzte Aenderung: 21.05.2017 
	  * Dateiname: Eingabe.java
	  * Beschreibung: eine Klasse, die die Userseingabe verarbeitet
	 */

public class Eingabe {

	// Attribute
	private static final int  GANZESJAHR = 1;
	private static final int  MONAT = 2;
	private static final int  MITMONTAGBEGINNEN = 3;
	private static final int  JAHRESPLAN = 4;
	private static final int  MENUEAUSWAHL = 5;
	private static final int  DATEISPEICHERN = 6;
	private static Scanner scan = new Scanner(System.in);
	
	/**menuePunkt
	 * 
	 * @return (int) Auswahl vom Menue Punkte - von o bis 6
	 * 
	 * Eine Funtkion, die User Menueauswahl zurueckgibt 
	 */
	public static int menuePunkt() {
		String meldung = "Bitte Menupunkte auswaehlen";
		int ergebnis = userEingabeLesen(MENUEAUSWAHL, 0, 6, meldung);
		return ergebnis;
	}
	/** liesJahr
	  * 
	  * @return (int) Gibt einen Jahr ab 1582 zurueck
	  * 
	  * Die Methode , die eine Jahreszahl vom User zurueck gibt
	  * 
	 */
	public static int liesJahr() {
		String meldung = "Bitte eine Jahreszahl eingeben(ab 1582).";
		int jahr = userEingabeLesen(GANZESJAHR, 1582, Integer.MAX_VALUE, meldung);
		return jahr;
	}
	
	/** liesMonat
	  *
	  * @return (int) Gibt einen Monat als Zahl zurueck (1 = Januar, 2 = Februar .....) 
	  * 
      * Die Methode , die eine Monatszahl vom User zurueck gibt
	 */
	public static int liesMonat() {
		String meldung = "Bitte geben Sie eine Monatszahl ein (von 1 bis incl 12) ";
		int monat = userEingabeLesen(MONAT, 1, 12, meldung);
		return monat;
	}
	
	/**liesZweitesMonat
	 * 
	 * @param von (int) Monat von 1 bis 12 : 1 = Januar, 2 = Februar....
	 * 
	 * @return (int)  Gibt einen Monat als Zahl zurueck (1 = Januar, 2 = Februar .....) 
	 * 
	 * Die Methode , die eine Monatszahl vom User zurueck gibt
	 */
	public static int liesZweitesMonat (int von) {
		String meldung = "Bitte geben Sie zweite Monatszahl ein (von 1 bis incl 12 und groesser/gleich  "+ von  + " )";
		int zweitesMonat = userEingabeLesen(MONAT, von, 12,  meldung);
		return zweitesMonat;
	}
	
	public static int liesJahresPLaner() {
		String meldung = "Geben Sie [j], falls Jahresplaner mit Events sein soll" +
		System.lineSeparator() + "oder [n], falls ohne Events";
		int jahresPlaner = userEingabeLesen(MITMONTAGBEGINNEN, (int)'j', (int)'n', meldung);
		return jahresPlaner == (int) 'j' ? 1 : -1;
	}
	/** liesWochenTag
	  * 
	  * @return (int) Gibt einen 0 (fuer Sonntag) oder 1 (Montag) zurueck
	  * 
	  * Eine Methode, die gibt entweder 1 oder 0 zurueck
	 */
	public static int liesWochenTag() {
		// 0 - Sontag, 1 Montag
		String meldung = "Geben Sie [j], falls Kalendarblatt mit Montag bedinnen soll" +
		System.lineSeparator() + "oder [n], falls mit Sontag";
		int wochenTag = userEingabeLesen(MITMONTAGBEGINNEN, (int)'j', (int)'n', meldung);
		//if/else Bedinung
		return wochenTag == (int) 'j' ? 1 : 0;
	}
	
	/**liesDateiSpeichern
	 * 
	 * @return (int) gibt entweder 1 oder 0 zurueck (1 - Datei speichern, 0 - nicht speichern)
	 * 
	 * Eine Methode, die gibt entweder 1 oder 0 zurueck
	 */
	public static int liesDateiSpeichern() {
		String meldung = "Moechten Sie die Datei speichern? (j/n)";
		int dokumentSpeichern = userEingabeLesen(DATEISPEICHERN, (int)'j', (int) 'n', meldung);
		//if/else Bedinung
		return dokumentSpeichern == (int) 'j' ? 1 : 0;
	}
	
	/** dateinamenEingeben
	 * 
	 * @return(String) Dateiname, der User eingegeben hat
	 * 
	 * Eine Funktion, die den Usernamen fuer die Datei zurueck gibt
	 */
	public static String dateinamenEingeben() {
		
		System.out.println("Geben Sie die Dateinamen");
		String userEingabe = scan.nextLine();
		if (userEingabe.isEmpty()) {
			userEingabe = "";
		}
		return userEingabe;
	}

	/** liesUserEingabe
	  * 
	  * @param meldung(String) Eine Anweisung fuer den User
	  * @param einMenuePunkt - Menue Punkt von 0 bis 6
	  * @return eine Zahl, der User eingegeben hat
	  * ----------------
	  * Eine Hilfsmethode, der die Usereingabe einliest, und falls der user etwas anderes als int oder char eigegeben hat, 
	  * wird eine InputMismatchException gefangen und der User wird darum gebeten, den Zahl oder den char nochmal einzugeben.
	  * Das laeuft solange, bis der User einen int/char nicht eingibt. 
	  * Wenn das der Fall ist, wird diesen integer zurueckgegeben.
	 */
	private static int userEingabePruefen(String meldung, int einMenuePunkt) {
		int ergebnis = -1;
		boolean exit = false;
		//Aufruf einer Methode zeigeMeldung, um die "meldung" zu zeigen
		Ausgabe.zeigeMeldung(meldung);
		//solange "exit" false ist
		while (!exit) {
			try {
				// Fall, wenn char und nicht int ausgewertet werden muss
				if (einMenuePunkt == MITMONTAGBEGINNEN || einMenuePunkt == JAHRESPLAN || einMenuePunkt == DATEISPEICHERN) {
					// char als int casten
					ergebnis = (int)scan.next().charAt(0);
				} else {
					// int einlesen
					ergebnis = scan.nextInt();
				}
			} catch (InputMismatchException e) {
				Ausgabe.zeigeMeldung("Fehlertyp: " + e.toString() + " . " + meldung);
				// in die while schleife zurueck gehen
				continue;
			}
			exit = true;
		}
		if (scan.hasNextLine()) {
			scan.nextLine();
		}
		return ergebnis;
	}
	
	/**userEingabeLesen
	 * 
	 * @param einMenuePunkt(int) entspicht den finalen Attributen(GANZESJAHR, ....) 
	 * @param von (int) - Relevant fuer die gueltige Ueberpruefung von gueltigem Bereich
	 * @param bis (int) - Relevant fuer die gueltige Ueberpruefung von gueltigem Bereich
	 * @param meldung (String) die Meldung, was auf der Console gezeigt wird
	 * @return (int) eine entspechende Zahl fuer z.B Monat/Jahr/Jahr mit Feierntagen u.s.w
	 * 
	 * Eine FUnktion, die nimmt als Parameter die gueltigen Bereichen, eine Meldung, was dem User sagt, was er/sie eingeben muss
	 * und eine Menuepunkt. Diese Funktion ueberprueft, ob der User eine Zahl in gueltigem Bereich eingegeben hat. Wenn ja, 
	 * wird diese Zahl zurueckgegeben. Wenn nein, bleibt der User in der Schleife, solange eine gueltige Zahl eingegeben wird
	 */
	public static int userEingabeLesen(int einMenuePunkt, int von, int bis, String meldung) {
		boolean exit = false;
		int ergebnis = -1;
		boolean vergleich = false;
		//solange exit alse ist
		while(!exit) {
			// validierung vom User eingaben
			ergebnis = userEingabePruefen(meldung, einMenuePunkt);
			// je nach dem MenuePunkt sind verschieden Bedinungen auszufuellen
			switch(einMenuePunkt) {
			case GANZESJAHR:
				vergleich = ergebnis < von;
				break;
			case MONAT:
				vergleich = ergebnis < von || ergebnis > bis;
				break;
			case MITMONTAGBEGINNEN: 
				vergleich = ergebnis != von && ergebnis != bis;
				break;
			case JAHRESPLAN: 
				vergleich = ergebnis < von || ergebnis > bis;
				break;
			case DATEISPEICHERN: 
				vergleich = ergebnis != von && ergebnis != bis;
				break;
			case MENUEAUSWAHL: 
				vergleich = ergebnis < von || ergebnis > bis;
				break;
			default: 
				break;
			}
			if (vergleich) {
				Ausgabe.zeigeMeldung("Fehler! ");
				// in die while schleife zurueck gehen, falls ein Fehler durch
				// Usereingabe
				continue;
		}
			exit = true;
	}
		return ergebnis;
	}
}

