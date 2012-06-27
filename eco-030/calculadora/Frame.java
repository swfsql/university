import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.event.*;


public class Frame extends JFrame {
	//public Container pane;
	public JTextField input, out2;
	public int delay = 0;
	public String string;

	public Frame() {
		super("calc");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JDesktopPane pane;
		pane=new JDesktopPane();
        setContentPane(pane);

		//pane = getContentPane();
		//pane.setLayout(new FlowLayout());


		// txtField
		input = new JTextField(25);
		input.setBounds(10, 10, 465 - 30, 20);
		pane.add(input);
		//
		out2 = new JTextField(25);
		out2.setEditable(false);
		//out2.setHorizontalAlignment(JTextField.RIGHT);
		out2.setBounds(10, 30,  465 - 30, 20);
		//out2.setBorder(BorderFactory.createEmptyBorder());
		//out2.setBackground(new Color(0,0,0,0));
		pane.add(out2);
		
		//
		input.requestFocus();
		setSize(465, 465);
		setVisible(true);
		

        DocumentListener txtListener = new DocumentListener() {
			public void changedUpdate(DocumentEvent e) { event();}
			public void insertUpdate(DocumentEvent e) { event();}
			public void removeUpdate(DocumentEvent e) { event();}
			public void event() { delay = 10;}
		};
		input.getDocument().addDocumentListener(txtListener);
	}

}
