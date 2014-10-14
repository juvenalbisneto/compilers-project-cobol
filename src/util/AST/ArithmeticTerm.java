package util.AST;

import java.util.*;

public class ArithmeticTerm extends Expression {
	ArithmeticFactor afactor = null;
	OpMult op = null;
	ArithmeticTerm aterm = null;

	public ArithmeticTerm(ArithmeticFactor afactor){
		this.afactor = afactor;
	}

	public ArithmeticParcel(ArithmeticFactor afactor, OpMult op, ArithmeticTerm aterm){
		this.aterm = aterm;
		this.op = op;
		this.afactor = afactor;
	}



	public ArithmeticFactor getArithmeticFactor()){
		return this.afactor;
	}
	public OpAdd getOpMult(){
		return this.op;
	}
	public ArithmeticTerm(){
		return this.aterm;
	}


}
