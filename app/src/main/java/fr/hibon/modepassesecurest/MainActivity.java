package fr.hibon.modepassesecurest;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import fr.hibon.modepassesecurest.compte.CompteUtilisateur;
import fr.hibon.modepassesecurest.compte.bdd.ManipTables;
import fr.hibon.modepassesecurest.ihm.Test;
import fr.hibon.modepassesecurest.ihm.outipasses.*;
import fr.hibon.modepassesecurest.ihm.compte.*;
import fr.hibon.modepassesecurest.motpasse.ChainePasse;

/**
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button boutonConnexion, boutonConsulter ;
    ImageButton boutonCreation, boutonOutil ;

    EditText passeDispo, saisieIdentifiant, passeIdentifiant ;
    String passeCado ;

    ImageButton test ;

    // TODO largeur d\'ecran et haute densite ??

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("MP&SR");

        saisieIdentifiant = (EditText) findViewById(R.id.saisie_Identifiant) ;
        passeIdentifiant = (EditText) findViewById(R.id.saisie_Pass) ;

        boutonConnexion = (Button) findViewById(R.id.bouton_connexion) ;
        boutonConnexion.setOnClickListener(this);

        boutonCreation = (ImageButton) findViewById(R.id.bouton_creation) ;
        boutonCreation.setOnClickListener(this);

        boutonOutil = (ImageButton) findViewById(R.id.bouton_outils) ;
        boutonOutil.setOnClickListener(this);

        boutonConsulter = (Button) findViewById(R.id.bouton_consulter) ;
        boutonConsulter.setOnClickListener(this);

        passeCado = ChainePasse.genererMotDePasse().getChaineDuPasse() ;
        passeDispo = (EditText) findViewById(R.id.passeDispo) ;
        passeDispo.setText(passeCado);

        test = (ImageButton) findViewById(R.id.test_btn) ;
        test.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent mIn;

        switch(v.getId()) {

            case(R.id.bouton_consulter):
                if(CompteUtilisateur.getCompteConnecte().getNomUser() != null) {
                    mIn = new Intent(MainActivity.this, ConnecteAccueilInterface.class) ;
                    startActivity(mIn) ;
                    break ;
                }
                GestionIHM.popInfo(this, "Identification requise", "Vous n'êtes pas connecté");
                break ;

            case(R.id.test_btn):
                mIn = new Intent(MainActivity.this, Test.class) ;
                startActivity(mIn) ;
                break ;

            case(R.id.bouton_outils):
                mIn = new Intent(MainActivity.this, Passes.class) ;
                mIn.putExtra("passeCado", passeCado) ;
                startActivity(mIn) ;
                break ;

            case(R.id.bouton_connexion):
                String identifiant = saisieIdentifiant.getText().toString() ;
                String passe = passeIdentifiant.getText().toString() ;
                // TODO verifier saisie
                boolean existe = ManipTables.accesBase(this).verifierCompte(identifiant, passe) ;

                if (existe) {
                    mIn = new Intent(MainActivity.this, ConnecteAccueilInterface.class) ;
                    mIn.putExtra("creation", false) ;
                    mIn.putExtra("nom", identifiant) ;
                    mIn.putExtra("passe1", passe) ;
                    startActivity(mIn) ;
                } else {
                    saisieIdentifiant.setText("");
                    passeIdentifiant.setText("");
                    String titre = "Echec de connexion" ;
                    String message = "Compte non reconnu \n (identifiant et/ou mot de passe" ;
                    GestionIHM.popInfo(this, titre, message);
                }
                break ;

            case(R.id.bouton_creation):
                mIn = new Intent(MainActivity.this, CreationCompteInterface.class) ;
                startActivity(mIn) ;
                break ;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_accueil, menu);
        return true;
    }



    // TODO menu accueil
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent mIn ;

        switch (item.getItemId()){

            case R.id.menu_creation:
                mIn = new Intent(MainActivity.this, CreationCompteInterface.class) ;
                startActivity(mIn);
                return true;

            case R.id.menu_outipasses:
                mIn = new Intent(MainActivity.this, Passes.class);
                startActivity(mIn);
                return true;

            case R.id.menu_info:
                mIn = new Intent(MainActivity.this, Infos.class);
                startActivity(mIn);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
