package fr.hibon.modepassesecurest;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import fr.hibon.modepassesecurest.compte.bdd.ManipTables;
import fr.hibon.modepassesecurest.ihm.Test;
import fr.hibon.modepassesecurest.ihm.outipasses.*;
import fr.hibon.modepassesecurest.ihm.compte.*;
import fr.hibon.modepassesecurest.motpasse.ChainePasse;

/**
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button boutonConnexion ;
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

        passeCado = ChainePasse.genererMotDePasse().getChaineDuPasse() ;
        passeDispo = (EditText) findViewById(R.id.passeDispo) ;
        passeDispo.setText(passeCado);

        test = (ImageButton) findViewById(R.id.test_btn) ;
        test.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent mIn = null ;

        switch(v.getId()) {

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
                boolean existe = new ManipTables(this).verifierCompte(identifiant, passe) ;

                if (existe) {
                    mIn = new Intent(MainActivity.this, ConnecteAccueilInterface.class) ;
                    mIn.putExtra("creation", false) ;
                    mIn.putExtra("nom", identifiant) ;
                    mIn.putExtra("passe1", passe) ;
                    startActivity(mIn) ;
                } else {
                    saisieIdentifiant.setText("");
                    passeIdentifiant.setText("");
                    final Dialog dialog = new Dialog(this);
                    dialog.setContentView(R.layout.en_ligne_pop);
                    TextView titre = (TextView) dialog.findViewById(R.id.info_en_ligne);
                    titre.setText("Echec de connexion");
                    TextView textInfo = (TextView) dialog.findViewById(R.id.infoenligne);
                    textInfo.setText("Compte non reconnu");
                    Button dialogButton = (Button) dialog.findViewById(R.id.close_popUP);
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
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
