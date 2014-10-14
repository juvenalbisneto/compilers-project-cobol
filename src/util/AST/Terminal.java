package util.AST;

public class Terminal extends AST {
	String token = "Terminal";
	public String spelling;
	
	public Terminal(String spelling){
		this.spelling = spelling;
	}

	@Override
	public String toString(int level) {
		return (super.getSpaces(level) + token);
	}
}
