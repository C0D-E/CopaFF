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
public class SquadAlternate {
    
    private String playerID;

    public SquadAlternate(String playerID) {
        this.playerID = playerID;
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
    
    

}