package kalendar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import io.Ausgabe;
import io.DateiSpeicher;
import io.Eingabe;

/**
 * @author Anna Kovtun s0552342
 * @version Eclipse Neon
 * @since 10.05.2017 
 * Letze Aenderung: 25.05.2017
 * Dateiname: Kalendar.java 
 * Beschreibung: eine Klasse, die ein Kalendar fuer ein Monat oder das ganze Jahr
 * mit oder ohne Feiertagen erstellt
 */

public class Kalendar implements IKalendar {

	// attribute
	public static final String[] TAGEN = { "So", "Mo", "Di", "Mi", "Do", "Fr", "Sa", "So" };
	
	public static final String[] MONATEN = { "Januar", "Februar", "Maerz", "April", "Mai", "Juni", "Juli", "August", "September",
			"Oktober", "November", "Dezember" };
	private final int SPALTELAENGE = 7; // eine Kontante fuer den Anzahl von  Leerzeichen
	

	private boolean anzeigeFeiertage = true; // Ausgabe mit  Feiertagen
	
	private static Kalendar refKalendar = null;
	
	// konstanten fuer menueasuwahl
	private static final int  GANZESJAHR = 1;
	private static final int  MONAT = 2;
	private static final int  JAHRMITFEIERTAGEN = 3;
	private static final int  MONATMITFEIERTAGEN = 4;
	private static final int  KALENDARBLATTMONTAG = 5;
	private static final int  JAHRESPLANER = 6;
	private static final int BEENDEN = 0;
	
	private String monatBlattZumSpeichern = ""; // String, wo Monat gespeichert wird. Den braucht man fuer Dateispeicherung
	
	private int wochentagUser = 0; // 0 entspricht Sonntag; kann 0 oder 1 sein (1 = Montag)

	private int dateiSpeichernJaNein = 1; // Zum Datei Speichern; entweder 1 fuer "ja" oder 0 fuer "nein"
	
	private HashMap <Long, Event> feierTage = new HashMap<Long, Event>(); // HashMap mit Datum und entsprechenden Events
	
	private HashMap <Long, Event> geburtsTage = new HashMap<Long, Event>();
	
	private KalendarFunktionen kalendFunkt = new KalendarFunktionen();
	
	private ArrayList<String> feierTageImMonat = new ArrayList<String>(); // alle Feiertage in jeweiligem Monat

	private Kalendar() {
	}
	
	public static Kalendar getInstance() {
		if (refKalendar == null) {
			refKalendar = new Kalendar();
		} 
		return refKalendar;
	}
	
	/** wochenTagAusgabe
	 * 
	 * @param tagenImMonat(int): Anzahl von Tagen im Monat (entweder 31, 30, 29 oder 28)
	 * @param wochentagImJahr(int): eine Zahl von 0 bis 6. Entspricht 0 = Sonntag, 1 = Montag u.s.w.
	 * @param monat (int): Monat von 1 bis 12
	 * @param jahr(int) : Das Jahr
	 * @return String: die formatierten Zahlen (entsprechen Tagen im Monat)
	 * 
	 * Eine Hilfsfunktion fuer die Methode getMonatsblatt.
	 * Gibt die formatierten Zeihlen (entsprechen Tagen im Monat) als String zurueck
	 */
	private String wochenTagAusgabe(int tagenImMonat, int wochentagImJahr, int monat, int jahr) {
		feierTageImMonat.clear();
		String ergebnis = "";
		// fuer den ersten Tag im Kalendar: "(SPALTELAENGE * wochentagImJahr)": 
		//falls die wochentagImJahr ist nicht Sonntag(entspricht
		// wochentagImJahr = 0), dann entsprechende
		// Anzahl von Leerzeichen einfuegen
		if (wochentagImJahr > 0) {
			ergebnis += String.format("%" + (SPALTELAENGE * wochentagImJahr) + "s", " ");
		}
		// von 1 bis Laenge des Monats
		for (int i = 1; i <= tagenImMonat; i++) {
			// falls es ein feiertag ist 
			if (istFeierTag(i, monat, jahr)) {
				// den Tag mit "*"markieren
				ergebnis += String.format("*%02d*%" + (SPALTELAENGE - 4) + "s", i, " "); // -4 - weil zwei Sterne dazukommen
				// den ummer vom Tag speicehrn (1...28/29/30/31)
				String tag = String.format("%02d", i);
				// den Namen von Feiertag holen
				String feierTagName = getEventName(i, monat, jahr);
				//den Namen vom Feiertag in ArrayList speichern
				feierTageImMonat.add(tag + ". : " + feierTagName);
			} else {
				// falls es kein Feirtag ist, einach den Tagnummer speichern
				ergebnis += String.format(" %02d %" + (SPALTELAENGE - 4) + "s", i, " ");
			}
			
			// Zeilenumbruch abhaengich von der wocheTageImJahr
			if ((i + wochentagImJahr) % 7 == 0) {
				ergebnis += System.lineSeparator();
			}
		}
		ergebnis += System.lineSeparator();
		
		// falls ArrayList nicht leer ist
		if (feierTageImMonat.size() != 0){
			ergebnis += "Im " + MONATEN[monat - 1] + " gibt es folgende Feiertage: " + System.lineSeparator(); 
			// ArrayList mit den Namen von Feiertagen im diesem Monat ausgeben
			for(String s : feierTageImMonat) {
				ergebnis += s + System.lineSeparator();
			}
		}
		return ergebnis;
	}
	
