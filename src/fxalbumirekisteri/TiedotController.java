package fxalbumirekisteri;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


/**
 * Ohjain tiedot ikkunassa tapahtumille
 * @author Mikko Karhunen, miarkarh@student.jyu.fi
 * @version 26.4.2019
 */
public class TiedotController implements ModalControllerInterface<String> {

    @FXML private Button okNappi;
	
    @FXML void handleOK(ActionEvent event) {
    	sulje();
    }
    
    
//================================================================================================
    
    /**
     * Sulkee ikkunan
     */
    public void sulje() {
    	ModalController.closeStage(okNappi);
    }


	@Override
	public String getResult() {
		return null;
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
