package fr.hibon.modepassesecurest.compte ;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import fr.hibon.modepassesecurest.compte.Donnee;
import fr.hibon.modepassesecurest.compte.Repertoire;
import fr.hibon.modepassesecurest.compte.exception.CompteException;

/**
 * Teste la Classe CompteUtilisateur
 *
 */
public class RepertoireTest {

	private final Repertoire repo;

	private final String nomRepertoire;
	private final String noteRepertoire;

	private final Donnee donneeUn;
	private final String mailDonneeUn;
    private final String questionSecreteDonneeUn;
    private final String categorieDonneeUn;
    private final String noteDonneeUn;
    private final String nomDonneeUn;
    private final String siteWebDonneeUn;
    private final String loginDonneeUn;
    private final String passeDonneeUn;
	private final Donnee donneeDeux;
	private final String mailDonneeDeux;
    private final String questionSecreteDonneeDeux;
    private final String categorieDonneeDeux;
    private final String noteDonneeDeux;
    private final String nomDonneeDeux;
    private final String siteWebDonneeDeux;
    private final String loginDonneeDeux;
    private final String passeDonneeDeux;

	// ////////////////// ECHANTILLONAGE //////////////////

	/**
	 * Constructeur qui affecte des valeurs de test
	 */
	public RepertoireTest() {
		nomRepertoire = "Repertoire Test";
		noteRepertoire = "Ceci est mon répertoire";
		repo = new Repertoire(nomRepertoire, noteRepertoire);

		mailDonneeUn = "mail@mail.com";
		questionSecreteDonneeUn = "Quel était ?";
		categorieDonneeUn = "Compte website";
		noteDonneeUn = "Accès au forum";
		nomDonneeUn = "portail infos";
		siteWebDonneeUn = "http://lc.f.tr";
		loginDonneeUn = "identifiant";
		passeDonneeUn = "jojoLapin";
		donneeUn = new Donnee(passeDonneeUn, nomDonneeUn, loginDonneeUn, mailDonneeUn, siteWebDonneeUn, questionSecreteDonneeUn, categorieDonneeUn, noteDonneeUn);

		mailDonneeDeux = "ref@tom.com";
		questionSecreteDonneeDeux = "";
		categorieDonneeDeux = "Admin";
		noteDonneeDeux = "";
		nomDonneeDeux = "caf";
		siteWebDonneeDeux = "http://www.caf.fr";
		loginDonneeDeux = "monId";
		passeDonneeDeux = "d4'#4(8é(#~[deeRZFV";
		donneeDeux = new Donnee(passeDonneeDeux, nomDonneeDeux, loginDonneeDeux, mailDonneeDeux, siteWebDonneeDeux, questionSecreteDonneeDeux, categorieDonneeDeux, noteDonneeDeux);

		repo.getLesDonnees().add(donneeUn);
		repo.getLesDonnees().add(donneeDeux);
	}

	/**
	 * Echantillonnage : vider infos
	 * 
	 */
    private void viderEchantillon() {
		repo.getLesDonnees().clear();
	}

	/**
	 * Echantillonnage : mettre 2 Donn&eacute;es
	 * 
	 */
    private void ajouterDansEchantillon() {
		repo.getLesDonnees().add(donneeUn);
		repo.getLesDonnees().add(donneeDeux);
	}

	// ////////////////// ///////////////////////////////////////////
	// ////////////////// TESTS /////////////////////////////////
	// ////////////////// ///////////////////////////////////////////

	// //////////// Operations sur le REPERTOIRE : SETTERS - GETTERS

	/**
	 * Teste l'acc&egrave;s &agrave; nomRepertoire
	 */
	@Test
	public void getNomRepertoire_doitAcceder() {
		assertEquals(repo.getNomRepertoire(), this.nomRepertoire);
	}

	/**
	 * Teste l'acc&egrave;s &agrave; noteRepertoire
	 */
	@Test
	public void getNoteRepertoire_doitAcceder() {
		assertEquals(repo.getNoteRepertoire(), this.noteRepertoire);
	}