	/**getEventName
	 * 
	 * @param i (int) - entspricht den nummer vom Tag im Monat (von 1 bis 28/29/30/31)
	 * @param monat (int) von 1 bis 12
	 * @param jahr (int) ab 1582
	 * @return (String) Namen des Feiertags im diesem Tag
	 * 
	 * Eine Funktion, die den Namen vom Feiertages im bestimmten Tag ausgibt
	 */
	private String getEventName(int i, int monat, int jahr) {
		String eventName = "";
		// Key generieren
		long givenKey = kalendFunkt.julian_date(i, monat, jahr);
		// durch HashMap gehen
		for (Long key : feierTage.keySet()) {
			// Falls diese Key im HashMap gibt
			if (key == givenKey) {
				Event e = feierTage.get(key);
				// Den Feiertagnamen bekommen
				eventName = e.getName();
			}
		}
		
		return eventName;
	}

	@Override
	/** getMonatsblatt
	 * @param jahr(int) das Jahr zum Monat
	 * @param monat(int) der Monat, das angezeigt werden soll 
	 * @return String - der zusammengebastelte String
	 *  
	 * Beschreibung: Erzeugt ein Monatsblatt des Jahreskalenders und
	 * gibt das Monatsblatt in Stringform zurueck. Das Monatsblatt wird
	 * in einenm String mit Zeilenumbruechen abgelegt. Das Monatsblatt
	 * enthaelt immer die Kopfzeile fuer den entsprechenden Monat.
	 */
	public String getMonatsblatt(int jahr, int monat) {
		String ergebnis = "";
		// "kalendFunkt.tagesnummer(1, monat, jahr)": 1 - weil wir moechten den
		// ertsten Tag des Monats (den uebergeben wird) und des Jahres (wird im Variable "jahr" uebergeben) wissen
		int tagesNummer = kalendFunkt.tagesnummer(1, monat, jahr);
		// da WochenTagImJahr liefert einen int von 0 bis 6 (0 = Sonntag, 1 = Montag u.s.w.) und
		// wochentagUser ist entweder 0 (Sonntag) oder 1 (Monatg), deswegen
		// wochenTagImJahr ist gleich "kalendFunkt.wochentag_im_jahr(jahr,
		// tagesNummer) - wochenTag"
		int wochenTagImJahr = kalendFunkt.wochentag_im_jahr(jahr, tagesNummer) - wochentagUser;
		// falls wochenTag mit Montag anfangen und "kalendFunkt.wochentag_im_jahr(jahr, tagesNummer)"
		// liefert Sonntag zurueck, dann wird Sonntag am Ende der Woche gehen
		if (wochenTagImJahr < 0) {
			wochenTagImJahr = 6;
		}
		int monatLaenge = getMonatLaeng(monat, jahr);
		ergebnis += wochenTagAusgabe(monatLaenge, wochenTagImJahr, monat, jahr) + System.lineSeparator();
		return ergebnis;
	}
	
