package fr.hibon.modepassesecurest.compte.bdd.table;

import android.provider.BaseColumns;

/**
 * Created by lohib on 20/08/2017.
 */

public class Table_Categorie {


        public static final String CATEGORIE_TABLE_NOM = "Categorie" ;

        public static final String CATEGORIE_KEY = "id" ;
        public static final String CATEGORIE_NOM = "nom_cat" ;


    /* CREATION DE LA TABLE */
    public static final String CATEGORIE_TABLE_CREATE = "CREATE TABLE " +
            CATEGORIE_TABLE_NOM + "(" +
            CATEGORIE_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CATEGORIE_NOM + " TEXT ) ; " ;



}



