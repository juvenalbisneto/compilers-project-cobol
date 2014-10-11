package parser;

import scanner.LexicalException;
import scanner.Scanner;
import scanner.Token;
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
	 */ //TODO
	private void accept(int kind) throws SyntacticException, LexicalException {
		// If the current token kind is equal to the expected
			// Gets next token
		// If not
			// Raises an exception
		if (this.currentToken.getKind()== kind){
			this.currentToken=this.scanner.getNextToken();
		}else {
			throw new SyntacticException("Erro em accept", this.currentToken);
		}

	}
	
	/**
	 * Gets next token
	 * @throws LexicalException 
	 */ //TODO
	private void acceptIt() throws LexicalException {
		this.currentToken = this.scanner.getNextToken();
	}

	/**
	 * Verifies if the source program is syntactically correct
	 * @throws SyntacticException
	 */ //TODO
	public AST parse() throws SyntacticException {
		return null;
	}



	public parseCode(){
		while(this.currentToken()!= TokenType.EOT ){
			if (this.currentToken.getKind() == TokenType.GLOBALDATA){
				parseGlobalDataDiv();
			}else{
				parseProgramDiv();
			}
		}
	}

	public parseGlobalDataDiv(){
		accept(TokenType.GLOBALDATA);
		accept(TokenType.DIVISION);
		accept(TokenType.POINT);
		if(this.currentToken.getKind()==TokenType.PIC9 ||this.currentToken.getKind()==TokenType.PICBOOL){
			do{
				parseVarDeclaration();
			}while(this.currentToken!=TokenType.PROGRAM);

		}
		
	}

	public parseProgramDiv(){

		accept(TokenType.PROGRAM);
		accept(TokenType.DIVISION);
		accept(TokenType.POINT);

		if(this.currentToken.getKind()==TokenType.IDENTIFIER){
			do{
				parseFunction();
			}while(this.currentToken.getKind()!=TokenType.MAIN)
		}
		parseMainProc();
	}
	

	public parseVarDeclaration(){
		if(this.currentToken.getKind()==TokenType.PIC9){
			parseVarPIC9Declaration();
		}else{
			parseVarPICBOOLDeclaration();
		}
	}


	public parseVarPIC9Declaration(){
		accept(TokenType.PIC9);
		accept(TokenType.IDENTIFIER);
		if(this.currentToken.getKind()==TokenType.VALUE){
			accept(TokenType.VALUE);
			accept(TokenType.NUMBER);			
		}
		accept(TokenType.POINT);
	}

	public parseVarPICBOOLDeclaration(){
		accept(TokenType.PICBOOL);
		accept(TokenType.IDENTIFIER);
		if(this.currentToken.getKind()==TokenType.VALUE){
			accept(TokenType.VALUE);
			accept(TokenType.BOOLVALUE);			
		}
		accept(TokenType.POINT);
	}


	public parseMainProc(){
		accept(TokenType.MAIN);
		accept(TokenType.POINT);		
				
		while(this.currentToken.getKind()==TokenType.PIC9 ||this.currentToken.getKind()==TokenType.PICBOOL){
				parseVarDeclaration();
		}

		if(this.currentToken.getKind()==TokenType.ENDMAIN){
			accept(TokenType.ENDMAIN);
		}else{
			parseCommand();
			accept(TokenType.ENDMAIN);
		}
	}


	public parseFunction(){
		accept(TokenType.IDENTIFIER);

		if(this.currentToken.getKind()==TokenType.VOID){
			acceptIt();
		}else if(this.currentToken.getKind()==TokenType.PIC9){
			acceptIt();
		}else{
			accept(TokenType.PICBOOL);
		}

		if(this.currentToken.getKind()=TokenType.USING){
			acceptIt();
			if(this.currentToken.getKind()==TokenType.PIC9){
				acceptIt();
			}else{
				accept(TokenType.PICBOOL);
			}
			accept(TokenType.IDENTIFIER);

			while(this.currentToken.getKind()!=TokenType.POINT){

				if(this.currentToken.getKind()==TokenType.COMMA){
						acceptIt();
					if(this.currentToken.getKind()==TokenType.PIC9){
						acceptIt();
					}else{
						accept(TokenType.PICBOOL);
					}
						accept(TokenType.IDENTIFIER);
				}
			}
			accept(TokenType.POINT);

		}

		while(this.currentToken.getKind()==TokenType.PIC9 ||this.currentToken.getKind()==TokenType.PICBOOL){
			parseVarDeclaration();
		}

		if(this.currentToken.getKind()!=TokenType.ENDFUNCTION){
			parseCommand();
		}else{
			accept(TokenType.ENDFUNCTION);
		}		
	}

	public parseFunctionCall(){
		accept(TokenType.CALL);
		accept(TokenType.IDENTIFIER);

		if(this.currentToken.getKind()==TokenType.USING){
			accept(TokenType.IDENTIFIER);

			while(this.currentToken.getKind()==TokenType.COMMA){
				acceptIt();
				acceptIt(TokenType.IDENTIFIER);
			}			
		}
		accept(TokenType.POINT);
	}

	public parseCommand(){
		if(this.currentToken.getKind()==TokenType.IF){
			parseIf();
		}else if(this.currentToken.getKind()==TokenType.PERFORM){
			parseUntil();
		}else if(this.currentToken.getKind()==TokenType.ACCEPT){
			parseAssigment();
		}else if(this.currentToken.getKind()==TokenType.DISPLAY){
			parseDisplay();
		}else if(this.currentToken.getKind()==TokenType.CALL){
			parseFunctionCall();
		}else if(this.currentToken.getKind()==TokenType.BREAK){
			parseBreakStatment();
		}else if(this.currentToken.getKind()==TokenType.CONTINUE{
			parseContinueStatment();
		}else{
			parseReturnStatment();
		}
	}

	//******************************************************************

	//FALTA TUDO DE EXPRESSION PQ N SEI O Q O PROF VAI SUGERIR MUDAR....

	//******************************************************************
	public parseArithmeticParcel(){
		parseArithmeticTerm();
		if(this.currentToken.getKind()==TokenType.OP_ADD){
			acceptIt();
			parseArithmeticParcel();
		}	
	}
	
	public parseArithmeticTerm(){
		parseArithmeticFactor();
		if(this.currentToken.getKind()==TokenType.OP_MULT){
			acceptIt();
			parseArithmeticTerm();
		}	

	}

	public parseArithmeticFactor(){
		if(this.currentToken.getKind()==TokenType.NUMBER){
			acceptIt();
		}else if(this.currentToken.getKind()==TokenType.IDENTIFIER){
			acceptIt();
		}else{
			accept(TokenType.L_PAR);
			parseArithmeticParcel();
			accept(TokenType.R_PAR);
		}
	}


	public parseAssigment(){
		accept(TokenType.ACCEPT);
		accept(TokenType.IDENTIFIER);
		accept(TokenType.FROM);
		if(this.currentToken.getKind()==TokenType.CALL){
			parseFunctionCall();
		}else{
			parseExpression();
		}
		accept(TokenType.POINT);
	}


	public parseIfStatment(){
		accept(TokenType.IF);
		parseBooleanExpression();
		accept(TokenType.THEN);

		do{
			parseCommand();
		}while(this.currentToken.getKind()!=TokenType.ELSE || this.currentToken.getKind()!=TokenType.ENDIF);

		if(this.currentToken.getKind()==ELSE){
			acceptIt();
			do{
				parseCommand();
			}while(this.currentToken.getKind()!=TokenType.ENDIF);

		}
		accept(TokenType.ENDIF);

	}

	public parseUntil(){
		accept(TokenType.PERFORM);
		accept(TokenType.UNTIL);
		parseBooleanExpression();

		do{
			parseCommand();
		}while(this.currentToken.getKind()!=TokenType.ENDPERFORM);

	}

	public parseDisplay(){
		accept(TokenType.DISPLAY);
		if(this.currentToken.getKind()=TokenType.IDENTIFIER){
			acceptIt();
		}else{
			parseExpression();
		}
		accept(TokenType.POINT);
	}

	public parseReturnStatment(){
		accept(TokenType.RETURN);
		if(this.currentToken.getKind()=TokenType.IDENTIFIER){
			acceptIt();
		}else{
			parseExpression();
		}
		accept(TokenType.POINT);

	}

	public parseBreakStatment(){
		accept(TokenType.BREAK);
		accept(TokenType.POINT);
	}


	public parseContinueStatment(){
		accept(TokenType.CONTINUE);
		accept(TokenType.POINT);
	}








}

