	/**
	 * Teste l'affectation d'une valeur &agrave; nomRepertoire
	 */
	@Test
	public void setNomRepertoire_doitModifier() {
		String nouveau = "nouveauNom";
		repo.setNomRepertoire(nouveau);
		assertEquals(nouveau, repo.getNomRepertoire());
	}

	/**
	 * Teste l'affectation d'une valeur &agrave; noteRepertoire
	 */
	@Test
	public void setNoteRepertoire_doitModifier() {
		String nouveau = "nouvelle note";
		repo.setNoteRepertoire(nouveau);
		assertEquals(nouveau, repo.getNoteRepertoire());
	}
	

	/**
	 * Teste l'affectation d'une ArrayList
	 */
	@Test
	public void setLesDonnees_doitModifier() {
		Repertoire r = new Repertoire() ;
		ArrayList<Donnee> listeDonnees = new ArrayList<Donnee>() ;
		listeDonnees.add(donneeDeux) ; 
		listeDonnees.add(new Donnee()) ; 
		listeDonnees.add(new Donnee()) ; 
		r.setLesDonnees(listeDonnees);
	}
	

	// //////////// Gestion DONNEES ////////////////
	// //////////// //////////////// ////////////////

	// //////////// Ajout DONNEE ////////////////

	/**
	 * Ajout. 2 ajouts : La taille doit augmenter de 2
	 * 
	 */
	@Test
	public void ajoutDonnee_doitAugmenterTaille() {
		Donnee aAjouter1 = new Donnee();
		Donnee aAjouter2 = new Donnee();
		int tailleInit = repo.getLesDonnees().size();
		repo.ajoutDonnee(aAjouter1);
		repo.ajoutDonnee(aAjouter2);
		assertEquals(repo.getLesDonnees().size(), tailleInit + 2);
	}

	// //////////// Suppression DONNEE ////////////////

	/**
	 * Suppression. R&eacute;ussite - Test sur taille de la liste
	 * 
	 * @throws CompteException
	 *             si la donn&eacute; n'est pas trouv&acute;e
	 * 
	 */
	@Test
	public void supprDonnee_doitDiminuerTailleListe() throws CompteException {
		int tailleInit = repo.getLesDonnees().size();
		repo.supprDonnee(donneeUn);
		repo.supprDonnee(donneeDeux);
		assertEquals(repo.getLesDonnees().size(), tailleInit - 2);
	}

	/**
	 * Essai de Suppression mais n'existe pas. CompteException attendue
	 * 
	 * @throws CompteException
	 *             si la donn&eacute; n'est pas trouv&eacute;e
	 * 
	 */
	@Test(expected = CompteException.class)
	public void supprDonnee_exceptionDonneeExistePas() throws CompteException {
		Donnee donneeTrois = new Donnee();
		repo.supprDonnee(donneeTrois);
	}

	// //////////// Recherche Donnee par son NOM ////////////////

	/**
	 * Recherche donnant 1 r&eacute;sultat unique<BR>
	 * v&eacute;rifie que liste de r&eacute;sultats est de taille 1
	 */
	@Test
	public void cherchDonneeNom_1Donnee_taille() {
		this.viderEchantillon();
		this.ajouterDansEchantillon();
		ArrayList<Donnee> trouveeParNom = (ArrayList<Donnee>) repo.cherchDonneeNom(nomDonneeUn);
		assertEquals(trouveeParNom.size(), 1);
	}

	/**
	 * Recherche donnant 1 r&eacute;sultat unique<BR>
	 * v&eacute;rifie que l'info est disponible
	 */
	@Test
	public void cherchDonneeNom_1Donnee_info() {
		this.viderEchantillon();
		this.ajouterDansEchantillon();
		ArrayList<Donnee> trouveeParNom = (ArrayList<Donnee>) repo.cherchDonneeNom(nomDonneeUn);
		assertEquals(trouveeParNom.get(0).getMailDonnee(), mailDonneeUn);
	}


