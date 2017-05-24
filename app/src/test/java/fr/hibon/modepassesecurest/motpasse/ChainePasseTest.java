package fr.hibon.modepassesecurest.motpasse;


import java.util.ArrayList;

import fr.hibon.modepassesecurest.motpasse.Caractere;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

/**
 * Teste la classe ChainePasse <BR>
 * (MotDePasseCotation - ChainePasse - Caractere)
 */
public class ChainePasseTest {

    // // Composition d'une chaine (analyse + instanciation ChainePasse)

    /**
     * Teste la methode composition() qui : <BR>
     * - rep&eacute;re les types de caracteres presents dans une chaine (param)
     * <BR>
     * - instancie le ChainePasse correspondant <BR>
     * Teste : échantillon de 5 chaine ; verification de la bonne analyse des
     * types presents / absents
     */
    @Test
    public void composition() {
        boolean test = true;
        String chTest = "&aEé";
        ChainePasse passe = ChainePasse.composition(chTest);
        if (passe.avecChiffre() || !passe.avecAccentcedil() || !passe.avecMinuscule() || !passe.avecMajuscule()
                || !passe.avecSpecial())
            test = false;
        chTest = "azr538";
        passe = ChainePasse.composition(chTest);
        if (!passe.avecChiffre() || passe.avecAccentcedil() || !passe.avecMinuscule() || passe.avecMajuscule()
                || passe.avecSpecial())
            test = false;
        chTest = "çallut!";
        passe = ChainePasse.composition(chTest);
        if (passe.avecChiffre() || !passe.avecAccentcedil() || !passe.avecMinuscule() || passe.avecMajuscule()
                || !passe.avecSpecial())
            test = false;
        chTest = "#~{";
        passe = ChainePasse.composition(chTest);
        if (passe.avecChiffre() || passe.avecAccentcedil() || passe.avecMinuscule() || passe.avecMajuscule()
                || !passe.avecSpecial())
            test = false;
        chTest = "235FEZ";
        passe = ChainePasse.composition(chTest);
        if (!passe.avecChiffre() || passe.avecAccentcedil() || passe.avecMinuscule() || !passe.avecMajuscule()
                || passe.avecSpecial())
            test = false;
        assertTrue(test);
    }

    // //////// Generer mot de passe (defaut) longueur 10 sur 110 caracteres

    /**
     * verifie methode de generation de mot de passe par defaut <BR>
     * longueur 10 ; 110 caracteres utilisables<BR>
     * &nbsp; <BR>
     * Suivant le tirage (plus ou moins de types represent&eacute;s), le mot de
     * passe sera d'un niveau variable <BR>
     * (test sur 4 x 10 000 : ~ 13-14 % de niveau inférieur à Bon). <BR>
     * Le test verifie que tous les mots de passe g&eacute;n&eacute;r&eacute;s
     * sont de longueur 10 et que moins de 20% ne sont pas de niveau Bon
     */
    @Test
    public void genererMotDePasse() {
        boolean test = true;
        int peuVaries = 0;
        MotDePasseCotation cotation = null;
        for (int i = 1; i <= 100; i++) {
            ChainePasse ch = ChainePasse.genererMotDePasse();
            String pass = ch.getChaineDuPasse();
            cotation = MotDePasseCotation.analyser(pass);
            if (pass.length() != 10)
                test = false;
            if (cotation.getValeurAnalyse() < 4)
                peuVaries++;
        }
        if (peuVaries > 20)
            test = false;
        assertTrue(test);
    }

    // //////// Generation de mot de passe -////////////////////
    // longueur en parametre - caracteres 110 ////////////////////

    /**
     * verifie methode de generation de mot de passe avec longueur au choix de
     * l'utilisateur <BR>
     * 110 caracteres utilisables<BR>
     * &nbsp; <BR>
     * Le test verifie que tous les mots de passe g&eacute;n&eacute;r&eacute;s
     * sont de la longueur fixée
     */
    @Test
    public void genererMotDePasse_longueurChoisie_longueur() {
        boolean test = true;
        int longueurEnCours = 0;
        for (int i = 1; i <= 100; i++) {
            longueurEnCours = (int) (Math.random() * 60 + 1);
            ChainePasse ch = ChainePasse.genererMotDePasse(longueurEnCours);
            String pass = ch.getChaineDuPasse();
            if (pass.length() != longueurEnCours) {
                test = false;
            }
        }
        assertTrue(test);
    }

