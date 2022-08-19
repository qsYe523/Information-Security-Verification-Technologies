import java.io.*;
import java.net.*;
import java.security.*;

public class PublicKeySystem_Bob {
    public static void main(String[] args) throws Exception {
    	
    	int port = 7999;
        ServerSocket server = new ServerSocket(port);
        Socket s = server.accept();
    	
        // Use getInstance() method creating KeyPairGenerator object.
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        // Use initialize() method initializing the KeyPairGenerator object.
        keyPairGenerator.initialize(2048);
        // Use generateKeyPair() method generate the keyPair
        KeyPair pair = keyPairGenerator.generateKeyPair();
    	// Generate the pairs of public and private key for Bob.
        PublicKey publicKey_Bob = pair.getPublic();
        PrivateKey privateKey_Bob = pair.getPrivate();

        // Create a file.dat and save the publicKey_Bob in it.
        FileOutputStream fileOutputStream_Bob = new FileOutputStream("publicKey_Bob.dat");
        ObjectOutputStream output = new ObjectOutputStream(fileOutputStream_Bob);
        output.writeObject(publicKey_Bob);
        output.flush();
        output.close();
        
        DataInputStream inputStream = new DataInputStream(s.getInputStream());
        String signature = inputStream.readUTF();
        String encryptedMessage = inputStream.readUTF();
        
        //Read public key from Alice.
        ObjectInputStream input = new ObjectInputStream(new FileInputStream("publicKey_Alice.dat"));
        PublicKey publicKey_Alice = (PublicKey) input.readObject();
        input.close();
        
        String decrypted_Message = Encrypt_Decrypt_Signature.decrypt(encryptedMessage, privateKey_Bob);
        System.out.println("The original message is: "+ decrypted_Message);

        String hash_Message = Hash.hashes(decrypted_Message, "SHA");
        boolean result = Encrypt_Decrypt_Signature.verifySign(hash_Message, signature, publicKey_Alice);
        if (result == true){
            System.out.println("Validation succeeded.");
        }
        else{
            System.out.println("Validation failed.");
        }

        s.close();
    }

}