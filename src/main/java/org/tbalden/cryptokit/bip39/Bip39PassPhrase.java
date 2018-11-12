package org.tbalden.cryptokit.bip39;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class Bip39PassPhrase {

	public Bip39PassPhrase(File passFile) throws Exception{
		this(new FileReader(passFile));
	}
	public Bip39PassPhrase(InputStream passFile) throws Exception{
		this(new InputStreamReader(passFile));
	}
	
	HashMap<String,Integer> dicWords = new HashMap<String,Integer>();
	ArrayList<String> words = new ArrayList<String>();
	
	public List<String> getWords() {
		return words;
	}
	
	public Bip39PassPhrase(Reader r) throws Exception{
		BufferedReader br = new BufferedReader(r);
		String line = null;
		int i=0;
		do {
			line = br.readLine();
			if (line!=null) {
				StringTokenizer st = new StringTokenizer(line, " \t");
				while (st.hasMoreTokens()) {
					String t = st.nextToken();
					//System.out.println(t+" "+i);
					dicWords.put(t,i++);
					words.add(t);
				}
			}
		} 
		while (line!=null);		
	}
	
}
