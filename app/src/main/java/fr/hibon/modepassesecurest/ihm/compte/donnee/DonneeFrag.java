package fr.hibon.modepassesecurest.ihm.compte.donnee;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.hibon.modepassesecurest.R;
import fr.hibon.modepassesecurest.ihm.outipasses.frag.Analysor;
// version non app.v4 =     import android.app.Fragment;

/**
 * Created by lohib on 23/05/2017.
 */

public class DonneeFrag extends Fragment {

    public static DonneeFrag create() {
        DonneeFrag donneeFrag = new DonneeFrag();
        return donneeFrag ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.analysor, container, false) ;
    }




}
