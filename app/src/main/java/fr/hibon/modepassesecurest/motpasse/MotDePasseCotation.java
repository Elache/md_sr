package fr.hibon.modepassesecurest.motpasse;

public class MotDePasseCotation {

	private final ChainePasse motPasse;

	private final int valeurAnalyse;
	private final String qualifieAnalyse;

	private final int forceBits;
	private final String niveauAnssi;

	private MotDePasseCotation(String chaineToAnalyse) {
		motPasse = ChainePasse.composition(chaineToAnalyse);
		motPasse.setMotPasse(chaineToAnalyse);
		this.forceBits = quantifierBits();
		this.niveauAnssi = classifAnssi();
		this.valeurAnalyse = bilanChiffre();
		this.qualifieAnalyse = bilanTexte();
	}
	
	private MotDePasseCotation(ChainePasse passToAnalyse) {
		motPasse = passToAnalyse ;
		this.forceBits = quantifierBits();
		this.niveauAnssi = classifAnssi();
		this.valeurAnalyse = bilanChiffre();
		this.qualifieAnalyse = bilanTexte();
	}

	public static MotDePasseCotation analyser(String pass) {
		return new MotDePasseCotation(pass);
	}
	
	public static MotDePasseCotation analyser(ChainePasse pass) {
		return new MotDePasseCotation(pass);
	}

	public ChainePasse getMotPasse() {
		return this.motPasse;
	}
	
	public int longueurPasse() {
		return this.motPasse.longeurMot(); 
	}
	
	
	/// ANALYSE du MOT DE PASSE //////////////

	private int bilanChiffre() {

		// Cas lies uniquement a la longueur
		if (motPasse.longeurMot() <= 6)
			return 1;
		if (motPasse.longeurMot() >= 28)
			return 4;

		//
		if (this.determinerEtendue() <= 12) {
			if (motPasse.longeurMot() < 20) {
				return 1;
			}
			if (motPasse.longeurMot() < 24) {
				return 2;
			}
			return 3;
		}

		//
		if (this.determinerEtendue() <= 26) {
			if (motPasse.longeurMot() < 15) {
				return 1;
			}
			if (motPasse.longeurMot() < 18) {
				return 2;
			}
			if (motPasse.longeurMot() < 20) {
				return 3;
			}
			return 4;
		}

		//
		if (this.determinerEtendue() <= 38) {
			if (motPasse.longeurMot() < 13) {
				return 1;
			}
			if (motPasse.longeurMot() < 16) {
				return 2;
			}
			if (motPasse.longeurMot() < 18) {
				return 3;
			}
			return 4;
		}

		//
		if (this.determinerEtendue() <= 52) {
			if (motPasse.longeurMot() < 10) {
				return 1;
			}
			if (motPasse.longeurMot() < 12) {
				return 2;
			}
			if (motPasse.longeurMot() < 15) {
				return 3;
			}
			return 4;
		}

		//
		if (this.determinerEtendue() <= 64) {
			if (motPasse.longeurMot() < 8) {
				return 1;
			}
			if (motPasse.longeurMot() < 10) {
				return 2;
			}
			if (motPasse.longeurMot() < 13) {
				return 3;
			}
			return 4;
		}

		//
		if (this.determinerEtendue() <= 74) {
			if (motPasse.longeurMot() < 8) {
				return 1;
			}
			if (motPasse.longeurMot() < 10) {
				return 2;
			}
			if (motPasse.longeurMot() < 11) {
				return 3;
			}
			return 4;
		}

		//
		if (motPasse.longeurMot() < 8) {
			return 2;
		}
		if (motPasse.longeurMot() < 10) {
			return 3;
		}
		return 4;
	}

	private String bilanTexte() {
		switch (valeurAnalyse) {
		case 1:
			return "Très faible";
		case 2:
			return "Faible";
		case 3:
			return "Acceptable";
		case 4:
			return "Bon";
		default:
			return "1";
		}
	}


	public int getValeurAnalyse() {
		return valeurAnalyse;
	}

	public String getQualifieAnalyse() {
		return qualifieAnalyse;
	}


	/// ANSSI ///

	private int quantifierBits() {
		int bits = 1;
		double stat = combinaisonsPossibles();
		while (stat >= 2) {
			stat = stat / 2;
			bits++;
		}
		return bits;
	}

	private String classifAnssi() {
		if (this.forceBits <= 64)
			return "très faible";
		if (this.forceBits <= 80)
			return "faible";
		if (this.forceBits <= 100)
			return "moyen";
		if (this.forceBits <= 128)
			return "fort";
		if (this.forceBits <= 256)
			return "fort (>128b)";
		return "fort (>256b)"; // aes256
	}

	public int getForceBits() {
		return forceBits;
	}

	public String getNiveauAnssi() {
		return niveauAnssi;
	}

	
	/// Utilitaires ///


	// combinaisons : limite 1,8^308 (passw ~150 long ~1024 b)
	private double combinaisonsPossibles() {
		return Math.pow(determinerEtendue(), motPasse.longeurMot());
	}

	private int determinerEtendue() {
		int gamme = 0;
		if (motPasse.avecChiffre())
			gamme += 10;
		if (motPasse.avecMinuscule())
			gamme += 26;
		if (motPasse.avecMajuscule())
			gamme += 26;
		if (motPasse.avecAccentcedil())
			gamme += 12;
		if (motPasse.avecSpecial())
			gamme += 36;
		return gamme;
	}

}
