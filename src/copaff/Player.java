/*
 *To change this license header, choose License Headers in Project Properties.
 *To change this template file, choose Tools | Templates
 *and open the template in the editor.
 */
package copaff;

import java.time.LocalDateTime;

/**
 *
 * @author Gustavo A Salazar Lima
 */
public class Player {

    private String name;
    private String nickname;
    private String ffId;
    private String titulo;
    private String foto;
    private String country;
    private LocalDateTime createdOn;

    public Player() {
    }

    public Player(String name, String nickname, String ffId, String titulo,
            String foto, String country) {
        this.name = name;
        this.nickname = nickname;
        this.ffId = ffId;
        this.titulo = titulo;
        this.foto = foto;
        this.country = country;
        this.createdOn = LocalDateTime.now();

    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname the name to set
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return the ffId
     */
    public String getFfId() {
        return ffId;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the name to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the foto
     */
    public String getFoto() {
        return foto;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @return the createdOn
     */
    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

}
