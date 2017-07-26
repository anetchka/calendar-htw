package gui;
/**
 * @author Anna Kovtun s0552342
 * @version Eclipse Neon
 * @since 21.06.2017
 * Letzte Aenderung: 21.06.2017 
 * Dateiname: FeiertageFensterOhneSoSa.java
 * Beschreibung: eine Klasse die die Feiertagen im bestimmten Jahr in einem eigenem Fenster zeigt
 */
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.util.HashMap;

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

public class FeiertageFenster extends JFrame{
	//Attribute
	private JTextPane txtFeiertage = new JTextPane();
	
	//Konstruktor
	public FeiertageFenster(JComboBox<Integer> cbJahr) throws HeadlessException {
		this("Feiertage", cbJahr);
	}
	//Konstruktor
	public FeiertageFenster(String title, JComboBox<Integer> cbJahr) throws HeadlessException {
		super(title);
		//Fenster initialisieren
		init();
		//Content Pane
		Container cpane = this.getContentPane();
		//Layout fuer das Content Pane
		cpane.setLayout(new BorderLayout());
		
		JScrollPane scrollTextFeiertage = new JScrollPane(txtFeiertage);
		
		cpane.add(scrollTextFeiertage);
		
		//Jahr bekommen
		int jahr = (Integer) cbJahr.getSelectedItem();
		
		//Style
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
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	
		Kalendar cal = Kalendar.getInstance();
		//Feiertagen generieren
		cal.feierTagenGenerieren(jahr);
		//HasMap mit Feierntagen bekommen
		HashMap <Long, Event> feierTage = cal.getFeierTage();
		//KeySet als Array bekommen
		Long[] sortedDate = feierTage.keySet().toArray(new Long[0]);
		//KeySet sortieren
		java.util.Arrays.sort(sortedDate);
		//Durch KeySet iterieren
		for (Long datum : sortedDate) {
			//Event bekommen
			Event e = feierTage.get(datum);
			//Datum bom Event bekommen
			String datumAlsString = new KalendarFunktionen().kalenderDatumFuerJD(datum) + ": ";
			//Name vom Event bekommen
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
	/**
	 * Die Funktion initialisiert Fenster view
	 */
	private void init() {
		//Fenster visible zu machen
		this.setVisible(true);
		
		this.setMinimumSize(new Dimension(600, 600));
		this.setLocation(700, 300);
	}

}
