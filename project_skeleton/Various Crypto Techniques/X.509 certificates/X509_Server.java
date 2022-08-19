import java.io.*;
import java.net.*;
import java.security.*;

public class X509_Server {
    public static void main(String[] args) throws Exception{
    	
    	int port = 7999;
        ServerSocket server = new ServerSocket(port);
        Socket s = server.accept();
        
        // Read the keystore by password. 
        FileInputStream fileInputStream = new FileInputStream("mykeystore");
        KeyStore keyStore = KeyStore.getInstance("JKS");
        String password = "ayeqisheng";
        keyStore.load(fileInputStream, password.toCharArray());
        PrivateKey private_Key = (PrivateKey) keyStore.getKey("yqs", password.toCharArray());
        
        DataInputStream dataInputStream = new DataInputStream(s.getInputStream());
        String encrypted_Message = dataInputStream.readUTF();

        String decrypted_Message = Encrypt_Decrypt_Signature.decrypt(encrypted_Message, private_Key);
        System.out.println("After decryption, the original message is: "+decrypted_Message);

        s.close();
    }

}
