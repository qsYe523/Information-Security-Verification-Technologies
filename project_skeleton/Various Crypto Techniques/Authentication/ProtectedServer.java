import java.io.*;
import java.net.*;
import java.security.*;

public class ProtectedServer
{
	public boolean authenticate(InputStream inStream) throws IOException, NoSuchAlgorithmException 
	{
		DataInputStream in = new DataInputStream(inStream);

		//Get the Strings from the InputStream.
		//Read time stamps and random numbers from client
		String user = in.readUTF();
		Long timeStamps1 = in.readLong();
		Double random_Numbers1 = in.readDouble();
		Long timeStamps2 = in.readLong();
		Double random_Numbers2 = in.readDouble();
		String received_Messages = in.readUTF();
		byte[] first_Digest = Protection.makeDigest(user, lookupPassword(user), timeStamps1, random_Numbers1);
		byte[] second_Digest = Protection.makeDigest(first_Digest, timeStamps2, random_Numbers2);

		String sent_Messages = Hash.Byte_arrayToString(second_Digest);
		
		boolean temp = true;
		temp = sent_Messages.equals(received_Messages);
		return temp;
		
	}

	protected String lookupPassword(String user) { return "abc123"; }

	public static void main(String[] args) throws Exception 
	{
		int port = 7999;
		ServerSocket s = new ServerSocket(port);
		Socket client = s.accept();

		ProtectedServer server = new ProtectedServer();

		if (server.authenticate(client.getInputStream()))
		  System.out.println("Client logged in.");
		else
		  System.out.println("Client failed to log in.");

		s.close();
	}
}