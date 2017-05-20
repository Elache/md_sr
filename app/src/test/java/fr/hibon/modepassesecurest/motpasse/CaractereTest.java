package fr.hibon.modepassesecurest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntConsumer;

import org.junit.Test;

/**
 * Teste la classe Caractere
 * <BR>(MotDePasseCotation - ChainePasse - Caractere)
 */
public class CaractereTest {

		private ArrayList<Character> echantillon;
	
		// ////// Echantillon /////////////////////////////////////
	
		// chiffre, minusc, majusc, cédille, accent, composé, symbole, symbole et
		// '¤' hors-reference appli
		public CaractereTest() {
			echantillon = new ArrayList<>(); 
			echantillon.add('5');
			echantillon.add('a');
			echantillon.add('T');
			echantillon.add('ç');
			echantillon.add('ù');
			echantillon.add('â');
			echantillon.add('&');
			echantillon.add('#');
			echantillon.add('¤');
			echantillon.add('z') ;
			echantillon.add('v') ;
			echantillon.add('O') ;
			echantillon.add('[') ;
			echantillon.add('!') ; 
			echantillon.add('f') ;
		}
	
		// ////// setter /////////////////////////////////////
	
		/**
		 * Verifie setter de getLeChar() (valeur int ASCII d'un char) <BR>
		 * Test : set d'un caractere : le retrouver avec un get
		 */
		@Test
		public void setLeChar_doitModifier() {
			Caractere c = new Caractere('&');
			c.setLeChar('y'); // caractere 121
			assertEquals(121, c.getLeChar());
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
			boolean special = true;
			// test 'statistique'
			for (int i = 0; i < 100; i++) {
				if (!echantillon.contains(Caractere.genererCaractere(echantillon)))
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
			// exclusions
			ArrayList<Character> exclusions = new ArrayList<>() ; 
			exclusions.add('z'); 
			exclusions.add('v'); 
			boolean special = true ; 
			char enCours = '\0' ;
			// test 'statistique'
	 		for (int i = 0; i < 100; i++) {
	 			enCours = Caractere.genererCaractere(echantillon, exclusions) ;
	 			if( !echantillon.contains(enCours) || enCours == 'z' || enCours == 'v') {
	 				special = false ;
	 			}
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
			// inclusions
			ArrayList<Character> inclusions = new ArrayList<>() ; 
			inclusions.add('3'); 
			inclusions.add('4');
			boolean special = true ; 
			char enCours = '\0' ;
			// test 'statistique'
	 		for (int i = 0; i < 100; i++) {
	 			enCours = Caractere.genererCaractere(echantillon, null, inclusions) ;
	 			if( (!echantillon.contains(enCours) && enCours != '3' && enCours != '4'))
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
			ArrayList<Character> inclusions = new ArrayList<>() ; 
			inclusions.add('3'); 
			inclusions.add('4');
			boolean special = false ; 
			char enCours = '\0' ;
			// test 'statistique'
	 		for (int i = 0; i < 100; i++) {
	 			enCours = Caractere.genererCaractere(echantillon, null, inclusions) ;
	 			if(enCours == '3' || enCours == '4')
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
			ArrayList<Character> poolInit = new Caractere().getSpeciaux(); 
			// inclusions
			ArrayList<Character> inclusions = new ArrayList<>() ; 
			inclusions.add('z'); 
			inclusions.add('v');
			// exclusions
			ArrayList<Character> exclusions = new ArrayList<>() ; 
			exclusions.add('#'); 
			exclusions.add('$'); 
			boolean special = true ; 
			char enCours = '\0' ;
			// test 'statistique'
	 		for (int i = 0; i < 100; i++) {
	 			enCours = Caractere.genererCaractere(poolInit, exclusions, inclusions) ;
	 			if( (!poolInit.contains(enCours) && enCours != 'z' && enCours != 'v') || enCours == '#' || enCours == '$') {
	 				special = false ;
	 				System.out.println(enCours);
	 			}	 
			}
	 		assertTrue(special) ; 
		}
	
	
	
		// ////// Generer Chiffre
		/**
		 *  Verifie genereChiffre() : ne doit generer que des chiffres
		 */
		@Test
		public void genereChiffre() {
			boolean test = true;
			char enCours = '\0' ;
			for (int i = 0; i < 5; i++) {
				enCours = Caractere.genereChiffre();
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
			boolean test = true;
			char enCours = '\0' ;
			for (int i = 0; i < 5; i++) {
				enCours = Caractere.genereMinuscule();
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
			boolean test = true;
			char enCours = '\0' ;
			for (int i = 0; i < 5; i++) {
				enCours = Caractere.genereMajuscule();
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
			ArrayList<Character> reference = new ArrayList<>();
			reference.add('!'); reference.add('"'); reference.add('#'); reference.add('$'); reference.add('%'); 
			reference.add('&'); reference.add('\''); reference.add('('); reference.add(')'); reference.add('*');
			reference.add('+'); reference.add(','); reference.add('-'); reference.add('.'); reference.add('/');
			reference.add(':'); reference.add(';'); reference.add('<'); reference.add('='); reference.add('>');
			reference.add('?'); reference.add('@'); reference.add('['); reference.add('\\'); reference.add(']');
			reference.add('^'); reference.add('_'); reference.add('`'); reference.add('{'); reference.add('|');
			reference.add('}'); reference.add('~'); reference.add('£'); reference.add('¦'); reference.add('§');
			reference.add('°');
			boolean special = true ; 
			// test 'statistique'
	 		for (int i = 0; i < 100; i++) {
				if(!reference.contains(Caractere.genereSpecial()))
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
			ArrayList<Character> reference = new ArrayList<>();
			reference.add('à'); reference.add('â'); reference.add('ç'); reference.add('è');
			reference.add('é'); reference.add('ê'); reference.add('ë'); reference.add('î');
			reference.add('ï'); reference.add('ô'); reference.add('ù'); reference.add('û');
			reference.add('°');
			boolean special = true ; 
			// test 'statistique'
	 		for (int i = 0; i < 100; i++) {
				if(!reference.contains(Caractere.genereAccentCedil()))
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
			ArrayList<Character> pool = new Caractere().getSpeciaux() ;
			boolean test = true;
			boolean enCours = true;
			// test positif
			Caractere[] tabTestOK = { new Caractere('~'), new Caractere('&'), new Caractere('-'), new Caractere('_'),
					new Caractere(47), new Caractere(95), new Caractere(125), new Caractere(166), new Caractere('*'),
					new Caractere(60), new Caractere('%') };
			for (int i = 0; i < tabTestOK.length; i++) {
				enCours = pool.contains(tabTestOK[i].getLeChar());
				test = test && enCours;
			}
			// test negatif
			Caractere[] tabTestFalse = { new Caractere('a'), new Caractere('f'), new Caractere('R'), new Caractere('¤') };
			for (int i = 0; i < tabTestFalse.length; i++) {
				enCours = !pool.contains(tabTestFalse[i].getLeChar());
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
			ArrayList<Character> pool = Caractere.les110();
			boolean test = true;
			boolean enCours = true;
			// test positif
			Caractere[] tabTestOK = { new Caractere('~'), new Caractere('&'), new Caractere('-'), new Caractere('_'),
					new Caractere(47), new Caractere('a'), new Caractere('A'), new Caractere('é'), new Caractere('#'),
					new Caractere(95), new Caractere(125), new Caractere(166), new Caractere('*'), new Caractere(60),
					new Caractere('%') };
			for (int i = 0; i < tabTestOK.length; i++) {
				enCours = pool.contains(tabTestOK[i].getLeChar());
				test = test && enCours;
			}
			// test negatif
			Caractere[] tabTestFalse = { new Caractere('€'), new Caractere('ä'), new Caractere('ÿ'), new Caractere('¤') };
			for (int i = 0; i < tabTestFalse.length; i++) {
				enCours = !pool.contains(tabTestFalse[i].getLeChar());
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
			ArrayList<Character> pool = Caractere.poolCaracteres(true, true, true, true, true);
			boolean test = true;
			boolean enCours = true;
			// test positif
			Caractere[] tabTestOK = { new Caractere('ù'), new Caractere('û'), new Caractere('é'), new Caractere(226),
					new Caractere(239), new Caractere(251), new Caractere('a'), new Caractere('e'), new Caractere('4'),
					new Caractere('#'), new Caractere('%') };
			for (int i = 0; i < tabTestOK.length; i++) {
				enCours = pool.contains(tabTestOK[i].getLeChar());
				test = test && enCours;
			}
			// test negatif
			Caractere[] tabTestFalse = { new Caractere('ä'), new Caractere('¤'), new Caractere('¨'), new Caractere('ÿ') };
			for (int i = 0; i < tabTestFalse.length; i++) {
				enCours = !pool.contains(tabTestFalse[i].getLeChar());
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
			ArrayList<Character> pool = Caractere.poolCaracteres(false, true, true, false, false);
			boolean test = true;
			boolean enCours = true;
			// test positif
			Caractere[] tabTestOK = { new Caractere('a'), new Caractere('R'), new Caractere('f'), new Caractere(65),
					new Caractere(89), new Caractere(121), new Caractere('J'), new Caractere('q') };
			for (int i = 0; i < tabTestOK.length; i++) {
				enCours = pool.contains(tabTestOK[i].getLeChar());
				test = test && enCours;
			}
			// test negatif
			Caractere[] tabTestFalse = { new Caractere('ë'), new Caractere('¤'), new Caractere('5'), new Caractere('¨'), new Caractere('ÿ') };
			for (int i = 0; i < tabTestFalse.length; i++) {
				enCours = !pool.contains(tabTestFalse[i].getLeChar());
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
			ArrayList<Character> pool = Caractere.poolCaracteres(false, true, true, true, true);
			boolean test = true;
			boolean enCours = true;
			// test positif
			Caractere[] tabTestOK = { new Caractere('a'), new Caractere('R'), new Caractere('f'), new Caractere(65),
					new Caractere('#'), new Caractere('é'), new Caractere('J'), new Caractere(')') };
			for (int i = 0; i < tabTestOK.length; i++) {
				enCours = pool.contains(tabTestOK[i].getLeChar());
				test = test && enCours;
			}
			// test negatif
			Caractere[] tabTestFalse = { new Caractere('5'), new Caractere('9'), new Caractere('7'), new Caractere('4') };
			for (int i = 0; i < tabTestFalse.length; i++) {
				enCours = !pool.contains(tabTestFalse[i].getLeChar());
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
			ArrayList<Character> pool = Caractere.poolCaracteres(true, false, false, false, true);
			boolean test = true;
			boolean enCours = true;
			// test positif
			Caractere[] tabTestOK = { new Caractere('-'), new Caractere('\\'), new Caractere('#'), new Caractere(91),
					new Caractere(58), new Caractere('8') };
			for (int i = 0; i < tabTestOK.length; i++) {
				enCours = pool.contains(tabTestOK[i].getLeChar());
				test = test && enCours;
			}
			// test negatif
			Caractere[] tabTestFalse = { new Caractere('a'), new Caractere('F'), new Caractere('c'), new Caractere('Y') };
			for (int i = 0; i < tabTestFalse.length; i++) {
				enCours = !pool.contains(tabTestFalse[i].getLeChar());
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
			ArrayList<Character> pool = Caractere.poolCaracteres(false, false, false, false, false);
			boolean test = true;
			boolean enCours = true;
			// test positif
			Caractere[] tabTestOK = { new Caractere('5'), new Caractere('3'), new Caractere('7'), new Caractere(56) };
			for (int i = 0; i < tabTestOK.length; i++) {
				enCours = pool.contains(tabTestOK[i].getLeChar());
				test = test && enCours;
			}
			// test negatif
			Caractere[] tabTestFalse = { new Caractere('a'), new Caractere('F'), new Caractere('#'), new Caractere('Y') };
			for (int i = 0; i < tabTestFalse.length; i++) {
				enCours = !pool.contains(tabTestFalse[i].getLeChar());
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
			ArrayList<Character> pool =  Caractere.poolCaracteres(false, true, true, true, false);
			boolean test = true;
			boolean enCours = true;
			// test positif
			Caractere[] tabTestOK = { new Caractere('0'), new Caractere('4'), new Caractere('{'), new Caractere(59),
					new Caractere('9') };
			for (int i = 0; i < tabTestOK.length; i++) {
				enCours = !pool.contains(tabTestOK[i].getLeChar());
				test = test && enCours;
			}
			// test negatif
			Caractere[] tabTestFalse = { new Caractere('T'), new Caractere('é'), new Caractere('a'), new Caractere('u') };
			for (int i = 0; i < tabTestFalse.length; i++) {
				// true
				enCours = pool.contains(tabTestFalse[i].getLeChar());
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
			ArrayList<Character> pool =  Caractere.poolCaracteres(false, true, true, true, false);
			ArrayList<Character> inclusions = new ArrayList<>();
			inclusions.add('0');
			inclusions.add('4');
			inclusions.add('{');
			inclusions.add(';');
			inclusions.add('%');
			pool = Caractere.completerPool(pool, inclusions);
			boolean test = true;
			boolean enCours = true;
			// test positif
			Caractere[] tabTestOK = { new Caractere('0'), new Caractere('4'), new Caractere('{'), new Caractere(59),
					new Caractere('a'), new Caractere('T'), new Caractere('é') };
			for (int i = 0; i < tabTestOK.length; i++) {
				enCours = pool.contains(tabTestOK[i].getLeChar());
				test = test && enCours;
			}
			// test negatif
			Caractere[] tabTestFalse = { new Caractere('5'), new Caractere('7'), new Caractere('#'), new Caractere('~') };
			for (int i = 0; i < tabTestFalse.length; i++) {
				enCours = !pool.contains(tabTestFalse[i].getLeChar());
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
			boolean test = (new Caractere(echantillon.get(0))).estUnChiffre();
			// valeurs false
			for (int i = 1; i < echantillon.size() ; i++) {
				boolean enCours = !(new Caractere(echantillon.get(i))).estUnChiffre();
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
			Caractere[] vrais = {new Caractere('a'),new Caractere('y'),new Caractere('k'),new Caractere('z'),new Caractere('n'),new Caractere('m')};
			boolean test = true ;
			for (int i = 0 ; i < vrais.length ; i++){
				if(!vrais[i].estUneMinuscule())
					test = false; 
			}
			// valeur false
			Caractere[] faux = 	{new Caractere('é'),new Caractere('E'),new Caractere('_'),new Caractere('+'),new Caractere('|'),new Caractere('ù')};
			for (int j = 0 ; j < faux.length ; j++){
				if(faux[j].estUneMinuscule())
					test = false; 
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
			Caractere[] vrais = {new Caractere('A'),new Caractere('Y'),new Caractere('K'),new Caractere('Z'),new Caractere('N'),new Caractere('M')};
			boolean test = true ;
			for (int i = 0 ; i < vrais.length ; i++){
				if(!vrais[i].estUneMajuscule())
					test = false; 
			}
			// valeur false
			Caractere[] faux = 	{new Caractere('é'),new Caractere('e'),new Caractere('_'),new Caractere('+'),new Caractere('|'),new Caractere('m')};
			for (int j = 0 ; j < faux.length ; j++){
				if(faux[j].estUneMajuscule())
					test = false; 
			}
			assertTrue(test);
		}
	
		/**
		 * Teste la verification estUnAccent()
		 * <BR> verifie des valeurs : inclure (true) ou non (false)
		 */
		@Test
		public void estUnAccent() {
			// valeur true
			Caractere[] vrais = {new Caractere('é'),new Caractere('ï'),new Caractere('ô'),new Caractere('ç'),new Caractere('à')};
			boolean test = true ;
			for (int i = 0 ; i < vrais.length ; i++){
				if(!vrais[i].estUnAccentCedil())
					test = false; 
			}
			// valeur false
			Caractere[] faux = 	{new Caractere('A'),new Caractere('e'),new Caractere('_'),new Caractere('+'),new Caractere('ÿ'),new Caractere('ö')};
			for (int j = 0 ; j < faux.length ; j++){
				if(faux[j].estUnAccentCedil())
					test = false; 
			}
			assertTrue(test);
		}
	
		/**
		 * Teste la verification estUnSpecial()
		 * <BR> verifie des valeurs : inclure (true) ou non (false)
		 */
		@Test
		public void estUnSpecial() {
			// valeur true
			Caractere[] vrais = {new Caractere('~'),new Caractere('&'),new Caractere('['),new Caractere('@'),new Caractere('%')
					,new Caractere('+'),new Caractere('$'), new Caractere('£'), new Caractere('^')};
			boolean test = true ;
			for (int i = 0 ; i < vrais.length ; i++){
				if(!vrais[i].estUnSpecial())
					test = false; 
			}
			// valeur false - inclut 2 valeurs hors application (¤ et €)
			Caractere[] faux = 	{new Caractere('é'),new Caractere('¤'),new Caractere('€'),new Caractere('D'),new Caractere('R'),new Caractere('m')};
			for (int j = 0 ; j < faux.length ; j++){
				if(faux[j].estUnSpecial())
					test = false; 
			}
			assertTrue(test);
		}
	
		/**
		 * Teste la verification estUnSpecial()
		 * <BR> verifie des valeurs qui ne font pas partie de la table des caracteres de l'application
		 */
		@Test
		public void estUn_HorsCaractereApplication() {
			// valeurs false
			Caractere[] hors = {new Caractere('¤'), new Caractere('€'), new Caractere('²'), new Caractere('µ')} ;
			boolean test = true ;
			for (int i = 0; i < hors.length; i++) {
				if(hors[i].estUnChiffre() || hors[i].estUneMinuscule() || hors[i].estUneMajuscule() || hors[i].estUnAccentCedil() || hors[i].estUnSpecial())
					test = false ; 
			} 

				
		}
	

}
