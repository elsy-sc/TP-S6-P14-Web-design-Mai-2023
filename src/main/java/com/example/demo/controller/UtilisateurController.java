package com.example.demo.controller;

import com.example.demo.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UtilisateurController {

    @Autowired
    DataSource dataSource;
    @RequestMapping("/utilisateur/login-utilisateur.php")
    public ModelAndView login(ModelAndView mv, @RequestParam(required = false) String email, @RequestParam(required = false) String motdepasse) throws Exception {
        if(email != null && motdepasse != null){
            Connection connection = dataSource.getConnection();
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setEmail(email);
            utilisateur = utilisateur.getUtilisateur(connection);
            if (utilisateur != null){
                utilisateur.setMotdepasse(motdepasse);
                utilisateur = utilisateur.getUtilisateur(connection);
                if(utilisateur != null){
                    mv.setViewName("redirect:/article/liste-des-articles-sur-les-informations-de-lintelligence-artificielle-" + utilisateur.getId() +  ".php");
                }
                else{
                    mv.setViewName("redirect:/utilisateur/login-utilisateur.php?err_motdepasse");
                }
            }
            else{
                mv.setViewName("redirect:/utilisateur/login-utilisateur.php?err_email");
            }
            connection.close();
            return mv;
        }
        mv.setViewName("login-utilisateur");
        return mv;
    }

}
