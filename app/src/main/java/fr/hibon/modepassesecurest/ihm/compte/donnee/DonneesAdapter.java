package fr.hibon.modepassesecurest.ihm.compte.donnee;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.app.AlertDialog;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

/**
 */

public class DonneesAdapter extends RecyclerView.Adapter<DonneesAdapter.MyViewHolder> {

    @Override
    public DonneesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(DonneesAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

   //     private final TextView name;
   //     private final TextView description;

  //      private Pair<String, String> currentPair;

        public MyViewHolder(final View itemView) {
            super(itemView);

   //         name = ((TextView) itemView.findViewById(R.id.name));
   //         description = ((TextView) itemView.findViewById(R.id.description));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    new AlertDialog.Builder(itemView.getContext())
//                           .setMessage(currentPair.second)
//                            .show();
                }
            });
        }

        public void display(Pair<String, String> pair) {
//            currentPair = pair;
//            name.setText(pair.first);
//            description.setText(pair.second);
        }
    }
}