	/**
	 * Recherche donnant 2 r&eacute;sultats<BR>
	 * v&eacute;rifie que liste de r&eacute;sultats est de taille 2
	 */
	@Test
	public void cherchDonneeNom_2Donnees_taille() {
		this.viderEchantillon();
		this.ajouterDansEchantillon();
		repo.ajoutDonnee(new Donnee("","portail infos","","","","","",""));
		ArrayList<Donnee> trouveeParNom = (ArrayList<Donnee>) repo.cherchDonneeNom(nomDonneeUn);
		assertEquals(trouveeParNom.size(), 2);
	}
	
	
	/**
	 * Recherche sans r&eacute;sultat<BR>
	 * v&eacute;rifie que liste de r&eacute;sultats est de taille 0
	 */
	@Test
	public void cherchDonneeNom_0Donnee_taille() {
		this.viderEchantillon();
		this.ajouterDansEchantillon();
		ArrayList<Donnee> trouveeParNom = (ArrayList<Donnee>) repo.cherchDonneeNom("absente");
		assertEquals(trouveeParNom.size(), 0);
	}


	/**
	 * Recherche sans r&eacute;sultat : null en param&egrave;<BR>
	 * v&eacute;rifie que liste de r&eacute;sultats est de taille 0
	 */
	@Test
	public void cherchDonneeNom_null_taille() {
		this.viderEchantillon();
		this.ajouterDansEchantillon();
		ArrayList<Donnee> trouveeParNom = (ArrayList<Donnee>) repo.cherchDonneeNom(null);
		assertEquals(trouveeParNom.size(), 0);
	}


	// //////////// Recherche Donnee par son MAIL ////////////////

	/**
	 * Recherche donnant 1 r&eacute;sultat unique<BR>
	 * v&eacute;rifie que liste de r&eacute;sultats est de taille 1
	 */
	@Test
	public void cherchDonneeMail_1Donnee_taille() {
		this.viderEchantillon();
		this.ajouterDansEchantillon();
		ArrayList<Donnee> trouveeParMail = (ArrayList<Donnee>) repo.cherchDonneeMail(mailDonneeUn);
		assertEquals(trouveeParMail.size(), 1);
	}

		/**
	 * Recherche donnant 2 r&eacute;sultats<BR>
	 * v&eacute;rifie que liste de r&eacute;sultats est de taille 2
	 */
	@Test
	public void cherchDonneeMail_2Donnees_taille() {
		this.viderEchantillon();
		this.ajouterDansEchantillon();
		repo.ajoutDonnee(new Donnee("","","","mail@mail.com","","","",""));
		ArrayList<Donnee> trouveeParMail = (ArrayList<Donnee>) repo.cherchDonneeMail(mailDonneeUn);
		assertEquals(trouveeParMail.size(), 2);
	}
	
	/**
	 * Recherche sans r&eacute;sultat<BR>
	 * v&eacute;rifie que liste de r&eacute;sultats est de taille 0
	 */
	@Test
	public void cherchDonneeMail_0Donnee_taille() {
		this.viderEchantillon();
		this.ajouterDansEchantillon();
		ArrayList<Donnee> trouveeParMail = (ArrayList<Donnee>) repo.cherchDonneeMail("absente");
		assertEquals(trouveeParMail.size(), 0);
	}


	// //////////// Recherche Donnee dans NOTE ////////////////

	/**
	 * Recherche donnant 1 r&eacute;sultat unique<BR>
	 * v&eacute;rifie que liste de r&eacute;sultats est de taille 1
	 */
	@Test
	public void cherchDonneeNote_1Donnee_taille() {
		this.viderEchantillon();
		this.ajouterDansEchantillon();
		ArrayList<Donnee> trouveeDansNote = (ArrayList<Donnee>) repo.cherchDonneeNote(noteDonneeUn);
		assertEquals(trouveeDansNote.size(), 1);
	}

		/**
	 * Recherche donnant 2 r&eacute;sultats<BR>
	 * v&eacute;rifie que liste de r&eacute;sultats est de taille 2
	 */
	@Test
	public void cherchDonneeNote_2Donnees_taille() {
		this.viderEchantillon();
		this.ajouterDansEchantillon();
		repo.ajoutDonnee(new Donnee("","","","","","","","Accès au forum"));
		ArrayList<Donnee> trouveeDansNote = (ArrayList<Donnee>) repo.cherchDonneeNote(noteDonneeUn);
		assertEquals(trouveeDansNote.size(), 2);
	}
	
