package util.AST;

import java.util.*;

public class ArithmeticParcel extends ArithmeticFactor {

	ArithmeticTerm aterm = null;
	OpAdd op = null;
	ArithmeticParcel aparcel = null;

	public ArithmeticParcel(ArithmeticTerm aterm){
		this.aterm = aterm;
	}

	public ArithmeticParcel(ArithmeticTerm aterm, OpAdd op, ArithmeticParcel aparcel){
		this.aterm = aterm;
		this.op = op;
		this.aparcel = aparcel;
	}



	public ArithmeticTerm getArithmeticTerm()){
		return this.aterm;
	}
	public OpAdd getOpAdd(){
		return this.op;
	}
	public ArithmeticParcel(){
		return this.aparcel;
	}

}