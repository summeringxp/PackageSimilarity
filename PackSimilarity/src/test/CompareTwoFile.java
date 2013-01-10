package test;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import tokenizer.Tokenizer;

public class CompareTwoFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Tokenizer tokenizer = new Tokenizer();
		String google = "C:\\Users\\peixia\\Desktop\\t.c";
		//String git = "C:\\Users\\peixia\\Desktop\\shuron\\gzwrter-git.c";
		try {
			String content = "";
			Scanner scanner = new Scanner(new File(google));
			while(scanner.hasNextLine()){
				content+=scanner.nextLine();
				content+="\n";
			}
			scanner.close();
			System.out.println("google:"+tokenizer.getContentHash(google,content));
			//System.out.println("git:"+tokenizer.getFileHash(git));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
