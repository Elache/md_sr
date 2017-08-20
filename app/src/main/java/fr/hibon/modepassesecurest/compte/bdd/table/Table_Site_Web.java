package fr.hibon.modepassesecurest.compte.bdd.table;

/**
 * Created by lohib on 20/08/2017.
 */

public class Table_Site_Web {

    public static final String SITE_WEB_TABLE_NOM = "Site_web" ;

    public static final String SITE_WEB_KEY = "id_web" ;
    public static final String SITE_WEB_NOM = "nom_web" ;
    public static final String SITE_WEB_URL = "url_web" ;

    public static final String SITE_WEB_TABLE_CREATE = "CREATE TABLE" + SITE_WEB_TABLE_NOM + " " +
            "(" + SITE_WEB_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SITE_WEB_NOM +
            " TEXT, " + SITE_WEB_URL +  " TEXT ) ;" ;

}



