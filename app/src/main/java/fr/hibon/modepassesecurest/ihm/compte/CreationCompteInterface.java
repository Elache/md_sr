package fr.hibon.modepassesecurest.ihm.compte;

import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


import java.util.zip.CRC32;

import fr.hibon.modepassesecurest.R;
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

        if (v.getId() == R.id.bouton_enligne_help) {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.en_ligne_pop);
            dialog.setTitle("Informations");
            TextView textInfo = (TextView) dialog.findViewById(R.id.infoenligne);
            textInfo.setText(getString(R.string.sauver_en_ligne));
            Button dialogButton = (Button) dialog.findViewById(R.id.close_popUP);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }

        if (v.getId() == R.id.bouton_creation) {

            Intent mIn ;
            user_nom = nom.getText().toString() ;
            user_pass1 = pass1.getText().toString() ;
            user_pass1_confirmer = pass1confirm.getText().toString() ;
            user_pass2 = pass2.getText().toString() ;
            user_pass2_confirmer  = pass2confirm.getText().toString() ;
            user_mail = mail.getText().toString() ;
            enLigne = ((CheckBox) findViewById(R.id.mettre_en_ligne)).isChecked() ;

            String avertiss = new GestionIHM(user_nom,user_pass1,user_pass1_confirmer,user_pass2, user_pass2_confirmer, user_mail, enLigne).verifier() ;
            if(avertiss.length() == 0 || userInforme){
                mIn = new Intent(CreationCompteInterface.this, ConnecteAccueilInterface.class) ;
                mIn.putExtra("nom", user_nom) ;
                mIn.putExtra("passe1", user_pass1) ;
                mIn.putExtra("passe2", user_pass2) ;
                mIn.putExtra("mail", user_mail) ;
                mIn.putExtra("creation", true) ;
                startActivity(mIn) ;            }
            else {
                final Dialog dial = new Dialog(this);
                dial.setContentView(R.layout.en_ligne_pop);
                dial.setTitle("Informations");
                TextView titre = (TextView) dial.findViewById(R.id.info_en_ligne);
                titre.setText("Des saisies incorrectes");
                TextView textInfo = (TextView) dial.findViewById(R.id.infoenligne);
                textInfo.setText(avertiss);
                Button dialogButton = (Button) dial.findViewById(R.id.close_popUP);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dial.dismiss();
                    }
                });
                dial.show();
                this.userInforme = true ;
            }

            // TODO option : en ligne

        }

    }



}
