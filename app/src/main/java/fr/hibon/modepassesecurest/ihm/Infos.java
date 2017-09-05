package fr.hibon.modepassesecurest.ihm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import fr.hibon.modepassesecurest.R;

/**
 * Ecran d'informations generales sur l'application
 */
public class Infos extends AppCompatActivity {

    public static final String INFOS = "ModePasse SecuRest est une application Android destinée à gérer vos mots de passe (conservés " +
            "sous une forme chiffrée) et à en protéger l'accès au sein d'un Compte-utilisateur avec authentification." +
            "\nMalgré ces éléments de sécurité, nous vous invitons à réfléchir sur la nature des données, plus ou moins sensibles, que vous voulez stocker." +
            "\n\n- - - - - - - - - - - - -" +
            "\nLors de la première utilisation, il vous faut CREER un COMPTE, en choisissant :" +
            "\n* un identifiant (unique)" +
            "\n* deux mots de passe différents, doublon pour vous permettre de récupérer votre compte en cas d'oubli de l'un" +
            "\nRemarques : " +
            "\n   - pour un usage local sur votre appareil mobile, l'application vous laisse la possibilité de n'utiliser aucun mot de passe." +
            "\n   - le formulaire permet de sécuriser vos saisies en donnant confirmation des passes (facultatif)" +
            "\n   - des informations préremplies sont disponibles et modifiables." +
            "\n   - vous pouvez créer plusieurs comptes. La création ne peut se faire que lorsqu'on n'est pas déjà connecté à l'application. " +
            "\n- - - - - - - - - - - - -" +
            "\nAprès création, vous disposez d'un répertoire dans lequel vous pouvez " +
            "\n\n 1. enregistrer des données (le mot de passe et des informations contextuelles : site web, identifiant...) " +
            "\n\n 2. les consulter, après listage complet ou recherche" +
            "\n Recherche : elle s'effectue simultanément sur les champs \"nom de la donnée\", \"question secrète\" et \"note personnelle\"."+
            "\n Vous pouvez saisir plusieurs mots espacés : toutes les données qui correspondent à l'un au moins (OU) " +
            "seront affichées. Vous pouvez ne saisir que des parties de mot (par ex. 'mail' vous renverra des données telles que gmail, webmail...)" +
            "\nLa casse n'a pas d'importance." +
            "\n\n 3. les modifier ou supprimer. Pour ces opérations, une confirmation vous sera demandée." +
            "\n Réactualisez les informations (icône LISTE) après validation de tels changements." +
            "\n" ;

    public static final String COMPLEMENTAIRES = "Vous trouverez aussi, dans les sections correspondantes de l'application, des informations : " +
            "\n - sur la création de compte en ligne (fonction non encore disponible)" +
            "\n - sur l'analyse et la génération de mots de passe. " +
            "\n La génération est paramétrable (puis bouton GENERER) et affiche l'analyse correspondante." +
            "\n Vous pouvez également saisir un mot de passe et cliquer sur ANALYSER." +
            "\n\n D'autres informations seront disponibles concernant l'utilisation en ligne (sauvegarde et consultation ; identifiants et mots de passe), fonctionnalité à venir." +
            "\n"  ;

    public static final String LEGALES = "Application réalisée par Loïc HIBON" +
            "\nloic.hibon@free.fr" +
            "\nLimite de responsabilités..." +
            "\n";


    TextView tit1, tit2, tit3 ;
    TextView champ1, champ2, champ3 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infos);

        tit1 = (TextView) findViewById(R.id.info_titre1) ;
        champ1 = (TextView) findViewById(R.id.info1) ;

        tit2 = (TextView) findViewById(R.id.info_titre2) ;
        champ2 = (TextView) findViewById(R.id.info2) ;

        tit3 = (TextView) findViewById(R.id.info_titre3) ;
        champ3 = (TextView) findViewById(R.id.info3) ;

        tit1.setText("Informations générales");
        champ1.setText(INFOS);

        tit2.setText("Compléments");
        champ2.setText(COMPLEMENTAIRES);

        tit3.setText("Mentions légales");
        champ3.setText(LEGALES);



    }






}
