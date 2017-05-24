package fr.hibon.modepassesecurest.ihm.outipasses;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fr.hibon.modepassesecurest.R;
import fr.hibon.modepassesecurest.ihm.outipasses.frag.Analysor;
import fr.hibon.modepassesecurest.ihm.outipasses.frag.Generator;

import static fr.hibon.modepassesecurest.R.layout.passes_frag;

/**
 */

public class Passes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Analysor fragAnaly = Analysor.create() ;
    //    Generator fragGen = Generator.create() ;

        getFragmentManager().beginTransaction().add(android.R.id.content, fragAnaly).commit() ;

    }

}
