package fr.hibon.modepassesecurest.compte.bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import fr.hibon.modepassesecurest.compte.bdd.table.Table_Categorie;
import fr.hibon.modepassesecurest.compte.bdd.table.Table_Compte_Utilisateur;
import fr.hibon.modepassesecurest.compte.bdd.table.Table_Donnee;
import fr.hibon.modepassesecurest.compte.bdd.table.Table_Repertoire;
import fr.hibon.modepassesecurest.compte.bdd.table.Table_Site_Web;

/**
 * Created by lohib on 20/08/2017.
 */

public class GestionBaseLocale extends SQLiteOpenHelper {

    /* Nom de la base de donnees */
    public static final String DATABASE_NOM = "modepasse_securest.db";
    public static final int DATABASE_VERSION = 3;


    /* ** CREATION DES TABLES - REQUETES ********
    * ****************** */

    /* Table Categorie */
    public static final String CATEGORIE_TABLE_CREATE = Table_Categorie.CATEGORIE_TABLE_CREATE;

    /* Table Compte_Utilisateur */
    public static final String COMPTE_USER_TABLE_CREATE = Table_Compte_Utilisateur.COMPTE_USER_TABLE_CREATE;

    /* Table Donnee */
    public static final String DONNEE_TABLE_CREATE = Table_Donnee.DONNEE_TABLE_CREATE;

    /* Table Site_web */
    public static final String SITE_WEB_TABLE_CREATE = Table_Site_Web.SITE_WEB_TABLE_CREATE;

    /* Table Repertoire */
    public static final String REPERTOIRE_TABLE_CREATE = Table_Repertoire.REPERTOIRE_TABLE_CREATE;



       /* ** METHODES ********
        * ****************** */


    public GestionBaseLocale(Context context) {
        super(context, DATABASE_NOM, null, DATABASE_VERSION);
    }

    public GestionBaseLocale(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(COMPTE_USER_TABLE_CREATE) ;
        db.execSQL(REPERTOIRE_TABLE_CREATE) ;
        db.execSQL(DONNEE_TABLE_CREATE);
        db.execSQL(CATEGORIE_TABLE_CREATE) ;
        db.execSQL(SITE_WEB_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_Donnee.DONNEE_TABLE_NOM + " ; ");
        db.execSQL("DROP TABLE IF EXISTS " + Table_Repertoire.REPERTOIRE_TABLE_NOM + " ; " );
        db.execSQL("DROP TABLE IF EXISTS " + Table_Compte_Utilisateur.COMPTE_USER_TABLE_NOM + " ; " );
        db.execSQL("DROP TABLE IF EXISTS " + Table_Categorie.CATEGORIE_TABLE_NOM + " ; " );
        db.execSQL("DROP TABLE IF EXISTS " + Table_Site_Web.SITE_WEB_TABLE_NOM + " ;" );
        onCreate(db);
    }

}