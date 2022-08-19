import java.io.*;
import java.net.*;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class X509_Client {
    public static void main(String[] args) throws Exception{
    	
    	String message = "The quick brown fox jumps over the lazy dog.";
        String host = "localhost";
        int port = 7999;
        Socket s = new Socket(host, port);
        
        // Read cert.cer
        FileInputStream fileInputStream = new FileInputStream("cert.cer");
        CertificateFactory cert_Factory = CertificateFactory.getInstance("X509");
        X509Certificate cert = (X509Certificate) cert_Factory.generateCertificate(fileInputStream);
        
        // Verify the public key in the cert.cer
        PublicKey public_Key = cert.getPublicKey();
        try {
            cert.verify(cert.getPublicKey());
            System.out.println("Public key verified.");
        }catch (SignatureException e) {
            System.out.println("Public key verification failed.");
        }
        
        // Print out the the expiration date and the content of the certificate.
        System.out.println("The certificate is valid from "+cert.getNotBefore()+" to "+cert.getNotAfter());
        System.out.println(cert.toString());
        
        String encrypted_Message = Encrypt_Decrypt_Signature.encrypt(message, public_Key);
        DataOutputStream dataOutputStream = new DataOutputStream(s.getOutputStream());
        dataOutputStream.writeUTF(encrypted_Message);
        dataOutputStream.close();
    }

}
