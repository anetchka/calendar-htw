package fileExtension;
/**
 * @author Anna Kovtun s0552342
 * @version Eclipse Neon
 * @since 22.06.2017
 * Letzte Aenderung: 22.06.2017 
 * Dateiname: FileTypeExtension.java
 * Beschreibung: eine Klasse, die zwei FUnktionen vom FileFilter implementiert
 * 
 */
import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FileTypeExtension extends FileFilter{
	//Attibute
	private String extension;
	private String description;
	//Konstruktor
	public FileTypeExtension(String extension, String description) {
		super();
		this.extension = extension;
		this.description = description;
	}
	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}
		return f.getName().endsWith(extension);
	}
	@Override
	public String getDescription() {
		return description + String.format(" (%s)", extension);	
	
	}
	
	
}
