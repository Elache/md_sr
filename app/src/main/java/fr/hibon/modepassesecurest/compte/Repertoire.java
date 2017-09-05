package fr.hibon.modepassesecurest.compte;

import java.util.ArrayList;
import java.util.List;

import fr.hibon.modepassesecurest.compte.exception.CompteException;
import fr.hibon.modepassesecurest.compte.exception.CompteException.ErreurDetail;


/**
 * Les R&eacute;pertoires regroupent les Donn&eacute;es et peuvent avoir un nom et une note de description 
 *
 */
public class Repertoire {

	private ArrayList<Donnee> lesDonnees;

	private int idRepEnBase ;
	private String nomRepertoire;
	private String noteRepertoire;

	public Repertoire() {
		lesDonnees = new ArrayList<Donnee>();
	}

	public Repertoire(String nomRepertoire, String noteRepertoire) {
		super();
		this.lesDonnees = new ArrayList<Donnee>();
		this.nomRepertoire = nomRepertoire;
		this.noteRepertoire = noteRepertoire;
		this.idRepEnBase = -1 ;
	}

	public Repertoire(String nomRepertoire, String noteRepertoire, int id) {
		super();
		this.lesDonnees = new ArrayList<Donnee>();
		this.nomRepertoire = nomRepertoire;
		this.noteRepertoire = noteRepertoire;
		this.idRepEnBase = id ;
	}

	// ////////// Administration des DONNEES dans le Repertoire ////


	public ArrayList<Donnee> getLesDonnees() {
		return this.lesDonnees ;
	}

	
	public void setLesDonnees(ArrayList<Donnee> listeDonnees) {
		this.lesDonnees = listeDonnees ;
	}
	
	/**
	 * Ajouter une donn&eacute;e
	 * @param d Donn&eacute;e &agrave; ajouter
	 */
	public void ajoutDonnee(Donnee d) {
		lesDonnees.add(d);
	}

	/** Supprimer une donn&eacute;e
	 * @throws CompteException si la donn&eacute;e ne peut &ecirc;tre trouv&eacute;e
	 * @param d Donn&eacute;e &agrave; supprimer
	 */
	public void supprDonnee(Donnee d) throws CompteException {
		if (lesDonnees.contains(d)) {
			lesDonnees.remove(d);
			return ;
		}
		throw new CompteException(ErreurDetail.DonneeNExistePas) ;
	}

	/** Supprimer une donn&eacute;e
	 * @throws CompteException si la donn&eacute;e ne peut &ecirc;tre trouv&eacute;e
	 * @param idEnBase de la donnee a supprimer
	 */
	public void supprDonnee(int idEnBase) {
		for (Donnee donn : lesDonnees) {
			if (donn.getIdEnBaseLocale() == idEnBase) {
				lesDonnees.remove(donn);
				return ;
			}
		}
	}


	// ////////// Recherche de DONNEES dans le Repertoire ////

	/** Rechercher des Donn&eacute;es par nom 
	 * @param nom cha&icirc;ne cherch&eacute;e
	 * @return liste des donn&eacute;es trouv&eacute;es (ou vide)
	 */
	public List<Donnee> cherchDonneeNom(String nom) {
		ArrayList<Donnee> nomsTrouves = new ArrayList<Donnee>();
		for (Donnee d : this.lesDonnees) {
			if (d.getNomDonnee().equals(nom))
				nomsTrouves.add(d);
		}
		return nomsTrouves;
	}

	/** Rechercher des Donn&eacute;es par adresse email associ&eacute;e
	 * @param mail cha&icirc;ne cherch&eacute;e
	 * @return liste des donn&eacute;es trouv&eacute;es (ou vide)
	 */
	public List<Donnee> cherchDonneeMail(String mail) {
		ArrayList<Donnee> mailsTrouves = new ArrayList<Donnee>();
		for (Donnee d : this.lesDonnees) {
			if (d.getMailDonnee().equals(mail))
				mailsTrouves.add(d);
		}
		return mailsTrouves;
	}

