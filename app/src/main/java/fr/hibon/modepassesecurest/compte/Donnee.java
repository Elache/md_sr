package fr.hibon.modepassesecurest.compte;

import fr.hibon.modepassesecurest.compte.utiles.ChiffeMode;

/**
 * Donn&eacute;e : l'information conserv&eacute;e dans les r&eacute;pertoires (= les mots de passe personnels)
 */
public class Donnee {

    private String passeDonnee;
    private String nomDonnee;

    private String loginDonnee;
    private String mailDonnee;
    private String siteWebDonnee;
    private String questionSecreteDonnee;
    private String noteDonnee;

    private String categorieDonnee;

    private String passeDonneeChiffre;
    private String cleChiffrementDonnee;

    public Donnee() {
    }

    /**
     * Cr&eacute;ation d'une Donn&eacute;e par l'Utilisateur (= saisie).<br>
     * L'objet conserve le mot de passe saisi
     *
     * @param passeDonnee           le mot de passe &agrave; conserver ( = saisi )
     * @param nomDonnee             nom de la Donn&eacute;e
     * @param mailDonnee            mail associ&eacute; au mot de passe (Donn&eacute;)
     * @param questionSecreteDonnee question secr&egrave;te (et r&eacute;ponse)
     * @param categorieDonnee       cat&eacute;gorie choisie pour la donn&eacute;e
     * @param noteDonnee            commentaire
     * @param siteWebDonnee         site web associ&eacute;
     * @param loginDonnee           identifiant associ&eacute; au mot de passe
     */
    public Donnee(String passeDonnee, String nomDonnee, String loginDonnee, String mailDonnee, String siteWebDonnee,
                  String questionSecreteDonnee, String categorieDonnee, String noteDonnee) {
        this(nomDonnee, loginDonnee, mailDonnee, siteWebDonnee, questionSecreteDonnee, categorieDonnee, noteDonnee);
        this.passeDonnee = passeDonnee ;
    }

    /**
     * R&eacute;cup&eacute;ration d'une Donn&eacute;e dans la base de donn&eacute;es <br>
     * L'affichage du mot de passe en clair est possible mais il n'est conserv&eacute; que sous forme chiffr&eacute;e
     * @param passeDonnee           le mot de passe &agrave; conserver ( = saisi )
     * @param nomDonnee             nom de la Donn&eacute;e
     * @param mailDonnee            mail associ&eacute; au mot de passe (Donn&eacute;)
     * @param questionSecreteDonnee question secr&egrave;te (et r&eacute;ponse)
     * @param categorieDonnee       cat&eacute;gorie choisie pour la donn&eacute;e
     * @param noteDonnee            commentaire
     * @param siteWebDonnee         site web associ&eacute;
     * @param loginDonnee           identifiant associ&eacute; au mot de passe
     * @param cleDonnee             cl&eacute; utilis&eacute;e et conserv&eacute;e en base
     */
    public Donnee(String passeDonnee, String nomDonnee, String loginDonnee, String mailDonnee, String siteWebDonnee,
                  String questionSecreteDonnee, String categorieDonnee, String noteDonnee, String cleDonnee) {
        this(nomDonnee, loginDonnee, mailDonnee, siteWebDonnee, questionSecreteDonnee, categorieDonnee, noteDonnee);
        this.passeDonneeChiffre = passeDonnee;
        this.cleChiffrementDonnee = cleDonnee;
    }

    /**
     * Partie commune des constructeurs, affectation des informations additionnelles
     *
     * @param nomDonnee             nom de la Donn&eacute;e
     * @param mailDonnee            mail associ&eacute; au mot de passe (Donn&eacute;)
     * @param questionSecreteDonnee question secr&egrave;te (et r&eacute;ponse)
     * @param categorieDonnee       cat&eacute;gorie choisie pour la donn&eacute;e
     * @param noteDonnee            commentaire
     * @param siteWebDonnee         site web associ&eacute;
     * @param loginDonnee           identifiant associ&eacute; au mot de passe
     */
    private Donnee(String nomDonnee, String loginDonnee, String mailDonnee, String siteWebDonnee, String questionSecreteDonnee,
                   String categorieDonnee, String noteDonnee) {
        this.nomDonnee = nomDonnee;
        this.loginDonnee = loginDonnee;
        this.mailDonnee = mailDonnee;
        // TODO  voir url web
        this.siteWebDonnee = siteWebDonnee;
        this.questionSecreteDonnee = questionSecreteDonnee;
        this.categorieDonnee = categorieDonnee;
        this.noteDonnee = noteDonnee;
    }

    // ////////// Affichage passe en clair //////////////////

    /**
     * Affichage du mot de passe en clair
     * <BR>(appelle la fonction de d&eacute;chiffrement) ChiffeMode.dechiffrer(passeClair)
     *
     * @return mot de passe en clair (String)
     */
    public String afficherMotPasse() {
        ChiffeMode cm = new ChiffeMode() ;
        return cm.dechiffrer(this.passeDonneeChiffre, this.cleChiffrementDonnee);
    }


    // ////////// Chiffrement du mot de passe //////////////////

    /**
     * Chiffrement du mot de passe
     * <BR>(appelle la fonction de chiffrement) ChiffeMode.chiffrer(passeClair)
     *
     * @return Objet ChiffeMode (passe chiffr&eacute; + cl&eacute;)
     */
    public void chiffrerMotPasse() {
        ChiffeMode cm = new ChiffeMode() ;
        cm.chiffrer(this.passeDonnee);
        this.passeDonneeChiffre = cm.getPasseCode();
        this.cleChiffrementDonnee = cm.getCleCode();
    }


    // ////////// VERIFIE LA PRESENCE D'UN MOT //////////////////

    /**
     * Teste si un mot fait partie d'un champ (cha&icirc;ne)
     *
     * @param mot   cha&icirc;ne &agrave; tester
     * @param champ champ dans lequel tester pr&eacute;sence
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
     *
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
        result = prime * result + ((siteWebDonnee == null) ? 0 : siteWebDonnee.hashCode());
        return result;
    }

    /**
     * Deux Donn&eacute;es sont &eacute;gales si nom et login sont identiques
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

    public String getPasseDonneeChiffre() {
        return passeDonneeChiffre ;
    }

    public String getCleChiffrementDonnee() {
        return cleChiffrementDonnee ;
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