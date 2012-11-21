

public class TokenJava {
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getLexpos() {
		return lexpos;
	}

	public void setLexpos(int lexpos) {
		this.lexpos = lexpos;
	}

	public int getLineno() {
		return lineno;
	}

	public void setLineno(int lineno) {
		this.lineno = lineno;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	private String type;
	private String value;
	private int lexpos;
	private int lineno;
	private int column;
	private String filename;
	
	public TokenJava(String type, String value, int lexpos, int lineno, int column, String filename){
		this.type = type;
		this.value = value;
		this.lexpos = lexpos;
		this.lineno = lineno;
		this.column = column;
		this.filename = filename;
	}

	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append(this.getType());
		sb.append("\t");
		sb.append(this.getValue());
		sb.append("\t");
		sb.append(this.getLineno());
		sb.append(":");
		sb.append(this.getColumn());
		sb.append("(");
		sb.append(this.getLexpos());
		sb.append(")");
		sb.append(this.getFilename());
		return sb.toString();
	}
}
