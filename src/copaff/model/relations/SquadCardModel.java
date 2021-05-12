/*
 *To change this license header, choose License Headers in Project Properties.
 *To change this template file, choose Tools | Templates
 *and open the template in the editor.
 */
package copaff.model.relations;

/**
 *
 * @author Gustavo A Salazar Lima
 */
public class SquadCardModel {

    private String cardID;
    private int squadInGamePosition;
    private String squadID;
    private String player1ID;
    private String player2ID;
    private String player3ID;
    private String player4ID;
    private int player1Kills;
    private int player2Kills;
    private int player3Kills;
    private int player4Kills;
    private int finalSquadPosition;
    private int totalPoints;

    public SquadCardModel(String cardID, int squadInGamePosition, String squadID,
            String player1ID, String player2ID, String player3ID, String player4ID,
            int player1Kills, int player2Kills, int player3Kills, int player4Kills,
            int finalSquadPosition, int totalPoints) {
        this.cardID = cardID;
        this.squadInGamePosition = squadInGamePosition;
        this.squadID = squadID;
        this.player1ID = player1ID;
        this.player2ID = player2ID;
        this.player3ID = player3ID;
        this.player4ID = player4ID;
        this.player1Kills = player1Kills;
        this.player2Kills = player2Kills;
        this.player3Kills = player3Kills;
        this.player4Kills = player4Kills;
        this.finalSquadPosition = finalSquadPosition;
        this.totalPoints = totalPoints;
    }

    /**
     * @return the cardID
     */
    public String getCardID() {
        return cardID;
    }

    /**
     * @param cardID the cardID to set
     */
    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    /**
     * @return the squadInGamePosition
     */
    public int getSquadInGamePosition() {
        return squadInGamePosition;
    }

    /**
     * @param squadInGamePosition the squadInGamePosition to set
     */
    public void setSquadInGamePosition(int squadInGamePosition) {
        this.squadInGamePosition = squadInGamePosition;
    }

    /**
     * @return the squadID
     */
    public String getSquadID() {
        return squadID;
    }

    /**
     * @param squadID the squadID to set
     */
    public void setSquadID(String squadID) {
        this.squadID = squadID;
    }

    /**
     * @return the player1ID
     */
    public String getPlayer1ID() {
        return player1ID;
    }

    /**
     * @param player1ID the player1ID to set
     */
    public void setPlayer1ID(String player1ID) {
        this.player1ID = player1ID;
    }

    /**
     * @return the player2ID
     */
    public String getPlayer2ID() {
        return player2ID;
    }

    /**
     * @param player2ID the player2ID to set
     */
    public void setPlayer2ID(String player2ID) {
        this.player2ID = player2ID;
    }

    /**
     * @return the player3ID
     */
    public String getPlayer3ID() {
        return player3ID;
    }

    /**
     * @param player3ID the player3ID to set
     */
    public void setPlayer3ID(String player3ID) {
        this.player3ID = player3ID;
    }

    /**
     * @return the player4ID
     */
    public String getPlayer4ID() {
        return player4ID;
    }

    /**
     * @param player4ID the player4ID to set
     */
    public void setPlayer4ID(String player4ID) {
        this.player4ID = player4ID;
    }

    /**
     * @return the player1Kills
     */
    public int getPlayer1Kills() {
        return player1Kills;
    }

    /**
     * @param player1Kills the player1Kills to set
     */
    public void setPlayer1Kills(int player1Kills) {
        this.player1Kills = player1Kills;
    }

    /**
     * @return the player2Kills
     */
    public int getPlayer2Kills() {
        return player2Kills;
    }

    /**
     * @param player2Kills the player2Kills to set
     */
    public void setPlayer2Kills(int player2Kills) {
        this.player2Kills = player2Kills;
    }

    /**
     * @return the player3Kills
     */
    public int getPlayer3Kills() {
        return player3Kills;
    }

    /**
     * @param player3Kills the player3Kills to set
     */
    public void setPlayer3Kills(int player3Kills) {
        this.player3Kills = player3Kills;
    }

    /**
     * @return the player4Kills
     */
    public int getPlayer4Kills() {
        return player4Kills;
    }

    /**
     * @param player4Kills the player4Kills to set
     */
    public void setPlayer4Kills(int player4Kills) {
        this.player4Kills = player4Kills;
    }

    /**
     * @return the finalSquadPosition
     */
    public int getFinalSquadPosition() {
        return finalSquadPosition;
    }

    /**
     * @param finalSquadPosition the finalSquadPosition to set
     */
    public void setFinalSquadPosition(int finalSquadPosition) {
        this.finalSquadPosition = finalSquadPosition;
    }

    /**
     * @return the totalPoints
     */
    public int getSquadTotalPoints() {
        return totalPoints;
    }

}
