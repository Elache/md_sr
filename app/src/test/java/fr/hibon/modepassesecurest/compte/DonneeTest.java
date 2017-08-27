package fr.hibon.modepassesecurest.compte;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fr.hibon.modepassesecurest.compte.Donnee;
import fr.hibon.modepassesecurest.compte.Repertoire;

public class DonneeTest {

	Donnee donneeUn;
	String mailDonneeUn, questionSecreteDonneeUn, categorieDonneeUn, noteDonneeUn, nomDonneeUn, siteWebDonneeUn,
			loginDonneeUn, passeDonneeUn;

	Donnee donneeDeux;
	String mailDonneeDeux, questionSecreteDonneeDeux, categorieDonneeDeux, noteDonneeDeux, nomDonneeDeux,
			siteWebDonneeDeux, loginDonneeDeux, passeDonneeDeux;
	
	// ////////////////// ECHANTILLONAGE //////////////////

	/**
	 * Constructeur qui affecte des valeurs de test :
	 * <BR>CREATION d'1 donn&eacute;e (saisie)
	 */
	public DonneeTest() {
		passeDonneeUn = "jojoLapin";
		nomDonneeUn = "portail infos";
		loginDonneeUn = "identifiant";
		mailDonneeUn = "mail@mail.com";
		siteWebDonneeUn = "http://lc.f.tr";
		questionSecreteDonneeUn = "Quel était ?";
		categorieDonneeUn = "Compte website";
		noteDonneeUn = "Accès au forum";
		donneeUn = new Donnee(passeDonneeUn, nomDonneeUn, loginDonneeUn, mailDonneeUn, siteWebDonneeUn, questionSecreteDonneeUn, categorieDonneeUn, noteDonneeUn);

		passeDonneeDeux = "d4'#4(8é(#~[deeRZFV";
		nomDonneeDeux = "caf";
		loginDonneeDeux = "monId";
		mailDonneeDeux = "ref@tom.com";
		siteWebDonneeDeux = "http://www.caf.fr";
		questionSecreteDonneeDeux = "";
		categorieDonneeDeux = "Admin";
		noteDonneeDeux = "";
		donneeDeux = new Donnee(passeDonneeUn, nomDonneeUn, loginDonneeUn, mailDonneeUn, siteWebDonneeUn, questionSecreteDonneeUn, categorieDonneeUn, noteDonneeUn);
	}

	

	// ////////////////// ///////////////////////////////////////////
	// ////////////////// TESTS /////////////////////////////////
	// ////////////////// ///////////////////////////////////////////



	// //////////// Recherche dans champs texte de la Donnéee : ////////////////
	// //////////// nomDonnee, questionSecreteDonnee, noteDonnee ////////////////

	/**
	 * Recherche r&eacute;ussie : dans nom
	 */
	@Test
	public void presenceMotDansObjet_OK_Nom() {
		assertTrue(donneeUn.presenceMotDansObjet("infos"));
	}

		/**
	 * Recherche r&eacute;ussie : dans note
	 */
	@Test
	public void presenceMotDansObjet_OK_Note() {
		assertTrue(donneeUn.presenceMotDansObjet("forum"));
	}
	
	/**
	 * Recherche r&eacute;ussie : dans question secrete
	 */
	@Test
	public void presenceMotDansObjet_OK_QuestSecret() {
		assertTrue(donneeUn.presenceMotDansObjet("était"));
	}
	
	/**
	 * Recherche &eacute;chec
	 */
	@Test
	public void presenceMotDansObjet_absent() {
		assertFalse(donneeUn.presenceMotDansObjet("voiture"));
	}
	
	
	// //////////// utilitaire recherche ////////////////
	
	/**
	 * Recherche d'un mot dans une cha&icirc;ne - R&eacute;ussite<BR>
	 */
	@Test
	public void presenceMotDansChamp_OK() {
		assertTrue(Donnee.presenceMotDansChamp(donneeUn.getNomDonnee(), "info"));
	}
	
	/**
	 * Recherche d'un mot dans une cha&icirc;ne - Echec<BR>
	 */
	@Test
	public void presenceMotDansChamp_absent() {
		assertFalse(Donnee.presenceMotDansChamp(donneeUn.getNomDonnee(), "tst" ));
	}	


