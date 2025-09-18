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
 * Luokka genrien joukon käsittelyyn
 * @author Mikko Karhunen, miarkarh@student.jyu.fi
 * @version 26.4.2019
 */
public class Genret implements Iterable<Genre> {
    private String tiedostonPerusNimi = "";
    private final Collection<Genre> alkiot = new ArrayList<Genre>();
	
	/**
	 * lisää genren alkioihin
	 * @param genre genre joka lisätään
     * <pre name="test">
     *  Genret genret = new Genret();
     *  for (int i = 0; i<=10; i++) {
     *  	Genre gen = new Genre();
     *  	gen.rekisteroi();
     *  	genret.lisaa(gen);
     *  	genret.getLkm() === i+1;
     *  }
     *  genret.anna(1).getTunnusNumero() === 1;
     *  genret.anna(7).getTunnusNumero() === 7;
     *  genret.anna(10).getTunnusNumero() === 10;
 	 * </pre>
 	 */
	public void lisaa(Genre genre) {
		alkiot.add(genre);
	}

	
    /**
     * Lukee genret tiedostosta.
     * @param tied tiedoston nimen alkuosa
     * @throws SailoException jos lukeminen epäonnistuu
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     * #import java.util.Iterator;
     *  Genret genret = new Genret();
     *  Genre jazz1 = new Genre(); jazz1.alustaEsimerkki();
     *  Genre jazz2 = new Genre(); jazz2.alustaEsimerkki();
     *  Genre jazz3 = new Genre(); jazz3.alustaEsimerkki(); 
     *  String tiedNimi = "testirekis";
     *  File ftied = new File(tiedNimi+".dat");
     *  ftied.delete();
     *  genret.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  genret.lisaa(jazz1);
     *  genret.lisaa(jazz2);
     *  genret.lisaa(jazz3);
     *  genret.tallenna();
     *  genret = new Genret();
     *  genret.lueTiedostosta(tiedNimi);
     *  Iterator<Genre> i = genret.iterator();
     *  i.next().toString() === jazz1.toString();
     *  i.next().toString() === jazz2.toString();
     *  i.next().toString() === jazz3.toString();
     *  i.hasNext() === false;
     *  genret.lisaa(jazz3);
     *  genret.tallenna();
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
                Genre gen = new Genre();
                gen.parse(rivi);
                lisaa(gen);
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
	 * palauttaa genren sen tunnusuneron avulla
	 * @param gTunNro genren tunnusnumero jota haetaan
	 * @return genre jonka tunnusnumero on oikea
	 */
	public Genre anna(int gTunNro) {
		for (Genre g:alkiot) {
			if (g.getTunnusNumero()==gTunNro)
				return g;
		}
		return null;
	}
    
    
    /**
     * Tallentaa genret tiedostoon.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna() throws SailoException {
        File ftied = new File(getTiedostonNimi());

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            for (Genre gen: alkiot) {
            	if (gen != null) fo.println(gen.toString());
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
	 * Palauttaa genrien lukumäärän rekisterissä. 
	 * @return genrien lukumäärä
	 */
	public int getLkm() {
		return alkiot.size();
	}


	/**
	 * Iteraattori genreille
	 */
	@Override
	public Iterator<Genre> iterator() {
		return alkiot.iterator();
	}
    
	
	/**
	 * Poistaa genren genrien joukosta
	 * @param gen genre joka poistetaan.
	 */
	public void poistaGenre(Genre gen) {
		alkiot.remove(gen);
	}
	
	
    /**
     * Testiohjelma genrille
     * @param args ei käytässä
     */
    public static void main(String[] args) {
    	Genret genret = new Genret();
    	Genre gen1 = new Genre();
    	gen1.rekisteroi();
    	gen1.alustaEsimerkki(); 
    	Genre gen2 = new Genre();
    	gen2.rekisteroi();
    	gen2.alustaEsimerkki();
	    genret.lisaa(gen1);
		genret.lisaa(gen2);
			
        for (Genre g:genret) {
        	g.tulosta(System.out);
        }

    }
}
