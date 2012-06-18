import java.util.Scanner;

public class Main {

	/*
	 * @author:
	 * - Thiago Machado da Silva (21215).
	 * - Tiago Paixão Visconti (22233).
	 * 
	 * @patterns:
	 * - english.
	 * - i/o on Main class only.
	 * 
	 * @about:
	 * - project hosted on https://github.com/thyfl/unifei/tree/master/eco-030/calculadora.
	 * - teacher: Fernando Santos (Universidade Federal de Itajubá, Campus Itabira).
	 * - text editor: Sublime text 2.
	 * - compiler: javac.
	 * - some of the code is from the flash apps http://wonderfl.net/c/kkNQ/read or http://wonderfl.net/c/3G3M/read, from Silva.
	 * - division is also inspired on the binary search http://en.wikipedia.org/wiki/Binary_search_algorithm.
	 *
	 * @instructions:
	 * - type 'exit' to end the program.
	 * - type an basic expression to get result. eg.: '123 + 123 / 122' (= '124').
	 *
	 * @todo:
	 * - real & fraction nums.
	 * - UI.
	 * - variables.
	 * - use stacks instead of lists.
	 */

	public static void main(String[] args) {
		Expression exp = new Expression(); // recive inputs, make a List out of it.
		Calculator calc = new Calculator(); // output calculated List.

		Scanner scan = new Scanner(System.in);

		// examples:
		{
			System.out.println("\n-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
			System.out.println("examples that throw exception x/0:");

			System.out.println("\n(1-1) / (0/1)");
			exp.setInput("(1-1) / (0/1)");
			System.out.print("  ");System.out.println(calc.right(exp)); 

			System.out.println("\n(-1+1*2) / (3-6/2)");
			exp.setInput("(-1+1*2) / (3-6/2)");
			System.out.print("  ");System.out.println(calc.right(exp));

			System.out.println("\n-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");

			System.out.println("'exit' quits\n\n-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
		}

		while(!exp.getInput("exit")){
			System.out.println("");
			exp.setInput(scan.nextLine());
			System.out.println(calc.right(exp)); 
			// called 'right' because in the example: 'A = 2+2', we consider '2+2' the 'right side'.
			// future plans include calculations on the 'left side', 'A ='. 
		}	
	}
}