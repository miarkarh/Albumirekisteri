package albumirekisteri;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Luokka yhtye oliolle
 * @author Mikko Karhunen, miarkarh@student.jyu.fi
 * @version 26.4.2019
 */
public class Yhtye {
	private int tunnusNro;
	private String nimi;	
	
	private static int seuraavaNro = 1;
	
	
	/**
	 * 
	 * @return Yhtyeen nimi
	 */
	public String getNimi() {
		return nimi;
	}
	
	
	/**
	 * 
	 * @return yhtyeen tunnusnumero
	 */
	public int getTunnusNumero() {
		return tunnusNro;
	}
	
		
	/**
	 * asettaa nimen yhtyeelle
	 * @param yhtNim nimi joka asetetaan yhtyeelle.
	 */
	public void setNimi(String yhtNim) {
		nimi = yhtNim;
	}
	
	
	/**
	 * Asettaa tunnusnumeron ja samalla varmistaa että
	 * seuraava numero on aina suurempi kuin tähän mennessä suurin.
	 * @param nr asetettava tunnusnumero
	 */
	public void setTunnusNro(int nr) {
		tunnusNro = nr;
		if ( tunnusNro >= seuraavaNro ) seuraavaNro = tunnusNro + 1;
	}
	
	
	
	/**
	 * rekisteräi yhtyeen yhtyeiden joukkoon
	 * @return rekisteräidyn yhteen tunnusnumero
	 * @example
     * <pre name="test">
     *   Yhtye a1 = new Yhtye();
     *   a1.getTunnusNumero() === 0;
     *   a1.rekisteroi();
     *   Yhtye a2 = new Yhtye();
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
	 * Palauttaa yhtyeen tiedot merkkijonona jonka voi tallentaa tiedostoon.
	 * @return yhtye tolppaeroteltuna merkkijonona 
	 * @example
	 * <pre name="test">
	 *   Yhtye yht = new Yhtye();
	 *   yht.parse("  2| Ben E. King ");
	 *   yht.toString()    === "2|Ben E. King";
	 * </pre>
	 */
	@Override
	public String toString() {
		return "" + tunnusNro + "|" + nimi;
	}


	/**
	 * Selvitää yhtyeen tiedot | erotellusta merkkijonosta.
	 * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusnro.
	 * @param rivi josta yhtyeen tiedot otetaan
	 * @example
     * <pre name="test">
     *   Yhtye yht = new Yhtye();
     *   yht.parse("  1  |        Billy Talent   ");
     *   yht.getTunnusNumero() === 1;
     *   yht.toString()    === "1|Billy Talent";
     *   
     *   yht.rekisteroi();
     *   int n = yht.getTunnusNumero();
     *   yht.parse(""+(n+20));
     *   yht.rekisteroi();
     *   yht.getTunnusNumero() === n+20+1;
     *   yht.toString()     === "" + (n+20+1) + "|Billy Talent";
     *   yht.getNimi() === "Billy Talent";
     * </pre>
     */
	public void parse(String rivi) {
		StringBuffer sb = new StringBuffer(rivi);
		setTunnusNro(Mjonot.erota(sb, '|', tunnusNro));
		nimi = Mjonot.erota(sb, '|', nimi);
	}
	
	
	/**
	 * tekee yhtyeen esimerkin.
	 */
	public void alustaEsimerkki() {
		tunnusNro = 1;
		nimi = "abc";
	}
}
