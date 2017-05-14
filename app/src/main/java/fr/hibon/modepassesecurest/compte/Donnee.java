package fr.hibon.modepassesecurest.compte;

/**
 * Donn&eacute;e : l'information conserv&eacute;e dans les r&eacute;pertoires (= les mots de passe personnels)
 *
 */
public class Donnee {

	private String nomDonnee;
	private String passeDonnee;

	private String loginDonnee;
	private String mailDonnee;
	private String siteWebDonnee;
	private String questionSecreteDonnee;
	private String noteDonnee;

	private String categorieDonnee;

	public Donnee() {
	}

	public Donnee(String mailDonnee, String questionSecreteDonnee, String categorieDonnee, String noteDonnee,
			String nomDonnee, String siteWebDonnee, String loginDonnee, String passeDonnee) {
		super();
		this.mailDonnee = mailDonnee;
		this.questionSecreteDonnee = questionSecreteDonnee;
		this.categorieDonnee = categorieDonnee;
		this.noteDonnee = noteDonnee;
		this.nomDonnee = nomDonnee;
		this.siteWebDonnee = siteWebDonnee;
		// TODO  voir url web
		this.loginDonnee = loginDonnee;
		this.passeDonnee = passeDonnee;
	}

	// ////////// Affichage passe en clair //////////////////

	/** Affichage du mot de passe en clair
	 * <BR>(appelle la fonction de d&eacute;chiffrement)
	 * @return mot de passe en clair (String) 
	 */
	public String afficherMotPasse() {
		String passeClair = "";
		// TODO Déchiffrer et afficher - conserver clé dans objet ?
		return passeClair;
	}

	// ////////// VERIFIE LA PRESENCE D'UN MOT //////////////////

	/** Teste si un mot fait partie d'un champ (cha&icirc;ne) 
	 * @param mot cha&icirc;ne &agrave; tester
	 * @param champ
	 *            champ dans lequel tester pr&eacute;sence
	 * @return true si mot pr&eacute;sent
	 */
	public static boolean presenceMotDansChamp(String champ, String mot) {
		if (champ.contains(mot))
			return true;
		return false;
	}

	/**
	 * Cherche le mot dans les champs texte : nomDonnee, questionSecreteDonnee et
	 * noteDonnee
	 * @param mot cha&icirc;ne &agrave; tester
	 * @return true si mot pr&eacute;sent
	 */
	public boolean presenceMotDansObjet(String mot) {
		if (presenceMotDansChamp(nomDonnee, mot) || presenceMotDansChamp(questionSecreteDonnee, mot)
				|| presenceMotDansChamp(noteDonnee, mot))
			return true;
		return false;
	}

	// ////////// EQUALS et HASHCODE//////////////////

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((loginDonnee == null) ? 0 : loginDonnee.hashCode());
		result = prime * result + ((nomDonnee == null) ? 0 : nomDonnee.hashCode());
		result = prime * result + ((passeDonnee == null) ? 0 : passeDonnee.hashCode());
		result = prime * result + ((siteWebDonnee == null) ? 0 : siteWebDonnee.hashCode());
		return result;
	}

	/**
	 * Deux Donn&eacute;es sont &eacute;gales si nom, mot de passe et login sont identiques
	 */
	@Override
	public boolean equals(Object obj) {
		// TODO verifier equals : nom + passe ?
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Donnee other = (Donnee) obj;
		if (loginDonnee == null) {
			if (other.loginDonnee != null)
				return false;
		} else if (!loginDonnee.equals(other.loginDonnee))
			return false;
		if (nomDonnee == null) {
			if (other.nomDonnee != null)
				return false;
		} else if (!nomDonnee.equals(other.nomDonnee))
			return false;
		if (passeDonnee == null) {
			if (other.passeDonnee != null)
				return false;
		} else if (!passeDonnee.equals(other.passeDonnee))
			return false;
		return true;
	}

	// ////////// GETTERS //////////////////

	public String getNomDonnee() {
		return nomDonnee;
	}

	public String getPasseDonnee() {
		return passeDonnee;
	}

	public String getLoginDonnee() {
		return loginDonnee;
	}

	public String getMailDonnee() {
		return mailDonnee;
	}

	public String getSiteWebDonnee() {
		return siteWebDonnee;
	}

	public String getQuestionSecreteDonnee() {
		return questionSecreteDonnee;
	}

	public String getNoteDonnee() {
		return noteDonnee;
	}

	public String getCategorieDonnee() {
		return categorieDonnee;
	}

	// ////////// SETTERS //////////////////

	public void setNomDonnee(String nomDonnee) {
		this.nomDonnee = nomDonnee;
	}

	public void setPasseDonnee(String passeDonnee) {
		this.passeDonnee = passeDonnee;
	}

	public void setLoginDonnee(String loginDonnee) {
		this.loginDonnee = loginDonnee;
	}

	public void setMailDonnee(String mailDonnee) {
		this.mailDonnee = mailDonnee;
	}

	public void setSiteWebDonnee(String siteWebDonnee) {
		this.siteWebDonnee = siteWebDonnee;
	}

	public void setQuestionSecreteDonnee(String questionSecreteDonnee) {
		this.questionSecreteDonnee = questionSecreteDonnee;
	}

	public void setNoteDonnee(String noteDonnee) {
		this.noteDonnee = noteDonnee;
	}

	public void setCategorieDonnee(String categorieDonnee) {
		this.categorieDonnee = categorieDonnee;
	}

}