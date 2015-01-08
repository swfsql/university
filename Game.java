import java.awt.*;
import javax.swing.*;

public class Game {

  static int FPS = 25;
  long time = 0;
  boolean onStage, goalCheck;

  //Main main;
  //Menu menu; //delete better because Menu.java and Game.java needs not to depends on
  Camera cam;
  KeyList keys;
  Stage stage;
  Bar bar;

  //public static void main(String[] args) throws Exception {
  public void myMain() throws Exception {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    JFrame f = new JFrame("Kuru");
    Game game = new Game(null);
    f.add(game.cam);
    f.setBounds(0, 0, screenSize.width, screenSize.height);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setVisible(true);

    game.run();
  }

  public Game(Main main) throws Exception {
	//main = main  
	// screen resolution
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    cam = new Camera();
    cam.resize(screenSize.width, screenSize.height);

    // we won't draw the stage right now
    stage = new Stage();
    stage.searchStages(); // search for available stages in the stages directory

    // Menu
    menu = new Menu(main);
    menu.setStageMax(stage.stages.length);
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

    /*if (goalCheck == true) {
    	main.setIsClear(true);
    	main.setCurrentTime(time);
    	main.setNext(main.SeqID.RESULT);
    	main.myMain();
    }*/
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
