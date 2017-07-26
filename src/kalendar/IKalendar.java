package kalendar;
/**
 * 
 * @author Bannert
 */

public interface IKalendar {
	
	/**
     * Beschreibung: Erzeugt ein Monatsblatt des Jahreskalenders 
     * und gibt das Monatsblatt in Stringform zurÃ¼ck. 
     * Das Monatsblatt wird in einenm String mit Zeilenumbruechen abgelegt. 
     * Das Monatsblatt enthÃ¤lt immer die Kopfzeile fÃ¼r den entsprechenden Monat.
     * Beispiel:
     * 
    ******************* Mai 2017 ******************
    So    Mo    Di    Mi    Do    Fr    Sa
          01    02    03    04    05    06
    07    08    09    10    11    12    13
    14    15    16    17    18    19    20
    21    22    23    24    25    26    27
    28    29    30    31
     * 
     * @param jahr - das Jahr zum Monat 
     * @param monat - der Monat, das angezeigt werden soll
     * @return String - der zusammengebastelte String
     */
     public String getMonatsblatt(int jahr, int monat);
    
    /**
     * Methode, um die Kopfzeile eines Monatsblattes in Stringform zurÃ¼ckzugeben. 
     * Dieses erfordert die Uebergabe des Monats und des Jahres als Integer 
     * und liefert die Kopfzeile komplett als String zurueck.
     * @param jahr - das Jahr zum Monat 
     * @param monat - der Monat, das angezeigt werden soll
     * @return liefert die Kopfzeile komplett als String zurueck
     *    Beisiel:
        ******************* Mai 2016 ******************
        So    Mo    Di    Mi    Do    Fr    Sa
     *         
     */
     public String getKopfzeileMonatsblatt(int jahr, int monat);
    
    /**
     * Methode zur Ausgabe eines Monatsblattes auf der Konsole
     * 
     * @param jahr - das Jahr zum Monat 
     * @param monat - der Monat, das angezeigt werden soll
     */
    public void zeigeMonat(int jahr, int monat);
    
    /**
     * Methode zur Ausgabe aller Monatsblaetter eines Jahres auf der Konsole
     * @param jahr - das Jahr, das angezeigt werden soll
     */
    public void zeigeJahr(int jahr);
    
    /**
     * Die Methode liest eine Monatszahl ein, 
     * evaluiert diese Zahl auf den gÃ¼ltigen Bereich [1,..,12]
     * und gibt die eingelese und Ã¼berrÃ¼fte Monatszahl zurÃ¼ck.
     * Wenn der Benutzer keine gÃ¼ltige Monatszahl eingibt, verbleibt 
     * das Programm in der Einlese-Schleife.
     * @return int - der eingelesene Monat 
     */
    public int liesMonat(); 

    /**
     * Die Methode liest eine Jahreszahl ein, 
     * evaluiert diese Zahl auf den gÃ¼ltige Ziffern 
     * und gibt die eingelese und Ã¼berrÃ¼fte Jahreszahl zurÃ¼ck.
     * Wenn der Benutzer keine gÃ¼ltige Jahresangabe macht, verbleibt 
     * das Programm in der Einlese-Schleife.
     * @return int - das eingelesene Jahr 
     */
     public int liesJahr();

    /**
     * stellt das Auswahlmenue dar und liest die Benutzeruswahl ein
     * die Benutzerauswahl wird evaluiert auf GÃ¼ltigkeit
     * Wenn der Benutzer keine gÃ¼ltige Engabe macht, verbleibt 
     * das Programm in der Einlese-Schleife.
     */
    public void auswahlMenue();

}
