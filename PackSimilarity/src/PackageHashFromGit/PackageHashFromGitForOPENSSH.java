package PackageHashFromGit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.treewalk.TreeWalk;

import tokenizer.Config;
import tokenizer.Tokenizer;

public class PackageHashFromGitForOPENSSH {

	/**
	 * @param args
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String name = "libcurl";
		String gitRoot = "C:\\Users\\peixia\\Desktop\\shuron\\libcurl\\src\\curl\\.git";
		PackageHashFromGitForOPENSSH phfg = new PackageHashFromGitForOPENSSH();
		try {
			phfg.gethashes(gitRoot, name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String gethashes(String gitRoot, String name)
			throws Exception {
		String out = "";
		Tokenizer tokenizer = new Tokenizer();
		final Git git = Git.open(new File(gitRoot));

		Iterable<RevCommit> log = git.log().call();
		Repository repository = git.getRepository();

		HashMap<String, String> reverse = new HashMap<String, String>();
		Map<String, Ref> ref = repository.getAllRefs();

		java.util.Iterator<Entry<String, Ref>> iter = ref.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, Ref> entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			String val = ((Ref) entry.getValue()).toString();
			// System.out.println(key+" "+val.substring(4,val.length()-1).split("=")[1]);
			val = val.substring(4, val.length() - 1).split("=")[1];
			
		
				reverse.put(val, key);
			
		}
		int count = 0;
		int count2 = 1;
		for (RevCommit commit : log) {
			
			if (!reverse.containsKey(commit.getName())) {
				//System.out.println("No"+commit.getName());
				continue;
			}
			String tag = reverse.get(commit.getName());
			tag = tag.substring(tag.lastIndexOf("/") + 1);

			out += tag + ":";
			System.out.print(tag + ":");
			RevTree revTree = commit.getTree();
			// System.out.println(revTree.name());
			TreeWalk treeWalk = new TreeWalk(repository);
			//TreeWalk treeWalk = TreeWalk.forPath(repository, "lib", revTree);
			treeWalk.addTree(revTree);
			//System.out.println("depth:"+treeWalk.getDepth());
			 treeWalk.setRecursive(true);
			// 文件名错误
			if (treeWalk == null)
				return null;

			String hashes = "";

			while (treeWalk.next()) {
				
				if (treeWalk.getPathString().startsWith("lib")&&isSourceCode(treeWalk.getPathString())) {
					String fname = treeWalk.getPathString();
//					 System.out.println((count2++) + " " +
//					 reverse.get(commit.getName())
//					 + " " + treeWalk.getPathString()+treeWalk.getObjectId(0));
					ObjectId blobId = treeWalk.getObjectId(0);
					//System.out.println(blobId.toString());
					ObjectLoader loader = repository.open(blobId);
					byte[] bytes = loader.getBytes();
					String content = new String(bytes);
					long hash = tokenizer.getContentHash(
							treeWalk.getPathString(), content);
					// System.out.println(hash);
					hashes += fname + " " + hash + "\t";

				}

			}

			out += hashes;
			
			out += "\n";
			System.out.println(hashes);
		}
		//writefile(name, out);
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

	private static void writefile(String filename, String content) {
		// TODO Auto-generated method stub
		String dir = "C:\\Users\\peixia\\Desktop\\shuron\\";
		String savefilename = filename;
		File file = new File(dir + savefilename + ".txt");
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
