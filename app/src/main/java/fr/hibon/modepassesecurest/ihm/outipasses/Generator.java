package fr.hibon.modepassesecurest.ihm.outipasses;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import fr.hibon.modepassesecurest.R;
import fr.hibon.modepassesecurest.motpasse.ChainePasse;
import fr.hibon.modepassesecurest.motpasse.MotDePasseCotation;

/**
 *
 */

public class Generator extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnGenere ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.generator);
        setTitle("MP&SR");

        btnGenere = (ImageButton) findViewById(R.id.bouton_generator) ;
        btnGenere.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        Intent mIn ;

        /* LONGUEUR SELECTIONNEE */
        EditText nbCaractSaisi = (EditText) findViewById(R.id.saisie_longueur) ;
        int longueur = longueurChoisie(nbCaractSaisi) ;


        /* CARACTERES */
        boolean chiffres, minuscules, majuscules, accents, speciaux ;
        CheckBox chiff, min, maj, acc, sp ;

        chiff = (CheckBox) findViewById(R.id.chk_chiffres);
        min = (CheckBox) findViewById(R.id.chk_minusc);
        maj = (CheckBox) findViewById(R.id.chk_majusc);
        acc = (CheckBox) findViewById(R.id.chk_accent);
        sp = (CheckBox) findViewById(R.id.chk_speciaux);

        chiffres = chiff.isChecked() ;
        minuscules = min.isChecked() ;
        majuscules = maj.isChecked() ;
        accents = acc.isChecked() ;
        speciaux = sp.isChecked() ;


        /* INCLUSIONS, EXCLUSIONS */
        EditText inclu, exclu ;
        String inclure, exclure ;
        ArrayList<Character> inclusions, exclusions ;

        inclu = (EditText)findViewById(R.id.inclus_saisie);
        exclu = (EditText)findViewById(R.id.exclus_saisie);
        inclure = inclu.getText().toString();
        exclure = exclu.getText().toString();
        inclusions = etablirListe(inclure) ;
        exclusions = etablirListe(exclure) ;

        /* Affichage MOT GENERE */
        EditText champ = (EditText) findViewById(R.id.saisie_aTester) ;
        ChainePasse chaine = ChainePasse.genererMotDePasse(longueur, chiffres, minuscules, majuscules,accents,speciaux, exclusions, inclusions);
        champ.setText(chaine.getChaineDuPasse());

        /* Affichage ANALYSE */
        MotDePasseCotation lePasse = MotDePasseCotation.analyser(chaine);

        TextView affichAnalyse = (TextView) findViewById(R.id.info_analyse) ;
        affichAnalyse.setText(lePasse.getQualifieAnalyse() + " (" + lePasse.getForceBits() + "bits )") ;


        mIn = new Intent(Generator.this, Analysor.class);

        startActivity(mIn) ;
    }



     /* OUTILS : recuperation de la longueur */
    private int longueurChoisie(EditText nb) {
        String nbCaractLu = nb.getText().toString() ;
        return Integer.parseInt(nbCaractLu) ;
    }


    /* OUTILS : crée liste d'exclusions ou d'inclusions */
    private ArrayList<Character> etablirListe(String chaine){
        if(chaine == null || chaine.length() == 0)
            return null ;

        ArrayList<Character> liste = new ArrayList<>() ;
        for (char c : chaine.toCharArray()) {
            if(c != ' ')
                liste.add(c);
        }
        return liste ;
    }

}
