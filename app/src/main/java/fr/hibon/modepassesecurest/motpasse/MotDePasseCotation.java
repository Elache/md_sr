package fr.hibon.modepassesecurest.motpasse;

/**
 * Assure l'analyse des mots de passe
 * <BR>Methode de l'application ModePasse SecuRest
 * <BR>+ methode de l'ANSSI (force en bits) : https://www.ssi.gouv.fr/administration/precautions-elementaires/calculer-la-force-dun-mot-de-passe/
 */
public class MotDePasseCotation {

	private final ChainePasse motPasse;

	private final int valeurAnalyse;
	private final String qualifieAnalyse;

	private final int forceBits;
	private final String niveauAnssi;

	/**
	 * Constructeur priv&eacute; <BR>Objet avec resultats d'analyse pour qualifier la force du mot de passe
	 * @param chaineToAnalyse mot a analyser
	 */
	private MotDePasseCotation(String chaineToAnalyse) {
		motPasse = ChainePasse.composition(chaineToAnalyse);
		motPasse.setMotPasse(chaineToAnalyse);
		this.forceBits = quantifierBits();
		this.niveauAnssi = classifAnssi();
		this.valeurAnalyse = bilanChiffre();
		this.qualifieAnalyse = bilanTexte();
	}

	/**
	 *  Constructeur priv&eacute; <BR>Objet avec resultats d'analyse pour qualifier la force du mot de passe
	 * @param passToAnalyse ChainePasse a analyser
	 */
	private MotDePasseCotation(ChainePasse passToAnalyse) {
		motPasse = passToAnalyse ;
		this.forceBits = quantifierBits();
		this.niveauAnssi = classifAnssi();
		this.valeurAnalyse = bilanChiffre();
		this.qualifieAnalyse = bilanTexte();
	}

	/**
	 * Analyse une chaine pour qualifier la force du mot de passe
	 * @param pass mot a analyser
	 * @return Objet MotDePasseCotation avec les resultats d'analyse
	 */
	public static MotDePasseCotation analyser(String pass) {
		return new MotDePasseCotation(pass);
	}

	/**
	 * Analyse un ChainePasse pour qualifier la force du mot de passe
	 * @param pass ChainePasse a analyser
	 * @return Objet MotDePasseCotation avec les resultats d'analyse
	 */
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

	/**
	 * Retourne le niveau du mot de passe qualifi&eacute; par l'application
	 * <BR>fonction de la longueur et du nombre de caracteres potentiellement utilisables
	 * @return force du mot de passe (1 &agrave; 4)
	 */
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

	/**
	 * Retourne le niveau du mot de passe qualifi&eacute; par l'application
	 * @return force du mot de passe (textuel)
	 */
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

	/**
	 * Determine la force en bits d'un mot de passe d'apres les combinaisons possibles
	 * @return force en bits
	 */
	private int quantifierBits() {
		int bits = 1;
		double stat = combinaisonsPossibles();
		while (stat >= 2) {
			stat = stat / 2;
			bits++;
		}
		return bits;
	}

	/**
	 * Decrit le niveau du mot de passe suivant sa force en bits (methode ANSSI)
	 * @return niveau ANSSI (textuel)
	 */
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


	/**
	 * Calcul les combinaisons possibles suivant la longueur du mot et le nombre de caracteres differents utilisables
	 * <BR>limite 1,8^308 (passw ~150 long ~1024 b)
	 * @return nombre de combinaisons possibles
	 */
	private double combinaisonsPossibles() {
		return Math.pow(determinerEtendue(), motPasse.longeurMot());
	}

	/**
	 * A partir des types de caracteres representes, determine la taille (minimale) du pool initial de caracteres
	 * @return nombre de caracteres du pool de depart
	 */
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