    // //////// Generation de mot de passe -////////////////////
    // longueur 0 en parametre ////////////////////

    /**
     * verifie methode de generation de mot de passe avec longueur au choix de
     * l'utilisateur <BR>
     * Le test verifie que le choix de longueur 0 donne un mot de passe de
     * longueur 1
     */
    @Test
    public void genererMotDePasse_longueurChoisie_0() {
        int longueurAttendue = 1;
        int longueurChoisie = 0;
        ChainePasse ch = ChainePasse.genererMotDePasse(longueurChoisie);
        assertEquals(longueurAttendue, ch.longeurMot());
    }

    // //////// Generation de mot de passe -////////////////////
    // longueur 100 en parametre////////////////////

    /**
     * verifie methode de generation de mot de passe avec longueur au choix de
     * l'utilisateur <BR>
     * Le test verifie que le choix de longueur 100 donne un mot de passe de
     * longueur 60 (max)
     */
    @Test
    public void genererMotDePasse_longueurChoisie_100() {
        int attendue = 60;
        int choisie = 100;
        ChainePasse ch = ChainePasse.genererMotDePasse(choisie);
        assertEquals(attendue, ch.longeurMot());
    }

    // //////// Generation de mot de passe ////////////////////
    // longueur en parametre - caracteres possibles : 110

    /**
     * verifie methode de g&eacute;n&eacute;ration de mot de passe avec longueur
     * au choix de l'utilisateur <BR>
     * 110 caracteres utilisables<BR>
     * &nbsp; <BR>
     * Le test genere 100 mots de passe de longueur aleatoire et verifie que
     * tous les types de caracteres peuvent etre repr&eacute;sent&eacute;s
     */
    @Test
    public void genererMotDePasse_longueurChoisie_variete() {
        boolean chiffres = false;
        boolean minuscules = false;
        boolean majuscules = false;
        boolean accents = false;
        boolean speciaux = false;
        int longueurEnCours = 0;
        for (int i = 1; i <= 100; i++) {
            longueurEnCours = (int) (Math.random() * 60 + 1);
            ChainePasse ch = ChainePasse.genererMotDePasse(longueurEnCours);
            if (ch.avecChiffre())
                chiffres = true;
            if (ch.avecMinuscule())
                minuscules = true;
            if (ch.avecMajuscule())
                majuscules = true;
            if (ch.avecAccentcedil())
                accents = true;
            if (ch.avecSpecial())
                speciaux = true;
        }
        assertTrue(chiffres && minuscules && majuscules && accents && speciaux);
    }

    // //////// Generation avec choix des types + inclusions + exclusions
    // ////////////////////

    /**
     * Verifie la g&eacute;n&eacute;ration de mot de passe avec tout caractere
     * sauf ceux pass&eacute;s en parametre (exclusions) <BR>
     * Genere 100 mots de passe de 20 de long. Aucun caractere de la liste
     * d'exclusions ne doit apparaitre
     */
    @Test
    public void genererMotDePasse_exclusions() {
        boolean test = true;
        // exclusions
        ArrayList<Character> excl = echantillon_exclusions();
        ChainePasse exTest = null;
        char enCours = 'x';
        for (int i = 0; i < 100; i++) {
            exTest = ChainePasse.genererMotDePasse(20, true, true, true, true, true, excl, null);
            for (Character exclus : excl) {
                enCours = exclus;
                if (exTest.getChaineDuPasse().contains(String.valueOf(enCours)))
                    test = false;
            }
        }
        assertTrue(test);
    }

