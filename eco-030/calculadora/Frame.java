import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;


public class Frame extends JFrame {
	public Container pane;
	public JTextField input;
	public int delay = 0;
	public String string;

	public Frame() {
		super("calc");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pane = getContentPane();
		pane.setLayout(new FlowLayout());
		// txtField
		input = new JTextField(25);
		pane.add(input);
		input.requestFocus();
		//
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
