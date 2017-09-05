package fr.hibon.modepassesecurest.compte.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import fr.hibon.modepassesecurest.compte.utiles.ChiffeMode;

import static fr.hibon.modepassesecurest.compte.bdd.table.Table_Compte_Utilisateur.* ;
import static fr.hibon.modepassesecurest.compte.bdd.table.Table_Donnee.*;
import static fr.hibon.modepassesecurest.compte.bdd.table.Table_Repertoire.*;
import static fr.hibon.modepassesecurest.compte.bdd.table.Table_Site_Web.*;
import static fr.hibon.modepassesecurest.compte.bdd.table.Table_Categorie.*;



/**
 * Gere la creation des tables et l'initialisation
 * <BR>(categories + jeu d'essai)
 */

public class GestionBaseLocale extends SQLiteOpenHelper {

    /* Nom de la base de donnees */
    private static final String DATABASE_NOM = "modepasse_securest.db";
    private static final int DATABASE_VERSION = 8;


        /* ** METHODES ********
        * ****************** */


    public GestionBaseLocale(Context context) {
        super(context, DATABASE_NOM, null, DATABASE_VERSION);
    }

    public GestionBaseLocale(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
    }

    /**
     * Creation des 5 tables
     * <BR>Initialisation des categories
     * <BR>En phase de développement : création d'un jeu d'essai
     * @param db Base de donnees
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(COMPTE_USER_TABLE_CREATE) ;
        db.execSQL(REPERTOIRE_TABLE_CREATE) ;
        db.execSQL(DONNEE_TABLE_CREATE);
        db.execSQL(CATEGORIE_TABLE_CREATE) ;
        db.execSQL(SITE_WEB_TABLE_CREATE);
        lesCategories(db);

        // jeu de tests
        jeuTest(db) ;
    }

    /**
     * Recree la base pour mise à jour de version
     * @param db base de donnees
     * @param oldVersion num ancienne version
     * @param newVersion num nouvelle version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DONNEE_TABLE_NOM + " ; ");
        db.execSQL("DROP TABLE IF EXISTS " + REPERTOIRE_TABLE_NOM + " ; " );
        db.execSQL("DROP TABLE IF EXISTS " + COMPTE_USER_TABLE_NOM + " ; " );
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORIE_TABLE_NOM + " ; " );
        db.execSQL("DROP TABLE IF EXISTS " + SITE_WEB_TABLE_NOM + " ;" );
        onCreate(db);
    }


    /** ** Categories : Initialisation de la table * */
    private final void lesCategories(SQLiteDatabase dBase) {
        String[] cat = {"Général", "Accès Internet et connexions", "E-mail", "Réseaux sociaux", "Sites web Loisirs", "Jeux", "Services web", "Codes confidentiels", "Autres"};
        String reqInsertPrefixe = "INSERT INTO " + CATEGORIE_TABLE_NOM + " (" + CATEGORIE_NOM + ") VALUES ('";
        String reqInsertSuffixe = "') ;";
        String requete ;
        for (int i = 0; i < cat.length; i++){
            requete = reqInsertPrefixe + cat[i] + reqInsertSuffixe;
            dBase.execSQL(requete);
        }
    }


    public static String getDatabaseNom() {
        return DATABASE_NOM ;
    }