	/**
	 * Recherche sans r&eacute;sultat<BR>
	 * v&eacute;rifie que liste de r&eacute;sultats est de taille 0
	 */
	@Test
	public void cherchDonneeNote_0Donnee_taille() {
		this.viderEchantillon();
		this.ajouterDansEchantillon();
		ArrayList<Donnee> trouveeDansNote = (ArrayList<Donnee>) repo.cherchDonneeNote("absente");
		assertEquals(trouveeDansNote.size(), 0);
	}


	// //////////// Recherche Donnee dans Question Secrete ////////////////

	/**
	 * Recherche donnant 1 r&eacute;sultat unique<BR>
	 * v&eacute;rifie que liste de r&eacute;sultats est de taille 1
	 */
	@Test
	public void cherchDonneeQuestSecret_1Donnee_taille() {
		this.viderEchantillon();
		this.ajouterDansEchantillon();
		ArrayList<Donnee> trouveeDansQuestSecret = (ArrayList<Donnee>) repo.cherchDonneeQuestSecret(questionSecreteDonneeUn);
		assertEquals(trouveeDansQuestSecret.size(), 1);
	}

		/**
	 * Recherche donnant 2 r&eacute;sultats<BR>
	 * v&eacute;rifie que liste de r&eacute;sultats est de taille 2
	 */
	@Test
	public void cherchDonneeQuestSecret_2Donnees_taille() {
		this.viderEchantillon();
		this.ajouterDansEchantillon();
		repo.ajoutDonnee(new Donnee("","","","","","Quel était ?","",""));
		ArrayList<Donnee> trouveeDansQuestSecret = (ArrayList<Donnee>) repo.cherchDonneeQuestSecret(questionSecreteDonneeUn);
		assertEquals(trouveeDansQuestSecret.size(), 2);
	}
	
	/**
	 * Recherche sans r&eacute;sultat<BR>
	 * v&eacute;rifie que liste de r&eacute;sultats est de taille 0
	 */
	@Test
	public void cherchDonneeQuestSecret_0Donnee_taille() {
		this.viderEchantillon();
		this.ajouterDansEchantillon();
		ArrayList<Donnee> trouveeDansQuestSecret = (ArrayList<Donnee>) repo.cherchDonneeQuestSecret("absente");
		assertEquals(trouveeDansQuestSecret.size(), 0);
	}


	// //////////// Recherche Donnee dans Website ////////////////

	/**
	 * Recherche donnant 1 r&eacute;sultat unique<BR>
	 * v&eacute;rifie que liste de r&eacute;sultats est de taille 1
	 */
	@Test
	public void cherchDonneeWebsite_1Donnee_taille() {
		this.viderEchantillon();
		this.ajouterDansEchantillon();
		ArrayList<Donnee> trouveeDansWebsite = (ArrayList<Donnee>) repo.cherchDonneeWebsite(siteWebDonneeUn);
		assertEquals(trouveeDansWebsite.size(), 1);
	}

		/**
	 * Recherche donnant 2 r&eacute;sultats<BR>
	 * v&eacute;rifie que liste de r&eacute;sultats est de taille 2
	 */
	@Test
	public void cherchDonneeWebsite_2Donnees_taille() {
		this.viderEchantillon();
		this.ajouterDansEchantillon();
		repo.ajoutDonnee(new Donnee("","","","","http://lc.f.tr","","",""));
		ArrayList<Donnee> trouveeDansWebsite = (ArrayList<Donnee>) repo.cherchDonneeWebsite(siteWebDonneeUn);
		assertEquals(trouveeDansWebsite.size(), 2);
	}
	
	/**
	 * Recherche sans r&eacute;sultat<BR>
	 * v&eacute;rifie que liste de r&eacute;sultats est de taille 0
	 */
	@Test
	public void cherchDonneeWebsite_0Donnee_taille() {
		this.viderEchantillon();
		this.ajouterDansEchantillon();
		ArrayList<Donnee> trouveeDansWebsite = (ArrayList<Donnee>) repo.cherchDonneeWebsite("absente");
		assertEquals(trouveeDansWebsite.size(), 0);
	}


