package controller;
/**
 * @author Anna Kovtun s0552342
 * @version Eclipse Neon
 * @since 17.06.2017
 * Letzte Aenderung: 17.06.2017 
 * Dateiname: ActionListenerOeffnen.java
 * Beschreibung: eine Klasse, die die ActionListener implementiert 
 * und zwar ein File in bestimmten Formaten oeffnet(.rtf/.txt/.csv)
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import fileExtension.FileTypeExtension;
import gui.MeinFenster;

public class ActionListenerOeffnen implements ActionListener {
	//Attribute
	private MeinFenster mfenster = null;
	//Konstruktor
	public ActionListenerOeffnen(MeinFenster mfenster) {
		super();
		this.mfenster = mfenster;
	}

	/**
	 * @param event ActionEvent
	 * 
	 * Diese Funktion oeffnet die Datei in .rtf, .txt und .cvs format
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		JFileChooser fileChooser  = new JFileChooser();
		fileChooser.setDialogTitle("Datei Oeffnen");
		fileChooser.setFileFilter(new FileTypeExtension(".txt", "Text File"));
		fileChooser.setFileFilter(new FileTypeExtension(".rtf", "Text Dokument"));
		fileChooser.setFileFilter(new FileTypeExtension(".csv", "Excell File"));
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			if (fileChooser.getSelectedFile().getName().endsWith(".csv")) {
				mfenster.oeffnenCSV(fileChooser.getSelectedFile().getPath());
			} else {
				mfenster.oeffnenKalendar(fileChooser.getSelectedFile().getPath());
			}
			
		}
	}

}
