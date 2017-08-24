package fr.hibon.modepassesecurest.ihm;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import fr.hibon.modepassesecurest.R;
import fr.hibon.modepassesecurest.compte.CompteUtilisateur;
import fr.hibon.modepassesecurest.compte.Repertoire;
import fr.hibon.modepassesecurest.compte.bdd.GestionBaseLocale;
import fr.hibon.modepassesecurest.compte.bdd.ManipTables;
import fr.hibon.modepassesecurest.compte.bdd.table.Table_Compte_Utilisateur;
import fr.hibon.modepassesecurest.compte.bdd.table.Table_Repertoire;
import fr.hibon.modepassesecurest.compte.bdd.table.Table_Site_Web;
import fr.hibon.modepassesecurest.compte.exception.CompteException;
import fr.hibon.modepassesecurest.compte.utiles.ChiffeMode;
import fr.hibon.modepassesecurest.ihm.compte.GestionIHM;

/**
 * Created by lohib on 23/08/2017.
 */

public class Test extends AppCompatActivity  {

    TextView aff, aff2 ;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.infos);

        aff = (TextView) findViewById(R.id.info1) ;
        aff2 = (TextView) findViewById(R.id.info2) ;

        ManipTables manip ;
        String  requete ;

        manip = new ManipTables(this) ;

        CompteUtilisateur lUtilisateur = CompteUtilisateur.getCompteConnecte();
        try {
            lUtilisateur.renseigneLeCompte("Loic", "passe", "test", "@", "Note");
        } catch (CompteException e) {

        }

        for(int i = 3 ; i <= 15 ; i++)
            manip.supprimeRepertoire(i);
        for(int i = 2 ; i <= 19 ; i++)
            manip.supprimeUtilisateur(i);


        requete = "SELECT * FROM " + Table_Compte_Utilisateur.COMPTE_USER_TABLE_NOM ;
        Cursor c = manip.getLaBase().rawQuery(requete, null) ;
        if(c.getCount() > 0 ) {
            for (int i = 0; i < c.getCount()	; i++) {
                c.moveToNext() ;
                aff.append("\n" + c.getString(0) + " " + c.getString(1) + " " +  c.getString(2) ) ;
            }
        }
        c.close() ;

        requete = "SELECT * FROM " + Table_Repertoire.REPERTOIRE_TABLE_NOM;
        c = manip.getLaBase().rawQuery(requete, null) ;
        if(c.getCount() > 0 ) {
            for (int i = 0; i < c.getCount()	; i++) {
                c.moveToNext();
                aff2.append("\n" + c.getString(0) + " " + c.getString(1) + " User : " + c.getString(3));
            }
        }

        manip.fermerLaBase();
    }

}