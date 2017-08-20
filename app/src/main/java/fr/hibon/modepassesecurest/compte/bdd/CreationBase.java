package fr.hibon.modepassesecurest.compte.bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import fr.hibon.modepassesecurest.compte.Donnee;
import fr.hibon.modepassesecurest.compte.bdd.table.Table_Categorie;
import fr.hibon.modepassesecurest.compte.bdd.table.Table_Compte_Utilisateur;
import fr.hibon.modepassesecurest.compte.bdd.table.Table_Donnee;
import fr.hibon.modepassesecurest.compte.bdd.table.Table_Repertoire;
import fr.hibon.modepassesecurest.compte.bdd.table.Table_Site_Web;

/**
 * Created by lohib on 20/08/2017.
 */

public class CreationBase extends SQLiteOpenHelper {

    /* Nom de la base de donnees */
    public static final String DATABASE_NOM = "modepasse_securest" ;
    public static final int DATABASE_VERSION = 1 ;

    protected static CreationBase instanceDeLaBase ;

    /* ** REQUETES ********
    * ****************** */

    /* Création des 5 tables */
    public static final String CREATE_CINQ_TABLES =
            Table_Compte_Utilisateur.COMPTE_USER_TABLE_CREATE +
            Table_Repertoire.REPERTOIRE_TABLE_CREATE +
            Table_Donnee.DONNEE_TABLE_CREATE +
            Table_Categorie.CATEGORIE_TABLE_CREATE +
            Table_Site_Web.SITE_WEB_TABLE_CREATE ;

    /* DROP des 5 tables */

    public static final String DROP_CINQ_TABLES =
            "DROP TABLE IF EXISTS " + Table_Donnee.DONNEE_TABLE_NOM + " ; " +
            "DROP TABLE IF EXISTS " + Table_Repertoire.REPERTOIRE_TABLE_NOM + " ; " +
            "DROP TABLE IF EXISTS " + Table_Compte_Utilisateur.COMPTE_USER_TABLE_NOM + " ; " +
            "DROP TABLE IF EXISTS " + Table_Categorie.CATEGORIE_TABLE_NOM + " ; " +
            "DROP TABLE IF EXISTS " + Table_Site_Web.SITE_WEB_TABLE_NOM + " ;" ;

    /* CONTRAINTES DE CLES ETRANGERS */

    public static final String CONTRAINTE_DONNEE_CATEGORIE =
        "ALTER TABLE" + Table_Donnee.DONNEE_TABLE_NOM + " ADD CONSTRAINT FK_"
                + Table_Donnee.DONNEE_TABLE_NOM + "_"
                + Table_Categorie.CATEGORIE_TABLE_NOM + "FOREIGN KEY (id_cat) REFERENCES"
                + Table_Categorie.CATEGORIE_TABLE_NOM + "(id_cat); " ;

    public static final String CONTRAINTE_DONNEE_REPERTOIRE =
            "ALTER TABLE" + Table_Donnee.DONNEE_TABLE_NOM + " ADD CONSTRAINT FK_"
                + Table_Donnee.DONNEE_TABLE_NOM + "_"
                + Table_Repertoire.REPERTOIRE_TABLE_NOM + "FOREIGN KEY (id_repertoire) REFERENCES"
                + Table_Repertoire.REPERTOIRE_TABLE_NOM + "(id_repertoire); " ;

    public static final String CONTRAINTE_DONNEE_WEB =
            "ALTER TABLE" + Table_Donnee.DONNEE_TABLE_NOM + " ADD CONSTRAINT FK_"
                    + Table_Donnee.DONNEE_TABLE_NOM + "_"
                    + Table_Site_Web.SITE_WEB_TABLE_NOM + "FOREIGN KEY (id_web) REFERENCES"
                    + Table_Site_Web.SITE_WEB_TABLE_NOM + "(id_web); " ;

    public static final String CONTRAINTE_UTILISATEUR_REPERTOIRE =
            "ALTER TABLE" + Table_Compte_Utilisateur.COMPTE_USER_TABLE_NOM + " ADD CONSTRAINT FK_"
                    + Table_Compte_Utilisateur.COMPTE_USER_TABLE_NOM + "_"
                    + Table_Repertoire.REPERTOIRE_TABLE_NOM + "FOREIGN KEY (id_u) REFERENCES"
                    + Table_Repertoire.REPERTOIRE_TABLE_NOM + "(id_u); " ;

    /* Les 4 contraintes */
    public static final String CONTRAINTES = CONTRAINTE_DONNEE_CATEGORIE +
            CONTRAINTE_DONNEE_REPERTOIRE + CONTRAINTE_DONNEE_WEB + CONTRAINTE_UTILISATEUR_REPERTOIRE ;


       /* ** METHODES ********
        * ****************** */

    public static synchronized CreationBase getInstance(Context context) {
        if(instanceDeLaBase == null) {
            instanceDeLaBase = new CreationBase(context) ;
        }
        return instanceDeLaBase ;
    }

    public CreationBase(Context context) {
        super(context, DATABASE_NOM, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CINQ_TABLES + CONTRAINTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_CINQ_TABLES)  ;
        onCreate(db);
    }


}





