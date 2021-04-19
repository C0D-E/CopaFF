/*
 *To change this license header, choose License Headers in Project Properties.
 *To change this template file, choose Tools | Templates
 *and open the template in the editor.
 */
package copaff;

/**
 *
 * @author Gustavo A Salazar Lima
 */
public class Team {

    private String name;
    private String id;
    private String logo;

    public Team() {
    }

    public Team(String name, String id, String logo) {
        this.name = name;
        this.id = id;
        this.logo = logo;
    }

    /**
     * @return the clanName
     */
    public String getName() {
        return name;
    }

    /**
     * @return the clanId
     */
    public String getId() {
        return id;
    }

    /**
     * @return the clanLogo
     */
    public String getLogo() {
        return logo;
    }

}
