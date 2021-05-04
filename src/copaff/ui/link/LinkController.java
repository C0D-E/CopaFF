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
import copaff.model.Player;
import copaff.model.Squad;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.StringUtils;

/**
 * FXML Controller class
 *
 * @author TAVOS
 */
public class LinkController implements Initializable {

    @FXML
    private JFXComboBox<Squad> squadName;
    @FXML
    private JFXComboBox<Player> selectorPlayer1;
    @FXML
    private JFXComboBox<Player> selectorPlayer2;
    @FXML
    private JFXComboBox<Player> selectorPlayer3;
    @FXML
    private JFXComboBox<Player> selectorPlayer4;
    @FXML
    private StackPane rootPane;
    @FXML
    private VBox mainContainer;

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

        handler = DatabaseHandler.getInstance();
        qu = "SELECT * FROM PLAYER";
        rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String country = rs.getString("country");
                LocalDateTime created = rs.getTimestamp("created").toLocalDateTime();
                Player player = new Player(id, name, country, created);
                selectorPlayer1.getItems().add(player);
                selectorPlayer2.getItems().add(player);
                selectorPlayer3.getItems().add(player);
                selectorPlayer4.getItems().add(player);
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(LinkController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    public void assign(ActionEvent event) {
        if (squadName.getSelectionModel().isEmpty() || selectorPlayer1.getSelectionModel().isEmpty()
                || selectorPlayer2.getSelectionModel().isEmpty()
                || selectorPlayer3.getSelectionModel().isEmpty()
                || selectorPlayer4.getSelectionModel().isEmpty()) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Insufficient Data", "Please enter data in all fields.");
            return;
        }

        String squadID = StringUtils.trimToEmpty(squadName.getSelectionModel().getSelectedItem().getId());
        Player player1 = selectorPlayer1.getSelectionModel().getSelectedItem();
        Player player2 = selectorPlayer2.getSelectionModel().getSelectedItem();
        Player player3 = selectorPlayer3.getSelectionModel().getSelectedItem();
        Player player4 = selectorPlayer4.getSelectionModel().getSelectedItem();

        DataHelper.wipeTable("SQUAD" + squadID.replaceAll("-", ""));

        DataHelper.insertPLayerInFixedSquad(squadID, player1);
        DataHelper.insertPLayerInFixedSquad(squadID, player2);
        DataHelper.insertPLayerInFixedSquad(squadID, player3);
        DataHelper.insertPLayerInFixedSquad(squadID, player4);
        AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Data assign", "Squad succesfully assiggned");
    }

}
