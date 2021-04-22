/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copaff.registration;

import com.jfoenix.controls.JFXComboBox;
import copaff.CountriesFetcher;
import copaff.Country;
import copaff.LoadCountryFlags;
import copaff.CountryCellView;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author tavos
 */
public class RegistrationController implements Initializable {

    public static Map<String, Image> flags;
    public static CountriesFetcher.CountryList countries;

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
    @FXML
    private TextField kills;
    @FXML
    private TextField position;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LoadCountryFlags loadCountryFlags = new LoadCountryFlags();
        Thread th = new Thread(loadCountryFlags);
        th.setDaemon(true);
        th.start();
        try {
            flags = loadCountryFlags.get().getFlags();
            countries = loadCountryFlags.get().getCountryList();
            init();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }

    }

    public final void init() {
        country.getItems().addAll(countries);
        country.setCellFactory((ListView<Country> p) -> new CountryCellView());
    }

    @FXML
    private void handleRegisterAction(ActionEvent event) {

    }

}
