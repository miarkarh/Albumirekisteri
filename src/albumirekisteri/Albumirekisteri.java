package albumirekisteri;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Luokka rekisterin käsittelyyn. Kappale listaa ei ole.
 * @author Mikko Karhunen, miarkarh@student.jyu.fi
 * @version 26.4.2019
 */
public class Albumirekisteri {
	private Albumit albumit = new Albumit(); 
	private Yhtyeet yhtyeet = new Yhtyeet();
	private Genret genret = new Genret();
	private Albumigenret albumigenret = new Albumigenret();
	
	
	/**
	 * 
	 * @return palauttaa albumien lukumäärän
	 */
	public int getAlbumeita() {
		return albumit.getLkm();
	}
	
	
	/**
	 * 
	 * @return yhtyeiden lukumäärä
	 */
	public int getYhtyeita() {
		return yhtyeet.getLkm();
	}
	
	
	/**
	 * 
	 * @return albumigenre relaatioiden lukumäärä
	 */
	public int getAlbumigenreja() {
		return albumigenret.getLkm();
	}
	
	
	/**
	 * 
	 * @return Genrien lukumäärä
	 */
	public int getGenreja() {
		return genret.getLkm();
	}	
	
	
	/**
	 * 
	 * @param i albumin paikka albumeissa
	 * @return Palauttaa albumin paikassa i
	 */
	public Albumi annaAlbumi(int i) {
		return albumit.anna(i);
	}
	

	/**
	 * 
	 * @param yid yhtyeen tunnusnumero yhtyeiden tietorakenteessa
	 * @return yhtye tunnusnumeron perusteella
	 */
	public Yhtye annaYhtye(int yid) {
		return yhtyeet.anna(yid);
	}
	
	
	/**
	 * Palauttaa albumille läydetyt genret
	 * @param albumi albumi jolle etsitään
	 * @return albumin genret
	 * @example
	 * <pre name="test">
	 * #import java.util.List;
	 * 	Albumirekisteri albumirekisteri = new Albumirekisteri();
	 *  
	 *  Albumi alb1 = new Albumi(), alb2 = new Albumi();
	 *	alb1.rekisteroi();
	 *	alb1.alustaEsim();
	 * 	alb2.rekisteroi();
	 *	alb2.alustaEsim();
	 *	albumirekisteri.lisaa(alb1);
	 * 	albumirekisteri.lisaa(alb2);
	 *  
	 *  Genre gen1 = new Genre();
	 *	gen1.rekisteroi();
	 *	gen1.alustaEsimerkki();
	 *	Albumigenre albgen = new Albumigenre();
	 *	albgen.setAlbumiNro(alb2.getTunnusNumero());
	 *	albgen.setGenreNro(gen1.getTunnusNumero());
	 *  albumirekisteri.lisaa(gen1);
	 *	albumirekisteri.lisaa(albgen);
	 *	albumirekisteri.lisaa(gen1);
	 *	albumirekisteri.lisaa(albgen);
	 *	
	 *	Albumi albumi = new Albumi();
	 *	albumi = albumirekisteri.annaAlbumi(0) === alb1;
	 *	List<Genre> genret = albumirekisteri.annaGenret(albumi);
	 *	genret.isEmpty() === true;
	 *
	 *	albumi = albumirekisteri.annaAlbumi(1) === alb2;
	 *	genret = albumirekisteri.annaGenret(albumi);
	 *	genret.get(0) === gen1;
	 *  genret.get(1) === gen1;
	 *  genret.size() === 2;
	 * </pre>
	 */
	public List<Genre> annaGenret(Albumi albumi) {
		ArrayList<Genre> loydetytGenret = new ArrayList<Genre>(); 
		for (Albumigenre albgen:annaAlbumigenret(albumi)) {
			loydetytGenret.add(genret.anna(albgen.getGenreNro()));
		}
		return loydetytGenret;
	}
	
	
	/**
	 * Palauttaa albumin albumigenre-relaatiot
	 * @param albumi albumi jolle haetaan
	 * @return albumin albumigenre relaatiot
	 */
	public List<Albumigenre> annaAlbumigenret(Albumi albumi) {
		return albumigenret.annaAlbumigenret(albumi.getTunnusNumero());
	}
	
	
	/**
	 * lisää uuden albumin
	 * @param alb albumi joka lisätään
	 */
	public void lisaa(Albumi alb) {
		albumit.lisaa(alb);
	}
	
	
	/**
	 * Lisää uuden yhtyeen yhtyeisiin
	 * @param yht yhtye joka lisätään
	 */
	public void lisaa(Yhtye yht) {
		yhtyeet.lisaa(yht);
	}
	
	
	/**
	 * lisää uuden albumigenre relaation albumigenreihin
	 * @param albgen albumigenre relaatio joka lisätään
	 */
	public void lisaa(Albumigenre albgen) {
		albumigenret.lisaa(albgen);
	}
	