	/** Rechercher des Donn&eacute;es dont la note contient la cha&icirc;ne en param&egrave;tre
	 * @param ch cha&icirc;ne cherch&eacute;e
	 * @return liste des donn&eacute;es trouv&eacute;es (ou vide)
	 */
	public List<Donnee> cherchDonneeNote(String ch) {
		ArrayList<Donnee> notesTrouves = new ArrayList<Donnee>();
		for (Donnee d : this.lesDonnees) {
			if (Donnee.presenceMotDansChamp(d.getNoteDonnee(), ch))
				notesTrouves.add(d);
		}
		return notesTrouves;
	}

	/** Rechercher des Donn&eacute;es dont la question secr&egrave;te contient la cha&icirc;ne en param&egrave;tre
	 * @param ch cha&icirc;ne cherch&eacute;e
	 * @return liste des donn&eacute;es trouv&eacute;es (ou vide)
	 */
	public List<Donnee> cherchDonneeQuestSecret(String ch) {
		ArrayList<Donnee> questionsTrouves = new ArrayList<Donnee>();
		for (Donnee d : this.lesDonnees) {
			if (Donnee.presenceMotDansChamp(d.getQuestionSecreteDonnee(), ch))
				questionsTrouves.add(d);
		}
		return questionsTrouves;
	}

	/** Rechercher des Donn&eacute;es dont le site web associ&eacute; contient la cha&icirc;ne en param&egrave;tre
	 * @param ch cha&icirc;ne cherch&eacute;e
	 * @return liste des donn&eacute;es trouv&eacute;es (ou vide)
	 */
	public List<Donnee> cherchDonneeWebsite(String ch) {
		ArrayList<Donnee> websitesTrouves = new ArrayList<Donnee>();
		for (Donnee d : this.lesDonnees) {
			if (Donnee.presenceMotDansChamp(d.getSiteWebDonnee(), ch))
				websitesTrouves.add(d);
		}
		return websitesTrouves;
	}
	

	// ////////// Verifie presence D'UN MOT dans champs REPERTOIRE ////

	/** V&eacute;rifie la pr&eacute;sence d'un mot dans un champ (cha&icirc;ne)
	 * @param mot cha&icirc;ne &agrave; trouver
	 * @param champ champ dans lequel tester pr&eacute;sence
	 * @return true si le mot existe dans le champ
	 */
	public static boolean presenceMotDansChamp(String champ, String mot) {
		return champ.contains(mot);
	}

	/** V&eacute;rifie si la cha&icirc;ne en param&egrave;tres est pr&eacute;sente dans le nom ou la note du Repertoire
	 * @param mot cha&icirc;ne de caract&egrave;res &agrave; tester
	 * @return true si la cha&icirc;ne est pr&eacute;sente (false si absente)
	 */
	public boolean presenceMotDansObjet(String mot) {
		if(mot == null)
			return false ;
		return this.nomRepertoire.contains(mot) || this.noteRepertoire.contains(mot);
	}



	// ////////// GETTERS //////////////////

	public String getNomRepertoire() {
		return nomRepertoire;
	}

	public String getNoteRepertoire() {
		return noteRepertoire;
	}

	public int getidRepEnBase() {
		return idRepEnBase;

	}

	// ////////// SETTERS //////////////////

	public void setNomRepertoire(String nomRepertoire) {
		this.nomRepertoire = nomRepertoire;
	}

	public void setNoteRepertoire(String noteRepertoire) {
		this.noteRepertoire = noteRepertoire;
	}


	// ////////// EQUALS et HASHCODE//////////////////

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nomRepertoire == null) ? 0 : nomRepertoire.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO determiner EQUALS
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Repertoire other = (Repertoire) obj;
		if (nomRepertoire == null) {
			if (other.nomRepertoire != null)
				return false;
		} else if (!nomRepertoire.equals(other.nomRepertoire))
			return false;
		return true;
	}

	@Override
	public String toString() {
		if(this.getNomRepertoire() == null || this.getNomRepertoire().length() == 0 )
			return "Mon Répertoire" ;
		return this.getNomRepertoire() ;
	}
}