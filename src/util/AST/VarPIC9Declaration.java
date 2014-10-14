package util.AST;

import java.util.*;

public class VarPIC9Declaration extends VarDeclaration {
	Identifier id;

	public VarPIC9Declaration(Identifier id){
		this.id=id;
	}

	public getIdentifier(){
		return this.id;
	}

}