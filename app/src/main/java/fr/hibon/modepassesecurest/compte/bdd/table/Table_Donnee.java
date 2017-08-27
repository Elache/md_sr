package fr.hibon.modepassesecurest.compte.bdd.table;

/**
 * Created by lohib on 20/08/2017.
 */

public class Table_Donnee {

        /* Table: Donnee */
        public static final String DONNEE_TABLE_NOM = "Donnee";

        public static final String DONNEE_KEY = "id";
        public static final String DONNEE_NOM = "nom_donnee";
        public static final String DONNEE_LOGIN = "login_donnee";
        public static final String DONNEE_PASSE = "passe_donnee";
        public static final String DONNEE_QUESTION = "question_secrete_donnee";
        public static final String DONNEE_MAIL = "mail_donnee";
        public static final String DONNEE_NOTE = "note_donnee";
        public static final String DONNEE_DATE_CREE = "date_creation_donnee";
        public static final String DONNEE_DATE_MODIF = "date_derniere_modif_donnee";
        public static final String DONNEE_CLE_CHIFFRE = "cle_chiffre_donnee";
        public static final String DONNEE_CATEGORIE = "id_cat";
        public static final String DONNEE_REPERTOIRE = "id_repertoire";
        public static final String DONNEE_WEB = "id_web";


    /* CREATION DE LA TABLE */
    public static final String DONNEE_TABLE_CREATE = "CREATE TABLE " + DONNEE_TABLE_NOM + " " +
            "(" + DONNEE_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DONNEE_NOM +
            " TEXT, " + DONNEE_LOGIN + " TEXT, " + DONNEE_PASSE + " TEXT, " + DONNEE_QUESTION + " TEXT, " +
            DONNEE_MAIL + " TEXT, " + DONNEE_NOTE + " TEXT, " + DONNEE_DATE_CREE + " REAL NOT NULL, " +
            DONNEE_DATE_MODIF + " REAL ," + DONNEE_CLE_CHIFFRE + " TEXT, " +
            DONNEE_CATEGORIE + " INTEGER CONSTRAINT FK_" + DONNEE_TABLE_NOM + "_" + Table_Categorie.CATEGORIE_TABLE_NOM + " REFERENCES " +  Table_Categorie.CATEGORIE_TABLE_NOM + "(id)," +
            DONNEE_REPERTOIRE + " INTEGER NOT NULL CONSTRAINT FK_" + DONNEE_TABLE_NOM + "_" + Table_Repertoire.REPERTOIRE_TABLE_NOM + " REFERENCES " +  Table_Repertoire.REPERTOIRE_TABLE_NOM + "(id), " +
            DONNEE_WEB + " INTEGER CONSTRAINT FK_" + DONNEE_TABLE_NOM + "_" + Table_Site_Web.SITE_WEB_TABLE_NOM + " REFERENCES " +  Table_Site_Web.SITE_WEB_TABLE_NOM + "(id) ) ; " ;


}



