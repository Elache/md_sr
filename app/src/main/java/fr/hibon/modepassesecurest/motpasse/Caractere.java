package fr.hibon.modepassesecurest;

import java.util.ArrayList;
import java.util.Arrays;

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
	private static ArrayList<Character> ensembleAccentCedil ; // 12 minusc. accentuees, cedille {224, 226,231,232,233,234,235,238,239,244,249,251};
	private static ArrayList<Character> ensembleSpeciaux ; // = caracteresSpeciaux(); // 36 caract speciaux

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
	 * Teste que la valeur estUnChiffre()
	 * @return true si le type est bon
	 */
	public boolean estUnChiffre() {
		return ensembleChiffres.contains(leChar);
	}

	/**
	 * Teste que la valeur estUneMinuscule()
	 * @return true si le type est bon
	 */
	public boolean estUneMinuscule() {
		return ensembleMinuscules.contains(leChar);
	}

	/**
	 * Teste que la valeur estUneMajuscule()
	 * @return true si le type est bon
	 */
	public boolean estUneMajuscule() {
		return ensembleMajuscules.contains(leChar);
	}

	/**
	 * Teste que la valeur estUnAccentCedil()
	 * @return true si le type est bon
	 */
	public boolean estUnAccentCedil() {
		return ensembleAccentCedil.contains(leChar);
	}

	/**
	 * Teste que la valeur estUnSpecial()
	 * @return true si le type est bon
	 */
	public boolean estUnSpecial() {
		return ensembleSpeciaux.contains(leChar);
	}


	/////////////// GENERER UN CARACTERE ///////////////

	/**
	 * Genere un caractere, pris dans une liste (parametre)
	 * @param table liste des caracteres possibles pour la generation
	 * @return un caractere faisant partie des caracteres possibles (table)
	 */
	public static char genererCaractere(ArrayList<Character> table) {
		return genererCaractere(table, null);
	}

	/**
	 * Genere un caractere, pris dans une liste, en tenant compte d'exclusions (caracteres &agrave; ne pas utiliser)
	 * @param table liste des caracteres possibles pour la generation
	 * @param exclusions liste de caracteres &agrave; ne pas utiliser
	 * @return un caractere faisant partie des caracteres possibles (table hors exclusions)
	 */
	public static char genererCaractere(ArrayList<Character> table, ArrayList<Character> exclusions) {
		char c = table.get((int) (Math.random() * table.size())) ;
		if (exclusions == null) {
			return c;
		}
		while (exclusions.contains(c)) {
			c = table.get((int) (Math.random() * table.size())) ;
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
	 * @return
	 */
	public static char genererCaractere(ArrayList<Character> table, ArrayList<Character> exclusions, ArrayList<Character> inclusions) {
		ArrayList<Character> poolAvecInclusions = completerPool(table, inclusions) ;
		return genererCaractere(poolAvecInclusions, exclusions) ;
	}


	// ///////////// GENERER UN TYPE DE CARACTERE ///////////////

	/**
	 * Genere un : chiffre
	 * @return un caractere : chiffre
	 */
	public static char genereChiffre() {
		Caractere c = new Caractere() ;
		return genererCaractere(c.getChiffres());
	}

	/**
	 * Genere une lettre minuscule
	 * @return un caractere : lettre minuscule
	 */
	public static char genereMinuscule() {
		Caractere c = new Caractere() ;
		return genererCaractere(c.getMinuscules());
	}

	/**
	 * Genere une lettre majuscule
	 * @return un caractere : lettre majuscule
	 */
	public static char genereMajuscule() {
		Caractere c = new Caractere() ;
		return genererCaractere(c.getMajuscules());
	}

	/**
	 * Genere un : caract&egrave;re accentu&eacute;, un &ccedil; ou un ^
	 * @return un caractere : caractere accentu&eacute;
	 */
	public static char genereAccentCedil() {
		Caractere c = new Caractere() ;
		return genererCaractere(c.getAccentcedil());
	}

	/**
	 * Genere un : caractere special
	 * @return un caractere : caractere special
	 */
	public static char genereSpecial() {
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
	public static ArrayList<Character> poolCaracteres(boolean chiffres, boolean minusc, boolean majusc, boolean accent,
													  boolean special) {
		Caractere c = new Caractere() ;
		ArrayList<Character> list = new ArrayList<Character>();
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
	public static ArrayList<Character> completerPool(ArrayList<Character>  pool, ArrayList<Character> inclusions) {
		ArrayList<Character> poolEnCoursAL = pool ;
		if(inclusions != null && inclusions.size() != 0){
			// dédoublonne
			for (Character inc : inclusions) {
				if (!poolEnCoursAL.contains(inc)) {
					poolEnCoursAL.add(inc);
				}
			}
		}
		return poolEnCoursAL;
	}

	/////////////// TABLEAU DES 110 CARACTERES ///////////////

	/**
	 * Genere la liste des 110 caracteres de l'application
	 * <BR>(= Ensemble des caracteres pouvant etre utilis&eacute;s pour generer des mots de passe) 
	 * @return liste des 110 caracteres
	 */
	public static ArrayList<Character> les110() {
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
	 * @return liste des chiffres
	 */
	private void lesChiffres() {
		Caractere.ensembleChiffres = new ArrayList<Character>() ;
		for (int i = 48 ; i <= 57 ; i++)
			ensembleChiffres.add(new Character((char) i)) ;
	}

	/**
	 * Genere la liste des minuscules
	 * @return liste des minuscules
	 */
	private void lesMinuscules() {
		Caractere.ensembleMinuscules = new ArrayList<Character>() ;
		for(int i = 97 ; i <= 122 ; i++)
			ensembleMinuscules.add(new Character((char) i)) ;
	}

	/**
	 * Genere la liste des majuscules
	 * @return liste des majuscules
	 */
	private void lesMajuscules() {
		Caractere.ensembleMajuscules = new ArrayList<Character>() ;
		for(int i = 65 ; i <= 90 ; i++)
			ensembleMajuscules.add(new Character((char) i)) ;
	}

	/**
	 * Genere la liste des 12 accents / cedille
	 * @return liste des accents / cedille
	 */
	private void lesAccents() {
		Caractere.ensembleAccentCedil = new ArrayList<Character>() ;
		ensembleAccentCedil.add(new Character((char) 224)) ;
		ensembleAccentCedil.add(new Character((char) 226)) ;
		ensembleAccentCedil.add(new Character((char) 231)) ;
		ensembleAccentCedil.add(new Character((char) 232)) ;
		ensembleAccentCedil.add(new Character((char) 233)) ;
		ensembleAccentCedil.add(new Character((char) 234)) ;
		ensembleAccentCedil.add(new Character((char) 235)) ;
		ensembleAccentCedil.add(new Character((char) 238)) ;
		ensembleAccentCedil.add(new Character((char) 239)) ;
		ensembleAccentCedil.add(new Character((char) 244)) ;
		ensembleAccentCedil.add(new Character((char) 249)) ;
		ensembleAccentCedil.add(new Character((char) 251)) ;
	}

	// les 36 caract speciaux
	/**
	 * Genere la liste des 36 caracteres speciaux de l'application
	 * @return liste des 36 caracteres speciaux
	 */
	private void lesSpeciaux() {
		Caractere.ensembleSpeciaux = new ArrayList<Character>() ;
		for (int i = 33; i <= 47; i++)
			ensembleSpeciaux.add(new Character((char) i));
		for (int i = 58; i <= 64; i++)
			ensembleSpeciaux.add(new Character((char) i));
		for (int i = 91; i <= 96; i++)
			ensembleSpeciaux.add(new Character((char) i));
		for (int i = 123; i <= 126; i++)
			ensembleSpeciaux.add(new Character((char) i));
		ensembleSpeciaux.add(new Character((char) 163));
		ensembleSpeciaux.add(new Character((char) 166));
		ensembleSpeciaux.add(new Character((char) 167));
		ensembleSpeciaux.add(new Character((char) 176));
	}



	// ////////////////Getters ////////////////////

	public ArrayList<Character> getChiffres() {
		return ensembleChiffres;
	}

	public ArrayList<Character> getMinuscules() {
		return ensembleMinuscules;
	}
	public ArrayList<Character> getMajuscules() {
		return ensembleMajuscules;
	}

	public ArrayList<Character> getAccentcedil() {
		return ensembleAccentCedil;
	}

	public ArrayList<Character> getSpeciaux() {
		return ensembleSpeciaux;
	}


	// pour this.leChar et sa valeur ASCII 

	public void setLeChar(int valChar) {
		setLeChar((char) valChar);
	}

	public void setLeChar(char caract) {
		this.leChar = caract ;
	}

	public char getLeChar() {
		return this.leChar ;
	}
}
