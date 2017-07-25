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

/**
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button boutonConnexion ;
    ImageButton outipasses_help, boutonCreation, boutonGenerator, boutonAnalysor  ;

    int largeurEcran ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("MP&SR");
        largeurEcran =largeurEcran(this) ;

        boutonConnexion = (Button) findViewById(R.id.bouton_connexion) ;
        boutonConnexion.setOnClickListener(this);

        boutonCreation = (ImageButton) findViewById(R.id.bouton_creation) ;
        boutonCreation.setOnClickListener(this);

        boutonGenerator = (ImageButton) findViewById(R.id.bouton_generator) ;
        boutonGenerator.setOnClickListener(this);

        boutonAnalysor =(ImageButton) findViewById(R.id.bouton_analysor) ;
        boutonAnalysor.setOnClickListener(this);

        outipasses_help = (ImageButton) findViewById(R.id.bouton_outipasses_help);
        outipasses_help.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent mIn = null ;

        switch(v.getId()) {

            case R.id.bouton_generator:
                if(largeurEcran > 959) {
                    mIn = new Intent(MainActivity.this, Passes.class);
                }
                else {
                    mIn = new Intent(MainActivity.this, Generator.class);
                }
                break;

            case R.id.bouton_analysor:
                if(largeurEcran > 959) {
                    mIn = new Intent(MainActivity.this, Passes.class);
                }
                else {
                    mIn = new Intent(MainActivity.this, Analysor.class);
                }
                break;

            case(R.id.bouton_outipasses_help):
                mIn = new Intent(MainActivity.this, Infos.class) ;
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


    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent mIn = null  ;
        int largeurEcran = largeurEcran(this) ;

        switch (item.getItemId()){
            case R.id.connexionMenu:
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
            case R.id.creationMenu:
                mIn = new Intent(MainActivity.this, CreationCompteInterface.class) ;
                return true;
            case R.id.generatorMenu:
                if(largeurEcran > 959) {
                    mIn = new Intent(MainActivity.this, Passes.class);
                    return true;
                }
                else {
                    mIn = new Intent(MainActivity.this, Generator.class);
                    return true;
                }
            case R.id.analysorMenu:
                if(largeurEcran > 959) {
                    mIn = new Intent(MainActivity.this, Passes.class);
                    return true;
                    }
                    else {
                        mIn = new Intent(MainActivity.this, Analysor.class);
                        return true;
                    }
                }

        return super.onOptionsItemSelected(item);
    }

*/
    public int largeurEcran(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }
}
