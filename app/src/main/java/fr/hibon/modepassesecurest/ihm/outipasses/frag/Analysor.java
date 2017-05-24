package fr.hibon.modepassesecurest.ihm.outipasses.frag;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.hibon.modepassesecurest.R;

/**
 *
 */

public class Analysor extends Fragment {

    public static Analysor create() {
        Analysor analyFrag = new Analysor();
        return analyFrag ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.analysor, container, false) ;
    }



}
