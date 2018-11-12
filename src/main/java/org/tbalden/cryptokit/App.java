package org.tbalden.cryptokit;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.*;
import java.math.*;
import java.util.ArrayList;

import org.tbalden.cryptokit.bip39.Bip39Dictionary;
import org.tbalden.cryptokit.bip39.Bip39PassPhrase;
import org.tbalden.cryptokit.bip39.novacrypto.MnemonicValidator;

/**
 * bip39 shuffler with secondary passphrase. To enable storing of bip39
 * passphrase words in a newly generated phrase with less risk of being leaked
 * and lost. Purpose is to complement storage of originnal passphrase in a
 * secure place and a secondary version of the passphrase password protected
 * online. The tool is to be used on an offline, secured machine only! It will
 * shuffle the words into a bip39 passphrase with valid CRC.
 */
public class App {
	public static void main(String[] args) throws Exception {
		if (args.length != 0) {
			for (String arg : args) {
				// System.out.println("arg[]"+arg);
			}
			if (args[0].equals("-v")) {
				InputStream phrase = new FileInputStream(new File(args[1]));
				String mnemonicResult = new BufferedReader(new InputStreamReader(phrase)).readLine();
				MnemonicValidator.ofWordList(org.tbalden.cryptokit.bip39.novacrypto.wordlists.English.INSTANCE)
						.validate(mnemonicResult);
			} else if (args[0].equals("-e")) {
				// encode shuffle
				InputStream is = App.class.getResourceAsStream("/org/tbalden/cryptokit/bip39/bip39-english.txt");
				InputStream phrase = new FileInputStream(new File(args[1]));
				InputStream pwd = new FileInputStream(new File(args[2]));

				String password = new BufferedReader(new InputStreamReader(pwd)).readLine();
				BigInteger hash = Hasher.hash(password);
				Bip39Dictionary odic = new Bip39Dictionary(is);
				Bip39PassPhrase ophrase = new Bip39PassPhrase(phrase);
				odic.shuffle(hash);
				ArrayList<String> result = new ArrayList<String>();
				for (String i : ophrase.getWords()) {
					// System.out.println("ORDER: "+odic.getDicOrder(i));
					// System.out.println("SHUFFLED: "+i + " ->
					// "+odic.getDicShuffledWord(i)+ " ->
					// //"+odic.getDicOriginalWord(odic.getDicShuffledWord(i)));
					result.add(odic.getDicShuffledWord(i));
				}
				System.out.println("\n--------------------- shuffled");
				String mnemonicResult = "";
				String lastWord = "";
				boolean first = true;
				int count = 0;
				for (String i : result) {
					if (count++ == 23) {
						lastWord = i;
					} else {
						mnemonicResult = mnemonicResult + (first ? "" : " ") + i;
					}
					first = false;
				}
				// System.out.println("23 words shuffled: "+mnemonicResult);
				// System.out.println("last word shuffled: "+lastWord);

				for (int i = 0; i < odic.getDicSize(); i++) {
					try {
						MnemonicValidator.ofWordList(org.tbalden.cryptokit.bip39.novacrypto.wordlists.English.INSTANCE)
								.validate(mnemonicResult + " " + odic.getDicWord(i));
						System.out.println("BIP39 FAKE VALID RESULTS: " + mnemonicResult + " " + odic.getDicWord(i));
					} catch (Throwable t) {
					}
				}
				System.out.println("25th word: " + lastWord);

				/*
				 * InputStream shuffledPhrase = new
				 * java.io.ByteArrayInputStream(mnemonicResult.getBytes());
				 * Bip39PassPhrase sphrase = new
				 * Bip39PassPhrase(shuffledPhrase);
				 * 
				 * System.out.println("\n--------------------- ordered back");
				 * for (String i:sphrase.getWords()) {
				 * System.out.print(" "+odic.getDicOriginalWord(i)); }
				 * System.out.println(" "+odic.getDicOriginalWord(lastWord));
				 */
			} else if (args[0].equals("-d")) {
				// decode reorder
				InputStream is = App.class.getResourceAsStream("/org/tbalden/cryptokit/bip39/bip39-english.txt");
				InputStream phrase = new FileInputStream(new File(args[1]));
				InputStream pwd = new FileInputStream(new File(args[2]));

				String password = new BufferedReader(new InputStreamReader(pwd)).readLine();
				BigInteger hash = Hasher.hash(password);
				Bip39Dictionary odic = new Bip39Dictionary(is);
				Bip39PassPhrase ophrase = new Bip39PassPhrase(phrase);
				odic.shuffle(hash);

				System.out.println("\n--------------------- ordered back");
				int count = 0;
				for (String i : ophrase.getWords()) {
					if (count == 23) { // 24th non part of original...
						// lastWord = i;
					} else if (count == 24) { // 25th - part of the original
												// phrase
						System.out.print(" " + odic.getDicOriginalWord(i));
					} else {
						System.out.print(" " + odic.getDicOriginalWord(i));
					}
					count++;
				}

			} else if (args[0].equals("-t")) {

				InputStream is = App.class.getResourceAsStream("/org/tbalden/cryptokit/bip39/bip39-english.txt");
				InputStream phrase = App.class.getResourceAsStream("/org/tbalden/cryptokit/bip39/test/test-phrase.txt");

				InputStream pwd = App.class.getResourceAsStream("/org/tbalden/cryptokit/bip39/test/test-password.txt");
				String password = new BufferedReader(new InputStreamReader(pwd)).readLine();
				BigInteger hash = Hasher.hash(password);
				Bip39Dictionary odic = new Bip39Dictionary(is);
				Bip39PassPhrase ophrase = new Bip39PassPhrase(phrase);
				odic.shuffle(hash);
				ArrayList<String> result = new ArrayList<String>();
				for (String i : ophrase.getWords()) {
					// System.out.println("ORDER: "+odic.getDicOrder(i));
					// System.out.println("SHUFFLED: "+i + " ->
					// "+odic.getDicShuffledWord(i)+ " ->
					// //"+odic.getDicOriginalWord(odic.getDicShuffledWord(i)));
					result.add(odic.getDicShuffledWord(i));
				}
				System.out.println("\n--------------------- shuffled");
				String mnemonicResult = "";
				String lastWord = "";
				boolean first = true;
				int count = 0;
				for (String i : result) {
					if (count++ == 23) {
						lastWord = i;
					} else {
						mnemonicResult = mnemonicResult + (first ? "" : " ") + i;
					}
					first = false;
				}
				System.out.println("23 words shuffled: " + mnemonicResult);
				System.out.println("last word shuffled: " + lastWord);

				for (int i = 0; i < odic.getDicSize(); i++) {
					try {
						MnemonicValidator.ofWordList(org.tbalden.cryptokit.bip39.novacrypto.wordlists.English.INSTANCE)
								.validate(mnemonicResult + " " + odic.getDicWord(i));
						System.out.println("BIP39 FAKE VALID RESULTS: " + mnemonicResult + " " + odic.getDicWord(i));
					} catch (Throwable t) {
					}
				}

				InputStream shuffledPhrase = new java.io.ByteArrayInputStream(mnemonicResult.getBytes());
				Bip39PassPhrase sphrase = new Bip39PassPhrase(shuffledPhrase);

				System.out.println("\n--------------------- ordered back");
				for (String i : sphrase.getWords()) {
					System.out.print(" " + odic.getDicOriginalWord(i));
				}
				System.out.println(" " + odic.getDicOriginalWord(lastWord));
			}
		} else {
			System.out.println("24 word passphrase to 23 shuffled words + CRC word (BIP39 valid) + 25th shuffled word\ntool to shuffle and order back.\nusage: \n-v mnemonic.txt -- validate \n-e mnemonic.txt password.txt > encode_result.txt -- shuffle with password to 8 possible BIP39 mnemonic\n-d mnemonic-shuffled.txt password.txt > decode_result.txt -- order back 24 + 1 words BIP39 shuffled mnemonic with password to original BIP39 24 word mnemonic");
		}
	}
}
