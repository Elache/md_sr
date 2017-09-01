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

    private ChainePasse passeInternet;
    private ChainePasse passeInternetChiffre;

    private boolean isConnecte ;

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


        if (nomU == null){
            throw new CompteException(ErreurDetail.IdentifiantVide);
        }

        if (passeU == null || passeRecoursU == null) {
            throw new CompteException(ErreurDetail.MotDePasseVide);
        }

        instanceSingleton.nomUser = nomU;
        instanceSingleton.mailContactUser = mailContactU;
        instanceSingleton.notePersoUser = notePersoU;

        instanceSingleton.passeUser = ChainePasse.composition(passeU) ;
        instanceSingleton.cleChiffreUser = null ;

        instanceSingleton.passeRecours = ChainePasse.composition(passeRecoursU) ;

        instanceSingleton.passeInternet =  null ;
        if(!instanceSingleton.passeInternetAcceptable(passeU, passeRecoursU)) {
            ChainePasse chPassInternet = ChainePasse.composition(passeU)  ;
            instanceSingleton.passeInternet = ChainePasse.passeComplete10(chPassInternet) ;
        }
        instanceSingleton.isConnecte = true ;
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
     * @param lesRep liste des repertoires
     * @throws CompteException
     */
    public static void recupereLeCompte(String nomU, String passeU, String passeRecoursU, String mailContactU,
                                        String notePersoU,  String passeInternet,  String cleChiffreUser, ArrayList<Repertoire> lesRep) throws CompteException {
        getCompteConnecte().deconnecter() ;
        getCompteConnecte() ;
        instanceSingleton.nomUser = nomU;
        instanceSingleton.passeUserChiffre = ChainePasse.composition(passeU);
        instanceSingleton.passeRecoursChiffre = ChainePasse.composition(passeRecoursU);
        instanceSingleton.mailContactUser = mailContactU;
        instanceSingleton.notePersoUser = notePersoU;
        instanceSingleton.passeInternetChiffre = ChainePasse.composition(passeInternet);
        instanceSingleton.cleChiffreUser = cleChiffreUser;
        instanceSingleton.lesRepertoires = lesRep;
        instanceSingleton.isConnecte = true ;
    }

    // ////////// Gestion de la CONNEXION //////////////////

    /**
     * D&eacute;connexion : instance nulle
     */
    public void deconnecter() {
        instanceSingleton = null;
        isConnecte = false ;
        lesRepertoires = null;
        nomUser = null ;
        mailContactUser = null ;
        notePersoUser = null ;

        passeUser = null;
        passeUserChiffre = null;

        passeRecours = null;
        passeRecoursChiffre = null;

        passeInternet = null;
        passeInternetChiffre = null;

        cleChiffreUser = null;
    }

    // ////////// Accès INTERNET //////////////////

    /**
     * V&eacute;rifie si au moins un mot de passe a une s&eacute;curit&eacute;
     * suffisante pour connexion &agrave; la base de donn&eacute;es en ligne
     * @return true si 1 des mots de passe est de niveau acceptable ou bon
     */
    public static boolean passeInternetAcceptable(String pass1, String pass2) {
        MotDePasseCotation mp = MotDePasseCotation.analyser(pass1);
        if (mp.getValeurAnalyse() >= 3)
            return true;
        mp = MotDePasseCotation.analyser(pass2);
        return mp.getValeurAnalyse() >= 3;
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
     * Supprimer un r&eacute;pertoire &agrave; partir de son idEnBase
     *
     * @param idEnBase id du repertoire dans la base locale (cle primaire) a supprimer
     */
    public void supprRepert(int idEnBase)  throws CompteException {
        this.supprRepert(trouverRepert(idEnBase));
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

    /**
     * Trouver un r&eacute;pertoire (par idEnBase)
     *
     * @param idEnBase identifiant en base de donnee locale du repertoire
     * @return le r&eacute;pertoire trouv&eacute;
     */
    public Repertoire trouverRepert(int idEnBase) {
        for (Repertoire rep : lesRepertoires) {
            if (rep.getidRepEnBase() == idEnBase) {
                return rep;
            }
        }
        return null ;
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
    public String afficherMotPasse(String passeToAfficher) {
        return new ChiffeMode().dechiffrer(passeToAfficher, this.cleChiffreUser);
    }


    // ////////// Chiffrement du mot de passe //////////////////

    /**
     * Chiffrement du mot de passe
     * <BR>(appelle la fonction de chiffrement) ChiffeMode.chiffrer(passeClair)
     */
    public void chiffrerMotPasse(String passeToChiffrer, int selectPasse) {

        ChiffeMode cm = new ChiffeMode();

        if(this.cleChiffreUser != null)
            cm.chiffrer(passeToChiffrer, cleChiffreUser);
        else {
            cm.chiffrer(passeToChiffrer) ;
            this.cleChiffreUser = cm.getCleCode() ;
        }

        ChainePasse chP = ChainePasse.composition(cm.getPasseCode());

        if (selectPasse == 1) {
            this.passeUserChiffre = chP;
        }
        if (selectPasse == 2) {
            this.passeRecoursChiffre = chP;
        }
        if (selectPasse == 3) {
            this.passeInternetChiffre = chP;
        }
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

    public ChainePasse getPasseInternet() {
        return passeInternet;
    }

    public ChainePasse getPasseInternetChiffre() {
        return passeInternetChiffre;
    }

    public boolean getIsConnecte() {
        return isConnecte;
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

    public void setPasseInternet(ChainePasse passInternet)  {
        this.passeInternet = passInternet;
    }

    public void setPasseInternetChiffre(ChainePasse passInternet)  {
        this.passeInternetChiffre = passInternet;
    }
}