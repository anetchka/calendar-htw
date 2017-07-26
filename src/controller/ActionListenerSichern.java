package controller;
/**
 * @author Anna Kovtun s0552342
 * @version Eclipse Neon
 * @since 17.06.2017
 * Letzte Aenderung: 17.06.2017 
 * Dateiname: ActionListenerSichern.java
 * Beschreibung: eine Klasse, die die ActionListener implementiert 
 * und zwar ein File in bestimmten Formaten speichert(.rtf)
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

import fileExtension.FileTypeExtension;
import gui.MeinFenster;

public class ActionListenerSichern implements ActionListener {

	private MeinFenster mfenster = null;
	
	public ActionListenerSichern(MeinFenster mFenster) {
		super();
		this.mfenster = mFenster;
	}
	
	/**
	 * @param e(ActionEvent) ActionEvent
	 * 
	 * Eine Funktion, die die Datei in .rtf Format speichert
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		JFileChooser fileChooser  = new JFileChooser();
		fileChooser.setDialogTitle("Datei Sichern");
		fileChooser.setFileFilter(new FileTypeExtension(".rtf", "Text Dokument"));
		if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			mfenster.speicherKalender(fileChooser.getSelectedFile().getPath());
		}
	}

}
