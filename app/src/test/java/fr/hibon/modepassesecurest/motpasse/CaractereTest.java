package fr.hibon.modepassesecurest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Teste la classe Caractere
 * <BR>(MotDePasseCotation - ChainePasse - Caractere)
 */
public class CaractereTest {

		private Caractere[] echantillon;
	
		// ////// Echantillon /////////////////////////////////////
	
		// chiffre, minusc, majusc, cédille, accent, composé, symbole, symbole et
		// '¤' hors-reference appli
		public CaractereTest() {
			echantillon = new Caractere[9];
			echantillon[0] = new Caractere('5');
			echantillon[1] = new Caractere('a');
			echantillon[2] = new Caractere('T');
			echantillon[3] = new Caractere('ç');
			echantillon[4] = new Caractere('ù');
			echantillon[5] = new Caractere('â');
			echantillon[6] = new Caractere('&');
			echantillon[7] = new Caractere('#');
			echantillon[8] = new Caractere('¤');
		}
	
		// ////// setter /////////////////////////////////////
	
		/**
		 * Verifie setter de getLaValeurDuChar() (valeur int ASCII d'un char) <BR>
		 * Test : set d'un caractere : le retrouver avec un get
		 */
		@Test
		public void setLaValeurDuChar_doitModifier() {
			Caractere c = new Caractere('&');
			c.setLaValeurDuChar('y'); // caractere 121
			assertEquals(121, c.getLaValeurDuChar());
		}
	
		// ////// Generer CARACTERE
	
		/**
		 * Verifie que caractere g&eacute;n&eacute;r&eacute; est tir&eacute; dans
		 * pool-source voulu <BR>
		 * source : les caracteres sources (tirer dedans) <BR>
		 * g&eacute;n&eacute;ration de 100 caracteres : tous doivent appartenir au
		 * pool-source
		 */
		@Test
		public void genererCaractere_nominal() {
			Integer[] sourceTab = { new Integer('z'), new Integer('v'), new Integer('O'), new Integer('['),
					new Integer('!'), new Integer('f'), new Integer('5') };
			List<Integer> sourceAL = Arrays.asList(sourceTab);
			boolean special = true;
			// test 'statistique'
			for (int i = 0; i < 100; i++) {
				if (!sourceAL.contains((int) Caractere.genererCaractere(sourceTab)))
					special = false;
			}
			assertTrue(special);
		}
		
	
		
		// ////// Generer CARACTERE exclusions
		
		/**
		 * Verifie que caractere g&eacute;n&eacute;r&eacute; est tir&eacute; dans pool-source 
		 * auquel on retire une liste spécifique 
		 * <BR>source : les caracteres sources (tirer dedans)
		 * <BR>exclusions : liste de caracteres &agrave; ne pas utiliser
		 * <BR>g&eacute;n&eacute;ration de 100 caracteres : tous doivent appartenir au pool-source hors exclusions
		 */
		@Test
		public void genererCaractere_exclusions() {
			Integer[] sourceTab = {new Integer('z'),new Integer('v'),new Integer('O'),new Integer('['),new Integer('!'),new Integer('f'),new Integer('5')} ;
			List<Integer> sourceAL = Arrays.asList(sourceTab);
			// exclusions
			ArrayList<Integer> exclusions = new ArrayList<>() ; 
			exclusions.add(new Integer('z')); 
			exclusions.add(new Integer('v')); 
			boolean special = true ; 
			int enCours = 0 ;
			// test 'statistique'
	 		for (int i = 0; i < 100; i++) {
	 			enCours = (int) Caractere.genererCaractere(sourceTab, exclusions) ;
	 			if( !sourceAL.contains(enCours) || enCours == 'z' || enCours == 'v')
					special = false ; 
			}
	 		assertTrue(special) ; 
		}
		
	
		
		// ////// Generer CARACTERE inclusions
		
