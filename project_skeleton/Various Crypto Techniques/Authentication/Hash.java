import java.util.Scanner;
import java.security.*;

public class Hash {
	public static String hashes(String words, String function) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(function);
			//messageDigest.update() is responsible for encryption
			//words.getBytes() changes string to byte array which is encoding format
			messageDigest.update(words.getBytes());
			//Get the encrypted array
			byte[] byteArray = messageDigest.digest();
			//Change it into hexadecimal string 
			return Byte_arrayToString(byteArray);
		}catch (Exception e) {
			return null;
	    }
		
	}	
	public static String Byte_arrayToString(byte[] byteArray) {
		StringBuilder stringBuilder = new StringBuilder();
		for (byte b : byteArray) {
			stringBuilder.append(String.format("%02x", b));
		}
		return stringBuilder.toString();
	}
	
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter the words you want to hash: ");
		String words = scan.nextLine();
		System.out.print("Which kind of functions do you want to use, MD5 or SHA: ");
		String function = scan.nextLine();
		String result = hashes(words, function);
		System.out.print("The " + function + " encryption result of " + "\"" +words+ "\"" +" is: "+ result);
	}
}
