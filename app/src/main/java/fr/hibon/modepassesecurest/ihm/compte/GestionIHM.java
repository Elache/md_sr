package fr.hibon.modepassesecurest.ihm.compte;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import fr.hibon.modepassesecurest.R;
import fr.hibon.modepassesecurest.compte.CompteUtilisateur;
import fr.hibon.modepassesecurest.compte.Donnee;
import fr.hibon.modepassesecurest.compte.Repertoire;
import fr.hibon.modepassesecurest.compte.bdd.GestionBaseLocale;
import fr.hibon.modepassesecurest.compte.bdd.ManipTables;
import fr.hibon.modepassesecurest.compte.bdd.table.Table_Compte_Utilisateur;
import fr.hibon.modepassesecurest.compte.bdd.table.Table_Repertoire;
import fr.hibon.modepassesecurest.compte.bdd.table.Table_Site_Web;
import fr.hibon.modepassesecurest.compte.exception.CompteException;
import fr.hibon.modepassesecurest.compte.utiles.ChiffeMode;

import static java.security.AccessController.getContext;

/**
 * Created by lohib on 19/08/2017.
 */

public class GestionIHM {

    ManipTables baseTables;

    String user_nom, user_pass1, user_pass1_confirmer, user_pass2, user_pass2_confirmer, user_mail;
    boolean enLigne;

    public GestionIHM() {

    }

    public GestionIHM(String user_nom, String user_pass1, String user_pass1_confirmer, String user_pass2, String user_pass2_confirmer, String user_mail, boolean enLigne) {
        this.user_nom = user_nom;
        this.user_pass1 = user_pass1;
        this.user_pass1_confirmer = user_pass1_confirmer;
        this.user_pass2 = user_pass2;
        this.user_pass2_confirmer = user_pass2_confirmer;
        this.user_mail = user_mail;
        this.enLigne = enLigne;
    }


    /* ***** */
    /* GESTION DE LA CREATION */
    /* ***** */


    public String verifier() {
        String avertiss = "";
        boolean confirm = true;
        if (!verifNom() && enLigne) {
            avertiss += "! Un nom aussi court ne sera pas adapté à l'utilisation de la consultation web.\n";
        }
        if (!verifConcordePasse(user_pass1, user_pass1_confirmer)) {
            avertiss += "! \"Passe 1\" et confirmation différents \n";
            confirm = false;
        }
        if (!verifConcordePasse(user_pass2, user_pass2_confirmer)) {
            avertiss += "! \"Passe de recours\" et confirmation différents \n";
            confirm = false;
        }
        if (!CompteUtilisateur.getCompteConnecte().passeInternetAcceptable(user_pass1, user_pass2) && enLigne) {
            avertiss += "! Ces mots de passe, trop simples, ne vous permettront pas d'accéder à l'interrogation en ligne de la base de données. " +
                    "\n (la sauvegarde sur serveur reste possible).";
        }
        if (avertiss.length() > 0) {
            avertiss += "\n Vous pouvez modifier vos saisies ou bien les valider sans tenir compte de cet avertissement.";
            if (!confirm)
                avertiss += "\n (passe retenu même sans confirmation)";
        }

        return avertiss;
    }

    /* Instancie et Insert en Base */
    public void instanceInsert(Context context) {
        CompteUtilisateur lUtilisateur = CompteUtilisateur.getCompteConnecte();
        String unPasse = "";

        if (lUtilisateur.getPasseUser() != null) {
            unPasse = lUtilisateur.getPasseUser().getChaineDuPasse();
            lUtilisateur.chiffrerMotPasse(unPasse, 1);
        }
        if (lUtilisateur.getPasseRecours() != null) {
            unPasse = lUtilisateur.getPasseRecours().getChaineDuPasse();
            lUtilisateur.chiffrerMotPasse(unPasse, 2);
        }
        if (lUtilisateur.getPasseInternet() != null) {
            unPasse = lUtilisateur.getPasseInternet().getChaineDuPasse();
            lUtilisateur.chiffrerMotPasse(unPasse, 3);
        }

        ManipTables manip = new ManipTables(context);
        manip.insertCompteUtilisateur(lUtilisateur.getNomUser(), lUtilisateur.getPasseUserChiffre().getChaineDuPasse(),
                lUtilisateur.getPasseRecoursChiffre().getChaineDuPasse(), lUtilisateur.getMailContactUser(),
                "", lUtilisateur.getCleChiffreUser());

        /* Version 1 : mono-repertoire */
        String requete = "SELECT " + Table_Compte_Utilisateur.COMPTE_USER_KEY + " FROM "
                + Table_Compte_Utilisateur.COMPTE_USER_TABLE_NOM
                + " WHERE " + Table_Compte_Utilisateur.COMPTE_USER_NOM + " = '" + lUtilisateur.getNomUser() + "';";

        manip = new ManipTables(context);
        Cursor res = manip.getLaBase().rawQuery(requete, null);
        res.moveToNext();
        int idUser = res.getInt(0);
        res.close();

        String req = "SELECT max(" + Table_Repertoire.REPERTOIRE_KEY + ") FROM " + Table_Repertoire.REPERTOIRE_TABLE_NOM + ";" ;
        Cursor result = manip.getLaBase().rawQuery(req, null);
        result.moveToFirst();
        int idRep = result.getInt(0) ;
        Repertoire premierRep = new Repertoire("Mes codes", "Répertoire", idRep);
        lUtilisateur.ajoutRepert(premierRep);
        manip.inserRepertoire(premierRep.getNomRepertoire(), premierRep.getNoteRepertoire(), idUser);

        manip.fermerLaBase();
    }


    private boolean verifNom() {
        // TODO verif du nom a lier a internet
        if (user_nom.length() < 4)
            return false;
        return true;
    }


    private boolean verifConcordePasse(String pass, String pass_confirm) {
        if (!pass.equals(pass_confirm))
            return false;
        return true;
    }



    /* ***** */
    /* GESTION DE DONNEES */
    /* ***** */

    public static ArrayList<Donnee> rechercheDonneeParNom(Context c, int idRep, String recherche) {
        return new ManipTables(c).rechercheParMot(idRep, recherche) ;
    }


    /* ***** */
    /* OUTIL */
    /* ***** */

    // POP-UP
    public static void popInfo(Context context, String titre, String message) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.en_ligne_pop);
        dialog.setTitle("Informations");
        TextView title = (TextView) dialog.findViewById(R.id.info_en_ligne);
        title.setText(titre);
        TextView textInfo = (TextView) dialog.findViewById(R.id.infoenligne);
        textInfo.setText(message);
        Button dialogButton = (Button) dialog.findViewById(R.id.close_popUP);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    // pop_up OUI / NON pour deconnexion
    public static void pop2Choix(Context context, String titre, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titre);
        builder.setMessage(message);

        builder.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                CompteUtilisateur.getCompteConnecte().deconnecter() ;
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("NON", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


}