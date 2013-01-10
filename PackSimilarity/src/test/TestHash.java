package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import tokenizer.Config;

public class TestHash {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestHash th = new TestHash();
		String result = "";
//		ArrayList<HashInfo> versions = new ArrayList<HashInfo>();
		try {
			Scanner scanner = new Scanner(new File("C:\\Users\\peixia\\Desktop\\shuron\\libpng\\otherprojectshash.txt"));
			int coun=0;
			
			while(scanner.hasNextLine()){
				String l = scanner.nextLine();
				if(l.equals("---")){
					break;
				}
				HashInfo info = th.parse(l);
				System.out.println("\t\t----------------------"+info.getProjectname()+"------------------------");
				System.out.print("libpng   \\\t|");
				for(int i=0;i<info.getFilename().length;i++){
					System.out.print(info.getFilename()[i]+"  \t|");
				}
				System.out.println();
				Scanner scanner2 = new Scanner(new File("C:\\Users\\peixia\\Desktop\\shuron\\libpng.txt"));
				int max=0;
				String maxs = "";
				int len = info.getFilename().length;
				int zlibnum=0;
				int foldernum=0;
				int zlibnumtt=0;
				while (scanner2.hasNextLine()){
					HashInfo info2 = th.parse(scanner2.nextLine());
					System.out.print(info2.getProjectname()+"    \t|\t");
					int count=0;
					int foldernumt=0;
					for(int i = 0;i<len;i++){
						if(!Config.isSourceCode(info.getFilename()[i])){
							continue;
						}
						foldernumt++;
						boolean found =false;
						int zlibnumt=0;
						for(int j=0;j<info2.getFilename().length;j++){
							if(info2.getFilename()[j].contains("/")){
								continue;
							}
							zlibnumt++;
							if(info2.getHash()[j]==info.getHash()[i]){
								found=true;
							}
						}
						if(found){
							System.out.print("O"+"\t|\t");
							zlibnumtt = zlibnumt;
							count++;
						}else{
							System.out.print("X"+"\t|\t");
						}
					}
					if(count>max){
						max = count;
						maxs = info2.getProjectname();
						zlibnum = zlibnumtt;
						foldernum = foldernumt;
					}
					zlibnumtt=0;
					foldernumt=0;
					System.out.print(count+"/"+foldernum);
					System.out.println();
				}
				result+=(info.getProjectname()+"\t"+maxs+" "+max+"/"+foldernum+"\n");
				System.out.println();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(result);
		
	}

	private HashInfo parse(String nextLine) {
		HashInfo info = new HashInfo();
		String[] tem = nextLine.split(":");
		info.setProjectname(tem[0]);
		String[] tem2 = tem[1].split("\t");
		int length = tem2.length;
		String[] name = new String[length];
		int[] hashes = new int[length];
		for(int i=0;i<length;i++){
			name[i]=tem2[i].split(" ")[0];
			hashes[i]=Integer.parseInt(tem2[i].split(" ")[1]);
		}
		info.setFilename(name);
		info.setHash(hashes);
		return info;
	}

}
