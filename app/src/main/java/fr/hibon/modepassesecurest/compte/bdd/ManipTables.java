package fr.hibon.modepassesecurest.compte.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;


import java.util.ArrayList;

import fr.hibon.modepassesecurest.compte.CompteUtilisateur;
import fr.hibon.modepassesecurest.compte.Donnee;
import fr.hibon.modepassesecurest.compte.Repertoire;
import fr.hibon.modepassesecurest.compte.bdd.table.Table_Site_Web;
import fr.hibon.modepassesecurest.compte.exception.CompteException;
import fr.hibon.modepassesecurest.compte.utiles.ChiffeMode;

import static fr.hibon.modepassesecurest.compte.bdd.table.Table_Categorie.*;
import static fr.hibon.modepassesecurest.compte.bdd.table.Table_Compte_Utilisateur.* ;
import static fr.hibon.modepassesecurest.compte.bdd.table.Table_Donnee.*;
import static fr.hibon.modepassesecurest.compte.bdd.table.Table_Repertoire.*;


/**
 * Created by lohib on 22/08/2017.
 */

public class ManipTables {


    private static ManipTables manipBase ;
    private static SQLiteDatabase laBase ;
    private final GestionBaseLocale outilBase ;

    private ManipTables(Context c) throws SQLException {
        this.outilBase = new GestionBaseLocale(c);
        try {
            laBase = outilBase.getWritableDatabase();
        } catch (SQLiteException ex) {
            laBase = outilBase.getReadableDatabase();
        }
        manipBase = this ;
    }

    public static ManipTables accesBase(Context c) {
        if(manipBase != null)
            return manipBase ;
        manipBase = new ManipTables(c) ;
        return manipBase ;
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
        valeurs.put(COMPTE_USER_NOM, nom);
        valeurs.put(COMPTE_USER_PASSE, passe);
        valeurs.put(COMPTE_USER_PASSE_RECOURS, passeRecours);
        valeurs.put(COMPTE_USER_MAIL, mail);
        valeurs.put(COMPTE_USER_NOTE, note);
        valeurs.put(COMPTE_USER_CLE_CHIFFRE, cle_chiffre);
        laBase.insert(COMPTE_USER_TABLE_NOM, null, valeurs) ;
    }

    public void supprimeUtilisateur(int index) {
        String requete = "DELETE FROM " + COMPTE_USER_TABLE_NOM +
                " WHERE " + COMPTE_USER_KEY + " = '" + index + "'; ";
        this.laBase.execSQL(requete);
    }



    /* Rechercher Utilisateur en Base et Instancie si existe */
    public boolean verifierCompte(String userNom, String userPassSaisi) {

        /* REQUETE Compte_Utilisateur */
        String champs = COMPTE_USER_NOM + ", " + COMPTE_USER_PASSE + ", " +
                COMPTE_USER_PASSE_RECOURS + ", " + COMPTE_USER_CLE_CHIFFRE ;
        String condition = " WHERE " + COMPTE_USER_NOM + " = '" + userNom + "' ;";

        String recherche = "SELECT " + champs + " FROM " + COMPTE_USER_TABLE_NOM + condition ;

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
        String mot1 = res.getString(res.getColumnIndex(COMPTE_USER_PASSE)) ;
        String mot2 = res.getString(res.getColumnIndex(COMPTE_USER_PASSE_RECOURS)) ;
        String cle = res.getString(res.getColumnIndex(COMPTE_USER_CLE_CHIFFRE));
        res.close() ;

        ChiffeMode chiffrage = new ChiffeMode() ;
        String motDecode1 = chiffrage.dechiffrer(mot1,cle);
        String motDecode2  = chiffrage.dechiffrer(mot2,cle);

        if (!motDecode1.equals(userPassSaisi) && !motDecode2.equals(userPassSaisi) ) {
            laBase.close();
            return false ;
        }

        String collecte = "SELECT * FROM " + COMPTE_USER_TABLE_NOM + condition ;
        res = laBase.rawQuery(collecte, null );
        res.moveToNext() ;
        int id_User = res.getInt(res.getColumnIndex(COMPTE_USER_KEY));
        String mail = res.getString(res.getColumnIndex(COMPTE_USER_MAIL));
        String note = res.getString(res.getColumnIndex(COMPTE_USER_NOTE));
        res.close() ;

        // TODO passeInternet vide car Base locale // gerer aussi quand existe en ligne
        ArrayList<Repertoire> lesRep = listerRepertoires(id_User) ;
        CompteUtilisateur.getCompteConnecte() ;
        CompteUtilisateur.recupereLeCompte(userNom, mot1, mot2, mail, note,  "",  cle, lesRep);
        laBase.close();
        return true ;
    }



