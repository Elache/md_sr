package fr.hibon.modepassesecurest.compte;

import java.util.ArrayList;

import fr.hibon.modepassesecurest.compte.utiles.ChiffeMode;
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
    private String mailContactUser;
    private String notePersoUser;

    private ChainePasse passeUser;
    private ChainePasse passeUserChiffre;
    private String cleChiffreUser;

    private ChainePasse passeRecours;
    private ChainePasse passeRecoursChiffre;
    private String cleChiffreRecours;

    private ChainePasse passeInternet;
    private ChainePasse passeInternetChiffre;
    private String cleChiffreInternet;


    /**
     * Constructeur priv&eacute; : PDC Singleton
     */
    private CompteUtilisateur() {
    }

    /**
     * Acc&egrave;s &agrave; l'instance unique de CompteUtilisateur OU cr&eacute;ation
     * avec informations compl&egrave;tes
     *
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
     * <b>param&egrave;tres saisis par l'Utilisateur</b>
     * <BR>
     * Si aucun mot de passe adapt&eacute; pour usage Internet, mot de passe compl&eacute;t&eacute;
     * jusque longueur 10 et stock&eacute; dans attribut passeInternet
     *
     * @param nomU          nom d'Utilisateur
     * @param passeU        mot de passe Utilisateur (authentification-syst&egrave;me)
     * @param passeRecoursU mot de passe de recours Utilisateur
     * @param mailContactU  mail de l'Utilisateur
     * @param notePersoU    note personnelle sur le CompteUtilisateur
     * @throws CompteException l'identifiant et les mots passe ne doivent pas &ecirc;tre
     *                         vides
     */
    public static void renseigneLeCompte(String nomU, String passeU, String passeRecoursU, String mailContactU,
                                         String notePersoU) throws CompteException {

        getCompteConnecte();

        // TODO gerer exception
        if (nomU.length() == 0 || nomU == null)
            throw new CompteException(ErreurDetail.IdentifiantVide);

        // TODO gerer exception
        if (passeU.length() == 0 || passeRecoursU.length() == 0 || passeU == null || passeRecoursU == null)
            throw new CompteException(ErreurDetail.MotDePasseVide);

        instanceSingleton.nomUser = nomU;
        instanceSingleton.mailContactUser = mailContactU;
        instanceSingleton.notePersoUser = notePersoU;

        instanceSingleton.passeUser = ChainePasse.composition(passeU) ;
        instanceSingleton.cleChiffreUser = null ;

        instanceSingleton.passeRecours = ChainePasse.composition(passeRecoursU) ;
        instanceSingleton.cleChiffreRecours = null ;

        instanceSingleton.passeInternet =  null ;
        instanceSingleton.cleChiffreInternet = null ;
        if(!instanceSingleton.passeInternetAcceptable(passeU, passeRecoursU)) {
            ChainePasse chPassInternet = ChainePasse.composition(passeU)  ;
            instanceSingleton.passeInternet = ChainePasse.passeComplete10(chPassInternet) ;
        }
    }

    /**
     * Instancie un CompteUtilisateur avec les valeurs pass&eacute;es en param&egrave;tres
     * <BR>
     * @param nomU nom utilisateur en BDD
     * @param passeU passe Utilisateur en BDD
     * @param passeRecoursU passe de recours en BDD
     * @param mailContactU mail utilisateur en BDD
     * @param notePersoU note personnelle en BDD
     * @param passeInternet passe-internet (complete)
     * @param cleChiffreUser cle chiffrement Passe-utilisateur en BDD
     * @param cleChiffreRecours cle chiffrement Passe-recours en BDD
     * @param cleChiffreInternet cle chiffrement Passe-complete-internet en BDD
     * @param lesRep liste des repertoires
     * @throws CompteException
     */
    public static void recupereLeCompte(String nomU, String passeU, String passeRecoursU, String mailContactU,
                                        String notePersoU,  String passeInternet,  String cleChiffreUser, String cleChiffreRecours, String cleChiffreInternet, ArrayList<Repertoire> lesRep) throws CompteException {
        getCompteConnecte().deconnecter() ;
        instanceSingleton.nomUser = nomU;
        instanceSingleton.passeUserChiffre = ChainePasse.composition(passeU);
        instanceSingleton.passeRecoursChiffre = ChainePasse.composition(passeRecoursU);
        instanceSingleton.mailContactUser = mailContactU;
        instanceSingleton.notePersoUser = notePersoU;
        instanceSingleton.passeInternetChiffre = ChainePasse.composition(passeInternet);
        instanceSingleton.cleChiffreUser = cleChiffreUser;
        instanceSingleton.cleChiffreRecours = cleChiffreRecours;
        instanceSingleton.cleChiffreInternet = cleChiffreInternet;
        instanceSingleton.lesRepertoires = lesRep;
    }

    // ////////// Gestion de la CONNEXION //////////////////

    /**
     * D&eacute;connexion : instance nulle
     */
    public void deconnecter() {
        instanceSingleton = null;
        lesRepertoires = null;
        nomUser = null ;
        mailContactUser = "";
        notePersoUser = "";

        passeUser = null;
        passeUserChiffre = null;

        passeRecours = null;
        passeRecoursChiffre = null;

        passeInternet = null;
        passeInternetChiffre = null;

        cleChiffreUser = null;
        cleChiffreRecours = null;
        cleChiffreInternet = null;
    }

    // ////////// Accès INTERNET //////////////////

    /**
     * V&eacute;rifie si au moins un mot de passe a une s&eacute;curit&eacute;
     * suffisante pour connexion &agrave; la base de donn&eacute;es en ligne
     * @return true si 1 des mots de passe est de niveau acceptable ou bon
     */
    public boolean passeInternetAcceptable(String pass1, String pass2) {
        MotDePasseCotation mp = MotDePasseCotation.analyser(pass1);
        if (mp.getValeurAnalyse() >= 3)
            return true;
        mp = MotDePasseCotation.analyser(pass2);
        if (mp.getValeurAnalyse() >= 3)
            return true;
        return false;
    }


    // ////////// Gestion des REPERTOIRES //////////////////

    public ArrayList<Repertoire> getLesRepertoires() {
        return lesRepertoires;
    }

    /**
     * Ajouter un r&eacute;pertoire
     *
     * @param r le r&eacute;pertoire &agrave; ajouter
     */
    public void ajoutRepert(Repertoire r) {
        this.lesRepertoires.add(r);
    }

    /**
     * Supprimer un r&eacute;pertoire*
     *
     * @param r le r&eacute;pertoire &agrave; supprimer
     * @throws CompteException si le r&eacute;pertoire n'existe pas
     */
    public void supprRepert(Repertoire r) throws CompteException {
        if (this.lesRepertoires.contains(r)) {
            this.lesRepertoires.remove(r);
            return;
        }
        // TODO gérer exception
        throw new CompteException(ErreurDetail.RepertoireNExistePas);
    }

    /**
     * Supprimer un r&eacute;pertoire &agrave; partir de son nom
     *
     * @param nomRep le nom du r&eacute;pertoire &agrave; supprimer
     * @throws CompteException si le r&eacute;pertoire n'existe pas
     */
    public void supprRepert(String nomRep) throws CompteException {
        this.supprRepert(trouverRepert(nomRep));
    }

    /**
     * Supprimer tous les R&eacute;pertoires
     */
    public void viderListeRepert() {
        this.lesRepertoires.clear();
    }


    /**
     * Trouver un r&eacute;pertoire (par nom)
     *
     * @param nomRep le nom du r&eacute;pertoire &agrave; chercher
     * @return le r&eacute;pertoire trouv&eacute;
     * @throws CompteException si plusieurs r&eacute;pertoires de m&ecirc;me nom
     */
    public Repertoire trouverRepert(String nomRep) throws CompteException {
        // TODO gérer exception
        int doublons = 0;
        Repertoire enCours = null;
        for (Repertoire rep : lesRepertoires) {
            if (rep.getNomRepertoire().equals(nomRep)) {
                doublons++;
                enCours = rep;
            }
            if (doublons > 1) {
                // TODO gérer exception
                throw new CompteException(ErreurDetail.NomRepertoireAmbigu);
            }
        }
        return enCours;
    }

    public void setLesRepertoires(ArrayList<Repertoire> lesRepertoires) {
        this.lesRepertoires = lesRepertoires;
    }


    // ////////// Affichage passe en clair //////////////////

    /**
     * Affichage du mot de passe en clair
     * <BR>(appelle la fonction de d&eacute;chiffrement) ChiffeMode.dechiffrer(passeClair)
     *
     * @return mot de passe en clair (String)
     */
    public String afficherMotPasse(String passeToAfficher, int selectPasse) {
        // 1 Mot de passe principal passe
        if(selectPasse == 1)
            return new ChiffeMode().dechiffrer(passeToAfficher, this.cleChiffreUser);
        if(selectPasse == 2)
            return new ChiffeMode().dechiffrer(passeToAfficher, this.cleChiffreRecours);
        if(selectPasse == 3)
            return new ChiffeMode().dechiffrer(passeToAfficher, this.cleChiffreInternet);
        return "" ;
    }


    // ////////// Chiffrement du mot de passe //////////////////

    /**
     * Chiffrement du mot de passe
     * <BR>(appelle la fonction de chiffrement) ChiffeMode.chiffrer(passeClair)
     */
    public void chiffrerMotPasse(String passeToChiffrer, int selectPasse) {
        ChiffeMode cm = new ChiffeMode();
        cm.chiffrer(passeToChiffrer);
        ChainePasse chP = ChainePasse.composition(cm.getPasseCode());
        if (selectPasse == 1) {
            this.passeUserChiffre = chP;
            this.cleChiffreUser = cm.getCleCode();
        }
        if (selectPasse == 2) {
            this.passeRecoursChiffre = chP;
            this.cleChiffreRecours = cm.getCleCode();
        }
        if (selectPasse == 3) {
            this.passeInternetChiffre = chP;
            this.cleChiffreInternet = cm.getCleCode();
        }
    }

    /**
     * Chiffre les mots de passe (user et recours + internet si existe) et affecte valeurs
     */
    public void chiffrerLesPasses() {
        CompteUtilisateur cu = getCompteConnecte() ;
        cu.chiffrerMotPasse(this.passeUser.getChaineDuPasse(), 1);
        cu.chiffrerMotPasse(this.passeRecours.getChaineDuPasse(), 2);
        if(this.passeInternet != null && this.passeInternet.getChaineDuPasse() != "" && this.passeInternet.getChaineDuPasse() !=  null)
            cu.chiffrerMotPasse(this.passeInternet.getChaineDuPasse(), 3);
    }


    // ////////// GETTERS Attributs COMPTE-UTILISATEUR///

    public String getNomUser() {
        return nomUser;
    }

    public String getMailContactUser() {
        return mailContactUser;
    }

    public String getNotePersoUser() {
        return notePersoUser;
    }

    public ChainePasse getPasseUser() {
        return passeUser;
    }

    public ChainePasse getPasseUserChiffre() {
        return passeUserChiffre;
    }

    public String getCleChiffreUser() {
        return cleChiffreUser;
    }

    public ChainePasse getPasseRecours() {
        return passeRecours;
    }

    public ChainePasse getPasseRecoursChiffre() {
        return passeRecoursChiffre;
    }

    public String getCleChiffreRecours() {
        return cleChiffreRecours;
    }

    public ChainePasse getPasseInternet() {
        return passeInternet;
    }

    public ChainePasse getPasseInternetChiffre() {
        return passeInternetChiffre;
    }

    public String getCleChiffreInternet() {
        return cleChiffreInternet;
    }


    // ////////// SETTERS //////////////////

    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }

    public void setMailContactUser(String mailContactUser) {
        this.mailContactUser = mailContactUser;
    }

    public void setNotePersoUser(String notePersoUser) {
        this.notePersoUser = notePersoUser;
    }

    public void setPasseUser(ChainePasse passeUser) {
        this.passeUser = passeUser;
    }

    public void setPasseUserChiffre(ChainePasse passeChUser) {
        this.passeUserChiffre = passeChUser;
    }

    public void setCleChiffreUser(String cleChiffreU) {
        this.cleChiffreUser = cleChiffreU ;
    }

    public void setPasseRecours(ChainePasse passeRecours) {
        this.passeRecours = passeRecours;
    }

    public void setPasseRecoursChiffre(ChainePasse passeRecoursC) {
        this.passeRecoursChiffre = passeRecoursC;
    }

    public void setCleChiffreRecours(String cleChiffreR) {
        this.cleChiffreRecours = cleChiffreR ;
    }

    public void setPasseInternet(ChainePasse passInternet)  {
        this.passeInternet = passInternet;
    }

    public void setPasseInternetChiffre(ChainePasse passInternet)  {
        this.passeInternetChiffre = passInternet;
    }
    public void setCleChiffreInternet(String cleInternet) {
        this.cleChiffreInternet= cleInternet ;
    }
}