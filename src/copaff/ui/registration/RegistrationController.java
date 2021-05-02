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
import copaff.model.relations.FixedSquad;
import copaff.model.relations.SquadAlternate;
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
    private TextField squadName;
    @FXML
    private TextField clanName;
    @FXML
    private StackPane rootPane;
    @FXML
    private GridPane mainContainer;
    private TextField squadId;
    @FXML
    private TextField playerName;
    @FXML
    private TextField playerID;
    @FXML
    private JFXComboBox<Country> playerCountry;
    @FXML
    private TextField squadID;
    @FXML
    private TextField clanID;

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
        playerCountry.getItems().addAll(countries);
        playerCountry.setCellFactory((ListView<Country> p) -> new CountryCellView());
    }

    @FXML
    private void handleRegisterPlayerAction(ActionEvent event) {
        String playerName = StringUtils.trimToEmpty(this.playerName.getText());
        String playerID = StringUtils.trimToEmpty(this.playerID.getText());
        String playerCountry = StringUtils.trimToEmpty(this.playerCountry.getEditor().getText());

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
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "New player added", playerName + " has been added");
            this.playerName.clear();
            this.playerID.clear();
            this.playerCountry.getEditor().clear();
        } else {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Failed to add new player", "Check all the entries and try again");
        }
    }

    @FXML
    private void handleRegisterSquadAction(ActionEvent event) {
        String squadName = StringUtils.trimToEmpty(this.squadName.getText());
        String squadID = StringUtils.trimToEmpty(this.squadID.getText());

        if (squadName.isEmpty() || squadID.isEmpty()) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Insufficient Data", "Please enter data in all fields.");
            return;
        }

        if (DataHelper.isPlayerExists(squadID)) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Duplicate squad id", "Squad with same squad ID exists.\nPlease use new ID");
            return;
        }
        FixedSquad fixedSquad = new FixedSquad(squadID);
        boolean result = DataHelper.insertNewFixedSquad(fixedSquad);
        if (result) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "New squad added", squadName + " has been added");
            this.squadName.clear();
            this.squadID.clear();
        } else {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Failed to add new squad", "Check all the entries and try again");
        }
    }

    @FXML
    private void handleRegisterClanAction(ActionEvent event) {
        String clanName = StringUtils.trimToEmpty(this.clanName.getText());
        String clanID = StringUtils.trimToEmpty(this.clanID.getText());

        if (clanName.isEmpty() || clanID.isEmpty()) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Insufficient Data", "Please enter data in all fields.");
            return;
        }

        if (DataHelper.isPlayerExists(clanID)) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Duplicate clan id", "Squad with same clan ID exists.\nPlease use new ID");
            return;
        }
        FixedSquad fixedSquad = new FixedSquad(clanID);
        boolean result = DataHelper.insertNewFixedSquad(fixedSquad);
        if (result) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "New clan added", clanName + " has been added");
            this.clanName.clear();
            this.clanID.clear();
        } else {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Failed to add new clan", "Check all the entries and try again");
        }
    }
}
