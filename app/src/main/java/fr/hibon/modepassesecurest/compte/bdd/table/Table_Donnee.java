package fr.hibon.modepassesecurest.compte.bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lohib on 20/08/2017.
 */

public class CreationBase extends SQLiteOpenHelper {



    /* Table: Donnee */
    public static final String DONNEE_TABLE_NOM = "Donnee" ;

    public static final String DONNEE_KEY = "id_donnee" ;
    public static final String DONNEE_NOM = "nom_donnee" ;
    public static final String DONNEE_LOGIN = "login_donnee" ;
    public static final String DONNEE_PASSE = "passe_donnee" ;
    public static final String DONNEE_QUESTION = "question_secrete_donnee" ;
    public static final String DONNEE_MAIL = "mail_donnee" ;
    public static final String DONNEE_NOTE = "note_donnee" ;
    public static final String DONNEE_DATE_CREE = "date_creation_donnee" ;
    public static final String DONNEE_DATE_MODIF = "date_derniere_modif_donnee" ;
    public static final String DONNEE_CLE_CHIFFRE = "cle_chiffre_donnee" ;
    public static final String DONNEE_CATEGORIE = "id_cat" ;
    public static final String DONNEE_REPERTOIRE = "id_repertoire" ;
    public static final String DONNEE_WEB = "id_web" ;


    public static final String DONNEE_TABLE_CREATE = "CREATE TABLE" + DONNEE_TABLE_NOM + " " +
            "(" + DONNEE_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DONNEE_NOM + " TEXT, " + DONNEE_LOGIN + " TEXT, " + DONNEE_PASSE + " TEXT, " + DONNEE_QUESTION + " TEXT, " + DONNEE_MAIL + " TEXT, " + DONNEE_NOTE + " TEXT, " + DONNEE_DATE_CREE + " REAL NOT NULL, " + DONNEE_DATE_MODIF + " REAL ," + DONNEE_CLE_CHIFFRE + " TEXT, " + DONNEE_CATEGORIE + " INTEGER, " + DONNEE_REPERTOIRE + " INTEGER NOT NULL, " + DONNEE_WEB + " INTEGER ) ;" ;

    // //////////////////////////////////
    public CreationBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}



