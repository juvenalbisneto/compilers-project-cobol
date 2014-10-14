package util.AST;

import java.util.*;

public class VarPICBOOLDeclaration extends VarDeclaration {
	Identifier id;

	public VarPICBOOLDeclaration(Identifier id){
		this.id=id;
	}

	public getIdentifier(){
		return this.id;
	}

}