package fr.hibon.modepassesecurest.ihm.compte;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compte_creation);

        nom = ((EditText) findViewById(R.id.user_nom)) ;
        nom.setText("Moi-" + ChainePasse.genererMotDePasse(4, true, false,false,false,false,null, null).getChaineDuPasse());


        String passe = ChainePasse.genererMotDePasse().getChaineDuPasse() ;
        pass1 = ((EditText) findViewById(R.id.user_pass1)) ;
        pass1.setText(passe);
        pass1confirm = ((EditText) findViewById(R.id.user_pass1_confirm)) ;
        pass1confirm.setText(passe);

        String passeRecours = ChainePasse.genererMotDePasse(4).getChaineDuPasse() ;
        pass2 = ((EditText) findViewById(R.id.user_pass2)) ;
        pass2.setText(passeRecours) ;
        pass2confirm = ((EditText) findViewById(R.id.user_pass2_confirm)) ;
        pass2confirm.setText(passeRecours) ;

        mail = ((EditText) findViewById(R.id.user_mail_saisi)) ;

        enLigne = false ;

        info_internet = (ImageButton) findViewById(R.id.bouton_enligne_help) ;
        info_internet.setOnClickListener(this);

        bouton_creation = (Button) findViewById(R.id.bouton_creation) ;
        bouton_creation.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        user_nom = nom.getText().toString() ;



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


            user_pass1 = pass1.getText().toString() ;
            user_pass1_confirmer = pass1confirm.getText().toString() ;
            user_pass2 = pass2.getText().toString() ;
            user_pass2_confirmer  = pass2confirm.getText().toString() ;
            user_mail = mail.getText().toString() ;

            enLigne = ((CheckBox) findViewById(R.id.mettre_en_ligne)).isChecked() ;


            // TODO verifie info ; cree base - utilisateur - repertoire
            // TODO option en ligne

            //  if(GestionIHM.verifierCompte(user_nom, user_pass1, user_pass1_confirmer, user_pass2, user_pass2_confirmer, enLigne)) {}
            Intent mIn = new Intent(CreationCompteInterface.this, ConnecteAccueilInterface.class) ;

            mIn.putExtra("nom", user_nom) ;
            mIn.putExtra("passe1", user_pass1) ;
            mIn.putExtra("passe2", user_pass2) ;
            mIn.putExtra("mail", user_mail) ;

            startActivity(mIn) ;

        }

    }


}
