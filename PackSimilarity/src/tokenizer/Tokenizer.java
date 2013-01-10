package tokenizer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.CharStreams;
import com.google.jgments.RegexLexer;
import com.google.jgments.RegexLexerIterator;
import com.google.jgments.SyntaxSpan;
import com.google.jgments.syntax.LanguageDefinition;
import com.google.jgments.syntax.Token;

/**
 * Tokenize a source code fragment. Using Jgments as lexer.
 * 
 * @author Jiachen YANG &lt;jc-yang@ist.sjtu.edu.cn&gt;
 * @see <a href="http://code.google.com/p/jgments/">Jgments</a>
 * 
 */

public class Tokenizer {

	private static final Logger logger = LoggerFactory
			.getLogger(Tokenizer.class);

	/**
	 * Return a {@link LanguageDefinition} by the extension name of a file path.
	 * 
	 * @param filepath
	 *            Path of file name
	 * @return Corresponding {@link LanguageDefinition}
	 */
	public static LanguageDefinition getLexerByFilename(String filepath) {
		int dot = filepath.lastIndexOf('.');
		if (dot == -1)
			throw new IllegalArgumentException(
					"Filepath has no extension to identify its language: "
							+ filepath);
		String ext = filepath.substring(dot + 1).toLowerCase();
		LanguageDefinition result = Config.langmap.get(ext);
		if (result != null)
			return result;
		throw new IllegalArgumentException("File extension not known: " + ext);
	}

	/**
	 * Normalized string for identifiers(variable names, type names, etc.)
	 */
	private static final String i = "$";

	/**
	 * Normalized string for literals (string literals, numbers, keywords like
	 * "True", etc.)
	 */
	private static final String l = "$";

	private static String[] typeNames = { "CString", "UINT32", "u8", "u32" };

	/**
	 * For a token type from Jygment, returns its type string. If the token
	 * should be ignored, returns null.
	 * <p>
	 * For example,
	 * <table>
	 * <thead>
	 * <tr>
	 * <th>Token type</th>
	 * <th>Token type example</th>
	 * <th>Type string</th>
	 * </tr>
	 * </thead> <tbody>
	 * <tr>
	 * <th>KEYWORD_TYPE</th>
	 * <td>int</td>
	 * <td>"$"</td>
	 * </tr>
	 * <tr>
	 * <th>KEYWORD_CONSTANT</th>
	 * <td>True</td>
	 * <td>"$"</td>
	 * </tr>
	 * <tr>
	 * <th>KEYWORD_PSEUDO</th>
	 * <td>ClassName.<b>class</b></td>
	 * <td>"$"</td>
	 * </tr>
	 * <tr>
	 * <th>KEYWORD_*</th>
	 * <td>if</td>
	 * <td>"if"</td>
	 * </tr>
	 * <tr>
	 * <th>NAME_ATTRIBUTE</th>
	 * <td>@Override</td>
	 * <td>null</td>
	 * </tr>
	 * <tr>
	 * <th>NAME_*</th>
	 * <td>variable</td>
	 * <td>"$"</td>
	 * </tr>
	 * <tr>
	 * <th>LITERAL_*</th>
	 * <td>1234</td>
	 * <td>"$"</td>
	 * </tr>
	 * <tr>
	 * <th>OPERATOR</th>
	 * <td>+</td>
	 * <td>"+"</td>
	 * </tr>
	 * <tr>
	 * <th>PUNCTUATION</th>
	 * <td>;</td>
	 * <td>";"</td>
	 * </tr>
	 * <tr>
	 * <th>COMMENT</th>
	 * <td>/* A comment *\/</td>
	 * <td>null</td>
	 * </tr>
	 * <tr>
	 * <th>OTHER</th>
	 * <td></td>
	 * <td>null</td>
	 * </tr>
	 * </tbody>
	 * </table>
	 * 
	 * @param token
	 *            {@link Token} type from Jygment
	 * @param value
	 *            Token value string from Jygment
	 * @return A string represents type of token, or null if the token should be
	 *         ignored
	 * @see <a href="http://pygments.org/docs/tokens/">Builtin Tokens of
	 *      Pygments</a> for all token types that available.
	 */
	public static String getType(Token token, String value) {
		String tokenname = token.name();
		if (token == Token.KEYWORD_TYPE) {
			return value;
		} else if (token == Token.KEYWORD_CONSTANT) {
			return l;
		} else if (token == Token.KEYWORD_PSEUDO) {
			return l;
		} else if (tokenname.startsWith("KEYWORD")) {
			return value;
		} else if (token == Token.NAME_ATTRIBUTE) {
			return null;
		} else if (token == Token.NAME_ENTITY) {
			return null;
		} else if (token == Token.NAME_TAG) {
			return null;
		} else if (token == Token.NAME_DECORATOR) {
			return null;
		} else if (tokenname.startsWith("NAME")) {
			for (String typeName : typeNames) {
				if (value.equals(typeName)) {
					return value;
				}
			}
			return i;
		} else if (tokenname.startsWith("LITERAL")) {
			return l;
		} else if (tokenname.startsWith("OPERATOR")) {
			return value;
		} else if (tokenname.startsWith("PUNCTUATION")) {
			return value;
		} else if (tokenname.startsWith("TEXT")) {
			return null;
		} else if (tokenname.startsWith("COMMENT")) {
			return null;
		} else if (tokenname.startsWith("ERROR")) {
			return null;
		}
		throw new IllegalArgumentException("Unknown token type: "
				+ token.name());
	}

