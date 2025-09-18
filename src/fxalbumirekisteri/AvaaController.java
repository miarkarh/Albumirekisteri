package fxalbumirekisteri;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * @author Mikko Karhunen, miarkarh@student.jyu.fi
 * @version 26.4.2019
 *
 * Ohjain avausnäytässä tapahtuville tapahtumille. Käyttäjän kysymisen hallinnointi ei käytössä
 */
public class AvaaController implements ModalControllerInterface<String> {
	
    @FXML private TextField textKayttaja;
    

    @FXML private void handleKayttaja(ActionEvent event) {
    	kayttaja();
    }
    

//======================================================================
    private String kayttis;
    
    
    /**
     *  Hakee käyttäjän ja aukaisee käyttäliittymän.
     */
    private void kayttaja() {
    	//TODO: käyttäjän olemassa olo tarkistus
    	boolean vastaus = Dialogs.showQuestionDialog("Uusi käyttäjä","Kyseistä käyttäjää ei läydy.\nHaluatko luoda uuden käyttäjän?", "Kyllä", "Ei");
        if (vastaus == false) return;
        kayttis = textKayttaja.getText(); //TODO: käyttäjän luonti
        //TODO: käyttäjän käyttä
    	ModalController.closeStage(textKayttaja);
    }


	@Override
	public String getResult() {
		return kayttis;
	}


	@Override
	public void handleShown() {
		//
	}


	@Override
	public void setDefault(String oletus) {
		//
	}
}
