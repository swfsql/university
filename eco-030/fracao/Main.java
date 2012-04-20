import java.util.Scanner;

public class Main {

	/*
	 * @author:
	 * - Thiago Machado da Silva (21215).
	 * - Thiago Paixão Visconti (22233).
	 * 
	 * @patterns:
	 * - english.
	 * - i/o on Main class only.
	 * - use _ in private atributes or methods.
	 * - { (opening brackets) on same line.
	 * - scope for grouped commands.
	 * - use alternate operators when possible '?:'.
	 * - avoid the usage of 'else'. Always use 'continue' or 'return'. 
	 * 
	 * @about:
	 * - teacher: Fernando Santos (Universidade Federal de Itajubá, Campus Itabira).
	 * - text editor: Sublime text 2, BlueJ, Eclipse.
	 * - compiler: javac.
	 * - forked from https://github.com/thyfl/unifei/tree/master/eco-030/calculadora, from Silva and Visconti.
	 * - project hosted on https://github.com/thyfl/unifei/tree/master/eco-030/fracao.
	 *
	 * @instructions:
	 * - type 'exit' to end the program.
	 * - type 'numerator', '/', 'denominator', 'operator', 'numerator', '/', 'denominator'. 
	 *   eg.: '1/2 * 3/2' (= '3/4').
	 */


	public static void main(String[] args) {
		Expression exp = new Expression(); // recive inputs, make a List out of it.
		Calculator calc = new Calculator(); // output calculated List.

		Scanner scan = new Scanner(System.in);
		System.out.println("'exit' quits");

		while(!exp.getInput("exit")){
			System.out.println("");
			exp.setInput(scan.nextLine());
			System.out.println(calc.right(exp)); 
			// called 'right' because in the example: 'A = 2+2', we consider '2+2' the 'right side'.
			// future plans include calculations on the 'left side', 'A ='. 
		}	
	}
}