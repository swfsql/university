import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Game extends Sequence {

  // drawing related
  private Graphics gv; // Graphics for buffering
  private Image offImage; // Image for buffering

  private boolean leave,
                  isClear;

  private Camera cam;
  private KeyList keys;
  private Stage stage;
  private Bar bar;

  private int stageID;

  private long startTime,
               eplapsedTime;
  private JLabel labelTime;

  Game(Stage stage) {
    this.stage = stage; // now this game obj holds the same Stage reference as the menu obj
    bar = new Bar();
    keys = new KeyList(); // keys for the bar's movement
    cam = new Camera();
    cam.resize(Main.WIDTH, Main.HEIGHT);
    pDrawLabel();
  }

  public void setStageID(int stageID) {
    this.stageID = stageID;
  }

  public String getStageName() {
    return stage.getStageName(stageID);
  }

  public boolean isClear() {
    return isClear;
  }

  public long getTime() {
    if (!leave) eplapsedTime = System.currentTimeMillis() - startTime;
    return eplapsedTime;
  }

  public void myMain() {
    super.seqInit(keys);
    System.out.println("Play started on stage [" + stageID + "] " + stage.getStageName(stageID));
    stage.load(stageID, bar);
    isClear = false;
    leave = false;
    startTime = System.currentTimeMillis();
    while (!leave) {
      play();
      try {
        Thread.sleep(1000 / Main.FPS);
      } catch (InterruptedException e) {
        System.err.println("Exception: " + e.getMessage());
      }
    }
    super.seqEnd(keys);
  }

  private void play() {
    if (bar.goalCheck(stage)) {
      isClear = true;
      getTime(); // to update the time
      leave = true;
    }

    this.updateBuffer();
    bar.move(keys, stage);
    bar.collision(stage);
    if (bar.collided == bar.collided_max) bar.move(keys, stage); // don't let the bar move into a wall when it first collides.
    cam.move(bar, stage); // camera tries to follow the bar

    this.clear();
    stage.drawBelow(gv, cam, this);
    bar.draw(gv, cam);
    stage.drawAbove(gv, cam, this);
    
    // update time elapsed
    labelTime.setText(getCurrentTime(getTime()));

    this.repaint();
  }

  private void pDrawLabel(){ //currentTime、stageNameのラベル表示
    String time = getCurrentTime(getTime()); 
    Font fontLabel_32 = new Font("Arial",Font.PLAIN,32);
    //Labelの作成
    labelTime			= new JLabel(time);
    addLabel(labelTime,				fontLabel_32,	0, 0, 150, 50);
  }

  private String getCurrentTime(long time){ //currentタイムを 分:秒 に直す関数
    String strTime;
    strTime = String.valueOf( String.format("%02d", time / 60000 )); //分
    strTime += ":";
    strTime +=  String.valueOf( String.format("%02d",(time % 60000) / 1000 )); //秒
    strTime += ":";
    strTime +=  String.valueOf( String.format("%02d",(time % 60000) % 1000 /10)); //小数点第２位まで表示（少数第3位は切り捨て）
    return strTime;
  }
  private void addLabel(JLabel label,Font font,int x,int y,int width,int height){
    label.setHorizontalAlignment(JLabel.CENTER);
    label.setFont(font);
    label.setBounds(x,y,width,height);
    super.f_frame.getContentPane().add(label);
  }

  // Paint

  // clear drawings
  private void clear() {
    // clears the entire screen
    gv.clearRect(0, 0, cam.w, cam.h);
  }
  public void update(Graphics g) {
    paint(g);
  }
  // buffer for flickering
  private void updateBuffer() {
    offImage = createImage(cam.w, cam.h);
    gv = offImage.getGraphics();
    ((Graphics2D) gv).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
  }

  public void paint(Graphics graphics) {
    graphics.drawImage(offImage, 0, 0, cam.w, cam.h, this);
  }


}
