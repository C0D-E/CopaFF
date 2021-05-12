/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copaff.ui.link;

import com.jfoenix.controls.JFXComboBox;
import copaff.alert.AlertMaker;
import copaff.database.DataHelper;
import copaff.database.DatabaseHandler;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.StringUtils;
import squadcard.Player;
import squadcard.Squad;

/**
 * FXML Controller class
 *
 * @author TAVOS
 */
public class LinkController implements Initializable {
    
    ObservableList<Player> players = FXCollections.observableArrayList();

    @FXML
    private JFXComboBox<Squad> squadName;
    @FXML
    private StackPane rootPane;
    @FXML
    private VBox mainContainer;
    @FXML
    private JFXComboBox<Player> selectorPlayer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        DatabaseHandler handler = DatabaseHandler.getInstance();
        String qu = "SELECT * FROM SQUAD";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                squadName.getItems().add(new Squad(id, name));
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(LinkController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        squadName.valueProperty().addListener((observable, oldValue, newValue) -> {
            selectorPlayer.getItems().clear();
            DatabaseHandler dbHandler = DatabaseHandler.getInstance();
            String query = "SELECT * FROM PLAYER";
            ResultSet resulSet = dbHandler.execQuery(query);
            try {
                while (resulSet.next()) {
                    String playerId = resulSet.getString("id");
                    String playerName = resulSet.getString("name");
                    String playerCountry = resulSet.getString("country");
                    LocalDateTime created = resulSet.getTimestamp("created").toLocalDateTime();
                    Player player = new Player(playerId, playerName, playerCountry, created);
                    if (!DataHelper.isPlayerExistsInTable("SQUAD", newValue.getId(), playerId)) {
                        selectorPlayer.getItems().add(player);
                    }
                }
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(LinkController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        });
    }

    @FXML
    public void assign(ActionEvent event) {
        if (squadName.getSelectionModel().isEmpty() || selectorPlayer.getSelectionModel().isEmpty()) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Insufficient Data", "Please enter data in all fields.");
            return;
        }

        Squad squad = squadName.getSelectionModel().getSelectedItem();
        String squadID = StringUtils.trimToEmpty(squad.getId());
        Player player = selectorPlayer.getSelectionModel().getSelectedItem();

        if (DataHelper.isPlayerExistsInTable("SQUAD", squadID, player.getId())) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Data assign", "Player " + player.getName() + " is already assiggned in squad " + squad.getName());
            return;
        }
        DataHelper.insertPLayerInTable("SQUAD", squadID, player.getId());
        AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Data assign", "Squad succesfully assiggned");
    }

}
