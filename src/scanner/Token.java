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
	
	public static String kindName(int kind){
		switch (kind){
		case TokenType.EOF: return "EOF";
		case TokenType.IDENTIFIER: return"IDENTIFIER";
		case TokenType.NUMBER: return"NUMBER";
		case TokenType.BOOL_VALUE: return"BOOL_VALUE";
		case TokenType.PIC9_TYPE: return"PIC9_TYPE";
		case TokenType.PICBOOL_TYPE: return"PICBOOL_TYPE";
		case TokenType.VOID_TYPE: return"VOID_TYPE";
		case TokenType.OP_RELACIONAL: return"OP_RELACIONAL";
		case TokenType.OP_ADD: return"OP_ADD";
		case TokenType.OP_MULT: return"OP_MULT";
		case TokenType.L_PAR: return"L_PAR";
		case TokenType.R_PAR: return"R_PAR";
		case TokenType.POINT: return"POINT";
		case TokenType.COMMA: return"COMMA";
		case TokenType.MAJOR: return"MAJOR";
		case TokenType.MAJOREQ: return"MAJOREQ";
		case TokenType.MINOR: return"MINOR";
		case TokenType.MINOREQ: return"MINOREQ";
		case TokenType.DIFFERENT: return"DIFFERENT";
		case TokenType.EQUALS: return"EQUALS";
		case TokenType.ACCEPT: return"ACCEPT";
		case TokenType.FROM: return"FROM";
		case TokenType.COMPUTE: return"COMPUTE";
		case TokenType.VALUE: return"VALUE";
		case TokenType.CALL: return"CALL";
		case TokenType.USING: return"USING";
		case TokenType.DISPLAY: return"DISPLAY";
		case TokenType.RETURN: return"RETURN";
		case TokenType.IF: return"IF";
		case TokenType.THEN: return"THEN";
		case TokenType.ELSE: return"ELSE";
		case TokenType.END_IF: return"END_IF";
		case TokenType.PERFORM: return"PERFORM";
		case TokenType.UNTIL: return"UNTIL";
		case TokenType.END_PERFORM: return"END_PERFORM";
		case TokenType.BREAK: return"BREAK";
		case TokenType.CONTINUE: return"CONTINUE";
		case TokenType.GLOBALDATA: return"GLOBALDATA";
		case TokenType.PROGRAM: return"PROGRAM";
		case TokenType.DIVISION: return"DIVISION";
		case TokenType.MAIN: return"MAIN";
		case TokenType.END_MAIN: return"END_MAIN";
		case TokenType.END_FUNCTION: return"END_FUNCTION";
		default: return "";
		}
	}
	
	public interface TokenType {
		public final static byte 
		EOF = 0, IDENTIFIER = 1, NUMBER = 2, BOOL_VALUE = 3, PIC9_TYPE = 4, PICBOOL_TYPE = 5, VOID_TYPE = 6,
		
		OP_RELACIONAL = 10, OP_ADD = 11, OP_MULT = 12, L_PAR = 13, R_PAR = 14, POINT = 15, COMMA = 16,
		MAJOR = 20, MAJOREQ = 21, MINOR = 22, MINOREQ = 23, DIFFERENT = 24, EQUALS = 25,
		
		ACCEPT = 30, FROM = 31, COMPUTE = 32, VALUE = 33, CALL = 34, USING = 35, DISPLAY = 36, RETURN = 37,
		
		IF = 40, THEN = 41, ELSE = 42, END_IF = 43,
		PERFORM = 50, UNTIL = 51, END_PERFORM = 52, BREAK = 53, CONTINUE = 54,
		GLOBALDATA = 60, PROGRAM = 61, DIVISION = 62, MAIN = 63, END_MAIN = 64, END_FUNCTION = 65;
	}
}
