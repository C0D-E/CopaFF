/*
 *To change this license header, choose License Headers in Project Properties.
 *To change this template file, choose Tools | Templates
 *and open the template in the editor.
 */
package copaff.model.match_modes;

import java.time.LocalDateTime;

/**
 *
 * @author Gustavo A Salazar Lima
 */
public class Scrimmage {

    private String id;
    private String customRoomID;
    private String creatorID;
    private String map;
    private int block;
    private LocalDateTime created;

    public Scrimmage(String id, String customRoomID, String creatorID, String map, int block) {
        this.id = id;
        this.customRoomID = customRoomID;
        this.creatorID = creatorID;
        this.map = map;
        this.block = block;
        this.created = LocalDateTime.now();
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the customRoomID
     */
    public String getCustomRoomID() {
        return customRoomID;
    }

    /**
     * @param customRoomID the customRoomID to set
     */
    public void setCustomRoomID(String customRoomID) {
        this.customRoomID = customRoomID;
    }

    /**
     * @return the creatorID
     */
    public String getCreatorId() {
        return creatorID;
    }

    /**
     * @return the map
     */
    public String getMap() {
        return map;
    }

    /**
     * @return the group
     */
    public int getBlock() {
        return block;
    }

    /**
     * @return the created
     */
    public LocalDateTime getCreated() {
        return created;
    }

    @Override
    public String toString() {
        return getMap() + " - " + getCustomRoomID();
    }

}
