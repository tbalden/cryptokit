package org.tbalden.cryptokit;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.tbalden.cryptokit.bip39.Bip39Dictionary;
import org.tbalden.cryptokit.bip39.Bip39PassPhrase;

/**
 * bip39 shuffler with secondary passphrase.
 * To enable storing of bip39 passphrase words in a newly generated phrase with less risk of being leaked and lost.
 * Purpose is to complement storage of originnal passphrase in a secure place and a secondary version of the passphrase password protected online.
 * The tool is to be used on an offline, secured machine only!
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	InputStream is = App.class.getResourceAsStream("/org/tbalden/cryptokit/bip39/bip39-english.txt");
    	InputStream phrase = App.class.getResourceAsStream("/org/tbalden/cryptokit/bip39/test/test-phrase.txt");
    	InputStream shuffledPhrase = App.class.getResourceAsStream("/org/tbalden/cryptokit/bip39/test/test-shuffledphrase.txt");
    	InputStream pwd = App.class.getResourceAsStream("/org/tbalden/cryptokit/bip39/test/test-password.txt");
    	String password = new BufferedReader(new InputStreamReader(pwd)).readLine();
    	int hash = Hasher.hash(password).intValue();
    	Bip39Dictionary odic = new Bip39Dictionary(is);
    	Bip39PassPhrase ophrase =  new Bip39PassPhrase(phrase);
    	Bip39PassPhrase sphrase =  new Bip39PassPhrase(shuffledPhrase);
    	odic.shuffle(hash);
    	ArrayList<String> result = new ArrayList<String>();
    	for (String i:ophrase.getWords()) {
    		System.out.println("ORDER: "+odic.getDicOrder(i));
    		System.out.println("SHUFFLED: "+i + " -> "+odic.getDicShuffledWord(i)+ " -> "+odic.getDicOriginalWord(odic.getDicShuffledWord(i)));
    		result.add(odic.getDicShuffledWord(i));
    	}
    	System.out.println("\n--------------------- shuffled");
    	for (String i:result)
    	{
    		System.out.print(" "+i);
    	}
    	System.out.println("\n--------------------- ordered back");
    	for (String i:sphrase.getWords())
    	{
    		System.out.print(" "+odic.getDicOriginalWord(i));
    	}
    }
}
 