    /* Recupere Repertoires de l'Utilisateur */
    public ArrayList<Repertoire> listerRepertoires(int id_User) {
        ArrayList<Repertoire> lesRep = new ArrayList<>() ;
        String nomRepertoire;
        String noteRepertoire;
        int idRep = -1 ;
        String requete = "SELECT * FROM "
                + REPERTOIRE_TABLE_NOM
                + " WHERE " + REPERTOIRE_UTILISATEUR_ASSOCIE + " = '" + id_User + "' ;" ;
        Cursor res = laBase.rawQuery(requete, null );
        res.moveToFirst();
        while (!res.isAfterLast()) {
            nomRepertoire = res.getString(res.getColumnIndex(REPERTOIRE_NOM)) ;
            noteRepertoire = res.getString(res.getColumnIndex(REPERTOIRE_DESCRIPTION)) ;
            idRep = res.getInt(res.getColumnIndex(REPERTOIRE_KEY)) ;
            lesRep.add(new Repertoire(nomRepertoire, noteRepertoire, idRep)) ;
            res.moveToNext();
        }
        res.close() ;
        return lesRep ;
    }



    // verifie l'existence d'un identifiant
    private boolean identifExiste(Context context, String identifiant) {
        String req = "SELECT * FROM " + COMPTE_USER_TABLE_NOM
                + " WHERE " + COMPTE_USER_NOM + " ='" + identifiant + "';" ;
        Cursor res = laBase.rawQuery(req, null );
        int nbVal = res.getCount() ;
        res.close();
        return nbVal > 0;
    }

    private boolean identiNVide(String id) {
        return id.length() == 0 || id == null;
    }

    public String erreursBloquantes(Context context, String identifiant) {
        String erreur = ""  ;
        if(identiNVide(identifiant))
            erreur += "L'identifiant ne peut être vide." +
                    "\n Pour créer un Compte, il vous faut un identifiant." ;
        if(identifExiste(context, identifiant))
            erreur += "Cet identifiant existe déjà dans la base de données. " +
                    "\n Pour créer un Compte, il faut un nouvel identifiant." ;
        return erreur ;
    }



    /* ****************** */
    /* Table REPERTOIRE */
    /* ****************** */

        /* Insere un repertoire */
    public void inserRepertoire(String nom, String note, int userProprio) {
        ContentValues valeurs = new ContentValues() ;
        valeurs.put(REPERTOIRE_NOM, nom ) ;
        valeurs.put(REPERTOIRE_DESCRIPTION, note) ;
        valeurs.put(REPERTOIRE_UTILISATEUR_ASSOCIE, userProprio) ;
        laBase.insert(REPERTOIRE_TABLE_NOM, null, valeurs) ;
    }


    /* Recupere Donnees d'un Repertoire */
    public ArrayList<Donnee> listerDonnees(int repertoire) {
        ArrayList<Donnee> lesInfos = new ArrayList<>() ;
        String passeD, nomD, mailD, questD, catD, noteD, webD, logD, cleChD ;
        int keyD ;

        String requete = "SELECT * FROM "
                + DONNEE_TABLE_NOM
                + " WHERE " + DONNEE_REPERTOIRE + " = '"  +
                repertoire + "' ;" ;

        Cursor res = laBase.rawQuery(requete, null );
        res.moveToFirst();
        while (!res.isAfterLast()) {

            passeD = res.getString(res.getColumnIndex(DONNEE_PASSE)) ;
            nomD = res.getString(res.getColumnIndex(DONNEE_NOM )) ;
            mailD = res.getString(res.getColumnIndex(DONNEE_MAIL ));
            questD = res.getString(res.getColumnIndex(DONNEE_QUESTION));
            catD = res.getString(res.getColumnIndex(DONNEE_CATEGORIE ) );
            noteD = res.getString(res.getColumnIndex(DONNEE_NOTE )) ;
            webD = res.getString(res.getColumnIndex(DONNEE_WEB  ));
            logD = res.getString(res.getColumnIndex(DONNEE_LOGIN ) );
            cleChD  = res.getString(res.getColumnIndex(DONNEE_CLE_CHIFFRE )) ;
            keyD = res.getInt(res.getColumnIndex(DONNEE_KEY));
            lesInfos.add(new Donnee(passeD, nomD, logD, mailD, webD, questD, catD, noteD, cleChD, keyD)) ;
            res.moveToNext();
        }
        res.close() ;
        return lesInfos ;
    }


