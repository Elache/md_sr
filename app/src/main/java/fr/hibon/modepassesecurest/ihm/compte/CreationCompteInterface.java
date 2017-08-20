package fr.hibon.modepassesecurest.ihm.compte;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import fr.hibon.modepassesecurest.R;
import fr.hibon.modepassesecurest.compte.bdd.CreationBase;
import fr.hibon.modepassesecurest.ihm.outipasses.Analysor;

/**
 *
 */

public class CreationCompteInterface extends AppCompatActivity implements View.OnClickListener {

    String user_nom, user_pass1, user_pass1_confirmer, user_pass2, user_pass2_confirmer ;
    Button bouton_creation ;
    boolean enLigne ;
    ImageButton info_internet ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compte_creation);

        user_nom = ((EditText) findViewById(R.id.user_nom)).getText().toString() ;
        user_pass1 = ((EditText) findViewById(R.id.user_pass1)).getText().toString() ;
        user_pass1_confirmer = ((EditText) findViewById(R.id.user_pass1_confirm)).getText().toString() ;
        user_pass2 = ((EditText) findViewById(R.id.user_pass2)).getText().toString() ;
        user_pass2_confirmer  = ((EditText) findViewById(R.id.user_pass2_confirm)).getText().toString() ;

        enLigne = ((CheckBox) findViewById(R.id.mettre_en_ligne)).isChecked() ;

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
            TextView textInfo = (TextView) dialog.findViewById(R.id.infoenligne) ;
            textInfo.setText(getString(R.string.sauver_en_ligne))  ;
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
            if(GestionIHM.verifierCompte(user_nom, user_pass1, user_pass1_confirmer, user_pass2, user_pass2_confirmer, enLigne)) {
                // TODO creation suivant enligne
                CreationBase.getInstance(getApplicationContext());
            }
        }

    }
}
