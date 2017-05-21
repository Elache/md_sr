package fr.hibon.modepassesecurest.compte;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.ArrayList;

import fr.hibon.modepassesecurest.compte.exception.CompteException;
import fr.hibon.modepassesecurest.compte.utiles.ChiffeMode;
import fr.hibon.modepassesecurest.motpasse.ChainePasse;

import static org.junit.Assert.assertTrue;


/***
 * Teste les methodes de CompteUtilisateur utilisant le chiffrement (android.util.Base64) : mock
 */

@RunWith(AndroidJUnit4.class)
public class CompteUtilisateurMockTest {


    private ArrayList<Repertoire> lesRepertoires;
    private String nomUser;
    private String mailContactUser;
    private String notePersoUser;

    private String passeUserStr;
    private ChainePasse passeUser;
    private ChainePasse passeUserChiffre;
    private String cleChiffreDonneeUser;

    private String passeRecoursUserStr;
    private ChainePasse passeRecoursUser;
    private ChainePasse passeRecoursUserChiffre;
    private String cleChiffreDonneeRecours;

    private ChainePasse passeCompleteInternet;
    private ChainePasse passeCompleteInternetChiffre;
    private String cleChiffreDonneeCompleteInternet;

    private Repertoire rep1;

    // ////////////////// ECHANTILLONAGE //////////////////

    /**
     * Constructeur qui affecte des valeurs de test
     */
    public CompteUtilisateurMockTest() {
        nomUser = "loic.hibon";
        passeUserStr = "Fra:3-Bré:0!";
        passeRecoursUserStr = "le plop";
        mailContactUser = "lohibon@free.fr";
        notePersoUser = "Compte de test";
    }

    /**
     * Echantillonnage : CompteUtilisateur avec valeurs
     *
     * @return CompteUtilisateur de test
     */
    private CompteUtilisateur preparerCompteTest() {
        CompteUtilisateur cu = CompteUtilisateur.getCompteConnecte();
        try {
            CompteUtilisateur.renseigneLeCompte(nomUser, passeUserStr, passeRecoursUserStr, mailContactUser,
                    notePersoUser);
        } catch (CompteException e) {
        }
        lesRepertoires = new ArrayList<Repertoire>();

        passeUser = ChainePasse.composition(passeUserStr);
        passeUserChiffre = ChainePasse.composition("Chiffre-U-Fra");
        cleChiffreDonneeUser = "Clé U";

        rep1 = new Repertoire("RepTest", "");
        lesRepertoires.add(rep1);

        passeRecoursUser = ChainePasse.composition(passeRecoursUserStr);
        passeRecoursUserChiffre = ChainePasse.composition("plopCHIFFRE");
        cleChiffreDonneeRecours = "Clé R";

        passeCompleteInternet = ChainePasse.composition("ds,f43''tfzét_'NZ");
        passeCompleteInternetChiffre = ChainePasse.composition("le meme en chiffre");
        cleChiffreDonneeCompleteInternet = "la clé 3";
        return cu;
    }


    // ///////// TEsts

    @Test
    public void chiffrerLesPasses() throws CompteException {
        CompteUtilisateur compUser = preparerCompteTest() ;
        compUser.chiffrerLesPasses();

        boolean test = true ;
        test = test && (compUser.getCleChiffreUser() != null)
                && (compUser.getCleChiffreRecours() != null)
                && (compUser.getCleChiffreInternet() != null) ;
        test=  test &&  compUser.getPasseUser() != null &&  compUser.getPasseRecoursChiffre() != null && compUser.getPasseInternet() != null ;
        test = test && compUser.getPasseRecoursChiffre().longeurMot() > 24 && compUser.getCleChiffreRecours().length() > 24 ;
        assertTrue(test) ;
    }


}
