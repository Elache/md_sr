package fr.hibon.modepassesecurest.ihm.outipasses;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import fr.hibon.modepassesecurest.R;
import fr.hibon.modepassesecurest.compte.utiles.ChiffeMode;

/**
 *
 */

public class Analysor extends AppCompatActivity  implements View.OnClickListener {

    Button boutonConnexion ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        boutonConnexion = (Button) findViewById(R.id.connexion) ;
        boutonConnexion.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        EditText champPasse = (EditText)findViewById(R.id.saisieMP);
        String message  = champPasse.getText().toString();

        ChiffeMode ch = new ChiffeMode() ;
        ch.chiffrer(message) ;

        TextView total = (TextView) findViewById(R.id.aff);
//        total.setText(message + " : " + ch.getPasseCode() + " cle " + ch.getCleCode());
        total.setText(ch.getPasseCode().length() + " " + ch.getPasseCode());

 //       TextView tot = (TextView) findViewById(R.id.aff2);
 //       tot.setText(message + " : " + ch.dechiffrer(ch.getPasseCode(),ch.getCleCode()));


    }
}
