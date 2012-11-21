

import java.io.IOException;
import java.util.ArrayList;


public class TryJygments {
	public static String [] testlist = {
		"C:\\Users\\jc-yang\\Dropbox\\eclipsews\\test\\src\\Tokenizer.java",
		"C:\\Users\\jc-yang\\Dropbox\\eclipsews\\test\\src\\FilterComment.java"};
	
	public static void testTokenizer(String filepath) throws IOException{
		ArrayList<TokenJava> v=tokenizer.tokenize(filepath);

		for (TokenJava token : v){
			System.out.println(token);
		}
	}
	private static Tokenizer tokenizer=new Tokenizer();
	
	public static void main(String[] args) throws IOException {
		long start=System.currentTimeMillis();
		for(String path: testlist){
			testTokenizer(path);
		}
		long end=System.currentTimeMillis();
		System.out.println(end-start);

	}
}
