import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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

	public static Frame frame;
	public static Expression exp;
	public static Calculator calc;

	public static void main(String[] args) {
		exp = new Expression(); // recive inputs, make a List out of it.
		calc = new Calculator(); // output calculated List.
		frame = new Frame();

		ActionListener ef = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (--frame.delay == 0) {
					exp.setInput(frame.input.getText());
					System.out.println(calc.right(exp)); 
				}
        	}
        };
        new Timer(100, ef).start();
	}
}