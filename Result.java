import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Container;

public class Result extends Sequence implements ActionListener{

  private JFrame f_frame;
  private Container f_container;

  private int f_stageID;
  private boolean f_isClear;
  private long f_currentTime;

  //----------------------------------------------▼初期化関数▼----------------------------------------------//

  Result(int stageID,boolean isClear,long currentTime){ //受け取ったMainをResultのフィールド変数のf_mainへ格納
    f_stageID = stageID;
    f_isClear = isClear;
    f_currentTime = currentTime;
    f_frame = super.f_frame;
    f_container = f_frame.getContentPane();
    f_container.setLayout(null);
    //pInitFrame();
  }

  /*private void pInitFrame(){ //frameの初期設定
    f_frame = new JFrame("RESULT");
    f_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f_frame.setBounds((f_frame.getToolkit().getScreenSize().width/2)-400,
    (f_frame.getToolkit().getScreenSize().height/2)-300, Main.WIDTH, Main.HEIGHT);
    f_frame.setVisible(true);

    f_container = f_frame.getContentPane();
    f_container.setLayout(null);
    }*/

  //----------------------------------------------▲初期化関数▲----------------------------------------------//

  //----------------------------------------------▼メイン処理▼----------------------------------------------//

  public void myMain(){ //Mainから呼ばれるメソッド
    pDrawLabel(); //currentTime、stageIDのラベル表示
    pDrawButton(); //OKのボタン作成
  }

  private void pDrawLabel(){ //currentTime、stageIDのラベル表示

    String sGC = getCurrentTime(f_currentTime); //getCurrentTime:currentタイムを 分:秒 に直す関数  sGC = StringGameClear
    String sGO = " -- : -- "; //sGO = StringGameOver ゲームオーバーの場合表示する
    String stage = "STAGE " + f_stageID;

    Font fontLabel_32 = new Font("Arial",Font.PLAIN,32);
    Font fontLabel_100= new Font("Arial",Font.PLAIN,100);

    //Labelの作成
    JLabel labelGameClearOrOver = new JLabel(f_isClear ? "GAME CLEAR" : "GAME OVER");
    JLabel labelCurrentTime 	= new JLabel(f_isClear ? sGC : sGO);
    JLabel labelStage 			= new JLabel(stage);
    JLabel labelTime			= new JLabel("TIME");

    addLabel(labelStage,		  	fontLabel_32,	0,35,Main.WIDTH,100);
    addLabel(labelGameClearOrOver,	fontLabel_100,	0,161,Main.WIDTH,100);
    addLabel(labelTime,				fontLabel_32,	0,317,Main.WIDTH,100);
    addLabel(labelCurrentTime,		fontLabel_32,	0,347,Main.WIDTH,100);
  }

  private void addLabel(JLabel label,Font font,int x,int y,int width,int height){
    label.setHorizontalAlignment(JLabel.CENTER);
    label.setFont(font);
    label.setBounds(x,y,width,height);
    f_container.add(label);
  }

  private String getCurrentTime(long time){ //currentタイムを 分:秒 に直す関数
    String strTime;
    strTime = String.valueOf( String.format("%02d", time / 60000 )); //分
    strTime += " ' ";
    strTime +=  String.valueOf( String.format("%02d",(time % 60000) / 1000 )); //秒
    strTime += " \" ";
    strTime +=  String.valueOf( String.format("%02d",(time % 60000) % 1000 /10)); //小数点第２位まで表示（少数第3位は切り捨て）
    return strTime;
  }

  private void pDrawButton(){ //OKのボタン作成
    JButton buttonOK = new JButton("SPACE");
    buttonOK.addActionListener(this);
    buttonOK.setBounds(360,492,80,30);
    f_container.add(buttonOK);
  }

  //----------------------------------------------▲メイン処理▲----------------------------------------------//

  //----------------------------------------------▼ボタンが押された時の処理▼----------------------------------------------//

  public void actionPerformed(ActionEvent e){ //OKボタンが押された時の処理、つまりRankingへのシーケンス処理
    super.seqEnd(null);
  }

  //----------------------------------------------▲ボタンが押された時の処理▲----------------------------------------------//

}
