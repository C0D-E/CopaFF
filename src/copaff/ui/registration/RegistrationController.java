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
import copaff.database.DatabaseHandler;
import copaff.model.Clan;
import copaff.model.Player;
import copaff.model.Squad;
import copaff.model.Team;
import copaff.model.match_modes.Scrimmage;
import copaff.ui.link.LinkController;
import copaff.util.CountriesFetcher;
import copaff.util.Country;
import copaff.util.LoadCountryFlags;
import copaff.util.CountryCellView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import masktextfield.MaskTextField;
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
    private ImageView playerLogo;
    @FXML
    private Label squadLogoLabel;
    @FXML
    private Label teamLogoLabel;
    @FXML
    private Label clanLogoLabel;
    @FXML
    private Label playerLogoLabel;
    @FXML
    private JFXTextField scrimmageID;
    @FXML
    private JFXComboBox<String> maps;
    @FXML
    private JFXComboBox<Player> playerCreatorList;
    @FXML
    private MaskTextField scrimBlock;
    @FXML
    private JFXTextField customRoomID;
    @FXML
    private JFXButton genScrimID;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
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
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        genScrimID.setUserData(scrimmageID);
        genSquadID.setUserData(squadID);
        genTeamID.setUserData(teamID);

        playerLogoLabel.setUserData(playerLogo);
        squadLogoLabel.setUserData(squadLogo);
        teamLogoLabel.setUserData(teamLogo);
        clanLogoLabel.setUserData(clanLogo);

        playerLogo.setUserData("C:\\Users\\TAVOS\\Documents\\NetBeansProjects\\CopaFF\\src\\copaff\\ui\\main\\user.png");
        File file = new File("C:\\Users\\TAVOS\\Documents\\NetBeansProjects\\CopaFF\\src\\copaff\\ui\\main\\user.png");
        String imageUrl;
        try {
            imageUrl = file.toURI().toURL().toExternalForm();
            Image image = new Image(imageUrl);
            playerLogo.setImage(image);
        } catch (MalformedURLException ex) {
            Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
        }

        maps.getItems().add("Bermuda");
        maps.getItems().add("Bermuda Remasterizada");
        maps.getItems().add("Purgatorio");
        maps.getItems().add("Kalahari");

        DatabaseHandler handler = DatabaseHandler.getInstance();
        String qu = "SELECT * FROM PLAYER";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                Player player = new Player(rs.getString("id"), rs.getString("name"),
                        rs.getString("country"), rs.getTimestamp("created").toLocalDateTime());
                playerCreatorList.getItems().add(player);
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(LinkController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    public final void init() {
        playerCountry.getItems().addAll(countries);
        playerCountry.setCellFactory((ListView<Country> p) -> new CountryCellView());
    }

    @FXML
    private void handleMinusAction(ActionEvent event) {
        int block = Integer.valueOf(scrimBlock.getText());
        block -= 1;
        scrimBlock.setText(String.valueOf(block));

    }

    @FXML
    private void handlePlusAction(ActionEvent event) {
        int block = Integer.valueOf(scrimBlock.getText());
        block += 1;
        scrimBlock.setText(String.valueOf(block));
    }

    @FXML
    private void handleRegisterScrimmageAction(ActionEvent event) {
        String customRoomIDTmp = StringUtils.trimToEmpty(customRoomID.getText());
        String scrimmageIDTmp = StringUtils.trimToEmpty(scrimmageID.getText());
        String scrimBlockTmp = StringUtils.trimToEmpty(scrimBlock.getText());

        if (customRoomIDTmp.isEmpty() || scrimmageIDTmp.isEmpty() || scrimBlockTmp.isEmpty()
                || maps.getSelectionModel().isEmpty() || playerCreatorList.getSelectionModel().isEmpty()) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Insufficient Data", "Please enter data in all fields.");
            return;
        }

        String map = StringUtils.trimToEmpty(maps.getSelectionModel().getSelectedItem());
        String playerCreatorID = StringUtils.trimToEmpty(playerCreatorList.getSelectionModel().getSelectedItem().getId());

        Scrimmage scrim = new Scrimmage(scrimmageIDTmp, customRoomIDTmp, playerCreatorID, map, Integer.parseInt(scrimBlockTmp));
        boolean result = DataHelper.insertNewScrimmage(scrim);
        boolean result1 = DataHelper.createMatchTable(scrimmageIDTmp);
        if (result || result1) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "New Scrimmage added", "Scrim with Custom ROom ID: " + customRoomIDTmp + " has been added");
            customRoomID.clear();
            scrimmageID.clear();
            scrimBlock.setText(String.valueOf(1));
            playerCreatorList.getSelectionModel().clearSelection();
            maps.getSelectionModel().clearSelection();
        } else {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Failed to add new Scrimmage", "Check all the entries and try again");
        }
    }

    @FXML
    private void handleRegisterPlayerAction(ActionEvent event) {
        String playerNameTmp = StringUtils.trimToEmpty(playerName.getText());
        String playerIDTmp = StringUtils.trimToEmpty(playerID.getText());

        if (playerNameTmp.isEmpty() || playerIDTmp.isEmpty() || playerCountry.getSelectionModel().isEmpty()) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Insufficient Data", "Please enter data in all fields.");
            return;
        }
        String playerCountryTmp = StringUtils.trimToEmpty(playerCountry.getSelectionModel().getSelectedItem().getName());

        if (playerLogo.getImage() == null) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Insufficient Data", "Please select an image for the logo of this player.");
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

        Player player = new Player(playerIDTmp, playerNameTmp, playerCountryTmp);
        boolean result = DataHelper.insertNewPlayer(player, fs, (int) f.length());
        if (result) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "New player added", playerNameTmp + " has been added");
            playerID.clear();
            playerName.clear();
            playerCountry.getSelectionModel().clearSelection();
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

        if (squadLogo.getImage() == null) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Insufficient Data", "Please select an image for the logo of this squad.");
            return;
        }

        if (DataHelper.isSquadExists(squadIDTmp)) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Duplicate squad id", "Squad with same squad ID exists.\nPlease use new ID");
            return;
        }

        File f = new File((String) squadLogo.getUserData());
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
        } catch (FileNotFoundException ex) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Failed to add new squad", ex.toString());
            return;
        }

        Squad squad = new Squad(squadIDTmp, squadNameTmp);
        boolean result = DataHelper.insertNewSquad(squad, fs, (int) f.length());
        boolean result1 = DataHelper.createTable("SQUAD",squadIDTmp);
        if (result || result1) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "New squad added", squadNameTmp + " has been added");
            squadName.clear();
            squadID.clear();
            squadLogo.setImage(null);
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

        if (teamLogo.getImage() == null) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Insufficient Data", "Please select an image for the logo of this team.");
            return;
        }

        if (DataHelper.isClanExists(teamNameTmp)) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Duplicate team id", "Team with same teamID exists.\nPlease use new ID");
            return;
        }

        File f = new File((String) teamLogo.getUserData());
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
        } catch (FileNotFoundException ex) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Failed to add new team", ex.toString());
            return;
        }

        Team team = new Team(teamIDTmp, teamNameTmp);
        boolean result = DataHelper.insertNewTeam(team, fs, (int) f.length());
        result = result || DataHelper.createTable("TEAM", teamIDTmp);
        if (result) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "New clan added", teamNameTmp + " has been added");
            teamName.clear();
            teamID.clear();
            teamLogo.setImage(null);
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

        if (clanLogo.getImage() == null) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Insufficient Data", "Please select an image for the logo of this clan.");
            return;
        }

        if (DataHelper.isClanExists(clanIDTmp)) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Duplicate clan id", "Clan with same clanID exists.\nPlease use new ID");
            return;
        }

        File f = new File((String) clanLogo.getUserData());
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
        } catch (FileNotFoundException ex) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Failed to add new clan", ex.toString());
            return;
        }

        Clan clan = new Clan(clanIDTmp, clanNameTmp);
        boolean result = DataHelper.insertNewClan(clan, fs, (int) f.length());
        result = result || DataHelper.createTable("CLAN", clanIDTmp);
        if (result) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "New clan added", clanNameTmp + " has been added");
            clanName.clear();
            clanID.clear();
            clanLogo.setImage(null);
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
                ImageView logoView = (ImageView) logo.getUserData();
                logoView.setImage(image);
                logoView.setUserData(imageUrl.replaceAll("file:/", ""));
            } catch (MalformedURLException ex) {
                throw new IllegalStateException(ex);
            }
        }
    }
}
