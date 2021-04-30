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
public class Game {

    private String playerID;
    private int kills;
    private int position;

    public Game(String playerID, int kills, int position) {
        this.playerID = playerID;
        this.kills = kills;
        this.position = position;
    }

    /**
     * @return the playerID
     */
    public String getPlayerId() {
        return playerID;
    }

    /**
     * @param playerID the playerID to set
     */
    public void setPlayerId(String playerID) {
        this.playerID = playerID;
    }

    /**
     * @return the kills
     */
    public int getKills() {
        return kills;
    }

    /**
     * @param kills the kills to set
     */
    public void setKills(int kills) {
        this.kills = kills;
    }

    /**
     * @return the position
     */
    public int getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(int position) {
        this.position = position;
    }

}
