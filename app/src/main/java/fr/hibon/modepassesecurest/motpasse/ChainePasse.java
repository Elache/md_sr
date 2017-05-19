
package fr.hibon.modepassesecurest;

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
		if (n < 1){
			n = 1;
		}
		if (n > 60){
			n = 60;
		}
			
		ChainePasse mp = new ChainePasse();
		int type;
		String leMP = "";

		for (int i = 0; i < n; i++) {
			type = (int) (Math.random() * 5 + 1);
			switch (type) {
			case 1:
				leMP += Caractere.genereChiffre();
				mp.avecChiffre = true;
				break;
			case 2:
				leMP += Caractere.genereAccentCedil();
				mp.avecAccentCedil = true;
				break;
			case 3:
				leMP += Caractere.genereMajuscule();
				mp.avecMajuscule = true;
				break;
			case 4:
				leMP += Caractere.genereSpecial();
				mp.avecSpecial = true;
				break;
			case 5:
				leMP += Caractere.genereMinuscule();
				mp.avecMinuscule = true;
				break;
			default:
				leMP += Caractere.genereChiffre();
				mp.avecChiffre = true;
				break;
			}
		}
		mp.chaineDuPasse = leMP;
		return mp;
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
	 * @param n
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
	public static ChainePasse genererMotDePasse(int n, boolean chiffres, boolean minusc, boolean majusc, boolean accent,
			boolean special, ArrayList<Integer> exclusions, ArrayList<Integer> inclusions) {

		// nombre de types representés ; permet d'ajouter un caractere par type
		// attendu
		// et completer ensuite la longueur du mot de passe avec n'importe quel
		// caractere
		int nbTypes = 0;

		// variables pour generation du mot de passe
		String leMP = "";
		ChainePasse mp = new ChainePasse();
		TriInclusions inc = new TriInclusions() ;

		// pool initial de caracteres
		Integer[] pool = Caractere.poolCaracteres(chiffres, minusc, majusc, accent, special);

		// mot de passe : limite de 1 à 60 caractères
		if (n < 1)
			n = 1;
		if (n > 60)
			n = 60;

		// extension du pool avec les inclusions
		// tri des inclusions par type
		if (inclusions != null) {
			pool = Caractere.completerPool(pool, inclusions);
			inc = new TriInclusions(inclusions);
		}

		// si le type chiffre est attendu : on tire 1 chiffre
		// sinon si les inclusions comportent des chiffres, on en tire un dedans
		if (chiffres) {
			leMP += Caractere.genererCaractere(Caractere.completerPool(Caractere.getChiffres(), inc.chiffres),
					exclusions);
			mp.avecChiffre = true;
			nbTypes++;
		} else if (inc.chiffres != null && inc.chiffres.size() != 0) {
			leMP += Caractere.genererCaractere(convertirALenTab(inc.chiffres), exclusions);
			mp.avecChiffre = true;
			nbTypes++;
		}

		// si le type minuscules est attendu : on en tire 1
		// sinon si les inclusions comportent des minuscules, on en tire 1
		// dedans
		if (minusc) {
			leMP += Caractere.genererCaractere(Caractere.completerPool(Caractere.getMinuscules(), inc.minuscules),
					exclusions);
			mp.avecMinuscule = true;
			nbTypes++;
		} else if (inc.minuscules != null && inc.minuscules.size() != 0) {
			leMP += Caractere.genererCaractere(convertirALenTab(inc.minuscules), exclusions);
			mp.avecMinuscule = true;
			nbTypes++;
		}

		// si le type majuscules est attendu : on en tire 1
		// sinon si les inclusions comportent des majuscules, on en tire 1
		// dedans
		if (majusc) {
			leMP += Caractere.genererCaractere(Caractere.completerPool(Caractere.getMajuscules(), inc.majuscules),
					exclusions);
			mp.avecMajuscule = true;
			nbTypes++;
		} else if (inc.majuscules != null && inc.majuscules.size() != 0) {
			leMP += Caractere.genererCaractere(convertirALenTab(inc.majuscules), exclusions);
			mp.avecMajuscule = true;
			nbTypes++;
		}

		// si le type 'accent / cedille' est attendu : on en tire 1
		// sinon si les inclusions comportent des 'accent/cedille', on en tire 1
		// dedans
		if (accent) {
			leMP += Caractere.genererCaractere(Caractere.completerPool(Caractere.getAccentcedil(), inc.accentsCedil),
					exclusions);
			mp.avecAccentCedil = true;
			nbTypes++;
		} else if (inc.accentsCedil != null && inc.accentsCedil.size() != 0) {
			leMP += Caractere.genererCaractere(convertirALenTab(inc.accentsCedil), exclusions);
			mp.avecAccentCedil = true;
			nbTypes++;
		}

		// si le type 'caract. speciaux' est attendu : on en tire 1
		// sinon si les inclusions comportent des 'caracteres speciaux', on en
		// tire 1 dedans
		if (special) {
			leMP += Caractere.genererCaractere(Caractere.completerPool(Caractere.getSpeciaux(), inc.speciaux),
					exclusions);
			mp.avecSpecial = true;
			nbTypes++;
		} else if (inc.speciaux != null && inc.speciaux.size() != 0) {
			leMP += Caractere.genererCaractere(convertirALenTab(inc.speciaux), exclusions);
			mp.avecSpecial = true;
			nbTypes++;
		}

		// si aucun type n'a été sélectionné, on utilise des chiffres
		if (nbTypes == 0) {
			leMP += Caractere.genererCaractere(Caractere.getChiffres(), exclusions);
			mp.avecChiffre = true;
			nbTypes = 1;
		}

		// apres avoir généré un caractere de chaque type attendu,
		// on complete le mot de passe jusqu'à longueur voulu avec des
		// caracteres (tous types confondus)
		for (int i = 0; i < n - nbTypes; i++) {
			leMP += Caractere.genererCaractere(pool, exclusions);
		}

		mp.chaineDuPasse = leMP;
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
	public boolean avecChiffre() {
		return avecChiffre;
	}

	
	/**
	 * Verifie si presence d'une minuscule dans le mot de passe
	 * 
	 * @return true si une minuscule au moins est presente
	 */
	public boolean avecMinuscule() {
		return avecMinuscule;
	}

	
	/**
	 * Verifie si presence d'une majuscule dans le mot de passe
	 * 
	 * @return true si une majuscule au moins est presente
	 */
	public boolean avecMajuscule() {
		return avecMajuscule;
	}

	
	/**
	 * Verifie si presence d'une minuscule accentu&eacute;e (ou c cedille) dans
	 * le mot de passe
	 * 
	 * @return true si un accent au moins est present
	 */
	public boolean avecAccentcedil() {
		return avecAccentCedil;
	}
	

	/**
	 * Verifie si presence d'un caractere special dans le mot de passe
	 * 
	 * @return true si une caractere special au moins est presente
	 */
	public boolean avecSpecial() {
		return avecSpecial;
	}

	
	/**
	 * toString() 
	 */
	public String toString() {
		return chaineDuPasse ;
	}
	

	// methode outil
	/**
	 * Convertit une ArrayList d'Integer en tableau d'Integer
	 * 
	 * @param al
	 *            ArrayList d'Integer
	 * @return tableau d'Integer
	 */
	public static Integer[] convertirALenTab(ArrayList<Integer> al) {
		Integer[] tab = new Integer[al.size()];
		for (int i = 0; i < al.size(); i++) {
			tab[i] = al.get(i);
		}
		return tab;
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

	ArrayList<Integer> chiffres, minuscules, majuscules, accentsCedil, speciaux;

	public TriInclusions() {
		chiffres = new ArrayList<>();
		minuscules = new ArrayList<>();
		majuscules = new ArrayList<>();
		accentsCedil = new ArrayList<>();
		speciaux = new ArrayList<>();
	}
	
	public TriInclusions(ArrayList<Integer> inclusions) {
		this() ;
		for (Integer incl : inclusions) {
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
