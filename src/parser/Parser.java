package parser;

import scanner.LexicalException;
import scanner.Scanner;
import scanner.Token;
import scanner.Token.TokenType;
import util.AST.AST;

/**
 * Parser class
 * @version 2010-august-29
 * @discipline Projeto de Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class Parser { 

	// The current token
	private Token currentToken = null;
	// The scanner
	private Scanner scanner = null;
	
	/**
	 * Parser constructor
	 * @throws LexicalException 
	 */
	public Parser() throws LexicalException {
		// Initializes the scanner object
		this.scanner = new Scanner();
		this.currentToken = this.scanner.getNextToken();
	}
	
	/**
	 * Verifies if the current token kind is the expected one
	 * @param kind
	 * @throws SyntacticException
	 * @throws LexicalException 
	 */
	private void accept(int kind) throws SyntacticException, LexicalException {
		// If the current token kind is equal to the expected
			// Gets next token
		// If not
			// Raises an exception
		if (this.currentToken.getKind() == kind){
			this.currentToken = this.scanner.getNextToken();
		} else {
			throw new SyntacticException("Accept ERROR in currentToken= " + this.currentToken.getSpelling() + " | expectedToken = " + Token.kindName(kind), this.currentToken);
		}

	}
	
	/**
	 * Gets next token
	 * @throws LexicalException 
	 */
	private void acceptIt() throws LexicalException {
		this.currentToken = this.scanner.getNextToken();
	}

	/**
	 * Verifies if the source program is syntactically correct
	 * @throws SyntacticException
	 */
	public AST parse() throws SyntacticException {
		return null;
	}

	public void parseCode() throws SyntacticException, LexicalException{
		while(this.currentToken.getKind() != TokenType.EOF ){
			if (this.currentToken.getKind() == TokenType.GLOBALDATA){
				parseGlobalDataDiv();
			} else {
				parseProgramDiv();
			}
		}
	}

	public void parseGlobalDataDiv() throws SyntacticException, LexicalException{
		accept(TokenType.GLOBALDATA);
		accept(TokenType.DIVISION);
		accept(TokenType.POINT);
		
		if(this.currentToken.getKind() == TokenType.PIC9_TYPE || this.currentToken.getKind() == TokenType.PICBOOL_TYPE){
			do{
				parseVarDeclaration();
			} while (this.currentToken.getKind() != TokenType.PROGRAM);

		}
		
	}

	public void parseProgramDiv() throws SyntacticException, LexicalException{

		accept(TokenType.PROGRAM);
		accept(TokenType.DIVISION);
		accept(TokenType.POINT);

		if(this.currentToken.getKind() == TokenType.IDENTIFIER){
			do{
				parseFunction();
			} while (this.currentToken.getKind() != TokenType.MAIN);
		}
		parseMainProc();
	}
	

	public void parseVarDeclaration() throws SyntacticException, LexicalException{
		if(this.currentToken.getKind() == TokenType.PIC9_TYPE){
			parseVarPIC9Declaration();
		} else {
			parseVarPICBOOLDeclaration();
		}
	}


	public void parseVarPIC9Declaration() throws SyntacticException, LexicalException{
		accept(TokenType.PIC9_TYPE);
		accept(TokenType.IDENTIFIER);
		
		if(this.currentToken.getKind() == TokenType.VALUE){
			accept(TokenType.VALUE);
			accept(TokenType.NUMBER);			
		}
		accept(TokenType.POINT);
	}

	public void parseVarPICBOOLDeclaration() throws SyntacticException, LexicalException{
		accept(TokenType.PICBOOL_TYPE);
		accept(TokenType.IDENTIFIER);
		if(this.currentToken.getKind() == TokenType.VALUE){
			accept(TokenType.VALUE);
			accept(TokenType.BOOL_VALUE);			
		}
		accept(TokenType.POINT);
	}


	public void parseMainProc() throws SyntacticException, LexicalException{
		accept(TokenType.MAIN);
		accept(TokenType.POINT);		
				
		while(this.currentToken.getKind() == TokenType.PIC9_TYPE ||this.currentToken.getKind() == TokenType.PICBOOL_TYPE){
				parseVarDeclaration();
		}

		if(this.currentToken.getKind() == TokenType.END_MAIN){
			accept(TokenType.END_MAIN);
		} else {
			parseCommand();
			accept(TokenType.END_MAIN);
		}
	}


	public void parseFunction() throws SyntacticException, LexicalException{
		accept(TokenType.IDENTIFIER);

		if(this.currentToken.getKind() == TokenType.VOID_TYPE){
			acceptIt();
		}else if(this.currentToken.getKind() == TokenType.PIC9_TYPE){
			acceptIt();
		} else {
			accept(TokenType.PICBOOL_TYPE);
		}

		if(this.currentToken.getKind() == TokenType.USING){
			acceptIt();
			if(this.currentToken.getKind() == TokenType.PIC9_TYPE){
				acceptIt();
			} else {
				accept(TokenType.PICBOOL_TYPE);
			}
			accept(TokenType.IDENTIFIER);

			while(this.currentToken.getKind() != TokenType.POINT){

				if(this.currentToken.getKind() == TokenType.COMMA){
						acceptIt();
					if(this.currentToken.getKind() == TokenType.PIC9_TYPE){
						acceptIt();
					} else {
						accept(TokenType.PICBOOL_TYPE);
					}
						accept(TokenType.IDENTIFIER);
				}
			}
			accept(TokenType.POINT);

		}

		while(this.currentToken.getKind() == TokenType.PIC9_TYPE ||this.currentToken.getKind() == TokenType.PICBOOL_TYPE){
			parseVarDeclaration();
		}

		if(this.currentToken.getKind() != TokenType.END_FUNCTION){
			parseCommand();
		} else {
			accept(TokenType.END_FUNCTION);
		}		
	}

	public void parseFunctionCall() throws SyntacticException, LexicalException{
		accept(TokenType.CALL);
		accept(TokenType.IDENTIFIER);

		if(this.currentToken.getKind() == TokenType.USING){
			accept(TokenType.IDENTIFIER);

			while(this.currentToken.getKind() == TokenType.COMMA){
				acceptIt();
				accept(TokenType.IDENTIFIER);
			}			
		}
		accept(TokenType.POINT);
	}

	public void parseCommand(){
		if(this.currentToken.getKind() == TokenType.IF){
			parseIf();
		}else if(this.currentToken.getKind() == TokenType.PERFORM){
			parseUntil();
		}else if(this.currentToken.getKind() == TokenType.ACCEPT){
			parseAssigment();
		}else if(this.currentToken.getKind() == TokenType.DISPLAY){
			parseDisplay();
		}else if(this.currentToken.getKind() == TokenType.CALL){
			parseFunctionCall();
		}else if(this.currentToken.getKind() == TokenType.BREAK){
			parseBreakStatment();
		}else if(this.currentToken.getKind() == TokenType.CONTINUE){
			parseContinueStatment();
		} else {
			parseReturnStatment();
		}
	}

	//******************************************************************
	// FALTA TUDO DE EXPRESSION PQ N SEI O Q O PROF VAI SUGERIR MUDAR...
	//******************************************************************
	//TODO EXPRESSIONS
	public void parseExpression(){
		
	};
	public void parseBooleanExpression(){
		
	};
	
	
	public void parseArithmeticParcel() throws LexicalException, SyntacticException{
		parseArithmeticTerm();
		if(this.currentToken.getKind() == TokenType.OP_ADD){
			acceptIt();
			parseArithmeticParcel();
		}	
	}
	
	public void parseArithmeticTerm() throws LexicalException, SyntacticException{
		parseArithmeticFactor();
		if(this.currentToken.getKind() == TokenType.OP_MULT){
			acceptIt();
			parseArithmeticTerm();
		}	

	}

	public void parseArithmeticFactor() throws LexicalException, SyntacticException{
		if(this.currentToken.getKind() == TokenType.NUMBER){
			acceptIt();
		}else if(this.currentToken.getKind() == TokenType.IDENTIFIER){
			acceptIt();
		} else {
			accept(TokenType.L_PAR);
			parseArithmeticParcel();
			accept(TokenType.R_PAR);
		}
	}


	public void parseAssigment() throws SyntacticException, LexicalException{
		accept(TokenType.ACCEPT);
		accept(TokenType.IDENTIFIER);
		accept(TokenType.FROM);
		if(this.currentToken.getKind() == TokenType.CALL){
			parseFunctionCall();
		} else {
			parseExpression();
		}
		accept(TokenType.POINT);
	}


	public void parseIfStatment() throws SyntacticException, LexicalException{
		accept(TokenType.IF);
		parseBooleanExpression();
		accept(TokenType.THEN);

		do{
			parseCommand();
		} while(this.currentToken.getKind() != TokenType.ELSE || this.currentToken.getKind() != TokenType.END_IF);

		if(this.currentToken.getKind() == TokenType.ELSE){
			acceptIt();
			do{
				parseCommand();
			} while(this.currentToken.getKind() != TokenType.END_IF);

		}
		accept(TokenType.END_IF);

	}

	public void parseUntil() throws SyntacticException, LexicalException{
		accept(TokenType.PERFORM);
		accept(TokenType.UNTIL);
		parseBooleanExpression();

		do{
			parseCommand();
		} while(this.currentToken.getKind() != TokenType.END_PERFORM);

	}

	public void parseDisplay() throws SyntacticException, LexicalException{
		accept(TokenType.DISPLAY);
		if(this.currentToken.getKind() == TokenType.IDENTIFIER){
			acceptIt();
		} else {
			parseExpression();
		}
		accept(TokenType.POINT);
	}

	public void parseReturnStatment() throws SyntacticException, LexicalException{
		accept(TokenType.RETURN);
		if(this.currentToken.getKind() == TokenType.IDENTIFIER){
			acceptIt();
		} else {
			parseExpression();
		}
		accept(TokenType.POINT);

	}

	public void parseBreakStatment() throws SyntacticException, LexicalException{
		accept(TokenType.BREAK);
		accept(TokenType.POINT);
	}


	public void parseContinueStatment() throws SyntacticException, LexicalException{
		accept(TokenType.CONTINUE);
		accept(TokenType.POINT);
	}








}

















