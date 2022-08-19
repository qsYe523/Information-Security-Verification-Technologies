import java.security.*;
import java.util.Base64;
import javax.crypto.Cipher;

public class Encrypt_Decrypt_Signature {
	
	public static String encrypt(String encrypt_Message, PublicKey public_Key) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, public_Key);
        byte[] encrypted_Message = cipher.doFinal(encrypt_Message.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encrypted_Message);
    }
	public static String decrypt(String decrypt_Message, PrivateKey private_Key) throws Exception{
        byte[] b = Base64.getDecoder().decode(decrypt_Message);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, private_Key);
        return new String(cipher.doFinal(b),"UTF-8");
    }
	public static String signature(String plain_Text, PrivateKey private_Key) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(private_Key);
        signature.update(plain_Text.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(signature.sign());
	}
    public static boolean verifySign(String plain_Text, String sign, PublicKey public_Key) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(public_Key);
        signature.update(plain_Text.getBytes("UTF-8"));
        byte[] b = Base64.getDecoder().decode(sign);
        return signature.verify(b);
    }

}
