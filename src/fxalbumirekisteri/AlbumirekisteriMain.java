package fxalbumirekisteri;

import albumirekisteri.Albumirekisteri;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author Mikko Karhunen, miarkarh@student.jyu.fi
 * @version 26.4.2019
 *
 * Pääluokka ohjelmalle ja käynnistykseen. Käyttäjän kysyminen ei ole käytössä.
 */
public class AlbumirekisteriMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
           	//ModalController.showModal(AlbumirekisteriGUIController.class.getResource("AvaaView.fxml"), "Avaa", null, "");
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("AlbumirekisteriGUIView.fxml"));
            final Pane root = ldr.load();
            final AlbumirekisteriGUIController albumirekisteriCtrl = (AlbumirekisteriGUIController) ldr.getController();
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("albumirekisteri.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("albumirekisteri");
            
            Albumirekisteri albumirekisteri = new Albumirekisteri();
            albumirekisteriCtrl.setAlbumirekisteri(albumirekisteri);
            
            primaryStage.show();
            
//            Application.Parameters params = getParameters(); 
//            if ( params.getRaw().size() > 0 ) 
                albumirekisteriCtrl.lueTiedosto("albumirekisteri");  //TODO: käyttäjän kysyntä
//            else
//                if ( albumirekisteriCtrl.avaa() == false ) Platform.exit();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  Käynnistää käyttäliittymän.
     * @param args Ei käytässä
     */
    public static void main(String[] args) {
        launch(args);
    }
}