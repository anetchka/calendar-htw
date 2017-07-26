package start;
import gui.MeinFenster;
import kalendar.Kalendar;
import kalendar.KalendarFunktionen;

/**
 * 
 * @author Anna Kovtun s0552342
 * @version Eclipse Neon
 * @since 20.05.2017
 * Letzte Aenderung: 22.06.2017 
 * Dateiname: Start.java
 * Beschreibung: eine Klasse mit Start-Methode, die mein Fenster erzeugt
 */

public class Start {
	
	/** 
	 * 
	 * @param args(String[])
	 * 
	 * erzeugt ein Kalender Objekt und ruft die Methode auswahltMenue auf
	 */
	public static void main(String[] args) {
	//	Kalendar.getInstance().auswahlMenue();
		MeinFenster myWindow = new MeinFenster("Kalender");
	}
}
