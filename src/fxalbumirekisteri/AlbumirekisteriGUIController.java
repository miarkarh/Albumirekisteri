package fxalbumirekisteri;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.ohj2.Mjonot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import albumirekisteri.Albumi;
import albumirekisteri.Albumigenre;
import albumirekisteri.Albumirekisteri;
import albumirekisteri.Genre;
import albumirekisteri.SailoException;
import albumirekisteri.Yhtye;


	/**
     * @author Mikko Karhunen, miarkarh@student.jyu.fi
     * @version 26.4.2019
	 *	Luokka käyttäliittymän ohjaimelle. Käyttäjän hallinnointi ei käytössä (käyttäjän kysyminen, avaa).
	 */
public class AlbumirekisteriGUIController implements Initializable {

    
    @FXML public ListChooser<Albumi> chooserAlbumit;
    @FXML public TextField textHakukentta;
    @FXML public ComboBoxChooser<String> hakuehto;  
    @FXML public ScrollPane panelAlbumi;
    @FXML public TextField textNimi;
    @FXML public TextField textYhtye;
    @FXML public TextField textGenre;
    @FXML public TextField textJulkaisuvuosi;
    @FXML public TextArea areaLisatietoja;
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();      
    }
    
    
    @FXML public void handleHaku(KeyEvent event) {
    	if (valittuAlbumi != null)
    		hae(valittuAlbumi.getTunnusNumero());
    	hae(0);
    }

    
    @FXML public void handleAvaa(ActionEvent event) {
    	avaa();
    }

    
    @FXML public void handleLopeta(ActionEvent event) {
    	lopeta();
    }

    
    @FXML public void handleMuutos(KeyEvent event) {
    	muutos = true;
    }
    
    
    @FXML public void handleUusiAlbumi(ActionEvent event) {
    	uusiAlbumi();
    }
    
    
    @FXML public void handlePoistaAlbumi(ActionEvent event) {
    	if (valittuAlbumi == null) return;
    	poistaAlbumi();
    }

    
    @FXML public void handleTallenna(ActionEvent event) {
    	tallenna();
    }

    
    @FXML public void handleTietoja(ActionEvent event) {
    	 ModalController.showModal(AlbumirekisteriGUIController.class.getResource("TiedotView.fxml"), "Albumirekisteri", null, ""); 
    }

    
    @FXML public void handleApua(ActionEvent event) {
        linkki();
    }    
    
    
    @FXML public void handleUutta(KeyEvent event) {
        if (muutos) paivitaTiedot();
    }

  //===========================================================================================     

    public String albumirekisterinnimi = "albumirekisteri";
    public Albumirekisteri albumirekisteri;
    public Albumi valittuAlbumi;
    public boolean muutos = false;
    
    
    /***
     * Alustaa näkymän
     */
    public void alusta() {
    	
    	textNimi.setText("");
    	textYhtye.setText("");
    	textGenre.setText("");
    	textJulkaisuvuosi.setText("");
    	
    	chooserAlbumit.addSelectionListener(e -> naytaAlbumi());
    }
    
    
    /**
     * Asettaa käyttäliittymän nimen
     * @param title nimi mikä asetetaan
     */
    public void setTitle(String title) {
        ModalController.getStage(textNimi).setTitle(title); //SDFSDF
    }
    
    
    /**
     * Tallentaa käyttäjän tekemät muutokset käyttäliittymässä.
     * Palauttaa virhe ilmoituksen jos tallennus ei onnistunut.
     * @return virhe ilmoitus
     */
    public String tallenna() {
        if (muutos) paivitaTiedot();
        try {
            albumirekisteri.tallenna();
            return null;
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + ex.getMessage());
            return ex.getMessage();
        }
    }
    
    
    /**
     * Alustaa rekisterin lukien annetusta tiedon nimestä
     * @param nimi tiedosto josta rekisterin tiedot luetaan
     * @return null jos onnistuu, muuten virhe tekstinä
     */
    public String lueTiedosto(String nimi) {
        albumirekisterinnimi = nimi;
        setTitle("Albumirekisteri - " + albumirekisterinnimi);
        try {
            albumirekisteri.lueTiedostosta(nimi);
            hae(0);
            return null;
        } catch (SailoException e) {
            hae(0);
            String virhe = e.getMessage(); 
            if ( virhe != null ) Dialogs.showMessageDialog(virhe);
            return virhe;
        }
     }
    
    
    /**
     * Kysytään tiedoston nimi ja luetaan se 
     * @return true jos onnistui, false jos ei
     */
    public boolean avaa() {//TODO: käyttäjän kysymys
        ModalController.showModal(AlbumirekisteriGUIController.class.getResource("AvaaView.fxml"), "Avaa", null, "");
        return true;
    }
    
    
    /**
     * Sulkee käyttäliittymän. Käyttäjän halutessa tallentaa ennen sulkemista. 
     * Jos tallentaminen ei onnistu, käyttäliittymää ei suljeta.
     */
    public void lopeta() {
        if (muutos) paivitaTiedot();
        if (Dialogs.showQuestionDialog("Lopetus","Haluatko varmasti lopettaa?", "Kyllä", "Ei")) {
        	if (Dialogs.showQuestionDialog("Tallennus","Haluatko tallentaa muutokset?", "Kyllä", "Ei")) {
        		String virhe = tallenna(); 
        		if (virhe != null) return;
        	}		
        }
    	ModalController.closeStage(panelAlbumi);
    }
    
    
    /**
     * Hakee albumeita ja lisää ne näkymään
     * @param albNumero albumin tunnusnumero
     */
    public void hae(int albNumero) {
    	String haku = textHakukentta.getText();
    	String haeht = hakuehto.getSelectedText();
    	chooserAlbumit.clear();
    	
    	Collection<Albumi> albumit = null;
    	albumit = albumirekisteri.etsi(haku, haeht);
    	
    	if (albumit != null) {
    	    int index = 0, i = 0;
    	    for (Albumi albumi:albumit) {
    	        if (albumi.getTunnusNumero() == albNumero) index = i;
    	        chooserAlbumit.add(albumi.getNimi(),albumi);
    	        i++;
    	    }
            chooserAlbumit.setSelectedIndex(index);
            naytaAlbumi();
    	}
    }
    
    
    /**
     * Luo uuden albumin pohjan.
     */
    public void uusiAlbumi() {
    	Albumi uusi = new Albumi();
    	uusi.rekisteroi();
    	uusi.alusta();
        albumirekisteri.lisaa(uusi);
    	hae(uusi.getTunnusNumero());
    }
    
    
    /**
     * Poistaa albumin rekisteristä.
     */
    public void poistaAlbumi() {
    	if (Dialogs.showQuestionDialog("Poista?","Haluatko varmasti poistaa albumin?", "Kyllä", "Ei")) {
    		albumirekisteri.poistaAlbumi(valittuAlbumi);
    		muutos = false;
    		hae(0);
    	};
    }

    
    /**
     * 
     * @param albumirekisteri rekisteri mitä käytetään
     */
    public void setAlbumirekisteri(Albumirekisteri albumirekisteri) {
        this.albumirekisteri = albumirekisteri;
        naytaAlbumi();
    }
    
    
    /**
     * Tulostaa albumin näkymän käyttäliittymään
     */
    public void naytaAlbumi() {
        if (muutos) paivitaTiedot();
    	valittuAlbumi = chooserAlbumit.getSelectedObject();
    	
    	textNimi.setText("");
    	textYhtye.setText("");
    	textGenre.setText("");
    	textJulkaisuvuosi.setText("");
    	areaLisatietoja.setText("");
    	
    	if (valittuAlbumi == null) return;
    	textNimi.setText(valittuAlbumi.getNimi());
    	Yhtye yht = albumirekisteri.annaYhtye(valittuAlbumi.getYhtyeenTunnusNumero());
    	if (yht != null)
    		textYhtye.setText(yht.getNimi());
    	String genret = "";
        for (Genre gen: albumirekisteri.annaGenret(valittuAlbumi))
        	genret += gen.getNimi() + " ";
    	textGenre.setText(genret);
    	textJulkaisuvuosi.setText("" + valittuAlbumi.getJulkaisuvuosi());
    	areaLisatietoja.setText(valittuAlbumi.getLisatietoja());
    }
    
    
    /**
     * Aukaisee suunnitelman nettiselaimessa.
     */
   public void linkki() { 
        Desktop desktop = Desktop.getDesktop(); 
        try { 
        	URI uri = new URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/2019k/ht/miarkarh"); 
        	desktop.browse(uri); 
        } catch (URISyntaxException e) { 
        	return; 
        } catch (IOException e) { 
       	return;
        }
   }
   
   
   /**
    * Lisää uuden/uudet genren/genret käyttäjän syätteestä. Luo uuden genren, jos ei ole vielä olemassa.
    * Tarkistaa samalla onko vanha genre enää missään käytässä.
    */
   public void uusiGenre() { 
	   List<Genre> gens = albumirekisteri.annaGenret(valittuAlbumi);
	   StringBuilder genNimet = new StringBuilder(textGenre.getText());
	   String genNimi;
	   albumirekisteri.poistaAlbumigenret(valittuAlbumi);
	   while (true) {
		   genNimi = Mjonot.erota(genNimet);
		   if (genNimi.equals("")) break;
		   Genre gen = albumirekisteri.etsiGenre(genNimi);
		   if (gen == null) { 
			   gen = new Genre();
			   gen.rekisteroi();
			   gen.setNimi(genNimi); 
			   albumirekisteri.lisaa(gen);
		   }
		   Albumigenre albgen = new Albumigenre();
		   albgen.setAlbumiNro(valittuAlbumi.getTunnusNumero());
		   albgen.setGenreNro(gen.getTunnusNumero());
		   albumirekisteri.lisaa(albgen); 
	   }
	   
  		for (Genre gen: gens)
			if (albumirekisteri.etsi(gen.getNimi(), "genre").size()==0) albumirekisteri.poistaGenre(gen);
   }

   
   /**
    * Asettaa uuden nimen albumille käyttäjän syätteestä.
    */
   public void uusiNimi() {
	   valittuAlbumi.setNimi(textNimi.getText());
   }

   
   /**
    * Lisää uuden yhtyeen albumille. Tarkistaa onko jo olemassa. 
    * Tarkistaa myäs onko vanhaa yhtyettä enää millään albumilla käytässä.
    */
   public void uusiYhtye() {
	   Yhtye vanhaYhtye = albumirekisteri.annaYhtye(valittuAlbumi.getYhtyeenTunnusNumero());
	   if (textYhtye.getText().equals("")) return;
	   Yhtye yht = albumirekisteri.etsiYhtye(textYhtye.getText());
	   if (yht == null) {
		   yht = new Yhtye();
		   yht.rekisteroi();
		   yht.setNimi(textYhtye.getText());
		   albumirekisteri.lisaa(yht);
	   }
	   valittuAlbumi.setYhtyeID(yht.getTunnusNumero());
	   
	   	if (vanhaYhtye!=null)
			if (albumirekisteri.etsi(vanhaYhtye.getNimi(), "yhtye").size()==0) albumirekisteri.poistaYhtye(vanhaYhtye);
   }

   
   /**
    * Lisää uuden julkaisuvuoden albumille käyttäjän syätteestä.
    */
   public void uusiVuosi() {
	   String julkaisuvuosi = textJulkaisuvuosi.getText();
	   valittuAlbumi.setVuosi(Mjonot.erotaInt(julkaisuvuosi, 0));
   }
   
   
   /**
    * Lisää uudet lisätiedot albumille.
    */
   public void uudetLisatiedot() {
	   valittuAlbumi.setLisatietoja(areaLisatietoja.getText());
   }
   
   
   /**
    * Päivittää albumiin käyttäjän syöttämät tiedot.
    */
   public void paivitaTiedot() {
       if (valittuAlbumi == null) return;
       uusiGenre();
       uusiNimi();
       uusiVuosi();
       uusiYhtye();
       uudetLisatiedot();
//       hae(valittuAlbumi.getTunnusNumero());
       muutos = false;
   }
}