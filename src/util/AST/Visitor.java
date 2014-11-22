package util.AST;

import checker.SemanticException;

public interface Visitor {
	public Object visitAccept  				(Accept accept, Object args) 					throws SemanticException;
	public Object visitArithmeticExpression	(ArithmeticExpression expression, Object args)	throws SemanticException;
	public Object visitArithmeticParcel  	(ArithmeticParcel parcel, Object args) 			throws SemanticException;
	public Object visitArithmeticTerm  		(ArithmeticTerm term, Object args)				throws SemanticException;
	public Object visitArithmeticFactor  	(ArithmeticFactor factor, Object args) 			throws SemanticException;
	public Object visitBooleanExpression	(BooleanExpression expression, Object args)		throws SemanticException;
	public Object visitBoolValue			(BoolValue bool, Object args)					throws SemanticException;
	public Object visitBreak				(Break brk, Object args)						throws SemanticException;
	public Object visitCode					(Code code, Object args)						throws SemanticException;
	public Object visitContinue				(Continue cont, Object args)					throws SemanticException;
	public Object visitDisplay				(Display display, Object args)					throws SemanticException;
	public Object visitFunction				(Function function, Object args)				throws SemanticException;
	public Object visitFunctionCall			(FunctionCall fcall, Object args)				throws SemanticException;
	public Object visitGlobalDataDiv		(GlobalDataDiv gdd, Object args)				throws SemanticException;
	public Object visitIdentifier			(Identifier id, Object args)					throws SemanticException;
	public Object visitIfStatement			(IfStatement ifStatement, Object args)			throws SemanticException;
	public Object visitMainProc				(MainProc main, Object args)					throws SemanticException;
	public Object visitNumber				(Number number, Object args)					throws SemanticException;
	public Object visitOpAdd				(OpAdd opAdd, Object args)						throws SemanticException;
	public Object visitOpMult				(OpMult opMult, Object args)					throws SemanticException;
	public Object visitOpRelational			(OpRelational opRel, Object args)				throws SemanticException;
	public Object visitProgramDiv			(ProgramDiv pdiv, Object args)					throws SemanticException;
	public Object visitReturn				(Return rtn, Object args)						throws SemanticException;
	public Object visitUntil				(Until until, Object args)						throws SemanticException;
	public Object visitVarPIC9Declaration	(VarPIC9Declaration var9, Object args)			throws SemanticException;
	public Object visitVarPICBOOLDeclaration(VarPICBOOLDeclaration varBool, Object args)	throws SemanticException;
}
