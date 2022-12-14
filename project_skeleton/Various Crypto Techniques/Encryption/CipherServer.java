import java.io.*;
import java.net.*;

public class CipherServer
{
	public static void main(String[] args) throws Exception 
	{
		int port = 7999;
		ServerSocket server = new ServerSocket(port);
		Socket s = server.accept();

		// YOU NEED TO DO THESE STEPS:
		// -Read the key from the file generated by the client.
		String path = ".\\Storage.txt";
		InputStreamReader input = new InputStreamReader(new FileInputStream(new File(path)));
		BufferedReader bufferedReader = new BufferedReader(input);
		String pw_Key = new BufferedReader(input).readLine();

		byte[] encryptMessage1 = new byte[100];
		DataInputStream inputStream  = new DataInputStream(s.getInputStream());
		inputStream.read(encryptMessage1);	
		
		// -Convert the received message into a qualified length
		int count = 0;
		for (int i=0; i<encryptMessage1.length; i++){
			if (encryptMessage1[i] != 0){
				count++;
			}
		}
		
		byte[] encryptMessage2 = new byte[count];
		for (int j=0;j<encryptMessage2.length;j++){
			encryptMessage2[j] = encryptMessage1[j];
		}
		// -Use the key to decrypt the incoming message from socket s.	
		String decrypted_Message = new String(Encrypt_And_Decrypt.decrypt(encryptMessage2, pw_Key));
		// -Print out the decrypt String to see if it matches the orignal message.
		System.out.println("After decryption, the original message is: "+decrypted_Message);
		s.close();
	}

}