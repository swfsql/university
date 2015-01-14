import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Ranking extends Sequence implements ActionListener {

  JFrame frame = super.f_frame;

  private boolean leave;

  public void myMain() {
    makeFrame();
    makeLabel();
    pDrawButton();
    makeTextField();

    super.seqInit(null);
    leave = false;
    while(!leave) {
      try {
        Thread.sleep(1000 / Main.FPS);
      } catch (InterruptedException e) {
        System.err.println("Exception: " + e.getMessage());
      }
    }
    super.seqEnd(null);

  }

  /*
     TestFrame(String title){
     JFrame frame = new JFrame("title");

     JLabel label = new JLabel();
     JButton buttonOK = new JButton("SPACE");
     frame.setBounds((frame.getToolkit().getScreenSize().width/2)-400,(frame.getToolkit().getScreenSize().height/2)-300, Main.WIDTH, Main.HEIGHT);
     label.setText("ラベル");
     frame.add(label);
     buttonOK.setBounds(360,492,80,30);
     frame.add(buttonOK);
     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     frame.setVisible(true);
     }

*/

  private void makeFrame(){
    //frame.setBounds(200,200,780, 580);
    //		frame.setLocation(200, 200);
    //		frame.setSize(780, 580);
    //		frame.setBounds((frame.getToolkit().getScreenSize().width/2)-400,(frame.getToolkit().getScreenSize().height/2)-300, Main.WIDTH, Main.HEIGHT);
    frame.setVisible(true);
  }

  private void pDrawButton(){ //OKのボタン作成
    JButton buttonOK = new JButton("SPACE");
    buttonOK.addActionListener(this);
    buttonOK.setBounds(360,492,80,30);
    //		buttonOK.setLocation(10, 20);
    //		buttonOK.setSize(300, 100);
    frame.add(buttonOK);
  }

  private void makeLabel(){
    JLabel label = new JLabel();
    label.setText("ラベル");
    frame.add(label);

  }


  private void makeTextField(){
    TextField txt = new TextField("");
    txt.setBounds(10, 20, 100, 30);
    //		txt.setLocation(30, 40);
    //		txt.setSize(100, 50);
    frame.add(txt);
  }

  //----------------------------------------------▼ボタンが押された時の処理▼----------------------------------------------//

  public void actionPerformed(ActionEvent e){ //OK **moonspeak**
    leave = true;
  }

  //----------------------------------------------▲ボタンが押された時の処理▲----------------------------------------------//

}
