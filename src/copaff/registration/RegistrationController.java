/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copaff.registration;

import com.jfoenix.controls.JFXComboBox;
import copaff.CountriesFetcher;
import copaff.CountriesFetcher.CountryList;
import copaff.Country;
import com.keyword.intlphonenumberinput.IntlPhoneNumberInputControl;
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
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author tavos
 */
public class RegistrationController implements Initializable {

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
    private GridPane gridPane;
    @FXML
    private JFXComboBox<Country> country;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CountryList countries = CountriesFetcher.getCountries();
        country.getItems().addAll(countries);
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
