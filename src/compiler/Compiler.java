package compiler;
import checker.Checker;
import checker.SemanticException;
import parser.Parser;
import parser.SyntacticException;
import scanner.LexicalException;
import scanner.Scanner;
import scanner.Token;
import scanner.Token.TokenType;
import util.AST.AST;
import util.AST.Code;
import util.symbolsTable.IdentificationTable;

/**
 * Compiler driver
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class Compiler {
	
	// Compiler identification table
	public static IdentificationTable identificationTable = null;

	/**
	 * Compiler start point
	 * @param args - none
	 * @throws LexicalException 
	 * @throws SemanticException 
	 */
	public static void main(String[] args) throws LexicalException, SemanticException {
		testScanner();
		// Initializes the identification table with the reserved words 
		Compiler.initIdentificationTable();
		
		// Creates the parser object
		Parser p = new Parser();
		
		// Creates the AST object
		AST astRoot = null;
		
		try {
			// Parses the source code
			astRoot = p.parse();
			System.out.println("\n-- AST STRUCTURE --");
			if ( astRoot != null ) {
				System.out.println(astRoot.toString(0));
			}
		} catch (SyntacticException e) {
			// Shows the syntactic/lexical error stack trace 
			e.printStackTrace();
		}
				
		try {			
			Checker ckr = new Checker();
			ckr.check((Code) astRoot);			
			
			System.out.println("\n-- Checker--");
			
		} catch (SemanticException e) {
			// Shows the syntactic/lexical error stack trace 
			e.printStackTrace();
		}
		
		
		
	}
	
	/**
	 * Initializes the identification table with the reserved words
	 */
	private static void initIdentificationTable() {
		// Calls the initializer methods
		Compiler.identificationTable = new IdentificationTable();
	}
	
	private static void testScanner() throws LexicalException{
		System.out.println("-- INICIO ANALISE LEXICA --");
		Scanner scanner = new Scanner();
		Token t = new Token(1, "", 0, 0);
		while(t.getKind() != TokenType.EOF){
			t = scanner.getNextToken();
			
			System.out.println(t.getSpelling() + "\t(" + t.getKind() + " - " + Token.kindName(t.getKind()) + ")");
		}
		System.out.println("-- FIM ANALISE LEXICA --");
	}
}
