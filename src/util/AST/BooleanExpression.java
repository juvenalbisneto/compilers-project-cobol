package util.AST;

import java.util.*;

public class BooleanExpression extends Expression {
		
	OpRelational opr = null;	
	BoolValue bvalue = null;
	
	BooleanExpression bexpression_l = null;
	ArithmeticExpression aexpression_l = null;
	Identifier id_l;
	Number numero_l;

	BooleanExpression bexpression_r = null;
	ArithmeticExpression aexpression_r = null;
	Identifier id_r;
	Number numero_r;


	public BooleanExpression(BooleanExpression bexpression_l,OpRelational opr, BooleanExpression bexpression_r){
		this.bexpression_l=bexpression_l;
		this.opr=opr;
		this.bexpression_r=bexpression_r;
	}

	public BooleanExpression(BoolValue bvalue){
		this.bvalue=bvalue;
	}

	
}