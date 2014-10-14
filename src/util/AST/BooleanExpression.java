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
	public BooleanExpression(BooleanExpression bexpression_l,OpRelational opr, Number numero_r){
		this.bexpression_l=bexpression_l;
		this.opr=opr;
		this.numero_r=numero_r;
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
	public BooleanExpression(ArithmeticExpression aexpression_l,OpRelational opr, Number numero_r){
		this.aexpression_l=aexpression_l;
		this.opr=opr;
		this.numero_r=numero_r;
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
	public BooleanExpression(Identifier id_l,OpRelational opr, Number numero_r){
		this.id_l=id_l;
		this.opr=opr;
		this.numero_r=numero_r;
	}


	public BooleanExpression(Number numero_l,OpRelational opr, BooleanExpression bexpression_r){
		this.numero_l=numero_l;
		this.opr=opr;
		this.bexpression_r=bexpression_r;
	}
	public BooleanExpression(Number numero_l,OpRelational opr, ArithmeticExpression aexpression_r){
		this.numero_l=numero_l;
		this.opr=opr;
		this.aexpression_r=aexpression_r;
	}
	public BooleanExpression(Number numero_l,OpRelational opr, Identifier id_r){
		this.numero_l=numero_l;
		this.opr=opr;
		this.id_r=id_r;
	}
	public BooleanExpression(Number numero_l,OpRelational opr, Number numero_r){
		this.numero_l=numero_l;
		this.opr=opr;
		this.numero_r=numero_r;
	}


	public BooleanExpression(BoolValue bvalue){
		this.bvalue=bvalue;
	}



//nem sei se ta certo e faltam gets
	
}