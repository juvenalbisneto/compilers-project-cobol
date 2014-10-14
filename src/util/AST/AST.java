package util.AST;

/**
 * AST class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public abstract class AST {

	public String getSpaces(int level) {
		StringBuffer str = new StringBuffer();
		while( level>0 ) {
			str.append(" ");
			level--;
		}
		return str.toString();
	}
	
	public abstract String toString(int level);
	
	public interface Types {
		public static final int VARIAVEL = 0, FUNCAO = 1, PARAMETRO = 2;
		public static final int GLOBAL = 0, LOCAL = 1;
	}
}
