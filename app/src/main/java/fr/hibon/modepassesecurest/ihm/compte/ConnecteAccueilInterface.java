package fr.hibon.modepassesecurest.ihm.compte;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import fr.hibon.modepassesecurest.MainActivity;
import fr.hibon.modepassesecurest.R;
import fr.hibon.modepassesecurest.compte.CompteUtilisateur;
import fr.hibon.modepassesecurest.compte.Donnee;
import fr.hibon.modepassesecurest.compte.Repertoire;
import fr.hibon.modepassesecurest.compte.bdd.ManipTables;
import fr.hibon.modepassesecurest.compte.exception.CompteException;
import fr.hibon.modepassesecurest.ihm.compte.donnee.DonneeAdapter;
import fr.hibon.modepassesecurest.ihm.compte.donnee.DonneesControl;
import fr.hibon.modepassesecurest.ihm.Infos;
import fr.hibon.modepassesecurest.ihm.outipasses.Passes;

/**
 * Gere l'interface d'accueil apres connexion : nom, liste des repertoires, acces aux fonctions de recherche et liste, affichage des noms de donn&eacute;es trouv&eacute;es
 */
public class ConnecteAccueilInterface extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    CompteUtilisateur lUtilisateur ;

    TextView  user, titreRep, titreDonnee  ;
    Spinner repert ;
    EditText recherches ;
    ImageButton cherche_btn, lister_btn, addDonnee ;
    ListView listView ;
    Repertoire selectRep ;
    boolean creation ;
    LinearLayout affichRes ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connecte);
        setTitle("MP&SR");

        /* Origine de l'acces */
        creation = getIntent().getBooleanExtra("creation", false) ;

       /* Accueil et titres */
        user =  (TextView) findViewById(R.id.accueil_connecte_user) ;
        titreRep = (TextView) findViewById(R.id.titre_repert) ;
        titreRep.setText("Liste des répertoires");
        titreDonnee = (TextView) findViewById(R.id.titre_donnees) ;
        titreDonnee.setText("Mots de passe et données");

        /* affichage et recherches */
        lister_btn =  (ImageButton) findViewById(R.id.btn_lister) ;
        lister_btn.setOnClickListener(this) ;
        cherche_btn = (ImageButton) findViewById(R.id.btn_search) ;
        cherche_btn.setOnClickListener(this) ;
        addDonnee = (ImageButton) findViewById(R.id.ajouter_donnee) ;
        addDonnee.setOnClickListener(this) ;

        /* Si creation du compte (donc premiere connexion), insere en base */
        if(creation) {
                String nomSaisi = getIntent().getStringExtra("nom") ;
                String passe1Saisi = getIntent().getStringExtra("passe1") ;
                String passe2Saisi = getIntent().getStringExtra("passe2") ;
                String mailSaisi = getIntent().getStringExtra("mail") ;
            try {
                CompteUtilisateur.renseigneLeCompte(nomSaisi, passe1Saisi, passe2Saisi, mailSaisi,"") ;
            } catch (CompteException e) {
                e.printStackTrace();
            }
            new GestionIHM().instanceInsert(this) ;
        }


        /* Utilisateur et Base de données */
        lUtilisateur = CompteUtilisateur.getCompteConnecte() ;

        /* Repertoire par defaut */
        selectRep = lUtilisateur.getLesRepertoires().get(0) ;
        
        user.setText("Connecté : " + lUtilisateur.getNomUser());

       /* Liste des repertoires : Spinner */
        repert = (Spinner) findViewById(R.id.accueil_connecte_rep) ;
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, lUtilisateur.getLesRepertoires()) ;
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repert.setBackgroundColor(Color.LTGRAY);
        repert.setAdapter(adapter) ;
        repert.setOnItemSelectedListener(this) ;

        /* Liste des données : ListView */
        listView = (ListView) findViewById(R.id.donneesListe);
        listView.setMinimumHeight(250);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Donnee selectD = (Donnee) parent.getItemAtPosition(position) ;
                affichDonnees(selectD, selectRep.getidRepEnBase());
            }
        });

        /* Panel de resultat masque initialement */
        affichRes = (LinearLayout) findViewById(R.id.lay_liste) ;
        affichRes.setVisibility(View.INVISIBLE);
    }


    /* Affichage d'une donnee - popup */
    private void affichDonnees(Donnee d, int rep) {
        DonneesControl.afficherDonnee(this, d, rep) ;
    }

    /**
     * Gere les choix sur les boutons pour
     * Rechercher / Lister / Ajouter une Donnee
     * @param v vue
     */
    @Override
    public void onClick(View v) {

        switch(v.getId()) {

            case R.id.btn_search :
                recherches = (EditText) findViewById(R.id.cherche) ;
                String mots = recherches.getText().toString() ;
                ArrayList<Donnee> results = GestionIHM.rechercheDonneeParNom(this, selectRep.getidRepEnBase(), mots) ;
                affichResultats(results);
                break ;

            case R.id.btn_lister :
                ArrayList<Donnee> result = new ManipTables(this).listerDonnees(selectRep.getidRepEnBase()) ;
                affichResultats(result);
                break ;

            case R.id.ajouter_donnee :
                DonneesControl.saisirDonnee(this, selectRep.getidRepEnBase());
        }
    }


    /**
     * Gere l'affichage de la liste de Donnee retourn&eacute;es par une requete (rechercher ou lister)
     *
     * @param result Liste de Donnee a afficher
     */
    private void affichResultats(ArrayList<Donnee> result) {
        final DonneeAdapter adaptListe ;
        affichRes.setVisibility(View.VISIBLE);
        if(result.size() > 0) {
            adaptListe = new DonneeAdapter(result) ;
            listView.setAdapter(adaptListe);
        } else {
            String[] message = { " Pas de résultat "} ;
            ArrayAdapter<String> avertiss = new ArrayAdapter(this, android.R.layout.simple_spinner_item, message) ;
            avertiss.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            listView.setAdapter(avertiss) ;
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        affichRes.setVisibility(View.INVISIBLE);
        selectRep = (Repertoire) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        affichRes.setVisibility(View.INVISIBLE);
        selectRep = (Repertoire) parent.getItemAtPosition(0);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);
         MenuInflater inflater = getMenuInflater();
         inflater.inflate(R.menu.menu_interne, menu);
        return true;
    }

    /**
     * Gere les choix du menu :
     * <BR>Deconnexion, Outi'passes, Infos, Creer une Donnee
     * @param item item Menu
     * @return booleen
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent mIn ;

        switch (item.getItemId()){

            case R.id.menu_deco:
                CompteUtilisateur.getCompteConnecte().deconnecter();
                mIn = new Intent(ConnecteAccueilInterface.this, MainActivity.class) ;
                startActivity(mIn);
                return true;

            case R.id.menu_nouvelle:
                DonneesControl.saisirDonnee(this, selectRep.getidRepEnBase());
                return true;

            case R.id.menu_outipasses:
                mIn = new Intent(ConnecteAccueilInterface.this, Passes.class);
                startActivity(mIn);
                return true;

            case R.id.menu_info:
                mIn = new Intent(ConnecteAccueilInterface.this, Infos.class);
                startActivity(mIn);
                return true;


        }

        return super.onOptionsItemSelected(item);
    }
}
