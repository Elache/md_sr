package fr.hibon.modepassesecurest.compte.bdd;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import fr.hibon.modepassesecurest.compte.CompteUtilisateur;
import fr.hibon.modepassesecurest.compte.Donnee;
import fr.hibon.modepassesecurest.compte.Repertoire;
import fr.hibon.modepassesecurest.compte.bdd.table.Table_Compte_Utilisateur;
import fr.hibon.modepassesecurest.compte.bdd.table.Table_Donnee;
import fr.hibon.modepassesecurest.compte.bdd.table.Table_Repertoire;
import fr.hibon.modepassesecurest.compte.bdd.table.Table_Site_Web;
import fr.hibon.modepassesecurest.compte.exception.CompteException;
import fr.hibon.modepassesecurest.compte.utiles.ChiffeMode;

/**
 * Created by lohib on 22/08/2017.
 */

public class ManipTables {

    private SQLiteDatabase laBase ;
    private GestionBaseLocale outilBase ;

    public ManipTables(Context c) throws SQLException {
        this.outilBase = new GestionBaseLocale(c);
        try {
            laBase = outilBase.getWritableDatabase();
        } catch (SQLiteException ex) {
            laBase = outilBase.getReadableDatabase();
        }
    }

    public void fermerLaBase() {
        outilBase.close();
    }

    public SQLiteDatabase getLaBase() {
        return laBase;
    }

    public GestionBaseLocale getOutilBase() {
        return outilBase;
    }



    /* ****************** */
    /* TABLE Compte_Utilisateur */
    /* ****************** */


    /* Insert Utilisateur */
    public void insertCompteUtilisateur(String nom, String passe, String passeRecours, String mail, String note, String cle_chiffre) {
        ContentValues valeurs = new ContentValues() ;
        valeurs.put(Table_Compte_Utilisateur.COMPTE_USER_NOM, nom);
        valeurs.put(Table_Compte_Utilisateur.COMPTE_USER_PASSE, passe);
        valeurs.put(Table_Compte_Utilisateur.COMPTE_USER_PASSE_RECOURS, passeRecours);
        valeurs.put(Table_Compte_Utilisateur.COMPTE_USER_MAIL, mail);
        valeurs.put(Table_Compte_Utilisateur.COMPTE_USER_NOTE, note);
        valeurs.put(Table_Compte_Utilisateur.COMPTE_USER_CLE_CHIFFRE, cle_chiffre);
        laBase.insert(Table_Compte_Utilisateur.COMPTE_USER_TABLE_NOM, null, valeurs) ;
    }

    public void supprimeUtilisateur(int index) {
        String requete = "DELETE FROM " + Table_Compte_Utilisateur.COMPTE_USER_TABLE_NOM +
                " WHERE " + Table_Compte_Utilisateur.COMPTE_USER_KEY + " = '" + index + "'; ";
        this.laBase.execSQL(requete);
    }

