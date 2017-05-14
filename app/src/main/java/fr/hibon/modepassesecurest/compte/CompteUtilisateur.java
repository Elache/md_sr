package fr.hibon.modepassesecurest.compte;

import java.util.ArrayList;
import fr.hibon.modepassesecurest.motpasse.ChainePasse;
import fr.hibon.modepassesecurest.motpasse.MotDePasseCotation;
import fr.hibon.modepassesecurest.compte.exception.CompteException;
import fr.hibon.modepassesecurest.compte.exception.CompteException.ErreurDetail;

/**
 * CompteUtilisateur : <BR>
 * Patron de conception Singleton <BR>
 * - regroupe les attributs d'un Utilisateur (instanci&eacute; &agrave; la
 * cr&eacute;ation du Compte ou apr&egrave;s connexion et
 * r&eacute;cup&eacute;ration dans la base de donn&eacute;es <BR>
 * - permet la gestion de la connexion de l'Utilisateur
 */
public class CompteUtilisateur {

	private static CompteUtilisateur instanceSingleton;
	private ArrayList<Repertoire> lesRepertoires;
	private String nomUser;
	private ChainePasse passeUser;
	private ChainePasse passeRecoursUser;
	private String mailContactUser;
	private String notePersoUser;

	private ChainePasse passeCompleteInternet;

	/**
	 * Constructeur priv� : PDC Singleton
	 */
	private CompteUtilisateur() {
	}

	/**
	 * Acc&egrave;s � l'instance unique de CompteUtilisateur OU cr&eacute;ation
	 * avec informations compl&egrave;tes
	 * @return instanceSingleton instance unique de CompteUtilisateur
	 */
	public static CompteUtilisateur getCompteConnecte() {
		if (instanceSingleton == null) {
			instanceSingleton = new CompteUtilisateur();
			instanceSingleton.lesRepertoires = new ArrayList<Repertoire>();
		}
		return instanceSingleton;
	}

	/**
	 * Renseigne les informations du compte &agrave; partir des
	 * param&egrave;tres
	 * 
	 * @param nomU
	 *            nom d'Utilisateur
	 * @param passeU
	 *            mot de passe Utilisateur (authentification-syst&egrave;me)
	 * @param passeRecoursU
	 *            mot de passe de recours Utilisateur
	 * @param mailContactU
	 *            mail de l'Utilisateur
	 * @param notePersoU
	 *            note personnelle sur le CompteUtilisateur
	 * @throws CompteException
	 *             l'identifiant et les mots passe ne doivent pas &ecirc;tre
	 *             vides
	 */
	public void renseigneLeCompte(String nomU, String passeU, String passeRecoursU, String mailContactU,
			String notePersoU) throws CompteException {
		
		// TODO gerer exception
		if (nomU.length() == 0 || nomU == null )
			throw new CompteException(ErreurDetail.IdentifiantVide);
		
		// TODO gerer exception
		if (passeU.length() == 0 || passeRecoursU.length() == 0 || passeU == null || passeRecoursU == null)
			throw new CompteException(ErreurDetail.MotDePasseVide);
		
		instanceSingleton.nomUser = nomU;
		instanceSingleton.passeUser = ChainePasse.composition(passeU);
		instanceSingleton.passeRecoursUser = ChainePasse.composition(passeRecoursU);
		instanceSingleton.mailContactUser = mailContactU;
		instanceSingleton.notePersoUser = notePersoU;
	}

	// ////////// Gestion de la CONNEXION //////////////////

	/**
	 * D&eacute;connexion : instance nulle
	 */
	public void deconnecter() {
		instanceSingleton = null;
		lesRepertoires = null ;
		nomUser = "" ;
		passeUser = null  ;
		passeRecoursUser = null;
		mailContactUser = "" ;
		notePersoUser = "" ;
		passeCompleteInternet = null;
	}

	// ////////// Acc�s INTERNET //////////////////

