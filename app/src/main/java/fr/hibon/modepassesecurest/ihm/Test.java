package fr.hibon.modepassesecurest.ihm;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import fr.hibon.modepassesecurest.R;
import fr.hibon.modepassesecurest.compte.bdd.table.Table_Categorie;
import fr.hibon.modepassesecurest.compte.bdd.table.Table_Compte_Utilisateur;
import fr.hibon.modepassesecurest.compte.bdd.table.Table_Donnee;
import fr.hibon.modepassesecurest.compte.bdd.ManipTables;

import static fr.hibon.modepassesecurest.compte.bdd.table.Table_Repertoire.REPERTOIRE_TABLE_NOM;


/**
 * Gestion de l'export des donnees d'un utilisateur
 */

public class Test extends AppCompatActivity  {

    TextView aff, aff2, aff3, aff4 ;
    TextView tit1, tit2, tit3, tit4 ;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        aff = (TextView) findViewById(R.id.info1) ;
        aff2 = (TextView) findViewById(R.id.info2) ;
        aff3 = (TextView) findViewById(R.id.info3) ;
        aff4 = (TextView) findViewById(R.id.info4) ;
        tit1 = (TextView) findViewById(R.id.info_titre1) ;
        tit2 = (TextView) findViewById(R.id.info_titre2) ;
        tit3 = (TextView) findViewById(R.id.info_titre3) ;
        tit4 = (TextView) findViewById(R.id.info_titre4) ;

        ManipTables manip = new ManipTables(this)  ;
        String  requete ;
        Cursor c ;


/* *** *** CREATIONS *** *** */
/* *** *** CREATIONS *** *** */
/* *** *** CREATIONS *** *** */


/* Section de CREATION d'un SET DE DONNEES */
/*
        // création d'un compte utilisateur    Admin // passe1  et  passe2

                ChiffeMode cm = new ChiffeMode();
                cm.chiffrer("passe1")  ;
                String cle = cm.getCleCode() ;
                String passeTestChiffre = cm.getPasseCode() ;
                cm.chiffrer("passe2", cle) ;
                String passePasseChiffre = cm.getPasseCode() ;
                manip.insertCompteUtilisateur("Admin", passeTestChiffre, passePasseChiffre, "mail@freesbee.fr", "Utilisateur test", cle);

*/




/* VIDER les tables : Compte_Utilisateur, Repertoire et Donnee */
/*
        requete = "DELETE FROM " + Table_Compte_Utilisateur.COMPTE_USER_TABLE_NOM ;
        manip.getLaBase().execSQL(requete);
        requete =  "DELETE FROM " + Table_Repertoire.REPERTOIRE_TABLE_NOM ;
        manip.getLaBase().execSQL(requete);
        requete =  "DELETE FROM " + Table_Donnee.DONNEE_TABLE_NOM ;
        manip.getLaBase().execSQL(requete);
*/



/* *** *** AFFICHAGE *** *** */
/* *** *** AFFICHAGE *** *** */
/* *** *** AFFICHAGE *** *** */

// Lister les comptes-utilisateurs de la base
        tit1.setText("Utilisateurs :");
        requete = "SELECT * FROM " + Table_Compte_Utilisateur.COMPTE_USER_TABLE_NOM ;
        c = manip.getLaBase().rawQuery(requete, null) ;
        if(c.getCount() > 0 ) {
            for (int i = 0; i < c.getCount()	; i++) {
                c.moveToNext() ;
                aff.append(c.getString(0) + " " + c.getString(1) + " " +  c.getString(4) + "\n" ) ;
            }
        }
        c.close() ;



// Lister les repertoires de la base
        tit2.setText("Répertoires");
        requete = "SELECT * FROM " + REPERTOIRE_TABLE_NOM;
        c = manip.getLaBase().rawQuery(requete, null) ;
        if(c.getCount() > 0 ) {
            for (int i = 0; i < c.getCount()	; i++) {
                c.moveToNext();
                aff2.append(c.getString(0) + " " + c.getString(1) + " User : " + c.getString(3) + "\n" );
            }
        }
        c.close() ;



// Lister les données de la base
        tit3.setText("Données");
        requete = "SELECT * FROM " + Table_Donnee.DONNEE_TABLE_NOM;
        c = manip.getLaBase().rawQuery(requete, null) ;
        if(c.getCount() > 0 ) {
            for (int i = 0; i < c.getCount()	; i++) {
                c.moveToNext();
                aff3.append(c.getString(0) + " " + c.getString(1) + " idRepert : " + c.getString(11) + "\n" );
            }
        }
        c.close() ;



// Lister les categories de la base
        tit4.setText("Catégories");
        requete = "SELECT * FROM " + Table_Categorie.CATEGORIE_TABLE_NOM ;
        c = manip.getLaBase().rawQuery(requete, null) ;
        if(c.getCount() > 0 ) {
            for (int i = 0; i < c.getCount() ; i++) {
                c.moveToNext();
                aff4.append(c.getString(0));
                aff4.append(" " + c.getString(1));
                aff4.append("\n") ;
            }
        }
        c.close();


        manip.fermerLaBase();
    }


}