package fr.hibon.modepassesecurest.ihm.compte.donnee;

        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

import java.util.ArrayList;

import fr.hibon.modepassesecurest.R;
import fr.hibon.modepassesecurest.compte.Donnee;

/**
 * Created by Boris Sauvage on 10/10/2015.
 */
public class DonneeAdapter extends BaseAdapter {

    private ArrayList<Donnee> mItems;

    private static class Holder {
        ImageView imageView;
        TextView textView;
    }

    public DonneeAdapter(ArrayList<Donnee>  items) {
        mItems = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lister_donnees, parent, false);
            holder = new Holder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.ico);
            holder.textView = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        Donnee getDonnee = getItem(position);
        holder.imageView.setImageResource(R.drawable.coche);
        holder.textView.setText(getDonnee.getNomDonnee());
        return convertView;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Donnee getItem(int position) {
        return (Donnee) mItems.get(position) ;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}