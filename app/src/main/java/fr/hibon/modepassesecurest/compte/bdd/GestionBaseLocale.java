package fr.hibon.modepassesecurest.compte.bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static fr.hibon.modepassesecurest.compte.bdd.table.Table_Categorie.*;
import static fr.hibon.modepassesecurest.compte.bdd.table.Table_Compte_Utilisateur.* ;
import static fr.hibon.modepassesecurest.compte.bdd.table.Table_Donnee.*;
import static fr.hibon.modepassesecurest.compte.bdd.table.Table_Repertoire.*;
import static fr.hibon.modepassesecurest.compte.bdd.table.Table_Site_Web.*;
import static fr.hibon.modepassesecurest.compte.bdd.table.Table_Categorie.*;



/**
 * Created by lohib on 20/08/2017.
 */

public class GestionBaseLocale extends SQLiteOpenHelper {

    /* Nom de la base de donnees */
    private static final String DATABASE_NOM = "modepasse_securest.db";
    private static final int DATABASE_VERSION = 4;


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
        db.execSQL(lesCategories());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DONNEE_TABLE_NOM + " ; ");
        db.execSQL("DROP TABLE IF EXISTS " + REPERTOIRE_TABLE_NOM + " ; " );
        db.execSQL("DROP TABLE IF EXISTS " + COMPTE_USER_TABLE_NOM + " ; " );
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORIE_TABLE_NOM + " ; " );
        db.execSQL("DROP TABLE IF EXISTS " + SITE_WEB_TABLE_NOM + " ;" );
        onCreate(db);
    }


    /* ** CATEGORIE : INITIALISATION TABLE * */
    private static final String lesCategories() {
        String[] cat = {"Général", "Accès Internet et connexions", "E-mail", "Réseaux sociaux", "Sites web Loisirs", "Jeux", "Services web", "Codes confidentiels", "Autres"};
        String reqInsertPrefixe = "INSERT INTO " + CATEGORIE_TABLE_NOM + " (" + CATEGORIE_NOM + ") VALUES ('";
        String reqInsertSuffixe = "') ;";
        String requete = "";
        for (int i = 0; i < cat.length; i++)
            requete += reqInsertPrefixe + cat[i] + reqInsertSuffixe;
        return requete;
    }
}