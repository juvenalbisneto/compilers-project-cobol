package util.AST;

import checker.SemanticException;

public class VarPIC9Declaration extends VarDeclaration {
	Number num = null;

	public VarPIC9Declaration(Identifier id){
		super("PIC9");
		super.id = id;
		this.num = new Number("0");
	}
	public VarPIC9Declaration(Identifier id, Number num){
		super("PIC9");
		super.id = id;
		if (num == null) {
			this.num = new Number("0");
		} else {
			this.num = num;
		}
	}
	
	public Number getNumber(){
		return this.num;
	}

	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitVarPIC9Declaration(this, args);
	}
}