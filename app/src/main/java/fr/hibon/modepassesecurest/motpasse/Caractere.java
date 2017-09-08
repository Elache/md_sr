package fr.hibon.modepassesecurest.motpasse;

import java.util.ArrayList;

/**
 * Caractere d&eacute;finit 5 types de caract&egrave;res (110 au total) <BR>
 * Chiffres, lettres majuscules et minuscules, lettres accentu&eacute;es (12),
 * caract&egrave;res sp&eacute;ciaux (36).
 * <BR>Services : <BR>-determiner type d'un caractere ; 
 * <BR>- generer un caractere (de type-s specifi&eacute;-s ; en excluant une liste specifique de caracteres ; en incluant une liste specifique de caracteres)
 * <BR>- generer des pools de caracteres suivant parametres
 */
public class Caractere {

	private char leChar;

	// Listes exhaustives ordonnées
	private static ArrayList<Character> ensembleChiffres ;  // 10 chiffres [48-57]
	private static ArrayList<Character> ensembleMinuscules ; // 26 minusc [97-122]
	private static ArrayList<Character> ensembleMajuscules ; // 26 majusc [65-90]
	private static ArrayList<Character> ensembleAccentCedil ; // 12 minusc. accentuees, cedille
	private static ArrayList<Character> ensembleSpeciaux ; // 36 caract speciaux

	/**
	 * Constructeur affecte le caractere du parametre et initialise les 5 listes de caracteres (=types)
	 * @param caract caractere &agrave; affecter
	 */
	public Caractere(char caract) {
		this.leChar = caract;
		lesChiffres();
		lesMinuscules();
		lesMajuscules() ;
		lesAccents() ;
		lesSpeciaux() ;
	}

	public Caractere(int valChar) {
		this((char) valChar) ;
	}

	public Caractere() {
		this('\0') ;
	}


	/////////////// TESTER LE TYPE D'UN CARACTERE ///////////////

	/**
	 * Teste que la valeur estUnChiffre
	 * @return true si le type est bon
	 */
	boolean estUnChiffre() {
		return ensembleChiffres.contains(leChar);
	}

	/**
	 * Teste que la valeur estUneMinuscule
	 * @return true si le type est bon
	 */
	boolean estUneMinuscule() {
		return ensembleMinuscules.contains(leChar);
	}

	/**
	 * Teste que la valeur estUneMajuscule
	 * @return true si le type est bon
	 */
	boolean estUneMajuscule() {
		return ensembleMajuscules.contains(leChar);
	}

	/**
	 * Teste que la valeur estUnAccentCedil
	 * @return true si le type est bon
	 */
	boolean estUnAccentCedil() {
		return ensembleAccentCedil.contains(leChar);
	}

	/**
	 * Teste que la valeur estUnSpecial
	 * @return true si le type est bon
	 */
	boolean estUnSpecial() {
		return ensembleSpeciaux.contains(leChar);
	}


	/////////////// GENERER UN CARACTERE ///////////////

	/**
	 * Genere un caractere, pris dans une liste (parametre)
	 * @param table liste des caracteres possibles pour la generation
	 * @return un caractere faisant partie des caracteres possibles (table)
	 */
	static char genererCaractere(ArrayList<Character> table) {
		return genererCaractere(table, null);
	}

	/**
	 * Genere un caractere, pris dans une liste, en tenant compte d'exclusions (caracteres &agrave; ne pas utiliser)
	 * @param table liste des caracteres possibles pour la generation
	 * @param exclusions liste de caracteres &agrave; ne pas utiliser
	 * @return un caractere faisant partie des caracteres possibles (table hors exclusions)
	 */
	static char genererCaractere(ArrayList<Character> table, ArrayList<Character> exclusions) {
		ArrayList<Character> pool ;
		if(table.size() == 0 || table == null)
			pool = ensembleChiffres ;
		else
			pool = table ;


		char c = pool.get((int) (Math.random() * pool.size())) ;
		if (exclusions == null) {
			return c;
		}
		while (exclusions.contains(c)) {
			c = pool.get((int) (Math.random() * pool.size())) ;
		}
		return c;
	}

