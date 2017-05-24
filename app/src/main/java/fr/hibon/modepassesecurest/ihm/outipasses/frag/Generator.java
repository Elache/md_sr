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

public class Generator extends Fragment {

    public static Generator create() {
        Generator genFrag = new Generator();
        return genFrag ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.passes_frag, container, false) ;
    }



}
