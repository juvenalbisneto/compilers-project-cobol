package scanner;

/**
 * Token class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class Token {

	// The token kind
	private int kind;
	// The token spelling
	private String spelling;
	// The line and column that the token was found
	private int line, column;
	
	/**
	 * Default constructor
	 * @param kind
	 * @param spelling
	 * @param line
	 * @param column
	 */
	public Token(int kind, String spelling, int line, int column) {
		this.kind = kind;
		this.spelling = spelling;
		this.line = line;
		this.column = column;
	}

	/**
	 * Returns token kind
	 * @return
	 */
	public int getKind() {
		return kind;
	}

	/**
	 * Returns token spelling
	 * @return
	 */
	public String getSpelling() {
		return spelling;
	}

	/**
	 * Returns the line where the token was found
	 * @return
	 */
	public int getLine() {
		return line;
	}

	/**
	 * Returns the column where the token was found
	 * @return
	 */	
	public int getColumn() {
		return column;
	}
	
	public interface TokenType {
		public final static byte 
		EOF = 0, IDENTIFIER = 1, NUMBER = 2, BOOL_VALUE = 3, PIC9_TYPE = 4, PICBOOL_TYPE = 5, VOID_TYPE = 6,
		
		OP_RELACIONAL = 10, OP_ADD = 11, OP_MULT = 12, L_PAR = 13, R_PAR = 14, POINT = 15,
		MAJOR = 20, MAJOREQ = 21, MINOR = 22, MINOREQ = 23, DIFFERENT = 24,
		
		ACCEPT = 30, FROM = 31, COMPUTE = 32, VALUE = 33, CALL = 34, USING = 35, DISPLAY = 36, RETURN = 37,
		
		IF = 40, THEN = 41, ELSE = 42, END_IF = 43,
		PERFORM = 50, UNTIL = 51, END_PERFORM = 52, BREAK = 53, CONTINUE = 54,
		GLOBALDATA = 60, PROGRAM = 61, DIVISION = 62, MAIN = 63, END_MAIN = 64, END_FUNCTION = 65;
	}
}
