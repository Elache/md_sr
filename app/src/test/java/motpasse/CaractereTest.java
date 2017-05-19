package motpasse;

import org.junit.Test;

import fr.hibon.modepassesecurest.motpasse.Caractere;

import static junit.framework.Assert.assertTrue;

/**
 * Created by lohib on 16/05/2017.
 */

public class CaractereTest {

    private Caractere[] echantillon ;


    // ////// Echantillon /////////////////////////////////////

    // chiffre, minusc, majusc, cédeille, accent, composé, symbole, symbole et '¤' hors-reference appli
    public CaractereTest() {
        echantillon = new Caractere[9] ;
        echantillon[0] = new Caractere('5') ;
        echantillon[1] = new Caractere('a') ;
        echantillon[2] = new Caractere('T') ;
        echantillon[3] = new Caractere('ç');
        echantillon[4] = new Caractere('é') ;
        echantillon[5] = new Caractere('â') ;
        echantillon[6] = new Caractere('&') ;
        echantillon[7] = new Caractere('#') ;
        echantillon[8] = new Caractere('¤') ;
}


    // ////// /////////////////////////////////////

    @Test
    public void estUnChiffre() {
        boolean test = echantillon[0].estUnChiffre() ;
        for (int i = 1 ; i < echantillon.length ; i++) {
            boolean enCours = !echantillon[i].estUnChiffre() ;
            test = test && enCours ;
        }
        assertTrue(test) ;
    }

    @Test
    public void estUneMinuscule() {
        boolean test = echantillon[1].estUneMinuscule() ;
        test = test && !echantillon[0].estUneMinuscule() ;
        for (int i = 2 ; i < echantillon.length ; i++) {
            boolean enCours = !echantillon[i].estUneMinuscule() ;
            test = test && enCours ;
        }
        assertTrue(test) ;
    }

}
