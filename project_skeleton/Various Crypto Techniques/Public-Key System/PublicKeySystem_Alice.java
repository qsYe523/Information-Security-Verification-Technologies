import java.io.*;
import java.net.*;
import java.security.*;

public class PublicKeySystem_Alice {
	
    public static void main(String[] args) throws Exception {
    	
    	String message = "The quick brown fox jumps over the lazy dog.";
        String host = "localhost";
        int port = 7999;
        Socket s = new Socket(host, port);
        
    	// Use getInstance() method creating KeyPairGenerator object.
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        // Use initialize() method initializing the KeyPairGenerator object.
        keyPairGenerator.initialize(2048);
        // Use generateKeyPair() method generate the keyPair
        KeyPair pair = keyPairGenerator.generateKeyPair();
    	// Generate the pairs of public and private key for Alice.
        PublicKey publicKey_Alice = pair.getPublic();
        PrivateKey privateKey_Alice = pair.getPrivate();

        // Create a file.dat and save the publicKey_Alice in it.
        FileOutputStream fileOutputStream_Alice = new FileOutputStream("publicKey_Alice.dat");
        ObjectOutputStream output = new ObjectOutputStream(fileOutputStream_Alice);
        output.writeObject(publicKey_Alice);
        output.flush();
        output.close();
        
        // Read public key from Bob.
        FileInputStream fileOutputStream_Bob = new FileInputStream("publicKey_Bob.dat");
        ObjectInputStream input_PublicBob = new ObjectInputStream(fileOutputStream_Bob);
        PublicKey publicKey_Bob = (PublicKey) input_PublicBob.readObject();
        input_PublicBob.close();

        String hash_Message = Hash.hashes(message, "SHA");
        String signature = Encrypt_Decrypt_Signature.signature(hash_Message, privateKey_Alice);
        String encrypted_Message = Encrypt_Decrypt_Signature.encrypt(message, publicKey_Bob);

        DataOutputStream outputStream = new DataOutputStream(s.getOutputStream());
        outputStream.writeUTF(signature);
        outputStream.writeUTF(encrypted_Message);
        
        s.close();
    }
}
