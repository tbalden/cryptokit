package org.tbalden.cryptokit.bip39;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;

import org.tbalden.cryptokit.Hasher;

public class Bip39Dictionary {

	public Bip39Dictionary(File dicFile) throws Exception{
		this(new FileReader(dicFile));
	}
	public Bip39Dictionary(InputStream dicFile) throws Exception{
		this(new InputStreamReader(dicFile));
	}
	
	HashMap<String,Integer> dicWords = new HashMap<String,Integer>();
	HashMap<Integer,String> dicOrder = new HashMap<Integer,String>();
	HashMap<Integer,Integer> dicShuffledOrder = new HashMap<Integer,Integer>();
	HashMap<Integer,Integer> dicShuffledOrderBackMap = new HashMap<Integer,Integer>();
	
	public String getDicWord(int order) {
		return dicOrder.get(order);
	}
	public Integer getDicOrder(String word) {
		return dicWords.get(word);
	}
	public Integer getDicSize() {
		return dicWords.size();
	}
	public String getDicShuffledWord(String originalWord) {
		return getDicWord(dicShuffledOrder.get(getDicOrder(originalWord)));
	}
	public String getDicOriginalWord(String shuffledWord) {
		return getDicWord(dicShuffledOrderBackMap.get(getDicOrder(shuffledWord)));
	}
	
	public void shuffle(int hash) {
		ArrayList<Integer> shuffled = new ArrayList<Integer>();
		int max = getDicSize();
		for (int i=0; i<dicWords.size();i++) {
			int h = Math.abs(Hasher.hash(""+hash+getDicWord(i)).intValue() % max);
			int inc = 1;
			while (shuffled.contains(h)) {
				h = Math.abs(Hasher.hash(""+hash+getDicWord(i)+inc++).intValue() % max);
			}
			shuffled.add(h);
			dicShuffledOrder.put(i, h);
			dicShuffledOrderBackMap.put(h, i);
			System.out.println("shuffling "+getDicWord(i)+" -> "+h);
		}
	}
	
	public Bip39Dictionary(Reader r) throws Exception{
		BufferedReader br = new BufferedReader(r);
		String line = null;
		int i=0;
		do {
			line = br.readLine();
			if (line!=null) {
				System.out.println(line+" "+i);
				dicOrder.put(i,line);
				dicWords.put(line,i++);
			}
		} 
		while (line!=null);
	}
	
}