		/**
		 * Verifie que caractere g&eacute;n&eacute;r&eacute; est tir&eacute; dans pool-source voulu
		 * auquel on ajoute une liste d'inclusions (caracteres autoris&eacute;s en plus) 
		 * <BR>source : les caracteres sources (tirer dedans)
		 * <BR>inclusions : les caracteres &agrave; la source
		 * <BR>g&eacute;n&eacute;ration de 100 caracteres : tous doivent appartenir au pool-source (+inclusions)
		 */
		@Test
		public void genererCaractere_inclusions() {
			Integer[] poolInit = Caractere.getSpeciaux();
			List<Integer> poolToCherche = Arrays.asList(poolInit) ; 
			// inclusions
			ArrayList<Integer> inclusions = new ArrayList<>() ; 
			inclusions.add(new Integer('z')); 
			inclusions.add(new Integer('v'));
			boolean special = true ; 
			int enCours = 0 ;
			// test 'statistique'
	 		for (int i = 0; i < 100; i++) {
	 			enCours = (int) Caractere.genererCaractere(poolInit, null, inclusions) ;
	 			if( (!poolToCherche.contains(enCours) && enCours != 'z' && enCours != 'v'))
					special = false ; 
			}
	 		assertTrue(special) ; 		
		}
		
		/**
		 * Verifie que les caracteres d'inclusion (autoris&eacute;s sp&eacute;cifiquement) peuvent etre presents dans les caracteres g&eacute;n&eacute;r&eacute;s
		 * <BR>source : les caracteres sources (tirer dedans)
		 * <BR>inclusions : les caracteres &agrave; la source
		 * <BR>g&eacute;n&eacute;ration de 100 caracteres : tous doivent appartenir au pool-source (+inclusions)
		 */
		@Test
		public void genererCaractere_inclusionsPresentes() {
			Integer[] poolInit = Caractere.getSpeciaux();
			List<Integer> poolToCherche = Arrays.asList(poolInit) ; 
			ArrayList<Integer> inclusions = new ArrayList<>() ; 
			inclusions.add(new Integer('z')); 
			inclusions.add(new Integer('v'));
			boolean special = false ; 
			int enCours = 0 ;
			// test 'statistique'
	 		for (int i = 0; i < 100; i++) {
	 			enCours = (int) Caractere.genererCaractere(poolInit, null, inclusions) ;
	 			if(enCours == 'z' || enCours == 'v')
					special = true ; 
			}
	 		assertTrue(special) ; 			
		}
		
		
		// ////// Generer CARACTERE inclusions et exclusions
			
		/**
		 * Verifie que caractere g&eacute;n&eacute;r&eacute; est tir&eacute; dans pool-source voulu
		 * auquel on ajoute une liste d'inclusions (caracteres autoris&eacute;s en plus) et d'exclusions 
		 * <BR>source : les caracteres sources (tirer dedans)
		 * <BR>inclusions : les caracteres &agrave; la source
		 * <BR>exclusions : liste de caracteres &agrave; ne pas utiliser
		 * <BR>g&eacute;n&eacute;ration de 100 caracteres : tous doivent appartenir au pool-source (+inclusions -exclusions)
		 */
		@Test
		public void genererCaractere_inclusionsEtExclusions() {
			Integer[] poolInit = Caractere.getSpeciaux();
			List<Integer> poolToCherche = Arrays.asList(poolInit) ; 
			// inclusions
			ArrayList<Integer> inclusions = new ArrayList<>() ; 
			inclusions.add(new Integer('z')); 
			inclusions.add(new Integer('v'));
			// exclusions
			ArrayList<Integer> exclusions = new ArrayList<>() ; 
			exclusions.add(new Integer('#')); 
			exclusions.add(new Integer('$')); 
			boolean special = true ; 
			int enCours = 0 ;
			// test 'statistique'
	 		for (int i = 0; i < 100; i++) {
	 			enCours = (int) Caractere.genererCaractere(poolInit, exclusions, inclusions) ;
	 			if( (!poolToCherche.contains(enCours) && enCours != 'z' && enCours != 'v') || enCours == '#' || enCours == '$')
					special = false ; 
			}
	 		assertTrue(special) ; 
		}
	
	
	
		// ////// Generer Chiffre
		/**
		 *  Verifie genereChiffre() : ne doit generer que des chiffres
		 */
		@Test
		public void genereChiffre() {
			ArrayList<Integer> essais = new ArrayList<>();
			boolean test = true;
			int enCours = 0;
			for (int i = 0; i < 5; i++) {
				enCours = (int) Caractere.genereChiffre();
				if(enCours < '0' || enCours > '9') {
					test = false ; 
				}
			}
			assertTrue(test);
		}
	
