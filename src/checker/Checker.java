package checker;

import java.util.ArrayList;

import util.AST.*;
import util.AST.AST.Types;
import util.AST.Number;
import util.symbolsTable.IdentificationTable;

public class Checker implements Visitor{
	private IdentificationTable idTable;
	private int numRetornos = 0;
	
	public void check(Code code) throws SemanticException {
		idTable = new IdentificationTable();
		code.visit(this, null);
	}
	
	public Object visitAccept(Accept accept, Object args)
			throws SemanticException {
		
		Object temp = idTable.retrieve(accept.getIdentifier().spelling);
		if(temp == null){
			throw new SemanticException("Variavel " + accept.getIdentifier().spelling + " nao declarada!");
		} else {
			if (accept.getIdIn() != null) {
				VarDeclaration input = ((VarDeclaration)idTable.retrieve(accept.getIdIn().spelling));
				if(!input.getType().equals(((VarDeclaration)temp).getType())){
					throw new SemanticException(
							"Tipos incompativeis. O valor atribuido nao eh do tipo da variavel!");
				}
			} else if (accept.getFunctionCall() != null) {
				Function func = (Function)idTable.retrieve(accept.getFunctionCall().getId().spelling);
				if(!func.getTipoRetorno().equals(((VarDeclaration)temp).getType())){
					throw new SemanticException(
							"Tipos incompativeis. O valor atribuido nao eh do tipo da variavel!");
				}
			} else if (accept.getExpression() != null) {
				if(!accept.getExpression().visit(this, args).equals(((VarDeclaration)temp).getType())){
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
		if (expression.getNumber() != null) {
			return expression.getNumber().visit(this, args);
		} else {
			return expression.getArithmeticParcel().visit(this, args);
		}
	}
	
	public Object visitArithmeticParcel(ArithmeticParcel parcel, Object args)
			throws SemanticException {
		ArithmeticTerm term = parcel.getArithmeticTerm();
		ArithmeticParcel aExp = parcel.getArithmeticParcel();
		Object temp = term.visit(this, args);
		if (aExp == null) {
			return term.visit(this, args);
		} else if (aExp.visit(this, args) != null && aExp.visit(this, args).equals(temp)) {
			return temp;
		} else
			throw new SemanticException("Tipos incompativeis ( "
					+ term.visit(this, args) + " - "
					+ aExp.visit(this, args) + " )");
	}

	public Object visitArithmeticTerm(ArithmeticTerm term, Object args)
			throws SemanticException {
		ArithmeticTerm termo = term.getArithmeticTerm();
		ArithmeticFactor fac = term.getArithmeticFactor();
		Object temp = fac.visit(this, args);
		if (termo == null) {
			return fac.visit(this, args);
		} else if (termo.visit(this, args) != null && termo.visit(this, args).equals(temp)) {
			return temp;
		} else {
			throw new SemanticException("Tipos incompativeis ( "
					+ fac.visit(this, args) + " - "
					+ termo.visit(this, args) + " )");
		}
	}
	
	public Object visitArithmeticFactor(ArithmeticFactor factor, Object args)
			throws SemanticException {
		if (factor.getId() != null) {
			return factor.getId().visit(this, factor);
		} else if (factor.getNumber() != null) {
			return "PIC9";
		} else if (factor.getArithmeticParcel() != null) {
			return factor.getArithmeticParcel().visit(this, args);
		} else {
			throw new SemanticException("Erro no visitArithmeticFactor");
		}
	}

	public Object visitBooleanExpression(BooleanExpression expression,
			Object args) throws SemanticException {
		
		if (expression.getBooleanExpression_l() != null || 
				(expression.getIdentifier_l() != null 
						&& ((VarDeclaration)idTable.retrieve(expression.getIdentifier_l().spelling) instanceof VarPICBOOLDeclaration) 
						&& ((VarPICBOOLDeclaration)idTable.retrieve(expression.getIdentifier_l().spelling)).getType().equals("PICBOOL"))) {
			
			if(expression.getOpRelational().spelling.equals("=") || expression.getOpRelational().spelling.equals("<>")){
				if (expression.getBooleanExpression_r() != null){
					return "PICBOOL";
				} else if (expression.getIdentifier_r() != null 
						&& expression.getIdentifier_r().type.equals("PICBOOL")) {
					return "PICBOOL";
				} else {
					throw new SemanticException("Erro! Nao se pode comparar um booleano com um numero");
				}
			} else {
				throw new SemanticException("Erro! Tipo de operador invalido");
			}
		} else if (expression.getArithmeticExpression_l() != null || 
				(expression.getIdentifier_l() != null 
				&& ((VarDeclaration)idTable.retrieve(expression.getIdentifier_l().spelling) instanceof VarPIC9Declaration) 
				&& ((VarPIC9Declaration)idTable.retrieve(expression.getIdentifier_l().spelling)).getType().equals("PIC9"))) {
			
			if (expression.getArithmeticExpression_r() != null
				&& expression.getArithmeticExpression_r().visit(this, args).equals("PIC9")) {
				return "PICBOOL";
			} else if (expression.getIdentifier_r() != null 
					&& expression.getIdentifier_r().type.equals("PIC9")) {
				return "PICBOOL";
			} else {
				throw new SemanticException("Erro! Nao se pode comparar um numero com um booleano");
			}
		}
		if(expression.getBooleanValue() != null){
			return "PICBOOL";
		}
		
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

	//TODO Analisar parte comentada pra ver se quer essa regra (nao ter comandos apos um retorno)
	public Object visitFunction(Function function, Object args)
			throws SemanticException {
		function.getID().visit(this, function);
		idTable.openScope();
		this.numRetornos = 0;
		
		if(function.getParams() != null)
			for (Identifier param : function.getParams()) {
				param.visit(this, args);
			}
		if(function.getVarDeclarations() != null)
			for (VarDeclaration vDec : function.getVarDeclarations()) {
				vDec.visit(this, function);
			}
		
		Return ret = null;
//		ArrayList<Command> cmds = new ArrayList<Command>();
//		for (Command cmd : function.getCommands()) {
//			if (cmd instanceof Return) {
//				temp = cmd;
//				cmds.add(cmd);
//				break;
//			} else
//				cmds.add(cmd);
//		}

//		if (cmds.size() != function.getCommands().size())
//			throw new SemanticException(
//					"Nao deve haver comandos apos o retorno do procedimentos ou funcoes! [Regra extra]");
		
		ArrayList<Command> comandosInternos = new ArrayList<Command>();
		
		if(function.getCommands() != null)
			for (Command cmd : function.getCommands()) {
				if (cmd instanceof Return){
					ret = (Return) cmd;
					this.numRetornos++;
				} else if(cmd instanceof Until || cmd instanceof IfStatement){
					comandosInternos.add(cmd);
				}
				cmd.visit(this, function);
			}
		
		if (this.numRetornos == 0 && ret == null && function.getTipoRetorno() != null && !function.getTipoRetorno().equals("VOID")) {
			throw new SemanticException("A Funcao " + function.getID().spelling
					+ " precisa retornar um valor do tipo "
					+ function.getTipoRetorno());
		}
		
		idTable.closeScope();
		return null;
	}

	public Object visitFunctionCall(FunctionCall fcall, Object args)
			throws SemanticException {
		Object func = idTable.retrieve(fcall.getId().spelling);
		
		if (func == null) {
			throw new SemanticException("A funcao " 
					+ fcall.getId().spelling
					+ " nao foi declarada!");
		} else {
			if (!(func instanceof Function)) {
				throw new SemanticException("Identificador "
						+ fcall.getId().spelling
						+ " nao representa uma Funcao!");
			} else {
				ArrayList<String> paramsTypesFunc = ((Function) func).getParamsTypes();
				ArrayList<Identifier> paramsCall = fcall.getParams();
				
				if (paramsTypesFunc.size() != paramsCall.size()) {
					throw new SemanticException(
							"Quantidade de parametros passada diferente da quantidade de parametros requeridas pela Funcao!");
				} else if(paramsTypesFunc.size() > 0){
					for (int i = 0; i < paramsTypesFunc.size(); i++) {
						if (!paramsTypesFunc.get(i).equals( ((VarDeclaration)idTable.retrieve(paramsCall.get(i).spelling)).getType() )) {
							throw new SemanticException("Tipo dos parametros informados não correspondem ao tipo esperado");
						}
					}
				}
			}
		}
		return null;
	}

	public Object visitGlobalDataDiv(GlobalDataDiv gdd, Object args)
			throws SemanticException {
		if(gdd.getVarDeclaration() != null)
			for (VarDeclaration vDec : gdd.getVarDeclaration()) {
				vDec.visit(this, null);
			}
		return null;
	}

	public Object visitIdentifier(Identifier id, Object args)
			throws SemanticException {
		if (args instanceof VarPIC9Declaration || args instanceof VarPICBOOLDeclaration) { 
			id.kind = Types.VARIAVEL;
			id.type = ((VarDeclaration) args).getType();
			id.declaration = args;
			idTable.enter(id.spelling, (AST)args);
		} else if (args instanceof Function) {
			id.kind = Types.FUNCAO;
			id.type = ((Function) args).getTipoRetorno();
			id.declaration = args;
			idTable.enter(id.spelling, (AST) args);
		} else if (args instanceof ArithmeticFactor){
			Object temp = idTable.retrieve(((ArithmeticFactor) args).getId().spelling);
			if (temp == null && ((ArithmeticFactor) args).getId() != null) {
				throw new SemanticException("A variavel "
					+ ((ArithmeticFactor) args).getId().spelling
					+ " nao foi declarada!");
			} else {
				if(temp instanceof VarPIC9Declaration) {
					return ((VarPIC9Declaration) temp).getType();
				} else {
					return ((VarPICBOOLDeclaration) temp).getType();
				}
			}
		}
		return null;
	}

	public Object visitIfStatement(IfStatement ifStatement, Object args)
			throws SemanticException {
		
		Object temp = null;
		
		if(ifStatement.getBooleanExpression() instanceof BooleanExpression){
			((BooleanExpression) ifStatement.getBooleanExpression()).visit(this, args);
			idTable.openScope();
			if(ifStatement.getCommandIF() != null)
				for (Command ifCmd : ifStatement.getCommandIF()) {
					if (ifCmd instanceof Break) {
						break;
					} else
						temp = ifCmd.visit(this, args);
				}
			idTable.closeScope();
			idTable.openScope();
			if(ifStatement.getCommandElse() != null)
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
		idTable.openScope();

		if(main.getVarDeclarations() != null){
			for (VarDeclaration vDec : main.getVarDeclarations()) {
				vDec.visit(this, main);
			}
		}

		if(main.getCommands() != null)
			for (Command cmd : main.getCommands()) {
				if (cmd instanceof Return) {
					throw new SemanticException("Erro! A Main nao deve possuir retorno");
				} else {
					cmd.visit(this, main);
				}
			}
		
		idTable.closeScope();
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
		if(pdiv.getArrayFunction() != null){
			for (Function function : pdiv.getArrayFunction()) {
				function.visit(this, null);
			}
		}
		pdiv.getMainProc().visit(this, null);

		return null;
	}

	public Object visitReturn(Return rtn, Object args) throws SemanticException {
		this.numRetornos++;
		if (args instanceof Function) {
			if (((Function) args).getTipoRetorno() != null && ((Function) args).getTipoRetorno().equals("VOID")) {
				throw new SemanticException("Funcao VOID nao tem retorno");
			} else if (args instanceof Function && args instanceof ArithmeticExpression && ((ArithmeticExpression) args).getArithmeticParcel() == null) {
				if (rtn.getExpression().visit(this, args) != null && !(rtn.getExpression().visit(this, args).equals(((Function) args).getTipoRetorno()))) {
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
			} else if (rtn.getExpression() != null && !(rtn.getExpression().visit(this, args).equals(((Function) args).getTipoRetorno()))) {
				throw new SemanticException(
						"Valor retornado incompativel com o tipo de retorno da funcao!");
			}
		} else {
			throw new SemanticException(
					"Comando de retorno deve estar dentro de uma funcao!");
		}
		return null;
	}

	public Object visitUntil(Until until, Object args) throws SemanticException {
		until.getBooleanExpression().visit(this, args);

		idTable.openScope();
		if(until.getCommand() != null)
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

	public Object visitVarPIC9Declaration(VarPIC9Declaration var9, Object args)
			throws SemanticException {
		if(idTable.retrieve(var9.getIdentifier().spelling) != null){
			throw new SemanticException("Variavel " + var9.getIdentifier().spelling + " ja foi declarada");
		} else {
			var9.getIdentifier().visit(this, var9);
		}
		return null;
	}

	public Object visitVarPICBOOLDeclaration(VarPICBOOLDeclaration varBool,
			Object args) throws SemanticException {
		if(idTable.retrieve(varBool.getIdentifier().spelling) != null){
			throw new SemanticException("Variavel " + varBool.getIdentifier().spelling + " ja foi declarada");
		} else {
			varBool.getIdentifier().visit(this, varBool);
		}
		return null;
	}
}
