package fr.hibon.modepassesecurest.compte.bdd.table;

/**
 * Classe correspondant à la table Repertoire de la base de donnees
 * <BR>les champs ; la requete de creation
 */

public class Table_Repertoire {
    
        public static final String REPERTOIRE_TABLE_NOM = "Repertoire" ;

        public static final String REPERTOIRE_KEY = "id" ;
        public static final String REPERTOIRE_NOM = "nom_repertoire" ;
        public static final String REPERTOIRE_DESCRIPTION = "description_repertoire" ;
        public static final String REPERTOIRE_UTILISATEUR_ASSOCIE = "id_u" ;


    
    /** requete CREATION DE LA TABLE */
    public static final String REPERTOIRE_TABLE_CREATE = "CREATE TABLE " + REPERTOIRE_TABLE_NOM + " (" + REPERTOIRE_KEY +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + REPERTOIRE_NOM +
            " TEXT, " + REPERTOIRE_DESCRIPTION + " TEXT, " + REPERTOIRE_UTILISATEUR_ASSOCIE +
            " INTEGER NOT NULL CONSTRAINT FK_" + REPERTOIRE_TABLE_NOM + "_" + Table_Compte_Utilisateur.COMPTE_USER_TABLE_NOM + " REFERENCES "
            +  Table_Compte_Utilisateur.COMPTE_USER_TABLE_NOM + "(id)) ; " ;

}



