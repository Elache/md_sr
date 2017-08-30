package fr.hibon.modepassesecurest.compte.exception;


	/**
	 * Cette classe regroupe une enum de 5 types d'erreur <BR>
	 * 
	 * */
	public class CompteException extends Exception {

		private static final long serialVersionUID = 1L;
		private final ErreurDetail erreur;
		private String description = "";

		public CompteException(ErreurDetail erreur) {
			this.erreur = erreur;
			if (erreur == ErreurDetail.NomRepertoireAmbigu)
				description = "Plusieurs répertoires portent ce nom";
			if (erreur == ErreurDetail.RepertoireNExistePas) 
				description = "Ce répertoire n'existe pas";
			if (erreur == ErreurDetail.DonneeNExistePas)
				description = "Cette donnée n'existe pas";
			if (erreur == ErreurDetail.MotDePasseVide)
				description = "Le mot de passe est vide" ;
			if (erreur == ErreurDetail.IdentifiantVide)
				description = "L'identifiant est vide";
		}

		public ErreurDetail getErreur() {
			return erreur;
		}

		public String getDescription() {
			return description;
		}

		/**
		 * enum : 5 types d'erreur &agrave; passer en param&egrave;tres &agrave;
		 * CompteException <br> 
		 * (formulaire incomplet, passe et confirmation diff&eacute;rents, format
		 * mot de passe non valide, format mail invalide, identifiant
		 * d&eacute;j&agrave; utilis&eacute;
		 */
		public enum ErreurDetail {
			NomRepertoireAmbigu, RepertoireNExistePas, DonneeNExistePas, MotDePasseVide, IdentifiantVide
		}


}