		// ////// Generer minuscule
		/**
		 *  Verifie genereMinuscule()  : ne doit generer que des minuscules.
		 *  */
		@Test
		public void genereMinuscule() {
			ArrayList<Integer> essais = new ArrayList<>();
			boolean test = true;
			int enCours = 0;
			for (int i = 0; i < 5; i++) {
				enCours = (int) Caractere.genereMinuscule();
				if(enCours < 'a' || enCours > 'z') {
					test = false ; 
				}
			}
			assertTrue(test);
		}
	
		// ////// Generer majuscule
		/**
		 *  Verifie genereMajuscule()  : ne doit generer que des majuscules
		 */
		@Test
		public void genereMajuscule() {
			ArrayList<Integer> essais = new ArrayList<>();
			boolean test = true;
			int enCours = 0;
			for (int i = 0; i < 5; i++) {
				enCours = (int) Caractere.genereMajuscule();
				if(enCours < 'A' || enCours > 'Z') {
					test = false ; 
				}
			}
			assertTrue(test);
		}
	
		// ////// Generer special
		
		/**
		 *  Verifie genereSpecial()
		 *  <BR>reference : la liste de tous les caracteres speciaux de l'application 
		 *  <br>100 tirages : tous doivent appartenir &agrave; la liste des caracteres speciaux
		 */
		@Test
		public void genereSpecial() {
			ArrayList<Integer> reference = new ArrayList<>();
			reference.add((int)'!'); reference.add((int)'"'); reference.add((int)'#'); reference.add((int)'$'); reference.add((int)'%'); 
			reference.add((int)'&'); reference.add((int)'\''); reference.add((int)'('); reference.add((int)')'); reference.add((int)'*');
			reference.add((int)'+'); reference.add((int)','); reference.add((int)'-'); reference.add((int)'.'); reference.add((int)'/');
			reference.add((int)':'); reference.add((int)';'); reference.add((int)'<'); reference.add((int)'='); reference.add((int)'>');
			reference.add((int)'?'); reference.add((int)'@'); reference.add((int)'['); reference.add((int)'\\'); reference.add((int)']');
			reference.add((int)'^'); reference.add((int)'_'); reference.add((int)'`'); reference.add((int)'{'); reference.add((int)'|');
			reference.add((int)'}'); reference.add((int)'~'); reference.add((int)'£'); reference.add((int)'¦'); reference.add((int)'§');
			reference.add((int)'°');
			boolean special = true ; 
			// test 'statistique'
	 		for (int i = 0; i < 100; i++) {
				if(!reference.contains((int)Caractere.genereSpecial()))
					special = false ; 
			}
	 		assertTrue(special) ; 
		}
		
		// ////// Generer accent
		/**
		 *  Verifie genereAccent()
		* <br>100 tirages : tous doivent appartenir &agrave; la liste reference de toutes les minuscules accentu&eacute;es
		*/
		@Test
		public void genereAccent() {
			ArrayList<Integer> reference = new ArrayList<>();
			reference.add((int)'à'); reference.add((int)'â'); reference.add((int)'ç'); reference.add((int)'è');
			reference.add((int)'é'); reference.add((int)'ê'); reference.add((int)'ë'); reference.add((int)'î');
			reference.add((int)'ï'); reference.add((int)'ô'); reference.add((int)'ù'); reference.add((int)'û');
			reference.add((int)'°');
			boolean special = true ; 
			// test 'statistique'
	 		for (int i = 0; i < 100; i++) {
				if(!reference.contains((int)Caractere.genereAccentCedil()))
					special = false ; 
			}
	 		assertTrue(special) ; 
		}
		
		
	
		// ////// constituer le tab des caracteres speciaux
	
