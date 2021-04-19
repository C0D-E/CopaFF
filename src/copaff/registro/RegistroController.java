/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copaff.registro;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;

/**
 * FXML Controller class
 *
 * @author tavos
 */
public class RegistroController implements Initializable {

    @FXML
    private ColumnConstraints labels;
    @FXML
    private ColumnConstraints space;
    @FXML
    private TextField name;
    @FXML
    private TextField ffId;
    @FXML
    private TextField squadName;
    @FXML
    private TextField clanName;
    @FXML
    private TextField clanId;
    @FXML
    private Button register;
    @FXML
    private ChoiceBox<?> country;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleAddressAction(MouseEvent event) {
    }

    @FXML
    private void handleEmailAddressAction(MouseEvent event) {
    }

    @FXML
    private void handleRegisterAction(ActionEvent event) {
    }
    
}
