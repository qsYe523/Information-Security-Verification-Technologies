import java.io.*;
import java.net.*;

public class CipherClient
{
	public static void main(String[] args) throws Exception 
	{
		
		String message = "The quick brown fox jumps over the lazy dog.";
		String host = "localhost";
		int port = 7999;
		Socket s = new Socket(host, port);
		// YOU NEED TO DO THESE STEPS:
		//-Set the specified string for machining key.
		String password = "encryption";
		//-Created a new file and storage the specified string in it .
		File storage = new File(".\\Storage.txt");
		storage.createNewFile();
		FileWriter output = new FileWriter(storage);
		BufferedWriter buffered_Writer = new BufferedWriter(output);
		buffered_Writer.write(password);
		buffered_Writer.flush();
		buffered_Writer.close();
		// -Generate a DES key.(Generates a key using the specified string)
		// -Use the key to encrypt the message above and send it over socket s to the server.
		Encrypt_And_Decrypt a = new Encrypt_And_Decrypt();
		byte[] encrypted_Message = Encrypt_And_Decrypt.encrypt(message.getBytes(), password);
		DataOutputStream outputStream = new DataOutputStream(s.getOutputStream());
		outputStream.write(encrypted_Message);
		s.close();
	}

}