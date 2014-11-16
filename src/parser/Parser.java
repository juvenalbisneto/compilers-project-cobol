package parser;

import java.util.ArrayList;

import scanner.LexicalException;
import scanner.Scanner;
import scanner.Token;
import scanner.Token.TokenType;
import util.AST.*;
import util.AST.AST.Types;
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
			throw new SyntacticException("Accept ERROR: expectedToken = " + Token.kindName(kind), this.currentToken);
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
		
		ArrayList<VarDeclaration> aVar = null;
		if(this.currentToken.getKind() == TokenType.PIC9_TYPE || this.currentToken.getKind() == TokenType.PICBOOL_TYPE){
			aVar = new ArrayList<VarDeclaration>();
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
		
		ArrayList<Function> aFunc = null;
		if(this.currentToken.getKind() == TokenType.IDENTIFIER){
			aFunc = new ArrayList<Function>();
			do{
				aFunc.add(parseFunction());
			} while (this.currentToken.getKind() != TokenType.MAIN);
		}
		MainProc mProc = parseMainProc();
		
		return new ProgramDiv(aFunc, mProc);
	}

	public VarDeclaration parseVarDeclaration() throws SyntacticException, LexicalException{
		if(this.currentToken.getKind() == TokenType.PIC9_TYPE){
			return parseVarPIC9Declaration();
		} else {
			return parseVarPICBOOLDeclaration();
		}
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
		ArrayList<VarDeclaration> aVar = null;
		ArrayList<Command> aCmd = null;
		
		accept(TokenType.MAIN);
		accept(TokenType.POINT);		
		
		aVar = new ArrayList<VarDeclaration>();
		if(this.currentToken.getKind() == TokenType.PIC9_TYPE || this.currentToken.getKind() == TokenType.PICBOOL_TYPE){
			while(this.currentToken.getKind() == TokenType.PIC9_TYPE || this.currentToken.getKind() == TokenType.PICBOOL_TYPE){
				aVar.add(parseVarDeclaration());
			}
		}
		
		if(this.currentToken.getKind() != TokenType.END_MAIN){
			aCmd = new ArrayList<Command>();
			while(this.currentToken.getKind() != TokenType.END_MAIN){
				aCmd.add(parseCommand());
			}
		}
		accept(TokenType.END_MAIN);
		
		return new MainProc(aVar, aCmd);
	}

	public Function parseFunction() throws SyntacticException, LexicalException{
		Identifier id = null;
		String tipoRetorno;
		ArrayList<Identifier> params = null;
		ArrayList<String> paramTypes = null;
		ArrayList<VarDeclaration> declarations = null;
		ArrayList<Command> cmds = null;
		
		if(this.currentToken.getKind() == TokenType.IDENTIFIER){
			id = new Identifier(this.currentToken.getSpelling());
			acceptIt();
		} else {
			throw new SyntacticException("Accept ERROR: missing IDENTIFIER", this.currentToken);
		}
		
		tipoRetorno = this.currentToken.getSpelling();
		if(this.currentToken.getKind() == TokenType.VOID_TYPE){
			acceptIt();
		} else if(this.currentToken.getKind() == TokenType.PIC9_TYPE){
			acceptIt();
		} else {
			accept(TokenType.PICBOOL_TYPE);
		}

		if(this.currentToken.getKind() == TokenType.USING){
			params = new ArrayList<Identifier>();
			paramTypes = new ArrayList<String>();
			acceptIt();
			
			paramTypes.add(this.currentToken.getSpelling());
			if(this.currentToken.getKind() == TokenType.PIC9_TYPE){
				acceptIt();
			} else {
				accept(TokenType.PICBOOL_TYPE);
			}
			
			if(this.currentToken.getKind() == TokenType.IDENTIFIER){
				params.add(new Identifier(this.currentToken.getSpelling()));
				acceptIt();
			} else {
				throw new SyntacticException("Accept ERROR: missing IDENTIFIER", this.currentToken);
			}

			while(this.currentToken.getKind() != TokenType.POINT){
				if(this.currentToken.getKind() == TokenType.COMMA){
					acceptIt();
					
					paramTypes.add(this.currentToken.getSpelling());
					if(this.currentToken.getKind() == TokenType.PIC9_TYPE){
						acceptIt();
					} else {
						accept(TokenType.PICBOOL_TYPE);
					}
					
					if(this.currentToken.getKind() == TokenType.IDENTIFIER){
						params.add(new Identifier(this.currentToken.getSpelling()));
						acceptIt();
					} else {
						throw new SyntacticException("Accept ERROR: missing IDENTIFIER", this.currentToken);
					}
				} else {
					throw new SyntacticException("Accept ERROR: missing COMMA", this.currentToken);
				}
			}
		}
		accept(TokenType.POINT);
		
		if(this.currentToken.getKind() == TokenType.PIC9_TYPE || this.currentToken.getKind() == TokenType.PICBOOL_TYPE){
			declarations = new ArrayList<VarDeclaration>();
			while(this.currentToken.getKind() == TokenType.PIC9_TYPE || this.currentToken.getKind() == TokenType.PICBOOL_TYPE){
				declarations.add(parseVarDeclaration());
			}
		}
		
		if(this.currentToken.getKind() != TokenType.END_FUNCTION){
			cmds = new ArrayList<Command>();
			while(this.currentToken.getKind() != TokenType.END_FUNCTION){
				cmds.add(parseCommand());
			}
		}
		
		accept(TokenType.END_FUNCTION);
		
		return new Function(tipoRetorno, id, params, paramTypes, declarations, cmds);
	}

	public FunctionCall parseFunctionCall() throws SyntacticException, LexicalException{
		Identifier id = null;
		ArrayList<Identifier> ids = null;
		
		accept(TokenType.CALL);
		if(this.currentToken.getKind() == TokenType.IDENTIFIER){
			id = new Identifier(this.currentToken.getSpelling());
			acceptIt();
		} else {
			throw new SyntacticException("Accept ERROR: missing IDENTIFIER", this.currentToken);
		}

		if(this.currentToken.getKind() == TokenType.USING){
			ids = new ArrayList<Identifier>();
			acceptIt();
			if(this.currentToken.getKind() == TokenType.IDENTIFIER){
				ids.add(new Identifier(this.currentToken.getSpelling()));
				acceptIt();
			} else {
				throw new SyntacticException("Accept ERROR: missing IDENTIFIER", this.currentToken);
			}

			while(this.currentToken.getKind() == TokenType.COMMA){
				acceptIt();
				if(this.currentToken.getKind() == TokenType.IDENTIFIER){
					ids.add(new Identifier(this.currentToken.getSpelling()));
					acceptIt();
				} else {
					throw new SyntacticException("Accept ERROR: missing IDENTIFIER", this.currentToken);
				}
			}			
		}
		accept(TokenType.POINT);
		
		if(ids != null){
			return new FunctionCall(id);
		} else {
			return new FunctionCall(id, ids);
		}
	}

	public Command parseCommand() throws SyntacticException, LexicalException{
		if(this.currentToken.getKind() == TokenType.IF){
			return parseIfStatment();
		}else if(this.currentToken.getKind() == TokenType.PERFORM){
			return parseUntil();
		}else if(this.currentToken.getKind() == TokenType.ACCEPT){
			return parseAccept();
		}else if(this.currentToken.getKind() == TokenType.DISPLAY){
			return parseDisplay();
		}else if(this.currentToken.getKind() == TokenType.CALL){
			return parseFunctionCall();
		}else if(this.currentToken.getKind() == TokenType.BREAK){
			return parseBreakStatment();
		}else if(this.currentToken.getKind() == TokenType.CONTINUE){
			return parseContinueStatment();
		} else if(this.currentToken.getKind() == TokenType.RETURN){
			return parseReturn();
		} else {
			throw new SyntacticException("ERROR: Any Command found", this.currentToken);
		}
	}

	public Expression parseExpression() throws SyntacticException, LexicalException{
		if(this.currentToken.getKind() == TokenType.COMPUTE || this.currentToken.getKind() == TokenType.NUMBER){
			return parseArithmeticExpression();
		} else {
			return parseBooleanExpression();
		}
	}
	
	public BooleanExpression parseBooleanExpression() throws LexicalException, SyntacticException{
		if(this.currentToken.getKind() == TokenType.BOOL_VALUE){
			BoolValue bvalue = new BoolValue(this.currentToken.getSpelling());
			acceptIt();
			return new BooleanExpression(bvalue);
		} else {	
			BooleanExpression bexpression_l = null;
			ArithmeticExpression aexpression_l = null;
			Identifier id_l = null;
			Number numero_l = null;
			OpRelational opr = null;
			BooleanExpression bexpression_r = null;
			ArithmeticExpression aexpression_r = null;
			Identifier id_r = null;
			Number numero_r = null;
			
			accept(TokenType.L_PAR);
			
			if(this.currentToken.getKind() == TokenType.IDENTIFIER){
				id_l = new Identifier(this.currentToken.getSpelling());
				acceptIt();
			} else if(this.currentToken.getKind() == TokenType.NUMBER){
				numero_l = new Number(this.currentToken.getSpelling());
				acceptIt();
			} else if(this.currentToken.getKind() == TokenType.COMPUTE){
				aexpression_l = parseArithmeticExpression();
			} else {
				bexpression_l = parseBooleanExpression();
			}
			
			if(this.currentToken.getKind() == TokenType.OP_RELACIONAL){
				opr = new OpRelational(this.currentToken.getSpelling());
				acceptIt();
			} else {
				throw new SyntacticException("Accept ERROR: missing OP_RELACIONAL", this.currentToken);
			}
			
			if(this.currentToken.getKind() == TokenType.IDENTIFIER){
				id_r = new Identifier(this.currentToken.getSpelling());
				acceptIt();
			} else if(this.currentToken.getKind() == TokenType.NUMBER){
				numero_r = new Number(this.currentToken.getSpelling());
				acceptIt();
			} else if(this.currentToken.getKind() == TokenType.COMPUTE){
				aexpression_r = parseArithmeticExpression();
			} else {
				bexpression_r = parseBooleanExpression();
			}

			accept(TokenType.R_PAR);
			
			//Returns
			if(bexpression_r != null){
				if(bexpression_l != null){
					return new BooleanExpression(bexpression_l, opr, bexpression_r);
				} else if(aexpression_l != null){
					return new BooleanExpression(aexpression_l, opr, bexpression_r);
				} else {
					return new BooleanExpression(id_l, opr, bexpression_r);
				}
			} else if(aexpression_r != null){
				if(bexpression_l != null){
					return new BooleanExpression(bexpression_l, opr, aexpression_r);
				} else if(aexpression_l != null){
					return new BooleanExpression(aexpression_l, opr, aexpression_r);
				} else {
					return new BooleanExpression(id_l, opr, aexpression_r);
				}
			} else {
				if(bexpression_l != null){
					return new BooleanExpression(bexpression_l, opr, id_r);
				} else if(aexpression_l != null){
					return new BooleanExpression(aexpression_l, opr, id_r);
				} else {
					return new BooleanExpression(id_l, opr, id_r);
				}
			}
		}
	}
	
	public ArithmeticExpression parseArithmeticExpression() throws SyntacticException, LexicalException{
		if(this.currentToken.getKind() == TokenType.NUMBER){
			Number num = new Number(this.currentToken.getSpelling());
			acceptIt();
			return new ArithmeticExpression(num);
		} else {
			accept(TokenType.COMPUTE);
			accept(TokenType.L_PAR);
			ArithmeticParcel aparcel = parseArithmeticParcel();
			accept(TokenType.R_PAR);
			return new ArithmeticExpression(aparcel);
		}
	}

	public ArithmeticParcel parseArithmeticParcel() throws LexicalException, SyntacticException{
		ArithmeticTerm aTerm = parseArithmeticTerm();
		if(this.currentToken.getKind() == TokenType.OP_ADD){
			OpAdd op = new OpAdd(this.currentToken.getSpelling());
			acceptIt();
			ArithmeticParcel aParcel = parseArithmeticParcel();
			return new ArithmeticParcel(aTerm, op, aParcel);
		}
		return new ArithmeticParcel(aTerm);
	}
	
	public ArithmeticTerm parseArithmeticTerm() throws LexicalException, SyntacticException{
		ArithmeticFactor afactor = parseArithmeticFactor();
		if(this.currentToken.getKind() == TokenType.OP_MULT){
			OpMult op = new OpMult(this.currentToken.getSpelling());
			acceptIt();
			ArithmeticTerm aterm = parseArithmeticTerm();
			return new ArithmeticTerm(afactor, op, aterm);
		}	
		return new ArithmeticTerm(afactor);
	}

	public ArithmeticFactor parseArithmeticFactor() throws LexicalException, SyntacticException{
		
		if(this.currentToken.getKind() == TokenType.IDENTIFIER){
			Identifier id = new Identifier(this.currentToken.getSpelling());
			acceptIt();
			return new ArithmeticFactor(id);
		} else if(this.currentToken.getKind() == TokenType.NUMBER){
			Number num = new Number(this.currentToken.getSpelling());
			acceptIt();
			return new ArithmeticFactor(num);
		} else {
			accept(TokenType.L_PAR);
			ArithmeticParcel aParcel = parseArithmeticParcel();
			accept(TokenType.R_PAR);
			return new ArithmeticFactor(aParcel);
		}
	}


	public Accept parseAccept() throws SyntacticException, LexicalException{
		Identifier id = null;
		Expression exp = null;
		FunctionCall func = null;
		Identifier idIn = null;
		
		accept(TokenType.ACCEPT);
		if(this.currentToken.getKind() == TokenType.IDENTIFIER){
			id = new Identifier(this.currentToken.getSpelling());
			acceptIt();
		} else {
			throw new SyntacticException("Accept ERROR: missing IDENTIFIER", this.currentToken);
		}
		
		accept(TokenType.FROM);
		
		if(this.currentToken.getKind() == TokenType.CALL){
			func = parseFunctionCall();
		} else if(this.currentToken.getKind() == TokenType.IDENTIFIER){
			idIn = new Identifier(this.currentToken.getSpelling());
			acceptIt();
		} else {
			exp = parseExpression();
		}
		accept(TokenType.POINT);
		
		if(func != null){
			return new Accept(id, func);
		} else if(idIn != null){
			return new Accept(id, idIn);
		} else {
			return new Accept(id, exp);
		}
	}


	public IfStatement parseIfStatment() throws SyntacticException, LexicalException{
		BooleanExpression bExpression = null;
		ArrayList<Command> cmd_if = new ArrayList<Command>();
		ArrayList<Command> cmd_else = null;
		
		accept(TokenType.IF);
		bExpression = parseBooleanExpression();
		accept(TokenType.THEN);

		do{
			cmd_if.add(parseCommand());
		} while(this.currentToken.getKind() != TokenType.ELSE && this.currentToken.getKind() != TokenType.END_IF);

		if(this.currentToken.getKind() == TokenType.ELSE){
			cmd_else = new ArrayList<Command>();
			acceptIt();
			do{
				cmd_else.add(parseCommand());
			} while(this.currentToken.getKind() != TokenType.END_IF);

		}
		accept(TokenType.END_IF);
		
		if(cmd_else == null){
			return new IfStatement(bExpression, cmd_if);
		} else {
			return new IfStatement(bExpression, cmd_if, cmd_else);
		}
	}

	public Until parseUntil() throws SyntacticException, LexicalException{
		BooleanExpression bExpression = null;
		ArrayList<Command> aCmd = new ArrayList<Command>();
		
		accept(TokenType.PERFORM);
		accept(TokenType.UNTIL);
		bExpression = parseBooleanExpression();
		accept(TokenType.POINT);
		
		do{
			aCmd.add(parseCommand());
		} while(this.currentToken.getKind() != TokenType.END_PERFORM);
		
		accept(TokenType.END_PERFORM);
		
		return new Until(bExpression, aCmd);
	}

	public Display parseDisplay() throws SyntacticException, LexicalException{
		Identifier id = null;
		Expression exp = null;
		
		accept(TokenType.DISPLAY);
		if(this.currentToken.getKind() == TokenType.IDENTIFIER){
			id = new Identifier(this.currentToken.getSpelling());
			acceptIt();
		} else {
			exp = parseExpression();
		}
		accept(TokenType.POINT);
		
		if(id != null){
			return new Display(id);
		} else {
			return new Display(exp);
		}
	}

	public Return parseReturn() throws SyntacticException, LexicalException{
		Identifier id = null;
		Expression exp = null;
		
		accept(TokenType.RETURN);
		if(this.currentToken.getKind() == TokenType.IDENTIFIER){
			id = new Identifier(this.currentToken.getSpelling());
			acceptIt();
		} else {
			exp = parseExpression();
		}
		accept(TokenType.POINT);
		
		if(id != null){
			return new Return(id);
		} else {
			return new Return(exp);
		}
	}

	public Break parseBreakStatment() throws SyntacticException, LexicalException{
		accept(TokenType.BREAK);
		accept(TokenType.POINT);
		
		return new Break();
	}


	public Continue parseContinueStatment() throws SyntacticException, LexicalException{
		accept(TokenType.CONTINUE);
		accept(TokenType.POINT);
		
		return new Continue();
	}
	
}
//END of PARSER