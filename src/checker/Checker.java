package checker;

import java.util.ArrayList;

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
		if(idTable.retrieve(accept.getIdentifier().spelling) == null){
			throw new SemanticException("Variavel " + accept.getIdentifier().spelling + " nao declarada!");
		} else {
			if (accept.getIdIn() != null) {
				if(!accept.getIdIn().visit(this, args).equals(accept.getIdentifier().type)){
					throw new SemanticException(
							"Tipos incompativeis. O valor atribuido nao eh do tipo da variavel!");
				}
			} else if (accept.getFunctionCall() != null) {
				if(!accept.getFunctionCall().visit(this, args).equals(accept.getIdentifier().type)){
					throw new SemanticException(
							"Tipos incompativeis. O valor atribuido nao eh do tipo da variavel!");
				}
			} else if (accept.getExpression() != null) {
				if(!accept.getFunctionCall().visit(this, args).equals(accept.getIdentifier().type)){
					throw new SemanticException(
							"Tipos incompativeis. O valor atribuido nao eh do tipo da variavel!");
				}
			} else {
				throw new SemanticException("Valor nao informado!");
			}
			
		}
		
		return null;
	}

	public Object visitArithmeticExpression(ArithmeticExpression expression,
			Object args) throws SemanticException {
		//TODO
		return null;
	}
	
	public Object visitArithmeticParcel(ArithmeticParcel parcel, Object args)
			throws SemanticException {
		//TODO
		return null;
	}

	public Object visitArithmeticFactor(ArithmeticFactor factor, Object args)
			throws SemanticException {
		//TODO
		return null;
	}

	public Object visitArithmeticTerm(ArithmeticTerm term, Object args)
			throws SemanticException {
		//TODO
		return null;
	}

	public Object visitBooleanExpression(BooleanExpression expression,
			Object args) throws SemanticException {
		//TODO
		return null;
	}

	public Object visitBoolValue(BoolValue bool, Object args)
			throws SemanticException {
		return "PICBOOL";
	}

	public Object visitBreak(Break brk, Object args) throws SemanticException {
		if (!(args instanceof Until)) {
			throw new SemanticException("O comando BREAK só deve ser utilizado em loops");
		}
		return null;
	}

	public Object visitCode(Code code, Object args) throws SemanticException {
		if (code.getGlobalDataDiv() != null) {
			code.getGlobalDataDiv().visit(this, args);
		}
		code.getProgramDiv().visit(this, args);
		
		return null;
	}

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

	public Object visitContinue(Continue cont, Object args)
			throws SemanticException {
		if (!(args instanceof Until)) {
			throw new SemanticException("O comando CONTINUE só deve ser utilizado em loops");
		}
		return null;
	}

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
		//TODO
		return null;
	}

	//TODO Analisar parte comentada pra ver se quer essa regra
	public Object visitFunction(Function function, Object args)
			throws SemanticException {
		function.getID().visit(this, function);
		idTable.openScope();
		
		for (Identifier param : function.getParams()) {
			param.visit(this, args);
		}
		Object temp = null;
		ArrayList<Command> cmds = new ArrayList<Command>();
		for (Command cmd : function.getCommands()) {
			if (cmd instanceof Return) {
				temp = cmd;
				cmds.add(cmd);
//				break;
			} else
				cmds.add(cmd);
		}

//		if (cmds.size() != function.getCommands().size())
//			throw new SemanticException(
//					"Nao deve haver comandos apos o retorno do procedimentos ou funcoes! [Regra extra]");
		for (Command cmd : function.getCommands()) {
			if (temp != null) {
				if (temp instanceof Return)
					cmd.visit(this, function);
				else
					temp = cmd.visit(this, function);
			} else
				temp = cmd.visit(this, function);
		}

		if (temp == null && ((!function.getTipoRetorno().equals("VOID")))) {
			throw new SemanticException("A Funcao " + function.getID().spelling
					+ " precisa retornar um valor do tipo "
					+ function.getTipoRetorno());
		}
		
		idTable.closeScope();
		return null;
	}

	public Object visitFunctionCall(FunctionCall fcall, Object args)
			throws SemanticException {
		if (idTable.retrieve(fcall.getId().spelling) == null) {
			throw new SemanticException("A funcao " 
					+ fcall.getId().spelling
					+ " nao foi declarada!");
		} else {
			AST temp = idTable.retrieve(fcall.getId().spelling);
			if (!(temp instanceof Function)) {
				throw new SemanticException("Identificador "
						+ fcall.getId().spelling
						+ " nao representa uma Funcao!");
			} else {
				if (((Function) temp).getParamsTypes().size() != fcall.getParams().size()) {
					throw new SemanticException(
							"Quantidade de parametros passada diferente da quantidade de parametros requeridas pela Funcao!");
				} else {
					ArrayList<String> params = ((Function) temp).getParamsTypes();
					for (int i = 0; i < params.size(); i++) {
						if (!params.get(i).equals(fcall.getParams().get(i).type)) {
							throw new SemanticException(
									"Tipo dos parametros informados não correspondem ao tipo esperado");
						}
					}
				}
			}
		}
		return null;
	}

	public Object visitGlobalDataDiv(GlobalDataDiv gdd, Object args)
			throws SemanticException {
		for (VarDeclaration vDec : gdd.getVarDeclaration()) {
			vDec.visit(this, null);
		}
		return null;
	}
	
	public Object visitIdentifier(Identifier id, Object args)
			throws SemanticException {
		if (args instanceof VarDeclaration) { 
			id.kind = Types.VARIAVEL;
			id.type = ((VarDeclaration) args).getType();
			id.declaration = args;
			idTable.enter(id.spelling, (AST)args); 
		} else if (args instanceof Function) {
			id.kind = Types.FUNCAO;
			id.type = ((Function) args).getTipoRetorno();
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
				return ((ArithmeticFactor) args).getId().type;
			}
		} else {
			id.kind = Types.PARAMETRO;
			id.declaration = args;
			id.type = ((VarDeclaration) args).getType();
			idTable.enter(id.spelling, (AST) args);
		}
		return null;
	}

	public Object visitIfStatement(IfStatement ifStatement, Object args)
			throws SemanticException {
		
		Object temp = null;
		
		if(ifStatement.getBooleanExpression() instanceof BooleanExpression){
			((BooleanExpression) ifStatement.getBooleanExpression()).visit(this, args);
			idTable.openScope();
			for (Command ifCmd : ifStatement.getCommandIF()) {
				if (ifCmd instanceof Break) {
					break;
				} else
					temp = ifCmd.visit(this, args);
			}
			idTable.closeScope();
			
			for (Command elseCmd : ifStatement.getCommandElse()) {
				if (elseCmd instanceof Break) {
					break;
				} else {
					elseCmd.visit(this, args);
				}
			}
			idTable.closeScope();
		} else {
			throw new SemanticException("Expressao da condicao do IF nao e booleana!");
		}
		
		return temp;
	}

	public Object visitMainProc(MainProc main, Object args)
			throws SemanticException {
		//TODO
		return null;
	}

	public Object visitNumber(Number number, Object args)
			throws SemanticException {
		return "PIC9";
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
		//TODO
		return null;
	}

	public Object visitReturn(Return rtn, Object args) throws SemanticException {
		if (args instanceof Function) {
			if (((Function) args).getTipoRetorno().equals("VOID")) {
				throw new SemanticException("Funcao VOID nao tem retorno");
			} else if (args instanceof Function && ((ArithmeticExpression) args).getArithmeticParcel() == null) {
				if (!(rtn.getExpression().visit(this, args).equals(((Function) args).getTipoRetorno()))) {
					throw new SemanticException(
							"Valor retornado incompativel com o tipo de retorno da funcao!");
				} else {
					Identifier id = ((ArithmeticParcel) args).getArithmeticTerm().getArithmeticFactor()
							.getId();
					AST temp = idTable.retrieve(id.spelling);
					if (temp == null) {
						throw new SemanticException("A variavel " + id.spelling
								+ " nao foi declarada!");
					}
				}
			} else if (!(rtn.getExpression().visit(this, args).equals(((Function) args).getTipoRetorno()))) {
				throw new SemanticException(
						"Valor retornado incompativel com o tipo de retorno da funcao!");
			}
		} else {
			throw new SemanticException(
					"Comando de retorno deve estar dentro de uma funcao!");
		}
		return null;
	}

	public Object visitTerminal(Terminal term, Object args)
			throws SemanticException {
		//TODO
		return null;
	}

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

	public Object visitVarDeclaration(VarDeclaration var, Object args)
			throws SemanticException {
		if (var instanceof VarPIC9Declaration) {
			return ((VarPIC9Declaration) var).visit(this, args);
		} else if (var instanceof VarPICBOOLDeclaration) {
			return ((VarPICBOOLDeclaration) var).visit(this, args);
		}
		return null;
	}

	public Object visitVarPIC9Declaration(VarPIC9Declaration var9, Object args)
			throws SemanticException {
		if(idTable.retrieve(var9.getIdentifier().spelling) != null){
			throw new SemanticException("Variavel " + var9.getIdentifier().spelling + " ja foi declarada");
		} else {
			var9.getIdentifier().visit(this, args);
		}
		return null;
	}

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
