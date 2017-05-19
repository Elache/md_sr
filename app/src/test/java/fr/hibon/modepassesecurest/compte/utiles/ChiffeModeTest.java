package fr.hibon.modepassesecurest.compte.utiles;


import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Teste la Classe ChiffeMode
 *
 */
public class ChiffeModeTest {


    public ChiffeModeTest() {
    }


    // //////// PREFIXER  ////////////////////

    /**
    * */
    @Test
    public void prefixer_doitAjouter() {
        ChiffeMode ch = new ChiffeMode() ;
        String aPrefixer = "ChaineaPrefixer" ;
        String attendue = ChiffeMode.prefix + aPrefixer ;
        assertEquals(attendue, ch.prefixer(aPrefixer));
    }

    /**
     * */
    @Test
    public void prefixer_doitAjouterAVide() {
        ChiffeMode ch = new ChiffeMode() ;
        String aPrefixer = "" ;
        String attendue = ChiffeMode.prefix + aPrefixer ;
        assertEquals(attendue, ch.prefixer(aPrefixer));
    }

    /**
     * */
    @Test
    public void prefixer_doitAjouterANull() {
        ChiffeMode ch = new ChiffeMode() ;
        String aPrefixer = null ;
        // prefixer remplace null par chaine vide
        String attendue = ChiffeMode.prefix + "" ;
        assertEquals(attendue, ch.prefixer(aPrefixer));
    }



    // //////// DE-PREFIXER  ////////////////////


    /**
     * */
    @Test
    public void deprefixer_doitDeprefixer() {
        ChiffeMode ch = new ChiffeMode() ;
        String aDeprefixer = ChiffeMode.prefix + "leModePasseLaSecuReste" ;
        // prefixer remplace null par chaine vide
        String attendue = "leModePasseLaSecuReste" ;
        assertEquals(attendue, ch.dePrefixer(aDeprefixer));
    }


    /**
     * */
    @Test
    public void deprefixer_tropCourt_quePrefixe() {
        ChiffeMode ch = new ChiffeMode() ;
        String aDeprefixer = ChiffeMode.prefix ;
        String attendue = "" ;
        assertEquals(attendue, ch.dePrefixer(aDeprefixer));
    }


    /**
     * */
    @Test
    public void deprefixer_tropCourte() {
        ChiffeMode ch = new ChiffeMode() ;
        String aDeprefixer = "fderizis" ;
        String attendue = "" ;
        assertEquals(attendue, ch.dePrefixer(aDeprefixer));
    }

    /**
     * */
    @Test
    public void deprefixer_pasLePrefixe() {
        ChiffeMode ch = new ChiffeMode() ;
        String aDeprefixer = "leModePasseLaSecuReste" + ChiffeMode.prefix ;
        String attendue = "" ;
        assertEquals(attendue, ch.dePrefixer(aDeprefixer));
    }

    /**
     * */
    @Test
    public void deprefixer_presquePrefixe() {
        ChiffeMode ch = new ChiffeMode() ;
        String aDeprefixer = ChiffeMode.prefix.substring(0,8) +  "leModePasseLaSecuReste" ;
        String attendue = "" ;
        assertEquals(attendue, ch.dePrefixer(aDeprefixer));
    }



}
