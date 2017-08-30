package fr.hibon.modepassesecurest.motpasse;


import java.util.ArrayList;

/**
 * Attributs : chaine, longueur, caracterisation de la chaine (types presents /
 * absents) Services : - analyser composition d'une chaine <BR>
 * - generer une chaine pour mot de passe : par défaut (10 caracteres parmi 110)
 * ; de longueur parametr&eacute;e ; avec des caracteres specifiques &agrave;
 * inclure et/ou exclure <BR>
 * - completer un mot de passe pour augmenter sa force
 */
public class ChainePasse {

	private String chaineDuPasse;

	private int longueur;

	private boolean avecChiffre, avecMinuscule, avecMajuscule, avecAccentCedil, avecSpecial;

	ChainePasse() {
		chaineDuPasse = null;
		longueur = 1;
		avecChiffre = false;
		avecMinuscule = false;
		avecMajuscule = false;
		avecAccentCedil = false;
		avecSpecial = false;
	}

	// Construit un ChainePasse à partir d'une chaine et analyse types
	// caractères présents
	/**
	 * Instancie un ChainePasse &agrave; partir d'une chaine de caracteres <BR>
	 * (inclut la détermination de sa composition)
	 *
	 * @param chaineRef
	 *            chaine pour tester composition et constituer un mot de passe
	 * @return ChainePasse instance avec le mot de passe et sa composition
	 */
	public static ChainePasse composition(String chaineRef) {
		ChainePasse mp = new ChainePasse();
		Caractere enCours;
		if (chaineRef == null)
			return mp;
		mp.chaineDuPasse = chaineRef;
		mp.longueur = chaineRef.length();
		for (int i = 0; i < chaineRef.length(); i++) {
			enCours = new Caractere(chaineRef.charAt(i));
			if (enCours.estUnChiffre())
				mp.avecChiffre = true;
			if (enCours.estUneMinuscule())
				mp.avecMinuscule = true;
			if (enCours.estUneMajuscule())
				mp.avecMajuscule = true;
			if (enCours.estUnAccentCedil())
				mp.avecAccentCedil = true;
			if (enCours.estUnSpecial())
				mp.avecSpecial = true;
		}
		return mp;
	}

	// La Chaine : getter, setter, longueur

	public String getChaineDuPasse() {
		return chaineDuPasse;
	}

	void setMotPasse(String leMot) {
		chaineDuPasse = leMot;
	}

	public int longeurMot() {
		return this.longueur;
	}

	// Generer ChainePasse Par défaut

	/**
	 * Genere un ChainePasse, mot de passe par defaut de l'application : 10
	 * caracteres parmi 110
	 *
	 * @return ChainePasse mot de passe de longueur 10 (et composition
	 *         encapsul&eacute;e)
	 */
	public static ChainePasse genererMotDePasse() {
		return genererMotDePasse(10);
	}

	/**
	 * /** Genere un ChainePasse, mot de passe de longueur parametr&eacute;e
	 * (parmi 110)
	 *
	 * @param n
	 *            longueur voulue pour le mot de passe
	 * @return ChainePasse mot de passe de longueur parametr&eacute;e (et
	 *         composition encapsul&eacute;e)
	 */
	public static ChainePasse genererMotDePasse(int n) {
		return genererMotDePasse(n, true, true, true, true, true, null, null);
	}

