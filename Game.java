import java.awt.*;
import javax.swing.*;

public class Game {

  static int FPS = 25;
  boolean onStage;

  Camera cam;
  Menu menu;
  KeyList keys;
  Stage stage;
  Bar bar;

  public static void main(String s[]) throws Exception {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    JFrame f = new JFrame("Kuru");
    Game game = new Game();
    f.add(game.cam);
    f.setBounds(0, 0, screenSize.width, screenSize.height);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setVisible(true);

    game.run();
  }

  public Game() throws Exception {
    // screen resolution
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    cam = new Camera();
    cam.resize(screenSize.width, screenSize.height);

    // we won't draw the stage right now
    stage = new Stage();
    stage.searchStages(); // search for available stages in the stages directory

    // Menu
    menu = new Menu(stage.stages.length);
    cam.addKeyListener(menu); 
    cam.setFocusable(true); // keyboard focus

    // objects for play
    bar = new Bar(); // won't draw now, only in-play
    keys = new KeyList(); // keys for movement

  }

  public void update_menu() throws Exception {
    cam.clear();
    menu.draw(cam);
    if (menu.playRequested) {
      menu.playRequested = false;
      play(menu.stageID);
    }
  }

  public void play(int i) throws Exception {
    cam.removeKeyListener(menu); 
    cam.addKeyListener(keys); 
    stage.load(i, bar);
    onStage = true;
  }

  public void backToStageSelection() {


  }

  public void update_play() {
    bar.move(keys, stage);
    bar.collision(stage);
    if (bar.collided == bar.collided_max) bar.move(keys, stage);
    cam.move(bar, stage);

    cam.clear();
    stage.drawBelow(cam);
    bar.draw(cam);
    stage.drawAbove(cam);
  }

  public void run() throws Exception {
    cam.updateBuffer();
    while (true) {
      if (onStage) {
        cam.updateBuffer();
        update_play();
      } else {
        update_menu();
      }
      cam.repaint();
      Thread.sleep(1000 / FPS);
    }
  }
}
