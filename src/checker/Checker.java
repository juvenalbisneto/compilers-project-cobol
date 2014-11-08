package checker;

import util.AST.*;
import util.AST.Number;
import util.symbolsTable.IdentificationTable;

public class Checker implements Visitor{
	private IdentificationTable idTable;
	
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

	public Object visitBoolValue(BoolValue bool, Object args)
			throws SemanticException {
		
		return null;
	}

	public Object visitBreak(Break brk, Object args) throws SemanticException {
		
		return null;
	}

	public Object visitCode(Code code, Object args) throws SemanticException {
		
		return null;
	}

	public Object visitCommand(Command cmd, Object args)
			throws SemanticException {
		
		return null;
	}


	public Object visitContinue(Continue cont, Object args)
			throws SemanticException {
		
		return null;
	}

	public Object visitDisplay(Display display, Object args)
			throws SemanticException {
		
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

	public Object visitIdentifier(Identifier id, Object args)
			throws SemanticException {
		
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

	public Object visitNumber(Number number, Object args)
			throws SemanticException {
		
		return null;
	}

	public Object visitOpAdd(OpAdd opAdd, Object args) throws SemanticException {
		
		return null;
	}

	public Object visitOpMult(OpMult opMult, Object args)
			throws SemanticException {
		
		return null;
	}

	public Object visitOpRelational(OpRelational opRel, Object args)
			throws SemanticException {
		
		return null;
	}

	public Object visitProgramDiv(ProgramDiv pdiv, Object args)
			throws SemanticException {
		
		return null;
	}

	public Object visitReturn(Return rtn, Object args) throws SemanticException {
		
		return null;
	}

	public Object visitTerminal(Terminal term, Object args)
			throws SemanticException {
		
		return null;
	}

	public Object visitUntil(Until until, Object args) throws SemanticException {
		
		return null;
	}

	public Object visitVarDeclaration(VarDeclaration var, Object args)
			throws SemanticException {
		
		return null;
	}

	public Object visitVarPIC9Declaration(VarPIC9Declaration var9, Object args)
			throws SemanticException {
		
		return null;
	}

	public Object visitVarPICBOOLDeclaration(VarPICBOOLDeclaration varBool,
			Object args) throws SemanticException {
		
		return null;
	}
}
