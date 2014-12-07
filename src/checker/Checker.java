package checker;

import java.util.ArrayList;
import util.AST.*;
import util.AST.AST.Types;
import util.AST.Number;
import util.symbolsTable.IdentificationTable;

public class Checker implements Visitor{
	private IdentificationTable idTable;
	private int numRetornos = 0;
	private int contadorParametros = 0;
	private Object transmit = null;

	public void check(Code code) throws SemanticException {
		idTable = new IdentificationTable();
		code.visit(this, null);
	}

	public Object visitCode(Code code, Object args) throws SemanticException {
		if (code.getGlobalDataDiv() != null) {
			code.getGlobalDataDiv().visit(this, args);
		}
		code.getProgramDiv().visit(this, args);

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

	public Object visitMainProc(MainProc main, Object args)
			throws SemanticException {
		idTable.openScope();
		this.numRetornos = 0;

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

	public Object visitFunction(Function function, Object args)
			throws SemanticException {
		
		function.getID().visit(this, function);
		idTable.openScope();
		this.numRetornos = 0;

		if(function.getParams() != null)
			for (int i = 0; i < function.getParams().size(); i++) {
				this.transmit = function.getParamsTypes().get(i);
				function.getParams().get(i).visit(this, function);
			}
		this.contadorParametros = 0;
		
		if(function.getVarDeclarations() != null)
			for (VarDeclaration vDec : function.getVarDeclarations()) {
				vDec.visit(this, function);
			}

		Return ret = null;
		if(function.getCommands() != null)
			for (Command cmd : function.getCommands()) {
				if (cmd instanceof Return){
					ret = (Return) cmd;
					this.numRetornos++;
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

	public Object visitAccept(Accept accept, Object args)
			throws SemanticException {

		Object temp = idTable.retrieve(accept.getIdentifier().spelling);
		
		if (temp instanceof VarDeclaration) {
			accept.setIdentifier(((VarDeclaration)temp).getIdentifier());
		} else if (temp instanceof Identifier) {
			accept.setIdentifier((Identifier)temp);
		}
		
		if(temp == null){
			throw new SemanticException("Variavel " + accept.getIdentifier().spelling + " nao declarada!");
		} else {
			if (accept.getIdIn() != null) {
				Object input = idTable.retrieve(accept.getIdIn().spelling);
				if (input instanceof VarDeclaration) {
					accept.setIdIn(((VarDeclaration)input).getIdentifier());
					if(!((VarDeclaration)input).getType().equals(accept.getIdentifier().type)){
						throw new SemanticException(
								"Tipos incompativeis. O valor atribuido nao eh do tipo da variavel!");
					}
				} else if (input instanceof Identifier) {
					accept.setIdIn((Identifier)input);
					if(!((Identifier)input).type.equals(accept.getIdentifier().type)){
						throw new SemanticException(
								"Tipos incompativeis. O valor atribuido nao eh do tipo da variavel!");
					}
				}
				
			} else if (accept.getFunctionCall() != null) {
				Object func = idTable.retrieve(accept.getFunctionCall().getId().spelling);
				if (func == null){
					throw new SemanticException("Funcao nao declarada!");
				} else if(!(func instanceof Function)){
					throw new SemanticException("O nome informado nao pertence a uma funcao.");
				} else if (!((Function)func).getTipoRetorno().equals(accept.getIdentifier().type)){
					throw new SemanticException(
							"Tipos incompativeis. O valor atribuido nao eh do tipo da variavel!");
				}
				accept.getFunctionCall().visit(this, args);
			} else if (accept.getExpression() != null) {
				if(!accept.getExpression().visit(this, args).equals(accept.getIdentifier().type)){
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
		} else if (expression.getArithmeticParcel() != null){
			return expression.getArithmeticParcel().visit(this, args);
		} else
			throw new SemanticException("Expressao invalida.");
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
			throw new SemanticException("Tipos incompativeis ( " + term.visit(this, args) + " - " + aExp.visit(this, args) + " )");
	}

	public Object visitArithmeticTerm(ArithmeticTerm term, Object args)
			throws SemanticException {
		ArithmeticTerm termo = term.getArithmeticTerm();
		ArithmeticFactor fac = term.getArithmeticFactor();
		Object temp = fac.visit(this, args);
		if (termo == null) {
			return temp;
		} else if (termo.visit(this, args) != null && termo.visit(this, args).equals(temp)) {
			return temp;
		} else {
			throw new SemanticException("Tipos incompativeis ( " + fac.visit(this, args) + " - " + termo.visit(this, args) + " )");
		}
	}

	public Object visitArithmeticFactor(ArithmeticFactor factor, Object args)
			throws SemanticException {
		if (factor.getId() != null) {
			Object id = idTable.retrieve(factor.getId().spelling);
			if(id instanceof VarPIC9Declaration){
				factor.setId(((VarPIC9Declaration)id).getIdentifier());
				return "PIC9";
			} else if (id instanceof VarPICBOOLDeclaration){
				factor.setId(((VarPICBOOLDeclaration)id).getIdentifier());
				return "PICBOOL";
			} else if (id instanceof Identifier && ( ((Identifier)id).kind == Types.VARIAVEL || ((Identifier)id).kind == Types.PARAMETRO )){
				factor.setId((Identifier)id);
				return ((Identifier)id).type;
			} else {
				throw new SemanticException("Tipo de Identifier incompatível com o Factor.");
			}
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
		
		if (expression.getBooleanExpression_l() != null
				|| (expression.getIdentifier_l() != null
					&& (idTable.retrieve(expression.getIdentifier_l().spelling) instanceof VarPICBOOLDeclaration
						|| (idTable.retrieve(expression.getIdentifier_l().spelling) instanceof Identifier
							&& ((Identifier)idTable.retrieve(expression.getIdentifier_l().spelling)).type.equals("PICBOOL"))))) {
			
			if (expression.getIdentifier_l() != null) {
				Object temp = idTable.retrieve(expression.getIdentifier_l().spelling);
				if(temp instanceof Identifier){
					expression.setIdentifier_l((Identifier)temp);
				} else if (temp instanceof VarPICBOOLDeclaration){
					expression.setIdentifier_l(((VarPICBOOLDeclaration)temp).getIdentifier());
				}
			}
			
			if(expression.getOpRelational().spelling.equals("=") || expression.getOpRelational().spelling.equals("<>")){
				if (expression.getBooleanExpression_r() != null){
					return expression.getBooleanExpression_r().visit(this, args);
				} else if (expression.getIdentifier_r() != null
						&& (idTable.retrieve(expression.getIdentifier_r().spelling) instanceof VarPICBOOLDeclaration
								|| (idTable.retrieve(expression.getIdentifier_r().spelling) instanceof Identifier 
										&& ((Identifier)idTable.retrieve(expression.getIdentifier_r().spelling)).type.equals("PICBOOL")))) {
					
					if (expression.getIdentifier_r() != null) {
						Object temp = idTable.retrieve(expression.getIdentifier_r().spelling);
						if(temp instanceof Identifier){
							expression.setIdentifier_r((Identifier)temp);
						} else if (temp instanceof VarPICBOOLDeclaration){
							expression.setIdentifier_r(((VarPICBOOLDeclaration)temp).getIdentifier());
						}
					}
					
					return "PICBOOL";
				} else {
					throw new SemanticException("Erro! Nao se pode comparar um booleano com um numero");
				}
			} else {
				throw new SemanticException("Erro! Tipo de operador invalido");
			}
		} else if (expression.getArithmeticExpression_l() != null 
				|| (expression.getIdentifier_l() != null 
					&& (idTable.retrieve(expression.getIdentifier_l().spelling) instanceof VarPIC9Declaration
						|| (idTable.retrieve(expression.getIdentifier_l().spelling) instanceof Identifier
							&& ((Identifier)idTable.retrieve(expression.getIdentifier_l().spelling)).type.equals("PIC9"))))) {
			
			if (expression.getIdentifier_l() != null) {
				Object temp = idTable.retrieve(expression.getIdentifier_l().spelling);
				if(temp instanceof Identifier){
					expression.setIdentifier_l((Identifier)temp);
				} else if (temp instanceof VarPIC9Declaration){
					expression.setIdentifier_l(((VarPIC9Declaration)temp).getIdentifier());
				}
			}
			
			if (expression.getArithmeticExpression_r() != null
					&& expression.getArithmeticExpression_r().visit(this, args).equals("PIC9")) {
				return "PICBOOL";
			} else if (expression.getIdentifier_r() != null
					&& (idTable.retrieve(expression.getIdentifier_r().spelling) instanceof VarPIC9Declaration
							|| (idTable.retrieve(expression.getIdentifier_r().spelling) instanceof Identifier 
									&& ((Identifier)idTable.retrieve(expression.getIdentifier_r().spelling)).type.equals("PIC9")))) {
				
				if (expression.getIdentifier_r() != null) {
					Object temp = idTable.retrieve(expression.getIdentifier_r().spelling);
					if(temp instanceof Identifier){
						expression.setIdentifier_r((Identifier)temp);
					} else if (temp instanceof VarPIC9Declaration){
						expression.setIdentifier_r(((VarPIC9Declaration)temp).getIdentifier());
					}
				}
				
				return "PICBOOL";
			} else {
				throw new SemanticException("Erro! Nao se pode comparar um numero com um booleano");
			}
		} else if(expression.getBooleanValue() != null){
			return "PICBOOL";
		} else {
			throw new SemanticException("Expressao booleana invalida.");
		}
	}

	public Object visitFunctionCall(FunctionCall fcall, Object args)
			throws SemanticException {
		Object func = idTable.retrieve(fcall.getId().spelling);

		if (func == null) {
			throw new SemanticException("A funcao " + fcall.getId().spelling+ " nao foi declarada!");
		} else {
			if (!(func instanceof Function)) {
				throw new SemanticException("Identificador "+ fcall.getId().spelling + " nao representa uma Funcao!");
			} else {
				fcall.setId(((Function)func).getID());
				ArrayList<String> paramsTypesFunc = ((Function) func).getParamsTypes();
				ArrayList<Identifier> paramsCall = fcall.getParams();
				

				if (paramsTypesFunc.size() != paramsCall.size()) {
					throw new SemanticException("Quantidade de parametros passada diferente da quantidade de parametros requeridas pela Funcao!");
				} else if(paramsTypesFunc.size() > 0){
					for (int i = 0; i < paramsTypesFunc.size(); i++) {
						Object temp = idTable.retrieve(paramsCall.get(i).spelling);
						if (temp instanceof VarDeclaration){
							paramsCall.set(i, ((VarDeclaration)temp).getIdentifier());
							if(!paramsTypesFunc.get(i).equals( ((VarDeclaration)temp).getType() ))
								throw new SemanticException("Tipo dos parametros informados não correspondem ao tipo esperado");
						} else if (temp instanceof Identifier){
							paramsCall.set(i, (Identifier)temp);
							if(!paramsTypesFunc.get(i).equals( ((Identifier)temp).type ))
								throw new SemanticException("Tipo dos parametros informados não correspondem ao tipo esperado");
						} else {
							throw new SemanticException("Tipo de argumento invalido para o CALL");
						}
					}
				}
			}
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
			if(this.transmit == null){
				id.kind = Types.FUNCAO;
				id.type = ((Function) args).getTipoRetorno();
				id.declaration = args;
				idTable.enter(id.spelling, (AST) args);
			} else if (this.transmit instanceof String){
				id.kind = Types.PARAMETRO;
				id.type = (String)this.transmit;
				id.declaration = args;
				id.memoryPosition = this.contadorParametros;
				this.contadorParametros++;
				idTable.enter(id.spelling, (AST) id);
				this.transmit = null;
			}
		} else if (args instanceof ArithmeticFactor){
			Object temp = idTable.retrieve(id.spelling);
			if (temp == null && ((ArithmeticFactor) args).getId() != null) {
				throw new SemanticException("A variavel " + id.spelling + " nao foi declarada!");
			} else {
				if(temp instanceof VarPIC9Declaration) {
					id = ((VarPIC9Declaration) temp).getIdentifier();
					return ((VarPIC9Declaration) temp).getType();
				} else if(temp instanceof VarPICBOOLDeclaration){
					id = ((VarPICBOOLDeclaration) temp).getIdentifier();
					return ((VarPICBOOLDeclaration) temp).getType();
				} else if (temp instanceof Identifier) {
					id = (Identifier)temp;
					return ((Identifier)temp).type;
				}
			}
		} else if(args instanceof Display){
			Object temp = idTable.retrieve(((Display)args).getIdentifier().spelling);
			if (temp == null && ((Display) args).getIdentifier() != null) {
				throw new SemanticException("A variavel "
						+ ((Display) args).getIdentifier().spelling
						+ " nao foi declarada!");
			}
			if(temp instanceof VarPIC9Declaration) {
				id = ((VarPIC9Declaration) temp).getIdentifier();
				return ((VarPIC9Declaration) temp).getType();
			} else if(temp instanceof VarPICBOOLDeclaration){
				id = ((VarPICBOOLDeclaration) temp).getIdentifier();
				return ((VarPICBOOLDeclaration) temp).getType();
			} else if (temp instanceof Identifier) {
				id = (Identifier)temp;
				return ((Identifier)temp).type;
			}
		} else {
			throw new SemanticException("Identificador invalido.");
		}
		return null;
	}

	public Object visitIfStatement(IfStatement ifStatement, Object args)
			throws SemanticException {

		Object temp = null;

		if(ifStatement.getBooleanExpression() instanceof BooleanExpression){

			ifStatement.getBooleanExpression().visit(this, args);

			idTable.openScope();
			if(ifStatement.getCommandIF() != null)
				for (Command ifCmd : ifStatement.getCommandIF()) {
					temp = ifCmd.visit(this, args);
				}
			idTable.closeScope();

			idTable.openScope();
			if(ifStatement.getCommandElse() != null)
				for (Command elseCmd : ifStatement.getCommandElse()) {
					elseCmd.visit(this, args);
				}
			idTable.closeScope();

		} else {
			throw new SemanticException("Expressao da condicao do IF nao e booleana!");
		}

		return temp;
	}

	public Object visitReturn(Return rtn, Object args) throws SemanticException {
		this.numRetornos++;
		Function func = (Function)idTable.retrieve(((Function)args).getID().spelling);
		if (args instanceof Function) {
			if (func.getTipoRetorno() != null && func.getTipoRetorno().equals("VOID")) {

				throw new SemanticException("Funcao VOID nao tem retorno");

			} else if (rtn.getExpression() != null){ 
				if(rtn.getExpression() instanceof BooleanExpression && !func.getTipoRetorno().equals("PICBOOL")){

					throw new SemanticException("Valor retornado eh PICBOOL e a funcao requer retorno do tipo PIC9!");

				} else if (rtn.getExpression() instanceof ArithmeticExpression && !func.getTipoRetorno().equals("PIC9")){

					throw new SemanticException("Valor retornado eh PIC9 e a funcao requer retorno do tipo PICBOOL!");
				}
			} else if (rtn.getIdentifier() != null){
				Object id = idTable.retrieve(rtn.getIdentifier().spelling);
				if (id == null) {

					throw new SemanticException("A variavel " + rtn.getIdentifier().spelling + " nao foi declarada!");

				} else if(id instanceof VarDeclaration){
					rtn.setIdentifier(((VarDeclaration)id).getIdentifier());
					
					if(id instanceof VarPIC9Declaration && !func.getTipoRetorno().equals("PIC9")){
						throw new SemanticException("Valor retornado eh PIC9 e a funcao requer retorno do tipo PICBOOL!");
					} else if(id instanceof VarPICBOOLDeclaration  && !func.getTipoRetorno().equals("PICBOOL")){
						throw new SemanticException("Valor retornado eh PICBOOL e a funcao requer retorno do tipo PIC9!");
					}

				} else if(id instanceof Identifier){
					rtn.setIdentifier((Identifier)id);
					
					if(((Identifier)id).kind == Types.VARIAVEL || ((Identifier)id).kind == Types.PARAMETRO){
						if(((Identifier)id).type.equals("PIC9") && !func.getTipoRetorno().equals("PIC9")){
							throw new SemanticException("Valor retornado eh PIC9 e a funcao requer retorno do tipo PICBOOL!");
						} else if(((Identifier)id).type.equals("PICBOOL")  && !func.getTipoRetorno().equals("PICBOOL")){
							throw new SemanticException("Valor retornado eh PICBOOL e a funcao requer retorno do tipo PIC9!");
						}
					} else {
						throw new SemanticException("Identifier do Retorno nao eh nem uma variavel, nem um parametro da funcao.");
					}
				}
			} else {
				throw new SemanticException("Retorno incompativel com a linguagem.");
			}

		} else {
			throw new SemanticException("Comando de retorno deve estar dentro de uma funcao!");
		}
		return null;
	}

	public Object visitUntil(Until until, Object args) throws SemanticException {
		until.getBooleanExpression().visit(this, args);

		idTable.openScope();

		if(until.getCommand() != null) {
			for(Command cmd : until.getCommand()) {
				cmd.visit(this, until);
			}
		}

		idTable.closeScope();

		return null;
	}
	public Object visitContinue(Continue cont, Object args)
			throws SemanticException {
		if (!(args instanceof Until)) {
			throw new SemanticException("O comando CONTINUE só deve ser utilizado em loops");
		}
		return null;
	}
	public Object visitBreak(Break brk, Object args) throws SemanticException {
		if (!(args instanceof Until)) {
			throw new SemanticException("O comando BREAK só deve ser utilizado em loops");
		}
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

	public Object visitDisplay(Display display, Object args)
			throws SemanticException {
		if (display.getIdentifier() != null) {
			Object id = idTable.retrieve(display.getIdentifier().spelling);
			if(id == null){
				throw new SemanticException("Variavel " + display.getIdentifier().spelling + " nao declarada!");
			} else if(id instanceof VarDeclaration || ((Identifier)id).kind == Types.VARIAVEL || ((Identifier)id).kind == Types.PARAMETRO){
				if (id instanceof VarDeclaration) {
					display.setIdentifier(((VarDeclaration)id).getIdentifier());
				} else {
					display.setIdentifier((Identifier)id);
				}
				display.getIdentifier().visit(this, display);
			}
		} else if (display.getExpression() != null){
			display.getExpression().visit(this, display);
		} else {
			throw new SemanticException("Elemento incompativel com o DISPLAY.");
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
}