		/**
		 * Teste la methode qui g&eacute;n&egrave;re la liste des caracteres speciaux
		 * <BR>Compare une 15ne de caracteres, qui doivent etre tous class&eacutes correctement entre speciaux (true) et autres (false)
		 */
		@Test
		public void caracteresSpeciaux_Complet() {
			Integer[] pool = Caractere.caracteresSpeciaux();
			List<Integer> poolAL = Arrays.asList(pool);
			boolean test = true;
			boolean enCours = true;
			// test positif
			Caractere[] tabTestOK = { new Caractere('~'), new Caractere('&'), new Caractere('-'), new Caractere('_'),
					new Caractere(47), new Caractere(95), new Caractere(125), new Caractere(166), new Caractere('*'),
					new Caractere(60), new Caractere('%') };
			for (int i = 0; i < tabTestOK.length; i++) {
				enCours = poolAL.contains(tabTestOK[i].getLaValeurDuChar());
				test = test && enCours;
			}
			// test negatif
			Caractere[] tabTestFalse = { new Caractere('a'), new Caractere('f'), new Caractere('R'), new Caractere('¤') };
			for (int i = 0; i < tabTestFalse.length; i++) {
				enCours = !poolAL.contains(tabTestFalse[i].getLaValeurDuChar());
				test = test && enCours;
			}
			assertTrue(test);
		}
	
		// ////// constituer le tab de tous les caracteres de l'application
		/**
		 * Teste la methode qui g&eacute;n&egrave;re la liste des 110 caracteres de l'application
		 * <BR>Test d'appartenance d'une 15ne de caracteres + test sur des caracteres qui ne doivent pas etre presents
		 */
		@Test
		public void les110_Complet() {
			Integer[] pool = Caractere.les110();
			List<Integer> poolAL = Arrays.asList(pool);
			boolean test = true;
			boolean enCours = true;
			// test positif
			Caractere[] tabTestOK = { new Caractere('~'), new Caractere('&'), new Caractere('-'), new Caractere('_'),
					new Caractere(47), new Caractere('a'), new Caractere('A'), new Caractere('é'), new Caractere('#'),
					new Caractere(95), new Caractere(125), new Caractere(166), new Caractere('*'), new Caractere(60),
					new Caractere('%') };
			for (int i = 0; i < tabTestOK.length; i++) {
				enCours = poolAL.contains(tabTestOK[i].getLaValeurDuChar());
				test = test && enCours;
			}
			// test negatif
			Caractere[] tabTestFalse = { new Caractere('€'), new Caractere('ä'), new Caractere('ÿ'), new Caractere('¤') };
			for (int i = 0; i < tabTestFalse.length; i++) {
				enCours = !poolAL.contains(tabTestFalse[i].getLaValeurDuChar());
				test = test && enCours;
			}
			assertTrue(test);
		}
	
		// ////// constituer les pools de caracteres
	
		/**
		 * Teste la generation d'un pool parametr&eacute; de caracteres, en choisissant les types &agrave; utiliser (chiffres, min, maj...)
		 * <BR>Test : tous les types selectionn&eacute;
		 * <BR>verifie echantillon de caracteres qui doivent etre, ou non, presents dans le pool
		 */
		@Test
		public void poolCaracteres_Complet() {
			// tous caracteres possibles
			Integer[] pool = Caractere.poolCaracteres(true, true, true, true, true);
			List<Integer> poolAL = Arrays.asList(pool);
			boolean test = true;
			boolean enCours = true;
			// test positif
			Caractere[] tabTestOK = { new Caractere('ù'), new Caractere('û'), new Caractere('é'), new Caractere(226),
					new Caractere(239), new Caractere(251), new Caractere('a'), new Caractere('e'), new Caractere('4'),
					new Caractere('#'), new Caractere('%') };
			for (int i = 0; i < tabTestOK.length; i++) {
				enCours = poolAL.contains(tabTestOK[i].getLaValeurDuChar());
				test = test && enCours;
			}
			// test negatif
			Caractere[] tabTestFalse = { new Caractere('ä'), new Caractere('¤'), new Caractere('¨'), new Caractere('ÿ') };
			for (int i = 0; i < tabTestFalse.length; i++) {
				enCours = !poolAL.contains(tabTestFalse[i].getLaValeurDuChar());
				test = test && enCours;
			}
			assertTrue(test);
		}
	
