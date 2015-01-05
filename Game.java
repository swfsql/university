import java.awt.*;
import javax.swing.*;

public class Game {

  static int FPS = 25;
  boolean onStage;

  Camera cam;
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

    // MENU HERE
    


    // GAME PLAY STARTS HERE (assuming stage 0 selected)

    // keyboard events
    keys = new KeyList();
    cam.addKeyListener(keys); 
    cam.setFocusable(true); // keyboard focus

    bar = new Bar();
    stage = new Stage();
    stage.searchStages();

    stage.load(0, bar);
    onStage = true;

  }

  public void update() {
    bar.collision(stage);
    bar.move(keys, stage);
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
        update();
      } else {

      }
      cam.repaint();
      Thread.sleep(1000 / FPS);
    }
  }
}
