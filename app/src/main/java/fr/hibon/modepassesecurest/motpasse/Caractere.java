package fr.hibon.modepassesecurest.motpasse;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Caractere d&eacute;finit 5 types de caract&egrave; (110 au total) <BR>
 * Chiffres, lettres majuscules et minuscules, lettres accentu&eacute;es, caract&egrave;res
 * sp&eacute;ciaux.
 * 
 * @author lohib
 *
 */
public class Caractere {

	// Chiffres, Majuscules et Minuscules : plage [codes ASCII]
	private static final Integer[] CHIFFRES = { 48, 57 }; // 10
	private static final Integer[] MAJUSCULES = { 65, 90 }; // 26
	private static final Integer[] MINUSCULES = { 97, 122 }; // 26

	// Caract�res accentu�s et sp�ciaux : listes exhaustives ordonn�es
	private static final Integer[] ACCENTCEDIL = { 224, 226, 231, 232, 233, 234, 235, 238, 239, 244, 249, 251 }; // 12
	private static final Integer[] SPECIAUX = caracteresSpeciaux(); // 36

	
	/////////////// TESTER LE TYPE D'UN CARACTERE ///////////////
	
	public static boolean estUnChiffre(char car) {
		return estDansPlage(car, CHIFFRES);
	}

	public static boolean estUneMinuscule(char car) {
		return estDansPlage(car, MINUSCULES);
	}

	public static boolean estUneMajuscule(char car) {
		return estDansPlage(car, MAJUSCULES);
	}

	public static boolean estUnAccentCedil(char c) {
		return estDansTable(c, ACCENTCEDIL);
	}

	public static boolean estUnSpecial(char c) {
		return estDansTable(c, SPECIAUX);
	}

	// // // // // // // // //
	private static boolean estDansTable(char caractere, Integer[] table) {
		for (int i = 0; i < table.length; i++) {
			if ((int) table[i] > (int) caractere)
				return false;
			if ((int) table[i] == (int) caractere)
				return true;
		}
		return false;
	}

	// // // // // // // // //
	private static boolean estDansPlage(char caractere, Integer[] plage) {
		if (caractere >= plage[0] && caractere <= plage[1]) {
			return true;
		}
		return false;
	}

	/////////////// GENERER UN TYPE DE CARACTERE ///////////////

	public static char genererCaractere(Integer[] table) {
		return genererCaractere(table, null);
	}

	public static char genererCaractere(Integer[] table, ArrayList<Integer> exclusions) {
		char c = (char) (int) (table[(int) (Math.random() * table.length )]) ;
		if (exclusions == null) {
			return c; 
		}
		while (exclusions.contains((int) c)) {
			c = (char) (int) (table[(int) (Math.random() * table.length +1)]);
		}
		return c;
	}

	public static char genereChiffre() {
		return genererCaractere(CHIFFRES);
	}

	public static char genereMinuscule() {
		return genererCaractere(MINUSCULES);
	}

	public static char genereMajuscule() {
		return genererCaractere(MAJUSCULES);
	}

	public static char genereAccentCedil() {
		return genererCaractere(ACCENTCEDIL);
	}

	public static char genereSpecial() {
		return genererCaractere(SPECIAUX);
	}

	

	/////////////// TABLEAU AVEC TYPES CHOISIS ///////////////
	public static Integer[] poolCaracteres(boolean chiffres, boolean minusc, boolean majusc, boolean accent, boolean special) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		if(chiffres)
			for (int i = 0 ; i < CHIFFRES.length ; i++) {
				list.add((CHIFFRES[i]));
			}	
		if(minusc)
			for (int i = 0 ; i < MINUSCULES.length ; i++) {
				list.add((MINUSCULES[i]));
			}
		if(majusc)
			for (int i = 0 ; i < MAJUSCULES.length ; i++) {
				list.add((MAJUSCULES[i]));
			}	
		if(accent)
			list.addAll((Arrays.asList(ACCENTCEDIL)));	
		if(special)
			list.addAll((Arrays.asList(SPECIAUX)));	
		if(!chiffres && !minusc && !majusc && !accent && !special) {
			list.addAll((Arrays.asList(CHIFFRES)));		
		}
		Integer[] tabSp = new Integer[list.size()];
		tabSp = list.toArray(tabSp);
		return tabSp;
	}
	
	public static Integer[] completerPool(Integer[] pool, ArrayList<Integer> inclusions) {
		Integer[] poolCompletTab ; 
		ArrayList<Integer> poolEnCoursAL = new ArrayList<>() ; 
		poolEnCoursAL.addAll((Arrays.asList(pool)));	
		// d�doublonne  
		for (Integer inc : inclusions) {
			if (!poolEnCoursAL.contains(inc)) {
				poolEnCoursAL.add(inc) ; 
			}
		}
		poolCompletTab = new Integer[poolEnCoursAL.size()];
		poolCompletTab = poolEnCoursAL.toArray(poolCompletTab);
		return poolCompletTab;
	}
	
	/////////////// TABLEAU DES 110 CARACTERES ///////////////

	public static char[] les110() {
		ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(CHIFFRES));
		list.addAll((Arrays.asList(MINUSCULES)));
		list.addAll((Arrays.asList(MAJUSCULES)));
		list.addAll((Arrays.asList(ACCENTCEDIL)));
		list.addAll((Arrays.asList(SPECIAUX)));
		char[] tabList = new char[list.size()];
		for (int i = 0; i < list.size(); i++) {
			tabList[i] = (char) (int) list.get(i) ; 
		}
		return tabList;
	}
	

	/////////////// TABLEAU DE 36 CARACTERES SPECIAUX ///////////////

	private static Integer[] caracteresSpeciaux() {
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
	
	
	// Getters
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

	// outil : developper table bornee
	private static ArrayList<Integer> developperTableBornes(Integer[] aDevelop){
		ArrayList<Integer> a = new ArrayList<>() ;
		
		return a ;	
	}
	
}

