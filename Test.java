package Vigenere;

public class Test {
	
	public static void main(String[] args) {
		/*CaesarCipher caeserCipher = new CaesarCipher(5);
		FileResource fr1 = new FileResource();
		String message1 = fr1.asString();
		caeserCipher.decrypt(message1);
		caeserCipher.encrypt(message1);
		
		CaesarCracker caesarCracker = new CaesarCracker();
		FileResource fr = new FileResource();
		String message = fr.asString();
		System.out.println(caesarCracker.decrypt(message));
		System.out.println(caesarCracker.getKey(message));*/
		
		/*int[] key = {17,14,12,4};
		VigenereCipher vigenereCipher = new VigenereCipher(key);
		FileResource fr0 = new FileResource();
		String message = fr0.asString();
		System.out.println(vigenereCipher.decrypt(message));
		System.out.println(vigenereCipher.encrypt(message));*/
		
		VigenereBreaker test = new VigenereBreaker();
		//System.out.println(test.sliceString("abcdefghijklm", 4, 5));
		/*FileResource file = new FileResource();
		String message = file.asString();
		FileResource dict = new FileResource();
		HashSet<String> di = test.readDictionary(dict);
		int[] key =test.tryKeyLength(message, 57, 'e');
		VigenereCipher vigenereCipher = new VigenereCipher(key);
		String msg = vigenereCipher.decrypt(message);
		System.out.println(test.countWords(msg, di));
		System.out.println(msg);*/
		test.breakVigenere();
	}
}
