package parser;

import java.util.ArrayList;

import scanner.LexicalException;
import scanner.Scanner;
import scanner.Token;
import scanner.Token.TokenType;
import util.AST.*;
import util.AST.Number;

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
	 * @throws LexicalException 
	 */
	public AST parse() throws SyntacticException, LexicalException {
		AST code = parseCode();
		return code;
	}

	public Code parseCode() throws SyntacticException, LexicalException{
		GlobalDataDiv gdiv = null;
		if (this.currentToken.getKind() == TokenType.GLOBALDATA){
			gdiv = parseGlobalDataDiv();
		}
		ProgramDiv pdiv = parseProgramDiv();
		accept(TokenType.EOF);
		return new Code(gdiv, pdiv);
	}

	public GlobalDataDiv parseGlobalDataDiv() throws SyntacticException, LexicalException{
		accept(TokenType.GLOBALDATA);
		accept(TokenType.DIVISION);
		accept(TokenType.POINT);
		
		ArrayList<VarDeclaration> aVar = new ArrayList<VarDeclaration>();
		if(this.currentToken.getKind() == TokenType.PIC9_TYPE || this.currentToken.getKind() == TokenType.PICBOOL_TYPE){
			do{
				aVar.add(parseVarDeclaration());
			} while (this.currentToken.getKind() != TokenType.PROGRAM);
		}
		
		return new GlobalDataDiv(aVar);
	}

	public ProgramDiv parseProgramDiv() throws SyntacticException, LexicalException{
		accept(TokenType.PROGRAM);
		accept(TokenType.DIVISION);
		accept(TokenType.POINT);
		
		ArrayList<Function> aFunc = new ArrayList<Function>();
		if(this.currentToken.getKind() == TokenType.IDENTIFIER){
			do{
				aFunc.add(parseFunction());
			} while (this.currentToken.getKind() != TokenType.MAIN);
		}
		MainProc mProc = parseMainProc();
		
		return new ProgramDiv(aFunc, mProc);
	}

	public VarDeclaration parseVarDeclaration() throws SyntacticException, LexicalException{
		VarDeclaration vDecl = null;
		if(this.currentToken.getKind() == TokenType.PIC9_TYPE){
			vDecl = parseVarPIC9Declaration();
		} else {
			vDecl = parseVarPICBOOLDeclaration();
		}
		
		return vDecl;
	}

	public VarPIC9Declaration parseVarPIC9Declaration() throws SyntacticException, LexicalException{
		accept(TokenType.PIC9_TYPE);
		Identifier id = null;
		if(this.currentToken.getKind() == TokenType.IDENTIFIER){
			id = new Identifier(this.currentToken.getSpelling());
			acceptIt();
		} else {
			throw new SyntacticException("Accept ERROR: missing IDENTIFIER", this.currentToken);
		}
		
		Number num = null;
		if(this.currentToken.getKind() == TokenType.VALUE){
			acceptIt();
			if(this.currentToken.getKind() == TokenType.NUMBER){
				num = new Number(this.currentToken.getSpelling());
				acceptIt();
			} else {
				throw new SyntacticException("Accept ERROR: missing NUMBER", this.currentToken);
			}
						
		}
		accept(TokenType.POINT);
		return new VarPIC9Declaration(id, num);
	}

	public VarPICBOOLDeclaration parseVarPICBOOLDeclaration() throws SyntacticException, LexicalException{
		accept(TokenType.PICBOOL_TYPE);
		Identifier id = null;
		if(this.currentToken.getKind() == TokenType.IDENTIFIER){
			id = new Identifier(this.currentToken.getSpelling());
			accept(TokenType.IDENTIFIER);
		} else {
			throw new SyntacticException("Accept ERROR: missing IDENTIFIER", this.currentToken);
		}
		
		BoolValue bValue = null;
		if(this.currentToken.getKind() == TokenType.VALUE){
			acceptIt();
			accept(TokenType.BOOL_VALUE);
			if(this.currentToken.getKind() == TokenType.BOOL_VALUE){
				bValue = new BoolValue(this.currentToken.getSpelling());
				acceptIt();
			} else {
				throw new SyntacticException("Accept ERROR: missing BOOL_VALUE", this.currentToken);
			}
		}
		accept(TokenType.POINT);
		return new VarPICBOOLDeclaration(id, bValue);
	}

	public MainProc parseMainProc() throws SyntacticException, LexicalException{
		accept(TokenType.MAIN);
		accept(TokenType.POINT);		

		while(this.currentToken.getKind() == TokenType.PIC9_TYPE ||this.currentToken.getKind() == TokenType.PICBOOL_TYPE){
			parseVarDeclaration();
		}

		while(this.currentToken.getKind() != TokenType.END_MAIN){
			parseCommand();
		}
		accept(TokenType.END_MAIN);
	}

	public Function parseFunction() throws SyntacticException, LexicalException{
		accept(TokenType.IDENTIFIER);

		if(this.currentToken.getKind() == TokenType.VOID_TYPE){
			acceptIt();
		} else if(this.currentToken.getKind() == TokenType.PIC9_TYPE){
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
		}
		accept(TokenType.POINT);

		while(this.currentToken.getKind() == TokenType.PIC9_TYPE || this.currentToken.getKind() == TokenType.PICBOOL_TYPE){
			parseVarDeclaration();
		}

		while(this.currentToken.getKind() != TokenType.END_FUNCTION){
			parseCommand();
		}
		
		accept(TokenType.END_FUNCTION);
	}

	public void parseFunctionCall() throws SyntacticException, LexicalException{
		accept(TokenType.CALL);
		accept(TokenType.IDENTIFIER);

		if(this.currentToken.getKind() == TokenType.USING){
			acceptIt();
			accept(TokenType.IDENTIFIER);

			while(this.currentToken.getKind() == TokenType.COMMA){
				acceptIt();
				accept(TokenType.IDENTIFIER);
			}			
		}
		accept(TokenType.POINT);
	}

	public void parseCommand() throws SyntacticException, LexicalException{
		if(this.currentToken.getKind() == TokenType.IF){
			parseIfStatment();
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

	public void parseExpression() throws SyntacticException, LexicalException{
		if(this.currentToken.getKind() == TokenType.COMPUTE){
			parseArithmeticExpression();
		} else {
			parseBooleanExpression();
		}
	}
	
	public void parseBooleanExpression() throws LexicalException, SyntacticException{
		if(this.currentToken.getKind() == TokenType.BOOL_VALUE){
			acceptIt();
		} else {
			accept(TokenType.L_PAR);
			
			if(this.currentToken.getKind() == TokenType.IDENTIFIER){
				acceptIt();
			} if(this.currentToken.getKind() == TokenType.NUMBER){
				acceptIt();
			} if(this.currentToken.getKind() == TokenType.COMPUTE){
				parseArithmeticExpression();
			} else {
				parseBooleanExpression();
			}
			
			accept(TokenType.OP_RELACIONAL);
			
			if(this.currentToken.getKind() == TokenType.IDENTIFIER){
				acceptIt();
			} if(this.currentToken.getKind() == TokenType.NUMBER){
				acceptIt();
			} if(this.currentToken.getKind() == TokenType.COMPUTE){
				parseArithmeticExpression();
			} else {
				parseBooleanExpression();
			}
			
			accept(TokenType.R_PAR);
		}
	}
	
	public void parseArithmeticExpression() throws SyntacticException, LexicalException{
		accept(TokenType.COMPUTE);
		accept(TokenType.L_PAR);
		parseArithmeticParcel();
		accept(TokenType.R_PAR);
	}

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
		if(this.currentToken.getKind() == TokenType.IDENTIFIER){
			acceptIt();
		} else if(this.currentToken.getKind() == TokenType.NUMBER){
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
		} while(this.currentToken.getKind() != TokenType.ELSE && this.currentToken.getKind() != TokenType.END_IF);

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
		accept(TokenType.POINT);
		
		do{
			parseCommand();
		} while(this.currentToken.getKind() != TokenType.END_PERFORM);
		
		accept(TokenType.END_PERFORM);
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

















