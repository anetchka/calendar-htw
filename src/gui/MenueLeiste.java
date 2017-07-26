package gui;
import javax.swing.JComboBox;
/**
 * @author Anna Kovtun s0552342
 * @version Eclipse Neon
 * @since 17.06.2017
 * Letzte Aenderung: 17.06.2017 
 * Dateiname: MenueLeiste.java
 * Beschreibung: eine Klasse, die MenueBar und Menue Items implimentiert
 */
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import controller.ActionListenerAutor;
import controller.ActionListenerBeenden;
import controller.ActionListenerFeiertagenOhneSaSo;
import controller.ActionListenerHelp;
import controller.ActionListenerJahresPlanerShow;
import controller.ActionListenerLookAndFeel;
import controller.ActionListenerOeffnen;
import controller.ActionListenerSichern;
import controller.ActionListenerSichernJahresplan;
import controller.ActionistenerFeiertagezeigen;

public class MenueLeiste extends JMenuBar {
	private MeinFenster mfenster = null;
	//Menue Item
	private JMenu 	menueDatei = new JMenu("Datei"),
					menueKalender = new JMenu("Kalender"),
					menueJahresplaner = new JMenu("JahresPlanner"),
					menueLookAndFeel = new JMenu("Look & Feel"),
					menueInfo = new JMenu("Info");
	
	//Datei MenuItems
	private JMenuItem 	oeffnenItem = new JMenuItem("Oeffnen"),
						sichernItem = new JMenuItem("Sichern"),
						beendenItem = new JMenuItem("Beenden");
	
	//Kalendar MenuItems
	private JMenuItem 	feiertageItem = new JMenuItem("Feirtage"),
						feiertageSoSaItem = new JMenuItem("feiertageSoSa");
			
	//Jahresplaner MenuItems
	private JMenuItem 	showItem = new JMenuItem("Show"),
						saveItem = new JMenuItem("Save");
	
	//Look&Feel MenuItems
	private JMenuItem 	windowsItem = new JMenuItem("Windows"),
						metalItem = new JMenuItem("Metal"),
						motifItem = new JMenuItem("Motif");
	
	//Info MenuItems
	private JMenuItem 	autorItem = new JMenuItem("Autor"),
						helpItem = new JMenuItem("Help");

	public MenueLeiste(MeinFenster mfenster, JComboBox<Integer> cbJahr) {
		super();
		this.mfenster = mfenster;
		//Menue Datei
		menueDatei.add(oeffnenItem);
		menueDatei.add(sichernItem);
		menueDatei.add(beendenItem);
		
		//Listener to Datei Items anbinden
		oeffnenItem.addActionListener(new ActionListenerOeffnen(mfenster));
		sichernItem.addActionListener(new ActionListenerSichern(mfenster));
		beendenItem.addActionListener(new ActionListenerBeenden());
		
		//Kalendar Menue
		menueKalender.add(feiertageItem);
		menueKalender.add(feiertageSoSaItem);
		
		//Listener to Kalendar Items anbinden
		
		feiertageItem.addActionListener(new ActionistenerFeiertagezeigen(cbJahr));
		feiertageSoSaItem.addActionListener(new ActionListenerFeiertagenOhneSaSo(cbJahr));
		
		//Jahresplaner Menue
		menueJahresplaner.add(showItem);
		menueJahresplaner.add(saveItem);
	
		
		//Listener an Jahresplaner Items anbinden
		showItem.addActionListener(new ActionListenerJahresPlanerShow(mfenster));
		saveItem.addActionListener(new ActionListenerSichernJahresplan(mfenster));
		
		//Look&Feel Menue
		menueLookAndFeel.add(windowsItem);
		menueLookAndFeel.add(metalItem);
		menueLookAndFeel.add(motifItem);
		
		//Listener to Look&Feel anbinden
		ActionListenerLookAndFeel lafListener = new ActionListenerLookAndFeel(mfenster);
		windowsItem.addActionListener(lafListener);
		metalItem.addActionListener(lafListener);
		motifItem.addActionListener(lafListener);
		
		//Info Menue
		menueInfo.add(autorItem);
		menueInfo.add(helpItem);
		
		//Listener an Info Items anbinden
		autorItem.addActionListener(new ActionListenerAutor(mfenster));
		helpItem.addActionListener(new ActionListenerHelp(mfenster));

		
		//Menue auf MenueBar setzen
		this.add(menueDatei);
		this.add(menueKalender);
		this.add(menueJahresplaner);
		this.add(menueLookAndFeel);
		this.add(menueInfo);
	}
}