	// Generer ChainePasse Paramétré
	/**
	 * Genere un ChainePasse, de longueur parametr&eacute;e, en utilisant les
	 * caracteres des types selectionn&eacute;s (possibilit&eacute; d'une liste
	 * de caracteres &agrave; inclure ; d'une liste de caracteres &agrave;
	 * exclure) <BR>
	 * Pour tout type attendu, au moins un caractere est
	 * g&eacute;n&eacute;r&eacute;
	 *
	 * @param nb
	 *            longueur de mot de passe demand&eacute;e
	 * @param chiffres
	 *            true si chiffres autoris&eacute;s
	 * @param minusc
	 *            true si minuscules autoris&eacute;es
	 * @param majusc
	 *            true si majuscules autoris&eacute;es
	 * @param accent
	 *            true si accents autoris&eacute;s
	 * @param special
	 *            true si caracteres speciaux autoris&eacute;s
	 * @param exclusions
	 *            liste de caracteres &agrave; ne pas utiliser
	 * @param inclusions
	 *            liste de caracteres pouvant etre utilis&eacute;s, &agrave;
	 *            ajouter &agrave; la table
	 * @return ChainePasse (mot de passe et composition)
	 */
	public static ChainePasse genererMotDePasse(int nb, boolean chiffres, boolean minusc, boolean majusc,
												boolean accent, boolean special, ArrayList<Character> exclusions, ArrayList<Character> inclusions) {

		// attentes
		int longueur ;
		int nbTypes ;

		// pool initial de caracteres
		ArrayList<Character> pool ;

		// mot de passe
		String leMP  ;
		ChainePasse mp ;

		// outils
		TriInclusions inc  ;
		Caractere cc  ;


		longueur = nb;
		// longueur max 60 caract (longueur)
		if (nb > 60)
			longueur = 60;
		// longueur min 1 caract (nb)
		if (nb < 1)
			nb = 1;

		// nb de types : pour ajouter 1 caract par type
		nbTypes = (chiffres ? 1 : 0) + (minusc ? 1 : 0) ;
		nbTypes +=	(majusc ? 1 : 0) + (accent ? 1 : 0) + (special ? 1 : 0);
		// pour generer autant de caracteres que de types
		if (nbTypes > longueur)
			longueur = nbTypes;


		// variables pour generation du mot de passe
		mp = new ChainePasse();
		leMP = "";

		//outils
		inc = new TriInclusions();
		cc = new Caractere();

		// pool initial de caracteres
		pool = Caractere.poolCaracteres(chiffres, minusc, majusc, accent, special);
		// aucun type choisi : chiffres utilises
		if (!chiffres && !minusc && !majusc && !accent && !special)
			chiffres = true;

		// extension du pool avec les inclusions
		// et tri des inclusions par type
		if (inclusions != null) {
			pool = Caractere.completerPool(pool, inclusions);
			inc = new TriInclusions(inclusions);
		}

		// si le type chiffre est attendu : on tire 1 chiffre
		// sinon si les inclusions comportent des chiffres, on en tire un dedans
		if (chiffres) {
			leMP += Caractere.genererCaractere(Caractere.completerPool(cc.getChiffres(), inc.chiffres), exclusions);
			mp.avecChiffre = true;
		} else if (inc.chiffres != null && inc.chiffres.size() != 0) {
			leMP += Caractere.genererCaractere(inc.chiffres, exclusions);
			mp.avecChiffre = true;
			// type present dans inclusions
			nbTypes++;
		}

		// si le type minuscules est attendu : on en tire 1
		// sinon si les inclusions comportent des minuscules, on en tire 1
		// dedans
		if (minusc) {
			leMP += Caractere.genererCaractere(Caractere.completerPool(cc.getMinuscules(), inc.minuscules), exclusions);
			mp.avecMinuscule = true;
		} else if (inc.minuscules != null && inc.minuscules.size() != 0) {
			leMP += Caractere.genererCaractere(inc.minuscules, exclusions);
			mp.avecMinuscule = true;
			// type present dans inclusions
			nbTypes++;
		}

		// si le type majuscules est attendu : on en tire 1
		// sinon si les inclusions comportent des majuscules, on en tire 1
		// dedans
		if (majusc) {
			leMP += Caractere.genererCaractere(Caractere.completerPool(cc.getMajuscules(), inc.majuscules), exclusions);
			mp.avecMajuscule = true;
		} else if (inc.majuscules != null && inc.majuscules.size() != 0) {
			leMP += Caractere.genererCaractere(inc.majuscules, exclusions);
			mp.avecMajuscule = true;
			// type present dans inclusions
			nbTypes++;
		}

		// si le type 'accent / cedille' est attendu : on en tire 1
		// sinon si les inclusions comportent des 'accent/cedille', on en tire 1
		// dedans
		if (accent) {
			leMP += Caractere.genererCaractere(Caractere.completerPool(cc.getAccentcedil(), inc.accentsCedil),
					exclusions);
			mp.avecAccentCedil = true;
		} else if (inc.accentsCedil != null && inc.accentsCedil.size() != 0) {
			leMP += Caractere.genererCaractere(inc.accentsCedil, exclusions);
			mp.avecAccentCedil = true;
			// type present dans inclusions
			nbTypes++;
		}

		// si le type 'caract. speciaux' est attendu : on en tire 1
		// sinon si les inclusions comportent des 'caracteres speciaux', on en
		// tire 1 dedans
		if (special) {
			leMP += Caractere.genererCaractere(Caractere.completerPool(cc.getSpeciaux(), inc.speciaux), exclusions);
			mp.avecSpecial = true;
		} else if (inc.speciaux != null && inc.speciaux.size() != 0) {
			leMP += Caractere.genererCaractere(inc.speciaux, exclusions);
			mp.avecSpecial = true;
			// type present dans inclusions
			nbTypes++;
		}

		// apres avoir généré 1 caract de chaque type attendu,
		// on complete le mot de passe jusqu'à longueur voulue avec des
		// caracteres (tous types autorises confondus)
		for (int i = 1; i <= longueur - nbTypes; i++) {
			leMP += Caractere.genererCaractere(pool, exclusions);
		}

		// si nb types voulus > longueur voulue, on renvoie seulement la longueur voulue
		if(nbTypes > nb)
			leMP = leMP.substring(0,nb) ;

		// le mot de passe
		mp.chaineDuPasse = leMP;
		mp.longueur = leMP.length();
		return mp;
	}