	/** getMonatLaeng
	 * 
	 * @param monat - monat von 1 bis 12
	 * @param jahr - das Jahr ab 1582
	 * @return (int) die Laenge von uebergebendem Monat
	 * 
	 * Eine Funktion, die gibt zurueck, wie viele Tage ein Monat hat.
	 */
	public int getMonatLaeng (int monat, int jahr) {
		int monatLaenge = -1;
		boolean istSchaltjahr = kalendFunkt.istSchaltjahr(jahr);
		switch (monat) {
		// Monaten mit 31 Tagen
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			return monatLaenge = 31;
		// Monaten mit 30 Tagen
		case 4:
		case 6:
		case 9:
		case 11:
			return monatLaenge = 30;
		case 2:
			// falls das Jahr ist ein Schaltjahr, hat der Monat 29 Tagen
			return istSchaltjahr ? 29 : 28;
		default: 
			return monatLaenge;
		}
	}
	/**
	 * istFeierTag
	 * 
	 * @param tag(int) - von 1 bis 28/29/30/31
	 * @param monat(int) - von 1 bis 12
	 * @param jahr(int) - ab 1582
	 * @return true oder false
	 * 
	 * Eine Funktion, die ueberprueft, ob  modus 1 oder -1 ist und ob das tag, monat, jahr in HashMap event ist.
	 * Falls ja, wierd ein true zurueckgeliefert, nein - false.
	 */
	public boolean istFeierTag (int tag, int monat, int jahr) {
		// Falls ohne Feiertagen ausgegeben werden muss, false zurueckgeben
		if (!anzeigeFeiertage) {
			return false;
		}
		// datum generieren
		long datum = kalendFunkt.julian_date(tag, monat, jahr);
		// uberpruefen, ob es dieses Datum im HasMap gibt
		return feierTage.containsKey(datum);
	}
	
	/**getFeierTage
	 * 
	 * @return(HashMap) HashMap mit den Namen von Feiertagen und dessen julianisches Datum
	 * 
	 * HashMap mit Datum und Events zurueck bekommen 
	 */
	public HashMap<Long, Event> getFeierTage() {
		return feierTage;
	}


	@Override
	/** getKopfzeileMonatsblatt
	 * 
	 * @param jahr(int) - das Jahr zum Monat 
	 * @param monat(int)- der Monat, das angezeigt werden soll 
	 * @return liefert die Kopfzeile komplett als String zurueck
	 *  
	 * Methode, um die Kopfzeile eines
	 * Monatsblattes in Stringform zurueckzugeben. Dieses erfordert die
	 * Uebergabe des Monats und des Jahres als Integer und liefert die
	 * Kopfzeile komplett als String zurueck.
	 * Beispiel: 
	 * ******************* Mai 2016 ******************
        So    Mo    Di    Mi    Do    Fr    Sa
	 * 
	 */
	public String getKopfzeileMonatsblatt(int jahr, int monat) {
		// "monaten[monat - 1 ]" - weil die int "monat" in Wertebereich von 1
		// bis incl 12 ist
		// und die Array monaten faengt mit dem index 0
		String ergebnis = "************  " + String.format("%9s  %d", MONATEN[monat - 1], jahr) + "  ***************" + System.lineSeparator();
		// falls wochenTagUser = 0, dann faengt der Kalendar mit Sonntag an, falls 1 - mit Montag 
		ergebnis += " " + TAGEN[wochentagUser];
		// wir fangen entweder mit "Sontag + 1" oder "Montag + 1" 
		for (int i = wochentagUser + 1; i < wochentagUser + 7; i++) {
			ergebnis += String.format("%" + SPALTELAENGE + "s", TAGEN[i]);
		}
		return ergebnis + System.lineSeparator();
	}

