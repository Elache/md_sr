package fr.hibon.modepassesecurest.ihm;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import fr.hibon.modepassesecurest.R;
import fr.hibon.modepassesecurest.compte.bdd.table.Table_Donnee;
import fr.hibon.modepassesecurest.compte.bdd.ManipTables;

/**
 * Created by lohib on 23/08/2017.
 */

public class Test extends AppCompatActivity  {

    TextView titre, aff, aff2 ;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.infos);

        aff = (TextView) findViewById(R.id.info1) ;
        aff2 = (TextView) findViewById(R.id.info2) ;
        titre = (TextView) findViewById(R.id.info_titre1) ;

        ManipTables manip ;
        String  requete ;

        Cursor c ;

        /*
        requete = "SELECT * FROM " + Table_Compte_Utilisateur.COMPTE_USER_TABLE_NOM ;
        c = manip.getLaBase().rawQuery(requete, null) ;
        if(c.getCount() > 0 ) {
            for (int i = 0; i < c.getCount()	; i++) {
                c.moveToNext() ;
                aff.append("\n" + c.getString(0) + " " + c.getString(1) + " " +  c.getString(2) ) ;
            }
        }
        c.close() ;
        */

        /*
        requete = "SELECT * FROM " + Table_Repertoire.REPERTOIRE_TABLE_NOM;
        c = manip.getLaBase().rawQuery(requete, null) ;
        if(c.getCount() > 0 ) {
            for (int i = 0; i < c.getCount()	; i++) {
                c.moveToNext();
                aff2.append("\n" + c.getString(0) + " " + c.getString(1) + " User : " + c.getString(3));
            }
        }
        c.close() ;
        */

        manip =  new ManipTables(this) ;

        requete = "SELECT * FROM " + Table_Donnee.DONNEE_TABLE_NOM ;
        c = manip.getLaBase().rawQuery(requete, null) ;
        if(c.getCount() > 0 ) {
            for (int i = 0; i < c.getCount() ; i++) {
                c.moveToNext();
                aff2.append("\n") ;
                aff2.append(c.getString(0).toString());
                aff2.append(" " + c.getString(1).toString());
                aff2.append(" " + c.getString(c.getColumnIndex(Table_Donnee.DONNEE_REPERTOIRE)).toString());
            }
        }
        aff.append(c.getCount()+ "") ;
        c.close();
        manip.fermerLaBase();
    }

}