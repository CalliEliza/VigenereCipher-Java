package Vigenere;

import java.util.HashMap;
import java.util.HashSet;

import edu.duke.FileResource;

public class VigenereBreaker {
	
	private String correctAns;
	//slices message up based on where key starts and how long it is. 
	public String sliceString(String message, int whichSlice, int totalSlices) {
		StringBuilder sliced = new StringBuilder();
        for (int i = whichSlice; i <message.length(); i+=totalSlices) {
        		char sub = message.charAt(i);
        		sliced.append(sub);
        		
        }
        String finalSliced = sliced.toString();
        return finalSliced;
    }

	// finds the keys when the keylength is known
    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        CaesarCracker caesarCracker = new CaesarCracker(mostCommon);
        for (int i = 0; i < klength; i++) {
        	String temp = sliceString(encrypted, i, klength);
        	int key0 = caesarCracker.getKey(temp);
        	key[i] = key0;
        	//System.out.print(key0 + " ");
        }
        return key;
    }
    
    // creates HashSet of words in dictionary
    public HashSet<String> readDictionary(FileResource fr) {
    	HashSet<String> dict = new HashSet<String>();
    	String words = fr.asString();
    	for (String word: words.split("\\W")) {
    		String lword = word.toLowerCase();
    		dict.add(lword);
    		//System.out.println(lword);
    	}
    	//System.out.println("Dictionary: " +dict.size());
    	return dict;
    }
    
    // loops through message and checks to see how many real words exists
    public int countWords(String message, HashSet<String> dictionary) {
    	int count = 0;
    	for (String word : message.split("\\W")) {
    		if (!word.isEmpty()) {
    			String lword = word.toLowerCase();
    			if (dictionary.contains(lword)) {
    				count++;
    				//System.out.println(lword);
    			}
    		}
    	}
    	//System.out.println(count);
    	return count;
    }
    
    public String breakForLanguage(String encrypted, HashSet<String> dictionary) {
    	int counter = 0;
    	int[] answerKey = null;
    	char commonChar = mostCommonCharIn(dictionary);
    	for (int i = 1; i < 101; i++) {
    		int[] key = tryKeyLength(encrypted, i, commonChar);
    		VigenereCipher cipher = new VigenereCipher(key);
    		String answer = cipher.decrypt(encrypted);
    		String answerL = answer.toLowerCase();
    		int count = countWords(answerL, dictionary);
    		if (count > counter) {
    			counter = count;
    			correctAns = answer;
    			answerKey = key;
    			//System.out.println(correctAns);
    		}
    	}
    	//System.out.println("Length of Key: " +answerKey.length);
    	return correctAns;
    }

    public char mostCommonCharIn(HashSet<String> dictionary) {
    	char c = 'c';
    	HashMap<Character, Integer> charCount = new HashMap<Character, Integer>();
    	// creates a hashmap to count characters in dictionary
    	// loops through words in dictionary
    	for (String s : dictionary) {
    		// loops through characters in string
    		for (int i = 0; i < s.length(); i++) {
    			// finds instance of character
    			char ch = s.charAt(i);
    			// if it isn't in hashmap, add to hash map with value one.
    			if (!charCount.containsKey(ch)) {
    				charCount.put(ch, 1);
    			}
    			else {
    				// if key exists, add one to counter value
    				int value = charCount.get(ch);
    				charCount.put(ch, value + 1);
    			}
    		}
    	}
    	// finds the character with the most instances by looping through hashmap
    	int counter = 0;
    	for (char character : charCount.keySet()) {
    		int value = charCount.get(character);
    		if (value > counter) {
    			counter = value;
    			c = character;
    		}
    	}
    	return c;
    }
    
    public String breakForAllLanguages(String encrypted, HashMap<String, HashSet<String>> languages) {
    	int largestCount = 0;
    	String correctLang = " ";
    	String currentAnswer = " ";
    	for (String language : languages.keySet()) {
    		HashSet<String> currentDict = languages.get(language);
    		currentAnswer = breakForLanguage(encrypted, currentDict);
    		int count = countWords(currentAnswer, currentDict);
    		if (count > largestCount) {
    			largestCount = count;
    			correctLang = language;
    		}
    	}
    	System.out.println(currentAnswer);
    	return correctLang;
    }
    
    // puts all above methods together, uses the VigenereCipher to crack it
    public void breakVigenere () {
        FileResource file = new FileResource();
        String s = file.asString();
        FileResource danish = new FileResource();
        HashSet<String> danDict = readDictionary(danish);
        FileResource dutch = new FileResource();
        HashSet<String> dutchDict = readDictionary(dutch);
        FileResource english = new FileResource();
        HashSet<String> engDict = readDictionary(english);
        FileResource french = new FileResource();
        HashSet<String> frenDict = readDictionary(french);
        FileResource german = new FileResource();
        HashSet<String> germDict = readDictionary(german);
        FileResource italian = new FileResource();
        HashSet<String> italianDict = readDictionary(italian);
        FileResource portuguese = new FileResource();
        HashSet<String> portDict = readDictionary(portuguese);
        FileResource spanish = new FileResource();
        HashSet<String> spanDict = readDictionary(spanish);
        HashMap<String, HashSet<String>> languages = new HashMap<String, HashSet<String>>();
        languages.put("english", engDict);
        languages.put("danish", danDict);
        languages.put("dutch", dutchDict);
        languages.put("french", frenDict);
        languages.put("german", germDict);
        languages.put("italian", italianDict);
        languages.put("portuguese", portDict);
        languages.put("spanish", spanDict);
        String decrypt = breakForLanguage(s, germDict);
        //String decrypt = breakForAllLanguages(s, languages);
        System.out.println(decrypt);
        }
}
