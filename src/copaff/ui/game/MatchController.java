/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copaff.ui.game;

import copaff.alert.AlertMaker;
import copaff.database.DataHelper;
import copaff.database.DatabaseHandler;
import copaff.model.match_modes.Scrimmage;
import copaff.model.relations.SquadCardModel;
import copaff.ui.link.LinkController;
import static copaff.ui.main.CopaFF.stage;
import copaff.util.LibraryAssistantUtil;
import copaff.util.Util;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import squadcard.Player;
import squadcard.Squad;
import squadcard.SquadCard;

/**
 * FXML Controller class
 *
 * @author TAVOS
 */
public class MatchController implements Initializable {

    private ObservableList<SquadCard> cards = FXCollections.observableArrayList();

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
     *
     * @param url
     * @param rb
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
                        String player1PhoneNumber = resultSet.getString("phoneNumber");
                        LocalDateTime player1Created = resultSet.getTimestamp("created").toLocalDateTime();
                        Player playerTmp = new Player(player1ID, player1Name, player1PhoneNumber, player1Created);
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
        if (squadList.getSelectionModel().isEmpty()
                | player1.getSelectionModel().isEmpty()
                | player2.getSelectionModel().isEmpty()
                | player3.getSelectionModel().isEmpty()
                | player4.getSelectionModel().isEmpty()) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "No Item Selected", "Please select the Players and the Squad to be add.");
            return;
        }

        SquadCard card = new SquadCard();

        card.setSquadInGamePosition(Integer.valueOf(squadPosition.getText()));
        Squad squad = squadList.getSelectionModel().getSelectedItem();
        card.setSquad(squad);

        card.setPlayer1(player1.getSelectionModel().getSelectedItem());
        card.setPlayer2(player2.getSelectionModel().getSelectedItem());
        card.setPlayer3(player3.getSelectionModel().getSelectedItem());
        card.setPlayer4(player4.getSelectionModel().getSelectedItem());

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

        if (cards.size() < 12) {
            cards.add(card);
        } else {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Maximum Reach", "Please remove squads before adding more.");
            return;
        }

        card.SetCardHeight(80);
        card.SetCardWidth(50);
        if (Integer.valueOf(squadPosition.getText()) % 2 == 0) {
            evenSquadPositions.getChildren().add(card);
        } else {
            oddSquadPositions.getChildren().add(card);
        }
    }

    @FXML
    private void handleLoadMatchAction(ActionEvent event) {
        if (scrimList.getSelectionModel().isEmpty()) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "No Item Selected", "Please select the Scrimmage to be load.");
            return;
        }
        cards.clear();
        DatabaseHandler handler = DatabaseHandler.getInstance();
        String qu = "SELECT * FROM MATCH" + scrimList.getSelectionModel().getSelectedItem().getId().replace("-", "");
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                String cardID = rs.getString("cardID");
                int squadInGamePosition = rs.getInt("squadInGamePosition");
                String squadID = rs.getString("squadID");
                String player1ID = rs.getString("playerID");
                String player2ID = rs.getString("player2ID");
                String player3ID = rs.getString("player3ID");
                String player4ID = rs.getString("player4ID");
                int player1Kills = rs.getInt("player1Kills");
                int player2Kills = rs.getInt("player2Kills");
                int player3Kills = rs.getInt("player3Kills");
                int player4Kills = rs.getInt("player4Kills");
                int finalSquadPosition = rs.getInt("finalSquadPosition");

                Squad squad = new Squad();
                String checkstmt = "SELECT * FROM SQUAD WHERE id=?";
                PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);
                stmt.setString(1, squadID);
                ResultSet result = stmt.executeQuery();
                if (result.next()) {
                    String squadIDTmp = result.getString("id");
                    String squadNameTmp = result.getString("name");

                    squad = new Squad(squadIDTmp, squadNameTmp);
                }
                Image squadLogo = null;
                checkstmt = "SELECT logo FROM SQUAD WHERE id=?";
                stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);
                stmt.setString(1, squad.getId());
                result = stmt.executeQuery();
                if (result.next()) {
                    squadLogo = new Image(result.getBinaryStream("logo"));
                }

                Player player1Tmp = new Player();
                checkstmt = "SELECT * FROM PLAYER WHERE id=?";
                stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);
                stmt.setString(1, player1ID);
                result = stmt.executeQuery();
                if (result.next()) {
                    String playerIDTmp = result.getString("id");
                    String playerNameTmp = result.getString("name");
                    String playerPhoneNumberTmp = result.getString("phoneNumber");
                    LocalDateTime playerCreatedTmp = result.getTimestamp("created").toLocalDateTime();

                    player1Tmp = new Player(playerIDTmp, playerNameTmp, playerPhoneNumberTmp, playerCreatedTmp);
                }
                Player player2Tmp = new Player();
                checkstmt = "SELECT * FROM PLAYER WHERE id=?";
                stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);
                stmt.setString(1, player2ID);
                result = stmt.executeQuery();
                if (result.next()) {
                    String playerIDTmp = result.getString("id");
                    String playerNameTmp = result.getString("name");
                    String playerPhoneNumberTmp = result.getString("phoneNumber");
                    LocalDateTime playerCreatedTmp = result.getTimestamp("created").toLocalDateTime();

                    player2Tmp = new Player(playerIDTmp, playerNameTmp, playerPhoneNumberTmp, playerCreatedTmp);
                }
                Player player3Tmp = new Player();
                checkstmt = "SELECT * FROM PLAYER WHERE id=?";
                stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);
                stmt.setString(1, player3ID);
                result = stmt.executeQuery();
                if (result.next()) {
                    String playerIDTmp = result.getString("id");
                    String playerNameTmp = result.getString("name");
                    String playerPhoneNumberTmp = result.getString("phoneNumber");
                    LocalDateTime playerCreatedTmp = result.getTimestamp("created").toLocalDateTime();

                    player3Tmp = new Player(playerIDTmp, playerNameTmp, playerPhoneNumberTmp, playerCreatedTmp);
                }
                Player player4Tmp = new Player();
                checkstmt = "SELECT * FROM PLAYER WHERE id=?";
                stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(checkstmt);
                stmt.setString(1, player4ID);
                result = stmt.executeQuery();
                if (result.next()) {
                    String playerIDTmp = result.getString("id");
                    String playerNameTmp = result.getString("name");
                    String playerPhoneNumberTmp = result.getString("phoneNumber");
                    LocalDateTime playerCreatedTmp = result.getTimestamp("created").toLocalDateTime();

                    player4Tmp = new Player(playerIDTmp, playerNameTmp, playerPhoneNumberTmp, playerCreatedTmp);
                }

                SquadCard card = new SquadCard();
                card.setCardID(cardID);
                card.setSquadInGamePosition(squadInGamePosition);
                card.setSquad(squad);
                card.setPlayer1(player1Tmp);
                card.setPlayer2(player2Tmp);
                card.setPlayer3(player3Tmp);
                card.setPlayer4(player4Tmp);
                card.setKillsPlayer1(player1Kills);
                card.setKillsPlayer2(player2Kills);
                card.setKillsPlayer3(player3Kills);
                card.setKillsPlayer4(player4Kills);
                card.setSquadFinalPosition(finalSquadPosition);
                card.setSquadLogo(squadLogo);

                if (cards.size() < 12) {
                    cards.add(card);
                } else {
                    AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Maximum Reach", "Please remove squads before adding more.");
                    return;
                }

                if (squadInGamePosition % 2 == 0) {
                    evenSquadPositions.getChildren().add(card);
                } else {
                    oddSquadPositions.getChildren().add(card);
                }
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(LinkController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleExportMatchAsPDFAction(ActionEvent event) {
        Util util = new Util(cards.);
    }

    @FXML
    private void handleSaveMatchAction(ActionEvent event) {
        if (scrimList.getSelectionModel().isEmpty()) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "No Item Selected", "Please select the Srimmage that belongs to the match.");
            return;
        }
        if (evenSquadPositions.getChildren().size() <= 0 & oddSquadPositions.getChildren().size() <= 0) {
            return;
        }
        for (SquadCard card : cards) {
            SquadCardModel cardModel = new SquadCardModel(card.getCardID(),
                    card.getSquadInGamePosition(), card.getSquad().getId(),
                    card.getPlayer1().getId(), card.getPlayer2().getId(),
                    card.getPlayer3().getId(), card.getPlayer4().getId(),
                    card.getKillsPlayer1(), card.getKillsPlayer2(),
                    card.getKillsPlayer3(), card.getKillsPlayer4(),
                    card.getSquadFinalPosition(), card.getSquadPositionPoints(),
                    card.getSquadTotalPoints());
            DataHelper.insertNewCard(scrimList.getSelectionModel().getSelectedItem().getId(), cardModel);
        }

    }

}