    /**
     * Verifie g&eacute;n&eacute;ration de mot de passe uniquement &agrave;
     * partir de chiffres <BR>
     * Test : 100 mots de passe de longueur 20 : tout caractere doit etre un
     * chiffre
     */
    @Test
    public void genererMotDePasse_queDesChiffres() {
        boolean test = true;
        ChainePasse exTest = null;
        String chainPasse = "";
        for (int i = 0; i < 100; i++) {
            exTest = ChainePasse.genererMotDePasse(20, true, false, false, false, false, null, null);
            chainPasse = exTest.getChaineDuPasse();
            for (int k = 0; k < chainPasse.length(); k++) {
                if (!Character.isDigit(chainPasse.charAt(k)))
                    test = false;
            }
        }
        assertTrue(test);
    }

    /**
     * Verifie g&eacute;n&eacute;ration de mot de passe uniquement &agrave;
     * partir de chiffres + des caracteres specifiquement autoris&eacute;s
     * (inclusions) <BR>
     * Test : 100 mots de passe de longueur 20 : tout caractere doit etre un
     * chiffre ou un caractere d'inclusion
     */
    @Test
    public void genererMotDePasse_inclusions() {
        boolean test = true;
        // inclusions
        ArrayList<Character> incl = echantillon_inclusions();
        ChainePasse exTest = null;
        String chainPasse = "";
        for (int i = 0; i < 100; i++) {
            exTest = ChainePasse.genererMotDePasse(20, true, false, false, false, false, null, incl);
            chainPasse = exTest.getChaineDuPasse();
            for (int k = 0; k < chainPasse.length(); k++) {
                if (!Character.isDigit(chainPasse.charAt(k)) && !incl.contains(new Character(chainPasse.charAt(k))))
                    test = false;
            }
        }
        assertTrue(test);
    }

    /**
     * Verifie g&eacute;n&eacute;ration de mot de passe avec la selection des
     * types autorises <BR>
     * Test : autorise les lettres minuscules, majuscules et accentuées <BR>
     * 100 mots de passe de longueur 20 : tout caractere doit etre d'un des
     * types autoris&eacute;s
     */
    @Test
    public void genererMotDePasse_3typesLettres() {
        boolean test = true;
        ChainePasse exTest = null;
        String chainPasse = "";
        Caractere ch = null;
        for (int i = 0; i < 100; i++) {
            exTest = ChainePasse.genererMotDePasse(20, false, true, true, true, false, null, null);
            chainPasse = exTest.getChaineDuPasse();
            for (int k = 0; k < chainPasse.length(); k++) {
                ch = new Caractere(chainPasse.charAt(k));
                if (!ch.estUneMinuscule() && !ch.estUneMajuscule() && !ch.estUnAccentCedil())
                    test = false;
            }
        }
        assertTrue(test);
    }

    /**
     * Verifie la g&eacute;n&eacute;ration de mot de passe avec tous parametres
     * possibles : selection des types, ajout de caracteres d'inclusion
     * (utilisables) et d'exclusion (non utilisables) <BR>
     * test : 100 mots de passe de 20 de long. Uniquement des majuscules + une
     * liste d'inclusions - une liste d'exclusions (dont des majuscules)
     */
    @Test
    public void genererMotDePasse_tousParam() {
        boolean test = true;
        // exclusions
        ArrayList<Character> excl = echantillon_exclu_VoyellesMajuscules();
        // inclusions
        ArrayList<Character> incl = echantillon_inclusions();
        ChainePasse exTest = null;
        String chainPasse = "";
        Caractere ch = null;
        for (int i = 0; i < 100; i++) {
            exTest = ChainePasse.genererMotDePasse(20, false, false, true, false, false, excl, incl);
            chainPasse = exTest.getChaineDuPasse();
            for (int k = 0; k < chainPasse.length(); k++) {
                ch = new Caractere(chainPasse.charAt(k));
                if (excl.contains(new Character(ch.getLeChar())))
                    test = false;
                if (!ch.estUneMajuscule() && !incl.contains(new Character(ch.getLeChar())))
                    test = false;
            }
        }
        assertTrue(test);
    }

