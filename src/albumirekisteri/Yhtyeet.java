package albumirekisteri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Yhtyeiden käsittely luokka
 * @author Mikko Karhunen, miarkarh@student.jyu.fi
 * @version 26.4.2019
 */
public class Yhtyeet implements Iterable<Yhtye> {
    private String tiedostonPerusNimi = "";
	private final Collection<Yhtye> alkiot = new ArrayList<Yhtye>();

	
	/**
	 * Muodostaja 
	 */
	public Yhtyeet() {
		
	}
	

	/**
	 * Palauttaa yhtyeen sen tunnusnumeron perusteella
	 * @param i yhtyeen tunnusnumero
	 * @return Yhtye jonka tunnusnumero täsmää
	 */
	public Yhtye anna(int i) {
		for (Yhtye yht:alkiot) {
			if (yht.getTunnusNumero() == i) return yht;
		}
		return null;
	}
	
	
	/**
	 * Lisää yhtyeen alkioihin
	 * @param yht yhtye joka lisätään
	 */
	public void lisaa(Yhtye yht) {
		alkiot.add(yht);
	}	
	
	
    /**
     * Lukee yhtyeet tiedostosta.
     * @param tied tiedoston nimen alkuosa
     * @throws SailoException jos lukeminen epäonnistuu
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     * #import java.util.Iterator;
     *  Yhtyeet yhtyeet = new Yhtyeet();
     *  Yhtye abc1 = new Yhtye(); abc1.alustaEsimerkki();
     *  Yhtye abc2 = new Yhtye(); abc2.alustaEsimerkki();
     *  Yhtye abc3 = new Yhtye(); abc3.alustaEsimerkki(); 
     *  String tiedNimi = "testirekis";
     *  File ftied = new File(tiedNimi+".dat");
     *  ftied.delete();
     *  yhtyeet.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  yhtyeet.lisaa(abc1);
     *  yhtyeet.lisaa(abc2);
     *  yhtyeet.lisaa(abc3);
     *  yhtyeet.tallenna();
     *  yhtyeet = new Yhtyeet();
     *  yhtyeet.lueTiedostosta(tiedNimi);
     *  Iterator<Yhtye> i = yhtyeet.iterator();
     *  i.next().toString() === abc1.toString();
     *  i.next().toString() === abc2.toString();
     *  i.next().toString() === abc3.toString();
     *  i.hasNext() === false;
     *  yhtyeet.lisaa(abc3);
     *  yhtyeet.tallenna();
     *  ftied.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {

            String rivi;
            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Yhtye yht = new Yhtye();
                yht.parse(rivi);
                lisaa(yht);
            }
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }

    
    /**
     * Luetaan aikaisemmin annetun nimisestä tiedostosta
     * @throws SailoException jos tulee poikkeus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }
    
    
    /**
     * Tallentaa yhtyeet tiedostoon.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna() throws SailoException {
        File ftied = new File(getTiedostonNimi());
        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            for (Yhtye yht: alkiot) {
                fo.println(yht.toString());
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }
    }
    
	
    /**
     * Asettaa tiedoston perusnimen ilan tarkenninta
     * @param tied tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String tied) {
        tiedostonPerusNimi = tied;
    }


    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }


    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return tiedostonPerusNimi + ".dat";
    }


	
	/**
	 * Palauttaa yhtyeiden lukumäärän rekisterissä. 
	 * @return yhtyeiden lukumäärä
	 */
	public int getLkm() {
		return alkiot.size();
	}
	
    
	/**
	 * Iteraattori yhtyeille
	 */
	@Override
	public Iterator<Yhtye> iterator() {
		return alkiot.iterator();
	}
	
	
    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien albumiten viitteet
     * @param haku hakuehto minkä perusteella haetaan
     * @return läytyneet yhtyeet yhtyeistä.
     * <pre name="test">
     * #import java.util.Collection;
     * #import java.util.Iterator;
     *   Yhtyeet yhts = new Yhtyeet();
     *   Yhtye yht1 = new Yhtye(); yht1.rekisteroi();
     *   yht1.setNimi("Abc");
     *   yhts.lisaa(yht1);
     *   Collection<Yhtye> loydetyt = yhts.etsi("Abc");
     *   loydetyt.size() === 1;
     *   Iterator<Yhtye> ite = loydetyt.iterator();
     *   ite.next() == yht1 === true; 
     * </pre>
     */
    public Collection<Yhtye> etsi(String haku) {
        Collection<Yhtye> loytyneet = new ArrayList<Yhtye>(); 
        for (Yhtye yht:alkiot) {
        	if (yht.getNimi().toLowerCase().contains(haku.toLowerCase()))
        		loytyneet.add(yht);  
        } 
        return loytyneet; 
    }
    
    
    /**
     * poistaa yhtyeen yhtyeistä
     * @param yht poistettava yhtye.
     */
    public void poistaYhtye(Yhtye yht) {
    	alkiot.remove(yht);
    }
}
