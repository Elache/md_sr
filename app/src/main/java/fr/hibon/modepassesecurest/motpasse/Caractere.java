package fr.hibon.modepassesecurest;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Caractere d&eacute;finit 5 types de caract&egrave;res (110 au total) <BR>
 * Chiffres, lettres majuscules et minuscules, lettres accentu&eacute;es,
 * caract&egrave;res sp&eacute;ciaux.
 * <BR>Services : determiner type d'un caractere ; <BR>
 * generer un caractere (de type-s specifi&eacute;-s ; en excluant une liste specifique de caracteres ; en incluant une liste specifique de caracteres)
*<BR> generer des pools de caracteres suivant parametres
 */
public class Caractere {

	private int laValeurDuChar;

	// Chiffres, Majuscules et Minuscules : plage [codes ASCII]
	private static final Integer[] CHIFFRES = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57 }; // 10
	private static final Integer[] MAJUSCULES = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90 }; // 26
	private static final Integer[] MINUSCULES = { 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122}; // 26 

	// Caractères accentués et spéciaux : listes exhaustives ordonnées
	private static final Integer[] ACCENTCEDIL = { 224, 226, 231, 232, 233, 234, 235, 238, 239, 244, 249, 251 }; // 12
	private static final Integer[] SPECIAUX = caracteresSpeciaux(); // 36

	public Caractere(int valChar) {
		this.laValeurDuChar = valChar;
	}

	/////////////// TESTER LE TYPE D'UN CARACTERE ///////////////

	/**
	 * Teste que la valeur estUnChiffre()
	 * @return true si le type est bon
	 */
	public boolean estUnChiffre() {
		return estDansTable(CHIFFRES);
	}

	/**
	 * Teste que la valeur estUneMinuscule()
	 * @return true si le type est bon
	 */
	public boolean estUneMinuscule() {
		return estDansTable(MINUSCULES);
	}

	/**
	 * Teste que la valeur estUneMajuscule()
	 * @return true si le type est bon
	 */
	public boolean estUneMajuscule() {
		return estDansTable(MAJUSCULES);
	}

	/**
	 * Teste que la valeur estUnAccentCedil()
	 * @return true si le type est bon
	 */
	public boolean estUnAccentCedil() {
		return estDansTable(ACCENTCEDIL);
	}

	/**
	 * Teste que la valeur estUnSpecial()
	 * @return true si le type est bon
	 */
	public boolean estUnSpecial() {
		return estDansTable(SPECIAUX);
	}

	// // // // // // // // //
	/**
	 * Verification de la presence d'un caractere dans un tableau (cf. types de caracteres)
	 * @param table tableau de caracteres de reference
	 * @return true si le caractere est present dans le tableau de reference
	 */
	boolean estDansTable(Integer[] table) {
		for (int i = 0; i < table.length; i++) {
			if (table[i] > this.laValeurDuChar)
				return false;
			if (table[i] == this.laValeurDuChar)
				return true;
		}
		return false;
	}

	/////////////// GENERER UN CARACTERE ///////////////

	/**
	 * Genere un caractere, pris dans une table (parametre)
	 * @param table table des caracteres possibles pour la generation
	 * @return un caractere faisant partie des caracteres possibles (table)
	 */
	public static char genererCaractere(Integer[] table) {
		return genererCaractere(table, null);
	}

	/**
	 * Genere un caractere, pris dans une table, en tenant compte d'exclusions (caracteres &agrave; ne pas utiliser)
	 * @param table table des caracteres possibles pour la generation
	 * @param exclusions liste de caracteres &agrave; ne pas utiliser
	 * @return un caractere faisant partie des caracteres possibles (table - exclusions)
	 */
	public static char genererCaractere(Integer[] table, ArrayList<Integer> exclusions) {
		char c = (char) (int) (table[(int) (Math.random() * table.length)]);
		if (exclusions == null) {
			return c;
		}
		while (exclusions.contains((int) c)) {
			c = (char) (int) (table[(int) (Math.random() * table.length)]);
		}
		return c;
	}

	/**
	 * Genere un caractere, pris dans une table qui est compl&eacute;t&eacute;e d'inclusions (liste de caracteres possible) et r&eacute;duite d'une liste d'exclusions (caracteres &agrave; ne pas utiliser)
	 * 
	 * @param table table des caracteres possibles pour la generation
	 * @param exclusions liste de caracteres &agrave; ne pas utiliser
	 * @param inclusions liste de caracteres pouvant etre utilis&eacute;s, &agrave; ajouter &agrave; la table
	 * @return un caractere faisant partie des caracteres possibles (table - exclusions + exclusions)
	 * @return
	 */
	public static char genererCaractere(Integer[] table, ArrayList<Integer> exclusions, ArrayList<Integer> inclusions) {
		Integer[] poolAvecInclusions = completerPool(table, inclusions) ; 
		return genererCaractere(poolAvecInclusions, exclusions) ; 
	}


	// ///////////// GENERER UN TYPE DE CARACTERE ///////////////
	
	/**
	 * Genere un : chiffre
	 * @return un caractere : chiffre
	 */
	public static char genereChiffre() {
		return genererCaractere(CHIFFRES);
	}

	/**
	 * Genere une lettre minuscule
	 * @return un caractere : lettre minuscule
	 */
	public static char genereMinuscule() {
		return genererCaractere(MINUSCULES);
	}

	/**
	 * Genere une lettre majuscule
	 * @return un caractere : lettre majuscule
	 */
	public static char genereMajuscule() {
		return genererCaractere(MAJUSCULES);
	}

	/**
	 * Genere un : caract&egrave;re accentu&eacute;, un &ccedil; ou un ^
	 * @return un caractere : caractere accentu&eacute;
	 */
	public static char genereAccentCedil() {
		return genererCaractere(ACCENTCEDIL);
	}

	/**
	 * Genere un : caractere special
	 * @return un caractere : caractere special
	 */
	public static char genereSpecial() {
		return genererCaractere(SPECIAUX);
	}

	/////////////// TABLEAU AVEC TYPES CHOISIS ///////////////
	
	/**
	 * Constitue un pool de caracteres, suivant selection des types autoris&eacute;s (chiffres, minuscules, majuscules, lettres accentu&eacute;es, caracteres speciaux)
	 * @param chiffres true si chiffres autoris&eacute;s
	 * @param minusc true si minuscules autoris&eacute;es
	 * @param majusc true si majuscules autoris&eacute;es
	 * @param accent true si accents autoris&eacute;s
	 * @param special true si caracteres speciaux autoris&eacute;s
	 * @return tableau de caracteres
	 */
	public static Integer[] poolCaracteres(boolean chiffres, boolean minusc, boolean majusc, boolean accent,
			boolean special) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		if (chiffres)
			list.addAll(Arrays.asList(CHIFFRES));
		if (minusc)
			list.addAll(Arrays.asList(MINUSCULES));
		if (majusc)
			list.addAll(Arrays.asList(MAJUSCULES));
		if (accent)
			list.addAll(Arrays.asList(ACCENTCEDIL));
		if (special)
			list.addAll(Arrays.asList(SPECIAUX));
		if (!chiffres && !minusc && !majusc && !accent && !special) {
			list.addAll(Arrays.asList(CHIFFRES));
		}
		Integer[] tabSp = new Integer[list.size()];
		tabSp = list.toArray(tabSp);
		return tabSp;
	}

	/**
	 *  Complete un pool de caracteres en ajoutant une liste d'inclusions 
	 * @param pool pool initial
	 * @param inclusions liste de caracteres &agrave; ajouter
	 * @return pool complet&eacute; avec les inclusions
	 */
	public static Integer[] completerPool(Integer[] pool, ArrayList<Integer> inclusions) {
		Integer[] poolCompletTab;
		ArrayList<Integer> poolEnCoursAL = new ArrayList<>();
		poolEnCoursAL.addAll((Arrays.asList(pool)));
		if(inclusions != null && inclusions.size() != 0){
			// dédoublonne
			for (Integer inc : inclusions) {
				if (!poolEnCoursAL.contains(inc)) {
					poolEnCoursAL.add(inc);
				}
			}
		}
		poolCompletTab = new Integer[poolEnCoursAL.size()];
		poolCompletTab = poolEnCoursAL.toArray(poolCompletTab);
		return poolCompletTab;
	}

	/////////////// TABLEAU DES 110 CARACTERES ///////////////

	/**
	 * Genere le Tableau des 110 caracteres de l'application
	 * <BR>(= Ensemble des caracteres pouvant etre utilis&eacute;s pour generer des mots de passe) 
	 * @return tableau des 110 caracteres
	 */
	public static Integer[] les110() {
		ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(CHIFFRES));
		list.addAll((Arrays.asList(MINUSCULES)));
		list.addAll((Arrays.asList(MAJUSCULES)));
		list.addAll((Arrays.asList(ACCENTCEDIL)));
		list.addAll((Arrays.asList(SPECIAUX)));
		Integer[] tabList = new Integer[list.size()];
		for (int i = 0; i < list.size(); i++) {
			tabList[i] = list.get(i);
		}
		return tabList;
	}

	/////////////// TABLEAU DE 36 CARACTERES SPECIAUX ///////////////


	/**
	 * Genere le Tableau des 36 caracteres speciaux de l'application
	 * @return tableau des 36 caracteres speciaux
	 */
	static Integer[] caracteresSpeciaux() {
		ArrayList<Integer> sp = new ArrayList<>();
		Integer[] tabSp;
		for (int i = 33; i <= 47; i++)
			sp.add(i);
		for (int i = 58; i <= 64; i++)
			sp.add(i);
		for (int i = 91; i <= 96; i++)
			sp.add(i);
		for (int i = 123; i <= 126; i++)
			sp.add(i);
		sp.add(163);
		sp.add(166);
		sp.add(167);
		sp.add(176);
		tabSp = new Integer[sp.size()];
		tabSp = sp.toArray(tabSp);
		return tabSp;
	}

	
	
	// ////////////////Getters ////////////////////
	
	public static Integer[] getChiffres() {
		return CHIFFRES;
	}
	
	public static Integer[] getMajuscules() {
		return MAJUSCULES;
	}

	public static Integer[] getMinuscules() {
		return MINUSCULES;
	}

	public static Integer[] getAccentcedil() {
		return ACCENTCEDIL;
	}

	public static Integer[] getSpeciaux() {
		return SPECIAUX;
	}

	
	// pour la valeur ASCII du char
	
	public void setLaValeurDuChar(int valChar) {
		this.laValeurDuChar = valChar;
	}
	
	public int getLaValeurDuChar() {
		return this.laValeurDuChar ;
	}
}
