package fr.hibon.modepassesecurest.compte;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import fr.hibon.modepassesecurest.motpasse.ChainePasse;
import fr.hibon.modepassesecurest.compte.CompteUtilisateur;
import fr.hibon.modepassesecurest.compte.Repertoire;
import fr.hibon.modepassesecurest.compte.exception.CompteException;

/**
 * Teste la Classe CompteUtilisateur
 *
 */
public class CompteUtilisateurTest {

	String nomUser;
	String passeUser;
	String passeRecoursUser;
	String mailContactUser;
	String notePersoUser;

	Repertoire rep1;

	// ////////////////// ECHANTILLONAGE //////////////////

	/**
	 * Constructeur qui affecte des valeurs de test
	 */
	public CompteUtilisateurTest() {
		nomUser = "loic.hibon";
		passeUser = "Fra:3-Bré:0!";
		passeRecoursUser = "plop";
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
			cu.renseigneLeCompte(nomUser, passeUser, passeRecoursUser, mailContactUser, notePersoUser);
		} catch (CompteException e) {
		}
		return cu;
	}

	/**
	 * Echantillonnage : CompteUtilisateur vide de r&eacute;pertoire
	 * 
	 * @return CompteUtilisateur vide
	 */
	public CompteUtilisateur preparerCompteTestVide() {
		CompteUtilisateur cu = CompteUtilisateur.getCompteConnecte();
		cu.viderListeRepert();
		return cu;
	}

	// ////////////////// ///////////////////////////////////////////
	// ////////////////// TESTS /////////////////////////////////
	// ////////////////// ///////////////////////////////////////////

	// ////////////////// Affectations renseigneLeCompte() //////////

	/**
	 * Teste l'affectation des valeurs &agrave; l'instance de CompteUtilisateur
	 * 
	 */
	@Test
	public void renseigneLeCompte_doitRemplirInfos() {
		CompteUtilisateur cu = preparerCompteTest();
		assertEquals(nomUser, cu.getNomUser());
	}

	/**
	 * Teste l'affectation des valeurs &agrave; l'instance de CompteUtilisateur : <BR>
	 * mot de passe (1) vide = exception attendue
	 * 
	 * @throws CompteException
	 *             quand nomUser ou mot de passe (1 et/ou 2) sont vides
	 */
	@Test(expected = CompteException.class)
	public void renseigneLeCompte_passeAbsent() throws CompteException {
		CompteUtilisateur cu = preparerCompteTest();
		cu.renseigneLeCompte(nomUser, "", passeRecoursUser, mailContactUser, notePersoUser);
	}

	/**
	 * Teste l'affectation des valeurs &agrave; l'instance de CompteUtilisateur : <BR>
	 * mot de passe (2) vide = exception attendue
	 * 
	 * @throws CompteException
	 *             quand nomUser ou mot de passe (1 et/ou 2) sont vides
	 */
	@Test(expected = CompteException.class)
	public void renseigneLeCompte_passe2Absent() throws CompteException {
		CompteUtilisateur cu = preparerCompteTest();
		cu.renseigneLeCompte(nomUser, passeUser, "", mailContactUser, notePersoUser);
	}

	/**
	 * Teste l'affectation des valeurs &agrave; l'instance de CompteUtilisateur : <BR>
	 * identifiant vide = exception attendue
	 * 
	 * @throws CompteException
	 *             quand nomUser ou mot de passe (1 et/ou 2) sont vides
	 */
	@Test(expected = CompteException.class)
	public void renseigneLeCompte_identifiantAbsent() throws CompteException {
		CompteUtilisateur cu = preparerCompteTest();
		cu.renseigneLeCompte("", passeUser, passeRecoursUser, mailContactUser, notePersoUser);
	}

	// ////////////////// Deconnexion // ///////////////////////////////

	/**
	 * V&eacute;rifie que la d&eacute;connexion initialise les valeurs (null)
	 */
	@Test
	public void deconnecter_doitAnnulerValeurs() {
		CompteUtilisateur cu = preparerCompteTest();
		cu.deconnecter();
		assertNull(cu.getPasseUser());
	}

	// ////////////////// Test Mots de passe pour Accès internet ////////////

	/**
	 * Verifie si les mots de passe sont suffisamment longs pour Base de
	 * donn&eacute;es en ligne <BR>
	 * Echec - donc le test d'acceptation doit renvoyer false
	 * 
	 */
	@Test
	public void passeInternetAcceptable_doitRefuser() {
		CompteUtilisateur cu = preparerCompteTest();
		cu.setPasseUser(ChainePasse.composition("2f"));
		cu.setPasseRecoursUser(ChainePasse.composition("7KrctéR"));
		assertFalse(cu.passeInternetAcceptable());
	}

	/**
	 * Verifie si les mots de passe sont suffisamment longs pour Base de
	 * donn&eacute;es en ligne <BR>
	 * Echec - donc doit g&eacute;n&eacute;rer 1 mot de longueur 10 et
	 * l'affecter &agrave; passeCompleteInternet
	 * 
	 */
	@Test
	public void passeInternetAcceptable_doitRefuserDoncAffecter() {
		CompteUtilisateur cu = preparerCompteTest();
		cu.setPasseUser(ChainePasse.composition("2f"));
		cu.setPasseRecoursUser(ChainePasse.composition("7KrctéR"));
		cu.passeInternetAcceptable();
		assertTrue(cu.getPasseCompleteInternet().longeurMot() == 10);
	}

	/**
	 * 
	 * Verifie si les mots de passe sont suffisamment longs pour Base de
	 * donn&eacute;es en ligne <BR>
	 * R&eacute;ussite (n1 OK)
	 * 
	 */
	@Test
	public void passeInternetAcceptable_1doitAccepter() {
		CompteUtilisateur cu = preparerCompteTest();
		cu.setPasseUser(ChainePasse.composition(">7KrctéR"));
		cu.setPasseRecoursUser(ChainePasse.composition("2f"));
		assertTrue(cu.passeInternetAcceptable());
	}

	/**
	 * 
	 * Verifie si les mots de passe sont suffisamment longs pour Base de
	 * donn&eacute;es en ligne <BR>
	 * R&eacute;ussite (n2 OK)
	 */
	@Test
	public void passeInternetAcceptable_2doitAccepter() {
		CompteUtilisateur cu = preparerCompteTest();
		cu.setPasseUser(ChainePasse.composition("2f"));
		cu.setPasseRecoursUser(ChainePasse.composition(">7KrctéR"));
		assertTrue(cu.passeInternetAcceptable());
	}

	// //////////// Gestion REPERTOIRES ////////////////
	// //////////// //////////////// ////////////////

	// //////////// Ajout REPERTOIRE ////////////////

	/**
	 * Ajout. 2 ajouts : La taille doit augmenter de 2
	 */
	@Test
	public void ajoutRepert_doitAugmenterTaille() {
		Repertoire aAjouter = new Repertoire();
		CompteUtilisateur cu = preparerCompteTest();
		int tailleInit = cu.getLesRepertoires().size();
		cu.ajoutRepert(aAjouter);
		cu.ajoutRepert(rep1);
		assertEquals(cu.getLesRepertoires().size(), tailleInit + 2);
	}

	// //////////// Recherche REPERTOIRE ////////////////

	/**
	 * Recherche. R&eacute;ussite - Test si r&eacute;sultat non null
	 * 
	 * @throws CompteException
	 *             exception quand plusieurs r&eacute;pertoires de m&ecirc;me
	 *             nom
	 * 
	 */
	@Test
	public void cherchRepert_doitRetournerRep() throws CompteException {
		Repertoire recherche = null;
		CompteUtilisateur cu = preparerCompteTestVide();
		cu.ajoutRepert(rep1);
		recherche = cu.trouverRepert(rep1.getNomRepertoire());
		assertNotNull(recherche);
	}

	/**
	 * Recherche. Echec - Test si r&eacute;sultat null
	 * 
	 * @throws CompteException
	 *             exception quand plusieurs r&eacute;pertoires de m&ecirc;me
	 *             nom
	 */
	@Test
	public void cherchRepert_doitRetournerNull() throws CompteException {
		Repertoire recherche = null;
		CompteUtilisateur cu = CompteUtilisateur.getCompteConnecte();
		recherche = cu.trouverRepert("MonRepertoire");
		assertNull(recherche);
	}

	// //////////// Suppression REPERTOIRE ////////////////

	/**
	 * Suppression. Test sur taille de la liste
	 * 
	 * @throws CompteException
	 *             si le r&eacute;pertoire &agrave; supprimer n'existe pas
	 */
	@Test
	public void supprRepert_doitDiminuerTailleListe() throws CompteException {
		CompteUtilisateur cu = preparerCompteTest();
		Repertoire addRep = new Repertoire();
		cu.ajoutRepert(addRep);
		int tailleInit = cu.getLesRepertoires().size();
		cu.supprRepert(addRep);
		assertEquals(cu.getLesRepertoires().size(), tailleInit - 1);
	}

	/**
	 * Suppression par nom de r&eacute;pertoire. Test sur r&eacute;sultat d'une
	 * recherche apr&eacute;s suppression
	 * 
	 * @throws CompteException
	 * 1. si le r&eacute;pertoire &agrave; supprimer n'existe pas <BR>2. si
	 * plusieurs r&acute;pertoires trouv&eacute;s
	 */
	@Test
	public void supprRepert_parNom_donneRechercheNulle() throws CompteException {
		Repertoire recherche = null;
		CompteUtilisateur cu = preparerCompteTestVide();
		cu.ajoutRepert(rep1);
		cu.supprRepert("RepTest");
		recherche = cu.trouverRepert("RepTest");
		assertNull(recherche);
	}

	/**
	 * Essai de Suppression mais n'existe pas. CompteException attendue
	 * @throws CompteException 
	 * 1. si le r&eacute;pertoire &agrave; supprimer n'existe pas <BR>2. si
	 * plusieurs r&eacute;pertoires trouv&eacute;s
	 */
	@Test(expected = CompteException.class)
	public void supprRepert_exceptionRepertExistePas() throws CompteException {
		CompteUtilisateur cu = preparerCompteTest();
		cu.supprRepert("Repo");
	}

	/**
	 * Essai de Suppression mais plusieurs trouv&eacute;s. CompteException attendue
	 *
     * @throws CompteException
     * 1. si le r&eacute;pertoire &agrave; supprimer n'existe pas <BR>2. si
     * plusieurs r&eacute;pertoires trouv&eacute;s
     * */
	@Test(expected = CompteException.class)
	public void supprRepert_exceptionDoublonsRepert() throws CompteException {
		CompteUtilisateur cu = preparerCompteTestVide();
		cu.ajoutRepert(rep1);
		cu.ajoutRepert(rep1);
		cu.supprRepert("RepTest");
	}
	

	// //////////// Affectation REPERTOIRE ////////////////

	/**
	 * V&eacute;rifie l'affectation d'une ArrayList au compte
	 */
	@Test
	public void setLesRepertoires()   {
		CompteUtilisateur cu = preparerCompteTestVide();
		int tailleInit = cu.getLesRepertoires().size();
		ArrayList<Repertoire> toutPret = new ArrayList<Repertoire>();
		toutPret.add(new Repertoire());
		toutPret.add(new Repertoire("2", null));
		toutPret.add(new Repertoire("3", "A"));
		cu.setLesRepertoires(toutPret);
		assertEquals(cu.getLesRepertoires().size(), tailleInit + 3);
	}

	// //////////// Gestion COMPTE ////////////////
	// //////////// //////////////// ////////////////

	// //////////// Operations sur le COMPTE : SETTERS ////////////////

	/**
	 * V&eacute;rifie &eacute;galit&eacute; des noms apr&egrave;s set (nouveau =
	 * affect&eacute;)
	 * 
	 */
	@Test
	public void setNomUser_doitModifier()  {
		String nouveau = "nouveauNom";
		CompteUtilisateur cu = CompteUtilisateur.getCompteConnecte();
		cu.setNomUser(nouveau);
		assertEquals(nouveau, cu.getNomUser());
	}

	/**
	 * V&eacute;rifie in&eacute;galit&eacute; des mots de passe apr&egrave;s set
	 * (ancien != affect&eacute;)
	 * 
	 */
	@Test
	public void setPasseUser_doitModifier()   {
		CompteUtilisateur cu = preparerCompteTest();
		ChainePasse nouveauPasse = ChainePasse.composition("NOUVEAU PASSE");
		ChainePasse ancienPasse = cu.getPasseUser();
		cu.setPasseUser(nouveauPasse);
		assertNotEquals(ancienPasse, cu.getPasseUser());
	}

	/**
	 * V&eacute;rifie &eacute;galit&eacute; des mots de passe apr&egrave;s set
	 * (nouveau = affect&eacute;)
	 * 
	 */
	@Test
	public void setPasseRecoursUser_doitModifier()   {
		CompteUtilisateur cu = preparerCompteTest();
		ChainePasse nouveauPasse = ChainePasse.composition("NOUVEAU PASSE");
		cu.setPasseRecoursUser(nouveauPasse);
		assertEquals("NOUVEAU PASSE", cu.getPasseRecoursUser().getChaineDuPasse());
	}

	/**
	 * V&eacute;rifie in&eacute;galit&eacute; des mails apr&egrave;s set (ancien
	 * != affect&eacute;)
	 * 
	 */
	@Test
	public void setMailContactUser_doitModifier()   {
		String nouveau = "NOUVEAU MAIL";
		CompteUtilisateur cu = preparerCompteTest();
		String ancienMail = cu.getMailContactUser();
		cu.setMailContactUser(nouveau);
		assertNotEquals(ancienMail, cu.getMailContactUser());
	}

	/**
	 * V&eacute;rifie &eacute;galit&eacute; des notes personnelles apr&egrave;s
	 * set (nouveau = affect&eacute;)
	 * 
	 */
	@Test
	public void setNotePersoUser_doitModifier()   {
		String nouveau = "NOUVEAU TEXT";
		CompteUtilisateur cu = preparerCompteTest();
		cu.setNotePersoUser(nouveau);
		assertEquals(nouveau, cu.getNotePersoUser());
	}

}
