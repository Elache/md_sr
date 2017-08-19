package fr.hibon.modepassesecurest.ihm.compte;

import android.widget.EditText;

import fr.hibon.modepassesecurest.R;

/**
 * Created by lohib on 19/08/2017.
 */

public class GestionIHM {

    String user_nom, user_pass1, user_pass1_confirmer, user_pass2, user_pass2_confirmer ;
    String avertissement ;

    public GestionIHM(String user_nom, String user_pass1, String user_pass1_confirmer, String user_pass2, String user_pass2_confirmer) {
        this.user_nom = user_nom;
        this.user_pass1 = user_pass1;
        this.user_pass1_confirmer = user_pass1_confirmer;
        this.user_pass2 = user_pass2;
        this.user_pass2_confirmer = user_pass2_confirmer;
        this.avertissement = "" ;
    }

    static boolean verifierCompte(String user_nom, String user_pass1, String user_pass1_confirmer, String user_pass2, String user_pass2_confirmer, boolean enLigne) {

        // TODO compte en ligne attentes
        return true ;
    }


    private String avertNom(){
        if(user_nom.length() == 0)
            return "Avertissement sur l'identifiant" ;
        return "";
    }

    private String nonConcordePasse(String pass, String pass_confirm, int i){
        if(!pass.equals(pass_confirm))
            return "Confirmation non identique pour le passe " + i ;
        return "" ;
    }







}
