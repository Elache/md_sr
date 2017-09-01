package fr.hibon.modepassesecurest.ihm;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;

import fr.hibon.modepassesecurest.R;
import fr.hibon.modepassesecurest.compte.CompteUtilisateur;
import fr.hibon.modepassesecurest.compte.bdd.GestionBaseLocale;
import fr.hibon.modepassesecurest.compte.bdd.table.Table_Categorie;
import fr.hibon.modepassesecurest.compte.bdd.table.Table_Compte_Utilisateur;
import fr.hibon.modepassesecurest.compte.bdd.table.Table_Donnee;
import fr.hibon.modepassesecurest.compte.bdd.ManipTables;
import fr.hibon.modepassesecurest.compte.utiles.ChiffeMode;
import fr.hibon.modepassesecurest.ihm.compte.GestionIHM;

import static fr.hibon.modepassesecurest.compte.bdd.table.Table_Categorie.CATEGORIE_NOM;
import static fr.hibon.modepassesecurest.compte.bdd.table.Table_Categorie.CATEGORIE_TABLE_NOM;
import static fr.hibon.modepassesecurest.compte.bdd.table.Table_Repertoire.REPERTOIRE_TABLE_NOM;


/**
 * Created by lohib on 23/08/2017.
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


/*
// création d'un compte utilisateur    loic // passe  et loic // test = acces

        ChiffeMode cm = new ChiffeMode();
        cm.chiffrer("test")  ;
        String cle = cm.getCleCode() ;
        String passeTestChiffre = cm.getPasseCode() ;
        cm.chiffrer("passe", cle) ;
        String passePasseChiffre = cm.getPasseCode() ;
        manip.insertCompteUtilisateur("Loic", passeTestChiffre, passePasseChiffre, "lohibon@free.fr", "Utilisateur test", cle);


// Creation 1er repertoire (automatique en IHM
        manip.inserRepertoire("Mes codes","",1) ;

// Creation d'un 2nd repertoire pour Utilisateur 1 (non accessible IHM)
        manip.inserRepertoire("Répertoire professionnel","",1) ;


// Insertion de donnees dans le repertoire

        ChiffeMode chm = new ChiffeMode();
        chm.chiffrer("motPasseDonnee")  ;
        manip.insertDonnee(chm.getPasseCode(), "gmail", "lohib", "mp@secours.fr",
                "www.gmail.com", "1er chat", "Webservices", "voir 2 autres comptes gmail", chm.getCleCode(), 1);

        chm = new ChiffeMode().chiffrer("passeMauvais") ;
        manip.insertDonnee(chm.getPasseCode(), "laposte", "jo.alfrit", "mp@se.fr",
                "www.lp.net", "", "E-mail", "", chm.getCleCode(), 1);


        chm = new ChiffeMode().chiffrer("d7svR43'Zé7") ;
        manip.insertDonnee(chm.getPasseCode(), "sfrBox", "admin_moi", "mail@sfr.fr",
                "moncompte-sfr.sfr.fr", "Code recup : hibuse", "Accès Internet et connexions", "", chm.getCleCode(), 1);


// Insertion de donnees dans le repertoire
        ChiffeMode cim = new ChiffeMode();
        cim.chiffrer("byPasse")  ;
        manip.insertDonnee(cim.getPasseCode(), "Entrée bât admin", "", "",
                "www.gmail.com", "1er chat", "Codes confidentiels", "754 Darling Street", cim.getCleCode(), 2);


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