	@Override
    /** zeigeMonat
     * 
     * @param jahr(int) - das Jahr zum Monat 
     * @param monat(int) - der Monat, das angezeigt werden soll 
     * 
     * Methode zur Ausgabe eines Monatsblattes auf der Konsole
     */
	public void zeigeMonat(int jahr, int monat) {
		// KopfZeile bekommen
		String kopfZeileMonatsBlatt = getKopfzeileMonatsblatt(jahr, monat);
		// Monatsblatt bekommen
		String monatsBlatt = getMonatsblatt(jahr, monat);
		// den ganzen Monat im String (attribut) speichern
		monatBlattZumSpeichern += kopfZeileMonatsBlatt + monatsBlatt;
		// die beide durch die "Ausgabe.zeigeMeldung" zeigen
		Ausgabe.zeigeMeldung(kopfZeileMonatsBlatt + System.lineSeparator() + monatsBlatt);
	}

	@Override
    /**zeigeJahr
     * 
     * @param jahr (int) - das Jahr, das angezeigt werden soll
     * 
     * Methode zur Ausgabe aller Monatsblaetter eines Jahres auf der Konsole
     */
	public void zeigeJahr(int jahr) {
		for (int i = 1; i < 13; i++)
			// die Methode zeigeMonat wird 12 mal aufgerufen (entspricht 12 Monaten im Jahr)
			zeigeMonat(jahr, i);
	}

	@Override
    /** liesMonat
     * 
     * @return int - der eingelesene Monat 
     * 
     * Die Methode ruft eine Methode liesMonat aus der Klasse Eingabe und gibt den Wert fuer den Monat als int zurueck
     */
	public int liesMonat() {
		return Eingabe.liesMonat();
	}
	
	/**liesMonat
	 * @param von(int) ein Monat 1 bis 12
	 * @return (int) Monat (1 bis 12)
	 * 
	 * Eine Funktion, die als PArameter ein Monat nimmt und das andere Monat vom User zurueckgibt
	 */
	public int liesMonat(int von) {
		return Eingabe.liesZweitesMonat(von);
		
	}

