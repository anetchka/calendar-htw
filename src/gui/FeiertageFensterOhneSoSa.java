package gui;
/**
 * @author Anna Kovtun s0552342
 * @version Eclipse Neon
 * @since 21.06.2017
 * Letzte Aenderung: 21.06.2017 
 * Dateiname: FeiertageFensterOhneSoSa.java
 * Beschreibung: eine Klasse die die Feiertagen ohne Sonntag und Samstag
 * im bestimmten Jahr in einem eigenem Fenster zeigt
 */
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import kalendar.Event;
import kalendar.Kalendar;
import kalendar.KalendarFunktionen;

public class FeiertageFensterOhneSoSa extends JFrame {
	//Attribute
	private JTextPane txtFeiertage = new JTextPane();

	//KOnstruktor
	public FeiertageFensterOhneSoSa(JComboBox<Integer> cbJahr) throws HeadlessException {
		this("Feiertage ohne Sonntag und Samstag", cbJahr);
	}
	//Konstruktor
	public FeiertageFensterOhneSoSa(String title, JComboBox<Integer> cbJahr) throws HeadlessException {
		super(title);
		init();

		//Fenster initialisieren
		Container cpane = this.getContentPane();
		//Layout fuer das Content Pane
		cpane.setLayout(new BorderLayout());

		JScrollPane scrollTextFeiertage = new JScrollPane(txtFeiertage);

		cpane.add(scrollTextFeiertage);

		// Jahr bekommen
		int jahr = (Integer) cbJahr.getSelectedItem();
		zeigeFeirtageOneSoSa(jahr);
		this.pack();
	}

	/**
	 * 
	 * @param jahr(int) Das Jahr
	 * Die Funktion zeigt alle Feiertagen, die nicht an Sonntag oder Samstag fallen
	 */
	public void zeigeFeirtageOneSoSa(int jahr) {
		// Style
		StyledDocument doc = txtFeiertage.getStyledDocument();
		Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
		Style regular = doc.addStyle("regular", def);
		StyleConstants.setFontFamily(regular, "Lucida Sans Typewriter");

		Style bold = doc.addStyle("bold", regular);
		StyleConstants.setBold(bold, true);

		String newLine = System.lineSeparator();
		String anfang = "Im Jahr " + jahr + " gibt es folgende Feiertage" + System.lineSeparator();
		try {
			doc.insertString(0, anfang, doc.getStyle("bold"));
		} catch (BadLocationException e2) {
			e2.printStackTrace();
		}

		Kalendar cal = Kalendar.getInstance();
		//Feiertagen generieren
		cal.feierTagenGenerieren(jahr);
		//Feiertagen bekommen
		HashMap<Long, Event> feierTage = cal.getFeierTage();
		//KeySet in Array speichern
		//Long[] sortedDate = feierTage.keySet().toArray(new Long[0]);
		SortedSet<Long> sortedDate = new TreeSet<Long>(feierTage.keySet());
//		SortedSet<Event>values = new TreeSet<Event>(feierTage.values());
		//KeySet sortieren
		//java.util.Arrays.sort(sortedDate);
		//Durch KeySet iterieren
		for (Long datum : sortedDate) {
			Event e = feierTage.get(datum);
			//Tag im Jahr ist DIfferenz zwischen zwischen Key datum und  new KalendarFunktionen().julian_date(1, 1, jahr)
			long tagImJahr = datum - new KalendarFunktionen().julian_date(1, 1, jahr) + 1;
			//Wochentag im Jahr bekommen
			int wochenTagimJahr = new KalendarFunktionen().wochentag_im_jahr(jahr, (int)tagImJahr);
			if (wochenTagimJahr != 0 && wochenTagimJahr != 6) {
				String datumAlsString = new KalendarFunktionen().kalenderDatumFuerJD(datum) + ": ";
				String name = e.getName();
				try {
					doc.insertString(doc.getLength(), datumAlsString, doc.getStyle("regular"));
					doc.insertString(doc.getLength(), name, doc.getStyle("regular"));
					doc.insertString(doc.getLength(), newLine, doc.getStyle("regular"));
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
			
		}
	}

	/**
	 * Die Funktion initialisiert Fenster view
	 */
	private void init() {
		// Fenster visible zu machen
		this.setVisible(true);

		this.setMinimumSize(new Dimension(600, 600));
		this.setLocation(700, 300);
	}
}
