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
public class Player {

    private String name;
    private String ffId;
    private Team team;
    private Clan clan;
    private String country;

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
     * @return the ffId
     */
    public String getFfId() {
        return ffId;
    }

    /**
     * @return the team
     */
    public Team getTeam() {
        return team;
    }

    /**
     * @param team the team to set
     */
    public void setTeam(Team team) {
        this.team = team;
    }

    /**
     * @return the clan
     */
    public Clan getClan() {
        return clan;
    }

    /**
     * @param clan the clan to set
     */
    public void setClan(Clan clan) {
        this.clan = clan;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

}