	@Override
    /** liesJahr
     * 
     * @return int - das eingelesene Jahr 
     * 
     * Die Methode ruft eine Methode liesJahr aus der Klasse Eingabe und gibt den Wert fuer das Jahr als int zurueck
     */
	public int liesJahr() {
		int jahr = Eingabe.liesJahr();
		//HashMap mit Datum und Feiertagen generieren
		feierTagenGenerieren(jahr);
		return jahr;
	}
	
	
	/**feierTagenGenerieren
	 * 
	 * @param jahr(int) : Das Jahr
	 * 
	 * Als erstes wird den Inhalt von HasMap geloest, damit nur die Events fuer den entsprechenden Jahr gespeichert werden.
	 * Danach wird das HasMap mit Datum und Feiertagen ausgefuellt.
	 * 
	 */
    public void feierTagenGenerieren(int jahr) {
    	//vorherige HasMap mit dem vorherigem Jahr loeschen
		feierTage.clear();
		//Falls es Geburtstage gibt, die in die Feiertage HashMap befuellen
		if (!geburtsTage.isEmpty()) {
			for (Long datum : geburtsTage.keySet()) {
				String datumAlsString = new KalendarFunktionen().kalenderDatumFuerJD(datum);
				String[] datumGesplittet = datumAlsString.split("\\.");
				int geburtstagJahr = Integer.valueOf(datumGesplittet[2]);
				int differenz = jahr - geburtstagJahr;
				if (differenz >= 0) {
					int geburtstatTag = Integer.valueOf(datumGesplittet[0]);
					int geburtstagMonat = Integer.valueOf(datumGesplittet[1]);
					Event geburtstag = geburtsTage.get(datum);
					long geburtstagAktuellesJahr = new KalendarFunktionen().julian_date(geburtstatTag, geburtstagMonat, jahr);
					feierTage.put(geburtstagAktuellesJahr, geburtstag);
				}
			}
		}
		
		// hier wird das Datum von Event durch Konstruktor Aufruf erstellt
		Event weihnachten = new Event("Weihnachten", 24, 12, jahr);
		// HashMap mit Datum und Event ausfuellen
		feierTage.put(weihnachten.getDatum(), weihnachten);
		
		Event event1 = new Event("Weihnachten", 25, 12, jahr);
		feierTage.put(event1.getDatum(), event1);
		
		Event event2 = new Event("Weihnachten", 26, 12, jahr);
		feierTage.put(event2.getDatum(), event2);
		
		Event sylvester = new Event("Sylvester", 31, 12, jahr);
		feierTage.put(sylvester.getDatum(), sylvester);
		
		Event natFeierTag = new Event("Tag der Vereinigung", 3, 10, jahr);
		feierTage.put(natFeierTag.getDatum(), natFeierTag);
		
		Event valentTag = new Event("Valentinstag", 14, 02, jahr);
		feierTage.put(valentTag.getDatum(), valentTag);
		
		Event ersterMai = new Event("Erster Mai", 1, 5, jahr);
		feierTage.put(ersterMai.getDatum(), ersterMai);
		
		Event allerheiligen = new Event ("Allerheiligen", 01, 11, jahr);
		feierTage.put(allerheiligen.getDatum(), allerheiligen);
		
		Event reformationsTag = new Event ("Reformationstag", 31, 10, jahr);
		feierTage.put(reformationsTag.getDatum(), reformationsTag);
		
		//datum fuer den Beginn des Jahres
		long jahresstart = kalendFunkt.julian_date(1, 1, jahr) - 1;
		
		//Ostertag ist beginn des Jahres + tagesnummer des Ostertages
		long osterTag = jahresstart + kalendFunkt.ostersonntag(jahr); 
		Event oster = new Event("Oster", osterTag);
		feierTage.put(oster.getDatum(), oster);
		
		long rosenTag = osterTag - 48;
		Event rosenMontag = new Event("Rosenmontag", rosenTag);
		feierTage.put(rosenMontag.getDatum(), rosenMontag);
		
		long ascherTag = osterTag - 46;
		Event ascherMittwoch = new Event("Ascher Mittwoch", ascherTag);
		feierTage.put(ascherMittwoch.getDatum(), ascherMittwoch);
		
		long gruenTag = osterTag -3;
		Event gruenDonnerstag = new Event("Gruendonnerstag", gruenTag);
		feierTage.put(gruenDonnerstag.getDatum(), gruenDonnerstag);
		
		long kraftTag = osterTag - 2;
		Event kraftFreitag = new Event("Kraftfreitag", kraftTag);
		feierTage.put(kraftFreitag.getDatum(), kraftFreitag);
		
		long osterMontagTag = osterTag + 1;
		Event osterMontag = new Event("OsterMontag", osterMontagTag);
		feierTage.put(osterMontag.getDatum(), osterMontag);
		
		long christTag = osterTag + 39;
		Event chrisHimmelfahrt = new Event("Chrishimmelfahrt", christTag);
		feierTage.put(chrisHimmelfahrt.getDatum(), chrisHimmelfahrt);
		
		long pflingsSonnTag = osterTag + 49;
		Event pfingstSonntag = new Event("Pfingstsonntag", pflingsSonnTag);
		feierTage.put(pfingstSonntag.getDatum(), pfingstSonntag);
		
		long pflingsMonTag = osterTag + 50;
		Event pfingstMontag = new Event("Pfingstmontag", pflingsMonTag);
		feierTage.put(pfingstMontag.getDatum(), pfingstMontag);
		
		long fronleich = osterTag + 60;
		Event fronleichnam = new Event("Fronleichnam", fronleich);
		feierTage.put(fronleichnam.getDatum(), fronleichnam);
		
		// tagesNummer von Mai zu bekommen
		int tagNummerMai = kalendFunkt.tagesnummer(1, 5, jahr);
		// Tag der Woche zu bekommen (ob tagNummerMai im So, Mo, Di... ist)
		int wochenTagMai = kalendFunkt.wochentag_im_jahr(jahr, tagNummerMai);
		//tagesnummer von 1.1jahr + (eine Woche + (7 - So, Mo, Mi...) + anzahl von Tagen von AnfangsJahr bis mai)
		long zweiterSonntagImMai = jahresstart + tagNummerMai + (7 - wochenTagMai) + 7;
		
		if (pflingsSonnTag == zweiterSonntagImMai) {
			zweiterSonntagImMai -= 7;
		}
		Event mutterTag = new Event("Muttertag", zweiterSonntagImMai);
		feierTage.put(mutterTag.getDatum(), mutterTag);
		
		int tagNummerWeihnachten = kalendFunkt.tagesnummer(24, 12, jahr);
		// So, Mo, Di.... bis Sa
		int weihnachtenWochenTag = kalendFunkt.wochentag_im_jahr(jahr, tagNummerWeihnachten);

		long sonttagVorWeihnachten; 
		if (weihnachtenWochenTag  == 0) {
			// if So, vorletzte Sonntag kalkulieren
			sonttagVorWeihnachten = weihnachten.getDatum() - 7;
		} else  {
			// fals was anderes als So, dann minus anzahl von Tagen  zu Sontag
			sonttagVorWeihnachten = weihnachten.getDatum() - weihnachtenWochenTag;
		} 
		// 4*7 - 4 Sonntage vor Weihnachten, "-4" -> Sonntag - 4 Tage = Mittwoch 
		Event bussUndBetttag = new Event("Buss- und Betttag", sonttagVorWeihnachten - (7*3) - 4);
		feierTage.put(bussUndBetttag.getDatum(), bussUndBetttag);
		
		Event advent1 = new Event("Erster Advent", sonttagVorWeihnachten - (7*3));
		feierTage.put(advent1.getDatum(), advent1);
		
		Event advent2 = new Event("Zweiter Advent", sonttagVorWeihnachten - (7*2));
		feierTage.put(advent2.getDatum(), advent2);
		
		Event advent3 = new Event("Dritter Advent", sonttagVorWeihnachten - (7*1));
		feierTage.put(advent3.getDatum(), advent3);
		
		Event advent4 = new Event("Vierter Advent", sonttagVorWeihnachten);
		feierTage.put(advent4.getDatum(), advent4);

	}