	// Completer une ChainePasse à 10
	/**
	 * Complete un mot de passe trop court (jusqu'&agrave; une longueur de 10)
	 *
	 * @param motPasse
	 *            le mot de passe trop court &agrave; compl&eacute;ter
	 * @return ChainePasse avec mot de passe de 10 caracteres
	 */
	public static ChainePasse passeComplete10(ChainePasse motPasse) {
		String nouvelleChaine = motPasse.chaineDuPasse;
		nouvelleChaine += genererMotDePasse(10 - motPasse.longueur);
		return composition(nouvelleChaine);
	}

	// getters pour tester composition mot de passe

	/**
	 * Verifie si presence d'un chiffre dans le mot de passe
	 *
	 * @return true si un chiffre au moins est present
	 */
	boolean avecChiffre() {
		return avecChiffre;
	}

	/**
	 * Verifie si presence d'une minuscule dans le mot de passe
	 *
	 * @return true si une minuscule au moins est presente
	 */
	boolean avecMinuscule() {
		return avecMinuscule;
	}

	/**
	 * Verifie si presence d'une majuscule dans le mot de passe
	 *
	 * @return true si une majuscule au moins est presente
	 */
	boolean avecMajuscule() {
		return avecMajuscule;
	}

	/**
	 * Verifie si presence d'une minuscule accentu&eacute;e (ou c cedille) dans
	 * le mot de passe
	 *
	 * @return true si un accent au moins est present
	 */
	boolean avecAccentcedil() {
		return avecAccentCedil;
	}

	/**
	 * Verifie si presence d'un caractere special dans le mot de passe
	 *
	 * @return true si une caractere special au moins est presente
	 */
	boolean avecSpecial() {
		return avecSpecial;
	}

	/**
	 * Etablit un texte listant les types de caracteres présents
	 */
	public String caracteresPresents() {
		String presence = ""; 
		if(avecChiffre)
			presence += "-Chiffres- " ; 
		if(avecMinuscule)
			presence += "-Minuscules- " ;
		if(avecMajuscule)
			presence += "-Majuscules- " ;
		if(avecAccentCedil)
			presence += "-Caractères accentués-  " ;
		if(avecSpecial)	
			presence += "-Caractères spéciaux- " ;
		return presence;
	}
	
	/**
	 * toString()
	 */
	public String toString() {
		return chaineDuPasse;
	}

	
	/**
	 * Equals : deux ChainePasse sont &eacute;gaux si les chaines qui les
	 * constituent sont identiques
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChainePasse other = (ChainePasse) obj;
		if (chaineDuPasse == null) {
			if (other.chaineDuPasse != null)
				return false;
		} else if (!chaineDuPasse.equals(other.chaineDuPasse))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (avecAccentCedil ? 1231 : 1237);
		result = prime * result + (avecChiffre ? 1231 : 1237);
		result = prime * result + (avecSpecial ? 1231 : 1237);
		result = prime * result + ((chaineDuPasse == null) ? 0 : chaineDuPasse.hashCode());
		result = prime * result + longueur;
		return result;
	}

}

// classe-outil : representation des types de caracteres dans une liste
// d'inclusions
/**
 * Classe outil, interne, pour r&eacute;partir les caracteres que l'on peut
 * inclure en plus, suivant leur type
 *
 *
 */
class TriInclusions {

	final ArrayList<Character> chiffres;
    final ArrayList<Character> minuscules;
    final ArrayList<Character> majuscules;
    final ArrayList<Character> accentsCedil;
    final ArrayList<Character> speciaux;

	TriInclusions() {
		chiffres = new ArrayList<>();
		minuscules = new ArrayList<>();
		majuscules = new ArrayList<>();
		accentsCedil = new ArrayList<>();
		speciaux = new ArrayList<>();
	}

	TriInclusions(ArrayList<Character> inclusions) {
		this();
		for (Character incl : inclusions) {
			if (new Caractere(incl).estUnChiffre())
				chiffres.add(incl);
			if (new Caractere(incl).estUneMinuscule())
				minuscules.add(incl);
			if (new Caractere(incl).estUneMajuscule())
				majuscules.add(incl);
			if (new Caractere(incl).estUnAccentCedil())
				accentsCedil.add(incl);
			if (new Caractere(incl).estUnSpecial())
				speciaux.add(incl);
		}
	}

}
