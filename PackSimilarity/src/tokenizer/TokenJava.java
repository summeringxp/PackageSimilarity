package tokenizer;


/**
 * Represents a token object.
 *
 * @author Jiachen YANG &lt;jc-yang@ist.sjtu.edu.cn&gt;
 */
public class TokenJava {
	

	/**
	 * Gets the token type.
	 *
	 * @return the token type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the token type.
	 *
	 * @param type the new token type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the token string.
	 *
	 * @return the token string
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the token string.
	 *
	 * @param value the new token string
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Gets the position of token in source code, count by char.
	 *
	 * @return the position of token in source code, count by char
	 */
	public int getLexpos() {
		return lexpos;
	}

	/**
	 * Sets the position of token in source code, count by char.
	 *
	 * @param lexpos the new position of token in source code, count by char
	 */
	public void setLexpos(int lexpos) {
		this.lexpos = lexpos;
	}

	/**
	 * Gets the line number of token in source code.
	 *
	 * @return the line number of token in source code
	 */
	public int getLineno() {
		return lineno;
	}

	/**
	 * Sets the line number of token in source code.
	 *
	 * @param lineno the new line number of token in source code
	 */
	public void setLineno(int lineno) {
		this.lineno = lineno;
	}

	/**
	 * Gets the line column of token in source code.
	 *
	 * @return the line column of token in source code
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Sets the line column of token in source code.
	 *
	 * @param column the new line column of token in source code
	 */
	public void setColumn(int column) {
		this.column = column;
	}

	/**
	 * Gets the filename of source code.
	 *
	 * @return the filename of source code
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * Sets the filename of source code.
	 *
	 * @param filename the new filename of source code
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/** Token type. */
	private String type;
	
	/** Token string. */
	private String value;
	
	/** Position of token in source code, count by char. */
	private int lexpos;
	
	/** Line number of token in source code. */
	private int lineno;
	
	/** Line column of token in source code. */
	private int column;
	
	/** Filename of source code. */
	private String filename;
	
	/** Logical line number*/
	private int logicalLine;
	
	public int getLogicalLine() {
		return logicalLine;
	}

	public void setLogicalLine(int logicalLine) {
		this.logicalLine = logicalLine;
	}

	/**
	 * Instantiates a new token java.
	 *
	 * @param type the type
	 * @param value the value
	 * @param lexpos the lexpos
	 * @param lineno the lineno
	 * @param column the column
	 * @param filename the filename
	 */
	public TokenJava(String type, String value, int lexpos, int lineno, int column, String filename){
		this.type = type;
		this.value = value;
		this.lexpos = lexpos;
		this.lineno = lineno;
		this.column = column;
		this.filename = filename;
		this.logicalLine = -1; // uninitialized 
	}

	/**
	 * One line description of token.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return String.format("%s\t%s\t:(%d)%s", type, value, lineno, column, lexpos, filename);
	}
}
