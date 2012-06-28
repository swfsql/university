import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class Frame extends JFrame {
	//public Container pane;
	public JTextField input, out2;
	public int delay = 0;
	public String string;
	public JList list;
	

	public Frame(DefaultListModel strings) {
		super("calc");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JDesktopPane pane;
		pane=new JDesktopPane();
        setContentPane(pane);

		//pane = getContentPane();
		//pane.setLayout(new FlowLayout());

		// input
		input = new JTextField(25);
		input.setBounds(10, 10, 465 - 30, 20);
		pane.add(input);

		// output
		out2 = new JTextField(25);
		out2.setEditable(false);
		out2.setBounds(12, 30,  465 - 30, 20);
		out2.setBorder(BorderFactory.createEmptyBorder());
		out2.setBackground(new Color(255,255,255,255));
		pane.add(out2);

		// list
		list = new JList(strings);
		list.setBounds(10, 50, 100, 100);
		pane.add(list);
		// scroll
		JScrollPane scroll = new JScrollPane(list);
		pane.add(scroll);
		scroll.setBounds(10, 70, 465 - 30, 465 - 30 - 70);
		
		//
		setSize(465, 465);
		setVisible(true);
		
		// when writing a new text
        DocumentListener txtListener = new DocumentListener() {
			public void changedUpdate(DocumentEvent e) { event();}
			public void insertUpdate(DocumentEvent e) { event();}
			public void removeUpdate(DocumentEvent e) { event();}
			public void event() { delay = 10;}
		};
		input.getDocument().addDocumentListener(txtListener);


		input.requestFocus();
	}

}