	// //////////// Recherche dans les Attributs du R&eacute;pertoire ////////////////

	/**
	 * Recherche d'un mot dans nom ou note du R&eacute;pertoire, sans r&eacute;sultat<BR>
	 */
	@Test
	public void presenceMotDansObjet_non() {
		assertFalse(repo.presenceMotDansObjet("voiture"));
	}
	
	/**
	 * Recherche d'un mot dans nom ou note du R&eacute;pertoire, 1  r&eacute;sultat<BR>
	 */
	@Test
	public void presenceMotDansObjet_ouiNom() {
		assertTrue(repo.presenceMotDansObjet("Repertoire"));
	}

	/**
	 * Recherche d'un mot dans nom ou note du R&eacute;pertoire, 1  r&eacute;sultat<BR>
	 */
	@Test
	public void presenceMotDansObjet_ouiNote() {
		assertTrue(repo.presenceMotDansObjet("répertoire"));
	}


	/**
	 * Recherche d'un mot dans nom ou note du R&eacute;pertoire, param&egrave;tre null<BR>
	 */
	@Test
	public void presenceMotDansObjet_null() {
		assertFalse(repo.presenceMotDansObjet(null));
	}


	// //////////// utilitaire recherche ////////////////
	
	/**
	 * Recherche d'un mot dans une cha&icirc;ne - R&eacute;ussite<BR>
	 */
	@Test
	public void presenceMotDansChamp_OK() {
		assertTrue(Repertoire.presenceMotDansChamp(repo.getNomRepertoire(), "Test"));
	}
	

	/**
	 * Recherche d'un mot dans une cha&icirc;ne - Echec<BR>
	 */
	@Test
	public void presenceMotDansChamp_html() {
		assertFalse(Repertoire.presenceMotDansChamp("Ceci est une cha&icirc;ne", "chaîne"));
	}
	
	
	/**
	 * Recherche d'un mot dans une cha&icirc;ne - Echec<BR>
	 */
	@Test
	public void presenceMotDansChamp_absent() {
		assertFalse(Repertoire.presenceMotDansChamp(repo.getNomRepertoire(), "tst" ));
	}	


	
	// //////////// COMPTE equals ///////////////////////////////////

	/**
	 * Teste EQUALS <BR>
	 * Identiques (unicit&eacute; du nom)
	 */
	@Test
	public void equals_oui() {
		Repertoire r1 = new Repertoire("MonRepertoire", "");
		Repertoire r2 = new Repertoire("MonRepertoire", "");
		assertEquals(r1, r2);
	}

	/**
	 * Teste EQUALS <BR>
	 * M&ecirc;me objet
	 */
	@Test
	public void equals_memeObjet() {
		Repertoire r1 = new Repertoire("MonRepertoire", "");
		Repertoire r2 = r1;
		assertEquals(r1, r2);
	}

	/**
	 * Teste EQUALS <BR>
	 * Diff&eacute;rents
	 */
	@Test
	public void equals_non() {
		Repertoire r1 = new Repertoire("MonRepertoire", "");
		Repertoire r2 = new Repertoire("MonRepertoir", "");
		assertNotEquals(r1, r2);
	}

	/**
	 * Teste EQUALS <BR>
	 * Param&egrave;tre null
	 */
	@Test
	public void equals_paramNull() {
		Repertoire r1 = new Repertoire("MonRepertoire", "");
		Repertoire r2 = null;
		assertNotEquals(r1, r2);
	}

	/**
	 * Teste EQUALS <BR>
	 * autre classe en param&egrave;tre
	 */
	@Test
	public void equals_autreClasse() {
		Repertoire r1 = new Repertoire("MonRepertoire", "");
		String r2 = "MonRepertoire";
		assertNotEquals(r1, r2);
	}

	
	/**
	 * Teste EQUALS <BR>
	 * objet courant null
	 */
	@Test(expected = NullPointerException.class)
	public void equals_comparaisonObjetNull() {
		Repertoire leNul = null ;
		leNul.equals(repo) ; 
	}
	
}
