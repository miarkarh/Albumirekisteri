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
 * Albumigenre relaatioiden lista luokka
 * @author Mikko Karhunen, miarkarh@student.jyu.fi
 * @version 26.4.2019
 */
public class Albumigenret implements Iterable<Albumigenre>{
    private String tiedostonPerusNimi = "";
	
	private final Collection<Albumigenre> alkiot = new ArrayList<Albumigenre>();
	
	
	/**
	 * Muodostaja
	 */
	public Albumigenret() {
		
	}
	
	
	/**
	 * Lisää uuden albumigenren albumigenre-relaatioiden joukkoon
	 * @param albgen albumigenre joka lisätään
	 */
	public void lisaa(Albumigenre albgen) {
		alkiot.add(albgen);
	}
	
	
	/**
	 * Palauttaa kaikki albumin albumigenre relaatiot.
	 * @param albNro albumin tunnusNro, albumi jolle haetaan relaatiot.
	 * @return Albumigenre relaatiot
	 */
    public ArrayList<Albumigenre> annaAlbumigenret(int albNro) {
        ArrayList<Albumigenre> relaatiot = new ArrayList<Albumigenre>();
        for (Albumigenre albgen : alkiot)
            if (albgen.getAlbumiNro() == albNro) relaatiot.add(albgen);
        return relaatiot;
    }
    

    /**
     * Lukee albumigenret tiedostosta.
     * @param tied tiedoston nimen alkuosa
     * @throws SailoException jos lukeminen epäonnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     * #import java.util.Iterator;
     *  Albumigenret albgenit = new Albumigenret();
     *  Albumigenre esim1 = new Albumigenre(); esim1.alustaEsim(1,2);
     *  Albumigenre esim2 = new Albumigenre(); esim2.alustaEsim(5,2);
     *  Albumigenre esim3 = new Albumigenre(); esim3.alustaEsim(1,3); 
     *  String tiedNimi = "testialbgen";
     *  File ftied = new File(tiedNimi+".dat");
     *  ftied.delete();
     *  albgenit.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  albgenit.lisaa(esim1);
     *  albgenit.lisaa(esim2);
     *  albgenit.lisaa(esim3);
     *  albgenit.tallenna();
     *  albgenit = new Albumigenret();
     *  albgenit.lueTiedostosta(tiedNimi);
     *  Iterator<Albumigenre> i = albgenit.iterator();
     *  i.next().toString() === esim1.toString();
     *  i.next().toString() === esim2.toString();
     *  i.next().toString() === esim3.toString();
     *  i.hasNext() === false;
     *  albgenit.lisaa(esim3);
     *  albgenit.tallenna();
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
                Albumigenre albgen = new Albumigenre();
                albgen.parse(rivi);
                lisaa(albgen);
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
     * Tallentaa albumigenret tiedostoon.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna() throws SailoException {
        File ftied = new File(getTiedostonNimi());
        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
        	fo.println(";aid|gid");
            for (Albumigenre albgen: alkiot) { //?? this vai alkiot
                fo.println(albgen.toString());
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }
    }
	
	
	/**
	 * Palauttaa albumigenre relaatioiden lukumäärän rekisterissä. 
	 * @return albumigenre relaatioiden lukumäärä
	 */
	public int getLkm() {
		return alkiot.size();
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
     * Iteraattori albumigenre relaatio listalle.
     */
	@Override
	public Iterator<Albumigenre> iterator() {
		return alkiot.iterator();
	}
	
	
	/**
	 * poistaa annetun albumin tunnusnumeron perusteella sen relaatiot genreihin.
	 * @param albumiNro Albumin tunnusnumero, jolta poistetaan albgen relaatiot
     * @example
     * <pre name="test">
     *  #import java.util.List;
     *  Albumigenret albumigenret = new Albumigenret();
     *  Albumigenre albgen1 = new Albumigenre(); albgen1.alustaEsim(2,4);
     *  Albumigenre albgen2 = new Albumigenre(); albgen2.alustaEsim(6,2);
     *  Albumigenre albgen3 = new Albumigenre(); albgen3.alustaEsim(9,4); 
     *  Albumigenre albgen4 = new Albumigenre(); albgen4.alustaEsim(2,5); 
     *  Albumigenre albgen5 = new Albumigenre(); albgen5.alustaEsim(2,6); 
     *  albumigenret.lisaa(albgen1);
     *  albumigenret.lisaa(albgen2);
     *  albumigenret.lisaa(albgen3);
     *  albumigenret.lisaa(albgen4);
     *  albumigenret.poistaAlbumigenret(2); albumigenret.getLkm() === 2;
     *  albumigenret.poistaAlbumigenret(4);   albumigenret.getLkm() === 2;
     *  List<Albumigenre> loydetyt = albumigenret.annaAlbumigenret(9);
     *  loydetyt.size() === 1; 
     *  loydetyt.get(0) === albgen3;
     * </pre>
     */
	public void poistaAlbumigenret(int albumiNro) {
		Albumigenre poistettava = null;
		for (Albumigenre albgen:alkiot) {
			if (albgen.getAlbumiNro()==albumiNro) {
				poistettava = albgen;
				break;
			}
		}
		if (poistettava == null) return;
		alkiot.remove(poistettava);
		poistaAlbumigenret(albumiNro);
	}
	
	
	/**
	 * Testaus ohjelma luokalle albumigenret
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		Albumigenret albumigenret = new Albumigenret();
		Albumigenre albgen1 = new Albumigenre();
		albgen1.rekisteroi();
		albgen1.setAlbumiNro(1);
		Genre gen = new Genre();
		gen.rekisteroi();
		gen.alustaEsimerkki();
		albgen1.setGenreNro(gen.getTunnusNumero());
		albumigenret.lisaa(albgen1);
		
		for (Albumigenre albgen:albumigenret.alkiot)
			System.out.println(albgen.getAlbumiNro() +" "+ albgen.getGenreNro());
	}
}
