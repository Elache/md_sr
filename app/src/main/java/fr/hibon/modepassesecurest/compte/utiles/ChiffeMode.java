package fr.hibon.modepassesecurest.compte.utiles;

import android.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Classe assurant chiffrement et d&eacute;chiffrement
 * <BR>Ajoute aussi un pr&eacute;fixe aux cha&icirc;nes &agrave; chiffrer
 */
public class ChiffeMode {

    private String passeCode ;
    private String cleCode;

    protected static final String prefix  = "&zAgrù:vR" ;


    public ChiffeMode() {
        passeCode = null ;
        cleCode = null;;
    }


    /**
     * Methode publique de chiffrement<BR>D&eacute;l&egrave;gue pour G&eacute;n&egrave;re une cl&eacute; (genererCle())et l'enregistre (instance ChiffeMode)
     * <BR>Lance encrypter(passe, cleSK)
     * @param passe Cha&icirc;ne &agrave; chiffrer
     * @return String Cha&icirc;ne chiffr&eacute;e
     */
    public ChiffeMode chiffrer(String passe) {
        SecretKey cleSK = null;
        try {
            cleSK = genererCle();
            // enregistre la cle sous forme de String
            this.cleCode = Base64.encodeToString(cleSK.getEncoded(), Base64.DEFAULT);
            this.passeCode = encrypter(passe, cleSK);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return this ;
    }

    /**
     * Methode publique de d&eacute;chiffrement<BR>
     *     R&eacute;g&eacute;n&egrave;re la SecretKey &agrave partir de la cha&icirc;ne
     * <BR>Lance decrypter(passe, cleSK)
     * @param passe passe &agrave; d&eacute;chiffrer
     * @param cle cl&eacute; de chiffrement utilis&eacute;e (String)
     * @return Cha&icirc;ne d&eacute;chiffr&eacute;e
     */
    public String dechiffrer(String passe, String cle) {
        String passeClair = null;
        try {
            byte[] versCle = Base64.decode(cle, Base64.DEFAULT);
            SecretKey cleOriginale = new SecretKeySpec(versCle, 0, versCle.length, "AES");
            passeClair = decrypter(passe, cleOriginale);
            return passeClair ;
        } catch (Exception e) {
            return passeClair ;
        }
    }


    // /////////////////


    /**
     * G&eacute;n&eacute;&egrave;re une cl&eacute; SecretKey AES128 bits
     *
     * @return SecretKey g&eacute;n&eacute;r&eacute;e ou null si NoSuchAlgorithmException
     */
    protected SecretKey genererCle() throws NoSuchAlgorithmException {
        SecretKey cleSK = null;
        KeyGenerator kyGen = KeyGenerator.getInstance("AES");
        kyGen.init(128);
        cleSK = kyGen.generateKey();
        return cleSK;
    }

    /**
     * Retourne une cha&icirc;ne chiffr&eacute;e  (&agrave; partir d'un message et d'une cl&eacute;)
     * @param passe cha&icirc;ne &agrave; chiffrer
     * @param cle cl&eacute; de chiffrement g&eacute;n&eacute;r&eacute;e pour chiffrer
     * @return cha&icirc;ne chiffr&eacute;e
     * @throws NoSuchAlgorithmException AES128 requis mais non accessible
     * @throws NoSuchPaddingException padding mechanism unreachable
     * @throws InvalidKeyException SecretKey : invalid encoding, wrong length, uninitialized, ...
     * @throws IllegalBlockSizeException  data (Cipher) is not a multiple of the block-size (16 bytes for AES).
     * @throws BadPaddingException about padding mechanism
     */
    private String encrypter(String passe, SecretKey cle)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, cle);
        passe =  prefixer(passe);
        byte[] donnees = passe.getBytes();
        return Base64.encodeToString(cipher.doFinal(donnees), Base64.DEFAULT);
    }

    /**
     * Retourne une cha&icirc;ne d&eacute;chiffr&eacute;e  (&agrave; partir d'un message et d'une cl&eacute;)
     * @param donnees cha&icirc;ne &agrave; d&eacute;chiffrer
     * @param cle cl&eacute; de chiffrement utilis&eacute;e pour chiffrer
     * @return cha&icirc;ne d&eacute;chiffr&eacute;e
     * @throws NoSuchAlgorithmException AES128 requis mais non accessible
     * @throws NoSuchPaddingException padding mechanism unreachable
     * @throws InvalidKeyException SecretKey : invalid encoding, wrong length, uninitialized, ...
     * @throws IllegalBlockSizeException  data (Cipher) is not a multiple of the block-size (16 bytes for AES).
     * @throws BadPaddingException about padding mechanism
     */
    private String decrypter(String donnees, SecretKey cle)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, cle);
        byte[] textBytes = cipher.doFinal(Base64.decode(donnees, Base64.DEFAULT));
        String passe = new String(textBytes);
        return dePrefixer(passe);
    }

    /**
     * Ajoute un pr&eacute;fixe &agrave; la cha&icirc;ne &agrave; chiffrer (constante de ChiffeMode)
     * @param passe passe &àgrave; pr&eacute;fixer
     * @return passe pr&eacute;fix&eacute;
     */
    protected String prefixer(String passe) {
        if(passe == null)
            passe = "" ;
        return ChiffeMode.prefix + passe;
    }


    /**
     * Retire le pr&eacute;fixe ajout&eacute; (constante de ChiffeMode)
     * @param passe passe &àgrave; dé-pr&eacute;fixer
     * @return passe sans pr&eacute;fixe
     */
    protected String dePrefixer(String passe) {
        if(passe.length() <= 9)
            return "" ;
        if(!(passe.substring(0,9)).equals(ChiffeMode.prefix))
            return "" ;
        return passe.substring(ChiffeMode.prefix.length());
    }


    public String getPasseCode() {
        return passeCode;
    }

    public String getCleCode() {
        return cleCode;
    }

}