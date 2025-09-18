package albumirekisteri;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Luokka genrelle
 * @author Mikko Karhunen, miarkarh@student.jyu.fi
 * @version 26.4.2019
 */
public class Genre {
	private int tunnusNro;
	private String nimi;
	
	private static int seuraavaNro = 1;
	
	
	/**
	 * @return genren tunnusnumeron
	 */
	public int getTunnusNumero() {
		return tunnusNro;
	}
	
	
	/**
	 * @return genren nimen
	 */
	public String getNimi() {
		return nimi;
	}
	
	
	/**
	 * Asettaa genrelle nimen
	 * @param gennimi nimi joka asetetaan
	 */
	public void setNimi(String gennimi) {
		nimi = gennimi;
	}
	
	
    /**
     * Asettaa tunnusnumeron genrelle varmistaen samalla että
     * seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nr asetettava tunnusnumero
     */
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if ( tunnusNro >= seuraavaNro ) seuraavaNro = tunnusNro + 1;
    }
	
	
	/**
	 * alustaa esimerkki genren nimen
	 */
	public void alustaEsimerkki() {
		nimi = "Jazz";
	}
	
	
	/**
	 * 
	 * @return genren tunnusnumero
	 * @example
     * <pre name="test">
     *   Genre a1 = new Genre();
     *   a1.getTunnusNumero() === 0;
     *   a1.rekisteroi();
     *   Genre a2 = new Genre();
     *   a2.rekisteroi();
     *   int n1 = a1.getTunnusNumero();
     *   int n2 = a2.getTunnusNumero();
     *   n1 === n2-1;
     * </pre>
     */
	public int rekisteroi() {
		tunnusNro = seuraavaNro;
		seuraavaNro++;
		return tunnusNro;
	}


	/**
	 * Tulostaa genren tiedot
	 * @param out virta jonne tulostetaan
	 */
	public void tulosta(PrintStream out) {
		out.print(nimi);
	}
	
	
	/**
	 * Muuntaa tietovirra
	 * @param os tietovirta joka muunnetaan
	 */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * Palauttaa genren tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return genre tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Genre genre = new Genre();
     *   genre.parse("   2 | Funk ");
     *   genre.toString()    === "2|Funk";
     * </pre>
     */
    @Override
    public String toString() {
        return "" + tunnusNro + "|" + nimi;
    }


    /**
     * Selvitää genren tiedot | erotellusta merkkijonosta.
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusnro.
     * @param rivi josta genren tiedot otetaan
     * @example
     * <pre name="test">
     *   Genre genre = new Genre();
     *   genre.parse("  10 | alternative ");
     *   genre.getTunnusNumero() === 10;
     *   genre.toString()    === "10|alternative";
     *   
     *   genre.rekisteroi();
     *   int n = genre.getTunnusNumero();
     *   genre.parse(""+(n+20));
     *   genre.rekisteroi();
     *   genre.getTunnusNumero() === n+20+1;
     *   genre.toString()     === "" + (n+20+1) + "|alternative";
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', tunnusNro));
        nimi = Mjonot.erota(sb, '|', nimi);
    }
    
    
     /**
      * Genren testiohjelma.
      * @param args ei käytässä
      */
     public static void main(String[] args) {
         Genre gen = new Genre();
         gen.rekisteroi();
         gen.alustaEsimerkki();
         gen.tulosta(System.out);
    }
}
