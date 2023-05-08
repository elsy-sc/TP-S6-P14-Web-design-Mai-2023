package com.example.demo.model;

import com.example.demo.util.Column;
import com.example.demo.util.Table;

import java.sql.Connection;
import java.util.List;

@Table(name = "utilisateur")
public class Utilisateur extends BaseModel{
    @Column(name = "noms")
    String noms;

    @Column(name = "email")
    String email;

    @Column(name = "motdepasse")
    String motdepasse;

    public String getNoms() {
        return noms;
    }

    public void setNoms(String noms) {
        this.noms = noms;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    public Utilisateur getUtilisateur(Connection connection) throws Exception{
        List<Utilisateur> list = read(null, null, null, null, null, connection);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

}
