import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import tokenizer.Config;
import tokenizer.Tokenizer;




public class FileHashCalculator {

	/**
	 * @param args
	 */
	Tokenizer tokenizer = new Tokenizer();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileHashCalculator fc = new FileHashCalculator();
		String path = "C:\\Users\\peixia\\Desktop\\libpng\\libpng_old\\libpng - コピー";
		String path2 = "C:\\Users\\peixia\\Desktop\\libpng\\libpng_old\\libpng";
		try {
			
			Integer[] origin =fc.getSortedPackageHashes(path);
			Integer[] target =fc.getSortedPackageHashes(path2);
			int[] result = fc.compareSimilarity(origin,target);
			System.out.println("origin:"+result[0]+"\ttarget:"+result[1]+"\tmatches:"+result[2]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int[] compareSimilarity(Integer[] q1,Integer[] q2){
		int len1 = q1.length;
		int len2 = q2.length;
		HashSet<Integer> set1 = new HashSet<Integer>();
		HashSet<Integer> set2 = new HashSet<Integer>();
		HashSet<Integer> set3 = new HashSet<Integer>();
		for(int i=0;i<len1-1;i++){
			set1.add(q1[i]);
			set3.add(q1[i]);
		}
		for(int j=0;j<len2-1;j++){
			set2.add(q2[j]);
			set3.add(q2[j]);
		}
		int m = set1.size()+set2.size()-set3.size();
		return new int[]{set1.size(),set2.size(),m};
	}
	public Integer[] getSortedPackageHashes(String path) throws IOException{
		ArrayList<Integer> arraylist = getPackageHashes(path);
		Integer[] hashes =arraylist.toArray(new Integer[arraylist.size()]);
		Arrays.sort(hashes);
		return hashes;
	}
	public ArrayList<Integer> getPackageHashes(String path) throws IOException{
		ArrayList<Integer> hashes = new ArrayList<Integer>();
		File dir = new File(path);
		if(dir.isFile()){
			if(isSourceCode(dir.getName())){
				
			hashes.add(tokenizer.getFileHash(dir.getCanonicalPath()));
			}
			return hashes;
		}
		for(File f:dir.listFiles()){
			hashes.addAll(getPackageHashes(f.getCanonicalPath()));
		}
		return hashes;
	}
	
	
	public boolean isSourceCode(String path){
		for(String ext : Config.extension2language.keySet()){
			if(path.toLowerCase().endsWith(ext)){
				return true;
			}
		}
		return false;
	}
	

}
