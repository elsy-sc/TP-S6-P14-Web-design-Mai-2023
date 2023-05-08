package com.example.demo.model;

import com.example.demo.util.Column;
import com.example.demo.util.Table;

@Table(name = "categorie")
public class Categorie extends BaseModel {
    @Column(name = "libelle")
    String libelle;

    @Column(name = "description")
    String description;

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
