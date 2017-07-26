package gui;
/**
 * @author Anna Kovtun s0552342
 * @version Eclipse Neon
 * @since 15.06.2017
 * Letzte Aenderung: 20.06.2017 
 * Dateiname: MeinFenster.java
 * Beschreibung: eine Klasse, die die Hauptfenster vom Kalendar Application zeigt
 */
import kalendar.Kalendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import kalendar.Event;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.text.rtf.RTFEditorKit;

import controller.ActionListenerKalendar;
import controller.ActionListenerMonatCmb;
import io.EinleserCVS;

public class MeinFenster extends JFrame{

	//Atributte
	private JTextPane txtKalendar = new JTextPane();
	private ButtonGroup buttonGrup = new ButtonGroup();
	private JLabel picLab = new JLabel();
	
	//JahresplanerFenster
	private JahresPlanerFenster jFenster = null;
	//Radio Button fuer Auswahlmenue
	private JRadioButton[] rdbutt = null;
	
	//CheckBox Wochenbeginn
	private JCheckBox wochenBeginn = new JCheckBox("Format wecheln?");
	
	//Combobox fuer die Monate
	private JComboBox<String> cmbMonat = new JComboBox<String>();
	//Combobox fuer das Jahr
	private	JComboBox<Integer> cmbJahr = new JComboBox<Integer>();
	
	//Panel erzeugen
	private JPanel pnMitte = new JPanel();
	private JPanel pnWest = new JPanel();
	private JPanel pnOst = new JPanel();
	private JPanel pnSued = new JPanel();
	
	 //Kalendar Instanz bekommen
	private Kalendar cal = Kalendar.getInstance();
	
	//default Konstruktor
	public MeinFenster() throws HeadlessException {
		this("Kalender");
	}
	
