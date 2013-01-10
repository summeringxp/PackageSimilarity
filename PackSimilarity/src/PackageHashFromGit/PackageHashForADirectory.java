package PackageHashFromGit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;

import tokenizer.Config;
import tokenizer.Tokenizer;

public class PackageHashForADirectory {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path = "/external/libpng/";
		String gitRoot = "git://github.com/cocos2d/cocos2d-iphone.git";
		PackageHashForADirectory phfg = new PackageHashForADirectory();
		try {
			phfg.gethashes(gitRoot,path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String gethashes(String gitRoot,String path) throws Exception {
		String out="";
		Tokenizer tokenizer = new Tokenizer();
		final Git git = Git.open(new File(gitRoot));
		

		Iterable<RevCommit> log = git.log().call();
		Repository repository = git.getRepository();
		ObjectId lastCommitId = repository.resolve(Constants.HEAD);
		
		RevWalk revWalk = new RevWalk(repository);
		RevCommit commit = revWalk.parseCommit(lastCommitId);
		RevTree tree = commit.getTree();
		
			// System.out.println(revTree.name());
			TreeWalk treeWalk = TreeWalk.forPath(repository, path, tree);
			treeWalk.addTree(tree);
			
			//treeWalk.setRecursive(true);
			// 文件名错误
			if (treeWalk == null)
				return null;
			
			String hashes = "";
			
			while (treeWalk.next()) {
				String p = treeWalk.getPathString();
				if (isSourceCode(path)) {
					String fname = path;
					System.out.println(fname);
//					ObjectId blobId = treeWalk.getObjectId(0);
//					ObjectLoader loader = repository.open(blobId);
//					byte[] bytes = loader.getBytes();
//					String content = new String(bytes);
//					long hash = tokenizer.getContentHash(treeWalk.getPathString(), content);
//					hashes += fname+" "+hash+"\t";
					
				}
				
			}
			
		
//			out+=hashes;
//			out+="\n";
//			System.out.println(hashes);
	
		//writefile(name,out);
		return null;
	}

	public static boolean isSourceCode(String path) {
		for (String ext : Config.extension2language.keySet()) {
			if (path.toLowerCase().endsWith(ext)) {
				return true;
			}
		}
		return false;
	}
	private static void writefile(String filename , String content ) {
		// TODO Auto-generated method stub
		String dir = "C:\\Users\\peixia\\Desktop\\shuron\\";
		String savefilename = filename;
		File file = new File(dir + savefilename+".txt");
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(content);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
