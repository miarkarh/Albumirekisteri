 package albumirekisteri;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Luokka albumigenre relaatiolle.
 * @author Mikko Karhunen, miarkarh@student.jyu.fi
 * @version 26.4.2019
 */
public class Albumigenre {
	private int tunnusNro;
	private int albumiNro;
	private int genreNro;
	
	private static int seuraavaNro = 1;
	
	
	/**
	 * Alustaa albumigenren
	 */
	public Albumigenre() {

	}
	
	
	/**
	 * asettaa albumigenre relaatioon albumin tunnusnumeron
	 * @param albumiNro albumin tunnusnumero
	 */
	public void setAlbumiNro(int albumiNro) {
		this.albumiNro = albumiNro;
	}
	
	
	/**
	 * Asettaa genrenumeron
	 * @param genreNro genren tunnusnumero
	 */
	public void setGenreNro(int genreNro) {
		this.genreNro = genreNro;
	}
	
	
	/**
	 * @return albumigenren tunnusnumeron
	 */
	public int getTunnusNro() {
		return tunnusNro;
	}
	
	
	/**
	 * @return albumin numero
	 */
	public int getAlbumiNro() {
		return albumiNro;
	}
	
	
	/**
	 * @return genren numero
	 */
	public int getGenreNro() {
		return genreNro;
	}
	
	
	/**
	 * rekisteräi albumigenren tietorekisteriin
	 * @return albumigenren tunnusnumero
     * @example
     * <pre name="test">
     *   Albumigenre a1 = new Albumigenre();
     *   a1.getTunnusNro() === 0;
     *   a1.rekisteroi();
     *   Albumigenre a2 = new Albumigenre();
     *   a2.rekisteroi();
     *   int t1 = a1.getTunnusNro();
     *   int t2 = a2.getTunnusNro();
     *   t1 === t2-1;
     * </pre>
     */
	public int rekisteroi() {
		tunnusNro = seuraavaNro;
		seuraavaNro++;
		return tunnusNro;
	}	
	
	
  /**
  * Palauttaa albumigenren tiedot merkkijonona jonka voi tallentaa tiedostoon.
  * @return albumigenre relaatio tolppaeroteltuna merkkijonona
  */
 @Override
 public String toString() {
     return "" + albumiNro + "|" + genreNro;
 }


 /**
  * Selvitää albumigenre relaation tiedot | erotellusta merkkijonosta.
  * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusnro.
  * @param rivi josta relaation tiedot otetaan
  * @example
  * <pre name="test">
  *   Albumigenre albgen = new Albumigenre();
  *   albgen.parse("   2   |  10  ");
  *   albgen.getAlbumiNro() === 2;
  *   albgen.getGenreNro() === 10;
  *   albgen.toString()    === "2|10";
  * </pre>
  */
 public void parse(String rivi) {
     StringBuffer sb = new StringBuffer(rivi);
     albumiNro = Mjonot.erota(sb, '|', albumiNro);
     genreNro = Mjonot.erota(sb, '|', genreNro);
 }
 
 
 /**
  * testauksille esimerkki alustus
  * @param albNro albumin tunnusnumero
  * @param genNro genren tunnusnumero
  */
 public void alustaEsim(int albNro, int genNro) {
	 albumiNro=albNro;
	 genreNro=genNro;
 }
}
