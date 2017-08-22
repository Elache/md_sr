package fr.hibon.modepassesecurest.compte.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import fr.hibon.modepassesecurest.compte.bdd.table.Table_Site_Web;

/**
 * Created by lohib on 22/08/2017.
 */

public class ManipTables {

    private SQLiteDatabase laBase ;
    private GestionBaseLocale outilBase ;

    public ManipTables(Context c) throws SQLException {
        this.outilBase = new GestionBaseLocale(c);
        try {
            laBase = outilBase.getWritableDatabase();
        } catch (SQLiteException ex) {
            laBase = outilBase.getReadableDatabase();
        }
    }

    public void fermerLaBase() {
        outilBase.close();
    }

    public SQLiteDatabase getLaBase() {
        return laBase;
    }

    public GestionBaseLocale getOutilBase() {
        return outilBase;
    }

    /* Insert SITE_WEB */
    public void insertSiteWeb(String nom, String url) {
        ContentValues valeurs = new ContentValues() ;
        valeurs.put(Table_Site_Web.SITE_WEB_NOM, nom) ;
        valeurs.put(Table_Site_Web.SITE_WEB_URL, url) ;
        laBase.insert(Table_Site_Web.SITE_WEB_TABLE_NOM, null, valeurs) ;
    }

    public void supprimeSiteWeb(int index) {
        String requete = "DELETE FROM " + Table_Site_Web.SITE_WEB_NOM + " WHERE '" + Table_Site_Web.SITE_WEB_KEY + "' = '" + index + "'" ;
        laBase.execSQL(requete);
    }


}