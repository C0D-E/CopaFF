/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copaff.ui.data;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import copaff.database.DatabaseHandler;
import copaff.ui.link.LinkController;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.BorderPane;
import squadcard.Player;

/**
 * FXML Controller class
 *
 * @author tavos
 */
public class DataController implements Initializable {

    @FXML
    private BorderPane rootContainer;
    @FXML
    private JFXTreeTableView tableView;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JFXTreeTableColumn empty = new JFXTreeTableColumn("");
        JFXTreeTableColumn name = new JFXTreeTableColumn("Name");
        JFXTreeTableColumn id = new JFXTreeTableColumn("ID");
        name.setPrefWidth(110);
        id.setPrefWidth(90);

        tableView.getColumns().addAll(empty, name, id);

        empty.setCellValueFactory(
                new TreeItemPropertyValueFactory<Player, String>("")
        );
        name.setCellValueFactory(
                new TreeItemPropertyValueFactory<Player, String>("name")
        );

        id.setCellValueFactory(
                new TreeItemPropertyValueFactory<Player, String>("id")
        );

        TreeItem<Player> rootNode = new TreeItem<Player>(new Player("", "PLAYERS", ""));
        
        DatabaseHandler dbHandler = DatabaseHandler.getInstance();
        String query = "SELECT * FROM PLAYER";
        ResultSet resulSet = dbHandler.execQuery(query);
        try {
            while (resulSet.next()) {
                String playerId = resulSet.getString("id");
                String playerName = resulSet.getString("name");
                String playerPhoneNumber = resulSet.getString("phoneNumber");
                LocalDateTime created = resulSet.getTimestamp("created").toLocalDateTime();
                Player player = new Player(playerId, playerName, playerPhoneNumber, created);
                rootNode.getChildren().add(new TreeItem<Player>(player));
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(LinkController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        tableView.setRoot(rootNode);
    }
}
