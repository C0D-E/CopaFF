/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copaff.ui.registration;

import com.jfoenix.controls.JFXComboBox;
import copaff.alert.AlertMaker;
import copaff.database.DataHelper;
import copaff.model.Player;
import copaff.util.CountriesFetcher;
import copaff.util.Country;
import copaff.util.LoadCountryFlags;
import copaff.util.CountryCellView;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.layout.StackPane;
import org.apache.commons.lang3.StringUtils;

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
    private JFXComboBox<Country> country;
    @FXML
    private TextField kills;
    @FXML
    private TextField position;
    @FXML
    private StackPane rootPane;
    @FXML
    private GridPane mainContainer;

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
        String playerName = StringUtils.trimToEmpty(name.getText());
        String playerID = StringUtils.trimToEmpty(ffId.getText());
        String playerCountry = StringUtils.trimToEmpty(country.getEditor().getText());

        if (playerName.isEmpty() || playerID.isEmpty() || playerCountry.isEmpty()) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Insufficient Data", "Please enter data in all fields.");
            return;
        }

        if (DataHelper.isPlayerExists(playerID)) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Duplicate player id", "Player with same Player ID exists.\nPlease use new ID");
            return;
        }
        Player player = new Player(playerID, playerID, playerCountry);
        boolean result = DataHelper.insertNewPlayer(player);
        if (result) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "New book added", playerName + " has been added");
            clearEntries();
        } else {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Failed to add new book", "Check all the entries and try again");
        }
    }

    private void clearEntries() {
        name.clear();
        ffId.clear();
        country.getEditor().clear();
    }

}
