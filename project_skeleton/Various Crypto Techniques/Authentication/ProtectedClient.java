import java.io.*;
import java.net.*;
import java.security.*;
import java.util.Date;
import java.util.Random;

public class ProtectedClient
{
	public void sendAuthentication(String user, String password, OutputStream outStream) throws IOException, NoSuchAlgorithmException 
	{
		DataOutputStream out = new DataOutputStream(outStream);

		// IMPLEMENT THIS FUNCTION.
		Long timeStamps1 = new Date().getTime();
		Double random_Numbers1 = new Random().nextDouble();
		byte[] first_Digest = Protection.makeDigest(user, password, timeStamps1, random_Numbers1);
		Long timeStamps2 = new Date().getTime();
		Double random_Numbers2 = new Random().nextDouble();
		byte[] second_Digest = Protection.makeDigest(first_Digest, timeStamps2, random_Numbers2);
		String digest_Messages = Hash.Byte_arrayToString(second_Digest);

		out.writeUTF(user);
		out.writeLong(timeStamps1);
		out.writeDouble(random_Numbers1);
		out.writeLong(timeStamps2);
		out.writeDouble(random_Numbers2);
		out.writeUTF(digest_Messages);
		
		out.flush();
	}

	public static void main(String[] args) throws Exception 
	{
		String host = "localhost";
		int port = 7999;
		String user = "George";
		String password = "abc123";
		Socket s = new Socket(host, port);

		ProtectedClient client = new ProtectedClient();
		client.sendAuthentication(user, password, s.getOutputStream());

		s.close();
	}
}