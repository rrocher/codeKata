package com.codekata;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
/**
 *The first version of code kata will use simple stream and single threading 
 * to make the code more extendable
 */
public class App 
{
	static BloomFilter bf = new BloomFilter(10000000, 6);

	static String fileDictionnary = "/usr/share/dict/words";
	public static void setBloomFilterSpeller() {

        List<String> list = new ArrayList<>();
		//read file into stream, try-with-resources
		try (Stream<String> stream = Files.lines(Paths.get(fileDictionnary))) {

			stream.filter(s->s.length()<6).forEach(s->{bf.add(s);});

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static boolean verifyValidity(String s1, String s2) {
		if (bf.contains(s1) && bf.contains(s2))
			return true;
		return false;
	}
	
	private static List<String> checkComposedString(String s) {
		List<String> set = new ArrayList<>();
		for (int x=1; x< s.length()-2;x++) {
			String s1 = s.substring(0, x+1);
			String s2 = s.substring(x+1);

			if (true == verifyValidity(s1, s2)) {
				set.add(s1);
				set.add(s2);
				return set;
			}
		}
		return null;
	}
	
    public static void main( String[] args )
    {
    	String fileName = "/home/rrocher/Downloads/wordlist.txt";

    	if (args.length > 0)
    		fileName = args[1];

    	if (args.length > 1)
    		fileDictionnary = args[2];
    	
    	setBloomFilterSpeller();
        
    	List<String> list = new ArrayList<>();
		//read file into stream, try-with-resources
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

			 list = stream
	                    .filter(line -> line.length() == 6)
	                    .collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
		}

		List<String> myset = null;
		for (String s:list) {
			myset = checkComposedString(s);
			if (myset == null) {
				continue;
			}
			System.out.printf("%s + %s = %s\n", myset.get(0), myset.get(1), s);
		}
    }
}
