package com.example.demo.controller;

import com.example.demo.model.Article;
import com.example.demo.model.Categorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

@Controller
public class ArticleController {
    @Autowired
    DataSource dataSource;

    @RequestMapping({"/article/liste-des-articles-sur-les-informations-de-lintelligence-artificielle-{idutilisateur}.php"})
    public ModelAndView lister(ModelAndView mv, @RequestParam(required = false) String keyword, @PathVariable("idutilisateur") String idutilisateur) throws Exception {
        Connection connection = dataSource.getConnection();
        mv.addObject("listecategorie", new Categorie().read(null, null, null, null, null, connection));
        String toSearch = null;
        if (keyword != null) {
            toSearch = keyword;
            mv.addObject("keyword", keyword);
        }
        List<Article> listearticle = new Article().read(new String[]{"titre", "resume", "contenu"}, toSearch, null, null, null, connection);
        for (Article article : listearticle) {
            article.setCategorie(connection);
        }
        mv.addObject("listearticle", listearticle);
        mv.addObject("idutilisateur", idutilisateur);
        connection.close();
        mv.setViewName("liste-article-back-office");
        return mv;
    }

    @RequestMapping({"/article/liste-des-articles-sur-les-informations-de-lintelligence-artificielle-front-office.php"})
    public ModelAndView listerFrontoffice(ModelAndView mv, @RequestParam(required = false) String keyword) throws Exception {
        Connection connection = dataSource.getConnection();
        mv.addObject("listecategorie", new Categorie().read(null, null, null, null, null, connection));
        String toSearch = null;
        if (keyword != null) {
            toSearch = keyword;
            mv.addObject("keyword", keyword);
        }
        List<Article> listearticle = new Article().read(new String[]{"titre", "resume", "contenu"}, toSearch, null, null, null, connection);
        for (Article article : listearticle) {
            article.setCategorie(connection);
        }
        mv.addObject("listearticle", listearticle);
        connection.close();
        mv.setViewName("liste-article-front-office");
        return mv;
    }



    @RequestMapping("/article/detail-des-articles/*-{id}.php")
    public ModelAndView detail(ModelAndView mv, @PathVariable("id") String id) throws Exception {
        Connection connection = dataSource.getConnection();
        Article art = new Article();
        art.setId(id);
        art = art.getArticle(connection);
        art.setCategorie(connection);
        mv.addObject("article", art);
        connection.close();
        mv.setViewName("detail-article-back-office");
        return mv;
    }

    @RequestMapping("/article/detail-des-articles-front-office/*-{id}.php")
    public ModelAndView detailFrontOffice(ModelAndView mv, @PathVariable("id") String id) throws Exception {
        Connection connection = dataSource.getConnection();
        Article art = new Article();
        art.setId(id);
        art = art.getArticle(connection);
        art.setCategorie(connection);
        mv.addObject("article", art);
        connection.close();
        mv.setViewName("detail-article-front-office");
        return mv;
    }

    @RequestMapping("/article/nouveau-article.php")
    public ModelAndView nouveau(@RequestParam(required = false, value = "file") MultipartFile file, ModelAndView mv, Article article) throws Exception {
        Connection connection = dataSource.getConnection();
        mv.addObject("listecategorie", new Categorie().read(null, null, null, null, null, connection));
        if (article.getTitre() != null) {
            Article temp = new Article();
            temp.setTitre(article.getTitre());
            temp.setIdcategorie(article.getIdcategorie());

            if (temp.getArticle(connection) != null) {
                mv.setViewName("redirect:/article/nouveau-article.php?err_article_existant");
                connection.close();
                return mv;
            } else {
                article.setId("art" + Article.getNextSequenceValue("seqarticle", connection));
                if (file.getBytes().length != 0) {
                    article.setImage(file.getBytes());
                }
                article.create(connection);
                mv.setViewName("redirect:/article/nouveau-article.php?succ_article_nouveau");
                connection.close();
                return mv;
            }
        }
        connection.close();
        mv.setViewName("nouveau-article");
        return mv;
    }

    @RequestMapping("/article/supprimer-article.php")
    public ModelAndView supprimer(ModelAndView mv, Article article)throws Exception{
        Connection connection = dataSource.getConnection();
        article.delete(connection, null);
        mv.setViewName("redirect:/article/liste-des-articles-sur-les-informations-de-lintelligence-artificielle.php?succ_article_suppr");
        connection.close();
        return mv;
    }

    @RequestMapping("/article/modifier-article.php")
    public ModelAndView modifier(@RequestParam(required = false, value = "file") MultipartFile file, ModelAndView mv, Article article) throws Exception{
        Connection connection = dataSource.getConnection();
        if (file.getBytes().length != 0) {
            article.setImage(file.getBytes());
        }
        article.update(connection, new String[]{"id"},null);
        connection.close();
        mv.setViewName("redirect:/article/liste-des-articles-sur-les-informations-de-lintelligence-artificielle.php?succ_article_modif");
        return mv;
    }

}