	/**
	 * lisää uuden genren tietokantaan
	 * @param gen genre joka lisätään
	 */
	public void lisaa(Genre gen) {
		genret.lisaa(gen);
	}
	
	
	
    /** 
     * Palauttaa listan hakuehtoihin sopivista albumeista. 
     * @param hakuehto hakuehto minkä perusteella haetaan
     * @param haku merkkijono jonka perusteella etsitään
     * @return löytyneet albumit
     * @example 
     * <pre name="test">
     * #import java.util.Collection;
     * #import java.util.Iterator;
     *   Albumirekisteri rek = new Albumirekisteri();
     *   Albumi alb1 = new Albumi(); alb1.rekisteroi();
     *   alb1.setNimi("Abc");
     *   rek.lisaa(alb1);
     *   Collection<Albumi> loydetyt = rek.etsi("Abc", "nimi");
     *   loydetyt.size() === 1;
     *   Iterator<Albumi> ite = loydetyt.iterator();
     *   ite.next() == alb1 === true; 
     * </pre>
     */
    public Collection<Albumi> etsi(String haku, String hakuehto) {
    	Collection<Albumi> loydetytAlbumit = new ArrayList<Albumi>();
    	if (hakuehto.toLowerCase().contains("yhtye")) {
    		Collection<Yhtye> loydetytYhtyeet = yhtyeet.etsi(haku);
    		for (Yhtye yht:loydetytYhtyeet)
    			for (Albumi alb:albumit)
    				if (alb.getYhtyeenTunnusNumero() == yht.getTunnusNumero()) loydetytAlbumit.add(alb);
    		return loydetytAlbumit;
    	}
    	if (hakuehto.equalsIgnoreCase("genre")) {
    		for (Albumi alb:albumit) {
    			List<Genre> albuminGenret = annaGenret(alb);
    			for (Genre gen: albuminGenret)
    				if (gen.getNimi().toLowerCase().contains(haku.toLowerCase())) {
    					loydetytAlbumit.add(alb);
    					break;
    				}
    			
    		}
    		return loydetytAlbumit;
    	}
    	loydetytAlbumit = albumit.etsi(haku, hakuehto);
    	return loydetytAlbumit; 
    } 
    
    
    /**
     * Etsii yhtyeen rekisterin yhtyeistä sen nimen perusteella.
     * @param nimi yhtyeen nimi
     * @return läydetty yhtye
     */
    public Yhtye etsiYhtye(String nimi) {
    	for (Yhtye yht:yhtyeet)
    		if (yht.getNimi().equals(nimi)) return yht;
    	return null;
    }
    
    
    /**
     * Etsii genren rekisterin genreistä sen nimen perusteella.
     * @param nimi genren nimi
     * @return läydetty genre
     */
    public Genre etsiGenre(String nimi) {
    	for (Genre gen:genret)
    		if (gen.getNimi().equals(nimi)) return gen;
    	return null;
    }
    
    
    /**
     * Asettaa tiedostojen perusnimet
     * @param nimi uusi nimi
     */
    public void setTiedosto(String nimi) {
        File dir = new File(nimi);
        dir.mkdirs(); //??
        String hakemistonNimi = "";
        if ( !nimi.isEmpty() ) hakemistonNimi = nimi +"/";
        albumit.setTiedostonPerusNimi(hakemistonNimi + "albumit");
        genret.setTiedostonPerusNimi(hakemistonNimi + "genret");
        albumigenret.setTiedostonPerusNimi(hakemistonNimi + "albumigenret");
        yhtyeet.setTiedostonPerusNimi(hakemistonNimi + "yhtyeet");
    }
    
    
    /**
     * Lukee rekisterin tiedot tiedostosta
     * @param nimi jota käyteään lukemisessa
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        albumit = new Albumit(); // jos luetaan olemassa olevaan niin helpoin tyhjentää näin
        yhtyeet = new Yhtyeet();
        genret = new Genret();
        albumigenret = new Albumigenret();

        setTiedosto(nimi);
        albumit.lueTiedostosta();
        yhtyeet.lueTiedostosta();
        albumigenret.lueTiedostosta();
        genret.lueTiedostosta();
    }


    /**
     * Tallentaa rekisterin tiedot tiedostoon.  
     * Vaikka albumien tallettamien epäonistuisi, niin yritetään silti tallettaa
     * genret ja yhtyeet ja albumigenre relaatiot ennen poikkeuksen heittämistä.
     * @throws SailoException jos tallettamisessa ongelmia
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
            albumit.tallenna();
        } catch ( SailoException ex ) {
            virhe = ex.getMessage();
        }

        try {
            yhtyeet.tallenna();
            albumigenret.tallenna();
            genret.tallenna();
        } catch ( SailoException ex ) {
            virhe += ex.getMessage();
        }
        if ( !"".equals(virhe) ) throw new SailoException(virhe);
    }
	
    
    /**
     * Poistaa albumin rekisteristä. Tarkistaa onko albumin yhtyettä, tai genriä muualla käytässä.
     * Jos ei ole, nekin poistetaan.
     * @param alb Albumi joka poistetaan
     */
    public void poistaAlbumi(Albumi alb) {
    	Yhtye yht = annaYhtye(alb.getYhtyeenTunnusNumero());
    	List<Genre> gens = annaGenret(alb);
    	poistaAlbumigenret(alb);
    	albumit.poistaAlbumi(alb);
    	
    	if (yht != null)
    		if (etsi(yht.getNimi(), "yhtye").size()==0) poistaYhtye(yht);
    	for (Genre gen: gens)
			if (etsi(gen.getNimi(), "genre").size()==0) poistaGenre(gen);
    }
    
    
    /**
     * Poistaa albumigenre-relaatiot albumilta.
     * @param alb albumi jolta poistetaan
     */
    public void poistaAlbumigenret(Albumi alb) {
    	albumigenret.poistaAlbumigenret(alb.getTunnusNumero());
    }
    
    
    /**
     * Poistaa yhtyeen rekisterin yhtyeistä.
     * @param yht Yhtye joka poistetaan.
     */
    public void poistaYhtye(Yhtye yht) {
    	yhtyeet.poistaYhtye(yht);
    }
    
    
    /**
     * Poistaa genren rekisterin genreistä.
     * @param gen Genre joka poistetaan.
     */
    public void poistaGenre(Genre gen) {
    	genret.poistaGenre(gen);
    }
    
	
	/**
	 * Testiohjelma
	 * @param args ei käytässä
	 */
	public static void main(String args[]) {
		Albumirekisteri albumirekisteri = new Albumirekisteri();
			
			Albumi alb1 = new Albumi(), alb2 = new Albumi();
			alb1.rekisteroi();
			alb1.alustaEsim();
			alb2.rekisteroi();
			alb2.alustaEsim();
			
			albumirekisteri.lisaa(alb1);
			albumirekisteri.lisaa(alb2);
	
			Genre gen1 = new Genre();
			gen1.rekisteroi();
			gen1.alustaEsimerkki();
			Albumigenre albgen = new Albumigenre();
			albgen.setAlbumiNro(alb2.getTunnusNumero());
			albgen.setGenreNro(gen1.getTunnusNumero());
			
				albumirekisteri.lisaa(gen1);
			 	albumirekisteri.lisaa(albgen);
			 	albumirekisteri.lisaa(gen1);
			 	albumirekisteri.lisaa(albgen);
			
			System.out.println("============= rekisterin testi =================");
			
			for (int i = 0; i < albumirekisteri.getAlbumeita(); i++) {
				Albumi albumi = albumirekisteri.annaAlbumi(i);
				System.out.println();
				albumi.tulosta(System.out);
				
	            List<Genre> genret = albumirekisteri.annaGenret(albumi);
            	System.out.print("       Genret:  ");
	            for (Genre gen:genret) {
	            	gen.tulosta(System.out);
	            }
	            System.out.println();
			}
	}
}
