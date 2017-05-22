package fr.hibon.modepassesecurest.compte;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import fr.hibon.modepassesecurest.compte.utiles.ChiffeMode;

import static junit.framework.Assert.assertTrue;

/**
 * Teste la classe Donnee pour les methodes de chiffrement / dechiffrement <BR>qui dependent de
 * <B>android.util.Base64 (mock)</B>
 */
@RunWith(AndroidJUnit4.class)
public class DonneeMockTest {

    Donnee donneeUn;
    String mailDonneeUn, questionSecreteDonneeUn, categorieDonneeUn, noteDonneeUn, nomDonneeUn, siteWebDonneeUn,
            loginDonneeUn, passeDonneeUn;

    public Donnee echantillon() {
        passeDonneeUn = "jojoLapin";
        nomDonneeUn = "portail infos";
        loginDonneeUn = "identifiant";
        mailDonneeUn = "mail@mail.com";
        siteWebDonneeUn = "http://lc.f.tr";
        questionSecreteDonneeUn = "Quel était ?";
        categorieDonneeUn = "Compte website";
        noteDonneeUn = "Accès au forum";
        donneeUn = new Donnee(passeDonneeUn, nomDonneeUn, loginDonneeUn, mailDonneeUn, siteWebDonneeUn, questionSecreteDonneeUn, categorieDonneeUn, noteDonneeUn);
        return donneeUn ;
    }


    // ////////// affichage en clair du mot de passe //////////////////

    /**
     * Test l'Affichage du mot de passe en clair
     * <BR>Test : 1. genere un passe chiffr&eacute; (+ verifie existence via taille > 24 et verifie existence cle)
     * <br>2. Affichage en clair doit correspondre &agrave; passe initial
     */
    @Test
    public void afficherMotPasse() {
        boolean test = true ;
        Donnee d = echantillon() ;
        d.chiffrerMotPasse();
        test = test && d.getPasseDonneeChiffre().length() >= 24 && (d.getCleChiffrementDonnee() != null) ;
        String passDech = d.afficherMotPasse();
        test = test && passDech.equals(d.getPasseDonnee()) ;
        assertTrue(test);
    }


    // ////////// Chiffrement du mot de passe //////////////////

    /**
     * Chiffrement du mot de passe
     */
    @Test
    public void chiffrerMotPasse() {
        boolean test = true ;
        Donnee d = echantillon() ;
        d.chiffrerMotPasse();
        test = test && d.getPasseDonneeChiffre().length() >= 24 && (d.getCleChiffrementDonnee() != null) ;
        assertTrue(test);
    }


}
