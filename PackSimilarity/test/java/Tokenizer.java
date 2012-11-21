

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;


import com.google.jgments.RegexLexer;
import com.google.jgments.RegexLexerIterator;
import com.google.jgments.SyntaxSpan;
import com.google.jgments.syntax.CSyntax;
import com.google.jgments.syntax.CppSyntax;
import com.google.jgments.syntax.JavaScriptSyntax;
import com.google.jgments.syntax.JavaSyntax;
import com.google.jgments.syntax.LanguageDefinition;
import com.google.jgments.syntax.PythonSyntax;
import com.google.jgments.syntax.Token;
import com.google.jgments.syntax.XMLSyntax;

public class Tokenizer {

	

	
	private static final HashMap<String,LanguageDefinition> langmap ;
	
	static {
		langmap = new HashMap<String,LanguageDefinition>();
		langmap.put("java", CppSyntax.INSTANCE);
	}
	
	public static LanguageDefinition getLexerByFilename(String filepath){
		int dot=filepath.lastIndexOf('.');
		if(dot==-1) 
			throw new IllegalArgumentException("Filepath has no extension to identify its language: "+filepath);
		String ext = filepath.substring(dot+1).toLowerCase();
		LanguageDefinition result = langmap.get(ext);
		if(result!=null)
			return result;
		throw new IllegalArgumentException("File extension not known: "+ext);
	}
		
	public static String getType(Token token, String value){
		// This mapping is taken from original python codes
//        # Keyworks
//        Token.Keyword:          (lambda ttype, tval: (tval, tval)),
//        Token.Keyword.Constant: (lambda ttype, tval: (I, tval)),
//        Token.Keyword.Pseudo:   (lambda ttype, tval: (I, tval)),
//        Token.Keyword.Type:     (lambda ttype, tval: (I, tval)),
//        # Names
//        Token.Name:             (lambda ttype, tval: (I, tval)),
//        Token.Name.Attribute:   (lambda ttype, tval: ("attribute", tval)),
//        Token.Name.Entity:      (lambda ttype, tval: ("entity", tval)),
//        Token.Name.Tag:         (lambda ttype, tval: ("tag", tval)),
//        # Literals
//        Token.Literal:          (lambda ttype, tval: ('l', tval)),
//        # Operators
//        Token.Operator:         (lambda ttype, tval: (tval, tval)),
//        # Punctuation
//        Token.Punctuation:      (lambda ttype, tval: (tval, tval)),
//        # Ignored
//        Token.Text:             (lambda ttype, tval: None),
//        Token.Comment:          (lambda ttype, tval: None),
//        Token.Other:            (lambda ttype, tval: None),
//        Token.Generic:          (lambda ttype, tval: None),
//        Token.Error:            (lambda ttype, tval: None)
		
		String i = "i"; // identifier
		String l = "l"; // literal
		if(token == Token.KEYWORD_TYPE){
			return null;
		}else if(token == Token.KEYWORD_CONSTANT){
			return null;
		}else if(token == Token.KEYWORD_PSEUDO){
			return null;
		}else if(token.name().startsWith("KEYWORD")){
			return null;
		}else if(token == Token.NAME_ATTRIBUTE){
			return null;
		}else if(token == Token.NAME_ENTITY){
			return null;
		}else if(token == Token.NAME_TAG){
			return null;
		}else if(token.name().startsWith("NAME")){
			return null;
		}else if(token.name().startsWith("LITERAL")){
			return null;
		}else if(token == Token.OPERATOR){
			return null;
		}else if(token == Token.PUNCTUATION){
			return null;
		}else if(token == Token.TEXT){
			return null;
		}else if(token.name().startsWith("COMMENT")){
			return "c";
		}else if(token == Token.ERROR){
			return null;
		}
		throw new IllegalArgumentException("Unknown token type: "+token.name());
	}

	public ArrayList<TokenJava> tokenize(String filepath) throws IOException{
		LanguageDefinition langdef=getLexerByFilename(filepath);
		String filecontent = readall(filepath);
		RegexLexer lex=new RegexLexer(langdef, filecontent);
		
		ArrayList<TokenJava> result = new ArrayList<TokenJava>();
		RegexLexerIterator iter = lex.iterator();
		int lineno = 1, column=1;
		while(iter.hasNext()){
			SyntaxSpan ss = iter.next();

			int start = ss.getStartPos();
			int end = ss.getEndPos();
			String value = filecontent.substring(start, end);
			
			String type = getType(ss.getToken(),value.trim().toLowerCase());
			
			if(type!=null){
				result.add(new TokenJava(type, value.trim().toLowerCase(), start, lineno, column, filepath));
			}
			
			//calc lineno and column by count '\n' 
			char endl='\n';
			int lines = 0, lastp = 0, p;
			while((p=value.indexOf(endl,lastp))!=-1){
				lines++;
				lastp=p+1;
			}
			
			if(lines > 0 ){
				lineno += lines;
				column = value.length()-lastp;
			}else{
				column += value.length();
			}
		}
		return result;
	}

	private String readall(String filepath) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath)));
		String line=null;
		while((line=br.readLine())!=null){
			sb.append(line);
			sb.append('\n');
		}
		String filecontent=sb.toString();
		return filecontent;
	}
}
