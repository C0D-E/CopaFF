/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copaff;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

/**
 *
 * @author tavos
 */
public class FXMLDocumentController implements Initializable {
   private double transitionMilis = 400;
    @FXML
    private BorderPane mainBorderPane;
    
    
     @FXML
    private void handleRegisterAction(ActionEvent event) {
        try {
            GridPane registration = FXMLLoader.load(getClass().getResource("/copaff/registro/Registro.fxml"));
            FadeTransition ft = new FadeTransition(Duration.millis(transitionMilis), registration);
            ft.setFromValue(0.1);
            ft.setToValue(1.0);
            ft.play();
            mainBorderPane.setCenter(registration);
        } catch (IOException ex) {
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
