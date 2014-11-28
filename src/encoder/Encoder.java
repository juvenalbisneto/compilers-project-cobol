package encoder;

import java.io.File;

import checker.SemanticException;
import util.Arquivo;
import util.AST.*;
import util.AST.AST.Types;
import util.AST.Number;

public class Encoder implements Visitor {
	int contadorIf = 1, contadorUntil = 1;
	int nextInstr = 0;
	
	File arquivo = new File("/Users/juvenalbisneto/Desktop/Output/program.asm");
	Arquivo out = new Arquivo(arquivo.toString(), arquivo.toString());
	
	public void encode(AST root) throws SemanticException {
		this.out.println("extern  _printf\n");
		root.visit(this, null);
		this.out.close();
	}
	
	public Object visitCode(Code code, Object args) throws SemanticException {
		
		this.out.println("SECTION .data");
		this.out.println("intFormat: db \"%d\", 10, 0");
		if (code.getGlobalDataDiv() != null) {
			code.getGlobalDataDiv().visit(this, null);
		}
		this.out.println();
		
		code.getProgramDiv().visit(this, null);
		
		return null;
	}
	
	public Object visitGlobalDataDiv(GlobalDataDiv gdd, Object args)
			throws SemanticException {
		
		if(gdd.getVarDeclaration() != null){
			for (VarDeclaration varDecl : gdd.getVarDeclaration()) {
				varDecl.visit(this, gdd);
			}
		}
		
		return null;
	}
	
	public Object visitProgramDiv(ProgramDiv pdiv, Object args)
			throws SemanticException {
		this.out.println("SECTION .text");
		this.out.println("global _WinMain@16\n");
		
		if(pdiv.getArrayFunction() != null){
			for (Function func : pdiv.getArrayFunction()) {
				func.visit(this, null);
			}
		}
		
		pdiv.getMainProc().visit(this, null);
		
		return null;
	}
	
	public Object visitFunction(Function function, Object args)
			throws SemanticException {
		
		this.out.println("\n; ------------------------------- ;\n");
		this.out.println("_"+function.getID().spelling+":");
		this.out.println("push ebp");
		this.out.println("mov ebp, esp");
		this.out.println();
		
		if (function.getVarDeclarations() != null) {
			for (VarDeclaration varDecl : function.getVarDeclarations()) {
				varDecl.visit(this, function);
			}
		}
		
		if (function.getCommands() != null) {
			for (Command cmd : function.getCommands()) {
				cmd.visit(this, null);
			}
		}
		
		this.out.println("mov esp, ebp");
		this.out.println("pop ebp");
		this.out.println("ret");
		return null;
	}
	
	public Object visitMainProc(MainProc main, Object args)
			throws SemanticException {
		
		this.out.println("\n; ------------------------------- ;\n");
		this.out.println("_WinMain@16:");
		this.out.println("push ebp");
		this.out.println("mov ebp, esp");
		this.out.println();
		
		if (main.getVarDeclarations() != null) {
			for (VarDeclaration varDecl : main.getVarDeclarations()) {
				varDecl.visit(this, main);
			}
		}
		
		if (main.getCommands() != null) {
			for (Command cmd : main.getCommands()) {
				cmd.visit(this, null);
			}
		}
		
		this.out.println("mov esp, ebp");
		this.out.println("pop ebp");
		this.out.println("mov eax, 0");
		this.out.print("ret");

		return null;
	}
	
	public Object visitBooleanExpression(BooleanExpression expression,
			Object args) throws SemanticException {
		
		if (expression.getBooleanValue() != null) {
			if (expression.getBooleanValue().spelling.equals("TRUE")) {
				this.out.println("push dword 1");
			} else {
				this.out.println("push dword 0");
			}
		} else if (true) {
			//TODO
		}
		return null;
	}
	
	public Object visitArithmeticExpression(ArithmeticExpression expression,
			Object args) throws SemanticException {
		
		if (expression.getNumber() != null) {
			this.out.println("push dword "+expression.getNumber().spelling);
		} else if (expression.getArithmeticParcel() != null) {
			expression.getArithmeticParcel().visit(this, args);
		}
		
		return null;
	}
	
	public Object visitArithmeticParcel(ArithmeticParcel parcel, Object args)
			throws SemanticException {
		
		parcel.getArithmeticTerm().visit(this, args);
		
		if (parcel.getArithmeticParcel() != null) {
			parcel.getArithmeticParcel().visit(this, args);
			this.out.println("pop ebx");
			this.out.println("pop eax");
			
			if (parcel.getOpAdd().spelling.equals("+")) {
				this.out.println("add eax, ebx");
			} else if (parcel.getOpAdd().spelling.equals("-")) {
				this.out.println("sub eax, ebx");
			}
			
			this.out.println("push eax");
		}
		
		return null;
	}
	