    /**
     * Verifie le respect de la limite de longueur de mot de passe > 0
     * <BR>test : mot de passe de longueur 0 (et moins) demand&eacute;. Doit donner un mot de longueur 1.
     */
    @Test
    public void genererMotDePasse_tousParam_longueur0() {
        boolean test = true;
        ChainePasse exTest = null;
        exTest = ChainePasse.genererMotDePasse(0, true, true, true, false, false, null, null);
        if (exTest.longeurMot() != 1)
            test = false;
        ChainePasse exTest2 = null;
        exTest2 = ChainePasse.genererMotDePasse(-2, true, true, true, false, false, null, null);
        if (exTest2.longeurMot() != 1)
            test = false;
        assertTrue(test);
    }

    /**
     * Verifie le respect de la limite de longueur de mot de passe > 0
     * <BR>test : mot de passe de longueur 0 (et moins) demand&eacute;. Doit donner un mot de longueur 1.
     */
    @Test
    public void genererMotDePasse_tousParam_longueur61() {
        ChainePasse exTest = null;
        exTest = ChainePasse.genererMotDePasse(61, false, true, true, false, false, null, null);
        assertEquals(exTest.longeurMot(), 60);
    }


    // //////// Completer passe jusque 10 caracteres ////////////////////

    /**
     * verifie methode passeComplete10() qui permet de completer un mot de passe
     * court jusqu'&agrave; atteindre une longueur de 10 caracteres <BR>
     * test : le mot de passe obtenu doit etre de longueur 10 et commencer par
     * les caracteres du mot de passe court
     */
    @Test
    public void passeComplete10() {
        String chaineTropCourte = "pass5";
        ChainePasse passeCourt = ChainePasse.composition(chaineTropCourte);

        String pass = ChainePasse.passeComplete10(passeCourt).getChaineDuPasse();
        assertTrue((pass.length() == 10) && (pass.substring(0, chaineTropCourte.length()).equals(chaineTropCourte)));
    }

    // //////// toString() ////////////////////

    /**
     * Test methode toString()
     */
    @Test
    public void toString_masque() {
        String pass = "&eacute;";
        ChainePasse c1 = new ChainePasse();
        c1.setMotPasse(pass);
        String passToStr = c1.toString();
        assertTrue(passToStr.equals(c1.getChaineDuPasse()));
    }

    // //////// EQUALS ////////////////////

    /**
     * Test Egalit&eacute; de deux ChainePasse <BR>
     * Condition &eacute;galit&eacute; v&eacute;rifi&eacute;e : chaineDuPasse
     * (String)
     */
    @Test
    public void equals_identiques() {
        String pass = "&eacute;";
        ChainePasse c1 = new ChainePasse();
        c1.setMotPasse(pass);
        ChainePasse c2 = new ChainePasse();
        c2.setMotPasse(pass);
        assertEquals(c1, c2);
    }

    /**
     * Test Egalit&eacute; de deux ChainePasse <BR>
     * Condition &eacute;galit&eacute; non v&eacute;rifi&eacute;e : 2
     * chaineDuPasse different
     */
    @Test
    public void equals_differentPasse() {
        String pass1 = "&eacute;";
        ChainePasse c1 = new ChainePasse();
        c1.setMotPasse(pass1);
        String pass2 = "&eacute";
        ChainePasse c2 = new ChainePasse();
        c2.setMotPasse(pass2);
        assertNotEquals(c1, c2);
    }

    /**
     * Test Egalit&eacute; de deux ChainePasse <BR>
     * Condition &eacute;galit&eacute; non v&eacute;rifi&eacute;e : une
     * chaineDuPasse (String) nulle
     */
    @Test
    public void equals_differentPasseNull() {
        String pass1 = "&eacute;";
        ChainePasse c1 = new ChainePasse();
        c1.setMotPasse(pass1);
        ChainePasse c2 = new ChainePasse();
        assertNotEquals(c1, c2);
    }

    /**
     * Test Egalit&eacute; de deux ChainePasse <BR>
     * Condition &eacute;galit&eacute; non v&eacute;rifi&eacute;e : une
     * chaineDuPasse (String) vide
     */
    @Test
    public void equals_differentPasseVide() {
        String pass1 = "&eacute;";
        ChainePasse c1 = new ChainePasse();
        c1.setMotPasse(pass1);
        String pass2 = "";
        ChainePasse c2 = new ChainePasse();
        c2.setMotPasse(pass2);
        assertNotEquals(c1, c2);
    }

