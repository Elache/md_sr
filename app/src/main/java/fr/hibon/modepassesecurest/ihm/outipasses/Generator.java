package fr.hibon.modepassesecurest.ihm.outipasses;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fr.hibon.modepassesecurest.R;

/**
 * Crée l'activité "Génération de mot de passe"
 * <BR>et son aide
 */

public class Generator extends AppCompatActivity  {



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.generator);
        setTitle("MP&SR");

    }

    public static String infoGeneration() {

        String infos = "L'utilisation du paramétrage est facultative." ;

        infos += "\n" + "Par défaut, le mot de passe généré comporte 10 caractères, tous les types listés pouvant en faire partie" ;

        infos += "\n" + "\n" + "Remarque : d'une façon générale, la qualité d'un mot de passe est plus particulièrement liée à sa longueur." ;

        infos += "\n" + "\n" + "PARAMETRES : Caractéristiques du mot de passe" ;
        infos += "\n" + "- " + "Longueur : nombre de caractères (10 par défaut)" ;
        infos += "\n" + "- " + "Types de caractères : cochés = utilisables" ;
        infos += "\n" + "- " + "Exclure : lister des caractères interdits" ;
        infos += "\n" + "- " + "Inclure : lister des caractères possibles" ;

        return infos ;
    }

}
