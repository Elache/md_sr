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

    private CompteUtilisateur compte;

    private ArrayList<Repertoire> lesRepertoires;
    private String nomUser;
    private String mailContactUser;
    private String notePersoUser;

    private String passeUserStr;
    private String passeRecoursUserStr;
    private Repertoire rep1;

    // ////////////////// ECHANTILLONAGE //////////////////

    /**
     * Constructeur qui affecte des valeurs de test
     */
    public CompteUtilisateurMockTest() {
        compte = CompteUtilisateur.getCompteConnecte();
        nomUser = "loic.hibon";
        passeUserStr = "Fra:3-Bré:0!";
        passeRecoursUserStr = "le plop";
        mailContactUser = "lohibon@free.fr";
        notePersoUser = "Compte de test";
        rep1 = new Repertoire("RepTest", "");
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

        cu.getLesRepertoires().add(rep1);

        cu.setPasseUser(ChainePasse.composition(passeUserStr));
        cu.setPasseUserChiffre(ChainePasse.composition("Chiffre-U-Fra"));
        cu.setCleChiffreUser(null);

        cu.setPasseRecours(ChainePasse.composition(passeRecoursUserStr));
        cu.setPasseRecoursChiffre(ChainePasse.composition("plopCHIFFRE"));
        cu.setCleChiffreRecours(null);

        cu.setPasseInternet(ChainePasse.composition("ds,f43''tfzét_'NZ"));
        cu.setPasseInternetChiffre(ChainePasse.composition("le meme en chiffre"));
        cu.setCleChiffreInternet(null);
        return cu;
    }


    // ///////// Tests //////////////////////

    /**
     * Verifie methode chiffrant tous les passes (User, Recours, internet s'il y a lieu)
     * <BR>Teste : les cles ne doivent plus etre nulles (et longueur > 20) et les passes chiffres initiaux doivent etre remplac&eacute;s
     * @throws CompteException (cf regles sur id / pass ; control&eacute; dans le test)
     */
    @Test
    public void chiffrerLesPasses() throws CompteException {
        CompteUtilisateur compUser = preparerCompteTest();
        compUser.chiffrerLesPasses();
        String passUChiffre = compUser.getPasseUserChiffre().getChaineDuPasse();
        String passRChiffre = compUser.getPasseRecoursChiffre().getChaineDuPasse();
        String passIChiffre = compUser.getPasseInternetChiffre().getChaineDuPasse();
        String cleU = compUser.getCleChiffreUser();
        String cleR = compUser.getCleChiffreRecours();
        String cleI = compUser.getCleChiffreInternet();
        boolean test = true;
        test = test && (!passUChiffre.equals("Chiffre-U-Fra")) && (!passRChiffre.equals("plopCHIFFRE")) && (!passIChiffre.equals("le meme en chiffre")) ;
        test = test &&  (cleU != null) && (cleR != null) && (cleI != null) ;
        test = test &&  (cleU.length() > 20) && (cleR.length() > 20) && (cleI.length() > 20) ;
        assertTrue(test);
    }
}
