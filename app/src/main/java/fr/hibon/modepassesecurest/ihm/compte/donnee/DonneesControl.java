package fr.hibon.modepassesecurest.ihm.compte.donnee;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import fr.hibon.modepassesecurest.R;
import fr.hibon.modepassesecurest.compte.CompteUtilisateur;
import fr.hibon.modepassesecurest.compte.Donnee;
import fr.hibon.modepassesecurest.compte.Repertoire;
import fr.hibon.modepassesecurest.compte.bdd.ManipTables;
import fr.hibon.modepassesecurest.compte.utiles.ChiffeMode;

/**
 * Created by lohib on 26/08/2017.
 */

/**
 * Gere les affichages d'interface pour <BR>affichage d'une donnee selectionnee,<BR>modification
 */
public class DonneesControl {


        /** Creation d'une donnee - Dialog - Champs de saisie */
        public static void saisirDonnee(Context cont, int rep) {

            // Affichage Donnée - pop up
            final Dialog dialog = new Dialog(cont);
            final Context contxt = cont ;
            dialog.setContentView(R.layout.donnee_creation);
            final int indexRepert = rep  ;

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

            final EditText nomEdT, passEdT, pass2EdT, webEdT, urlEdT, loginEdT, mailEdT, questionEdT, noteEdT ;
            final Spinner catSpin ;
            final String[] categorie = new String[1];

            nomEdT = ((EditText) dialog.findViewById(R.id.c_donnee_nom)) ;
            passEdT =  ((EditText) dialog.findViewById(R.id.c_donnee_pass)) ;
            pass2EdT =  ((EditText) dialog.findViewById(R.id.c_donnee_pass2)) ;
            loginEdT = ((EditText) dialog.findViewById(R.id.c_donnee_id)) ;
            mailEdT = ((EditText) dialog.findViewById(R.id.c_donnee_mail)) ;
            questionEdT = ((EditText) dialog.findViewById(R.id.c_donnee_question)) ;
            noteEdT = ((EditText) dialog.findViewById(R.id.c_donnee_notes)) ;

            // TODO site web en tant que table distincte
            webEdT = ((EditText) dialog.findViewById(R.id.c_donnee_web)) ;
            urlEdT = ((EditText) dialog.findViewById(R.id.c_donnee_web_url)) ;
            urlEdT.setVisibility(View.INVISIBLE); // provisoire

            // TODO categorie en tant que table (jointures)
            /* Liste des categories : Spinner */
            ManipTables manip = ManipTables.accesBase(contxt) ;
            final ArrayList<String> categ = manip.listerCateg();
            manip.fermerLaBase();
            catSpin = (Spinner) dialog.findViewById(R.id.c_donnee_categorie) ;
            ArrayAdapter adapter = new ArrayAdapter(contxt, android.R.layout.simple_spinner_item, categ) ;
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            catSpin.setAdapter(adapter) ;
            catSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   categorie[0] = parent.getItemAtPosition(position).toString() ;
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    categorie[0] = "" ;
                }
            }) ;

            // boutons
            Button b_fermer = (Button) dialog.findViewById(R.id.b_fermerD) ;
            b_fermer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss() ;
                }
            }  );

            Button b_creer = (Button) dialog.findViewById(R.id.bouton_creer) ;
            b_creer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String nom, pass, pass2, web, url, login, mail, question, note, cat ;
                    boolean confirm = true ;
                    nom = nomEdT.getText().toString() ;
                    pass = passEdT.getText().toString() ;
                    pass2 = pass2EdT.getText().toString() ;
                    if(!pass.equals(pass2)) {
                        confirm = false ;
                    }

                    web = webEdT.getText().toString() ;
                    // url = urlEdT.getText().toString() ;
                    login = loginEdT.getText().toString() ;
                    mail = mailEdT.getText().toString() ;
                    question = questionEdT.getText().toString() ;
                    note = noteEdT.getText().toString() ;
                    cat = categorie[0] ;

                    Donnee donneeSaisie = new Donnee(pass, nom, login, mail, web, question, cat, note) ;

                    popCreeConfirm(contxt, donneeSaisie, indexRepert, confirm) ;
                }
            });

            dialog.show();
            dialog.getWindow().setAttributes(lp);

        }



        /** Afficher une donnee (Dialog)
         * <BR>Mot de passe en clair sur demande
         * <BR>Acces a la modification et la suppression
         * */
        public static void afficherDonnee(Context cont, Donnee donnee, int rep) {

            // Affichage Donnée - pop up
            final Dialog dialog = new Dialog(cont);
            dialog.setContentView(R.layout.donnee_consultation);

            final Context leCont = cont ;
            final Donnee laDonnee = donnee ;
            final ChiffeMode cm = new ChiffeMode() ;

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

            final TextView passeDechiff = (TextView) dialog.findViewById(R.id.donnee_pass) ;
            passeDechiff.setVisibility(View.INVISIBLE);

            ((TextView) dialog.findViewById(R.id.donnee_nom)).setText(donnee.getNomDonnee()) ;
            ((TextView) dialog.findViewById(R.id.donnee_notes)).setText(donnee.getNoteDonnee()) ;
            ((TextView) dialog.findViewById(R.id.donnee_id)).setText(donnee.getLoginDonnee()) ;
            ((TextView) dialog.findViewById(R.id.donnee_mail)).setText(donnee.getMailDonnee()) ;
            ((TextView) dialog.findViewById(R.id.donnee_question)).setText(donnee.getQuestionSecreteDonnee()) ;

            // TODO site web en tant que table distincte
            // Site_web : récuperer dans Table les nom et url correspondant à l'id
            ((TextView) dialog.findViewById(R.id.donnee_web)).setText(donnee.getSiteWebDonnee()) ;
            // ((TextView) dialog.findViewById(R.id.donnee_web_url)).setText("") ;
            dialog.findViewById(R.id.donnee_web_url).setVisibility(View.INVISIBLE);
            dialog.findViewById(R.id.donnee_web_url_label).setVisibility(View.INVISIBLE);

            // TODO categorie en tant que table
            // récuperer dans Table Categorie le nom correspondant à l'id
            ((TextView) dialog.findViewById(R.id.donnee_categorie)).setText(donnee.getCategorieDonnee()) ;

            TextView fxBout = (TextView) dialog.findViewById(R.id.donnee_decoder) ;

            fxBout.setClickable(true) ;
            fxBout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String passe = cm.dechiffrer(laDonnee.getPasseDonneeChiffre(), laDonnee.getCleChiffrementDonnee()) ;
                    passeDechiff.setVisibility(View.VISIBLE);
                    passeDechiff.setText(passe) ;

                }
            });

            // boutons
            Button b_fermer = (Button) dialog.findViewById(R.id.b_fermerD) ;
            b_fermer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss() ;
                }
            });
            Button b_modif = (Button) dialog.findViewById(R.id.b_modifD) ;
            b_modif.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss() ;
                    modifierDonnee(leCont, laDonnee) ;
                }
            });

            Button b_suppr = (Button) dialog.findViewById(R.id.b_supprD) ;
            b_suppr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss() ;
                    popSupprConfirm(leCont, laDonnee) ;
                }
            });
            dialog.show();
            dialog.getWindow().setAttributes(lp);

        }



        /** Modifier une donnee (Dialog) */
        public static void modifierDonnee(Context cont, Donnee donnee) {
            final Dialog dialog = new Dialog(cont);
            final Context leCont = cont ;
            final Donnee laDonnee = donnee ;
            final String[] categSelect = new String[1] ;
            dialog.setContentView(R.layout.donnee_modif);

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

            ChiffeMode cm = new ChiffeMode() ;
            String passe = cm.dechiffrer(laDonnee.getPasseDonneeChiffre(), laDonnee.getCleChiffrementDonnee()) ;
            ((TextView) dialog.findViewById(R.id.donnee_pass_modif)).setText(passe) ;

            ((TextView) dialog.findViewById(R.id.donnee_nom_modif)).setText(donnee.getNomDonnee()) ;
            ((TextView) dialog.findViewById(R.id.donnee_notes_modif)).setText(donnee.getNoteDonnee()) ;
            ((TextView) dialog.findViewById(R.id.donnee_id_modif)).setText(donnee.getLoginDonnee()) ;
            ((TextView) dialog.findViewById(R.id.donnee_mail_modif)).setText(donnee.getMailDonnee()) ;
            ((TextView) dialog.findViewById(R.id.donnee_question_modif)).setText(donnee.getQuestionSecreteDonnee()) ;

            // to do : Site_web : récuperer dans Table les nom et url correspondant à l'id
            ((TextView) dialog.findViewById(R.id.donnee_web_modif)).setText(donnee.getSiteWebDonnee()) ;
            // masque en attendant
            // ((TextView) dialog.findViewById(R.id.donnee_web_url)).setText("") ;
            dialog.findViewById(R.id.donnee_url_modif).setVisibility(View.INVISIBLE);
            dialog.findViewById(R.id.donnee_web_url_label).setVisibility(View.INVISIBLE);

            // to do : récuperer dans Table Categorie le nom correspondant à l'id
            Spinner spinCat = (Spinner) dialog.findViewById(R.id.donnee_categorie_modif) ;
            ManipTables manip = ManipTables.accesBase(leCont) ;
            final ArrayList<String> categ = manip.listerCateg();
            manip.fermerLaBase();
            ArrayAdapter adapter = new ArrayAdapter(leCont, android.R.layout.simple_spinner_item, categ) ;
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinCat.setAdapter(adapter) ;
            spinCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    categSelect[0] = parent.getItemAtPosition(position).toString() ;
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    categSelect[0] = "" ;
                }
            }) ;

            // boutons
            Button b_fermer = (Button) dialog.findViewById(R.id.b_fermerD) ;
            b_fermer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss() ;
                }
            });

            Button b_valid = (Button) dialog.findViewById(R.id.b_valider) ;
            b_valid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String nomSaisi =  ((TextView) dialog.findViewById(R.id.donnee_nom_modif)).getText().toString() ;
                    String siteWebSaisi = ((TextView) dialog.findViewById(R.id.donnee_web_modif)).getText().toString() ;
                    String loginSaisi = ((TextView) dialog.findViewById(R.id.donnee_id_modif)).getText().toString() ;
                    String mailSaisi = ((TextView) dialog.findViewById(R.id.donnee_mail_modif)).getText().toString() ;
                    String questionSaisi = ((TextView) dialog.findViewById(R.id.donnee_question_modif)).getText().toString() ;
                    String categorieSaisi = categSelect[0] ;
                    String noteSaisi = ((TextView) dialog.findViewById(R.id.donnee_notes_modif)).getText().toString() ;
                    String passeSaisi = ((TextView) dialog.findViewById(R.id.donnee_pass_modif)).getText().toString() ;
                    Donnee donneeSaisie = new Donnee(passeSaisi, nomSaisi, loginSaisi, mailSaisi, siteWebSaisi, questionSaisi, categorieSaisi, noteSaisi) ;
                    ManipTables manip = ManipTables.accesBase(leCont) ;
                    manip.extraitDonnee(laDonnee.getIdEnBaseLocale()) ;
                    manip.fermerLaBase();
                    popModifConfirm(leCont, laDonnee, donneeSaisie) ;
                }
            });

            dialog.show();
            dialog.getWindow().setAttributes(lp);
        }



        // pop_up OUI / NON pour creation
        // Creation effective : Objet + Base de donnees
        public static void popCreeConfirm(final Context context, Donnee saisie, int idReper, boolean confirm) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            final Donnee donneeSaisie = saisie ;
            final int idRepertInser = idReper ;
            builder.setTitle("Enregistrement");
            String message = "Confirmez-vous l'enregistrement ?" ;

            if(!confirm)
                message += "\n\nRemarque : passe et confirmation différent.\nSi vous validez, la première saisie sera conservée." ;

            builder.setMessage(message);

            builder.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    donneeSaisie.chiffrerMotPasse();

                    // ajout objet repertoire
                    Repertoire r = CompteUtilisateur.getCompteConnecte().trouverRepert(idRepertInser) ;
                    r.ajoutDonnee(donneeSaisie);

                    // ajout BDD
                    ManipTables manip = ManipTables.accesBase(context) ;
                    manip.insertDonnee(donneeSaisie, idRepertInser);
                    manip.fermerLaBase();
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


    // pop_up OUI / NON pour suppression
    // Suppression effective : Objet + Base de donnees
    public static void popSupprConfirm(final Context context, Donnee donnee) {
        final Donnee laDonnee = donnee ;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("La suppression est irréversible.");
        builder.setMessage("Voulez-vous supprimer cette donnée ?");

        builder.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // supprime de la base
                ManipTables manip = ManipTables.accesBase(context);
                manip.supprDonnee(laDonnee.getIdEnBaseLocale());
                manip.fermerLaBase();
                // supprime l'objet
                for (Repertoire rep : CompteUtilisateur.getCompteConnecte().getLesRepertoires()) {
                    for (Donnee donn : rep.getLesDonnees()) {
                        if(donn.getIdEnBaseLocale() == laDonnee.getIdEnBaseLocale())
                            rep.getLesDonnees().remove(donn);
                    }
                }
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


    // pop_up OUI / NON pour valider modifications
    // modification effective : Objet + Base de donnees
    public static void popModifConfirm(final Context context, Donnee laDonnee, final Donnee donneeSaisie) {
        final Donnee aModifier = laDonnee ;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("La modification est irréversible.");
        builder.setMessage("Voulez-vous enregistrer des modifications ?");

        builder.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                // modifie l'objet
                upLaDonnee(aModifier, donneeSaisie) ;

                // insere en base de donnees
                ManipTables manip = ManipTables.accesBase(context) ;
                manip.modifDonnee(aModifier) ;
                manip.fermerLaBase();
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


    // met a jour l'objet Donnee apres validation des modifications
    private static void upLaDonnee(Donnee laDonnee, Donnee donneeSaisie) {

        if(!laDonnee.getNomDonnee().equals(donneeSaisie.getNomDonnee()))
            laDonnee.setNomDonnee(donneeSaisie.getNomDonnee());

        if(!laDonnee.getSiteWebDonnee().equals(donneeSaisie.getSiteWebDonnee()))
            laDonnee.setSiteWebDonnee(donneeSaisie.getSiteWebDonnee());

        if(!laDonnee.getLoginDonnee().equals(donneeSaisie.getLoginDonnee()))
            laDonnee.setLoginDonnee(donneeSaisie.getLoginDonnee());

        if(!laDonnee.getMailDonnee().equals(donneeSaisie.getMailDonnee()))
            laDonnee.setMailDonnee(donneeSaisie.getMailDonnee());

        if(!laDonnee.getQuestionSecreteDonnee().equals(donneeSaisie.getQuestionSecreteDonnee()))
            laDonnee.setQuestionSecreteDonnee(donneeSaisie.getQuestionSecreteDonnee());

        if(!laDonnee.getCategorieDonnee().equals(donneeSaisie.getCategorieDonnee()))
            laDonnee.setCategorieDonnee(donneeSaisie.getCategorieDonnee());

        if(!laDonnee.getNoteDonnee().equals(donneeSaisie.getNoteDonnee()))
            laDonnee.setNoteDonnee(donneeSaisie.getNoteDonnee());

        ChiffeMode  cm = new ChiffeMode() ;
        String passeEnBase = cm.dechiffrer(laDonnee.getPasseDonneeChiffre(), laDonnee.getCleChiffrementDonnee()) ;
        if(!passeEnBase.equals(donneeSaisie.getPasseDonnee())) {
            laDonnee.setPasseDonnee(donneeSaisie.getPasseDonnee());
            laDonnee.chiffrerMotPasse(true);
        }
    }

}
