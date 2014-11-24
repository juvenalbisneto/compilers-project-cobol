package encoder;

import java.io.File;

import checker.SemanticException;
import util.Arquivo;
import util.AST.*;
import util.AST.Number;

public class Encoder implements Visitor {
	
	int contadorIf = 1, contadorElse = 1, contadorTest = 1, contadorWhile = 1;
	int contadorDesvioCondicional = 1;
	int nextInstr = 0;
	
	File arquivo = new File("/Users/juvenalbisneto/Desktop/Output/output.asm");
	Arquivo out = new Arquivo(arquivo.toString(), arquivo.toString());
	
	public void encode(AST code) throws SemanticException {
		this.out.println("extern  _printf");
		code.visit(this, null);
		this.out.close();
	}
	
	public Object visitCode(Code code, Object args) throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visitAccept(Accept accept, Object args)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visitArithmeticExpression(ArithmeticExpression expression,
			Object args) throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visitArithmeticParcel(ArithmeticParcel parcel, Object args)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visitArithmeticTerm(ArithmeticTerm term, Object args)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visitArithmeticFactor(ArithmeticFactor factor, Object args)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visitBooleanExpression(BooleanExpression expression,
			Object args) throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visitBoolValue(BoolValue bool, Object args)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Object visitBreak(Break brk, Object args) throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visitContinue(Continue cont, Object args)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visitDisplay(Display display, Object args)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visitFunction(Function function, Object args)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visitFunctionCall(FunctionCall fcall, Object args)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitGlobalDataDiv(GlobalDataDiv gdd, Object args)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visitIdentifier(Identifier id, Object args)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visitIfStatement(IfStatement ifStatement, Object args)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visitMainProc(MainProc main, Object args)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visitNumber(Number number, Object args)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visitOpAdd(OpAdd opAdd, Object args) throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visitOpMult(OpMult opMult, Object args)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visitOpRelational(OpRelational opRel, Object args)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visitProgramDiv(ProgramDiv pdiv, Object args)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visitReturn(Return rtn, Object args) throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object visitUntil(Until until, Object args) throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitVarPIC9Declaration(VarPIC9Declaration var9, Object args)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitVarPICBOOLDeclaration(VarPICBOOLDeclaration varBool,
			Object args) throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

}
