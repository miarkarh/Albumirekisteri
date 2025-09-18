package albumirekisteri;


/**
 *  Virheluokka virheelle tilan loppuessa
 * @author Mikko Karhunen, miarkarh@student.jyu.fi
 * @version 26.4.2019
 */
public class SailoException extends Exception{
    private static final long serialVersionUID = 1L;


    /**
     * Virhe viesti
     * @param viesti virhe viesti joka näytetään
     */
    public SailoException(String viesti) {
        super(viesti);
    }
}
