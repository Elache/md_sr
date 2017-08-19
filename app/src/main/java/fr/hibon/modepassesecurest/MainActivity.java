package fr.hibon.modepassesecurest;

import android.app.Activity;
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

import fr.hibon.modepassesecurest.ihm.outipasses.*;
import fr.hibon.modepassesecurest.ihm.compte.*;
import fr.hibon.modepassesecurest.motpasse.ChainePasse;

/**
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button boutonConnexion ;
    ImageButton outipasses_help, boutonCreation, boutonOutil ;

    EditText passeDispo ;
    String passeCado ;

    // TODO largeur d\'ecran et haute densite ??

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("MP&SR");

        boutonConnexion = (Button) findViewById(R.id.bouton_connexion) ;
        boutonConnexion.setOnClickListener(this);

        boutonCreation = (ImageButton) findViewById(R.id.bouton_creation) ;
        boutonCreation.setOnClickListener(this);

        boutonOutil = (ImageButton) findViewById(R.id.bouton_outils) ;
        boutonOutil.setOnClickListener(this);

        passeCado = ChainePasse.genererMotDePasse().getChaineDuPasse() ;
        passeDispo = (EditText) findViewById(R.id.passeDispo) ;
        passeDispo.setText(passeCado);
    }

    @Override
    public void onClick(View v) {
        Intent mIn = null ;

        switch(v.getId()) {
            case(R.id.bouton_outils):
                mIn = new Intent(MainActivity.this, Passes.class) ;
                mIn.putExtra("passeCado", passeCado) ;
                break ;

            case(R.id.bouton_connexion):
                EditText champPasse = (EditText)findViewById(R.id.saisie_Pass);
                String passeSaisi = champPasse.getText().toString();
                boolean passeValide = passeSaisi.equals("123456") ;
                if (passeValide)
                    mIn = new Intent(MainActivity.this, ConnecteAccueilInterface.class) ;
                else
                    mIn = new Intent(MainActivity.this, MainActivity.class);
                break ;

            case(R.id.bouton_creation):
                mIn = new Intent(MainActivity.this, CreationCompteInterface.class) ;
                break ;
        }
        startActivity(mIn) ;
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

            case R.id.menu_connexion:
                EditText champPasse = (EditText)findViewById(R.id.saisie_Pass);
                String passeSaisi = champPasse.getText().toString();
                boolean passeValide = passeSaisi.equals("123456") ;
                if (passeValide){
                    mIn = new Intent(MainActivity.this, ConnecteAccueilInterface.class) ;
                    return true;
                }
                else
                    mIn = new Intent(MainActivity.this, MainActivity.class);
                return false ;

            case R.id.menu_creation:
                mIn = new Intent(MainActivity.this, CreationCompteInterface.class) ;
                return true;

            case R.id.menu_outipasses:
                mIn = new Intent(MainActivity.this, Passes.class);
                return true;

            case R.id.menu_info:
                mIn = new Intent(MainActivity.this, Infos.class);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
