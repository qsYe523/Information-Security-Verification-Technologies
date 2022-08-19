import java.io.*;
import java.net.*;
import java.security.*;
import java.math.BigInteger;

public class ElGamalBob
{

	private static boolean verifySignature(	BigInteger y, BigInteger g, BigInteger p, BigInteger a, BigInteger b, String message)
	{
		// IMPLEMENT THIS FUNCTION;
		// Convert String to number
		StringBuilder stringBuilder = new StringBuilder();
		char[] ch = message.toCharArray();
		int i=0;
		do {
			stringBuilder.append(Integer.valueOf(ch[i]).intValue());
			i++;
		}while( i < ch.length);
		BigInteger m = new BigInteger(stringBuilder.toString());
		BigInteger text1 = g.modPow(m,p);
		BigInteger text2 = y.modPow(a,p).multiply(a.modPow(b,p)).mod(p);
		
		// Comparing the two numbers, if it is the same, it will return true
		boolean temp = true;
		temp = text1.equals(text2);
		return temp;
	}

	public static void main(String[] args) throws Exception 
	{
		int port = 7999;
		ServerSocket s = new ServerSocket(port);
		Socket client = s.accept();
		ObjectInputStream is = new ObjectInputStream(client.getInputStream());

		// read public key
		BigInteger y = (BigInteger)is.readObject();
		BigInteger g = (BigInteger)is.readObject();
		BigInteger p = (BigInteger)is.readObject();

		// read message
		String message = (String)is.readObject();

		// read signature
		BigInteger a = (BigInteger)is.readObject();
		BigInteger b = (BigInteger)is.readObject();

		boolean result = verifySignature(y, g, p, a, b, message);

		System.out.println(message);
		
		if (result == true)
			System.out.println("Signature verified.");
		else
			System.out.println("Signature verification failed.");

		s.close();
	}
}