    /**
     * Test Egalit&eacute; de deux ChainePasse <BR>
     * Condition &eacute;galit&eacute; v&eacute;rifi&eacute;e : m&ecirc;me objet
     */
    @Test
    public void equals_memeObjet() {
        String pass1 = "&eacute;";
        ChainePasse c1 = new ChainePasse();
        c1.setMotPasse(pass1);
        ChainePasse c2 = c1;
        assertTrue(c1.equals(c2));
    }

    /**
     * Test Egalit&eacute; de deux ChainePasse <BR>
     * Condition &eacute;galit&eacute; non v&eacute;rifi&eacute;e : classe
     * diff&eacute;nte
     */
    @Test
    public void equals_classeDifferente() {
        String pass1 = "&eacute;";
        ChainePasse c1 = new ChainePasse();
        c1.setMotPasse(pass1);
        Caractere k1 = new Caractere('&');
        assertFalse(c1.equals(k1));
    }

    /**
     * Test Egalit&eacute; de deux ChainePasse <BR>
     * Condition &eacute;galit&eacute; non v&eacute;rifi&eacute;e :
     * param&egrave;tre null
     */
    @Test
    public void equals_comparaisonAvecNull() {
        ChainePasse c1 = new ChainePasse();
        assertFalse(c1.equals(null));
    }

    // //////// classe interne triInclusions (qui trie par type les caracteres
    // d'un tableau d'inclusions)

    @Test
    public void triInclusions_classeInterne() {
        boolean test;

        ArrayList<Character> inclusions = new ArrayList<>();
        inclusions.add(new Character('4'));
        inclusions.add(new Character('#'));
        inclusions.add(new Character('*'));
        inclusions.add(new Character('m'));
        inclusions.add(new Character('v'));
        inclusions.add(new Character('A'));

        TriInclusions caractTries = new TriInclusions(inclusions);

        // il doit y avoir un chiffre
        test = (caractTries.chiffres.size() == 1);
        // 2 speciaux
        test = test && (caractTries.speciaux.size() == 2);
        // pas d'accents
        test = test && (caractTries.accentsCedil.size() == 0);
        // 2 minuscules
        test = test && (caractTries.minuscules.size() == 2);
        // 1 majuscule
        test = test && (caractTries.majuscules.size() == 1);

        assertTrue(test);
    }

    // methode pour echantillon de test

    public ArrayList<Character> echantillon_exclusions() {
        ArrayList<Character> excl;
        excl = (ArrayList<Character>) Arrays.asList(new Character('0'), new Character('o'), new Character('%'), new Character('i'), new Character('+'), new Character('e'),
                new Character('#'), new Character('a'), new Character('ù'), new Character('â'), new Character('é'), new Character('~'), new Character('Q'), new Character('^'), new Character('ç'), new Character('6'));
        return excl;
    }

    public ArrayList<Character> echantillon_exclu_VoyellesMajuscules() {
        ArrayList<Character> excl = new ArrayList<>();
        excl.add(new Character('A'));
        excl.add(new Character('E'));
        excl.add(new Character('I'));
        excl.add(new Character('O'));
        excl.add(new Character('U'));
        excl.add(new Character('Y'));
        return excl;
    }

    // methode pour echantillon de test

    public ArrayList<Character> echantillon_inclusions() {
        ArrayList<Character> incl = new ArrayList<>();
        incl.add(new Character('1'));
        incl.add(new Character('p'));
        incl.add(new Character('$'));
        incl.add(new Character('j'));
        incl.add(new Character('-'));
        incl.add(new Character('S'));
        incl.add(new Character('{'));
        incl.add(new Character('z'));
        incl.add(new Character('è'));
        incl.add(new Character('ô'));
        incl.add(new Character('à'));
        incl.add(new Character('}'));
        incl.add(new Character('R'));
        incl.add(new Character('@'));
        incl.add(new Character('*'));
        incl.add(new Character('9'));
        return incl;
    }

}
