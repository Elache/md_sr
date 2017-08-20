package fr.hibon.modepassesecurest.compte.bdd.table;

/**
 * Created by lohib on 20/08/2017.
 */

public class Table_Repertoire {

    public static final String REPERTOIRE_TABLE_NOM = "Repertoire" ;

    public static final String REPERTOIRE_KEY = "id_repertoire" ;
    public static final String REPERTOIRE_NOM = "nom_repertoire" ;

    public static final String REPERTOIRE_DESCRIPTION = "description_repertoire" ;
    public static final String REPERTOIRE_UTILISATEUR_ASSOCIE = "id_u" ;
    
    public static final String REPERTOIRE_TABLE_CREATE = "CREATE TABLE" + REPERTOIRE_TABLE_NOM + " " +
            "(" + REPERTOIRE_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + REPERTOIRE_NOM +
            " TEXT, " + REPERTOIRE_DESCRIPTION + " TEXT, " + REPERTOIRE_UTILISATEUR_ASSOCIE +  " INTEGER NOT NULL ) ;" ;

}