    /** Genere un Jeu de tests */
    public static void jeuTest(SQLiteDatabase db) {

        // création d'un compte utilisateur    Loic // passe  et Loic // test pour acces

        ChiffeMode cm = new ChiffeMode();
        cm.chiffrer("test")  ;
        String cle = cm.getCleCode() ;
        String passeTestChiffre = cm.getPasseCode() ;
        cm.chiffrer("passe", cle) ;
        String passePasseChiffre = cm.getPasseCode() ;

        ContentValues valeurs = new ContentValues() ;
        valeurs.put(COMPTE_USER_NOM, "Loic");
        valeurs.put(COMPTE_USER_PASSE, passeTestChiffre);
        valeurs.put(COMPTE_USER_PASSE_RECOURS, passePasseChiffre);
        valeurs.put(COMPTE_USER_MAIL, "lohibon@free.fr");
        valeurs.put(COMPTE_USER_NOTE, "Utilisateur test");
        valeurs.put(COMPTE_USER_CLE_CHIFFRE, cle);
        db.insert(COMPTE_USER_TABLE_NOM, null, valeurs) ;


        // Creation 1er repertoire (automatique en IHM)
        valeurs = new ContentValues() ;
        valeurs.put(REPERTOIRE_NOM, "Mes codes" ) ;
        valeurs.put(REPERTOIRE_DESCRIPTION, "") ;
        valeurs.put(REPERTOIRE_UTILISATEUR_ASSOCIE, 1) ;
        db.insert(REPERTOIRE_TABLE_NOM, null, valeurs) ;


        // Creation d'un 2nd repertoire pour Utilisateur 1 (non accessible IHM)
        valeurs = new ContentValues() ;
        valeurs.put(REPERTOIRE_NOM, "Répertoire professionnel" ) ;
        valeurs.put(REPERTOIRE_DESCRIPTION, "") ;
        valeurs.put(REPERTOIRE_UTILISATEUR_ASSOCIE, 1) ;
        db.insert(REPERTOIRE_TABLE_NOM, null, valeurs) ;


        // Insertion de donnees dans le repertoire 1

        long dateCreation = System.currentTimeMillis() ;

        ChiffeMode chm = new ChiffeMode();

        chm.chiffrer("motPasseDonnee")  ;
        valeurs = new ContentValues() ;
        valeurs.put(DONNEE_NOM, "gmail") ;
        valeurs.put(DONNEE_LOGIN, "lohib") ;
        valeurs.put(DONNEE_PASSE, chm.getPasseCode()) ;
        valeurs.put(DONNEE_QUESTION, "1er chat") ;
        valeurs.put(DONNEE_MAIL, "mp@secours.fr") ;
        valeurs.put(DONNEE_NOTE, "voir mes 2 autres comptes") ;
        valeurs.put(DONNEE_DATE_CREE, dateCreation) ;
        valeurs.put(DONNEE_DATE_MODIF, dateCreation) ;
        valeurs.put(DONNEE_CLE_CHIFFRE, chm.getCleCode()) ;
        valeurs.put(DONNEE_CATEGORIE, "Webservices") ;
        valeurs.put(DONNEE_REPERTOIRE, 1) ;
        valeurs.put(DONNEE_WEB, "www.gmail.com") ;
        db.insert(DONNEE_TABLE_NOM, null, valeurs) ;


        chm = new ChiffeMode().chiffrer("passeMauvais") ;
        valeurs = new ContentValues() ;
        valeurs.put(DONNEE_NOM, "laposte") ;
        valeurs.put(DONNEE_LOGIN, "jo.alfrit") ;
        valeurs.put(DONNEE_PASSE, chm.getPasseCode()) ;
        valeurs.put(DONNEE_QUESTION, "") ;
        valeurs.put(DONNEE_MAIL, "mp@se.fr") ;
        valeurs.put(DONNEE_NOTE, "") ;
        valeurs.put(DONNEE_DATE_CREE, dateCreation) ;
        valeurs.put(DONNEE_DATE_MODIF, dateCreation) ;
        valeurs.put(DONNEE_CLE_CHIFFRE, chm.getCleCode()) ;
        valeurs.put(DONNEE_CATEGORIE, "E-mail") ;
        valeurs.put(DONNEE_REPERTOIRE, 1) ;
        valeurs.put(DONNEE_WEB, "www.lp.net") ;
        db.insert(DONNEE_TABLE_NOM, null, valeurs) ;


        chm = new ChiffeMode().chiffrer("d7svR43'Zé7") ;
        valeurs = new ContentValues() ;
        valeurs.put(DONNEE_NOM, "sfrBox") ;
        valeurs.put(DONNEE_LOGIN, "admin_moi") ;
        valeurs.put(DONNEE_PASSE, chm.getPasseCode()) ;
        valeurs.put(DONNEE_QUESTION, "Code recup : hibuse") ;
        valeurs.put(DONNEE_MAIL, "mail@sfr.fr") ;
        valeurs.put(DONNEE_NOTE, "") ;
        valeurs.put(DONNEE_DATE_CREE, dateCreation) ;
        valeurs.put(DONNEE_DATE_MODIF, dateCreation) ;
        valeurs.put(DONNEE_CLE_CHIFFRE, chm.getCleCode()) ;
        valeurs.put(DONNEE_CATEGORIE, "Accès Internet et connexions") ;
        valeurs.put(DONNEE_REPERTOIRE, 1) ;
        valeurs.put(DONNEE_WEB, "moncompte - sfr.sfr.fr") ;
        db.insert(DONNEE_TABLE_NOM, null, valeurs) ;


        // Insertion de donnees dans le repertoire 2
        chm = new ChiffeMode();
        chm.chiffrer("byPasse")  ;
        valeurs = new ContentValues() ;
        valeurs.put(DONNEE_NOM, "Entrée bât admin") ;
        valeurs.put(DONNEE_LOGIN, "") ;
        valeurs.put(DONNEE_PASSE, chm.getPasseCode()) ;
        valeurs.put(DONNEE_QUESTION, "dateNaissance") ;
        valeurs.put(DONNEE_MAIL, "") ;
        valeurs.put(DONNEE_NOTE, "754 Darling Street") ;
        valeurs.put(DONNEE_DATE_CREE, dateCreation) ;
        valeurs.put(DONNEE_DATE_MODIF, dateCreation) ;
        valeurs.put(DONNEE_CLE_CHIFFRE, chm.getCleCode()) ;
        valeurs.put(DONNEE_CATEGORIE, "Codes confidentiels") ;
        valeurs.put(DONNEE_REPERTOIRE, 2) ;
        valeurs.put(DONNEE_WEB, "www.secuAlarme.com") ;
        db.insert(DONNEE_TABLE_NOM, null, valeurs) ;


    }


}