	//Konstruktor mit Parametern
	public MeinFenster(String title) throws HeadlessException {
		super(title);
		//MeinFenster initialisieren
		init();
		
		//ContentPane holen
		Container cpane = this.getContentPane();
		//Layout fuer Contentpane festlegen
		cpane.setLayout(new BorderLayout());
		
		//Panel auf ContentPane legen
		cpane.add(pnWest, BorderLayout.WEST);
		cpane.add(pnOst, BorderLayout.EAST);
		cpane.add(pnSued, BorderLayout.SOUTH);
		
		//Panel mit Raender versehen
		pnMitte.setBorder(BorderFactory.createLineBorder(Color.green, 1));
		pnWest.setBorder(BorderFactory.createLineBorder(Color.green, 1));
		pnOst.setBorder(BorderFactory.createLineBorder(Color.green, 1));
		pnSued.setBorder(BorderFactory.createLineBorder(Color.green, 1));
		
		//Layout for the Panel
		GridBagLayout grid = new GridBagLayout();
		pnMitte.setLayout(new BorderLayout());
		pnWest.setLayout(grid);
		pnOst.setLayout(grid);
		pnSued.setLayout(grid);


		/////////////////////PANEL EAST///////////////////////////
		GridBagConstraints constrOst = new GridBagConstraints();
		constrOst.gridx = 0;
		constrOst.gridy = 0;
		JLabel aktionAuswahl = new JLabel("Aktion Auswahl");
		String[] label = {"Kalender fuer das ganzes Jahr      ", "Monatsblatt", "Jahreskalender mit Feiertagen", "Monatsblatt mit Feiertagen"};
		rdbutt = new JRadioButton[label.length];
		constrOst.anchor = GridBagConstraints.WEST;
		pnOst.add(aktionAuswahl, constrOst);
		
		ActionListenerKalendar actionListenerKalendar = new ActionListenerKalendar(this);
		//RadioButtons befuellen
		for (int i = 0; i < label.length; i++) {
			rdbutt[i] = new JRadioButton(label[i]);
			rdbutt[i].addActionListener(actionListenerKalendar);
			buttonGrup.add(rdbutt[i]);
			constrOst.gridy ++;
			pnOst.add(rdbutt[i], constrOst);
		}
		rdbutt[0].setSelected(true);
		
		
		//////////////////////////PANEL WEST///////////////////////////
		GridBagConstraints constrWest = new GridBagConstraints();
		constrWest.gridx = 0;
		constrWest.gridy = 0;
		constrWest.gridwidth = 2;
		constrWest.anchor = GridBagConstraints.WEST;
		constrWest.insets = new Insets(2, 10, 2, 10);
		
		//Label Auswaehlen
		JLabel auswahl = new JLabel("Bitte auswaehlen");
		pnWest.add(auswahl, constrWest);
		
		//Label Monat
		JLabel monat = new JLabel("Monat");
		constrWest.gridy = 1;
		constrWest.gridwidth = 1;
		constrWest.anchor = GridBagConstraints.CENTER;
		pnWest.add(monat, constrWest);
		
		//Label Jahr
		JLabel jahr = new JLabel("Jahr");
		constrWest.gridx = 1;
		constrWest.anchor = GridBagConstraints.EAST;
		pnWest.add(jahr, constrWest);
		
		//Kalendar holen 
		Calendar cal = Calendar.getInstance();

		//Combobox mit Monaten befuellen
		cmbMonat = fuelleMonatenCmb(cmbMonat);
		//Listener anbinden (Bilder zu Monat)
		cmbMonat.addActionListener(new ActionListenerMonatCmb(picLab));
		//Listener anbinden (Kalendar ensprechend Monaten anpassen)
		cmbMonat.addActionListener(actionListenerKalendar);
		//Constraints fuer MonatCombobox
		constrWest.gridx = 0;
		constrWest.gridy = 2;
		constrWest.anchor = GridBagConstraints.WEST;
		pnWest.add(cmbMonat, constrWest);

		//Combobox mit Jahren befuellen
		cmbJahr = fuelleJahrCmb(cmbJahr);
		cmbJahr.setSelectedItem(cal.get(Calendar.YEAR));
		//Listener anbinden
		cmbJahr.addActionListener(actionListenerKalendar);
		//constraints
		constrWest.gridx = 1;
		constrWest.anchor = GridBagConstraints.EAST;
		pnWest.add(cmbJahr, constrWest);
		
		//Picture
		picLab.setPreferredSize(new Dimension(250, 200));
		//Combobox auf aktuelle Monat setzen
		cmbMonat.setSelectedItem(Kalendar.MONATEN[cal.get(Calendar.MONTH)]);
		constrWest.gridx = 0;
		constrWest.gridy = 3;
		constrWest.gridwidth = 2;
		constrWest.anchor = GridBagConstraints.CENTER;
		pnWest.add(picLab, constrWest);

		//Label WochenPlan
		JLabel wochenPlan = new JLabel("Wochenbeginn mit So/Mo");
		constrWest.gridy = 4;
		constrWest.gridwidth = 1;
		constrWest.anchor = GridBagConstraints.WEST;
		pnWest.add(wochenPlan, constrWest);

		//Constraints fuer das Wochenbeginn
		constrWest.gridy = 5;
		wochenBeginn.addActionListener(actionListenerKalendar);
		pnWest.add(wochenBeginn, constrWest);
		
		//PANEL SUED
		JLabel suedLabel = new JLabel("    ");
		pnSued.add(suedLabel);
		
		///////////////////PANEL MITTE///////////////////////////////
		pnMitte.add(txtKalendar);
		//Scroll hinzufuegen 
		JScrollPane scrollTextKalendar = new JScrollPane(pnMitte);
		
		//CPane.add nicht pnMitte sondern scroll!!!!
		cpane.add(scrollTextKalendar);

		refreshCalendar();

		//MenueBar setzen
		this.setJMenuBar(new MenueLeiste(this, cmbJahr));
		
		this.pack();
	}
	/**
	 * 
	 * @param cmbMonaten (JComboBox) Combobox fuer Strings
	 * @return cmbMonaten (JComboBox) befuellte combobox mit Monaten
	 * Die Funktion bekommt als Parameter Combobox und gibt diese Combobox befuellt mit Monaten zurueck 
	 */
	private JComboBox<String> fuelleMonatenCmb(JComboBox<String> cmbMonaten) {
		for (int i = 0; i < Kalendar.MONATEN.length; i++) {
			cmbMonaten.addItem(Kalendar.MONATEN[i]);	
		}
		return cmbMonaten;
	}
	/**
	 * 
	 * @param cmbJahr (JComboBox) Combobox fue Zahlen
	 * @return cmbJahr (JComboBox) befuellte combobox mit Jahren (ab 1987 bis 2030)
	 * Die Funktion bekommt als Parameter Combobox und gibt diese Combobox befuellt mit Jahren (1987-2030) zurueck
	 */
	private JComboBox<Integer> fuelleJahrCmb(JComboBox<Integer> cmbJahr) {
		for (int i = 1987; i < 2030; i++) {
			cmbJahr.addItem(new Integer(i));
		}
		
		return cmbJahr;
	}
	/**
	 * Die Funktion initialisiert das Hauptfenster
	 */
	private void init() {
		//Fenster visible zu machen
		this.setVisible(true);
		
		//Fenster schlieﬂbar machen
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setPreferredSize(new Dimension(1200, 900));
		this.setLocation(500, 100);
		
	}
	/**
	 * Die Funktion erneut das Kalendar view
	 */
	public void refreshCalendar() {
		//Kalendar mit So oder Mo anzeigen
		cal.setMontagErst(wochenBeginn.isSelected());

		//Kalendartext clear
		txtKalendar.setText("");

		String str = "";
		//Style fue das Kalendar
		StyledDocument doc = txtKalendar.getStyledDocument();
		Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
		//Regular Style fuer Kalendar
		Style regular = doc.addStyle("regular", def);
		StyleConstants.setFontFamily(regular, "Lucida Sans Typewriter");
		//Bold Style fuer Kalendar Kopf
		Style bold = doc.addStyle("bold", regular);
		StyleConstants.setBold(bold, true);
		//Feiertagen Style fuer die Feiertagen
		Style feiertage = doc.addStyle("feiertage", regular);
		StyleConstants.setForeground(feiertage, Color.RED);

		//Jahr bekommen
		int year = (Integer)cmbJahr.getSelectedItem();
		//Monat bekommen
		int monat = (Integer)cmbMonat.getSelectedIndex() + 1;

		//Falls Kalendar fuer das ganze Jahr ausgewaehlt ist
		if (rdbutt[0].isSelected()) {
			//Kalendar ohne Feiertagen
			cal.setAnzeigeFeiertage(false);
			zeigeJahr(doc, str, year,cal.getAnzeigeFeiertage());	
			//Falls Monatsblatt ohne Feiertagen ausgewaehlt ist
		} else if (rdbutt[1].isSelected()) {
			//Kalendar ohne Feiertagen
			cal.setAnzeigeFeiertage(false);
			zeigeMonat(doc, str, year, monat, cal.getAnzeigeFeiertage());
			//Kalendar fuer das ganze Jahr mit Feiertagen
		} else if (rdbutt[2].isSelected()) {
			cal.setAnzeigeFeiertage(true);
			//Feiertagen generieren
			cal.feierTagenGenerieren(year);
			zeigeJahr(doc, str, year, cal.getAnzeigeFeiertage());			
			//Monat mit Feiertagen
		} else {
			//Kalendar mit Feiertagen
			cal.setAnzeigeFeiertage(true);
			cal.feierTagenGenerieren(year);
			zeigeMonat(doc, str, year, monat, cal.getAnzeigeFeiertage());
		}
	}
	/**
	 * 
	 * @param dateiname (String) Name des Datei
	 * Eine Funktion, die das Kalendar im .rtf Format speichert
	 */
	public void speicherKalender(String dateiname) {
		Document document = txtKalendar.getDocument();
		speicherRTFDatei(dateiname, document);
	}
	/**
	 * 
	 * @param dateiName (String) Name des Datei
	 * 
	 * Die Funktion oeffnet Kalendar im .rtf oder .txt Format
	 */
	public void oeffnenKalendar(String dateiName) {
		txtKalendar.setText("");
		if (dateiName.endsWith(".txt")) {
			File datei = new File(dateiName);
			String zeile = null;
			BufferedReader in = null;
			String text = new String();
			// Stream oeffnen
		
			try {
				in = new BufferedReader (new FileReader( datei ));
			} 
			catch (FileNotFoundException e) {
				System.out.println("Fehler Datei= "+ dateiName +" konnte nicht geoeffnet werden");
				e.printStackTrace();
			}
			
			// Stream lesen
			try {
				while( (zeile = in.readLine()) != null ){
					// die gelesene Zeile abspeichern in die Liste
					text += zeile + System.lineSeparator();
				}
			} catch (IOException e) {
				System.out.println("Fehler Datei= "+ dateiName +" konnte nicht gelesen werden");
				e.printStackTrace();
			}
			
			// Stream schliessen
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			txtKalendar.setText(text);
		} else if (dateiName.endsWith(".rtf")) {
			RTFEditorKit kit = new RTFEditorKit();
			if (!dateiName.endsWith(".rtf")) {
				dateiName += ".rtf";
			}

			BufferedInputStream in = null;
			Document document = txtKalendar.getDocument();
			try {
				in = new BufferedInputStream(new FileInputStream(dateiName));

				// Stream schreiben
				try {
					kit.read(in, document, 0);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}

			} catch (IOException e) {
				System.err.println("Konnte Datei nicht erstellen");
			} finally {
				try {
					in.close(); // Stream schlieﬂen
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 
	 * @param doc StyledDocument
	 * @param str (String) - die gezeigte im Hauptfenster String als Kalendar
	 * @param year (int) - das Jahr
	 * @param mitFeiertagen(boolean) - Kalendar mit oder ohne Feiertagen zeigen
	 * 
	 * Die Funktion ruft 12-Mal die Funktion zeigeMonat(StyledDocument doc, String str, int year, int monat, boolean mitFeiertagen)
	 */
	private void zeigeJahr(StyledDocument doc, String str, int year, boolean mitFeiertagen) {
		for (int i = 1; i <= 12; i++) {
				zeigeMonat(doc, str, year, i, mitFeiertagen);
		}
	}
	/**
	 * 
	 * @param doc StyledDocument
	 * @param str (String) - die gezeigte im Hauptfenster String als Kalendar
	 * @param year (int) - das Jahr
	 * @param monat (int) - der Monat (1 bis 12)
	 * @param mitFeiertagen (boolean) - Kalendar mit oder ohne Feiertagen zeigen
	 * 
	 * Die Funktion zeigt der Monat im Hauptfenster. Falls boolean mitFeiertagen true ist, werden die Feiertage in anderem Style gezeigt
	 */
	private void zeigeMonat(StyledDocument doc, String str, int year, int monat, boolean mitFeiertagen){
		//Monat mit Feiertagen zeigen
		if (mitFeiertagen) {
			try {
				//Kopfzeile fuer den Monat bekommen
				str = cal.getKopfzeileMonatsblatt(year, monat);
				//Kopfzeile in bold Style setzten
				doc.insertString(doc.getLength(), str, doc.getStyle("bold"));
				//Monatsblatt bekommen
				str = cal.getMonatsblatt(year, monat);			
				String neuenStyle = "";
				//Index
				int last = 0; 
				int beginn = str.indexOf("*");
				int end = 0;
				
				while (end < str.length() && beginn >= 0) {
					neuenStyle = str.substring(end, beginn);
					doc.insertString(doc.getLength(), neuenStyle, doc.getStyle("regular"));
					end = beginn + 4;
					neuenStyle = str.substring(beginn, end);
					doc.insertString(doc.getLength(), neuenStyle, doc.getStyle("feiertage"));
					beginn = str.indexOf("*", end);
					last = end;
				}
				if (beginn == -1) {
					end = str.indexOf("Im");
					if (end == -1) {
						doc.insertString(doc.getLength(), str, doc.getStyle("regular"));
					} else {
						neuenStyle = str.substring(last, end);
						doc.insertString(doc.getLength(), neuenStyle, doc.getStyle("regular"));
						neuenStyle = str.substring(end, str.length());
						doc.insertString(doc.getLength(), neuenStyle, doc.getStyle("feiertage"));
					}
				}
				
	        } catch (BadLocationException ble) {
	            System.err.println("Couldn't insert initial text into text pane.");
	        }
			//Monat ohne Feiertagen zeigen
		} else {
			try {
				//Kopfzeile fuer den Monat bekommen
				str = cal.getKopfzeileMonatsblatt(year, monat);
				//Kopfzeile in bold Style setzten
				doc.insertString(doc.getLength(), str, doc.getStyle("bold"));
				//Monatsblatt bekommen
				str = cal.getMonatsblatt(year, monat);
				doc.insertString(doc.getLength(), str, doc.getStyle("regular"));
	        } catch (BadLocationException ble) {
	            System.err.println("Couldn't insert initial text into text pane.");
	        }
		}
	}
	
	public void zeigeJahreSplanerFenster() {
		jFenster = new JahresPlanerFenster(cmbJahr);
	}
	
	public void speicherJahresplaner(String datei) {
		if (jFenster == null) {
			JOptionPane.showMessageDialog(this, "Oeffnen Sie erstmal das Jahresplnaer");
			return;
		}
		JTextPane txtJahresplaner = jFenster.getTxtpane();
		Document doc = txtJahresplaner.getDocument();
		speicherRTFDatei(datei, doc);
	}
	
	public void speicherRTFDatei(String datei, Document document) {
		RTFEditorKit kit = new RTFEditorKit();
		if (!datei.endsWith(".rtf")) {
			datei += ".rtf";
		}

		BufferedOutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(datei));

			// Stream schreiben
			try {
				kit.write(out, document, 0, document.getLength());
			} catch (BadLocationException e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			System.err.println("Konnte Datei nicht erstellen");
		} finally {
			try {
				out.close(); // Stream schlieﬂen
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void oeffnenCSV(String path) {
		HashMap<Long, Event> mapGeburtstag = EinleserCVS.lesenCVS(path);
		Kalendar.getInstance().befuelleGeburtstageMap(mapGeburtstag);
	}
}
