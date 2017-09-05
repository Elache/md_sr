        package fr.hibon.modepassesecurest.ihm.outipasses;


        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.TextView;

        import org.w3c.dom.Text;

        import java.util.ArrayList;

        import fr.hibon.modepassesecurest.MainActivity;
        import fr.hibon.modepassesecurest.R;
        import fr.hibon.modepassesecurest.compte.CompteUtilisateur;
        import fr.hibon.modepassesecurest.ihm.Infos;
        import fr.hibon.modepassesecurest.ihm.compte.ConnecteAccueilInterface;
        import fr.hibon.modepassesecurest.ihm.compte.CreationCompteInterface;
        import fr.hibon.modepassesecurest.ihm.compte.GestionIHM;
        import fr.hibon.modepassesecurest.motpasse.ChainePasse;
        import fr.hibon.modepassesecurest.motpasse.MotDePasseCotation;

/**
 * Gère les interactions entre les fonctions de G&eacute;n&eacute;ration et d'Analyse
 * en relation avec champ de saisie et affichage
 * <BR>Services : analyse un mot saisi, g&eacute;n&eacute;rer un mot (analyse incluse) avec param&egrave;tres
 *
 */

public class Passes extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnGenere;
    private ImageButton btnAnalyse;
    private ImageButton btnInfosGen;
    private ImageButton btnInfoAnalz ;
    private String passeCado ;
    private EditText champPasse ;
    private TextView affichAnalyse ;
    private ImageView resultatGraphique ;
    private MotDePasseCotation lePasse ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.passes);
        setTitle("Outi'passes");

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

    /**
     * Gere les interactions avec les boutons : infos sur analyse, infos sur generation, analyser, generer
     * @param v
     */
    @Override
    public void onClick(View v) {

        /* pour Aide sur l'ANALYSE */
        if (v.getId() == R.id.bouton_analyse_help) {
            String titre = "Analyse du mot de passe" ;
            String message = Analysor.infoAnalyse() ;
            GestionIHM.popInfo(this, titre, message );
        }

        /* pour Aide sur la GENERATION */
        if (v.getId() == R.id.bouton_outipasses_help) {
            String titre = "Paramétrer" ;
            String message = Generator.infoGeneration() ;
            GestionIHM.popInfo(this, titre, message );
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
            if (nbCaractLu.equals(""))
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

    /**
     * Outil : pour les inclusions ou les exclusions de caracteres,
     * cree une liste a partir de la chaine saisie (succession de caracteres)
     * @param chaine
     * @return
     */
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


    /**
     * Gere l'affichage graphique de la force du mot de passe
     */
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


    /**
     * Gere le commentaire sur la force du mot de passe
     */
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(CompteUtilisateur.getCompteConnecte().getIsConnecte()) {
            getMenuInflater().inflate(R.menu.menu_outipasses_co, menu);
        }
        else {
            getMenuInflater().inflate(R.menu.menu_outipasses, menu);
        }
        return true;
    }


    /**
     * Gere les interactions avec le menu : Infos, Deconnexion, retour accueil, creation de compte
     * @param item item selectionne dans le menu
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent mIn ;

        switch (item.getItemId()){

            case R.id.menu_info:
                mIn = new Intent(Passes.this, Infos.class);
                startActivity(mIn);
                return true ;

            case R.id.menu_deco:
            case R.id.menu_versAccueil :
                if(CompteUtilisateur.getCompteConnecte().getIsConnecte())
                    CompteUtilisateur.getCompteConnecte().deconnecter();
                mIn = new Intent(Passes.this, MainActivity.class) ;
                startActivity(mIn);
                return true;

            case R.id.menu_creation:
                mIn = new Intent(Passes.this, CreationCompteInterface.class);
                startActivity(mIn);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}