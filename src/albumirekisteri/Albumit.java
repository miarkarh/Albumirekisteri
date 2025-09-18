package albumirekisteri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Luokka albumeiden käsittelyyn
 * @author Mikko Karhunen, miarkarh@student.jyu.fi
 * @version 26.4.2019
 */
public class Albumit implements Iterable<Albumi> {
	private final int 	maxMaara 	= 8;
	private int 		lkm 		= 0;
    private String kokoNimi = "";
	private String tiedostonPerusNimi = "albumit";
	private Albumi[] 		alkiot 	= new Albumi[maxMaara];
	
	
	/**
	 * Lisää albumin alkioihin. Kasvatetaan kokoa jos tarvitsee.
	 * @param albumi albumi joka lisätään
     * <pre name="test">
     *  Albumit albumit = new Albumit();
     *  Albumi alb = new Albumi();
     *  for (int i = 0; i<=20; i++) {
     *  	albumit.lisaa(alb);
     *  	albumit.getLkm() === i+1;
     *  }
     *  albumit.anna(9) === alb;
     *  albumit.anna(10) === alb;
 	 * </pre>
 	 */
	public void lisaa(Albumi albumi) {
		if (alkiot.length <= lkm) alkiot = Arrays.copyOf(alkiot, alkiot.length+10);
		alkiot[lkm] = albumi;
		lkm++;
	}



	/**
	 * antaa i paikassa olevan albumin
	 * @param i paikka indeksi
	 * @return paikassa i olevan albumin
	 */
	public Albumi anna(int i) {
		return alkiot[i];
	}

	
    /**
     * Lukee albumit tiedostosta. 
     * @param tied tiedoston perusnimi
     * @throws SailoException jos lukeminen epäonnistuu
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     * #import java.util.Iterator;
     * 
     *  Albumit albumit = new Albumit();
     *  Albumi alb1 = new Albumi(), alb2 = new Albumi();
     *  alb1.alustaEsim();
     *  alb2.alustaEsim();
     *  String hakemisto = "testimiarkarh";
     *  String tiedNimi = hakemisto+"/albumit";
     *  File ftied = new File(tiedNimi+".dat");
     *  File dir = new File(hakemisto);
     *  dir.mkdir();
     *  ftied.delete();
     *  albumit.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  albumit.lisaa(alb1);
     *  albumit.lisaa(alb2);
     *  albumit.tallenna();
     *  albumit = new Albumit();            
     *  albumit.lueTiedostosta(tiedNimi);  //assd
     *  Iterator<Albumi> i = albumit.iterator();
     *  i.next(); //=== alb1;
     *  i.next(); //=== alb2;
     *  i.hasNext() === false;
     *  albumit.lisaa(alb2);
     *  albumit.tallenna();
     *  ftied.delete() === true;
     *  //File fbak = new File(tiedNimi+".bak");
     *  //fbak.delete() === true;
     *  dir.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
            kokoNimi = fi.readLine();
            if ( kokoNimi == null ) throw new SailoException("Albumirekisterin nimi puuttuu");
            String rivi;
            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Albumi albumi = new Albumi();
                albumi.parse(rivi);
                lisaa(albumi);
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
     * Tallentaa albumit tiedostoon.
     * @throws SailoException Jos tallentamisessa on ongelmia.
     */
    public void tallenna() throws SailoException {
        File ftied = new File(getTiedostonNimi());
        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            fo.println(kokoNimi);
            for (Albumi albumi : this) {
                fo.println(albumi.toString());
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }
    }
    

