import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.JFrame;

public class Game extends Sequence {

  // drawing related
  public Graphics gv; // Graphics for buffering
  private Image offImage; // Image for buffering

  private boolean leave;

  Camera cam;
  KeyList keys;
  Stage stage;
  Bar bar;

  private int FPS;
  private int stageID;
  private JFrame frame;

  public Game(int stageID,Stage stage) {
    this.frame = super.f_frame;
    this.FPS = Main.FPS;
    this.stage = stage; // now this game obj holds the same Stage reference as the menu obj
    this.stageID = stageID;
    bar = new Bar();
    keys = new KeyList(); // keys for the bar's movement
    cam = new Camera();
    cam.resize(Main.WIDTH, Main.HEIGHT);
  }

  public void myMain() {
    System.out.println("Play started on stage [" + stageID + "] " + stage.stages[stageID][0]);
    frame.setBounds((frame.getToolkit().getScreenSize().width/2)-Main.WIDTH/2, //????????????????????????????????????
        (frame.getToolkit().getScreenSize().height/2)- Main.HEIGHT/2, Main.WIDTH, Main.HEIGHT);
    frame.add(this);//???????????????????????????????
    frame.setVisible(true);//??????????????????????
    this.addKeyListener(keys);
    this.setFocusable(true); // keyboard focus
    stage.load(stageID, bar);
    leave = false;
    while (!leave) {
      play();
      try {
        Thread.sleep(1000 / FPS);
      } catch (InterruptedException e) {
        System.err.println("Exception: " + e.getMessage());
      }
    }
    this.removeKeyListener(keys);
    this.setFocusable(false); // keyboard focus
    sequence(Main.SeqID.SEQ_RANKING);
    /*frame.setVisible(false);
      frame.remove(this);*/
  }

  // Paint

  // clear drawings
  public void clear() {
    // clears the entire screen
    gv.clearRect(0, 0, cam.w, cam.h);
  }
  public void update(Graphics g) {
    paint(g);
  }
  // buffer for flickering
  public void updateBuffer() {
    offImage = createImage(cam.w, cam.h);
    gv = offImage.getGraphics();
    ((Graphics2D) gv).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
  }

  public void paint(Graphics graphics) {
    graphics.drawImage(offImage, 0, 0, cam.w, cam.h, this);
  }

  public void play() {
    this.updateBuffer();
    bar.move(keys, stage);
    bar.collision(stage);
    if (bar.collided == bar.collided_max) bar.move(keys, stage);
    cam.move(bar, stage);

    this.clear();
    stage.drawBelow(gv, cam, this);
    bar.draw(gv, cam);
    stage.drawAbove(gv, cam, this);

    this.repaint();
  }

}
