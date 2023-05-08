package com.example.demo.controller;

import com.example.demo.model.Categorie;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CategorieController {
    @Autowired
    DataSource dataSource;

    private final ObjectMapper objectMapper;

    public CategorieController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @RequestMapping("/categorie/creer")
    @ResponseBody
    public String creer (@RequestBody Categorie categorie) throws Exception {
        Connection connection = dataSource.getConnection();
        List<Categorie> cat = categorie.read(null, null, null, null, null, connection);

        if (cat.isEmpty()){
            if(!categorie.getLibelle().isEmpty()){
                categorie.setId("cat" + Categorie.getNextSequenceValue("seqcategorie", connection));
                categorie.create(connection);
                connection.close();
                Map<String, Object> jsonMap = new HashMap<>();
                jsonMap.put("message", "<div class=\"alert alert-success\" role=\"alert\">Catégorie créée avec succès !</div>");
                return objectMapper.writeValueAsString(jsonMap);
            }
            connection.close();
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("message", "<div class=\"alert alert-danger\" role=\"alert\">Libellé vide !</div>");
            return objectMapper.writeValueAsString(jsonMap);
        }
        else if (cat.size() == 1){
            connection.close();
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("message", "<div class=\"alert alert-danger\" role=\"alert\">Catégorie déjà existante !</div>");
            return objectMapper.writeValueAsString(jsonMap);
        }
        connection.close();
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("message", "<div class=\"alert alert-danger\" role=\"alert\">Libellé ne peut pas être vide!</div>");
        return objectMapper.writeValueAsString(jsonMap);
    }


    @RequestMapping("/categorie/modifier")
    @ResponseBody
    public String modifier(@RequestBody Categorie categorie) throws Exception{
        if (categorie.getLibelle() == null){
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("message", "<div class=\"alert alert-danger\" role=\"alert\">Libellé ne peut pas être vide!</div>");
            return objectMapper.writeValueAsString(jsonMap);
        }
        Connection connection = dataSource.getConnection();
        categorie.update(connection, new String[]{"id"}, null);
        connection.close();
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("message", "<div class=\"alert alert-success\" role=\"alert\">Catégorie modifiée avec succès !</div>");
        return objectMapper.writeValueAsString(jsonMap);
    }


    @RequestMapping("/categorie/supprimer")
    public ModelAndView supprimer(ModelAndView mv, Categorie categorie, @RequestParam(required = false) String redirect)throws Exception{
        Connection connection = dataSource.getConnection();
        categorie.delete(connection, null);
        mv.setViewName("redirect:" + redirect);
        connection.close();
        return mv;
    }
}
