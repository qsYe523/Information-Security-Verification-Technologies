import java.io.*;
import java.net.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.SecretKey;

public class Encrypt_And_Decrypt {
	
	// Generates the specified key using the entered string, and then encrypt the message
	public static byte[] encrypt(byte[] encrypt_Message, String original_password){
		try{
			DESKeySpec desKey = new DESKeySpec(original_password.getBytes());
			SecretKey secKey = SecretKeyFactory.getInstance("DES").generateSecret(desKey);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, secKey, new SecureRandom());
			return cipher.doFinal(encrypt_Message);
		}catch (Throwable e){
			e.printStackTrace();
		}
		return null;
	}
	// Use the input string to generate the specified key, and then decrypt the message
	public static byte[] decrypt(byte[] decrypt_Message, String password_From_Storage){
		try {
			DESKeySpec desKey = new DESKeySpec(password_From_Storage.getBytes());
			SecretKey secKey = SecretKeyFactory.getInstance("DES").generateSecret(desKey);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, secKey, new SecureRandom());
			return cipher.doFinal(decrypt_Message);
		}catch (Throwable e){
			e.printStackTrace();
		}
		return null;
	}

}
