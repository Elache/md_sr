package fr.hibon.modepassesecurest.compte.bdd.table;

/**
 * Classe correspondant à la table Categorie de la base de donnees
 * <BR>les champs ; la requete de creation
 */

public class Table_Categorie {


        public static final String CATEGORIE_TABLE_NOM = "Categorie" ;

        private static final String CATEGORIE_KEY = "id" ;
        public static final String CATEGORIE_NOM = "nom_cat" ;


    /** Requete CREATION DE LA TABLE */
    public static final String CATEGORIE_TABLE_CREATE = "CREATE TABLE " +
            CATEGORIE_TABLE_NOM + "(" +
            CATEGORIE_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CATEGORIE_NOM + " TEXT ) ; " ;



}



