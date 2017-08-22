package fr.hibon.modepassesecurest.ihm.compte;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.sql.ResultSet;

import fr.hibon.modepassesecurest.R;
import fr.hibon.modepassesecurest.compte.CompteUtilisateur;
import fr.hibon.modepassesecurest.compte.bdd.GestionBaseLocale;
import fr.hibon.modepassesecurest.compte.bdd.ManipTables;
import fr.hibon.modepassesecurest.compte.bdd.table.Table_Site_Web;
import fr.hibon.modepassesecurest.compte.exception.CompteException;

public class ConnecteAccueilInterface extends Activity {

    CompteUtilisateur lUtilisateur ;

    TextView affichage ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connecte);

        affichage = (TextView) findViewById(R.id.accueil_connecte) ;

        lUtilisateur = CompteUtilisateur.getCompteConnecte() ;
        try {
            lUtilisateur.renseigneLeCompte(getIntent().getStringExtra("nom"), getIntent().getStringExtra("passe1"), getIntent().getStringExtra("passe2"), getIntent().getStringExtra("mail"), null) ;
        } catch (CompteException e) {
            e.printStackTrace();
        }

        ManipTables manip = new ManipTables(this) ;

       // manip.insertSiteWeb("Le Site", "www.go.prix");
       // manip.insertSiteWeb("Web", "www.3ZE.prix") ;

        String[] tab = {Table_Site_Web.SITE_WEB_NOM,Table_Site_Web.SITE_WEB_URL} ;

  //      for(int i = 12 ; i < 24 ; i++)
            manip.supprimeSiteWeb(5) ;

        Cursor res = manip.getLaBase().rawQuery("SELECT * FROM " + Table_Site_Web.SITE_WEB_TABLE_NOM, null );


        res.moveToFirst();

        while (res.isAfterLast() == false) {
            affichage.append(res.getString(0) + res.getString(1) + " : " + res.getString(2) + "\n" );
            res.moveToNext();
        }



        affichage.append("\n" + res.getCount() );
        res.close() ;
        manip.fermerLaBase();



    }

}