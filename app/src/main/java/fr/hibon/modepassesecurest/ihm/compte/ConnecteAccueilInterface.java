package fr.hibon.modepassesecurest.ihm.compte;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.ResultSet;
import java.util.List;

import fr.hibon.modepassesecurest.R;
import fr.hibon.modepassesecurest.compte.CompteUtilisateur;
import fr.hibon.modepassesecurest.compte.Repertoire;
import fr.hibon.modepassesecurest.compte.bdd.GestionBaseLocale;
import fr.hibon.modepassesecurest.compte.bdd.ManipTables;
import fr.hibon.modepassesecurest.compte.bdd.table.Table_Compte_Utilisateur;
import fr.hibon.modepassesecurest.compte.bdd.table.Table_Site_Web;
import fr.hibon.modepassesecurest.compte.exception.CompteException;
import fr.hibon.modepassesecurest.compte.utiles.ChiffeMode;
import fr.hibon.modepassesecurest.motpasse.ChainePasse;

public class ConnecteAccueilInterface extends Activity {

    CompteUtilisateur lUtilisateur ;

    TextView titre, user, donnee, titreUser, titreRep, titreDonnee  ;
    Spinner repert ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connecte);

        titre = (TextView) findViewById(R.id.connecte) ;
        user =  (TextView) findViewById(R.id.accueil_connecte_user) ;
        donnee = (TextView) findViewById(R.id.accueil_connecte_donnees) ;

        titreUser = (TextView) findViewById(R.id.titre_user) ;
        titreUser.setText("Utilisateur connecté");
        titreRep = (TextView) findViewById(R.id.titre_repert) ;
        titreRep.setText("Liste des répertoires");
        titreDonnee = (TextView) findViewById(R.id.titre_donnees) ;
        titreDonnee.setText("Mots de passe et données");

        lUtilisateur = CompteUtilisateur.getCompteConnecte() ;
        ManipTables manip ;

        boolean creation = getIntent().getBooleanExtra("creation", false) ;

        if(creation) {
            try {
                String nomSaisi = getIntent().getStringExtra("nom") ;
                String passe1Saisi = getIntent().getStringExtra("passe1") ;
                String passe2Saisi = getIntent().getStringExtra("passe2") ;
                String mailSaisi = getIntent().getStringExtra("mail") ;
                lUtilisateur.renseigneLeCompte(nomSaisi, passe1Saisi, passe2Saisi, mailSaisi,"") ;


                if(passe1Saisi != null)
                    lUtilisateur.chiffrerMotPasse(passe1Saisi, 1);
                if(passe2Saisi != null)
                    lUtilisateur.chiffrerMotPasse(passe2Saisi, 2);
                if(lUtilisateur.getPasseInternet() != null)
                    lUtilisateur.chiffrerMotPasse(lUtilisateur.getPasseInternet().getChaineDuPasse(), 3);

                manip = new ManipTables(this) ;
                manip.insertCompteUtilisateur(lUtilisateur.getNomUser(), lUtilisateur.getPasseUserChiffre().getChaineDuPasse(),
                        lUtilisateur.getPasseRecoursChiffre().getChaineDuPasse(), lUtilisateur.getMailContactUser(),
                        "", lUtilisateur.getCleChiffreUser()) ;

                /* Version 1 : mono-repertoire */
                String requete = "SELECT " + Table_Compte_Utilisateur.COMPTE_USER_KEY + " FROM "
                        + Table_Compte_Utilisateur.COMPTE_USER_TABLE_NOM
                        + " WHERE " + Table_Compte_Utilisateur.COMPTE_USER_NOM + " = '" + lUtilisateur.getNomUser() + "';" ;
                // String requete = "SELECT last_insert_rowid();" ;
                manip = new ManipTables(this) ;
                Cursor res = manip.getLaBase().rawQuery(requete, null) ;
                res.moveToNext() ;
                int idUser = res.getInt(0);
                res.close() ;
                Repertoire premierRep = new Repertoire("Mes codes", "Répertoire") ;
                lUtilisateur.ajoutRepert(premierRep);
                manip.inserRepertoire(premierRep.getNomRepertoire(), premierRep.getNoteRepertoire(), idUser);
                /*        */

                manip.fermerLaBase();

            } catch (CompteException e) {
                e.printStackTrace();
            }

        }

        user.append("Bienvenue, " + getIntent().getStringExtra("nom") );
        repert = (Spinner) findViewById(R.id.accueil_connecte_rep) ;
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, lUtilisateur.getLesRepertoires()) ;
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repert.setAdapter(adapter) ;
    }

}