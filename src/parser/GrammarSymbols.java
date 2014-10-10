package parser;


/**
 * This class contains codes for each grammar terminal
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class GrammarSymbols {

	// Language terminals (starts from 0)
	public static final int EOF = 0, IDENTIFIER = 1, NUMBER = 2, BOOL_VALUE = 3, PIC9_TYPE = 4, PICBOOL_TYPE = 5, VOID_TYPE = 6,
			
			OP_RELACIONAL = 10, OP_ADD = 11, OP_MULT = 12, L_PAR = 13, R_PAR = 14, POINT = 15, COMMA = 16,
			MAJOR = 20, MAJOREQ = 21, MINOR = 22, MINOREQ = 23, DIFFERENT = 24, EQUALS = 25,
			
			ACCEPT = 30, FROM = 31, COMPUTE = 32, VALUE = 33, CALL = 34, USING = 35, DISPLAY = 36, RETURN = 37,
			
			IF = 40, THEN = 41, ELSE = 42, END_IF = 43,
			PERFORM = 50, UNTIL = 51, END_PERFORM = 52, BREAK = 53, CONTINUE = 54,
			GLOBALDATA = 60, PROGRAM = 61, DIVISION = 62, MAIN = 63, END_MAIN = 64, END_FUNCTION = 65;
	
}
