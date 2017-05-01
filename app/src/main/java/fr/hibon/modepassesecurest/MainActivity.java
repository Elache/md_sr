package fr.hibon.modepassesecurest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import fr.hibon.modepassesecurest.outipasses.*;
import fr.hibon.modepassesecurest.utilisateur.*;

/**
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    Button boutonConnexion, boutonCreation, boutonGenerator, boutonAnalysor ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boutonConnexion = (Button) findViewById(R.id.connexion) ;
        boutonCreation = (Button) findViewById(R.id.creation) ;

        boutonGenerator = (Button) findViewById(R.id.generator) ;
        boutonAnalysor =(Button) findViewById(R.id.analysor) ;

        boutonAnalysor.setOnClickListener(this);
        boutonGenerator.setOnClickListener(this);
        boutonConnexion.setOnClickListener(this);
        boutonCreation.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        Intent mIn = null  ;

        switch(v.getId()) {

            case(R.id.analysor):
                mIn = new Intent(MainActivity.this, Analysor.class) ;
                break ;
            case(R.id.generator):
                mIn = new Intent(MainActivity.this, Generator.class) ;
                break ;
            case(R.id.connexion):

                EditText champPasse = (EditText)findViewById(R.id.saisiePass);
                String passeSaisi = champPasse.getText().toString();
                boolean passeValide = passeSaisi.equals("123456") ;
                if (passeValide)
                    mIn = new Intent(MainActivity.this, Connecte.class) ;
                else
                    mIn = new Intent(MainActivity.this, MainActivity.class);
                break ;

            case(R.id.creation):
                mIn = new Intent(MainActivity.this, Creation.class) ;
                break ;
        }
        startActivity(mIn) ;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_accueil, menu);
        return true;
    }

    //gère le click sur une action de l'ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent mIn = null  ;

        switch (item.getItemId()){
            case R.id.connexionMenu:
                EditText champPasse = (EditText)findViewById(R.id.saisiePass);
                String passeSaisi = champPasse.getText().toString();
                boolean passeValide = passeSaisi.equals("123456") ;
                if (passeValide){
                    mIn = new Intent(MainActivity.this, Connecte.class) ;
                    return true;
                }
                else
                    mIn = new Intent(MainActivity.this, MainActivity.class);
                return false ;
            case R.id.creationMenu:
                mIn = new Intent(MainActivity.this, Creation.class) ;
                return true;
            case R.id.generatorMenu:
                mIn = new Intent(MainActivity.this, Generator.class) ;
                return true;
            case R.id.analysorMenu:
                mIn = new Intent(MainActivity.this, Analysor.class) ;
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}