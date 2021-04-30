/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copaff.registration;

import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author TAVOS
 */
public class RegistrationController implements Initializable {

    @FXML
    private StackPane stackpane;
    @FXML
    private GridPane gridPane;
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
    private JFXComboBox<?> country;
    @FXML
    private TextField kills;
    @FXML
    private TextField position;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleRegisterAction(ActionEvent event) {
    }
    
}