		/**
		 * Teste la generation d'un pool parametr&eacute; de caracteres, en choisissant les types &agrave; utiliser (chiffres, min, maj...)
		 * <BR>Test : pas de chiffres, pas de caracteres speciaux, pas d'accents
		 * <BR>verifie echantillon de caracteres qui doivent etre, ou non, presents dans le pool
		 */
		@Test
		public void poolCaracteres_lettresMinMaj() {
			// seulement lettres min et maj
			Integer[] pool = Caractere.poolCaracteres(false, true, true, false, false);
			List<Integer> poolAL = Arrays.asList(pool);
			boolean test = true;
			boolean enCours = true;
			// test positif
			Caractere[] tabTestOK = { new Caractere('a'), new Caractere('R'), new Caractere('f'), new Caractere(65),
					new Caractere(89), new Caractere(121), new Caractere('J'), new Caractere('q') };
			for (int i = 0; i < tabTestOK.length; i++) {
				enCours = poolAL.contains(tabTestOK[i].getLaValeurDuChar());
				test = test && enCours;
			}
			// test negatif
			Caractere[] tabTestFalse = { new Caractere('ë'), new Caractere('¤'), new Caractere('5'), new Caractere('¨'), new Caractere('ÿ') };
			for (int i = 0; i < tabTestFalse.length; i++) {
				enCours = !poolAL.contains(tabTestFalse[i].getLaValeurDuChar());
				test = test && enCours;
			}
			assertTrue(test);
		}
	
		/**
		 * Teste la generation d'un pool parametr&eacute; de caracteres, en choisissant les types &agrave; utiliser (chiffres, min, maj...)
		 * <BR>Test : tout sauf des chiffres
		 * <BR>verifie echantillon de caracteres qui doivent etre, ou non, presents dans le pool
		 */
		@Test
		public void poolCaracteres_sansChiffres() {
			// seulement lettres (min, maj, accent) et caracteres speciaux
			Integer[] pool = Caractere.poolCaracteres(false, true, true, true, true);
			List<Integer> poolAL = Arrays.asList(pool);
			boolean test = true;
			boolean enCours = true;
			// test positif
			Caractere[] tabTestOK = { new Caractere('a'), new Caractere('R'), new Caractere('f'), new Caractere(65),
					new Caractere('#'), new Caractere('é'), new Caractere('J'), new Caractere(')') };
			for (int i = 0; i < tabTestOK.length; i++) {
				enCours = poolAL.contains(tabTestOK[i].getLaValeurDuChar());
				test = test && enCours;
			}
			// test negatif
			Caractere[] tabTestFalse = { new Caractere('5'), new Caractere('9'), new Caractere('7'), new Caractere('4') };
			for (int i = 0; i < tabTestFalse.length; i++) {
				enCours = !poolAL.contains(tabTestFalse[i].getLaValeurDuChar());
				test = test && enCours;
			}
			assertTrue(test);
		}
	
		/**
		 * Teste la generation d'un pool parametr&eacute; de caracteres, en choisissant les types &agrave; utiliser (chiffres, min, maj...)
		 * <BR>Test : seuls chiffres ou caracteres speciaux
		 * <BR>verifie echantillon de caracteres qui doivent etre, ou non, presents dans le pool
		 */
		@Test
		public void poolCaracteres_sansLettres() {
			// seulement chiffres et caracteres speciaux
			Integer[] pool = Caractere.poolCaracteres(true, false, false, false, true);
			List<Integer> poolAL = Arrays.asList(pool);
			boolean test = true;
			boolean enCours = true;
			// test positif
			Caractere[] tabTestOK = { new Caractere('-'), new Caractere('\\'), new Caractere('#'), new Caractere(91),
					new Caractere(58), new Caractere('8') };
			for (int i = 0; i < tabTestOK.length; i++) {
				enCours = poolAL.contains(tabTestOK[i].getLaValeurDuChar());
				test = test && enCours;
			}
			// test negatif
			Caractere[] tabTestFalse = { new Caractere('a'), new Caractere('F'), new Caractere('c'), new Caractere('Y') };
			for (int i = 0; i < tabTestFalse.length; i++) {
				enCours = !poolAL.contains(tabTestFalse[i].getLaValeurDuChar());
				test = test && enCours;
			}
			assertTrue(test);
		}
	
	
		/**
		 * Teste la generation d'un pool parametr&eacute; de caracteres, en choisissant les types &agrave; utiliser (chiffres, min, maj...)
		 * <BR>Test : pas de parametres == uniquement des chiffres
		 * <BR>verifie echantillon de caracteres qui doivent etre, ou non, presents dans le pool
		 */
		@Test
		public void poolCaracteres_defaut() {
			Integer[] pool = Caractere.poolCaracteres(false, false, false, false, false);
			List<Integer> poolAL = Arrays.asList(pool);
			boolean test = true;
			boolean enCours = true;
			// test positif
			Caractere[] tabTestOK = { new Caractere('5'), new Caractere('3'), new Caractere('7'), new Caractere(56) };
			for (int i = 0; i < tabTestOK.length; i++) {
				enCours = poolAL.contains(tabTestOK[i].getLaValeurDuChar());
				test = test && enCours;
			}
			// test negatif
			Caractere[] tabTestFalse = { new Caractere('a'), new Caractere('F'), new Caractere('#'), new Caractere('Y') };
			for (int i = 0; i < tabTestFalse.length; i++) {
				enCours = !poolAL.contains(tabTestFalse[i].getLaValeurDuChar());
				test = test && enCours;
			}
			assertTrue(test);
		}
		
