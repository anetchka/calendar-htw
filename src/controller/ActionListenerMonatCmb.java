package controller;
/**
 * @author Anna Kovtun s0552342
 * @version Eclipse Neon
 * @since 17.06.2017
 * Letzte Aenderung: 17.06.2017 
 * Dateiname: ActionListenerMonatCmb.java
 * Beschreibung: eine Klasse, die die ActionListener implementiert 
 * und je nach dem Monatsnamen wird verschiedene Bilder gezeigt
 */
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;


public class ActionListenerMonatCmb implements ActionListener {
	//Attribute
	private JLabel picLab = new JLabel();
	//Konstruktor
	public ActionListenerMonatCmb(JLabel picLab) {
		this.picLab = picLab;
	}

	/**
	 * @param e(ActionEvent)
	 * 
	 * Eine Funktion, die verschieden Bilder abhaengig vom Monatsnamen zeigt
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox cb = (JComboBox)e.getSource();
		String monatName = (String)cb.getSelectedItem();
		ImageIcon imageIcon = new ImageIcon(new ImageIcon("meineBilder\\" + monatName + ".jpg").
				getImage().getScaledInstance(250, 200, Image.SCALE_DEFAULT));
		picLab.setIcon(imageIcon);
	}
}
