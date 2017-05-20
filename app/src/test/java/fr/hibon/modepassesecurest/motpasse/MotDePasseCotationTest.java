package fr.hibon.modepassesecurest;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import javax.jws.WebParam.Mode;

import org.junit.Test;

public class MotDePasseCotationTest {

	ArrayList<String> echantillon_1;
	ArrayList<String> echantillon_2;
	ArrayList<String> echantillon_3;
	ArrayList<String> echantillon_4;

	public MotDePasseCotationTest() {
		// 50ne de MP, classes par "force" (echelle ModePasse SecuRest
		echantillon_1 = new ArrayList<>();
		echantillon_2 = new ArrayList<>();
		echantillon_3 = new ArrayList<>();
		echantillon_4 = new ArrayList<>();

		this.echantillonStr();
	}

	/**
	 * Test Constructeur num 1 (param String) et getters <BR>
	 * Constructeur prive : methode publique analyser(String) String 5gAù%(
	 * degré 1 de 'force' (6 caract de long parmi 110)
	 */
	@Test
	public void MotDePasseCotation_paramString_OK() {
		String sp = ChainePasse.composition(echantillon_1.get(8)).getChaineDuPasse();
		// tout110_6 = "5gAù%(";
		MotDePasseCotation mp = MotDePasseCotation.analyser(sp);
		boolean test = true;
		if (mp.longueurPasse() != 6 || mp.getValeurAnalyse() != 1 || !mp.getQualifieAnalyse().equals("Très faible")
				|| mp.getForceBits() != 41 || !mp.getNiveauAnssi().equals("très faible")
				|| !mp.getMotPasse().toString().equals("5gAù%("))
			test = false;
		assertTrue(test);
	}
	
	/**
	 * Test Constructeur num 2 (param ChainePasse) et getters <BR>
	 * Constructeur prive : methode publique analyser(String) appel&eacute;e
	 * 5gAù%( degré 1 de 'force' (6 caract de long parmi 110)
	 */
	@Test
	public void MotDePasseCotation_paramChainePasse() {
		ChainePasse cp = ChainePasse.composition(echantillon_1.get(8)); // tout110_6
																		// =
																		// "5gAù%(";
		MotDePasseCotation mp = MotDePasseCotation.analyser(cp);

	}

	
	// /////////   teste methode de classification ModePasseSecuRest
	
	/**
	 * Verifie, sur ~50 passw, si le classement est le meme que celui determine
	 * 'manuellement'. <br>
	 * Teste chaque borne d'un niveau , inf et sup (tres faible / faible /
	 * acceptable / bon)<BR>
	 * <BR>
	 * pour les 7 classes taille de la gamme de caracteres utilisables (10
	 * chiffres... 110 caract).
	 */
	@Test
	public void bilanChiffre_() {
		boolean masseTest = true;
		MotDePasseCotation courant = null;
		for (int i = 0; i < echantillon_1.size(); i++) {
			courant = MotDePasseCotation.analyser(echantillon_1.get(i));
			if (courant.getValeurAnalyse() != 1)
				masseTest = false;
		}

		for (int i = 0; i < echantillon_2.size(); i++) {
			courant = MotDePasseCotation.analyser(echantillon_2.get(i));
			if (courant.getValeurAnalyse() != 2)
				masseTest = false;
		}

		for (int i = 0; i < echantillon_3.size(); i++) {
			courant = MotDePasseCotation.analyser(echantillon_3.get(i));
			if (courant.getValeurAnalyse() != 3)
				masseTest = false;
		}

		for (int i = 0; i < echantillon_4.size(); i++) {
			courant = MotDePasseCotation.analyser(echantillon_4.get(i));
			if (courant.getValeurAnalyse() != 4)
				masseTest = false;
		}
		assertTrue(masseTest);
	}

	
	// /////////   teste methode de classification Anssi pour forces importantes
	
		/**
		 * 
		 */
		@Test
		public void classiAnssi_() {
			boolean test = true; 
			String chiffres10_77 = echantillon_4.get(1) ; // 256
			MotDePasseCotation mp = MotDePasseCotation.analyser(chiffres10_77) ;
			if (mp.getForceBits() != 256 || !mp.getNiveauAnssi().equals("fort (>128b)"))
					test = false ; 
			String tout110_19 = echantillon_4.get(8) ; // 129
			mp = MotDePasseCotation.analyser(tout110_19) ;
			if (mp.getForceBits() != 129 || !mp.getNiveauAnssi().equals("fort (>128b)"))
					test = false ; 
			String tout110_38 = echantillon_4.get(9) ; // 258
			mp = MotDePasseCotation.analyser(tout110_38) ;
			if (mp.getForceBits() != 258 || !mp.getNiveauAnssi().equals("fort (>256b)"))
				test = false ; 
			String tout110_18 = echantillon_4.get(8).substring(0, 18) ; // 123
			mp = MotDePasseCotation.analyser(tout110_18) ;
			if (mp.getForceBits() != 123 || !mp.getNiveauAnssi().equals("fort"))
				test = false ; 
			assertTrue(test)  ;		
		}
	
	// ECHANTILLON ///////////

