/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copaff.ui.game;

import com.jfoenix.controls.JFXComboBox;
import copaff.database.DatabaseHandler;
import copaff.model.Player;
import copaff.model.Squad;
import copaff.model.match_modes.Scrimmage;
import copaff.ui.link.LinkController;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import masktextfield.MaskTextField;
import squadcard.SquadCardController;

/**
 * FXML Controller class
 *
 * @author TAVOS
 */
public class MatchController implements Initializable {

    @FXML
    private JFXComboBox<Scrimmage> scrimList;
    @FXML
    private JFXComboBox<Player> player1;
    @FXML
    private JFXComboBox<Player> player2;
    @FXML
    private JFXComboBox<Player> player3;
    @FXML
    private JFXComboBox<Player> player4;
    @FXML
    private VBox oddSquadPositions;
    @FXML
    private VBox evenSquadPositions;
    @FXML
    private JFXComboBox<Squad> squadList;
    @FXML
    private MaskTextField squadPosition;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        DatabaseHandler handler = DatabaseHandler.getInstance();
        String qu = "SELECT * FROM SCRIMMAGE";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                String id = rs.getString("id");
                String customRoomID = rs.getString("customRoomID");
                String creatorID = rs.getString("creatorID");
                String map = rs.getString("map");
                int block = rs.getInt("block");
                LocalDateTime created = rs.getTimestamp("created").toLocalDateTime();
                scrimList.getItems().add(new Scrimmage(id, customRoomID, creatorID, map, block));
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(LinkController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        handler = DatabaseHandler.getInstance();
        qu = "SELECT * FROM SQUAD";
        rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                String id = rs.getString("id");
                String squadName = rs.getString("name");
                squadList.getItems().add(new Squad(id, squadName));
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(LinkController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void handleMinusAction(ActionEvent event) {
        int squadPositionTmp = Integer.valueOf(squadPosition.getText());
        squadPositionTmp -= 1;
        squadPosition.setText(String.valueOf(squadPositionTmp));
    }

    @FXML
    private void handlePlusAction(ActionEvent event) {
        int squadPositionTmp = Integer.valueOf(squadPosition.getText());
        squadPositionTmp += 1;
        squadPosition.setText(String.valueOf(squadPositionTmp));
    }

}
