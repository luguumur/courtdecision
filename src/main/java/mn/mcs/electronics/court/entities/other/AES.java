package mn.mcs.electronics.court.entities.other;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class AES {
	
	private static final String ALGORITHM = "AES";
    private static final byte[] keyValue = 
        new byte[] { '0', 'h', 'i', 's', 'I', 's', '1', 'S', 'e', 'c', '0', '0', 't', 'K', 'e', 'y' };

     public static String encrypt(String valueToEnc) throws Exception {
    	if(valueToEnc != null){
	        Key key = generateKey();
	        Cipher c = Cipher.getInstance(ALGORITHM);
	        c.init(Cipher.ENCRYPT_MODE, key);
	        byte[] encValue = c.doFinal(valueToEnc.getBytes());
	        String encryptedValue = new BASE64Encoder().encode(encValue);
	        return encryptedValue;
    	}
    	return "";
    }

    public static String decrypt(String encryptedValue) throws Exception {
    	if(encryptedValue != null){
	        Key key = generateKey();
	        Cipher c = Cipher.getInstance(ALGORITHM);
	        c.init(Cipher.DECRYPT_MODE, key);
	        byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedValue);
	        byte[] decValue = c.doFinal(decordedValue);
	        return new String(decValue);
    	}
    	return "";
    }

    private static Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, ALGORITHM);
        // SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        // key = keyFactory.generateSecret(new DESKeySpec(keyValue));
        return key;
    }
}
