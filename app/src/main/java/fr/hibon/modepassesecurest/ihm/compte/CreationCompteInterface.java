package fr.hibon.modepassesecurest.ihm.compte;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import fr.hibon.modepassesecurest.R;
import fr.hibon.modepassesecurest.compte.CompteUtilisateur;
import fr.hibon.modepassesecurest.compte.bdd.ManipTables;
import fr.hibon.modepassesecurest.motpasse.ChainePasse;

/**
 *
 */

public class CreationCompteInterface extends AppCompatActivity implements View.OnClickListener {

    EditText nom, pass1, pass1confirm, pass2, pass2confirm, mail ;
    String user_nom, user_pass1, user_pass1_confirmer, user_pass2, user_pass2_confirmer, user_mail ;
    Button bouton_creation ;
    boolean enLigne ;
    ImageButton info_internet ;
    boolean userInforme ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compte_creation);

        userInforme = false ;

        nom = ((EditText) findViewById(R.id.user_nom)) ;
        nom.setText("Moi-" + ChainePasse.genererMotDePasse(4, true, false,false,false,false,null, null).getChaineDuPasse());

        String passe = ChainePasse.genererMotDePasse().getChaineDuPasse() ;
        pass1 = ((EditText) findViewById(R.id.user_pass1)) ;
        pass1.setText(passe);
        pass1confirm = ((EditText) findViewById(R.id.user_pass1_confirm)) ;

        String passeRecours = ChainePasse.genererMotDePasse(4).getChaineDuPasse() ;
        pass2 = ((EditText) findViewById(R.id.user_pass2)) ;
        pass2.setText(passeRecours) ;
        pass2confirm = ((EditText) findViewById(R.id.user_pass2_confirm)) ;

        mail = ((EditText) findViewById(R.id.user_mail_saisi)) ;

        enLigne = false ;

        info_internet = (ImageButton) findViewById(R.id.bouton_enligne_help) ;
        info_internet.setOnClickListener(this);

        bouton_creation = (Button) findViewById(R.id.bouton_creation) ;
        bouton_creation.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        String titre;
        String erreurs;
        String message = "";

        switch(v.getId()) {
            case R.id.bouton_enligne_help :
                titre = getString(R.string.mettre_en_ligne) ;
                message = getString(R.string.sauver_en_ligne) ;
                GestionIHM.popInfo(this, titre, message);
                break ;

            case R.id.bouton_creation :
                // TODO option : en ligne
                if(CompteUtilisateur.getCompteConnecte().getNomUser() != null){
                    titre = "Utilisateur déjà connecté" ;
                    message = "Voulez-vous vous déconnecter et créer un nouveau compte ?" ;
                    GestionIHM.pop2Choix(this, titre, message);
                    break ; //
                }

                Intent mIn ;
                user_nom = nom.getText().toString() ;
                user_pass1 = pass1.getText().toString() ;
                user_pass1_confirmer = pass1confirm.getText().toString() ;
                user_pass2 = pass2.getText().toString() ;
                user_pass2_confirmer  = pass2confirm.getText().toString() ;
                user_mail = mail.getText().toString() ;
                enLigne = ((CheckBox) findViewById(R.id.mettre_en_ligne)).isChecked() ;

                String avertiss = new GestionIHM(user_nom,user_pass1,user_pass1_confirmer,user_pass2, user_pass2_confirmer, user_mail, enLigne).verifier() ;

                ManipTables manip = ManipTables.accesBase(this) ;
                erreurs = manip.erreursBloquantes(this, user_nom) ;
                if(erreurs.length() > 0){
                    titre = "Erreur sur l'Identifiant" ;
                    message += erreurs + erreursEtExceptions() + avertiss ;
                    this.userInforme = true ;
                    GestionIHM.popInfo(this, titre, message);
                    break ;
                }
                else {
                    if(avertiss.length() == 0 || userInforme){
                        mIn = new Intent(CreationCompteInterface.this, ConnecteAccueilInterface.class) ;
                        mIn.putExtra("nom", user_nom) ;
                        mIn.putExtra("passe1", user_pass1) ;
                        mIn.putExtra("passe2", user_pass2) ;
                        mIn.putExtra("mail", user_mail) ;
                        mIn.putExtra("creation", true) ;
                        startActivity(mIn) ;
                        break ;
                    }
                    else {
                        titre = "Des saisies incorrectes" ;
                        GestionIHM.popInfo(this, titre, avertiss);
                        this.userInforme = true ;
                        break ;
                    }
                }


        }

    }


    private String erreursEtExceptions() {
        return "\n\n_____________________\nAutres anomalies : \n(correction facultative)\n" ;
    }


}