    /* Rechercher Utilisateur en Base et Instancie si existe */
    public boolean verifierCompte(String userNom, String userPassSaisi) {

        /* REQUETE Compte_Utilisateur */
        String champs = Table_Compte_Utilisateur.COMPTE_USER_NOM + ", " + Table_Compte_Utilisateur.COMPTE_USER_PASSE + ", " +
                Table_Compte_Utilisateur.COMPTE_USER_PASSE_RECOURS + ", " + Table_Compte_Utilisateur.COMPTE_USER_CLE_CHIFFRE ;
        String condition = " WHERE " + Table_Compte_Utilisateur.COMPTE_USER_NOM + " = '" + userNom + "' ;";

        String recherche = "SELECT " + champs + " FROM " + Table_Compte_Utilisateur.COMPTE_USER_TABLE_NOM + condition ;

        /* RECHERCHE : identifiant doit exister */
        Cursor res = laBase.rawQuery(recherche, null );
        if(res.getCount() == 0) {
            res.close();
            laBase.close();
            return false ;
        }
        /*  if(res.getCount() > 1) // EXCEPTION */

        /* Verification mot de passe saisi avec ceux en base de données */
        res.moveToNext() ;
        String mot1 = res.getString(res.getColumnIndex(Table_Compte_Utilisateur.COMPTE_USER_PASSE)) ;
        String mot2 = res.getString(res.getColumnIndex(Table_Compte_Utilisateur.COMPTE_USER_PASSE_RECOURS)) ;
        String cle = res.getString(res.getColumnIndex(Table_Compte_Utilisateur.COMPTE_USER_CLE_CHIFFRE));
        res.close() ;

        ChiffeMode chiffrage = new ChiffeMode() ;
        String motDecode1 = chiffrage.dechiffrer(mot1,cle);
        String motDecode2  = chiffrage.dechiffrer(mot2,cle);

        if (!motDecode1.equals(userPassSaisi) && !motDecode2.equals(userPassSaisi) ) {
            laBase.close();
            return false ;
        }

        String collecte = "SELECT * FROM " + Table_Compte_Utilisateur.COMPTE_USER_TABLE_NOM + condition ;
        res = laBase.rawQuery(collecte, null );
        res.moveToNext() ;
        int id_User = res.getInt(res.getColumnIndex(Table_Compte_Utilisateur.COMPTE_USER_KEY));
        String mail = res.getString(res.getColumnIndex(Table_Compte_Utilisateur.COMPTE_USER_MAIL));
        String note = res.getString(res.getColumnIndex(Table_Compte_Utilisateur.COMPTE_USER_NOTE));
        res.close() ;

        // TODO passeInternet vide car Base locale // gerer aussi quand existe en ligne
        ArrayList<Repertoire> lesRep = listerRepertoires(id_User) ;
        CompteUtilisateur.getCompteConnecte() ;
        try {
            CompteUtilisateur.recupereLeCompte(userNom, mot1, mot2, mail, note,  "",  cle, lesRep);
        } catch(CompteException ce) {}

        laBase.close();
        return true ;
    }

    /* Recupere Repertoires de l'Utilisateur */
    public ArrayList<Repertoire> listerRepertoires(int id_User) {
        ArrayList<Repertoire> lesRep = new ArrayList<>() ;
        String nomRepertoire = ""  ;
        String noteRepertoire = "" ;
        String requete = "SELECT * FROM "
                + Table_Repertoire.REPERTOIRE_TABLE_NOM
                + " WHERE " + Table_Repertoire.REPERTOIRE_UTILISATEUR_ASSOCIE + " = '" + id_User + "' ;" ;
        Cursor res = laBase.rawQuery(requete, null );
        res.moveToFirst();
        while (!res.isAfterLast()) {
            nomRepertoire = res.getString(res.getColumnIndex(Table_Repertoire.REPERTOIRE_NOM)) ;
            noteRepertoire = res.getString(res.getColumnIndex(Table_Repertoire.REPERTOIRE_DESCRIPTION)) ;
            lesRep.add(new Repertoire(nomRepertoire, noteRepertoire)) ;
            res.moveToNext();
        }
        res.close() ;
        return lesRep ;
    }



    /* ****************** */
    /* Table REPERTOIRE */
    /* ****************** */

        /* Insere un repertoire */
    public void inserRepertoire(String nom, String note, int userProprio) {
        ContentValues valeurs = new ContentValues() ;
        valeurs.put(Table_Repertoire.REPERTOIRE_NOM, nom ) ;
        valeurs.put(Table_Repertoire.REPERTOIRE_DESCRIPTION, note) ;
        valeurs.put(Table_Repertoire.REPERTOIRE_UTILISATEUR_ASSOCIE, userProprio) ;
        laBase.insert(Table_Repertoire.REPERTOIRE_TABLE_NOM, null, valeurs) ;
    }


