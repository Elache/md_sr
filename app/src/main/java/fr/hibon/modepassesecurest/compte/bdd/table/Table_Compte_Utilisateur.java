package fr.hibon.modepassesecurest.compte.bdd.table;

/**
 *Classe correspondant à la table Compte_utilisateur de la base de donnees
 * <BR>les champs ; la requete de creation
 */

public class Table_Compte_Utilisateur  {

        public static final String COMPTE_USER_TABLE_NOM = "Compte_Utilisateur";
    
        public static final String COMPTE_USER_KEY = "id";
        public static final String COMPTE_USER_NOM = "nom_u";
        public static final String COMPTE_USER_PASSE = "mot_passe_u";
        public static final String COMPTE_USER_PASSE_RECOURS = "mot_passe_recours_u";
        public static final String COMPTE_USER_MAIL = "mail_contact_u";
        public static final String COMPTE_USER_NOTE = "note_u";
        public static final String COMPTE_USER_CLE_CHIFFRE = "cle_chiffre_u";


    /** Requete CREATION DE LA TABLE */
    public static final String COMPTE_USER_TABLE_CREATE = "CREATE TABLE " + COMPTE_USER_TABLE_NOM + " " +
            "(" + COMPTE_USER_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COMPTE_USER_NOM +
            " TEXT UNIQUE, " + COMPTE_USER_PASSE + " TEXT, " + COMPTE_USER_PASSE_RECOURS + " TEXT, " + COMPTE_USER_MAIL + " TEXT, " +
            COMPTE_USER_NOTE + " TEXT, " + COMPTE_USER_CLE_CHIFFRE + " TEXT ) ; " ;


    
}



