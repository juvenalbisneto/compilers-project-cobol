package encoder;

import java.io.File;

import checker.SemanticException;
import util.Arquivo;
import util.AST.*;
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
		// TODO Auto-generated method stub
		this.out.println("_"+function.getID().spelling+":");
		this.out.println("push ebp");
		this.out.println("mov ebp, esp");
		this.out.println();
		
		if (function.getVarDeclarations() != null) {
			for (VarDeclaration varDecl : function.getVarDeclarations()) {
				varDecl.visit(this, null);
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
		
		this.out.println("\n; -------------------------------\n");
		this.out.println("_WinMain@16:");
		this.out.println("push ebp");
		this.out.println("mov ebp, esp");
		this.out.println();
		
		if (main.getVarDeclarations() != null) {
			for (VarDeclaration varDecl : main.getVarDeclarations()) {
				varDecl.visit(this, null);
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
		
		// TODO push do dado que vai ser printado
		this.out.println("push dword intFormat");
		this.out.println("call _printf");
		this.out.println("add esp, 8");
		this.out.println();
		
		return null;
	}
	
	public Object visitFunctionCall(FunctionCall fcall, Object args)
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
		
		ifStatement.getBooleanExpression().visit(this, null);
		this.out.println("push dword 1");
		this.out.println("pop ebx");
		this.out.println("pop eax");
		this.out.println("cmp eax, ebx");
		this.out.println("jne else_"+this.contadorIf+"_block");
		this.out.println();
		
		if (ifStatement.getCommandIF() != null) {
			for (Command cmd : ifStatement.getCommandIF()) {
				cmd.visit(this, null);
			}
		}
		
		this.out.println("jump if_"+this.contadorIf+"_final");
		this.out.println("else_"+this.contadorIf+"_block:");
		
		if (ifStatement.getCommandElse() != null) {
			for (Command cmd : ifStatement.getCommandElse()) {
				cmd.visit(this, null);
			}
		}
		
		this.out.println("if_"+this.contadorIf+"_final:");
		this.out.println();
		
		this.contadorIf++;
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
		if(args instanceof GlobalDataDiv) {
			var9.getIdentifier().memoryPosition = this.nextInstr;
			this.out.println(var9.getIdentifier().spelling + ": dd " + var9.getNumber().spelling);
			this.nextInstr += 4;
		} else if (args == null) {
			var9.getIdentifier().memoryPosition = this.nextInstr;
			this.out.println("sub esp, 4");
			this.out.println("push dword " + var9.getNumber().spelling);
			this.nextInstr += 4;
			this.out.println();
		} else {
			throw new SemanticException("Entrada invalida para a declaracao de variaveis PIC9.");
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
		} else if (args == null) {
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
			throw new SemanticException("Entrada invalida para a declaracao de variaveis PIC9.");
		}
		
		return null;
	}
	
	public Object visitNumber(Number number, Object args)
			throws SemanticException {
		return "PIC9";
	}
	public Object visitBooleanExpression(BooleanExpression expression,
			Object args) throws SemanticException {
		return "PICBOOL";
	}
}
