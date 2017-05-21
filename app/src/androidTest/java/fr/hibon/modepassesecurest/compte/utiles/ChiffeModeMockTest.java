package fr.hibon.modepassesecurest.compte.utiles;

import org.junit.Test;
import org.junit.runner.RunWith;


import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import android.support.test.runner.AndroidJUnit4;

import java.security.NoSuchAlgorithmException;

import javax.crypto.SecretKey;

/**
 * Teste la Classe ChiffeMode
 *
 */
@RunWith(AndroidJUnit4.class)
public class ChiffeModeMockTest {


    public ChiffeModeMockTest() {
    }

    /**
     * Verifie generation du mot de passe chiffr&eacute; : >= 24 caracteres
     */
    @Test
    public void chiffrer_nominal() {
        String pass = "rz'ssNFéè&" ;
        ChiffeMode ch = new ChiffeMode();
        ch.chiffrer(pass) ;
        assertTrue(ch.getPasseCode().length() >= 24 );
    }


    /**
     * Verifie generation du mot de passe chiffr&eacute; : >= 24 caracteres
     */
    @Test
    public void chiffrer_tresCourt() {
        String pass = "r" ;
        ChiffeMode ch = new ChiffeMode();
        ch.chiffrer(pass) ;
        assertTrue(ch.getPasseCode().length() >= 24 );
    }


    /**
     * Verifie generation du mot de passe chiffr&eacute; : >= 24 caracteres
     */
    @Test
    public void chiffrer_60MaxGeneration() {
        String pass = "rz'ssNFéè&rz'ssNFéè&rz'ssNFéè&rz'ssNFéè&rz'ssNFéè&rz'ssNFéè&" ;
        ChiffeMode ch = new ChiffeMode();
        ch.chiffrer(pass) ;
        assertTrue(ch.getPasseCode().length() > 110 );
    }


    /**
     * Verifie generation du mot de passe chiffr&eacute; : >= 24 caracteres
     */
    @Test
    public void chiffrer_200MaxConservation() {
        String pass = "rz'ssNFéè&rz'ssNFéè&rz'ssNFéè&rz'ssNFéè&rz'ssNFéè&rz'ssNFéè&rz'ssNFéè&rz'ssNFéè&rz'ssNFéè&rz'ssNFéè&rz'ssNFéè&rz'ssNFéè&rz'ssNFéè&rz'ssNFéè&rz'ssNFéè&rz'ssNFéè&rz'ssNFéè&rz'ssNFéè&rz'ssNFéè&rz'ssNFéè&" ;
        ChiffeMode ch = new ChiffeMode();
        ch.chiffrer(pass) ;
        assertTrue(ch.getPasseCode().length() >= 300 );
    }

    /**
     * Verifie correspondance apres chiffrage puis dechiffrage
     */
    @Test
    public void chiffrerDechiffrer_nominal() {
        String pass = "rz'ssNFéè&" ;
        ChiffeMode ch = new ChiffeMode();
        ch.chiffrer(pass) ;
        assertTrue(ch.getPasseCode().length() >= 24 );
        assertEquals(  ch.dechiffrer(ch.getPasseCode(),ch.getCleCode()) , pass) ;
    }


    /**
     * Verifie existence de la SecretKey via getAlgorithm()
     */
    @Test
    public void genererCle_nominal() {
        ChiffeMode ch = new ChiffeMode() ;
        SecretKey dsk = null ;
        try {
            dsk = ch.genererCle();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert dsk != null;
        assertEquals(dsk.getAlgorithm(), "AES");
    }



}
