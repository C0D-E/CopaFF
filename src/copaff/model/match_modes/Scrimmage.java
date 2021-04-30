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
    private String creatorID;
    private String map;
    private int block;
    private LocalDateTime created;

    public Scrimmage(String id, String creatorID, String map, int block) {
        this.id = id;
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

}
