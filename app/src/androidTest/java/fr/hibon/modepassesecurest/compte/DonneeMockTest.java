package fr.hibon.modepassesecurest.compte;

import fr.hibon.modepassesecurest.compte.utiles.ChiffeMode;

/**
 * Teste la classe Donnee pour les methodes de chiffrement / dechiffrement <BR>qui dependent de
 * <B>android.util.Base64 (mock)</B>
 */
public class DonneeMockTest {

    /**
     * Affichage du mot de passe en clair
     * <BR>(appelle la fonction de d&eacute;chiffrement) ChiffeMode.dechiffrer(passeClair)
     *
     * @return mot de passe en clair (String)
     */
    public String afficherMotPasse() {
        ChiffeMode cm;
        cm = new ChiffeMode();
        return cm.dechiffrer(this.passeDonneeChiffre, this.cleChiffrementDonnee);
    }


    // ////////// Chiffrement du mot de passe //////////////////

    /**
     * Chiffrement du mot de passe
     * <BR>(appelle la fonction de chiffrement) ChiffeMode.chiffrer(passeClair)
     */
    public void chiffrerMotPasse() {
        ChiffeMode cm = new ChiffeMode() ;
        cm.chiffrer(this.passeDonnee);
        this.passeDonneeChiffre = cm.getPasseCode();
        this.cleChiffrementDonnee = cm.getCleCode();
    }

    /**
     *
     */
	@Test
	public void afficherMotPasse_nominal() {
		String pass = "rz'ssNFéè&" ;
		ChiffeMode ch = new ChiffeMode();
		ch.chiffrer(pass) ;
		assertEquals(pass, ch.affic)
	}


}