	/** liesWochentagUser
     * 
     * Die Methode ruft eine Methode liesWochenTag aus der Klasse Eingabe 
     * und gibt den Wert fuer den wochentag als int zurueck
     */
	public void liesWochentagUser () {
		wochentagUser = Eingabe.liesWochenTag();
	}

	/**liesJahresPlaner
	 * 
	 * Eine Funktion, die Menueauswahl vom Jahresplaner einliest, und gibt zurueck, 
	 * ob der User den Jahresplan mit Event oder ohne sehen moechte(modus wird entsprechend als 1
	 * oder -1 gespeichert) 
	 */
	private void liesJahresPlaner() {
//		anzeigeFeiertage = Eingabe.liesJahresPLaner();
	}

	@Override
    /**auswahlMenue
     * ----------------
     * menueOut aus der Klasse Ausgabe wird aufgerufen und je nach dem was user eingubt (von 0 bis 6 inkl)
     * wird es entsprechend ausgeweartet
     * 
     */
	public void auswahlMenue() {
		boolean exit = false;
		do {
			// das Menue wird ausgegeben
			Ausgabe.menueOut();
			// user gibt einen Wert ein
			int userEingabe = Eingabe.menuePunkt();
			// userEingabe evaluieren
			int jahr = -1;
			int monat = -1;
			String dateiName = "";
			switch (userEingabe) {
			// falls "1" ausgewaehlt wurde
			case GANZESJAHR:
				anzeigeFeiertage = false;
				// das Jahr wird eingelesen
				jahr = liesJahr();
				// kalendar fuer das ganze Jahr wird gezeigt
				zeigeJahr(jahr);
				// // default Namen fuer das Jahr ohne Feiertagen
				dateiName = jahr + ".txt";
				monatBlattZumSpeichern = DateiSpeicher.dateiSpeichern(GANZESJAHR, dateiName, monatBlattZumSpeichern, dateiSpeichernJaNein);
				break;
			// falls "2" ausgewaehlt wurde
			case MONAT:
				// ohne Feirtagen
				anzeigeFeiertage = false;
				// das Jahr wird eingelesen
				jahr = liesJahr();
				// Monat wird eingelesen
				monat = liesMonat();
				zeigeMonat(jahr, monat);
				// default Namen fuer das Monat ohne Feiertagen
				dateiName = jahr + "_" + monat + ".txt";
				monatBlattZumSpeichern = DateiSpeicher.dateiSpeichern(MONAT, dateiName, monatBlattZumSpeichern, dateiSpeichernJaNein);
				break;
			case JAHRMITFEIERTAGEN: 
				anzeigeFeiertage = true;
				// das Jahr wird eingelesen
				jahr = liesJahr();
				zeigeJahr(jahr);
				// default Namen fuer das Jahr mit Feiertagen
				dateiName = jahr + "_f"  + ".txt";
				monatBlattZumSpeichern = DateiSpeicher.dateiSpeichern(JAHRMITFEIERTAGEN, dateiName, monatBlattZumSpeichern, dateiSpeichernJaNein);
				break;
			case MONATMITFEIERTAGEN: 
				anzeigeFeiertage = true;
				// das Jahr wird eingelesen
				jahr = liesJahr();
				// Monat wird eingelesen
				monat = liesMonat();
				// wochentagUser wird eingelesen
				//liesWochentagUser();
				// das Kalendar fuer bestimmten Monat wird eingezeigt
				zeigeMonat(jahr, monat);
				dateiName = jahr + "_" + monat + "_f" + ".txt";
				monatBlattZumSpeichern = DateiSpeicher.dateiSpeichern(MONATMITFEIERTAGEN, dateiName, monatBlattZumSpeichern, dateiSpeichernJaNein);
				break;
			case KALENDARBLATTMONTAG: 
				anzeigeFeiertage = false;
				liesWochentagUser();
				Ausgabe.zeigeMeldung("Ihr Kalendar wird jetzt mit " + (wochentagUser == 0 ? "Sonntag" : "Montag") + " anfangen");
				break;
			case JAHRESPLANER: 
				liesJahresPlaner();
				jahr = liesJahr();
				// Objekt der Klasse Jahresplaner erzeugen
				Jahresplaner jp = new Jahresplaner(jahr, anzeigeFeiertage);
				int von = liesMonat();
				int bis = liesMonat(von);
				String tmp = jp.gibJahresplan(von, bis);
				Ausgabe.zeigeMeldung(tmp);
				// falls mit feiertagen
				if (anzeigeFeiertage) {
					dateiName = jahr + "_" + von + "_" + bis + "_jahresplan_f" + ".txt";
				} else {
					dateiName = jahr + "_" + von + "_" + bis + "_jahresplan" + ".txt";
				}
				monatBlattZumSpeichern = DateiSpeicher.dateiSpeichern(JAHRESPLANER, dateiName, tmp, dateiSpeichernJaNein);
				break;
			// falls "0" ausgewaehlt wurde
			case BEENDEN:
				Ausgabe.exitMessage();
				// das Programm wird beendet
				exit = true;
			default:
				break;
			}
			// solange exit false ist
		} while (!exit);
	}
	
