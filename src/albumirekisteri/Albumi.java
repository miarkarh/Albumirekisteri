package albumirekisteri;

import java.io.*;

import fi.jyu.mit.ohj2.Mjonot;


/**
 * Luokka albumi oliolle. Kuvien käsittely ei toiminnassa.
 * @author Mikko Karhunen, miarkarh@student.jyu.fi
 * @version 26.4.2019
 */
public class Albumi {
	private int 	tunnusNumero;
	private String 	nimi 			= 	"";
	private int yid = 0;
	private int 	julkaisuvuosi 	= 	0;
	private String 	lisatietoja 	= 	"";
	//TODO: Kuva attribuutti
	private static int seuraavaNumero = 1;
	
	
	/**
	 * rekisteräi albumin albumeiden joukkoon
     * @example
     * <pre name="test">
     *   Albumi a1 = new Albumi();
     *   a1.getTunnusNumero() === 0;
     *   a1.rekisteroi();
     *   Albumi a2 = new Albumi();
     *   a2.rekisteroi();
     *   int t1 = a1.getTunnusNumero();
     *   int t2 = a2.getTunnusNumero();
     *   t1 === t2-1;
     * </pre>
     */
	public void rekisteroi() {
		tunnusNumero = seuraavaNumero;
		seuraavaNumero++;
	}

	
	/**
	 * Alustaa albumin esimerkki albumiksi
	 */
	public void alustaEsim() {
		nimi = "Kind Of Blue";
		yid = 1;
		julkaisuvuosi = 1959;
		lisatietoja = "plaaplaaplob";
	}

	
	/**
	 * Alustaa uuden albumin. Antaa albumille nimeksi "Nimi".
	 */
	public void alusta() {
		nimi = "Nimi";
	}
	

	/**
	 * Tulostaa albumin tiedot
	 * @param out virta jonne tulostetaan
	 */
	public void tulosta(PrintStream out) {
		out.println("Tunnus numero:	" + tunnusNumero);
		out.println(" Albumin nimi:	" + nimi);
		out.println("Julkaisuvuosi:	" + julkaisuvuosi);
		out.println("  Lisatietoja:	" + lisatietoja);
	}
	
	
	/**
	 * Muuntaa tietovirra
	 * @param os tietovirta joka muunnetaan
	 */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }


    /**
     * @return palauttaa albumin nimen
     * @example
     * <pre name="test">
     *   Albumi a = new Albumi();
     *   a.alustaEsim();
     *   a.getNimi() === "Kind Of Blue";
     * </pre>
     */
	public String getNimi() {
		return nimi;
	}
	

	/**
	 * @return palauttaa albumin julkaisuvuoden
	 */
	public int getJulkaisuvuosi() {
		return julkaisuvuosi;
	}
	
	
	/**
	 * @return palauttaa albumin julkaisuvuoden
	 */
	public int getYhtyeenTunnusNumero() {
		return yid;
	}
	
	
	/**
	 * @return albumin lisätiedot
	 */
	public String getLisatietoja() {
		return lisatietoja;
	}
	
	
	/**
	 * @return palauttaa albumin numeron
	 */
	public int getTunnusNumero() {
		return tunnusNumero;
	}
	
	
	/**
	 * Palauttaa hakuehdon mukaisen atribuutin albumilta. Oletuksen palauttaa nimen.
	 * @param hakuehto mikä atrribuutti palautetaan.
	 * @return palauttaa albumin jonkin attribuutin
	 */
	public String getEhto(String hakuehto) {
		if (hakuehto.equalsIgnoreCase("julkaisuvuosi")) return "" + julkaisuvuosi;
		return nimi;
	}
	
	
    /**
     * Asettaa tunnusnumeron albumille varmistaen samalla että
     * seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nr asetettava tunnusnumero
     */
    private void setTunnusNumero(int nr) {
        tunnusNumero = nr;
        if (tunnusNumero >= seuraavaNumero) seuraavaNumero = tunnusNumero + 1;
    }
	
	
    /**
     * Palauttaa albumin tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return albumi tolpilla eroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Albumi albumi = new Albumi();
     *   albumi.parse("  1 |  Kind Of Blue   |1959");
     *   albumi.toString().startsWith("1|Kind Of Blue|1959") === true;
     * </pre>  
     */
    @Override
    public String toString() {
        return "" +
                tunnusNumero + "|" +
                nimi + "|" +
                yid + "|" +
                julkaisuvuosi + "|" +
                lisatietoja;
    }


    /**
     * Selvitää albumin tiedot | erotellusta merkkijonosta
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusNro.
     * @param rivi josta albumin tiedot otetaan 
     * @example
     * <pre name="test">
     *   Albumi albumi = new Albumi();
     *   albumi.parse("  1 |  Kind Of Blue   |1959");
     *   albumi.getTunnusNumero() === 1;
     *   albumi.toString().startsWith("1|Kind Of Blue|1959") === true;
     *
     *   albumi.rekisteroi();
     *   int n = albumi.getTunnusNumero();
     *   albumi.parse(""+(n+20));       // Otetaan merkkijonosta vain tunnusnumero
     *   albumi.rekisteroi();           // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
     *   albumi.getTunnusNumero() === n+20+1;
     *     
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setTunnusNumero(Mjonot.erota(sb, '|', tunnusNumero));
        nimi = Mjonot.erota(sb, '|', nimi);
        yid = Mjonot.erota(sb, '|', yid);
        julkaisuvuosi = Mjonot.erota(sb, '|', julkaisuvuosi);
        lisatietoja = Mjonot.erota(sb, '|', lisatietoja);
    }


    /**
     * Asettaa albumille nimen
     * @param albnim nimi joka asetetaan
     */
	public void setNimi(String albnim) {
		nimi = albnim;
		
	}


	/**
	 * Asettaa albumille tietoon yhtyeen tunnusnumeron.
	 * @param i yhtyeen tunnusnumero
	 */
	public void setYhtyeID(int i) {
		yid = i;
		
	}


	/**
	 * Asettaa julkaisuvuoden
	 * @param vuosi julkaisuvuosi
	 */
	public void setVuosi(int vuosi) {
		julkaisuvuosi = vuosi;		
	}
	
	
	/**
	 * Asettaa lisätiedot
	 * @param lisatiedot jotka asetetaan albumille
	 */
	public void setLisatietoja(String lisatiedot) {
		lisatietoja = lisatiedot;
	}
}