	public void echantillonStr() {

		String chiffres10_6 = "258741";
		String chiffres10_19 = "5876130649865312568";
		String chiffres10_20 = "58761306498653125689";
		String chiffres10_23 = "58761306498653125689487";
		String chiffres10_24 = "587613064986531256894875";
		String chiffres10_27 = "587613064986531256894875654";
		String chiffres10_28 = "5876130649865312568974521025";
		String chiffres10_77 = "58761306498653125689745210255876130649865312568974521025587613064986531256897";

		String min26_14 = "dksozkdlskdjfc";
		String min26_15 = "dksozkdlskdjfcq";
		String min26_17 = "dksozkdlskdjfcqae";
		String min26_18 = "dksozkdlskdjfcqaes";
		String min26_19 = "dksozkdlskdjfcqaesz";
		String min26_20 = "dksozkdlskdjfcqaeszq";

		String chiffMin36_6 = "1f2d2s";
		String chiffMin36_12 = "d5h5f5d5c5f5";
		String chiffMin36_13 = "d5h5f5d5c5f56";
		String chiffMin36_15 = "d5h5f5d5c5f56zq";
		String chiffMin36_16 = "d5h5f5d5c5f56zqz";
		String chiffMin36_17 = "d5h5f5d5c5f56zqz5";
		String chiffMin36_18 = "d5h5f5d5c5f56zqz59";

		String minMaj52_9 = "FrUfKdAdO";
		String minMaj52_10 = "FrUfKdAdOs";
		String minMaj52_11 = "FrUfKdAdOsz";
		String minMaj52_12 = "FrUfKdAdOszH";
		String minMaj52_14 = "FrUfKdAdOszHbB";
		String minMaj52_15 = "FrUfKdAdOszHbBa";

		String minMajChiff62_7 = "Fr6kd2j";
		String minMajChiff62_8 = "Fr6kd2j3";
		String minMajChiff62_9 = "Fr6kd2j3g";
		String minMajChiff62_10 = "Fr6kd2j3gg";
		String minMajChiff62_12 = "Fr6kd2j3ggHs";
		String minMajChiff62_13 = "Fr6kd2j3ggHs9";

		String minMajAccentChiff74_7 = "mAé5d1s";
		String minMajAccentChiff74_8 = "mAé5d1s5";
		String minMajAccentChiff74_9 = "mAé5d1s5ù";
		String minMajAccentChiff74_10 = "mAé5d1s5ùL";
		String minMajAccentChiff74_11 = "mAé5d1s5ùLd";

		String tout110_6 = "5gAù%(";
		String tout110_7 = "5gtAù%(";
		String tout110_8 = "5gA0@ù%(";
		String tout110_9 = "5gAùù'{%(";
		String tout110_10 = "5gA5e8]ù%(";
		String tout110_19 = "5gA5e8]ù%(5gA5e8]ù%";
		String tout110_38 = "5gA5e8]ù%(5gA5e8]ù%5gA5e8]ù%(5gA5e8]ù%";

		echantillon_1.add(chiffres10_6);
		echantillon_1.add(chiffres10_19);
		echantillon_1.add(min26_14);
		echantillon_1.add(chiffMin36_6);
		echantillon_1.add(chiffMin36_12);
		echantillon_1.add(minMaj52_9);
		echantillon_1.add(minMajChiff62_7);
		echantillon_1.add(minMajAccentChiff74_7);
		echantillon_1.add(tout110_6);

		echantillon_2.add(chiffres10_20);
		echantillon_2.add(chiffres10_23);
		echantillon_2.add(min26_15);
		echantillon_2.add(min26_17);
		echantillon_2.add(chiffMin36_13);
		echantillon_2.add(chiffMin36_15);
		echantillon_2.add(minMaj52_10);
		echantillon_2.add(minMaj52_11);
		echantillon_2.add(minMajChiff62_8);
		echantillon_2.add(minMajChiff62_9);
		echantillon_2.add(minMajAccentChiff74_8);
		echantillon_2.add(minMajAccentChiff74_9);
		echantillon_2.add(tout110_7);

		echantillon_3.add(chiffres10_24);
		echantillon_3.add(chiffres10_27);
		echantillon_3.add(min26_18);
		echantillon_3.add(min26_19);
		echantillon_3.add(chiffMin36_16);
		echantillon_3.add(chiffMin36_17);
		echantillon_3.add(minMaj52_12);
		echantillon_3.add(minMaj52_14);
		echantillon_3.add(minMajChiff62_10);
		echantillon_3.add(minMajChiff62_12);
		echantillon_3.add(minMajAccentChiff74_10);
		echantillon_3.add(tout110_8);
		echantillon_3.add(tout110_9);

		echantillon_4.add(chiffres10_28);
		echantillon_4.add(chiffres10_77);
		echantillon_4.add(min26_20);
		echantillon_4.add(chiffMin36_18);
		echantillon_4.add(minMaj52_15);
		echantillon_4.add(minMajChiff62_13);
		echantillon_4.add(minMajAccentChiff74_11);
		echantillon_4.add(tout110_10);
		echantillon_4.add(tout110_19);
		echantillon_4.add(tout110_38);
	}

}