	/**
	 * Genere un caractere, pris dans une liste qui est compl&eacute;t&eacute;e d'inclusions (liste de caracteres possibles) et r&eacute;duite d'une liste d'exclusions (caracteres &agrave; ne pas utiliser)
	 *
	 * @param table liste des caracteres possibles pour la generation
	 * @param exclusions liste de caracteres &agrave; ne pas utiliser
	 * @param inclusions liste de caracteres pouvant etre utilis&eacute;s, &agrave; ajouter &agrave; la table
	 * @return un caractere faisant partie des caracteres possibles (table - exclusions + exclusions)
	 */
	static char genererCaractere(ArrayList<Character> table, ArrayList<Character> exclusions, ArrayList<Character> inclusions) {
		ArrayList<Character> poolAvecInclusions = completerPool(table, inclusions) ;
		return genererCaractere(poolAvecInclusions, exclusions) ;
	}


	// ///////////// GENERER UN TYPE DE CARACTERE ///////////////

	/**
	 * Genere un : chiffre
	 * @return un caractere : chiffre
	 */
	static char genereChiffre() {
		Caractere c = new Caractere() ;
		return genererCaractere(c.getChiffres());
	}

	/**
	 * Genere une lettre minuscule
	 * @return un caractere : lettre minuscule
	 */
	static char genereMinuscule() {
		Caractere c = new Caractere() ;
		return genererCaractere(c.getMinuscules());
	}

	/**
	 * Genere une lettre majuscule
	 * @return un caractere : lettre majuscule
	 */
	static char genereMajuscule() {
		Caractere c = new Caractere() ;
		return genererCaractere(c.getMajuscules());
	}

	/**
	 * Genere un : caract&egrave;re accentu&eacute;, un &ccedil; ou un ^
	 * @return un caractere : caractere accentu&eacute;
	 */
	static char genereAccentCedil() {
		Caractere c = new Caractere() ;
		return genererCaractere(c.getAccentcedil());
	}

	/**
	 * Genere un : caractere special
	 * @return un caractere : caractere special
	 */
	static char genereSpecial() {
		Caractere c = new Caractere() ;
		return genererCaractere(c.getSpeciaux());
	}

	/////////////// TABLEAU AVEC TYPES CHOISIS ///////////////

	/**
	 * Constitue un pool de caracteres, suivant selection des types autoris&eacute;s (chiffres, minuscules, majuscules, lettres accentu&eacute;es, caracteres speciaux)
	 * @param chiffres true si chiffres autoris&eacute;s
	 * @param minusc true si minuscules autoris&eacute;es
	 * @param majusc true si majuscules autoris&eacute;es
	 * @param accent true si accents autoris&eacute;s
	 * @param special true si caracteres speciaux autoris&eacute;s
	 * @return liste de caracteres
	 */
	static ArrayList<Character> poolCaracteres(boolean chiffres, boolean minusc, boolean majusc, boolean accent,
													  boolean special) {
		Caractere c = new Caractere() ;
		ArrayList<Character> list = new ArrayList<>();
		if (chiffres)
			list.addAll(c.getChiffres());
		if (minusc)
			list.addAll(c.getMinuscules());
		if (majusc)
			list.addAll(c.getMajuscules());
		if (accent)
			list.addAll(c.getAccentcedil());
		if (special)
			list.addAll(c.getSpeciaux());
		if (!chiffres && !minusc && !majusc && !accent && !special) {
			list.addAll(c.getChiffres());
		}
		return list;
	}

	/**
	 *  Complete un pool de caracteres en ajoutant une liste d'inclusions
	 * @param pool pool initial
	 * @param inclusions liste de caracteres &agrave; ajouter
	 * @return pool complet&eacute; avec les inclusions
	 */
	static ArrayList<Character> completerPool(ArrayList<Character>  pool, ArrayList<Character> inclusions) {
		if(inclusions != null && inclusions.size() != 0){
			// dédoublonne
			for (Character inc : inclusions) {
				if (!pool.contains(inc)) {
					pool.add(inc);
				}
			}
		}
		return pool;
	}

