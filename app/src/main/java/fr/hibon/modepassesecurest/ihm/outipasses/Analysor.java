package fr.hibon.modepassesecurest.ihm.outipasses;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fr.hibon.modepassesecurest.R;

/**
 * Crée l'activité "Analyse de mot de passe"
 * <BR>et son teste d'aide
 */

public class Analysor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analysor);
    }

    /**
     * Texte d'information sur l'analyse
     * @return chaine texte d'information sur analyse
     */
    public static String infoAnalyse() {

        String infos = "L'analyse utilise 2 facteurs :" ;
        infos += "\n" + "- la longueur du mot de passe" ;
        infos += "\n" + "- la variété des types de caractères utilisés" ;
        infos += "\n" + "\n" + "avec 5 types : chiffres, lettres minuscules, majuscules et accentuées, caractères spéciaux" ;

        infos += "\n" + "\n" + "Pour être qualifié de \"bon\", un mot de passe devra comporter au moins 10 caractères..." ;
        infos += "\n" + "ou plus si tous les types ne sont pas représentés" ;

        infos += "\n" + "\n" + "A partir de 28 caractères, le niveau est \"Bon\" même si les 10 chiffres sont uniquement utilisés." ;
        return infos ;
    }


}