	public Object visitArithmeticTerm(ArithmeticTerm term, Object args)
			throws SemanticException {
		
		term.getArithmeticFactor().visit(this, args);
		
		if (term.getArithmeticTerm() != null) {
			term.getArithmeticTerm().visit(this, args);
			
			this.out.println("pop ebx");
			this.out.println("pop eax");

			if (term.getOpMult().spelling.equals("*")) {
				this.out.println("imul eax, ebx");
			} else if (term.getOpMult().spelling.equals("/")) {
				this.out.println("div eax, ebx");
			}
			
			this.out.println("push eax");
		}
		return null;
	}
	
	public Object visitArithmeticFactor(ArithmeticFactor factor, Object args)
			throws SemanticException {
		
		if (factor.getNumber() != null) {
			this.out.println("push dword "+factor.getNumber().spelling);
		} else if (factor.getArithmeticParcel() != null) {
			factor.getArithmeticParcel().visit(this, args);
		} else if (factor.getId() != null) {
			if (factor.getId().local) {
				if (factor.getId().kind == Types.PARAMETRO) {
					this.out.println("pop dword [ ebp + "+factor.getId().memoryPosition+" ]");
				} else if (factor.getId().kind == Types.VARIAVEL) {
					this.out.println("pop dword [ ebp - "+factor.getId().memoryPosition+" ]");
				}
			} else {
				this.out.println("pop dword ["+factor.getId().spelling+"]");
			}
		}
		return null;
	}
	
	public Object visitFunctionCall(FunctionCall fcall, Object args)
			throws SemanticException {
		
		for (Identifier id : fcall.getParams()) {
			id.visit(this, fcall);
		}
		
		this.out.println("call _"+fcall.getId().spelling);
		this.out.println("add esp, " + (fcall.getParams().size() * 4));
		if (fcall.getId().type != null) {
			if (!(fcall.getId().equals("VOID"))) {
				this.out.println("push eax");
			}
		}
		// (?)
		return null;
	}
	
	public Object visitIdentifier(Identifier id, Object args)
			throws SemanticException {
		// TODO Auto-generated method stub
		System.out.println(id.spelling+" "+id.local);
		return null;
	}
	
	public Object visitIfStatement(IfStatement ifStatement, Object args)
			throws SemanticException {
		
		ifStatement.getBooleanExpression().visit(this, null);
		
		this.out.println("push dword 1");
		this.out.println("pop ebx");
		this.out.println("pop eax");
		this.out.println("cmp eax, ebx");
		this.out.println("jne else_"+this.contadorIf+"_begin");
		
		if (ifStatement.getCommandIF() != null) {
			for (Command cmd : ifStatement.getCommandIF()) {
				cmd.visit(this, args);
			}
		}
		
		this.out.println("jmp if_"+this.contadorIf+"_end");
		this.out.println("else_"+this.contadorIf+"_begin:");
		
		if (ifStatement.getCommandElse() != null) {
			for (Command cmd : ifStatement.getCommandElse()) {
				cmd.visit(this, args);
			}
		}
		
		this.out.println("if_"+this.contadorIf+"_end:");
		
		this.contadorIf++;
		return null;
	}
	
	public Object visitReturn(Return rtn, Object args) throws SemanticException {

		if (rtn.getIdentifier() != null) {
			rtn.getIdentifier().visit(this, rtn);
		} else if (rtn.getExpression() != null) {
			rtn.getExpression().visit(this, rtn);
		}
		
		this.out.println("pop dword eax");
		this.out.println("mov esp, ebp");
		this.out.println("pop ebp");
		this.out.println("ret");
		
		return null;
	}
	