		/**
		 * Teste la generation d'un pool parametr&eacute; de caracteres, en choisissant les types &agrave; utiliser (chiffres, min, maj...)
		 * <BR>Test : lettres (min, maj et accents) autoris&eacute;s
		 * <BR>verifie echantillon de caracteres qui doivent etre, ou non, presents dans le pool
		 */
		@Test
		public void poolCaracteres_lettres() {
			// seulement lettres (min, maj, accent)
			Integer[] pool = Caractere.poolCaracteres(false, true, true, true, false);
			List<Integer> poolAL = Arrays.asList(pool);
			boolean test = true;
			boolean enCours = true;
			// test positif
			Caractere[] tabTestOK = { new Caractere('0'), new Caractere('4'), new Caractere('{'), new Caractere(59),
					new Caractere('9') };
			for (int i = 0; i < tabTestOK.length; i++) {
				enCours = !poolAL.contains(tabTestOK[i].getLaValeurDuChar());
				test = test && enCours;
			}
			// test negatif
			Caractere[] tabTestFalse = { new Caractere('T'), new Caractere('é'), new Caractere('a'), new Caractere('u') };
			for (int i = 0; i < tabTestFalse.length; i++) {
				// true
				enCours = poolAL.contains(tabTestFalse[i].getLaValeurDuChar());
				test = test && enCours;
			}
			assertTrue(test);
	
		}
		
	
		// ////// inclure des caracteres specifiques sur un pool regroupant des types
	
		/**
		 * Teste l'ajout d'une liste de caracteres au pool utilisable
		 * <BR>Test : pas de chiffres, pas de caracteres speciaux
		 * <BR>inclusions : Ajout de quelques caracteres speciaux et chiffres
		 * <BR>verifie echantillon de caracteres qui doivent etre, ou non, presents dans le pool obtenu
		 */
		@Test
		public void completerPool() {
			// seulement lettres (min, maj, accent)
			Integer[] pool = Caractere.poolCaracteres(false, true, true, true, false);
			ArrayList<Integer> inclusions = new ArrayList<>();
			inclusions.add((int) '0');
			inclusions.add((int) '4');
			inclusions.add((int) '{');
			inclusions.add((int) ';');
			inclusions.add((int) '%');
			pool = Caractere.completerPool(pool, inclusions);
			List<Integer> poolAL = Arrays.asList(pool);
			boolean test = true;
			boolean enCours = true;
			// test positif
			Caractere[] tabTestOK = { new Caractere('0'), new Caractere('4'), new Caractere('{'), new Caractere(59),
					new Caractere('a'), new Caractere('T'), new Caractere('é') };
			for (int i = 0; i < tabTestOK.length; i++) {
				enCours = poolAL.contains(tabTestOK[i].getLaValeurDuChar());
				test = test && enCours;
			}
			// test negatif
			Caractere[] tabTestFalse = { new Caractere('5'), new Caractere('7'), new Caractere('#'), new Caractere('~') };
			for (int i = 0; i < tabTestFalse.length; i++) {
				enCours = !poolAL.contains(tabTestFalse[i].getLaValeurDuChar());
				test = test && enCours;
			}
			assertTrue(test);
		}
	
	
		// ////// determination du type de caracteres
		
		/**
		 * Teste la verification estUnChiffre()
		 * <BR> verifie des valeurs : inclure (true) ou non (false)
		 */
		@Test
		public void estUnChiffre() {
			// valeur True
			boolean test = echantillon[0].estUnChiffre();
			// valeurs false
			for (int i = 1; i < echantillon.length; i++) {
				boolean enCours = !echantillon[i].estUnChiffre();
				test = test && enCours;
			}
			assertTrue(test);
		}
	