    public void supprimeRepertoire(int index) {
        String requete = "DELETE FROM " + REPERTOIRE_TABLE_NOM +
                " WHERE " + REPERTOIRE_KEY + " = '" + index + "'; ";
        this.laBase.execSQL(requete);
    }



    /* ****************** */
    /* TABLE DONNEE */
    /* ****************** */

    /* Insert Donnee */
    public void insertDonnee(String passeDonnee, String nomDonnee, String loginDonnee, String mailDonnee, String siteWebDonnee,
                             String questionSecreteDonnee, String categorieDonnee, String noteDonnee, String cleDonnee, int repertoire) {
        long dateCreation = System.currentTimeMillis() ;
        ContentValues valeurs = new ContentValues() ;
        valeurs.put(DONNEE_NOM, nomDonnee) ;
        valeurs.put(DONNEE_LOGIN, loginDonnee) ;
        valeurs.put(DONNEE_PASSE, passeDonnee) ;
        valeurs.put(DONNEE_QUESTION, questionSecreteDonnee) ;
        valeurs.put(DONNEE_MAIL, mailDonnee) ;
        valeurs.put(DONNEE_NOTE, noteDonnee) ;
        valeurs.put(DONNEE_DATE_CREE, dateCreation) ;
        valeurs.put(DONNEE_DATE_MODIF, dateCreation) ;
        valeurs.put(DONNEE_CLE_CHIFFRE, cleDonnee) ;
        valeurs.put(DONNEE_CATEGORIE, categorieDonnee) ;
        valeurs.put(DONNEE_REPERTOIRE, repertoire) ;
        valeurs.put(DONNEE_WEB, siteWebDonnee) ;
        laBase.insert(DONNEE_TABLE_NOM, null, valeurs) ;
    }

    public void insertDonnee(Donnee donnee, int repert) {
        long dateCreation = System.currentTimeMillis() ;
        ContentValues valeurs = new ContentValues() ;
        valeurs.put(DONNEE_NOM, donnee.getNomDonnee()) ;
        valeurs.put(DONNEE_LOGIN, donnee.getLoginDonnee()) ;
        valeurs.put(DONNEE_PASSE, donnee.getPasseDonneeChiffre()) ;
        valeurs.put(DONNEE_QUESTION, donnee.getQuestionSecreteDonnee()) ;
        valeurs.put(DONNEE_MAIL, donnee.getMailDonnee()) ;
        valeurs.put(DONNEE_NOTE, donnee.getNoteDonnee()) ;
        valeurs.put(DONNEE_DATE_CREE, dateCreation) ;
        valeurs.put(DONNEE_DATE_MODIF, dateCreation) ;
        valeurs.put(DONNEE_CLE_CHIFFRE, donnee.getCleChiffrementDonnee()) ;
        valeurs.put(DONNEE_CATEGORIE, donnee.getCategorieDonnee()) ;
        valeurs.put(DONNEE_REPERTOIRE, repert) ;
        valeurs.put(DONNEE_WEB, donnee.getSiteWebDonnee()) ;
        laBase.insert(DONNEE_TABLE_NOM, null, valeurs) ;
    }


    /* supprimer une donnee */
    public void supprDonnee(int index) {
        String requete = "DELETE FROM " + DONNEE_TABLE_NOM +
                " WHERE " + DONNEE_KEY + " = '" + index + "'; ";
        this.laBase.execSQL(requete);
    }

    /* modifier une donnee */
    public void modifDonnee(Donnee laDonnee) {
        long dateModif = System.currentTimeMillis() ;
        String requete = "UPDATE " + DONNEE_TABLE_NOM + " SET "
                + DONNEE_NOM + " = '" + laDonnee.getNomDonnee() + "', "
                + DONNEE_LOGIN + " = '" + laDonnee.getLoginDonnee() + "', "
                + DONNEE_PASSE + " = '" + laDonnee.getPasseDonneeChiffre() + "', "
                + DONNEE_QUESTION + " = '" + laDonnee.getQuestionSecreteDonnee() + "', "
                + DONNEE_MAIL + " = '" + laDonnee.getMailDonnee() + "', "
                + DONNEE_NOTE + " = '" + laDonnee.getNoteDonnee() + "', "
                + DONNEE_DATE_MODIF + " = '" + dateModif + "', "
                + DONNEE_CATEGORIE + " = '" + laDonnee.getCategorieDonnee() + "', "
                + DONNEE_WEB + " = '" + laDonnee.getSiteWebDonnee() + "' "
                + " WHERE " + DONNEE_KEY + " = '" + laDonnee.getIdEnBaseLocale() + "' " ;
        this.laBase.execSQL(requete);
    }