	/////////////// TABLEAU DES 110 CARACTERES ///////////////

	/**
	 * Genere la liste des 110 caracteres de l'application
	 * <BR>(= Ensemble des caracteres pouvant etre utilis&eacute;s pour generer des mots de passe) 
	 * @return liste des 110 caracteres
	 */
	static ArrayList<Character> les110() {
		Caractere c = new Caractere() ;
		ArrayList<Character> list = c.getChiffres();
		list.addAll(c.getMinuscules());
		list.addAll(c.getMajuscules());
		list.addAll(c.getAccentcedil());
		list.addAll(c.getSpeciaux());
		return list;
	}

	// //////// Prepare la repartition entre types de caracteres /////////

	/**
	 * Genere la liste des 10 chiffres
	 */
	private void lesChiffres() {
		Caractere.ensembleChiffres = new ArrayList<>() ;
		for (int i = 48 ; i <= 57 ; i++) {
			ensembleChiffres.add((char) i);
		}
	}

	/**
	 * Genere la liste des minuscules
	 */
	private void lesMinuscules() {
		Caractere.ensembleMinuscules = new ArrayList<>() ;
		for(int i = 97 ; i <= 122 ; i++) {
			ensembleMinuscules.add((char) i);
		}
	}

	/**
	 * Genere la liste des majuscules
	 */
	private void lesMajuscules() {
		Caractere.ensembleMajuscules = new ArrayList<>() ;
		for(int i = 65 ; i <= 90 ; i++) {
			ensembleMajuscules.add((char) i);
		}
	}

	/**
	 * Genere la liste des 12 accents / cedille
	 */
	private void lesAccents() {
		Caractere.ensembleAccentCedil = new ArrayList<>() ;
		ensembleAccentCedil.add((char) 224) ;
		ensembleAccentCedil.add((char) 226) ;
		ensembleAccentCedil.add((char) 231) ;
		ensembleAccentCedil.add((char) 232) ;
		ensembleAccentCedil.add((char) 233) ;
		ensembleAccentCedil.add((char) 234) ;
		ensembleAccentCedil.add((char) 235) ;
		ensembleAccentCedil.add((char) 238) ;
		ensembleAccentCedil.add((char) 239) ;
		ensembleAccentCedil.add((char) 244) ;
		ensembleAccentCedil.add((char) 249) ;
		ensembleAccentCedil.add((char) 251) ;
	}

	// les 36 caract speciaux
	/**
	 * Genere la liste des 36 caracteres speciaux de l'application
	 */
	private void lesSpeciaux() {
		Caractere.ensembleSpeciaux = new ArrayList<>() ;
		for (int i = 33; i <= 47; i++)
			ensembleSpeciaux.add((char) i);
		for (int i = 58; i <= 64; i++)
			ensembleSpeciaux.add((char) i);
		for (int i = 91; i <= 96; i++)
			ensembleSpeciaux.add((char) i);
		for (int i = 123; i <= 126; i++)
			ensembleSpeciaux.add((char) i);
		ensembleSpeciaux.add((char) 163);
		ensembleSpeciaux.add((char) 166);
	//	ensembleSpeciaux.add((char) 167); §
		ensembleSpeciaux.add((char) 176);
	}



	// ////////////////Getters ////////////////////

	ArrayList<Character> getChiffres() {
		return ensembleChiffres;
	}

	ArrayList<Character> getMinuscules() {
		return ensembleMinuscules;
	}
	ArrayList<Character> getMajuscules() {
		return ensembleMajuscules;
	}

	ArrayList<Character> getAccentcedil() {
		return ensembleAccentCedil;
	}

	ArrayList<Character> getSpeciaux() {
		return ensembleSpeciaux;
	}


	// pour this.leChar et sa valeur ASCII 

	void setLeChar(int valChar) {
		setLeChar((char) valChar);
	}

	void setLeChar(char caract) {
		this.leChar = caract ;
	}

	char getLeChar() {
		return this.leChar ;
	}
}