		/**
		 * Teste la verification estUneMinuscule()
		 * <BR> verifie des valeurs : inclure (true) ou non (false)
		 */
		@Test
		public void estUneMinuscule() {
			// valeur true
			boolean test = echantillon[1].estUneMinuscule();
			// valeurs false
			test = test && !echantillon[0].estUneMinuscule();
			for (int i = 2; i < echantillon.length; i++) {
				boolean enCours = !echantillon[i].estUneMinuscule();
				test = test && enCours;
			}
			assertTrue(test);
		}
	
		
		/**
		 * Teste la verification estUneMajuscule()
		 * <BR> verifie des valeurs : inclure (true) ou non (false)
		 */
		@Test
		public void estUneMajuscule() {
			// valeur true
			boolean test = echantillon[2].estUneMajuscule();
			// valeurs false
			test = test && !echantillon[0].estUneMajuscule();
			test = test && !echantillon[1].estUneMajuscule();
			for (int i = 3; i < echantillon.length; i++) {
				boolean enCours = !echantillon[i].estUneMajuscule();
				test = test && enCours;
			}
			assertTrue(test);
		}
	
		/**
		 * Teste la verification estUnAccent()
		 * <BR> verifie des valeurs : inclure (true) ou non (false)
		 */
		@Test
		public void estUnAccent() {
			// valeurs true
			boolean test = echantillon[3].estUnAccentCedil();
			test = test && echantillon[4].estUnAccentCedil();
			test = test && echantillon[5].estUnAccentCedil();
			// valeurs false
			for (int i = 6; i < echantillon.length; i++) {
				boolean enCours = !echantillon[i].estUnAccentCedil();
				test = test && enCours;
			}
			for (int i = 0; i < 3; i++) {
				boolean enCours = !echantillon[i].estUnAccentCedil();
				test = test && enCours;
			}
			assertTrue(test);
		}
	
		/**
		 * Teste la verification estUnSpecial()
		 * <BR> verifie des valeurs : inclure (true) ou non (false)
		 */
		@Test
		public void estUnSpecial() {
			// valeurs true
			boolean test = echantillon[6].estUnSpecial();
			test = test && echantillon[7].estUnSpecial();
			// valeurs false
			for (int i = 0; i < 6; i++) {
				boolean enCours = !echantillon[i].estUnSpecial();
				test = test && enCours;
			}
			test = test && !echantillon[8].estUnAccentCedil();
			assertTrue(test);
		}
	
		/**
		 * Teste la verification estUnSpecial()
		 * <BR> verifie des valeurs qui ne font pas partie du tableau des caracteres de l'application
		 */
		@Test
		public void estUn_HorsCaractereApplication() {
			// valeurs false
			boolean test = !echantillon[8].estUnChiffre() && !echantillon[8].estUneMinuscule()
					&& !echantillon[8].estUneMajuscule() && !echantillon[8].estUnAccentCedil()
					&& !echantillon[8].estUnSpecial();
		}
	
		// ////// utilitaires : dansTable de caracteres (cf. tableaux)
	
		
		// table : les valeurs ordonnees
		/**
		 * Teste la verification de la presence d'un caractere dans un tableau (cf. types de caracteres)
		 * <BR> verifie des valeurs : inclure (true) ou non (false)
		 */
		@Test
		public void estDansTable() {
			boolean test = true;
			// valeurs true
			Caractere[] tabTestOK = { new Caractere('ù'), new Caractere('û'), new Caractere('é'), new Caractere(226),
					new Caractere(239), new Caractere(251) };
			for (int i = 0; i < tabTestOK.length; i++) {
				boolean enCours = tabTestOK[i].estDansTable(Caractere.getAccentcedil());
				test = test && enCours;
			}
			// valeurs false
			Caractere[] tabTestFalse = { new Caractere('a'), new Caractere('e'), new Caractere('4'), new Caractere('#'),
					new Caractere('ÿ'), new Caractere(225), new Caractere(117), new Caractere(236) };
			for (int i = 0; i < tabTestFalse.length; i++) {
				boolean enCours = !tabTestFalse[i].estDansTable(Caractere.getAccentcedil());
				test = test && enCours;
			}
			assertTrue(test);
		}


}
