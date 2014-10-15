package util.AST;

public abstract class ArithmeticFactor extends AST {
	Identifier id = null;
	Number num = null;
	ArithmeticParcel aParcel = null;

	public ArithmeticFactor(Identifier id){
		this.id = id;
	}
	public ArithmeticFactor(Number num){
		this.num = num;
	}
	public ArithmeticFactor(ArithmeticParcel aParcel){
		this.aParcel = aParcel;
	}

	public Identifier getId(){
		return this.id;
	}
	public Number getNumber(){
		return this.num;
	}
	public ArithmeticParcel getArithmeticParcel(){
		return this.aParcel;
	}
}