	public Object visitUntil(Until until, Object args) throws SemanticException {
		
		until.contadorUntil = this.contadorUntil;
		this.out.println("until_"+until.contadorUntil+"_begin:");
		
		until.getBooleanExpression().visit(this, null);
		
		this.out.println("push dword 1");
		this.out.println("pop dword ebx");
		this.out.println("pop dword eax");
		this.out.println("cmp eax, ebx");
		this.out.println("jne until_"+until.contadorUntil+"_end");
		
		if (until.getCommand() != null) {
			for (Command cmd : until.getCommand()) {
				cmd.visit(this, until);
			}
		}
		
		this.out.println("jmp until_"+until.contadorUntil+"_begin");
		this.out.println("until_"+until.contadorUntil+"_end:");
		
		this.contadorUntil++;
		return null;
	}
	public Object visitContinue(Continue cont, Object args)
			throws SemanticException {
		if(args instanceof Until){
			this.out.println("jmp until_"+((Until)args).contadorUntil+"_begin");
		} else {
			throw new SemanticException("[Encoder] Erro no CONTINUE.");
		}
		return null;
	}
	public Object visitBreak(Break brk, Object args) throws SemanticException {
		if(args instanceof Until){
			this.out.println("jmp until_"+((Until)args).contadorUntil+"_end");
		} else {
			throw new SemanticException("[Encoder] Erro no BREAK.");
		}
		return null;
	}
	
	public Object visitDisplay(Display display, Object args)
			throws SemanticException {
		
		if (display.getIdentifier() != null) {
			display.getIdentifier().visit(this, display);
		} else if(display.getExpression() != null) {
			display.getExpression().visit(this, display);
		} else {
			throw new SemanticException("[Encoder] Erro no DISPLAY.");
		}
		
		this.out.println("push dword intFormat");
		this.out.println("call _printf");
		this.out.println("add esp, 8");
		
		return null;
	}

	public Object visitVarPIC9Declaration(VarPIC9Declaration var9, Object args)
			throws SemanticException {
		if(args instanceof GlobalDataDiv) {
			var9.getIdentifier().memoryPosition = this.nextInstr;
			this.out.println(var9.getIdentifier().spelling + ": dd " + var9.getNumber().spelling);
			this.nextInstr += 4;
		} else if (args instanceof Function || args instanceof MainProc) {
			var9.getIdentifier().memoryPosition = this.nextInstr;
			this.out.println("sub esp, 4");
			this.out.println("push dword " + var9.getNumber().spelling);
			this.nextInstr += 4;
			this.out.println();
		} else {
			throw new SemanticException("[Encoder] Entrada invalida para a declaracao de variaveis PIC9.");
		}
		return null;
	}
	public Object visitVarPICBOOLDeclaration(VarPICBOOLDeclaration varBool,
			Object args) throws SemanticException {
		if(args instanceof GlobalDataDiv) {
			varBool.getIdentifier().memoryPosition = this.nextInstr;
			this.out.print(varBool.getIdentifier().spelling + ": dd ");
			if(varBool.getBoolValue().spelling.equals("TRUE")){
				this.out.println("1");
			} else {
				this.out.println("0");
			}
			this.nextInstr += 4;
		} else if (args instanceof Function || args instanceof MainProc) {
			varBool.getIdentifier().memoryPosition = this.nextInstr;
			this.out.println("sub esp, 4");
			this.out.print("push dword ");
			if(varBool.getBoolValue().spelling.equals("TRUE")){
				this.out.println("1");
			} else {
				this.out.println("0");
			}
			this.nextInstr += 4;
			this.out.println();
		} else {
			throw new SemanticException("[Encoder] Entrada invalida para a declaracao de variaveis PIC9.");
		}
		
		return null;
	}
	
	public Object visitAccept(Accept accept, Object args)
			throws SemanticException {
		// TODO Auto-generated method stub
		if (accept.getIdIn() != null) {
			accept.getIdIn().visit(this, accept);
		} else if (accept.getExpression() != null) {
			accept.getExpression().visit(this, null);
		} else if (accept.getFunctionCall() != null) {
			accept.getFunctionCall().visit(this, null);
		}
		
		if (accept.getIdentifier().local) {
			if (accept.getIdentifier().kind == Types.PARAMETRO) {
				this.out.println("pop dword [ ebp + "+accept.getIdentifier().memoryPosition+" ]");
			} else if (accept.getIdentifier().kind == Types.VARIAVEL) {
				this.out.println("pop dword [ ebp - "+accept.getIdentifier().memoryPosition+" ]");
			}
		} else {
			this.out.println("pop dword ["+accept.getIdentifier().spelling+"]");
		}
		return null;
	}
	
	public Object visitNumber(Number number, Object args)
			throws SemanticException {
		return "PIC9";
	}
	public Object visitBoolValue(BoolValue bool, Object args)
			throws SemanticException {
		return "PICBOOL";
	}
	
	public Object visitOpAdd(OpAdd opAdd, Object args) throws SemanticException {
		return null;
	}
	public Object visitOpMult(OpMult opMult, Object args) throws SemanticException {
		return null;
	}
	public Object visitOpRelational(OpRelational opRel, Object args) throws SemanticException {
		return null;
	}
}