	/**
	 * 
	 * @return palauttaa albumien lukumäärän
	 */
	public int getLkm() {
		return lkm;
	}


    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }


    /**
     * Asettaa tiedoston perusnimen ilan tarkenninta
     * @param nimi tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String nimi) {
        tiedostonPerusNimi = nimi;
    }


    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return getTiedostonPerusNimi() + ".dat";
    }
    
    
    /** 
     * Etsii hakuehtoihin käyvät albumit
     * @param hakuehto hakuehto 
     * @param haku etsittävän kentän indeksi
     * @return tietorakenteen läytyneistä albumiistä 
     * <pre name="test">
     * #import java.util.Collection;
     * #import java.util.Iterator;
     *   Albumit albumit = new Albumit();
     *   Albumi alb1 = new Albumi(); alb1.rekisteroi();
     *   alb1.setNimi("Abc");
     *   albumit.lisaa(alb1);
     *   Collection<Albumi> loydetyt = albumit.etsi("Abc", "nimi");
     *   loydetyt.size() === 1;
     *   Iterator<Albumi> ite = loydetyt.iterator();
     *   ite.next() == alb1 === true;
     * </pre>
     */
    public Collection<Albumi> etsi(String haku, String hakuehto) {
        Collection<Albumi> loytyneet = new ArrayList<Albumi>(); 
        for (Albumi albumi : this) {
        	if (albumi.getEhto(hakuehto).toLowerCase().contains(haku.toLowerCase()))
        		loytyneet.add(albumi);  
        } 
        return loytyneet; 
    }
    
    
    /**
     * Luokka albumiten iteroimiseksi.
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Albumit albumit = new Albumit();
     * Albumi alb1 = new Albumi(), alb2 = new Albumi();
     * alb1.rekisteroi(); alb2.rekisteroi();
     *
     * albumit.lisaa(alb1); 
     * albumit.lisaa(alb2); 
     * albumit.lisaa(alb1); 
     * 
     * StringBuffer ids = new StringBuffer(30);
     * for (Albumi Albumi:albumit)   // Kokeillaan for-silmukan toimintaa
     *   ids.append(" "+Albumi.getTunnusNumero());           
     * 
     * String tulos = " " + alb1.getTunnusNumero() + " " + alb2.getTunnusNumero() + " " + alb1.getTunnusNumero();
     * 
     * ids.toString() === tulos; 
     * 
     * ids = new StringBuffer(30);
     * for (Iterator<Albumi>  i=albumit.iterator(); i.hasNext(); ) { // ja iteraattorin toimintaa
     *   Albumi albumi = i.next();
     *   ids.append(" " + albumi.getTunnusNumero());           
     * }
     * 
     * ids.toString() === tulos;
     * 
     * Iterator<Albumi>  i=albumit.iterator();
     * i.next() == alb1  === true;
     * i.next() == alb2  === true;
     * i.next() == alb1  === true;
     * 
     * i.next();  #THROWS NoSuchElementException
     * </pre>
     */
    public class AlbumitIterator implements Iterator<Albumi> {
        private int kohdalla = 0;


        /**
         * Onko olemassa vielä seuraavaa albumia
         * @see java.util.Iterator#hasNext()
         * @return true jos on vielä albumeita
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }


        /**
         * Annetaan seuraava albumi
         * @return seuraava albumi
         * @throws NoSuchElementException jos seuraava alkiota ei enää ole
         * @see java.util.Iterator#next()
         */
        @Override
        public Albumi next() throws NoSuchElementException {
            if ( !hasNext() ) throw new NoSuchElementException("Ei oo");
            return anna(kohdalla++);
        }


        /**
         * Tuhoamista ei ole toteutettu
         * @throws UnsupportedOperationException aina
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Me ei poisteta");
        }
    }
    
    
    /**
     * Palautetaan iteraattori albumiistään.
     * @return albumi iteraattori
     */
    @Override
    public Iterator<Albumi> iterator() {
        return new AlbumitIterator();
    }
    
    
    /**
     * Poistaa albumin albumien joukosta
     * @param alb albumi joka poistetaan
     */
    public void poistaAlbumi(Albumi alb) {
    	int paikka = etsiPaikka(alb);
    	if (paikka < 0) return; 
        lkm--; 
        for (int i = paikka; i < lkm; i++) 
            alkiot[i] = alkiot[i + 1]; 
        alkiot[lkm] = null;
    }
    
    
    /**
     * Etsii albumin paikan.
     * @param alb albumi jonka paikka etsitään 
     * @return läytyneen albumin indeksi tai -1 jos ei läydy 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Albumit albumit = new Albumit(); 
     * Albumi alb1 = new Albumi(), alb2 = new Albumi(), alb3 = new Albumi(); 
     * alb1.rekisteroi(); alb2.rekisteroi(); alb3.rekisteroi();
     * albumit.lisaa(alb1); albumit.lisaa(alb2); albumit.lisaa(alb3); 
     * albumit.etsiPaikka(alb1) === 0; 
     * albumit.etsiPaikka(alb2) === 1; 
     * </pre> 
     */ 
    public int etsiPaikka(Albumi alb) {
        for (int i = 0; i < lkm; i++) 
            if (alb.equals(alkiot[i])) return i; 
        return -1; 
    } 
}
