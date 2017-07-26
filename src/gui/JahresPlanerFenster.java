package gui;
/**
 * @author Anna Kovtun s0552342
 * @version Eclipse Neon
 * @since 20.06.2017
 * Letzte Aenderung: 22.06.2017 
 * Dateiname: JahresPlanerFenster.java
 * Beschreibung: eine Klasse, die realisiert ein Fenster mit Jahresplaner
 */
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import controller.ActionListenerVonBisMonat;
import kalendar.Jahresplaner;
import kalendar.Kalendar;


public class JahresPlanerFenster extends JFrame{
	//Attribute
	private Kalendar cal = Kalendar.getInstance();
	private JComboBox<String> bisMonatcmb = new JComboBox<String>();
	private JComboBox<String> vonMonacmb = new JComboBox<String>();
	private int jahr = 0;
	private JTextPane txtpane = new JTextPane();
	//Konstruktor
	public JahresPlanerFenster(JComboBox<Integer> cbJahr) throws HeadlessException {
		this("Jahresplaner", cbJahr);
	}

	//Konstruktor
	public JahresPlanerFenster(String title, JComboBox<Integer> cbJahr) throws HeadlessException {
		super(title);
		init();
		
		//ContentPane holen
		Container cpane = this.getContentPane();
		cpane.setLayout(new BorderLayout());
		
		//Panels holen
		JPanel panelNord = new JPanel();
		JPanel panelMitte = new JPanel();
		
		//Jahresplaner mit Jahr vom Combobox bekommen
		this.jahr = (Integer)cbJahr.getSelectedItem();
		
		// TextPane mit ScrollPane auf die ContenPane legen
		
		panelMitte.add(txtpane);
		JScrollPane sp = new JScrollPane(panelMitte);
		
		cpane.add(sp);
		
		jahresplanGenerieren(jahr, 1, 12, txtpane);
		
		cpane.add(panelNord, BorderLayout.NORTH);
		
		//Layout fuer Panel Nord
		GridBagLayout grid = new GridBagLayout();
		panelNord.setLayout(new BorderLayout());
		panelNord.setLayout(grid);
		
		//Constraints fuer Panel Nord
		GridBagConstraints constrNord = new GridBagConstraints();
		constrNord.insets = new Insets(2, 20, 2, 10);
		
		//Label von
		JLabel vonLabel = new JLabel("Von");
		panelNord.add(vonLabel, constrNord);
		//Combobox von
		vonMonacmb = befuelleMonate(vonMonacmb);
		panelNord.add(vonMonacmb, constrNord);
		
		//Label bis
		JLabel bisLabel = new JLabel("Bis");
		panelNord.add(bisLabel, constrNord);
		//Combobox bis
		bisMonatcmb = befuelleMonate(bisMonatcmb);
		bisMonatcmb.setSelectedIndex(cal.MONATEN.length-1);
		panelNord.add(bisMonatcmb, constrNord);
		
		//Listener an Combobox anbinden
		vonMonacmb.addActionListener(new ActionListenerVonBisMonat(this));
		bisMonatcmb.addActionListener(new ActionListenerVonBisMonat(this));
			
		this.pack();
	}//Ende der Konstruktor

	/**
	 * 
	 * @param selectedIndex - index der vom Array mit Monatsnamen ausgewaehlt wurde
	 * @param monatsName - der Name vom Monate
	 * @return index (int) - der Index vom ausgewähletm Monat
	 * 
	 *  Die Funktion gibt der index vom ausgewaehlten Monat
	 */
	private int monatIndex(int selectedIndex, String monatsName) {
		int index = 0;
		for (int i = 0; i < cal.MONATEN.length; i++) {
			if (monatsName.equalsIgnoreCase(cal.MONATEN[i])) {
				index = i + 1;
				break;
			}
		}
		return index;
	}

	/**
	 * @param vonMonacmb(JComboBox) Combobox mit Monatsnamen
	 * @return vonMonacmb(JComboBox) Combobox mti befuellten Monatsnamen
	 * Die Funktion bekommt als Parameter eine leere Combobox 
	 * und bekommt diese Combobox befuellt mit Monatsnamen zurueck
	 */
	private JComboBox<String> befuelleMonate(JComboBox<String> vonMonacmb) {
		for(int i = 0; i < cal.MONATEN.length; i++) {
			vonMonacmb.addItem(cal.MONATEN[i]);
		}
		return vonMonacmb;
	}

	/**
	 * Die Funktion initialisiert Fenster view
	 */
	private void init() {
		this.setVisible(true);
		this.setPreferredSize(new Dimension(1200, 700));
		this.setLocation(500, 200);
	}

	/**
	 * Die Funktion erneut das Jahreplanerfenster 
	 */
	public void refresh() {
		txtpane.setText("");
		String vonMonatsName = (String)vonMonacmb.getSelectedItem();
		String bisMonatsName = (String)bisMonatcmb.getSelectedItem();
		int vonIndex = monatIndex(vonMonacmb.getSelectedIndex(), vonMonatsName);
		int bisIndex = monatIndex(bisMonatcmb.getSelectedIndex(), bisMonatsName);
		//Jahresplanergenerieren
		jahresplanGenerieren(this.jahr, vonIndex, bisIndex, txtpane);
	}
	
	/**
	 * 
	 * @param jahr (int) Jahr
	 * @param vonMonat (int) Monat (kann auswaehlen von Januar bis Dezember)
	 * @param bisMonat (int) Monat (kann auswaehlen von Januar bis Dezember)
	 * @param txtpane (JTextPane) textpane, wo das Jahresplaner text gezeigt wird
	 * 
	 * Die Funktion generiert Jahresplaner TextPane
	 */
	public void jahresplanGenerieren(int jahr, int vonMonat, int bisMonat, JTextPane txtpane) {
		
		//Anzege Feiertegen aif true setzten
		Kalendar.getInstance().setAnzeigeFeiertage(true);
		
		Jahresplaner jPlaner = new Jahresplaner(this.jahr, true);

		String jahresplan = jPlaner.gibJahresplan(vonMonat, bisMonat);
		if (jahresplan.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Monat von muss größer als Monat bis sein");
			return;
		}
		// Die Position vom ersten \n finden
		int pos = jahresplan.indexOf(System.lineSeparator());
		String kopf = jahresplan.substring(0, pos);
		String plan = jahresplan.substring(pos);

		// Verschiedene Style fuer das Jahresplaner definieren
		StyledDocument doc = txtpane.getStyledDocument();
		Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
		Style regular = doc.addStyle("regular", def);
		
		StyleConstants.setFontFamily(regular, "Lucida Sans Typewriter");
		Style bold = doc.addStyle("bold", regular);
		StyleConstants.setBold(bold, true);
		

		try {
			doc.insertString(0, kopf, doc.getStyle("bold"));
			doc.insertString(doc.getLength(), plan, doc.getStyle("regular"));
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public JTextPane getTxtpane() {
		return txtpane;
	}

	
} //Ende der Klasse
