package util.AST;

import checker.SemanticException;

public class BooleanExpression extends Expression {
		
	OpRelational opr = null;	
	BoolValue bvalue = null;
	
	BooleanExpression bexpression_l = null;
	ArithmeticExpression aexpression_l = null;
	Identifier id_l = null;

	BooleanExpression bexpression_r = null;
	ArithmeticExpression aexpression_r = null;
	Identifier id_r = null;

	
	public BooleanExpression(BooleanExpression bexpression_l,OpRelational opr, BooleanExpression bexpression_r){
		this.bexpression_l=bexpression_l;
		this.opr=opr;
		this.bexpression_r=bexpression_r;
	}
	public BooleanExpression(BooleanExpression bexpression_l,OpRelational opr, ArithmeticExpression aexpression_r){
		this.bexpression_l=bexpression_l;
		this.opr=opr;
		this.aexpression_r=aexpression_r;
	}
	public BooleanExpression(BooleanExpression bexpression_l,OpRelational opr, Identifier id_r){
		this.bexpression_l=bexpression_l;
		this.opr=opr;
		this.id_r=id_r;
	}

	public BooleanExpression(ArithmeticExpression aexpression_l,OpRelational opr, BooleanExpression bexpression_r){
		this.aexpression_l=aexpression_l;
		this.opr=opr;
		this.bexpression_r=bexpression_r;
	}

	public BooleanExpression(ArithmeticExpression aexpression_l,OpRelational opr, ArithmeticExpression aexpression_r){
		this.aexpression_l=aexpression_l;
		this.opr=opr;
		this.aexpression_r=aexpression_r;
	}
	public BooleanExpression(ArithmeticExpression aexpression_l,OpRelational opr, Identifier id_r){
		this.aexpression_l=aexpression_l;
		this.opr=opr;
		this.id_r=id_r;
	}


	public BooleanExpression(Identifier id_l,OpRelational opr, BooleanExpression bexpression_r){
		this.id_l=id_l;
		this.opr=opr;
		this.bexpression_r=bexpression_r;
	}
	public BooleanExpression(Identifier id_l,OpRelational opr, ArithmeticExpression aexpression_r){
		this.id_l=id_l;
		this.opr=opr;
		this.aexpression_r=aexpression_r;
	}
	public BooleanExpression(Identifier id_l,OpRelational opr, Identifier id_r){
		this.id_l=id_l;
		this.opr=opr;
		this.id_r=id_r;
	}


	public BooleanExpression(BoolValue bvalue){
		this.bvalue=bvalue;
	}

	public OpRelational getOpRelational(){
		return this.opr;
	}
	public BoolValue getBooleanValue(){
		return this.bvalue;
	}
	public BooleanExpression getBooleanExpression_l(){
		return this.bexpression_l;
	}
	public BooleanExpression getBooleanExpression_r(){
		return this.bexpression_r;
	}
	public ArithmeticExpression getArithmeticExpression_l(){
		return this.aexpression_l;
	}
	public ArithmeticExpression getArithmeticExpression_r(){
		return this.aexpression_r;
	}
	public Identifier getIdentifier_l(){
		return this.id_l;
	}
	public Identifier getIdentifier_r(){
		return this.id_r;
	}
	
	@Override
	public String toString(int level) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(Visitor v, Object args) throws SemanticException{
		return v.visitBooleanExpression(this, args);
	}
}