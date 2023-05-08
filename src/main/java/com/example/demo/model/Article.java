package com.example.demo.model;

import com.example.demo.util.Column;
import com.example.demo.util.Table;

import java.sql.Connection;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Table(name = "article")
public class Article extends BaseModel{

    @Column(name = "titre")
    String titre;

    @Column(name = "resume")
    String resume;

    @Column(name = "contenu")
    String contenu;

    @Column(name = "image")
    byte[] image;

    @Column(name = "idcategorie")
    String idcategorie;

    Categorie categorie;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getIdcategorie() {
        return idcategorie;
    }

    public void setIdcategorie(String idcategorie) {
        this.idcategorie = idcategorie;
    }

    public Article getArticle(Connection connection) throws Exception{
        List<Article> list = read(null, null, null, null, null, connection);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public String getImageShowableInHtml() {
        String base64Image = Base64.getEncoder().encodeToString(image);
        return "data:image/jpeg;base64," + base64Image;
    }

    public void setCategorie(Connection connection) throws Exception{
        Categorie cat = new Categorie();
        cat.setId(this.getIdcategorie());
        categorie = (Categorie) cat.read(null, null, null, null, null, connection).get(0);
    }

    public Categorie getCategorie(){
        return categorie;
    }

    public String getUrlRewriting() {
        return titre.toLowerCase().toLowerCase().replace("ù", "u").replace("è", "e").replace("ç", "c").replace("é", "e").replace("'", "").replace(" ", "-") + "-" + id + ".php";
    }

    @Override
    public String toString() {
        return "Article{" +
                "titre='" + titre + '\'' +
                ", resume='" + resume + '\'' +
                ", contenu='" + contenu + '\'' +
                ", idcategorie='" + idcategorie + '\'' +
                ", categorie=" + categorie +
                ", id='" + id + '\'' +
                '}';
    }
}
