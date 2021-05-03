/*
 *To change this license header, choose License Headers in Project Properties.
 *To change this template file, choose Tools | Templates
 *and open the template in the editor.
 */
package copaff.model;

import java.sql.Blob;
import java.time.LocalDateTime;

/**
 *
 * @author Gustavo A Salazar Lima
 */
public class Player {

    private String id;
    private String name;
    private String country;
    private LocalDateTime created;

    public Player() {
    }

    public Player(String name, String id, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.created = LocalDateTime.now();

    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the createdOn
     */
    public LocalDateTime getCreationDateTime() {
        return created;
    }
}