	/**
	 * V&eacute;rifie si au moins un mot de passe a une s&eacute;curit&eacute;
	 * suffisante pour connexion � la base de donn&eacute;es en ligne <BR>
	 * Si aucun mot de passe adapt&eacute;, mot de passe compl&eacute;t&eacute;
	 * jusque longueur 10 et stock&eacute; dans attribut passeCompleteInternet
	 * 
	 * @return true si 1 des mots de passe est de niveau acceptable ou bon
	 */
	public boolean passeInternetAcceptable() {
		MotDePasseCotation mp = MotDePasseCotation.analyser(passeUser);
		if (mp.getValeurAnalyse() >= 3)
			return true;
		mp = MotDePasseCotation.analyser(passeRecoursUser);
		if (mp.getValeurAnalyse() >= 3)
			return true;
		this.passeCompleteInternet = ChainePasse.passeComplete10(this.passeUser);
		return false;
	}


	// ////////// Gestion des REPERTOIRES //////////////////

	public ArrayList<Repertoire> getLesRepertoires() {
		return lesRepertoires;
	}

	/**
	 * Ajouter un r&eacute;pertoire
	 * 
	 * @param r
	 *            le r&eacute;pertoire &agrave; ajouter
	 */
	public void ajoutRepert(Repertoire r) {
		this.lesRepertoires.add(r);
	}

	/**
	 * Supprimer un r&eacute;pertoire*
	 * 
	 * @param r
	 *            le r&eacute;pertoire &agrave; supprimer
	 * @throws CompteException
	 *             si le r&eacute;pertoire n'existe pas
	 * 
	 */
	public void supprRepert(Repertoire r) throws CompteException {
		if (this.lesRepertoires.contains(r)) {
			this.lesRepertoires.remove(r);
			return;
		}
		// TODO g�rer exception
		throw new CompteException(ErreurDetail.RepertoireNExistePas);
	}

	/**
	 * Supprimer un r&eacute;pertoire &agrave; partir de son nom
	 * 
	 * @throws CompteException
	 *             si le r&eacute;pertoire n'existe pas
	 * @param nomRep
	 *            le nom du r&eacute;pertoire &agrave; supprimer
	 * 
	 */
	public void supprRepert(String nomRep) throws CompteException {
		this.supprRepert(trouverRepert(nomRep));
	}

	/**
	 * Supprimer tous les R&eacute;pertoires
	 * 
	 */
	public void viderListeRepert() {
		this.lesRepertoires.clear();
	}

	
	/**
	 * Trouver un r&eacute;pertoire (par nom)
	 * 
	 * @return le r&eacute;pertoire trouv&eacute;
	 * @param nomRep
	 *            le nom du r&eacute;pertoire &agrave; chercher
	 * @throws CompteException
	 *             si plusieurs r&eacute;pertoires de m&ecirc;me nom
	 */
	public Repertoire trouverRepert(String nomRep) throws CompteException {
		// TODO g�rer exception
		int doublons = 0;
		Repertoire enCours = null;
		for (Repertoire rep : lesRepertoires) {
			if (rep.getNomRepertoire().equals(nomRep)) {
				doublons++;
				enCours = rep;
			}
			if (doublons > 1) {
				// TODO g�rer exception
				throw new CompteException(ErreurDetail.NomRepertoireAmbigu);
			}
		}
		return enCours;
	}

	public void setLesRepertoires(ArrayList<Repertoire> lesRepertoires) {
		this.lesRepertoires = lesRepertoires;
	}

	

	// ////////// GETTERS Attributs COMPTE-UTILISATEUR///

	public String getNomUser() {
		return nomUser;
	}

	public ChainePasse getPasseUser() {
		return passeUser;
	}

	public ChainePasse getPasseRecoursUser() {
		return passeRecoursUser;
	}

	public String getMailContactUser() {
		return mailContactUser;
	}

	public String getNotePersoUser() {
		return notePersoUser;
	}
	
	public ChainePasse getPasseCompleteInternet() {
		return passeCompleteInternet;
	}

	// ////////// SETTERS //////////////////

	public void setNomUser(String nomUser) {
		this.nomUser = nomUser;
	}

	public void setPasseUser(ChainePasse passeUser) {
		this.passeUser = passeUser;
	}

	public void setPasseRecoursUser(ChainePasse passeRecoursUser) {
		this.passeRecoursUser = passeRecoursUser;
	}

	public void setMailContactUser(String mailContactUser) {
		this.mailContactUser = mailContactUser;
	}

	public void setNotePersoUser(String notePersoUser) {
		this.notePersoUser = notePersoUser;
	}

}