    /* Cree une Donnee a partir d'un enregistrement */
    public Donnee extraitDonnee(int index) {
        String requete =  " SELECT * FROM " + DONNEE_TABLE_NOM + " WHERE " + DONNEE_KEY + " ='" + index + "' ;" ;
        Cursor res = laBase.rawQuery(requete, null );
        String nom, login, passe, question, mail, note, cle_chiffre, categ, web ;
        int key ;
        Donnee laDonnee = null ;
        if (res.getCount() == 1) {
            res.moveToNext() ;
            nom = res.getString(res.getColumnIndex(DONNEE_NOM));
            login = res.getString(res.getColumnIndex(DONNEE_LOGIN));
            passe = res.getString(res.getColumnIndex(DONNEE_PASSE));
            question = res.getString(res.getColumnIndex(DONNEE_QUESTION));
            mail = res.getString(res.getColumnIndex(DONNEE_MAIL));
            note = res.getString(res.getColumnIndex(DONNEE_NOTE));
            cle_chiffre = res.getString(res.getColumnIndex(DONNEE_CLE_CHIFFRE));
            categ = res.getString(res.getColumnIndex(DONNEE_CATEGORIE));
            web = res.getString(res.getColumnIndex(DONNEE_WEB));
            key = res.getInt(res.getColumnIndex(DONNEE_KEY));
            laDonnee = new Donnee(passe, nom, login, mail, web, question, categ, note, cle_chiffre, key) ;
        }
        res.close();
        return laDonnee ;
    }


    public ArrayList<Donnee> rechercheParMot(int idRep, String chaine) {
        ArrayList<Donnee> resultats = new ArrayList<>() ;

        String[] champs = { DONNEE_NOM  , DONNEE_QUESTION , DONNEE_NOTE } ;
        String mots[] = chaine.split(" ");

        String condition = "" ;
        String condWhere = " WHERE " + DONNEE_REPERTOIRE + " = '" + idRep + "' " ;
        for (int i = 0 ; i < champs.length ; i++) {
            for (int j = 0 ; j < mots.length ; j++) {
                condition += " UPPER(" + champs[i] + ") LIKE  UPPER ('%" + mots[j] + "%') OR" ;
            }
        }
        if(condition.length() > 0) {
            // boucle a genere un OR en trop
            condition = condition.substring(0, condition.length()- 3 ) ;
            condition = condWhere + " AND (" + condition + ")";
        } else {
            condition = condWhere ;
        }
        String req = "SELECT * FROM " + DONNEE_TABLE_NOM + condition ;

        Cursor result = laBase.rawQuery(req, null );
        if (result.getCount() > 0) {
            for (int i = 0; i < result.getCount(); i++) {
                result.moveToNext();
                resultats.add(extraitDonnee(result.getInt(result.getColumnIndex(DONNEE_KEY)))) ;
            }
        }
        result.close();
        return resultats ;
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


    /* ****************** */
    /* TABLE CATEGORIE */
    /* ****************** */
    public ArrayList<String> listerCateg() {
        ArrayList<String> lesCategories = new ArrayList<>() ;
        String requete = "SELECT " + CATEGORIE_NOM + " FROM " + CATEGORIE_TABLE_NOM ;
        Cursor res = laBase.rawQuery(requete, null );
        res.moveToFirst();
        while (!res.isAfterLast()) {
            lesCategories.add(res.getString(res.getColumnIndex(CATEGORIE_NOM)))  ;
            res.moveToNext();
        }
        res.close() ;
        return lesCategories ;
    }


    public void insertCategorie(String nomC) {
        ContentValues valeurs = new ContentValues() ;
        valeurs.put(CATEGORIE_NOM, nomC) ;
        laBase.insert(CATEGORIE_TABLE_NOM, null, valeurs) ;
    }

}