	// //////////// Setter et getter ////////////////

	
	/**
	 * Test des Getters (global)
	 * <BR>compare concat&eacute;nation des valeurs affect&eacute;es et concat&eacute;nation des valeurs des attributs de l'objet (via getters)
	 */
	@Test
	public void les10getters() {
		// constructeur à partir d'infos en BDD : recuperation d'un passe crypté et d'une clé
		Donnee d = new Donnee("passeCrypre", nomDonneeUn, loginDonneeUn, mailDonneeUn, siteWebDonneeUn, questionSecreteDonneeUn, categorieDonneeUn, noteDonneeUn, "cleChiffrement", 1) ;
		// pour complèter le dernier champ
		d.setPasseDonnee("passeDecrypte");
		String origine = "passeCrypre" + nomDonneeUn + loginDonneeUn + mailDonneeUn + siteWebDonneeUn + questionSecreteDonneeUn + categorieDonneeUn + noteDonneeUn + "cleChiffrement" + "passeDecrypte" ;
		String gettage = d.getPasseDonneeChiffre() + d.getNomDonnee() + d.getLoginDonnee()  + d.getMailDonnee() + d.getSiteWebDonnee()  + d.getQuestionSecreteDonnee() + d.getCategorieDonnee() + d.getNoteDonnee() + d.getCleChiffrementDonnee() + d.getPasseDonnee();
		assertTrue(origine.equals(gettage));
	}	

	/**
	 * Test des Setters (global)
	 * <BR>compare concat&eacute;nation des valeurs affect&eacute;es et concat&eacute;nation des valeurs des attributs de l'objet apr&egrave;s affectation via setters
	 */
	@Test
	public void les8Setters() {
		String origine = passeDonneeDeux + nomDonneeDeux +loginDonneeDeux+ mailDonneeDeux + siteWebDonneeDeux + questionSecreteDonneeDeux + categorieDonneeDeux + noteDonneeDeux  ;
		Donnee donneeTrois = new Donnee() ;
		donneeTrois.setMailDonnee(mailDonneeDeux) ;
		donneeTrois.setQuestionSecreteDonnee(questionSecreteDonneeDeux) ;
		donneeTrois.setCategorieDonnee(categorieDonneeDeux) ;
		donneeTrois.setNoteDonnee(noteDonneeDeux) ;
		donneeTrois.setNomDonnee(nomDonneeDeux) ;
		donneeTrois.setSiteWebDonnee(siteWebDonneeDeux)  ;
		donneeTrois.setLoginDonnee(loginDonneeDeux) ;
		donneeTrois.setPasseDonnee(passeDonneeDeux);
		String result = donneeTrois.getPasseDonnee()+ donneeTrois.getNomDonnee() + donneeTrois.getLoginDonnee() +donneeTrois.getMailDonnee() + donneeTrois.getSiteWebDonnee()  + donneeTrois.getQuestionSecreteDonnee() + donneeTrois.getCategorieDonnee() + donneeTrois.getNoteDonnee() ;
		assertTrue(origine.equals(result));
	}	

	
	// //////////// Equals : 2 Donnees égales ////////////////

	
	/**
	 * Test Egalit&eacute; de deux Donn&eacute;es
	 * <BR>Condition &eacute;galit&eacute; v&eacute;rifi&eacute;e : nom + login identiques
	 */
	@Test
	public void equals_identiques() {
		Donnee d = new Donnee() ;
		d.setNomDonnee(nomDonneeUn);
		d.setLoginDonnee(loginDonneeUn);
		d.setPasseDonnee(passeDonneeUn);
		assertTrue(donneeUn.equals(d));
	}
	
	
	/**
	 * Test Egalit&eacute; de deux Donn&eacute;es
	 * <BR>Condition &eacute;galit&eacute; non v&eacute;rifi&eacute;e : nom !=
	 */
	@Test
	public void equals_differentNom() {
		Donnee d = new Donnee() ;
		d.setNomDonnee("Donnee1");
		d.setLoginDonnee(loginDonneeUn);
		d.setPasseDonnee(passeDonneeUn);
		assertFalse(donneeUn.equals(d)) ; 
	}
	