    /* Recupere Donnees d'un Repertoire */
    public ArrayList<Donnee> listerDonnees() {
        CompteUtilisateur user = CompteUtilisateur.getCompteConnecte() ;
        ArrayList<Donnee> lesInfos = new ArrayList<>() ;
        String passeD, nomD, mailD, questD, catD, noteD, webD, logD, cleChD ;

        String requete = "SELECT * FROM "
                + Table_Donnee.DONNEE_TABLE_NOM
                + " WHERE " + Table_Donnee.DONNEE_REPERTOIRE + " =  "  +
                Table_Repertoire.REPERTOIRE_KEY + ";" ;

        Cursor res = laBase.rawQuery(requete, null );
        res.moveToFirst();
        while (!res.isAfterLast()) {

            passeD = res.getString(res.getColumnIndex(Table_Donnee.DONNEE_PASSE)) ;
            nomD = res.getString(res.getColumnIndex(Table_Donnee.DONNEE_NOM )) ;
            mailD = res.getString(res.getColumnIndex(Table_Donnee.DONNEE_MAIL ));
            questD = res.getString(res.getColumnIndex(Table_Donnee.DONNEE_QUESTION));
            catD = res.getString(res.getColumnIndex(Table_Donnee.DONNEE_CATEGORIE ) );
            noteD = res.getString(res.getColumnIndex(Table_Donnee.DONNEE_NOTE )) ;
            webD = res.getString(res.getColumnIndex(Table_Donnee.DONNEE_WEB  ));
            logD = res.getString(res.getColumnIndex(Table_Donnee.DONNEE_LOGIN ) );
            cleChD  = res.getString(res.getColumnIndex(Table_Donnee.DONNEE_CLE_CHIFFRE )) ;

            lesInfos.add(new Donnee(passeD, nomD, logD, mailD, webD, questD, catD, noteD, cleChD)) ;
            res.moveToNext();
        }
        res.close() ;
        return lesInfos ;
    }


    public void supprimeRepertoire(int index) {
        String requete = "DELETE FROM " + Table_Repertoire.REPERTOIRE_TABLE_NOM +
                " WHERE " + Table_Repertoire.REPERTOIRE_KEY + " = '" + index + "'; ";
        this.laBase.execSQL(requete);
    }



    /* ****************** */
    /* TABLE DONNEE */
    /* ****************** */
    /* Insert Donnee */
    public void insertDonnee(String passeDonnee, String nomDonnee, String loginDonnee, String mailDonnee, String siteWebDonnee,
                             String questionSecreteDonnee, String categorieDonnee, String noteDonnee, String cleDonnee) {
        ContentValues valeurs = new ContentValues() ;
        valeurs.put(Table_Donnee.DONNEE_PASSE, passeDonnee) ;
        valeurs.put(Table_Donnee.DONNEE_NOM, nomDonnee) ;
        valeurs.put(Table_Donnee.DONNEE_LOGIN, loginDonnee) ;
        valeurs.put(Table_Donnee.DONNEE_MAIL, mailDonnee) ;
        valeurs.put(Table_Donnee.DONNEE_WEB, siteWebDonnee) ;
        valeurs.put(Table_Donnee.DONNEE_QUESTION, questionSecreteDonnee) ;
        valeurs.put(Table_Donnee.DONNEE_CATEGORIE, categorieDonnee) ;
        valeurs.put(Table_Donnee.DONNEE_NOTE, noteDonnee) ;
        valeurs.put(Table_Donnee.DONNEE_CLE_CHIFFRE, cleDonnee) ;
        laBase.insert(Table_Donnee.DONNEE_TABLE_NOM, null, valeurs) ;
    }



    /* ****************** */
    /* TABLE SITE WEB */
    /* ****************** */

    /* Insert SITE_WEB */
    public void insertSiteWeb(String nom, String url) {
        ContentValues valeurs = new ContentValues() ;
        valeurs.put(Table_Site_Web.SITE_WEB_NOM, nom) ;
        valeurs.put(Table_Site_Web.SITE_WEB_URL, url) ;
        laBase.insert(Table_Site_Web.SITE_WEB_TABLE_NOM, null, valeurs) ;
    }

    public void supprimeSiteWeb(int index) {
        String requete = "DELETE FROM " + Table_Site_Web.SITE_WEB_TABLE_NOM + " WHERE " + Table_Site_Web.SITE_WEB_KEY + " = '" + index + "'; ";
        this.laBase.execSQL(requete);
    }



}