/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigfito.marvel.explorer.model;

/**
 *
 * @author aorozco
 */
public class ComicCharacter {
    
    private String idCharacter;
    private String nameCharacter;
    private String descriptionCharacter;
    private String thumbnailCharacter;

    public ComicCharacter(){

    }

    public String getIdCharacter() {
        return idCharacter;
    }

    public void setIdCharacter(String idCharacter) {
        this.idCharacter = idCharacter;
    }

    public String getNameCharacter() {
        return nameCharacter;
    }

    public void setNameCharacter(String nameCharacter) {
        this.nameCharacter = nameCharacter;
    }

    public String getDescriptionCharacter() {
        return descriptionCharacter;
    }

    public void setDescriptionCharacter(String descriptionCharacter) {
        this.descriptionCharacter = descriptionCharacter;
    }

    public String getThumbnailCharacter() {
        return thumbnailCharacter;
    }

    public void setThumbnailCharacter(String thumbnailCharacter) {
        this.thumbnailCharacter = thumbnailCharacter;
    }
    
}
