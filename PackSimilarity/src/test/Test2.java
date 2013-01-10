package test;

import java.io.File;
import java.io.IOException;

import tokenizer.Config;
import tokenizer.Tokenizer;

public class Test2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File dir = new File(
				"C:\\Users\\peixia\\Desktop\\shuron\\libpng\\manually");
		Tokenizer t = new Tokenizer();
//		for (File project : dir.listFiles()) {
//			System.out.print(project.getName() + ":");
//			for (File f : project.listFiles()) {
//				try {
//					if (Config.isSourceCode(f.getName())) {
//						System.out.print(f.getName() + " "
//								+ t.getFileHash(f.getCanonicalPath()) + "\t");
//					}
//
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//			}
//			System.out.println();
//
//		}
		String s ="public static void main(){int a=0;a=a+1;}"; 
		t.getContentHash("a.java", s);
	}

}
