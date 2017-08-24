package fr.hibon.modepassesecurest.ihm.compte;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import fr.hibon.modepassesecurest.R;
import fr.hibon.modepassesecurest.compte.CompteUtilisateur;
import fr.hibon.modepassesecurest.compte.Repertoire;
import fr.hibon.modepassesecurest.compte.bdd.GestionBaseLocale;
import fr.hibon.modepassesecurest.compte.bdd.ManipTables;
import fr.hibon.modepassesecurest.compte.bdd.table.Table_Compte_Utilisateur;
import fr.hibon.modepassesecurest.compte.bdd.table.Table_Site_Web;
import fr.hibon.modepassesecurest.compte.exception.CompteException;
import fr.hibon.modepassesecurest.compte.utiles.ChiffeMode;

/**
 * Created by lohib on 19/08/2017.
 */

public class GestionIHM {

    ManipTables baseTables ;

    String user_nom, user_pass1, user_pass1_confirmer, user_pass2, user_pass2_confirmer, user_mail ;
    boolean enLigne ;

    public GestionIHM() {

    }
    public GestionIHM(String user_nom, String user_pass1, String user_pass1_confirmer, String user_pass2, String user_pass2_confirmer, String user_mail, boolean enLigne) {
        this.user_nom = user_nom;
        this.user_pass1 = user_pass1;
        this.user_pass1_confirmer = user_pass1_confirmer;
        this.user_pass2 = user_pass2;
        this.user_pass2_confirmer = user_pass2_confirmer;
        this.user_mail = user_mail ;
        this.enLigne = enLigne ;
    }


    /* ***** */
    /* GESTION DE LA CREATION */
    /* ***** */


    public String verifier() {
        String avertiss = "" ;
        boolean confirm = true ;
        if(!verifNom() && enLigne) {
            avertiss += "Un nom vide ne permettra pas d'utiliser un compte en ligne." ;
        }
        if (!verifConcordePasse(user_pass1, user_pass1_confirmer)) {
            avertiss += "! \"Passe 1\" et confirmation différents \n" ;
            confirm = false ;
        }
        if (!verifConcordePasse(user_pass2, user_pass2_confirmer)){
            avertiss += "! \"Passe de recours\" et confirmation différents \n" ;
            confirm = false ;
        }
        if(!CompteUtilisateur.getCompteConnecte().passeInternetAcceptable(user_pass1,user_pass2) && enLigne) {
            avertiss += "! Ces mots de passe, trop simples, ne vous permettront pas d'accéder à l'interrogation en ligne de la base de données. " +
                    "\n (la sauvegarde sur serveur reste possible)." ;
        }

        if(avertiss.length() > 0) {
            avertiss += "\n Vous pouvez modifier vos saisies ou bien les valider sans tenir compte de cet avertissement." ;
            if(!confirm)
                avertiss += "\n (passe retenu même sans confirmation)" ;
        }

        return avertiss ;
    }

    // TODO verif du nom a lier a internet

    private boolean verifNom(){
        if(user_nom.length() == 0)
            return false ;
        return true ;
    }

    private boolean verifConcordePasse(String pass, String pass_confirm){
        if(!pass.equals(pass_confirm))
            return false ;
        return true ;
    }



}
