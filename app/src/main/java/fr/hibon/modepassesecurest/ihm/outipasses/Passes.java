        package fr.hibon.modepassesecurest.ihm.outipasses;

        import android.app.AlertDialog;
        import android.app.Dialog;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.TextView;

        import java.util.ArrayList;

        import fr.hibon.modepassesecurest.R;
        import fr.hibon.modepassesecurest.motpasse.ChainePasse;
        import fr.hibon.modepassesecurest.motpasse.MotDePasseCotation;

/**
 * Gère les interactions entre les fonctions de G&eacute;n&eacute;ration et d'Analyse
 * en relation avec champ de saisie et affichage
 * <BR>Services : analyse un mot saisi, g&eacute;n&eacute;rer un mot (analyse incluse) avec param&egrave;tres
 *
 */

public class Passes extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnGenere, btnAnalyse, btnInfosGen, btnInfoAnalz ;
    String passeCado ;
    EditText champPasse ;
    TextView affichAnalyse ;
    ImageView resultatGraphique ;
    MotDePasseCotation lePasse ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.passes);
        setTitle("MP&SR - Outi'passes");

        btnGenere = (ImageButton) findViewById(R.id.bouton_generator) ;
        btnGenere.setOnClickListener(this);

        btnAnalyse = (ImageButton) findViewById(R.id.bouton_analysor) ;
        btnAnalyse.setOnClickListener(this);

        btnInfosGen = (ImageButton) findViewById(R.id.bouton_outipasses_help) ;
        btnInfosGen.setOnClickListener(this);

        btnInfoAnalz = (ImageButton) findViewById(R.id.bouton_analyse_help) ;
        btnInfoAnalz.setOnClickListener(this);

        passeCado = getIntent().getStringExtra("passeCado") ;
        champPasse = (EditText) findViewById(R.id.saisie_aTester) ;
        champPasse.setText(passeCado);
        lePasse = MotDePasseCotation.analyser(passeCado);
        afficheQualite();

    }


    @Override
    public void onClick(View v) {

        /* pour Aide sur l'ANALYSE */
        if (v.getId() == R.id.bouton_analyse_help) {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.en_ligne_pop);
            dialog.setTitle("Informations");

            TextView titre = (TextView) dialog.findViewById(R.id.info_en_ligne);
            titre.setText("Analyse du mot de passe");

            TextView textInfo = (TextView) dialog.findViewById(R.id.infoenligne) ;
            textInfo.setText(Analysor.infoAnalyse())  ;

            Button dialogButton = (Button) dialog.findViewById(R.id.close_popUP);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }

        /* pour Aide sur la GENERATION */
        if (v.getId() == R.id.bouton_outipasses_help) {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.en_ligne_pop);

            TextView titre = (TextView) dialog.findViewById(R.id.info_en_ligne);
            titre.setText("Paramétrer");

            dialog.setTitle("Informations");
            TextView textInfo = (TextView) dialog.findViewById(R.id.infoenligne) ;
            textInfo.setText(Generator.infoGeneration())  ;

            Button dialogButton = (Button) dialog.findViewById(R.id.close_popUP);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }

         /* pour ANALYSE */
        if (v.getId() == R.id.bouton_analysor) {
            champPasse = (EditText) findViewById(R.id.saisie_aTester);
            ChainePasse cp = ChainePasse.composition(champPasse.getText().toString()) ;
            lePasse = MotDePasseCotation.analyser(cp);
            afficheQualite();
        }

        /* pour GENERATION */
        if (v.getId() == R.id.bouton_generator) {
            /* LONGUEUR SELECTIONNEE */
            EditText nbCaractSaisi = (EditText) findViewById(R.id.saisie_longueur);
            String nbCaractLu = nbCaractSaisi.getText().toString();
            int longueur;
            if (nbCaractLu.equals("") || nbCaractLu.equals("0"))
                longueur = 10;
            else
                longueur = Integer.parseInt(nbCaractLu);

            /* CARACTERES */
            boolean chiffres, minuscules, majuscules, accents, speciaux;
            CheckBox chiff, min, maj, acc, sp;
            chiff = (CheckBox) findViewById(R.id.chk_chiffres);
            min = (CheckBox) findViewById(R.id.chk_minusc);
            maj = (CheckBox) findViewById(R.id.chk_majusc);
            acc = (CheckBox) findViewById(R.id.chk_accent);
            sp = (CheckBox) findViewById(R.id.chk_speciaux);
            chiffres = chiff.isChecked();
            minuscules = min.isChecked();
            majuscules = maj.isChecked();
            accents = acc.isChecked();
            speciaux = sp.isChecked();

            /* INCLUSIONS, EXCLUSIONS */
            EditText inclu, exclu;
            String inclure, exclure;
            ArrayList<Character> inclusions, exclusions;
            inclu = (EditText) findViewById(R.id.inclus_saisie);
            exclu = (EditText) findViewById(R.id.exclus_saisie);
            inclure = inclu.getText().toString();
            exclure = exclu.getText().toString();
            inclusions = etablirListe(inclure);
            exclusions = etablirListe(exclure);

            /* Affichage MOT GENERE */
            champPasse = (EditText) findViewById(R.id.saisie_aTester);
            ChainePasse chaine = ChainePasse.genererMotDePasse(longueur, chiffres, minuscules, majuscules, accents, speciaux, exclusions, inclusions);
            champPasse.setText(chaine.getChaineDuPasse());

            /* Affichage ANALYSE */
            lePasse = MotDePasseCotation.analyser(chaine);
            afficheQualite();
        }
    }



    /* OUTILS : crée liste d'exclusions ou d'inclusions */
    private ArrayList<Character> etablirListe(String chaine){
        if(chaine == null || chaine.length() == 0)
            return null ;

        ArrayList<Character> liste = new ArrayList<>() ;
        for (char c : chaine.toCharArray()) {
            if(c != ' ')
                liste.add(c);
        }
        return liste ;
    }


    /* OUTILS : qualifie Mot de passe */
    private void afficheQualite() {
        coteGraphique();
        coteCommentaire();
    }

    /* OUTILS : cotation graphique */
    private void coteGraphique() {
        resultatGraphique = (ImageView) findViewById(R.id.resultat_graphique) ;
        int cotePasse = lePasse.getValeurAnalyse() ;
        if(cotePasse == 1)
            resultatGraphique.setImageResource(R.drawable.echelle_1_tresfaible);
        if(cotePasse == 2)
            resultatGraphique.setImageResource(R.drawable.echelle_2_faible);
        if(cotePasse == 3)
            resultatGraphique.setImageResource(R.drawable.echelle_3_acceptable);
        if(cotePasse == 4)
            resultatGraphique.setImageResource(R.drawable.echelle_4_bon);
    }


    /* OUTILS : cotation commentaire */
    private void coteCommentaire() {
        affichAnalyse = (TextView) findViewById(R.id.info_analyse) ;
        String commentaire = "Niveau ModePasse Securest : " + lePasse.getQualifieAnalyse() + " (" + lePasse.getValeurAnalyse() + " / 4)" ;

        commentaire += "\n\n" + "Longueur : " + lePasse.longueurPasse() ;
        commentaire += "\n" + "Types de caractères présents : " + lePasse.getMotPasse().caracteresPresents() ;

        commentaire += "\n\n" + "Force en bits : " + lePasse.getForceBits() ;

        lePasse.getMotPasse().longeurMot() ;
        lePasse.getMotPasse().getChaineDuPasse() ;

        affichAnalyse.setText(commentaire) ;
    }

}