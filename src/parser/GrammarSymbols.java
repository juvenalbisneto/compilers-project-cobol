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
	public static final int ID = 0, 
	 NUMBER =1,
	 OPRELACIONAL=2,
	 OPADD=3,
	 OPMULT=4,
	 DOT=5,
	 LB=6,
	 RB=7,
	 IF=8,
	 THEN=9,
	 ELSE=10,
	 END-IF=11,
	 PERFORM=12,
	 UNTIL=13,
	 END-PERFORM =14,
	 VALUE =15,
	 PROGRAM=16,
	 GLOBALDATA=17,
	 DIVISION =18,
	 VOID =19,
	 CALL =20,
	 MAIN =21,
	 USING =22,
	 END-MAIN =23,
	 END-FUNCTION=24 ,
	 DISPLAY =25,
	 ACCEPT =26,
	 FROM=27,
	 COMPUTE=28,
	 RETURN=29,
	 BREAK=30,
	 CONTINUE=31 ,
	 EOF=32;




	//TODO
	public static final int EOT = 1000;
	
}
