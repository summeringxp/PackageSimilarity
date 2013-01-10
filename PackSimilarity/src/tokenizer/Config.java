package tokenizer;


import java.util.HashMap;
import java.util.Map;

import com.google.jgments.syntax.CSyntax;
import com.google.jgments.syntax.CppSyntax;
import com.google.jgments.syntax.JavaSyntax;
import com.google.jgments.syntax.LanguageDefinition;

/**
 * Static Configurations of CCSS
 * @author Jiachen YANG &lt;jc-yang@ist.sjtu.edu.cn&gt;
 *
 */
public class Config {
	/**
	 * base location of test files
	 */
	public static final String basePath="test/";
	public static final String uploadsPath="/tmp/uploads/";
	
	/**
	 * A list of files as test cases
	 */
	public static final String[] list={
			basePath + "ed-1.5/io.c",
			basePath + "ed-1.5/main_loop.c",
			basePath + "ed-1.5/buffer.c",
			basePath + "ed-1.5/carg_parser.c",
			basePath + "ed-1.5/global.c",
			basePath + "ed-1.5/main.c",
			basePath + "ed-1.5/regex.c",
			basePath + "ed-1.5/signal.c",
			basePath + "java/FilterComment.java",
			basePath + "java/Tokenizer.java",
			basePath + "java/TokenJava.java",
			basePath + "java/TryJygments.java",
	};

	/**
	 * A mapping between source code file extension name(with begining dot) to language name.
	 * Also used as list of supported source code filter.
	 */
	public static final Map<String, String> extension2language = new HashMap<String,String>();
	
	static{
		extension2language.put(".c",    "C");
		extension2language.put(".cc",   "C++");
		extension2language.put(".c++",   "C++");
		extension2language.put(".h",    "C");
		extension2language.put(".hpp",  "C++");
		extension2language.put(".cpp",  "C++");
		extension2language.put(".java", "java");
		//extension2language.put(".py",   "Python");
		//extension2language.put(".js",   "JavaScript");
	}

	/**
	 * A list of supported archive file extensions. 
	 * See 7zip-jbanding for all supported archive files.
	 * @see <a href="http://sevenzipjbind.sourceforge.net/">7-Zip-JBinding</a>
	 */
	public static final String [] archiveExtension = {
		".zip", 
		".tar.gz", 
		".tgz", 
		".tar.bz2", 
		".tbz2", 
		".jar", 
		".tar", 
	};

	/**
	 * A mapping between file extension(without begining dot) to language definitions.
	 * See jgments for all supported languages and language definitions.
	 * @see <a href="http://code.google.com/p/jgments/">Jgments</a>
	 */
	public static final Map<String,LanguageDefinition> langmap = new HashMap<String,LanguageDefinition>();
	
	static {
		langmap.put("c",     CSyntax.INSTANCE);
		//langmap.put("py",    PythonSyntax.INSTANCE);
		langmap.put("java",  JavaSyntax.INSTANCE);
		langmap.put("c++",   CppSyntax.INSTANCE);
		langmap.put("cc",    CppSyntax.INSTANCE);
		langmap.put("cpp",   CppSyntax.INSTANCE);
		langmap.put("hpp",   CppSyntax.INSTANCE);
		langmap.put("h",     CppSyntax.INSTANCE);
		//langmap.put("js",    JavaScriptSyntax.INSTANCE);
	}
	
	/**
	 * A list of all tarball extensions. Dots(.) are replaced by slash(_)
	 */
	public static final String [] untarExtension = {
		"_tar_gz",
		"_tar_bz2",
		"_tbz2",
		"_tgz"
	};
	public static final String DEFAULT_REVIEW = "analyze";
	public static boolean isSourceCode(String path) {
		for (String ext : Config.extension2language.keySet()) {
			if (path.toLowerCase().endsWith(ext)) {
				return true;
			}
		}
		return false;
	}
}
