package fr.hibon.modepassesecurest.motpasse;

import java.util.ArrayList;

public class ChainePasse {

	

	private String chaineDuPasse;

	private int longueur ; 
	
	private boolean avecChiffre, avecMinuscule, avecMajuscule, avecAccentCedil, avecSpecial;
	
	private ChainePasse() {
		chaineDuPasse = null;
		longueur = 1 ;
		avecChiffre = false;
		avecMinuscule = false;
		avecMajuscule = false;
		avecAccentCedil = false;
		avecSpecial = false;
	}
	
	// Construit un ChainePasse à partir d'une chaine et analyse types caractères présents
	public static ChainePasse composition(String aTester) {
		ChainePasse mp = new ChainePasse();
		if (aTester == null)
			return mp ;
		mp.chaineDuPasse = aTester ;
		mp.longueur = aTester.length() ;
		Caractere valCharATester = new Caractere(0) ;
		for (int i = 0; i < aTester.length(); i++) {
			valCharATester.setLaValeurDuChar(aTester.charAt(i)) ;
			if (valCharATester.estUnChiffre())
				mp.avecChiffre = true;
			if (valCharATester.estUneMinuscule())
				mp.avecMinuscule = true;
			if (valCharATester.estUneMajuscule())
				mp.avecMajuscule = true;
			if (valCharATester.estUnAccentCedil())
				mp.avecAccentCedil = true;
			if (valCharATester.estUnSpecial())
				mp.avecSpecial = true;
		}
		return mp;
	}
	
	
	// Chaine : getter, setter, longueur
	
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
	
	public static ChainePasse genererMotDePasse() {
		return genererMotDePasse(10);
	}

	public static ChainePasse genererMotDePasse(int n) {
		if (n < 1)
			n = 1; 	
		if (n > 60)
			n = 60 ;
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


	// Generer ChainePasse Param�tr�

	public static ChainePasse genererMotDePasse(int n, boolean chiffres, boolean minusc, boolean majusc, boolean accent, boolean special, ArrayList<Integer> exclusions, ArrayList<Integer> inclusions ) {			
		int nbTypes = 0 ;
		String leMP = "";
		ChainePasse mp = new ChainePasse();
		Integer[] pool = Caractere.poolCaracteres(chiffres, minusc, majusc, accent, special) ;
		
		// mot de passe de 1 à 60 caractères
		if (n < 1)
			n = 1; 	
		if (n > 60)
			n = 60 ;
		
		// permet l'inclusion mais pas de piocher les caract�res initiaux dedans...
		// il faut prendre en compte leur type...
		if (inclusions != null)
			pool = Caractere.completerPool(pool, inclusions) ;
		
		
		
		if(chiffres){
			leMP += Caractere.genererCaractere(Caractere.getChiffres(), exclusions) ;
			mp.avecChiffre = true ; 
			nbTypes++ ; 	
		}
		if(minusc){
			leMP +=Caractere.genererCaractere(Caractere.getMinuscules(), exclusions) ;
			mp.avecMinuscule = true ; 
			nbTypes++ ; 	
		}
		if(majusc){
			leMP +=Caractere.genererCaractere(Caractere.getMajuscules(), exclusions) ;
			mp.avecMajuscule = true ; 
			nbTypes++ ; 	
		}
		if(accent){
			leMP += Caractere.genererCaractere(Caractere.getAccentcedil(), exclusions) ;
			mp.avecAccentCedil = true ; 
			nbTypes++ ; 	
		}
		if(special){
			leMP += Caractere.genererCaractere(Caractere.getSpeciaux(), exclusions) ;
			mp.avecSpecial = true ; 
			nbTypes++ ; 	
		}

		if (nbTypes == 0) {
			leMP += Caractere.genererCaractere(Caractere.getChiffres(), exclusions) ;
			mp.avecChiffre = true ;
			nbTypes = 1 ;
		}
		for (int i = 0; i < n - nbTypes ; i++) {
			leMP += Caractere.genererCaractere(pool, exclusions) ;
		}
		mp.chaineDuPasse = leMP;
		return mp;
	}

	


	// Completer une ChainePasse � 10 
	public static ChainePasse passeComplete10(ChainePasse motPasse) {
		String nouvelleChaine = motPasse.chaineDuPasse ; 		
		nouvelleChaine += genererMotDePasse(10 - motPasse.longueur) ; 
		return composition(nouvelleChaine);  
		
	}

	// getters pour composition mot de passe 
	
	public boolean avecChiffre() {
		return avecChiffre;
	}

	public boolean avecMinuscule() {
		return avecMinuscule;
	}

	public boolean avecMajuscule() {
		return avecMajuscule;
	}

	public boolean avecAccentcedil() {
		return avecAccentCedil;
	}

	public boolean avecSpecial() {
		return avecSpecial;
	}

	// TODO Masquer
	public String toString() {
		return this.chaineDuPasse ;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (avecAccentCedil ? 1231 : 1237);
		result = prime * result + (avecChiffre ? 1231 : 1237);
		result = prime * result + (avecMajuscule ? 1231 : 1237);
		result = prime * result + (avecMinuscule ? 1231 : 1237);
		result = prime * result + (avecSpecial ? 1231 : 1237);
		result = prime * result + ((chaineDuPasse == null) ? 0 : chaineDuPasse.hashCode());
		result = prime * result + longueur;
		return result;
	}

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
	
}

