/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copaff.ui.game;

import copaff.database.DatabaseHandler;
import copaff.model.Player;
import copaff.model.Squad;
import copaff.model.match_modes.Scrimmage;
import copaff.ui.link.LinkController;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import masktextfield.MaskTextField;
import squadcard.SquadCard;

/**
 * FXML Controller class
 *
 * @author TAVOS
 */
public class MatchController implements Initializable {

    private ObservableList<SquadCard> cards = FXCollections.observableArrayList();
    ;

    @FXML
    private ChoiceBox<Scrimmage> scrimList;
    @FXML
    private ChoiceBox<Player> player1;
    @FXML
    private ChoiceBox<Player> player2;
    @FXML
    private ChoiceBox<Player> player3;
    @FXML
    private ChoiceBox<Player> player4;
    @FXML
    private VBox oddSquadPositions;
    @FXML
    private VBox evenSquadPositions;
    @FXML
    private ChoiceBox<Squad> squadList;
    @FXML
    private MaskTextField squadPosition;
    @FXML
    private StackPane rootPane;
    @FXML
    private VBox mainContainer;
    @FXML
    private ChoiceBox<SquadCard> cardList;

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

        squadList.valueProperty().addListener((observable, oldValue, newValue) -> {
            player1.getItems().clear();
            player2.getItems().clear();
            player3.getItems().clear();
            player4.getItems().clear();

            DatabaseHandler dbHandler = DatabaseHandler.getInstance();
            String query = "SELECT * FROM SQUAD" + newValue.getId().replaceAll("-", "");
            ResultSet results = dbHandler.execQuery(query);
            try {
                while (results.next()) {
                    String playerID = results.getString("playerID");

                    String q = "SELECT * FROM PLAYER WHERE id=?";
                    PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(q);
                    stmt.setString(1, playerID);
                    ResultSet resultSet = stmt.executeQuery();
                    if (resultSet.next()) {
                        String player1ID = resultSet.getString("id");
                        String player1Name = resultSet.getString("name");
                        String player1Country = resultSet.getString("country");
                        LocalDateTime player1Created = resultSet.getTimestamp("created").toLocalDateTime();
                        Player playerTmp = new Player(player1ID, player1Name, player1Country, player1Created);
                        player1.getItems().add(playerTmp);
                        player2.getItems().add(playerTmp);
                        player3.getItems().add(playerTmp);
                        player4.getItems().add(playerTmp);
                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(MatchController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        player1.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                player2.getItems().add(oldValue);
                player3.getItems().add(oldValue);
                player4.getItems().add(oldValue);
            }
            player2.getItems().remove(newValue);
            player3.getItems().remove(newValue);
            player4.getItems().remove(newValue);
        });
        player2.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                player1.getItems().add(oldValue);
                player3.getItems().add(oldValue);
                player4.getItems().add(oldValue);
            }
            player1.getItems().remove(newValue);
            player3.getItems().remove(newValue);
            player4.getItems().remove(newValue);
        });
        player3.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                player1.getItems().add(oldValue);
                player2.getItems().add(oldValue);
                player4.getItems().add(oldValue);
            }
            player1.getItems().remove(newValue);
            player2.getItems().remove(newValue);
            player4.getItems().remove(newValue);
        });
        player4.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                player1.getItems().add(oldValue);
                player2.getItems().add(oldValue);
                player3.getItems().add(oldValue);
            }
            player1.getItems().remove(newValue);
            player2.getItems().remove(newValue);
            player3.getItems().remove(newValue);
        });

        cardList.setItems(cards);

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

    @FXML
    private void handleRemoveCardAction(ActionEvent event) {
        SquadCard squadCard = cardList.getSelectionModel().getSelectedItem();
        cards.remove(squadCard);
        evenSquadPositions.getChildren().remove(squadCard);
        oddSquadPositions.getChildren().remove(squadCard);

    }

    @FXML
    private void handleAddSquadToMatchAction(ActionEvent event) {
        SquadCard card = new SquadCard();

        card.setSquadInGamePosition(Integer.valueOf(squadPosition.getText()));
        Squad squad = squadList.getSelectionModel().getSelectedItem();
        card.setSquadName(squad.getName());
        card.setSquadID(squad.getId());

        Player player1Tmp = player1.getSelectionModel().getSelectedItem();
        card.setPlayer1Name(player1Tmp.getName());
        Player player2Tmp = player2.getSelectionModel().getSelectedItem();
        card.setPlayer2Name(player2Tmp.getName());
        Player player3Tmp = player3.getSelectionModel().getSelectedItem();
        card.setPlayer3Name(player3Tmp.getName());
        Player player4Tmp = player4.getSelectionModel().getSelectedItem();
        card.setPlayer4Name(player4Tmp.getName());

        try {
            String checkstmt = "SELECT logo FROM SQUAD WHERE id=?";
            PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);
            stmt.setString(1, squad.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Image squadLogo = new Image(rs.getBinaryStream("logo"));
                card.setSquadLogo(squadLogo);
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(LinkController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        cards.add(card);

        if (Integer.valueOf(squadPosition.getText()) % 2 == 0) {
            evenSquadPositions.getChildren().add(card);
        } else {
            oddSquadPositions.getChildren().add(card);
        }
    }

}
