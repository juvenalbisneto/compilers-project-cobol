package checker;

import util.AST.*;
import util.AST.AST.Types;
import util.AST.Number;
import util.symbolsTable.IdentificationTable;

public class Checker implements Visitor{
	private IdentificationTable idTable;
	private int contadorParametros = 0;
	
	public void check(Code code) throws SemanticException {
		idTable = new IdentificationTable();
		code.visit(this, null);
	}
	
	
	public Object visitAccept(Accept accept, Object args)
			throws SemanticException {
		
		return null;
	}

	public Object visitArithmeticExpression(ArithmeticExpression expression,
			Object args) throws SemanticException {
		
		return null;
	}

	public Object visitArithmeticFactor(ArithmeticFactor factor, Object args)
			throws SemanticException {
		
		return null;
	}
	
	public Object visitArithmeticParcel(ArithmeticParcel parcel, Object args)
			throws SemanticException {
		
		return null;
	}

	public Object visitArithmeticTerm(ArithmeticTerm term, Object args)
			throws SemanticException {
		
		return null;
	}

	public Object visitBooleanExpression(BooleanExpression expression,
			Object args) throws SemanticException {
		
		return null;
	}

	//OK
	public Object visitBoolValue(BoolValue bool, Object args)
			throws SemanticException {
		return "PICBOOL";
	}

	//OK
	public Object visitBreak(Break brk, Object args) throws SemanticException {
		if (!(args instanceof Until)) {
			throw new SemanticException("O comando BREAK só deve ser utilizado em loops");
		}
		return null;
	}

	//OK
	public Object visitCode(Code code, Object args) throws SemanticException {
		if (code.getGlobalDataDiv() != null) {
			code.getGlobalDataDiv().visit(this, args);
		}
		code.getProgramDiv().visit(this, args);
		
		return null;
	}

	//OK
	public Object visitCommand(Command cmd, Object args)
			throws SemanticException {
		
		if (cmd instanceof IfStatement) {
			return ((IfStatement) cmd).visit(this, args);
		} else if (cmd instanceof Until) {
			return ((Until) cmd).visit(this, args);
		} else if (cmd instanceof Accept) {
			return ((Accept) cmd).visit(this, args);
		} else if (cmd instanceof Display) {
			return ((Display) cmd).visit(this, args);
		} else if (cmd instanceof FunctionCall) {
			return ((FunctionCall) cmd).visit(this, args);
		} else if (cmd instanceof Break) {
			return ((Break) cmd).visit(this, args);
		} else if (cmd instanceof Continue) {
			return ((Continue) cmd).visit(this, args);
		} else if (cmd instanceof Return) {
			return ((Return) cmd).visit(this, args);
		}
		
		return null;
	}

	//OK
	public Object visitContinue(Continue cont, Object args)
			throws SemanticException {
		if (!(args instanceof Until)) {
			throw new SemanticException("O comando CONTINUE só deve ser utilizado em loops");
		}
		return null;
	}

	//OK
	public Object visitDisplay(Display display, Object args)
			throws SemanticException {
		if (display.getIdentifier() != null) {
			display.getIdentifier().visit(this, display);
		} else {
			display.getExpression().visit(this, display);
		}
		return null;
	}


	public Object visitExpression(Expression expression, Object args)
			throws SemanticException {
		
		return null;
	}

	public Object visitFunction(Function function, Object args)
			throws SemanticException {
		
		return null;
	}

	public Object visitFunctionCall(FunctionCall fcall, Object args)
			throws SemanticException {
		
		return null;
	}

	public Object visitGlobalDataDiv(GlobalDataDiv gdd, Object args)
			throws SemanticException {
		
		return null;
	}
	
