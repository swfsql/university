import java.awt.*;
import javax.swing.*;

public class Game {

  static int FPS = 25;
  boolean onStage; // if we are on the menu or if we are playing a stage

  Camera cam;
  Menu menu; // menu acts as a KeyList, but also draw to the screen
  KeyList keys;
  Stage stage;
  Bar bar;

  public static void main(String s[]) throws Exception {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // get full screen size
    JFrame f = new JFrame("Kuru");
    Game game = new Game();
    f.add(game.cam); // Camera implements the necessary methods
    f.setBounds(0, 0, screenSize.width, screenSize.height);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setVisible(true);

    game.run(); // infinite loop
  }

  public Game() throws Exception {
    // screen resolution (again)
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

    // objects for play, but we will use this only after choosing a stage
    bar = new Bar();
    keys = new KeyList(); // keys for bar movement (TODO should we actually make the bar implements the KeyList methods?)

  }
  
  public void run() throws Exception {
    cam.updateBuffer();
	while (true) { // infinite loop
	  if (onStage) { // we we are playing (moving the bar)
	    cam.updateBuffer();
	    update_play();
	  } else { // we are NOT moving the bar, we are on the menu
	    update_menu();
	  }
	  cam.repaint();
      Thread.sleep(1000 / FPS);
    }
  }

  public void update_menu() throws Exception {
    cam.clear();
    menu.draw(cam);
    if (menu.playRequested) {
      menu.playRequested = false;
      play(menu.stageID);
    }
  }

  // after we finished the game, we go back to stage selection
  // TODO this function has never been used, will test in the future.
  public void backToStageSelection() {
    cam.removeKeyListener(keys); 
    cam.addKeyListener(menu);
    onStage = false;
  }
  
  public void play(int i) throws Exception {
    cam.removeKeyListener(menu); 
    cam.addKeyListener(keys); 
    stage.load(i, bar);
    onStage = true;
  }

  public void update_play() {
    bar.move(keys, stage);
    bar.collision(stage);
    
    // we dont let the bar touch a wall 
    if (bar.collided == bar.collided_max) bar.move(keys, stage);
    
    cam.move(bar, stage);

    cam.clear();
    stage.drawBelow(cam);
    bar.draw(cam);
    stage.drawAbove(cam);
  }

  
}
