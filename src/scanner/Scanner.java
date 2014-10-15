package scanner;

import compiler.Properties;
import scanner.Token.TokenType;
import util.Arquivo;

/**
 * Scanner class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class Scanner {

	// The file object that will be used to read the source code
	private Arquivo file;
	// The last char read from the source code
	private char currentChar;
	// The kind of the current token
	private int currentKind;
	// Buffer to append characters read from file
	private StringBuffer currentSpelling;
	// Current line and column in the source file
	private int line, column;
	
	/**
	 * Default constructor
	 */
	public Scanner() {
		this.file = new Arquivo(Properties.sourceCodeLocation);		
		this.line = 0;
		this.column = 0;
		this.currentChar = this.file.readChar();
	}
	
	/**
	 * Returns the next token
	 * @return
	 * @throws LexicalException
	 */ //TODO
	public Token getNextToken() throws LexicalException {
		this.currentSpelling = new StringBuffer("");
		
		while(isSeparator(currentChar)){
			this.scanSeparator();
		}
		
		this.currentSpelling.delete(0, this.currentSpelling.length()); //Pode tb apagar o conteudo do buffer
		this.currentKind = this.scanToken();
		
		return new Token(this.currentKind, this.currentSpelling.toString(), this.line, this.column);
	}
	
	/**
	 * Returns if a character is a separator
	 * @param c
	 * @return
	 */
	private boolean isSeparator(char c) {
		if ( c == '#' || c == ' ' || c == '\n' || c == '\t' ) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Reads (and ignores) a separator
	 * @throws LexicalException
	 */
	private void scanSeparator() throws LexicalException {
		if ( this.currentChar == '#' ) { 
			this.getNextChar(); 
			while ( this.isGraphic(this.currentChar) || this.currentChar == '\t') { 
				this.getNextChar(); 
			} 
			if (this.currentChar == '\n') {
				this.getNextChar();  
			} else {
				throw new LexicalException("Erro lexico no scanSeparator! Token esperado e '\\n'", this.currentChar, this.line, this.column);
			}
		} 
		else { 
			this.getNextChar(); 
		} 
	}
	
	/**
	 * Gets the next char
	 */
	private void getNextChar() {
		// Appends the current char to the string buffer
		this.currentSpelling.append(this.currentChar);
		// Reads the next one
		this.currentChar = this.file.readChar();
		// Increments the line and column
		this.incrementLineColumn();
	}
	
	/**
	 * Increments line and column
	 */
	private void incrementLineColumn() {
		// If the char read is a '\n', increments the line variable and assigns 0 to the column
		if ( this.currentChar == '\n' ) {
			this.line++;
			this.column = 0;
		// If the char read is not a '\n' 
		} else {
			// If it is a '\t', increments the column by 4
			if ( this.currentChar == '\t' ) {
				this.column = this.column + 4;
			// If it is not a '\t', increments the column by 1
			} else {
				this.column++;
			}
		}
	}
	
	/**
	 * Returns if a char is a digit (between 0 and 9)
	 * @param c
	 * @return
	 */
	private boolean isDigit(char c) {
		if ( c >= '0' && c <= '9' ) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns if a char is a letter (between a and z or between A and Z)
	 * @param c
	 * @return
	 */
	private boolean isLetter(char c) {
		if ( (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') ) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns if a char is a graphic (any ASCII visible character)
	 * @param c
	 * @return
	 */
	private boolean isGraphic(char c) {
		if ( c >= ' ' && c <= '~' ) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Scans the next token
	 * Simulates the DFA that recognizes the language described by the lexical grammar
	 * @return
	 * @throws LexicalException
	 */ //TODO
	private int scanToken() throws LexicalException {
		int s = 0;
		
		while (this.currentChar != '\000') { //enquanto !EOF
			switch(s){
			case 0:
				if(this.isLetter(this.currentChar)) {
					s = TokenType.IDENTIFIER;
					this.getNextChar();
					break;
				} else if (this.isDigit(this.currentChar)) {
					s = TokenType.NUMBER;
					this.getNextChar();
					break;
				} else if (this.isGraphic(this.currentChar)) {
					if (this.currentChar == '.') {
						this.getNextChar();
						return TokenType.POINT;
					} else if (this.currentChar == ','){
						this.getNextChar();
						return TokenType.COMMA;
					} else if (this.currentChar == '('){
						this.getNextChar();
						return TokenType.L_PAR;
					} else if (this.currentChar == ')'){
						this.getNextChar();
						return TokenType.R_PAR;
					} else if (this.currentChar == '+' || this.currentChar == '-'){
						this.getNextChar();
						return TokenType.OP_ADD;
					} else if (this.currentChar == '*' || this.currentChar == '/'){
						this.getNextChar();
						return TokenType.OP_MULT;
					} else if (this.currentChar == '='){
						this.getNextChar();
						return TokenType.OP_RELACIONAL;//TokenType.EQUALS;
					} else if (this.currentChar == '>'){
						s = TokenType.MAJOR;
						this.getNextChar();
						break;
					} else if (this.currentChar == '<'){
						s = TokenType.MINOR;
						this.getNextChar();
						break;
					}
				} else {
					throw new LexicalException("Erro lexico no estado 0!", this.currentChar, this.line, this.column);
				}
				break;
			case TokenType.IDENTIFIER:
				if (this.isLetter(this.currentChar) || this.isDigit(this.currentChar)){
					s = TokenType.IDENTIFIER;
					this.getNextChar();
					break;
				} else {
					if (this.currentSpelling.toString().equals("IF")){
						return TokenType.IF;
					} else if (this.currentSpelling.toString().equals("THEN")){
						return TokenType.THEN;
					} else if (this.currentSpelling.toString().equals("ELSE")){
						return TokenType.ELSE;
					} else if (this.currentSpelling.toString().equals("ENDIF")){
						return TokenType.END_IF;
					} else if (this.currentSpelling.toString().equals("PERFORM")){
						return TokenType.PERFORM;
					} else if (this.currentSpelling.toString().equals("UNTIL")){
						return TokenType.UNTIL;
					} else if (this.currentSpelling.toString().equals("ENDPERFORM")){
						return TokenType.END_PERFORM;
					} else if (this.currentSpelling.toString().equals("BREAK")){
						return TokenType.BREAK;
					} else if (this.currentSpelling.toString().equals("CONTINUE")){
						return TokenType.CONTINUE;
					} else if (this.currentSpelling.toString().equals("ACCEPT")){
						return TokenType.ACCEPT;
					} else if (this.currentSpelling.toString().equals("FROM")){
						return TokenType.FROM;
					} else if (this.currentSpelling.toString().equals("COMPUTE")){
						return TokenType.COMPUTE;
					} else if (this.currentSpelling.toString().equals("VALUE")){
						return TokenType.VALUE;
					} else if (this.currentSpelling.toString().equals("CALL")){
						return TokenType.CALL;
					} else if (this.currentSpelling.toString().equals("USING")){
						return TokenType.USING;
					} else if (this.currentSpelling.toString().equals("DISPLAY")){
						return TokenType.DISPLAY;
					} else if (this.currentSpelling.toString().equals("RETURN")){
						return TokenType.RETURN;
					} else if (this.currentSpelling.toString().equals("GLOBALDATA")){
						return TokenType.GLOBALDATA;
					} else if (this.currentSpelling.toString().equals("PROGRAM")){
						return TokenType.PROGRAM;
					} else if (this.currentSpelling.toString().equals("DIVISION")){
						return TokenType.DIVISION;
					} else if (this.currentSpelling.toString().equals("MAIN")){
						return TokenType.MAIN;
					} else if (this.currentSpelling.toString().equals("ENDMAIN")){
						return TokenType.END_MAIN;
					} else if (this.currentSpelling.toString().equals("ENDFUNCTION")){
						return TokenType.END_FUNCTION;
					} else if (this.currentSpelling.toString().equals("PIC9")){
						return TokenType.PIC9_TYPE;
					} else if (this.currentSpelling.toString().equals("PICBOOL")){
						return TokenType.PICBOOL_TYPE;
					} else if (this.currentSpelling.toString().equals("VOID")){
						return TokenType.VOID_TYPE;
					} else if (this.currentSpelling.toString().equals("TRUE") || this.currentSpelling.toString().equals("FALSE")){
						return TokenType.BOOL_VALUE;
					} else {
						return TokenType.IDENTIFIER;
					}
				}
			case TokenType.NUMBER:
				if (this.isDigit(this.currentChar)) {
					s = TokenType.NUMBER;
					this.getNextChar();
					break;
				} else {
					return TokenType.NUMBER;
				}
			case TokenType.MAJOR:
				if (this.currentChar == '='){
					this.getNextChar();
					return TokenType.OP_RELACIONAL;//TokenType.MAJOREQ;
				} else {
					return TokenType.OP_RELACIONAL;//TokenType.MAJOR;
				}
			case TokenType.MINOR:
				if (this.currentChar == '='){
					this.getNextChar();
					return TokenType.OP_RELACIONAL;//TokenType.MINOREQ;
				} else if (this.currentChar == '>') {
					this.getNextChar();
					return TokenType.OP_RELACIONAL;//TokenType.DIFFERENT;
				} else {
					return TokenType.OP_RELACIONAL;//TokenType.MINOR;
				}
			}
		}
		
		return s;
	}
	
	
}
