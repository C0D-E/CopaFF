/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copaff.ui.registration;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import copaff.alert.AlertMaker;
import copaff.database.DataHelper;
import copaff.model.Clan;
import copaff.model.Player;
import copaff.model.Squad;
import copaff.model.Team;
import copaff.util.CountriesFetcher;
import copaff.util.Country;
import copaff.util.LoadCountryFlags;
import copaff.util.CountryCellView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
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
    private JFXTextField squadName;
    @FXML
    private JFXTextField clanName;
    @FXML
    private StackPane rootPane;
    @FXML
    private VBox mainContainer;
    @FXML
    private JFXTextField playerName;
    @FXML
    private JFXTextField playerID;
    @FXML
    private JFXComboBox<Country> playerCountry;
    @FXML
    private JFXTextField squadID;
    @FXML
    private JFXTextField clanID;
    @FXML
    private JFXComboBox<Squad> playerSquad;
    @FXML
    private ImageView squadLogo;
    @FXML
    private ImageView teamLogo;
    @FXML
    private ImageView clanLogo;
    @FXML
    private JFXButton genSquadID;
    @FXML
    private JFXTextField teamName;
    @FXML
    private JFXTextField teamID;
    @FXML
    private JFXButton genTeamID;
    @FXML
    private JFXButton playerLogo;
    @FXML
    private Label squadLogoLabel;
    @FXML
    private Label teamLogoLabel;
    @FXML
    private Label clanLogoLabel;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Ajajajajaj");
        LoadCountryFlags loadCountryFlags = new LoadCountryFlags();
        Thread th = new Thread(loadCountryFlags);
        th.setDaemon(true);
        th.start();
        try {
            flags = loadCountryFlags.get().getFlags();
            countries = loadCountryFlags.get().getCountryList();
            init();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        genSquadID.setUserData(squadID);
        genTeamID.setUserData(teamID);

        squadLogoLabel.setUserData(squadLogo);
        teamLogoLabel.setUserData(teamLogo);
        clanLogoLabel.setUserData(clanLogo);
        
        playerLogo.setUserData("C:\\Users\\TAVOS\\Documents\\NetBeansProjects\\CopaFF\\src\\copaff\\ui\\main\\user.png");
    }

    public final void init() {
        playerCountry.getItems().addAll(countries);
        playerCountry.setCellFactory((ListView<Country> p) -> new CountryCellView());
    }

    @FXML
    private void handleRegisterPlayerAction(ActionEvent event) {
        String playerNameTmp = StringUtils.trimToEmpty(playerName.getText());
        String playerIDTmp = StringUtils.trimToEmpty(playerID.getText());
        String playerCountryTmp = StringUtils.trimToEmpty(playerCountry.getEditor().getText());

        if (playerNameTmp.isEmpty() || playerIDTmp.isEmpty() || playerCountryTmp.isEmpty()) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Insufficient Data", "Please enter data in all fields.");
            return;
        }

        if (DataHelper.isPlayerExists(playerIDTmp)) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Duplicate player id", "Player with same PlayerID exists.\nPlease use new ID");
            return;
        }

        File f = new File((String) playerLogo.getUserData());
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
        } catch (FileNotFoundException ex) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Failed to add new player", ex.toString());
            return;
        }

        Player player = new Player(playerNameTmp, playerIDTmp, playerCountryTmp);
        boolean result = DataHelper.insertNewPlayer(player, fs, (int) f.length());
        if (result) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "New player added", playerNameTmp + " has been added");
            playerName.clear();
            playerID.clear();
            playerCountry.getEditor().clear();
        } else {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Failed to add new player", "Check all the entries and try again");
        }
    }

    @FXML
    private void handleGenerateIDAction(ActionEvent event) {
        Button button = (Button) event.getSource();
        JFXTextField field = (JFXTextField) button.getUserData();

        String squadIDTmp = UUID.randomUUID().toString();
        field.setText(squadIDTmp.toUpperCase());
    }

    @FXML
    private void handleRegisterSquadAction(ActionEvent event) {
        String squadNameTmp = StringUtils.trimToEmpty(squadName.getText());
        String squadIDTmp = StringUtils.trimToEmpty(squadID.getText());

        if (squadNameTmp.isEmpty() || squadIDTmp.isEmpty()) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Insufficient Data", "Please enter data in all fields.");
            return;
        }

        if (DataHelper.isSquadExists(squadIDTmp)) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Duplicate squad id", "Squad with same squad ID exists.\nPlease use new ID");
            return;
        }

        File f = new File((String) squadLogoLabel.getUserData());
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
        } catch (FileNotFoundException ex) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Failed to add new squad", ex.toString());
            return;
        }

        Squad squad = new Squad(squadNameTmp, squadIDTmp);
        boolean result = DataHelper.insertNewSquad(squad, fs, (int) f.length());
        if (result) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "New squad added", squadNameTmp + " has been added");
            squadName.clear();
            squadID.clear();
        } else {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Failed to add new squad", "Check all the entries and try again");
        }
    }

    @FXML
    private void handleRegisterTeamAction(ActionEvent event) {
        String teamNameTmp = StringUtils.trimToEmpty(teamName.getText());
        String teamIDTmp = StringUtils.trimToEmpty(teamID.getText());

        if (teamNameTmp.isEmpty() || teamIDTmp.isEmpty()) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Insufficient Data", "Please enter data in all fields.");
            return;
        }

        if (DataHelper.isClanExists(teamNameTmp)) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Duplicate team id", "Team with same teamID exists.\nPlease use new ID");
            return;
        }

        File f = new File((String) teamLogoLabel.getUserData());
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
        } catch (FileNotFoundException ex) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Failed to add new team", ex.toString());
            return;
        }

        Team team = new Team(teamNameTmp, teamIDTmp);
        boolean result = DataHelper.insertNewTeam(team, fs, (int) f.length());
        if (result) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "New clan added", teamNameTmp + " has been added");
            teamName.clear();
            teamID.clear();
        } else {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Failed to add new clan", "Check all the entries and try again");
        }
    }

    @FXML
    private void handleRegisterClanAction(ActionEvent event) {
        String clanNameTmp = StringUtils.trimToEmpty(clanName.getText());
        String clanIDTmp = StringUtils.trimToEmpty(clanID.getText());

        if (clanNameTmp.isEmpty() || clanIDTmp.isEmpty()) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Insufficient Data", "Please enter data in all fields.");
            return;
        }

        if (DataHelper.isClanExists(clanIDTmp)) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Duplicate clan id", "Clan with same clanID exists.\nPlease use new ID");
            return;
        }

        File f = new File((String) clanLogoLabel.getUserData());
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
        } catch (FileNotFoundException ex) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Failed to add new clan", ex.toString());
            return;
        }

        Clan clan = new Clan(clanNameTmp, clanIDTmp);
        boolean result = DataHelper.insertNewClan(clan, fs, (int) f.length());
        if (result) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "New clan added", clanNameTmp + " has been added");
            clanName.clear();
            clanID.clear();
        } else {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Failed to add new clan", "Check all the entries and try again");
        }
    }

    @FXML
    private void handleUpload(MouseEvent event) {
        Label logo = (Label) event.getSource();

        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        //Show open file dialog
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                String imageUrl = file.toURI().toURL().toExternalForm();
                Image image = new Image(imageUrl);
                ((ImageView) logo.getUserData()).setImage(image);
                logo.setUserData(imageUrl.replaceAll("file:/", ""));
            } catch (MalformedURLException ex) {
                throw new IllegalStateException(ex);
            }
        }
    }

}
