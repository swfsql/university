import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
//
import java.util.Arrays;
import java.util.Comparator;

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
	public static DefaultListModel list;
	public static String s;

	public static void main(String[] args)  {
		new Main();
	}

	public Main() {
		exp = new Expression(); // recive inputs, make a List out of it.
		calc = new Calculator(); // output calculated List.System.out.println("a");
		//
		list = new DefaultListModel();
		frame = new Frame(list);

		// listeners

		// enterFrame
		ActionListener ef = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (--frame.delay == 0) {
					s = "";
					exp.setInput(frame.input.getText());
					try { s = calc.right(exp);} 
					catch (Exception ex) {frame.out2.setText(ex.getMessage());}
					frame.out2.setText(s);
				}
        	}
        };
        new Timer(100, ef).start();

        // Enter
        ActionListener en = new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		s = "";
				exp.setInput(frame.input.getText());
				try { s = calc.right(exp);} 
				catch (Exception ex) { frame.out2.setText(ex.getMessage());}
				frame.out2.setText(s);
				list.addElement(s);
				frame.input.setText("");
				frame.out2.setText("");
				exp.setInput(" ");
        	}
        };
        frame.input.addActionListener(en);

        // buttons

        // media
        ActionListener bMedia = new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		s = "";
        		int i = 0, l = list.size();
        		System.out.println("H0");
        		if (l < 1) {
        			System.out.println("H1");
        			s = " ";
        		} else {
        			System.out.println("H2");
        			s = "( " + list.getElementAt(i);
	        		while(++i < l) s += " + " + list.getElementAt(i);
	        		s += " ) / " + l;
        		}
        		System.out.println("H3");
        		System.out.println("H4");
				frame.input.setText(s);
				System.out.println("H5");
				exp.setInput(s);
				System.out.println("H6");
				try { s = calc.right(exp);} 
				catch (Exception ex) {frame.out2.setText(ex.getMessage());}
				System.out.println("H7");
				frame.out2.setText(s);
        	}
        };
        frame.b1.addActionListener(bMedia);

        // mediana
        ActionListener bMediana = new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		s = "";
				frame.out2.setText("APERTO MEDIANA");
        	}
        };
        frame.b2.addActionListener(bMediana);

         // sort
        ActionListener bSort = new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		s = "";
        		Object sorting[] = list.toArray();
        		Arrays.sort(sorting, new Comp());
        		int i = -1, l = list.size();
        		while(++i < l) list.setElementAt(sorting[i], i);
        	}
        };
        frame.b3.addActionListener(bSort);
        
	}
	
}

class Comp implements Comparator {
	public int compare(Object a, Object b) {
		String s = "(" + a + ")" + "- (" + b + ")";
		Main.exp.setInput(s);
		try { s = Main.calc.right(Main.exp);} 
		catch (Exception ex) {Main.frame.out2.setText(ex.getMessage());}
		return s.charAt(0) == '-' ? -1 : 1;
	}
 }


