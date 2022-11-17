package dataModel;

import org.apache.commons.codec.digest.DigestUtils;

public class Service {
    /*
    Qui viene utilizzato MD5.
    È una funzione unidirezionale diversa dalla codifica e dalla cifratura perché irreversibile.
    Questa funzione prende in input una stringa di lunghezza arbitraria e ne produce in output
    un'altra a 128 bit.
    */
    public static String encryptMD5(String testoChiaro){
        String key = DigestUtils.md5Hex(testoChiaro).toUpperCase();
        return key;
    }
    public static boolean checkMD5(String password, String testoChiaro){
        if (password.equals(encryptMD5(testoChiaro).toUpperCase()))
            return true;
        else
            return false;
    }

    /*
    Qui viene utilizzato SHA2.
    Con il termine SHA (Secure Hash Algorithm) si indica una famiglia di cinque diverse
    funzioni crittografiche di hash (SHA-1, SHA-224, SHA-256, SHA-384 e SHA-512).
    Come ogni algoritmo di hash, l'SHA produce un message digest di lunghezza fissa partendo
    da un messaggio di lunghezza variabile.
    La sicurezza di un algoritmo di hash risiede nel fatto che la funzione non sia invertibile
    (non sia cioè possibile risalire al messaggio originale conoscendo solo questo dato)
     */
    public static String encryptSHA2(String testoChiaro){
        String key = DigestUtils.sha256Hex(testoChiaro).toUpperCase();
        return key;
    }
    public static boolean checkSHA2(String password, String testoChiaro){
        if (password.equals(encryptSHA2(testoChiaro).toUpperCase()))
            return true;
        else
            return false;
    }

}