	/**
	 * 
	 * @return true oder false
	 * Eine Funktion, die true zurueckgibt, falls wochentagUser gleich 1 ist
	 * 
	 */
	public boolean getMontagErst() {
		return wochentagUser == 1;
	}

	/**
	 * 
	 * @param montagErst(boolean) - true - falls wochentagUser 1 ist
	 * Eine Funktion, die wochentagUser auf 0 oder 1 setzt (entspricht So oder Mo).
	 */
	public void setMontagErst(boolean montagErst) {
		this.wochentagUser = montagErst ? 1 : 0;
	}

	/**
	 * 
	 * @return true oder false 
	 * Die Funktion gibt true oder false abheangig vom anzeigeFeiertage zurueck
	 */
	public boolean getAnzeigeFeiertage() {
		return anzeigeFeiertage;
	}

	/**
	 * 
	 * @param anzeigeFeiertage - true oder false 
	 * Die Funktion setzt anzeigeFeiertage auf true oder false abhaengig vom Parameter
	 */
	public void setAnzeigeFeiertage(boolean anzeigeFeiertage) {
		this.anzeigeFeiertage = anzeigeFeiertage;
	}

	public void befuelleGeburtstageMap(HashMap<Long, Event> mapGeburtstag) {
		geburtsTage = mapGeburtstag;
	}
}
