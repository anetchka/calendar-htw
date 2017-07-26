package controller;
/**
 * @author Anna Kovtun s0552342
 * @version Eclipse Neon
 * @since 22.06.2017
 * Letzte Aenderung: 23.06.2017 
 * Dateiname: ActionListenerSichern.java
 * Beschreibung: eine Klasse, die die ActionListener implementiert 
 * und zwar ein File in bestimmten Formaten speichert(.rtf)
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

import fileExtension.FileTypeExtension;
import gui.MeinFenster;

public class ActionListenerSichernJahresplan implements ActionListener {

	private MeinFenster mfenster = null;
	
	public ActionListenerSichernJahresplan(MeinFenster mfenster) {
		super();
		this.mfenster = mfenster;
	}

	/**
	 * @param event(ActionEvent) ActionEvent
	 * 
	 * Eine Funktion, die die Datei in .rtf Format speichert
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		JFileChooser fileChooser  = new JFileChooser();
		fileChooser.setDialogTitle("Jahresplaner Sichern");
		fileChooser.setFileFilter(new FileTypeExtension(".rtf", "RTF Dokument"));
		if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			mfenster.speicherJahresplaner(fileChooser.getSelectedFile().getPath());	
		}
	}

}
