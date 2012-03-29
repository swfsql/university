import java.util.Scanner;

public class Main {

	/*
	 * @author:
	 * - Thiago Machado da Silva (21215).
	 * - Thiago Paixão Visconti (22233).
	 * 
	 * patterns:
	 * - i/o on Main class only.
	 * - use _ in private variables or functions.
	 * - { (opening brackets) on same line.
	 * 
	 * about:
	 * - teacher: Fernando (Universidade Federal de Itajubá, Campus Itabira).
	 * - text editor: Sublime text 2.
	 * - compiler: javac.
	 * - some of the code is from http://wonderfl.net/c/kkNQ/read or http://wonderfl.net/c/3G3M/read
	 *
	 * instructions:
	 * - type 'exit' to end the program.
	 * - type '123 + 123' to recive '246'. (TODO). 
     * - - minimum pattern: 'number operator number'. (TODO)
     * - - expression pattern: just any supported expression. (TODO - extra)
	 * - - - type 'A = 123' 'A + 123' to recive '246'. (TODO - extra)
	 */


	public static void main(String[] args) {
		Expression exp = new Expression(); // receberá os inputs e o transformará em uma expressão RPN
		Calculator calc = new Calculator(); // irá calcular a expressão RPN
		Var vars = new Var(); // será o head da lista de variáveis, usadas na expressão e calculadora

		Scanner scan = new Scanner(System.in);
		System.out.println("'exit' quits");


		while(!exp.getInput("exit")){
			System.out.println("");
			exp.setInput(scan.nextLine());
		}


		
	}


}