	//OK
	public Object visitIdentifier(Identifier id, Object args)
			throws SemanticException {
		if (args instanceof VarDeclaration) { 
			//TODO Verificar se tem que separar os tipos de declaracao
			id.type = Types.VARIAVEL;
			id.tipo = ((VarDeclaration) args).getType();
			id.declaration = args;
			idTable.enter(id.spelling, (AST)args); 
		} else if (args instanceof Function) {
			id.type = Types.FUNCAO;
			id.tipo = ((Function) args).getTipoRetorno();
			id.declaration = args;
			this.contadorParametros++;
			idTable.enter(id.spelling, (AST) args);
		} else if (args instanceof ArithmeticFactor){
			if (idTable.retrieve(((ArithmeticFactor) args).getId().spelling) == null
					&& ((ArithmeticFactor) args).getId() != null) {
				throw new SemanticException("A variavel "
					+ ((ArithmeticFactor) args).getId().spelling
					+ " nao foi declarada!");
			} else {
				return ((ArithmeticFactor) args).getId().tipo;
			}
		} else {
			id.type = Types.PARAMETRO;
			id.declaration = args;
			id.tipo = ((VarDeclaration) args).getType();
			idTable.enter(id.spelling, (AST) args);
		}
		return null;
	}

	public Object visitIfStatement(IfStatement ifStatement, Object args)
			throws SemanticException {
		
		return null;
	}

	public Object visitMainProc(MainProc main, Object args)
			throws SemanticException {
		
		return null;
	}

	//OK
	public Object visitNumber(Number number, Object args)
			throws SemanticException {
		return "PIC9";
	}

	//OK
	public Object visitOpAdd(OpAdd opAdd, Object args) throws SemanticException {
		return null;
	}

	//OK
	public Object visitOpMult(OpMult opMult, Object args)
			throws SemanticException {
		return null;
	}

	//OK
	public Object visitOpRelational(OpRelational opRel, Object args)
			throws SemanticException {
		return null;
	}

	public Object visitProgramDiv(ProgramDiv pdiv, Object args)
			throws SemanticException {
		
		return null;
	}

	public Object visitReturn(Return rtn, Object args) throws SemanticException {
		if (args instanceof Function) {
			if (((Function) args).getTipoRetorno().equals("VOID")) {
				throw new SemanticException("Funcao VOID nao tem retorno");
			} else if (true) {
				
			}
		}
		return null;
	}

	public Object visitTerminal(Terminal term, Object args)
			throws SemanticException {
		
		return null;
	}

	//OK
	public Object visitUntil(Until until, Object args) throws SemanticException {
		until.getBooleanExpression().visit(this, args);
		
		idTable.openScope();
		for(Command cmd : until.getCommand()){
			if (cmd instanceof Break) {
				visitBreak((Break) cmd, args);
			} else if (cmd instanceof Continue) {
				visitContinue((Continue)cmd, args);
			} else {
				cmd.visit(this, until);
			}
		}
		idTable.closeScope();
		return null;
	}

	//OK
	public Object visitVarDeclaration(VarDeclaration var, Object args)
			throws SemanticException {
		if (var instanceof VarPIC9Declaration) {
			return ((VarPIC9Declaration) var).visit(this, args);
		} else if (var instanceof VarPICBOOLDeclaration) {
			return ((VarPICBOOLDeclaration) var).visit(this, args);
		}
		return null;
	}

	//OK
	public Object visitVarPIC9Declaration(VarPIC9Declaration var9, Object args)
			throws SemanticException {
		if(idTable.retrieve(var9.getIdentifier().spelling) != null){
			throw new SemanticException("Variavel " + var9.getIdentifier().spelling + " ja foi declarada");
		} else {
			var9.getIdentifier().visit(this, args);
		}
		return null;
	}

	//OK
	public Object visitVarPICBOOLDeclaration(VarPICBOOLDeclaration varBool,
			Object args) throws SemanticException {
		if(idTable.retrieve(varBool.getIdentifier().spelling) != null){
			throw new SemanticException("Variavel " + varBool.getIdentifier().spelling + " ja foi declarada");
		} else {
			varBool.getIdentifier().visit(this, args);
		}
		return null;
	}
}
