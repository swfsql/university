import javax.swing.*;
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
	public static DefaultListModel list;
	public static String s;

	public static void main(String[] args)  {
		new Main();
	}

	public Main() {
		exp = new Expression(); // recive inputs, make a List out of it.
		calc = new Calculator(); // output calculated List.System.out.println("a");
		list = new DefaultListModel();
		frame = new Frame(list);

		// listeners

		// enterFrame
		ActionListener ef = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (--frame.delay == 0) {
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
        		int i = 0, l = list.size();
        		if (l < 1) {
        			s = " ";
        		} else {
        			s = "( " + list.getElementAt(i);
	        		while(++i < l) s += " + " + list.getElementAt(i);
	        		s += " ) / " + l;
        		}
        		
				frame.input.setText(s);
				exp.setInput(s);
				try { s = calc.right(exp);} 
				catch (Exception ex) {frame.out2.setText(ex.getMessage());}
				frame.out2.setText(s);
        	}
        };
        frame.b1.addActionListener(bMedia);

        // mediana
        ActionListener bMediana = new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
				frame.out2.setText("APERTO MEDIANA");
        	}
        };
        frame.b2.addActionListener(bMediana);

        // sort
        ActionListener bSort = new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
				frame.out2.setText("APERTO SORT");
        	}
        };
        frame.b3.addActionListener(bSort);
        
	}
	
}