	public String readAll(InputStream stream) throws IOException {
		StringBuffer sb = new StringBuffer();
		int c;
		while ((c = stream.read()) != -1) {
			sb.append((char) c);
		}
		return sb.toString().replace("\r\n", "\n").trim();
	}

	public int getFileHash(String filepath) throws IOException {
		InputStream stream = new FileInputStream(filepath);
		StringBuffer encodedContent = new StringBuffer();
		LanguageDefinition langdef = getLexerByFilename(filepath);
		String filecontent = readAll(stream);

		// Run the lexer and get its iterator
		RegexLexerIterator iter = new RegexLexer(langdef, filecontent)
				.iterator();
		while (iter.hasNext()) {
			SyntaxSpan ss = iter.next();
			int start = ss.getStartPos();
			int end = ss.getEndPos();
			String value = filecontent.substring(start, end);

			String type = getType(ss.getToken(), value.trim().toLowerCase());
			if (type != null)
				encodedContent.append(type);
		}
		stream.close();

		return encodedContent.toString().hashCode();
	}

	public int getContentHash(String filepath,String filecontent) {
		LanguageDefinition langdef = getLexerByFilename(filepath);
		StringBuffer encodedContent = new StringBuffer();
		RegexLexerIterator iter = new RegexLexer(langdef, filecontent)
				.iterator();
		while (iter.hasNext()) {
			SyntaxSpan ss = iter.next();
			int start = ss.getStartPos();
			int end = ss.getEndPos();
			String value = filecontent.substring(start, end);

			String type = getType(ss.getToken(), value.trim().toLowerCase());
			
			if (type != null)
				encodedContent.append(type);
		}
		//System.out.println(encodedContent.toString());
	
		return encodedContent.toString().hashCode();
	}

	/**
	 * Tokenize a code fragment.
	 * 
	 * @param filepath
	 *            Used to call {@link getLexerByFilename}
	 * @param stream
	 *            {@link InputStream} contains the content of code fragment
	 * @return A {@link List} of {@link TokenJava}
	 * @throws IOException
	 *             If any error while reading the {@link stream}
	 */
	public List<TokenJava> tokenize(String filepath, InputStream stream)
			throws IOException {
		// Get language defination by seeing extension of filename
		LanguageDefinition langdef = getLexerByFilename(filepath);
		// Read all contents of a file
		List<TokenJava> result = new ArrayList<TokenJava>();
		// String filecontent = CharStreams.toString(new
		// InputStreamReader(stream));
		String filecontent = readAll(stream);

		// Run the lexer and get its iterator
		RegexLexerIterator iter = new RegexLexer(langdef, filecontent)
				.iterator();
		TokenJava lastToken = null;

		int lineno = 1, column = 1;
		while (iter.hasNext()) {
			SyntaxSpan ss = iter.next();

			int start = ss.getStartPos();
			int end = ss.getEndPos();
			String value = filecontent.substring(start, end);

			String type = getType(ss.getToken(), value.trim().toLowerCase());

			// About "lastToken": compress a sequence of literals into one token
			if (type != null) {
				if (lastToken == null) {
					lastToken = new TokenJava(type, value, start, lineno,
							column, filepath);
				} else if (lastToken.getType().equals(l) && type.equals(l)) {
					lastToken = new TokenJava(lastToken.getType(),
							lastToken.getValue() + value,
							lastToken.getLexpos(), lastToken.getLineno(),
							lastToken.getColumn(), filepath);
				} else {
					result.add(lastToken);
					lastToken = new TokenJava(type, value, start, lineno,
							column, filepath);
				}
			}

			// logger.info("Token {} ",lastToken);

			// calc lineno and column by count '\n'
			char endl = '\n';
			int lines = 0, lastp = 0, p;
			while ((p = value.indexOf(endl, lastp)) != -1) {
				lines++;
				lastp = p + 1;
			}

			if (lines > 0) {
				lineno += lines;
				column = value.length() - lastp;
			} else {
				column += value.length();
			}
		}
		if (lastToken != null) {
			result.add(lastToken);
		}
		return result;
	}
}