	/**
	 * Test Egalit&eacute; de deux Donn&eacute;es
	 * <BR>Condition &eacute;galit&eacute; non v&eacute;rifi&eacute;e : identifiant !=
	 */
	@Test
	public void equals_differentIdentifiant() {
		Donnee d = new Donnee() ;
		d.setNomDonnee(nomDonneeUn);
		d.setLoginDonnee("IdentifiantDeMoi");
		d.setPasseDonnee(passeDonneeUn);
		assertFalse(donneeUn.equals(d)) ; 
	}
	
	/**
	 * Test Egalit&eacute; de deux Donn&eacute;es
	 * <BR>Condition &eacute;galit&eacute; v&eacute;rifi&eacute;e : seul passe !=
	 */
	@Test
	public void equals_differentPasse() {
		Donnee d = new Donnee() ;
		d.setNomDonnee(nomDonneeUn);
		d.setLoginDonnee(loginDonneeUn);
		d.setPasseDonnee("monPasseIntrouvable");
		assertTrue(donneeUn.equals(d)) ;
	}
	
	/**
	 * Test Egalit&eacute; de deux Donn&eacute;es
	 * <BR>Condition &eacute;galit&eacute; v&eacute;rifi&eacute;e : m&ecirc;me objet
	 */
	@Test
	public void equals_memeObjet() {
		Donnee d = donneeDeux ; 
		assertTrue(donneeDeux.equals(d)) ; 
	}
	
	/**
	 * Test Egalit&eacute; de deux Donn&eacute;es
	 * <BR>Condition &eacute;galit&eacute; non v&eacute;rifi&eacute;e : classe diff&eacute;nte
	 */
	@Test
	public void equals_classeDifferente() {
		Repertoire d = new Repertoire() ; 
		assertFalse(donneeDeux.equals(d)) ; 
	}
	
	/**
	 * Test Egalit&eacute; de deux Donn&eacute;es
	 * <BR>Condition &eacute;galit&eacute; non v&eacute;rifi&eacute;e : param&egrave;tre null
	 */
	@Test
	public void equals_comparaisonAvecNull() {
		assertFalse(donneeDeux.equals(null)) ; 
	}
	

	/**
	 * Test Egalit&eacute; de deux Donn&eacute;es
	 * <BR>Condition &eacute;galit&eacute; non v&eacute;rifi&eacute;e : objet null
	 */
	@Test(expected = NullPointerException.class)
	public void equals_comparaisonObjetNull() {
		Donnee laNulle = null ;
		laNulle.equals(donneeDeux) ; 
	}
	

	/**
	 * Test Egalit&eacute; de deux Donn&eacute;es
	 * <BR>Condition &eacute;galit&eacute; non v&eacute;rifi&eacute;e : nom null
	 */
	@Test
	public void equals_nomNull() {
		Donnee d = new Donnee() ;
		d.setLoginDonnee(loginDonneeUn);
		d.setPasseDonnee(passeDonneeUn);
		assertFalse(d.equals(donneeUn)) ; 
	}


	/**
	 * Test Egalit&eacute; de deux Donn&eacute;es
	 * <BR>Condition &eacute;galit&eacute; non v&eacute;rifi&eacute;e : login null
	 */
	@Test
	public void equals_loginNull() {
		Donnee d = new Donnee() ;
		d.setNomDonnee(nomDonneeUn);
		d.setPasseDonnee(passeDonneeUn);
		assertFalse(d.equals(donneeUn)) ; 
	}
	

	/**
	 * Test Egalit&eacute; de deux Donn&eacute;es
	 * <BR>Condition &eacute;galit&eacute; v&eacute;rifi&eacute;e avec  1 passe null
	 */
	@Test
	public void equals_passeNull() {
		Donnee d = new Donnee() ;
		d.setNomDonnee(nomDonneeUn);
		d.setLoginDonnee(loginDonneeUn);
		assertTrue(d.equals(donneeUn)) ;
	}

}
